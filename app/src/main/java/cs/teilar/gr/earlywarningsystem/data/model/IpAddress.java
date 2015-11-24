package cs.teilar.gr.earlywarningsystem.data.model;

import io.realm.RealmObject;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 13/05/2015
 */
public class IpAddress extends RealmObject {

    private String ip;

    public IpAddress() {

    }


    public IpAddress(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
