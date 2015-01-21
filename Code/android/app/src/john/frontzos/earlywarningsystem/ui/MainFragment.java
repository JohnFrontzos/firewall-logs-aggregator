package john.frontzos.earlywarningsystem.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dev.ukanth.ufirewall.Api;
import john.frontzos.earlywarningsystem.R;
import timber.log.Timber;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 21/01/2015
 */
public class MainFragment extends Fragment{
    @InjectView(R.id.button_logs) Button getLogs;
    @InjectView(R.id.switch_enable_firewall) Switch enableFirewall;
    @InjectView(R.id.button_startFirewall)  Button startFirewall;

    private Callback mCallback;

    public MainFragment() {
        // Default empty constructor. Required.
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set tag for logging

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        setHasOptionsMenu(true);
        enableFirewall.setChecked(Api.isEnabled(getActivity()));
        return view;
    }

    @OnClick(R.id.button_startFirewall)
    void onStartFirewall(){
        mCallback.onOpenFirewallButtonPressed();

    }


    @OnClick(R.id.button_logs)
    void onOpenLogs(){
        mCallback.onOpenLogsButtonPressed();

    }

    @OnCheckedChanged(R.id.switch_enable_firewall)
    void onChecked(boolean checked) {
            Api.setEnabled(getActivity(), checked,true);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
                mCallback = (Callback) activity;
            } catch (ClassCastException e) {
                Timber.w(e, "%s must implement fragment's callbacks.", activity.getLocalClassName());
            }
        }

    public interface Callback {
        public void onOpenFirewallButtonPressed();
        public void onOpenLogsButtonPressed();

    }

}
