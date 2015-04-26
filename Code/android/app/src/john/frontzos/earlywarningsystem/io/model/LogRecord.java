package john.frontzos.earlywarningsystem.io.model;

import java.util.List;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 18/04/2015
 */
public class LogRecord {
    private String name;
    private int appID;
    private List<String> destination;
    private long totalPackages;
    private String source;


    public LogRecord() {
    }

    public LogRecord(String name, int appID, List<String> destination, long totalPackages, String source) {
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

    public List<String> getDestination() {
        return destination;
    }

    public void setDestination(List<String> destination) {
        this.destination = destination;
    }

    public long getTotalPackages() {
        return totalPackages;
    }

    public void setTotalPackages(long totalPackages) {
        this.totalPackages = totalPackages;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
