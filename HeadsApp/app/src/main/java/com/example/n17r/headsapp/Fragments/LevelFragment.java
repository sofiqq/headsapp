package com.example.n17r.headsapp.Fragments;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.n17r.headsapp.MainActivity;
import com.example.n17r.headsapp.R;
import com.example.n17r.headsapp.RoolsActivity;
import com.example.n17r.headsapp.models.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofiq on 6/30/17.
 */

public class LevelFragment extends Fragment implements View.OnClickListener{
    public static final String LEVEL_CHOOSE_KEY = "LEVEL_CHOOSE_KEY";

    private boolean[] mShowingBack = new boolean[1000];
    private LinearLayout cvAnimals, cvStars, cvChildren, cvAct;
    private LinearLayout background;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_level, container, false);

        background = (LinearLayout) rootView.findViewById(R.id.ll_background);
        background.setBackgroundResource(R.drawable.gradient);
        for (int i = 0; i < 1000; i++) {
            mShowingBack[i] = false;
        }

        ImageView rools = (ImageView) rootView.findViewById(R.id.iv_rools);
        rools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoolsActivity.class);
                startActivity(intent);
            }
        });


        Bundle args1 = new Bundle();
        args1.putString("level", "Stars");
        CardFontFragment fragment1 = new CardFontFragment();
        fragment1.setArguments(args1);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_stars, fragment1)
                .commit();

        Bundle args2 = new Bundle();
        args2.putString("level", "RedBook");
        CardFontFragment fragment2 = new CardFontFragment();
        fragment2.setArguments(args2);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_animal, fragment2)
                .commit();

        Bundle args3 = new Bundle();
        args3.putString("level", "Children");
        CardFontFragment fragment3 = new CardFontFragment();
        fragment3.setArguments(args3);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_children, fragment3)
                .commit();

        Bundle args4 = new Bundle();
        args4.putString("level", "Act");
        CardFontFragment fragment4 = new CardFontFragment();
        fragment4.setArguments(args4);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_act, fragment4)
                .commit();


        cvAnimals = (LinearLayout) rootView.findViewById(R.id.menu_animal);
        cvStars = (LinearLayout) rootView.findViewById(R.id.menu_stars);
        cvChildren = (LinearLayout) rootView.findViewById(R.id.menu_children);
        cvAct = (LinearLayout) rootView.findViewById(R.id.menu_act);
        cvChildren.setOnClickListener(this);
        cvAnimals.setOnClickListener(this);
        cvStars.setOnClickListener(this);
        cvAct.setOnClickListener(this);

        return rootView;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_animal:
                flipCard("RedBook", 2, R.id.menu_animal);
                break;
            case R.id.menu_stars:
                flipCard("Stars", 1, R.id.menu_stars);
//                Bundle bundle = new Bundle();
//                bundle.putInt(LEVEL_CHOOSE_KEY, 1);
//                GameFragment fragment = new GameFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                fragment.setArguments(bundle);
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, fragment)
//                        .commit();
                break;
            case R.id.menu_children:
                flipCard("Children", 3, R.id.menu_children);
                break;
            case R.id.menu_act:
                flipCard("Act", 4, R.id.menu_act);
                break;

        }
    }

    private void flipCard(String lvl, int id, int container) {
        if (mShowingBack[id]) {
            getFragmentManager().popBackStack();
            mShowingBack[id] = false;
            return;
        }

        // Flip to the back.

        mShowingBack[id] = true;
        Bundle bundle = new Bundle();
        bundle.putString("level_chosen", lvl);
        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.
        FragmentManager fragmentManager = getFragmentManager();
        CardBackFragment fragment = new CardBackFragment();
        fragment.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                // Replace the default fragment animations with animator resources
                // representing rotations when switching to the back of the card, as
                // well as animator resources representing rotations when flipping
                // back to the front (e.g. when the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)

                // Replace any fragments currently in the container view with a
                // fragment representing the next page (indicated by the
                // just-incremented currentPage variable).
                .replace(container, fragment)

                // Add this transaction to the back stack, allowing users to press
                // Back to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();
    }
}
