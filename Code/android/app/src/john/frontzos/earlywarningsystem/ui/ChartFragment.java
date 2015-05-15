package john.frontzos.earlywarningsystem.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import john.frontzos.earlywarningsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChartFragment.OnRefreshData} interface
 * to handle interaction events.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {
    public static final String ARG_SECTION_NUMBER = "section_number";

    // TODO: Rename and change types of parameters
    private int mParam1;

    private OnRefreshData mListener;

    private LineChart chart;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(int param1) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.inject(this, view);
        chart = (LineChart) view.findViewById(R.id.chart);
        setDummyDataToChart(mParam1);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRefreshData) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRefreshData {
        // TODO: Update argument type and name
        public void OnRefreshData(Uri uri);
    }

    /** Let's try to make a chart with dummy data. */

    private void setDummyDataToChart(int fragmentNumber){
        LineDataSet data = new LineDataSet(generateData(fragmentNumber), "Apps");
        ArrayList<String> xVals= new ArrayList<String>();
        xVals.add("0");
        xVals.add("1");
        xVals.add("2");
        xVals.add("3");
        xVals.add("4");
        LineData dataChart = new LineData(xVals,data);
        chart.setData(dataChart);
        chart.invalidate();
    }

    private void styleChart(){
        chart.fitScreen();

    }

    private  ArrayList<Entry> generateData(int i){
        ArrayList<Entry>values = new ArrayList<Entry>();
        values.add(new Entry(new Random(Long.valueOf(i)).nextInt(1000),0));
        values.add(new Entry(80, 1));
        values.add(new Entry(300, 2));
        values.add(new Entry(310, 3));
        values.add(new Entry(340, 4));
        return values;
    }

}
