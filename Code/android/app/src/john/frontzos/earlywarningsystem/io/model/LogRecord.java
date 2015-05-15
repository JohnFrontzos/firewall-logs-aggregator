package john.frontzos.earlywarningsystem.io.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 18/04/2015
 */
public class LogRecord extends RealmObject{
    private String name;

    @PrimaryKey
    private int appID;
    private RealmList<IpAddress> destination;
    private long totalPackages;
    private IpAddress source;


    public LogRecord() {
    }

    public LogRecord(String name, int appID, RealmList<IpAddress> destination, long totalPackages, IpAddress source) {
        this.name = name;
        this.appID = appID;
        this.destination = destination;
        this.totalPackages = totalPackages;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public RealmList<IpAddress> getDestination() {
        return destination;
    }

    public void setDestination(RealmList<IpAddress> destination) {
        this.destination = destination;
    }

    public long getTotalPackages() {
        return totalPackages;
    }

    public void setTotalPackages(long totalPackages) {
        this.totalPackages = totalPackages;
    }

    public IpAddress getSource() {
        return source;
    }

    public void setSource(IpAddress source) {
        this.source = source;
    }
}
