package cs.teilar.gr.earlywarningsystem.ui;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.otto.Subscribe;

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

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 22/11/2015
 */
public class PieChartFragment extends Fragment {

    public static String TITLE = "Applications Blocked";

    @Bind(R.id.chart) PieChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);
        mChart.setHighlightPerTapEnabled(true);

        mChart.setRotationEnabled(false);

        setData();

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.PIECHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
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


    @Subscribe
    public void logsUpdateEvent(LogsUpdateEvent event) {
        setData();
        mChart.invalidate();
    }

    void setData() {
        mChart.setData(populateData());
        mChart.highlightValues(null);
    }

    PieData populateData() {
        LinkedHashMap<String, Float> map = getMapOfApps(getDB(getActivity()));
        if (map.size() == 0) {
            return null;
        }

        ArrayList<String> labels = new ArrayList<>(map.size());
        ArrayList<Entry> values = new ArrayList<>(map.size());

        for (String key : map.keySet()) {
            labels.add(String.valueOf(key));
            values.add(new Entry(map.get(key), labels.size() - 1));
        }
        PieDataSet dataSet = new PieDataSet(values, "Apps");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(labels.toArray(new String[labels.size()]));
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        data.setDataSet(dataSet);
        return data;
    }

    private ArrayList<LogRecord> getDB(Context context) {
        ArrayList<LogRecord> list = new ArrayList<>();
        Realm realm = Realm.getInstance(context);
        RealmResults<LogRecord> results = realm.where(LogRecord.class)
                .findAll();
        list.addAll(results);
        return list;
    }

    private LinkedHashMap<String, Float> getMapOfApps(ArrayList<LogRecord> list) {
        LinkedHashMap<String, Float> map = new LinkedHashMap<>();
        for (LogRecord record : list) {
            String name = record.getName();
            if (map.containsKey(name)) {
                map.put(name, map.get(name) + 1);
            } else {
                map.put(name, (float) 1);
            }
        }
        return map;
    }

}
