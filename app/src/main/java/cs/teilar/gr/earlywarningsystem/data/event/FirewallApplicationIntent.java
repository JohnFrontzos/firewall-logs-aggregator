package cs.teilar.gr.earlywarningsystem.data.event;

import android.content.Intent;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 21/02/2016
 */
public class FirewallApplicationIntent {

    private Intent intent;

    public FirewallApplicationIntent(Intent intent) {
        this.intent = intent;
    }

    public Intent getIntent() {
        return intent;
    }

}
