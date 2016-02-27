package cs.teilar.gr.earlywarningsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.squareup.otto.Subscribe;

import cs.teilar.gr.earlywarningsystem.R;
import cs.teilar.gr.earlywarningsystem.data.event.FirewallApplicationIntent;
import cs.teilar.gr.earlywarningsystem.data.model.Contracts;
import cs.teilar.gr.earlywarningsystem.data.service.AFWallService;
import cs.teilar.gr.earlywarningsystem.util.BusProvider;


public class MainActivity extends BaseActivity implements MainFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.get().register(this);
    }

    // TODO: 21/2/16 need to pick firewall from service
    @Override
    public void onOpenFirewallButtonPressed() {
        Intent service = new Intent(this, AFWallService.class);
        service.setAction(Contracts.Intents.APPLICATION_INTENT);
        startService(service);
    }

    @Override
    public void onOpenLogsButtonPressed() {
        //startActivity( new Intent(this, LogActivity.class));
    }

    @Override
    public void onOpenWizardButtonPressed() {
        startActivity(new Intent(this, WizardActivity.class));
    }

    @Override
    public void onOpenCharts() {
        startActivity(new Intent(this, ChartActivity.class));
    }

    @Subscribe
    public void getFirewallIntent(FirewallApplicationIntent intent) {
        startActivity(intent.getIntent());
    }

}
