package john.frontzos.earlywarningsystem.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import dev.ukanth.ufirewall.LogActivity;
import john.frontzos.earlywarningsystem.R;

public class MainActivity extends Activity implements MainFragment.Callback{

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
        startActivity(new Intent(this, dev.ukanth.ufirewall.MainActivity.class));
    }

    @Override
    public void onOpenLogsButtonPressed() {
        startActivity( new Intent(this, LogActivity.class));
    }

    @Override
    public void onOpenCharts() {
        startActivity(new Intent(this, ChartActivity.class));
    }
}
