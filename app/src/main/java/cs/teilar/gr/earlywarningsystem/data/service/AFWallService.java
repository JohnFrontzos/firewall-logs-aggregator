package cs.teilar.gr.earlywarningsystem.data.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.preference.PreferenceManager;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import cs.teilar.gr.earlywarningsystem.data.event.LogsUpdateEvent;
import cs.teilar.gr.earlywarningsystem.data.model.Contracts;
import cs.teilar.gr.earlywarningsystem.data.model.Contracts.Intents;
import cs.teilar.gr.earlywarningsystem.data.model.FirewallService;
import cs.teilar.gr.earlywarningsystem.data.model.LogRecord;
import cs.teilar.gr.earlywarningsystem.util.ApplicationUtils;
import cs.teilar.gr.earlywarningsystem.util.BusProvider;
import cs.teilar.gr.earlywarningsystem.util.DateUtils;
import cs.teilar.gr.earlywarningsystem.util.ShellExecuter;
import io.realm.Realm;
import io.realm.RealmList;
import timber.log.Timber;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 20/11/2015
 */
public class AFWallService extends IntentService implements FirewallService {

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     **/
    public AFWallService() {
        super("AFWall");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case Intents.SYNC_LOG:
                    syncLogs();
                    setLastUpdateDate();
                    break;
            }
        }
    }

    private void syncLogs() {
        String raw = getLogs();
        storeLogs(parseLogs(raw));
    }


    @Override
    public String getLogs() {
        ShellExecuter shell = new ShellExecuter();
        return shell.executor(new String[]{"su", "-c", "busybox dmesg"});
    }

    @Override
    public RealmList<LogRecord> parseLogs(String raw) {
        return parseLogsFromKernel(raw, true);
    }

   /* @Produce
    public String produceUpdate() {
        return new LogsUpdateEvent();
    }*/


    public void storeLogs(RealmList<LogRecord> records) {
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        realm.copyToRealm(records);
        realm.commitTransaction();
        BusProvider.get().post(new LogsUpdateEvent(records));
    }

    private RealmList<LogRecord> parseLogsFromKernel(String dmesg, boolean onlyNew) {
        final BufferedReader r = new BufferedReader(new StringReader(dmesg));
        RealmList<LogRecord> list = new RealmList<>();
        final Integer unknownUID = -11;
        String line;
        int start, end;
        String out, src, dst, proto, spt, dpt, len;
        List<ApplicationInfo> applications = ApplicationUtils.getApplicationList(this);

        try {
            while ((line = r.readLine()) != null) {
                if (!line.contains("{AFL}")) continue;
                Integer appid = unknownUID;
                DateTime date = null;

                LogRecord record = new LogRecord();

                if (((start = line.indexOf("[")) != -1) && ((end = line.indexOf("]", start)) != -1)) {
                    date = new DateTime(DateUtils.getDate(line.substring(start + 1, end).trim()).getMillis());
                }

                if (date != null && onlyNew) {
                    if (date.isAfter(getLastUpdateDate())) {
                        record.setTimestamp(date.getMillis());
                    } else {
                        continue;
                    }

                } else if (date != null) {
                    record.setTimestamp(date.getMillis());
                }

                if (((start = line.indexOf("UID=")) != -1) && ((end = line.indexOf(" ", start)) != -1)) {
                    appid = Integer.parseInt(line.substring(start + 4, end));
                    record.setAppID(appid);

                    for (ApplicationInfo app : applications) {
                        if (app.uid == appid) {
                            String name = getPackageManager().getApplicationLabel(app).toString();
                            Timber.d("App ID: %d App Name: %s", appid, name);
                            record.setName(name);
                            break;
                        }
                    }
                }


                if (((start = line.indexOf("DST=")) != -1) && ((end = line.indexOf(" ", start)) != -1)) {
                    dst = line.substring(start + 4, end);
                    record.setDestination(dst);
                }

//                if (((start = line.indexOf("DPT=")) != -1) && ((end = line.indexOf(" ", start)) != -1)) {
//                    dpt = line.substring(start + 4, end);
//                    record.dpt = dpt;
//                }

//                if (((start = line.indexOf("SPT=")) != -1) && ((end = line.indexOf(" ", start)) != -1)) {
//                    spt = line.substring(start + 4, end);
//                    record.spt = spt;
//                }

                if (((start = line.indexOf("PROTO=")) != -1) && ((end = line.indexOf(" ", start)) != -1)) {
                    proto = line.substring(start + 6, end);
                    record.setProtocol(proto);
                }

//                if (((start = line.indexOf("LEN=")) != -1) && ((end = line.indexOf(" ", start)) != -1)) {
//                    len = line.substring(start + 4, end);
//                    record.len = len;
//                }

                if (((start = line.indexOf("SRC=")) != -1) && ((end = line.indexOf(" ", start)) != -1)) {
                    src = line.substring(start + 4, end);
                    record.setSource(src);
                }

//                if (((start = line.indexOf("OUT=")) != -1) && ((end = line.indexOf(" ", start)) != -1)) {
//                    out = line.substring(start + 4, end);
//                    record.out = out;
//                }
                list.add(record);
            }
        } catch (IOException e) {
            Timber.e("Log parsing error");
        }
        return list;

    }

    private DateTime getLastUpdateDate() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return new DateTime(prefs.getLong(Contracts.Prefs.LAST_LOG_INSERTED, 0));
    }

    private void setLastUpdateDate() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putLong(Contracts.Prefs.LAST_LOG_INSERTED, DateTime.now().getMillis()).apply();
    }

}
