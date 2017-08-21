package com.headsapp.n17r.headsapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.headsapp.n17r.headsapp.R;

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
        String level = bundle.getString("level_chosen");
        if (level == "Stars") {
            title.setText("Звезды");
            description.setText("Звезды, известные личности Казахстана");
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("level", "Stars");
                    FragmentManager fragmentManager = getFragmentManager();
                    GameFragment fragment = new GameFragment();
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                }
            });
        }
        if (level == "RedBook") {
            title.setText("Животные");
            description.setText("Животные Красной книги Казахстана");
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("level", "Animals");
                    FragmentManager fragmentManager = getFragmentManager();
                    GameFragment fragment = new GameFragment();
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                }
            });
        }
        if (level == "Children") {
            title.setText("Для детей");
            description.setText("Несложные и забавные слова");
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("level", "Children");
                    FragmentManager fragmentManager = getFragmentManager();
                    GameFragment fragment = new GameFragment();
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                }
            });
        }

        if (level == "Act") {
            title.setText("Действия");
            description.setText("Различные действия по примеру 'Одевать шапку'");
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("level", "Act");
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
