package cs.teilar.gr.earlywarningsystem.data.model;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 13/05/2015
 */
public class Contracts {
    private static final String BASE = "cs.teilar.gr.earlywarningsystem";
    private static final String ACTION = ".ACTION_";


    public interface Intents {

        public static final String SYNC_LOG = BASE + ACTION + "SYNC_LOG";

    }

    public interface Prefs {

        public static final String FIREWALL_INIT = "isFirewallInitialized";
        public static final String LAST_LOG_INSERTED = "lastLogInserted";

    }


    public static final String PACKAGE_NAME_AFWALL = "dev.ukanth.ufirewall";

}
