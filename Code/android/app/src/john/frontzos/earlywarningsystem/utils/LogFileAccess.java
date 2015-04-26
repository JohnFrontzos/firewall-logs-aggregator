package john.frontzos.earlywarningsystem.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import dev.ukanth.ufirewall.Api;
import dev.ukanth.ufirewall.G;
import dev.ukanth.ufirewall.NflogService;
import dev.ukanth.ufirewall.RootShell;
import dev.ukanth.ufirewall.log.LogInfo;
import john.frontzos.earlywarningsystem.events.LogDataEvent;
import john.frontzos.earlywarningsystem.io.model.LogRecord;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 3/27/15
 */
public class LogFileAccess {

    public static String LOG_APP_NAME = "appName";
    public static String LOG_APP_ID = "appID";
    public static String LOG_SRC = "appSrc";
    public static String LOG_DEST = "appDest";
    public static String LOG_LEN = "appLen";
    public static String LOG_TOTAL_BLOCKED = "appTotalBlocked";


    private Context mContext;
    private String mData;

    public LogFileAccess(Context ctx) {
        mContext = ctx;
    }

    public void populateData() {
        if (G.logTarget().equals("NFLOG")) {
            triggerCallback(parseData(NflogService.fetchLogs()));
        } else {
            Api.fetchLogs(mContext, new RootShell.RootCommand()
                    .setLogging(true)
                    .setReopenShell(true)
                    .setFailureToast(dev.ukanth.ufirewall.R.string.log_fetch_error)
                    .setCallback(new RootShell.RootCommand.Callback() {
                        public void cbFunc(RootShell.RootCommand state) {
                            if (state.exitCode != 0) {
                                return;
                            } else {
                                triggerCallback(parseData(state.res.toString()));
                            }
                        }
                    }));
        }
    }

    private void triggerCallback(String data) {
        BusProvider.getInstance().post(new LogDataEvent(data));
    }

    public String parseData(String raw) {
        String data = LogInfo.parseLog(mContext, raw);
        parseLogDataFromFirewall(data);
        return data;

    }

    public List<LogRecord> parseLogDataFromFirewall(String data) {
        List<LogRecord> list = new ArrayList<LogRecord>();
        final BufferedReader r = new BufferedReader(new StringReader(data.toString()));
        String line;
        LogRecord record = new LogRecord();
        ArrayList<String> dst = new ArrayList<String>();
        int start, end;
        try {
            while ((line = r.readLine()) != null) {
                if (record == null) {
                    record = new LogRecord();
                    dst.clear();
                }
                if ((start = line.indexOf("AppID")) != -1) {
                    record.setAppID(Integer.parseInt(line.substring(start + 8)));
                } else if ((start = line.indexOf("Application's Name")) != -1) {
                    record.setName(line.substring(start + 20));
                } else if ((start = line.indexOf("Total Packets Blocked")) != -1) {
                    record.setTotalPackages(Long.parseLong(line.substring(start + 23)));
                } else if (((start = line.indexOf("[TCP]")) != -1) && ((end = line.indexOf(")", start)) != -1)) {
                    dst.add(line.substring(start + 5, end - 3));
                } else if (line.contains("---------")) {
                    if (TextUtils.isEmpty(record.getSource())) {
                        record.setSource("localhost");
                    }
                    record.setDestination(dst);
                    list.add(record);
                    record = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


}
