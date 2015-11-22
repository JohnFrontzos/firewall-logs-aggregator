package cs.teilar.gr.earlywarningsystem.data.event;

import cs.teilar.gr.earlywarningsystem.data.model.LogRecord;
import io.realm.RealmList;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 22/11/2015
 */
public class LogsUpdateEvent {

    private RealmList<LogRecord> records;

    public LogsUpdateEvent(RealmList<LogRecord> records) {
        this.records = records;
    }

    public RealmList<LogRecord> getRecords() {
        return records;
    }

    public void setRecords(RealmList<LogRecord> records) {
        this.records = records;
    }
}
