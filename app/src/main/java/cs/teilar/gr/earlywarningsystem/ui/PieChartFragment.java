package cs.teilar.gr.earlywarningsystem.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
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
        setData();
        Legend legend = mChart.getLegend();
        legend.setEnabled(false);


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
    }

    PieData populateData() {
        LinkedHashMap<Integer, Float> map = getMapOfApps(getDB(getActivity()));
        if (map.size() == 0) {
            return null;
        }

        ArrayList<String> labels = new ArrayList<>(map.size());
        ArrayList<Entry> values = new ArrayList<>(map.size());

        for (Integer key : map.keySet()) {
            labels.add(String.valueOf(key));
            values.add(new Entry(map.get(key), labels.size() - 1));
        }

        PieData data = new PieData(labels.toArray(new String[labels.size()]));
        PieDataSet dataSet = new PieDataSet(values, "test");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setDataSet(dataSet);
        return data;
    }

    private ArrayList<LogRecord> getDB(Context context) {
        ArrayList<LogRecord> list = new ArrayList<>();
        Realm realm = Realm.getInstance(context);
        RealmResults<LogRecord> results = realm.where(LogRecord.class)
                .notEqualTo("appID", -11)
                .findAll();
        list.addAll(results);
        return list;
    }

    private LinkedHashMap<Integer, Float> getMapOfApps(ArrayList<LogRecord> list) {
        LinkedHashMap<Integer, Float> map = new LinkedHashMap<>();
        for (LogRecord record : list) {
            int id = record.getAppID();
            if (map.containsKey(id)) {
                map.put(id, map.get(id) + 1);
            } else {
                map.put(id, (float) 1);
            }
        }
        return map;
    }

}
