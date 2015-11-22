package cs.teilar.gr.earlywarningsystem.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import cs.teilar.gr.earlywarningsystem.R;


public class MainActivity extends BaseActivity implements MainFragment.Callback{

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
    public void onOpenFirewallButtonPressed() {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("dev.ukanth.ufirewall");
        startActivity(launchIntent);
    }

    @Override
    public void onOpenLogsButtonPressed() {
        //startActivity( new Intent(this, LogActivity.class));
    }

    @Override
    public void onOpenCharts() {
        startActivity(new Intent(this, ChartActivity.class));
    }
}
