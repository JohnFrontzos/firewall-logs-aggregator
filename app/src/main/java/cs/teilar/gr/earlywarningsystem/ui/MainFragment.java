package cs.teilar.gr.earlywarningsystem.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cs.teilar.gr.earlywarningsystem.R;
import cs.teilar.gr.earlywarningsystem.util.BusProvider;
import timber.log.Timber;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 21/01/2015
 */
public class MainFragment extends Fragment {


    @Bind(R.id.button_startFirewall) Button buttonStartFirewall;
    @Bind(R.id.button_chart) Button buttonCharts;
    @Bind(R.id.button_logs) Button buttonLogs;

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
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.get().register(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);

    }

    @OnClick(R.id.button_startFirewall)
    void onStartFirewall() {
        mCallback.onOpenFirewallButtonPressed();
    }

    @OnClick(R.id.button_chart)
    void onChartClicked() {
        mCallback.onOpenCharts();
    }

    @OnClick(R.id.button_logs)
    void onOpenLogs() {
        mCallback.onOpenLogsButtonPressed();
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
