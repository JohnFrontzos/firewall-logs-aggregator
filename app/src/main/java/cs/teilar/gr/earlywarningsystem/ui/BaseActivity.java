package cs.teilar.gr.earlywarningsystem.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import cs.teilar.gr.earlywarningsystem.R;
import cs.teilar.gr.earlywarningsystem.data.model.Contracts;
import cs.teilar.gr.earlywarningsystem.data.service.AFWallService;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 20/11/2015
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Not very sophisticated, but provides the "back" functionality we need for now.
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_fetch_logs) {
            refreshLogs();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void refreshLogs() {
        Intent service = new Intent(this, AFWallService.class);
        service.setAction(Contracts.Intents.SYNC_LOG);
        startService(service);
    }
}
