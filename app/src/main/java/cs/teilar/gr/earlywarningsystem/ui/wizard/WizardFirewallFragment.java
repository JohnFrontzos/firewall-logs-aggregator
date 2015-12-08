package cs.teilar.gr.earlywarningsystem.ui.wizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cs.teilar.gr.earlywarningsystem.R;
import cs.teilar.gr.earlywarningsystem.data.model.Contracts;
import cs.teilar.gr.earlywarningsystem.data.service.AFWallService;
import cs.teilar.gr.earlywarningsystem.util.ApplicationUtils;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 24/11/2015
 */
public class WizardFirewallFragment extends Fragment {

    @Bind(R.id.layout_checkbox) LinearLayout layout;
    @Bind(R.id.empty) TextView empty;



    public static WizardFirewallFragment newInstance() {
        return new WizardFirewallFragment();
    }

    public WizardFirewallFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wizard_firewalls, container, false);
        ButterKnife.bind(this, v);
        populateFirewallList(layout);
        return v;
    }

    void populateFirewallList(LinearLayout layout) {
        if (aFWallisInstalled()) {
            empty.setVisibility(View.GONE);
            addCheckbox(layout, R.id.afwall_checkbox, "AFWall+");
        } else {
            empty.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.empty)
    public void download() {
        installAFWall();
    }

    @OnClick(R.id.title_refresh)
    public void onRefresh() {
        populateFirewallList(layout);
    }

    void addCheckbox(LinearLayout layout, final int id, String name) {
        if (layout.findViewById(R.id.afwall_checkbox) == null) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setText(name);
            cb.setId(id);
            layout.addView(cb);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    switch (id) {
                        case R.id.afwall_checkbox:
                            if (isChecked) {
                                if (getActivity() != null) {
                                    Intent service = new Intent(getActivity(), AFWallService.class);
                                    service.setAction(Contracts.Intents.SYNC_LOG);
                                    getActivity().startService(service);
                                }
                            }
                            break;
                    }
                }
            });
        }
    }


    // check if firewall is installed and trigger the service
    boolean aFWallisInstalled() {
        return (ApplicationUtils.isPackageInstalled(getActivity(), Contracts.PACKAGE_NAME_AFWALL));
    }

    void installAFWall() {
        getActivity().startActivity(ApplicationUtils.openGPlay(Contracts.PACKAGE_NAME_AFWALL));
    }
}