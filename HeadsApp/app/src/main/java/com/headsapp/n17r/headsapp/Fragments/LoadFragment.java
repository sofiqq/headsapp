package com.headsapp.n17r.headsapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.headsapp.n17r.headsapp.R;

/**
 * Created by sofiq on 8/11/17.
 */

public class LoadFragment extends android.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_load, container, false);



        return view;

    }
}
