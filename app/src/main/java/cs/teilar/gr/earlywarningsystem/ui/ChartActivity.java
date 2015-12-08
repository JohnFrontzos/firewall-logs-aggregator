package cs.teilar.gr.earlywarningsystem.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import cs.teilar.gr.earlywarningsystem.R;


public class ChartActivity extends BaseActivity implements BaseChartFragment.OnRefreshData {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    DemoCollectionPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;


    public static final int PERCENTAGE_APP_PIE_CHART = 0;
    public static final int BLOCKS_PER_TIME_CHART = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        mAppSectionsPagerAdapter = new DemoCollectionPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void OnRefreshData(Uri uri) {

    }


    // Since this is an object collection, use a FragmentStatePagerAdapter,
    // and NOT a FragmentPagerAdapter.
    public class DemoCollectionPagerAdapter extends FragmentPagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;
            switch (i) {
                case PERCENTAGE_APP_PIE_CHART:
                    return new PieChartFragment();
                case BLOCKS_PER_TIME_CHART:
                    return new LineChartFragment();
                default:
                    return new BaseChartFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case PERCENTAGE_APP_PIE_CHART:
                    return getString(R.string.title_pie_chart_fragment);
                case BLOCKS_PER_TIME_CHART:
                    return getString(R.string.title_line_chart_fragment);
                default:
                    return "No Chart";
            }
        }
    }
}
