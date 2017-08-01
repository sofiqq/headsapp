package com.example.n17r.headsapp.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;

import android.app.FragmentManager;
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

public class CardBackFragment extends Fragment {

    private TextView title, description;
    private CardView play;
    private LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_back, container, false);

        linearLayout = (LinearLayout) view.findViewById(R.id.ll_back);
        title = (TextView) view.findViewById(R.id.level_title);
        description = (TextView) view.findViewById(R.id.level_description);
        play = (CardView) view.findViewById(R.id.play);

        Bundle bundle = getArguments();
        int level = bundle.getInt("level_chosen", 0);
        if (level == 1) {
            title.setText("Звезды");
            description.setText("Звезды, известные личности Казахстана");
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("level", 1);
                    FragmentManager fragmentManager = getFragmentManager();
                    GameFragment fragment = new GameFragment();
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                }
            });
        }
        if (level == 2) {
            title.setText("Животные");
            description.setText("Животные Красной книги Казахстана");
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("level", 2);
                    FragmentManager fragmentManager = getFragmentManager();
                    GameFragment fragment = new GameFragment();
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                }
            });
        }

        return view;
    }
}
