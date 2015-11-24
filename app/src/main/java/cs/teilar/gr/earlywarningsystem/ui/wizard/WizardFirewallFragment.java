package cs.teilar.gr.earlywarningsystem.ui.wizard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cs.teilar.gr.earlywarningsystem.R;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 24/11/2015
 */
public class WizardFirewallFragment extends Fragment {
    private int mPage;

    @Bind(R.id.text)
    TextView text;

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

       /* String[] labels = getResources().getStringArray(R.array.wizard);
        text.setCompoundDrawablesWithIntrinsicBounds(0, getImageResource(mPage), 0, 0);
        text.setText(labels[mPage]);*/

        return v;
    }
}
