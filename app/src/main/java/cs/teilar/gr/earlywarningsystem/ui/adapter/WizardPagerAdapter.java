package cs.teilar.gr.earlywarningsystem.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cs.teilar.gr.earlywarningsystem.ui.wizard.WizardFinalFragment;
import cs.teilar.gr.earlywarningsystem.ui.wizard.WizardFirewallFragment;
import cs.teilar.gr.earlywarningsystem.ui.wizard.WizardFragment;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 28/10/2015
 */
public class WizardPagerAdapter extends FragmentPagerAdapter {

    private int WIZARD_PAGE_COUNT = 3;


    public WizardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return WizardFragment.newInstance();
            case 1:
                return WizardFirewallFragment.newInstance();
            case 2:
            default:
                return WizardFinalFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return WIZARD_PAGE_COUNT;
    }
}
