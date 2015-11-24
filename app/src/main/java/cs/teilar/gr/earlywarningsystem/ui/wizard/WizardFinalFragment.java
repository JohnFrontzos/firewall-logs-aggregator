package cs.teilar.gr.earlywarningsystem.ui.wizard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cs.teilar.gr.earlywarningsystem.R;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 24/11/2015
 */
public class WizardFinalFragment extends Fragment {

    public static WizardFinalFragment newInstance() {
        return new WizardFinalFragment();
    }

    public WizardFinalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wizard_final, container, false);
        ButterKnife.bind(this, v);

        return v;
    }
}
