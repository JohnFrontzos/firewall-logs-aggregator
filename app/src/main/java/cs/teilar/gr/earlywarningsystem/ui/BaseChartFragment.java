package cs.teilar.gr.earlywarningsystem.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
import cs.teilar.gr.earlywarningsystem.data.model.LogRecord;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseChartFragment.OnRefreshData} interface
 * to handle interaction events.
 * Use the {@link BaseChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseChartFragment extends Fragment {
    public static final String ARG_SECTION_NUMBER = "section_number";

    // TODO: Rename and change types of parameters
    private int mParam1;

    private OnRefreshData mListener;

    public static BaseChartFragment newInstance(int param1) {
        BaseChartFragment fragment = new BaseChartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public BaseChartFragment() {
        // Required empty public constructor
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


    private ArrayList<LogRecord> getData(Context context) {
        ArrayList<LogRecord> list = new ArrayList<>();
        Realm realm = Realm.getInstance(context);
        RealmResults<LogRecord> results = realm.where(LogRecord.class)
                .notEqualTo("appID", -11)
                .findAll();

        for (LogRecord record : results)
            list.add(record);
        return list;
    }

}
