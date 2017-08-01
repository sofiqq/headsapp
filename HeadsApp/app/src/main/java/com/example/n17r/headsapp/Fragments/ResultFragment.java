package com.example.n17r.headsapp.Fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.n17r.headsapp.ListAdapter;
import com.example.n17r.headsapp.R;

import java.util.ArrayList;

/**
 * Created by sofiq on 7/5/17.
 */

public class ResultFragment extends Fragment {
    private int correct = 0, all = 0;
    private TextView result;
    private CardView back;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_result, container, false);

        result = (TextView) rootView.findViewById(R.id.tv_result);
        back = (CardView) rootView.findViewById(R.id.cv_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LevelFragment fragment = new LevelFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }
        });
        Bundle bundle = getArguments();
        ArrayList<String> answerName = bundle.getStringArrayList("keyString");
        ArrayList<Integer> answerRight = bundle.getIntegerArrayList("keyInt");

        String ansName[] = new String[answerName.size()];
        int ansInt[] = new int[answerRight.size()];

        for (int i = 0; i < answerName.size(); i++) {
            all++;
            ansName[i] = answerName.get(i);
            ansInt[i] = answerRight.get(i);
            if (ansInt[i] == 1)
                correct++;
        }
        String slov = "";
        if (correct > 10 && correct < 20) {
            slov = " слов";
        }
        else {
            int q = correct % 10;
            if (q > 4 && q < 10)
                slov = " слов";
            if (q == 1)
                slov = " слово";
            if (q > 1 && q < 5) {
                slov = " слова";
            }
        }

        result.setText("Вы угадали " + correct + slov + " из " + all);

        ListView listView = (ListView) rootView.findViewById(R.id.lv);


        ListAdapter adapter = new ListAdapter(getActivity(), answerName, answerRight);
        listView.setAdapter(adapter);
        return rootView;
    }

}
