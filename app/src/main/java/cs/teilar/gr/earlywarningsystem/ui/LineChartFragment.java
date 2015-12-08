package cs.teilar.gr.earlywarningsystem.ui;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.squareup.otto.Subscribe;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cs.teilar.gr.earlywarningsystem.R;
import cs.teilar.gr.earlywarningsystem.data.event.LogsUpdateEvent;
import cs.teilar.gr.earlywarningsystem.data.model.LogRecord;
import cs.teilar.gr.earlywarningsystem.util.BusProvider;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 22/11/2015
 */
public class LineChartFragment extends Fragment implements OnChartValueSelectedListener {

    public static String TITLE = "Applications Blocked";

    @Bind(R.id.chart) LineChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("No data");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        LimitLine ll1 = new LimitLine(130f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        setData();

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);


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


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Timber.i("Entry selected", e.toString());
        Timber.i("", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
    }

    @Override
    public void onNothingSelected() {

    }

    @Subscribe
    public void logsUpdateEvent(LogsUpdateEvent event) {
        setData();
        mChart.invalidate();
    }

    void setData() {
        mChart.setData(populateData(DateTime.now().minusDays(5)));
    }

    LineData populateData(DateTime dateToStart) {
         LinkedHashMap<DateTime, Long> map = getDataForPeriod(getActivity(), dateToStart);

        if (map.size() == 0) {
            return null;
        }

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (DateTime key : map.keySet()) {
            xVals.add(new LocalDate(key).toString());
            yVals.add(new Entry(map.get(key), xVals.size() - 1));
        }


        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "Records Blocked");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);
//        set1.setDrawFilled(true);
        // set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
        // Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        return new LineData(xVals, dataSets);
    }

    private long getDBData(Context context, DateTime timestamp) {
        Realm realm = Realm.getInstance(context);
        RealmResults<LogRecord> results = realm.where(LogRecord.class)
                .between("timestamp", timestamp.withTimeAtStartOfDay().getMillis(), timestamp.plusDays(1).withTimeAtStartOfDay().getMillis())
                .findAll();
        return results.size();
    }

    private LinkedHashMap<DateTime, Long> getDataForPeriod(Context context, DateTime date) {
        LinkedHashMap<DateTime, Long> data = new LinkedHashMap<>();
        Days days = Days.daysBetween(date, DateTime.now());
        for (int i = days.getDays(); i >= 0; i--) {
            DateTime dateToFind = DateTime.now().minusDays(i);
            long count = getDBData(context, dateToFind);
            data.put(dateToFind, count);
        }
        return data;

    }

}
