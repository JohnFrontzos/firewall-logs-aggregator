package cs.teilar.gr.earlywarningsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.orhanobut.hawk.Hawk;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cs.teilar.gr.earlywarningsystem.R;
import cs.teilar.gr.earlywarningsystem.data.model.Contracts;
import cs.teilar.gr.earlywarningsystem.ui.adapter.WizardPagerAdapter;
import me.relex.circleindicator.CircleIndicator;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 24/11/2015
 */
public class WizardActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    @Bind(R.id.viewpager) ViewPager mPager;
    @Bind(R.id.indicator) CircleIndicator mIndicator;
    @Bind(R.id.next) Button mNext;
    @Bind(R.id.back) Button mBack;

    private WizardPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        ButterKnife.bind(this);

        //transparent status bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mPagerAdapter = new WizardPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);
        mIndicator.setViewPager(mPager);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            mBack.setText(R.string.skip);
            mNext.setText(R.string.next);
        } else if (position == mPagerAdapter.getCount() - 1) {
            mNext.setText(R.string.done);
        } else {
            mBack.setText(R.string.previous);
            mNext.setText(R.string.next);
        }

    }

    @OnClick({R.id.back, R.id.next})
    void onNavigationClick(View view) {
        int id = view.getId();
        int item = mPager.getCurrentItem();
        switch (id) {
            case R.id.back:
                if (item == 0) {
                    openNextActivity();
                } else {
                    mPager.setCurrentItem(item - 1);
                }
                break;
            case R.id.next:
                if (item == mPagerAdapter.getCount() - 1) {
                    openNextActivity();
                } else {
                    mPager.setCurrentItem(item + 1);

                }
                break;
        }

    }

    void openNextActivity() {
        Hawk.put(Contracts.Prefs.FIRST_TIME, false);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
