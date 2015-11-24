package cs.teilar.gr.earlywarningsystem.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import cs.teilar.gr.earlywarningsystem.data.model.LogRecord;
import io.realm.Realm;
import io.realm.RealmList;
import timber.log.Timber;

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

    public String populateData() {
        ShellExecuter executer = new ShellExecuter();
        mData = executer.executor(new String[]{"su", "-c", "busybox dmesg"});
        return mData;
    }

    public RealmList<LogRecord> parseData(String raw) {
        RealmList<LogRecord> records = parseLogsFromKernel(raw);
        Realm realm = Realm.getInstance(mContext);
        realm.beginTransaction();
        realm.copyToRealm(records);
        realm.commitTransaction();
        return records;

    }

  /* public RealmList<LogRecord> parseLogDataFromFirewall(String data) {
        RealmList<LogRecord> list = new RealmList<LogRecord>();
        final BufferedReader r = new BufferedReader(new StringReader(data.toString()));
        String line;
        LogRecord record = new LogRecord();
        RealmList<IpAddress> dst = new RealmList<IpAddress>();
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
                    dst.add(new IpAddress(line.substring(start + 5, end - 3)));
                } else if (line.contains("---------")) {
                    if (record.getSource() != null && TextUtils.isEmpty(record.getSource().getIp())) {
                        record.setSource(new IpAddress("localhost"));
                    }
                    record.setDestination(dst);
                    list.add(record);
                    record = null;
                }
            }
        } catch (IOException e) {
            Timber.e(e, "Error parsing logs");
            //e.printStackTrace();
        }

        return list;
    }*/

    public RealmList<LogRecord> parseLogsFromKernel(String dmesg) {
        final BufferedReader r = new BufferedReader(new StringReader(dmesg.toString()));
        RealmList<LogRecord> list = new RealmList<LogRecord>();
        final Integer unknownUID = -11;
        String line;
        int start, end;
        Integer appid;
        String out, src, dst, proto, spt, dpt, len;
        LogRecord record = null;

        try {
            while ((line = r.readLine()) != null) {
                if (!line.contains("{AFL}")) continue;
                appid = unknownUID;
                if (((start = line.indexOf("UID=")) != -1) && ((end = line.indexOf(" ", start)) != -1)) {
                    appid = Integer.parseInt(line.substring(start + 4, end));
                }

                if (record == null) {
                    record = new LogRecord();
                }

                //TODO change Real Obj to long ? or to JODA time? I don't know...
                if (((start = line.indexOf("[ ")) != -1) && ((end = line.indexOf("]", start)) != -1)) {
                    record.setTimestamp(new Date(DateUtils.getDate(line.substring(start + 1, end).trim()).getMillis()));
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


                record.setAppID(appid);
                list.add(record);
            }
        } catch (IOException e) {
            Timber.e("Log parsing error");
        }
        return list;

    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }
}
