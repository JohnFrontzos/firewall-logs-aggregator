package cs.teilar.gr.earlywarningsystem.data.model;

import android.app.Service;
import android.content.Intent;

import io.realm.RealmList;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 20/11/2015
 */
public interface FirewallService{

    public String getLogs();
    public RealmList<LogRecord> parseLogs(String raw);
    /*
    public boolean isFirewallInstalled();
    public Intent downloadApp();*/

}
