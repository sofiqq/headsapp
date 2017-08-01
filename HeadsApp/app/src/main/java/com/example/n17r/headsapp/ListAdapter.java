package com.example.n17r.headsapp;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> stringValues;
    private ArrayList<Integer> intValues;

    public ListAdapter(Context context, ArrayList<String> stringValues, ArrayList<Integer> intValues)
    {
        super(context, R.layout.list_item, stringValues);
        this.context = context;
        this.intValues = intValues;
        this.stringValues = stringValues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.colors);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.llColors);
        textView.setText(stringValues.get(position));
        int i = intValues.get(position);
        if (i == 0)
            textView.setTextColor(Color.rgb(200,0,0));
        else if (i == 1)
            textView.setTextColor(Color.rgb(0,200,0));
        return view;
    }
}
