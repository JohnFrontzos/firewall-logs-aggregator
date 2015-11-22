package cs.teilar.gr.earlywarningsystem.data.model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 18/04/2015
 */
public class LogRecord extends RealmObject {

    private int appID;
    private String name;
    private String destination;
    private String source;
    private String protocol;
    private Date timestamp;


    public LogRecord() {
    }

    public LogRecord(String name, int appID, String destination, String source, String protocol, Date timestamp) {
        this.name = name;
        this.appID = appID;
        this.destination = destination;
        this.source = source;
        this.protocol = protocol;
        this.timestamp = timestamp;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
