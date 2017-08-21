package com.headsapp.n17r.headsapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.headsapp.n17r.headsapp.R;

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
        String level = bundle.getString("level");
        if (level == "Stars") {
            title.setText("Звезды");
            linearLayout.setBackgroundDrawable( getResources().getDrawable(R.drawable.pevec) );
        }
        if (level == "RedBook") {
            linearLayout.setBackgroundDrawable( getResources().getDrawable(R.drawable.redbook) );
            title.setText("Животные");
        }
        if (level == "Children") {
            title.setText("Для детей");
            linearLayout.setBackgroundDrawable( getResources().getDrawable(R.drawable.children) );
        }
        if (level == "Act") {
            title.setText("Действия");
            linearLayout.setBackgroundDrawable( getResources().getDrawable(R.drawable.pantomima) );
        }


        return view;
    }
}
