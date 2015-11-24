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
 * @since 28/10/2015
 */
public class WizardFragment extends Fragment {
    
    public static WizardFragment newInstance() {
        return new WizardFragment();
    }

    public WizardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wizard_welcome, container, false);
        ButterKnife.bind(this, v);

       /* String[] labels = getResources().getStringArray(R.array.wizard);
        text.setCompoundDrawablesWithIntrinsicBounds(0, getImageResource(mPage), 0, 0);
        text.setText(labels[mPage]);*/

        return v;
    }

}