package com.sicauthefu.identify;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdentifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdentifyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<ProgressBar> progressBars;
    private ArrayList<TextView> textViews;
    private int period = 600;
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IdentifyFragment() {
        // Required empty public constructor
        progressBars = new ArrayList<ProgressBar>();
        textViews = new ArrayList<TextView>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdentifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IdentifyFragment newInstance(String param1, String param2) {
        IdentifyFragment fragment = new IdentifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_identify, container, false);

        progressBars.add(view.findViewById(R.id.progressBar1));
        progressBars.add(view.findViewById(R.id.progressBar2));
        progressBars.add(view.findViewById(R.id.progressBar3));
        progressBars.add(view.findViewById(R.id.progressBar4));
        progressBars.add(view.findViewById(R.id.progressBar5));
        textViews.add(view.findViewById(R.id.textView11));
        textViews.add(view.findViewById(R.id.textView22));
        textViews.add(view.findViewById(R.id.textView33));
        textViews.add(view.findViewById(R.id.textView44));
        textViews.add(view.findViewById(R.id.textView55));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TimerTask timerTask = new RefreshTask();
        new Timer().schedule(timerTask, 0, period);
    }

    public void refreshData(ArrayList<Integer> arrayList){
        if(arrayList.size()!=progressBars.size()||arrayList.size()!=textViews.size())
            return;
        view.post(()->{
            for(int i=0;i<arrayList.size();i++){
                ProgressBar progressBar = progressBars.get(i);
                ValueAnimator valueAnimator = ValueAnimator.ofInt(progressBar.getProgress(), arrayList.get(i));
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setDuration(period);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int value = (int)valueAnimator.getAnimatedValue();
                        progressBar.setProgress(value);
                    }
                });
                valueAnimator.start();
                textViews.get(i).setText(String.format("%s%%", arrayList.get(i)));
            }
        });

    }

    public class RefreshTask extends TimerTask {

        @Override
        public void run() {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            double res = 1.0;
            for(int i=0;i<4;i++){
                double factor = Math.random();
                arrayList.add((int) (res*factor*100));
                res = res*(1-factor);
            }
            arrayList.add((int)(res*100));
            refreshData(arrayList);
        }
    }
}