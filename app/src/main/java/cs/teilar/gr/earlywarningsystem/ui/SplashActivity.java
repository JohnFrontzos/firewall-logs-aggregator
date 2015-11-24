package cs.teilar.gr.earlywarningsystem.ui;

import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.hawk.Hawk;

import cs.teilar.gr.earlywarningsystem.R;
import cs.teilar.gr.earlywarningsystem.data.model.Contracts;
import cs.teilar.gr.earlywarningsystem.data.service.AFWallService;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 20/11/2015
 */
public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Hawk.get(Contracts.Prefs.FIRST_TIME, true)) {
            startActivity(new Intent(this, WizardActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
            Intent service = new Intent(this, AFWallService.class);
            service.setAction(Contracts.Intents.SYNC_LOG);
            startService(service);
        }
        finish();
    }
}


