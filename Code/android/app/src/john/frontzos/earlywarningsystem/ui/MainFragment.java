package john.frontzos.earlywarningsystem.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dev.ukanth.ufirewall.Api;
import john.frontzos.earlywarningsystem.R;
import john.frontzos.earlywarningsystem.events.LogDataEvent;
import john.frontzos.earlywarningsystem.utils.BusProvider;
import john.frontzos.earlywarningsystem.utils.LogFileAccess;
import timber.log.Timber;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 21/01/2015
 * @since 21/01/2015 chart button added
 */
public class MainFragment extends Fragment{
    @InjectView(R.id.button_logs) Button getLogs;
    @InjectView(R.id.switch_enable_firewall) Switch enableFirewall;
    @InjectView(R.id.button_startFirewall)  Button startFirewall;
    @InjectView(R.id.button_parse_logs) Button parseLogs;
    @InjectView(R.id.button_chart) Button charts;


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

    @Subscribe
    public void onLogDataEventCallback(LogDataEvent event) {
        Toast.makeText(getActivity(),"Houston we have data!!!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);

    }

    @OnClick(R.id.button_startFirewall)
    void onStartFirewall(){
        mCallback.onOpenFirewallButtonPressed();

    }

    @OnClick(R.id.button_chart)
    void onChartClicked(){
        mCallback.onOpenCharts();

    }

    @OnClick(R.id.button_parse_logs)
    void setParseLogs(){
        LogFileAccess log = new LogFileAccess(getActivity());
        log.populateData();

    }


    @OnClick(R.id.button_logs)
    void onOpenLogs(){
        mCallback.onOpenLogsButtonPressed();

    }

    @OnCheckedChanged(R.id.switch_enable_firewall)
    void onChecked(boolean checked) {
            Api.setEnabled(getActivity(), checked,true);
            Api.setLogging(getActivity(), checked);
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
        public void onOpenCharts();

    }



}
