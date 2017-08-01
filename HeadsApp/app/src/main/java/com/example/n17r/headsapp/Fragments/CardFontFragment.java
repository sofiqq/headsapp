package com.example.n17r.headsapp.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.n17r.headsapp.R;

/**
 * Created by sofiq on 7/14/17.
 */

public class CardFontFragment extends Fragment {
    private TextView title;

    private LinearLayout linearLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_card_front, container, false);

        title = (TextView) view.findViewById(R.id.title);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_front);

        Bundle bundle = getArguments();
        int level = bundle.getInt("level", 0);
        if (level == 1) {
            title.setText("Звезды");
            linearLayout.setBackgroundDrawable( getResources().getDrawable(R.drawable.pevec) );
        }
        if (level == 2) {
            linearLayout.setBackgroundDrawable( getResources().getDrawable(R.drawable.redbook) );
            title.setText("Животные");
        }


        return view;
    }
}
