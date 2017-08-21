package com.headsapp.n17r.headsapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.headsapp.n17r.headsapp.Fragments.LevelFragment;
import com.headsapp.n17r.headsapp.Fragments.LoadFragment;
import com.headsapp.n17r.headsapp.models.AllWords;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> level = new ArrayList<String>();
    private static ArrayList<AllWords> allWords = new ArrayList<AllWords>();
    private int laod, time = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        int load = intent.getIntExtra("load", 0);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (load == 0)
            database.setPersistenceEnabled(true);
        DatabaseReference reference = database.getReference("Data");
        reference.keepSynced(true);
        Log.e("start", "start");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dss : ds.getChildren()) {

                        level.add(ds.getKey());
                        AllWords word = dss.getValue(AllWords.class);
                        allWords.add(word);
                        Log.e("getKey", ds.getKey() + " " + dss.getValue());
                    }

                }
                Log.e("hello", "hello");
                LevelFragment fragment = new LevelFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                //LoadImage();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (load == 0) {
            LoadFragment loadFragment = new LoadFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.container, loadFragment)
                    .commit();

        } else {
            LevelFragment fragment = new LevelFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }


    }

    public static ArrayList<AllWords> getBase(String lvl) {
        ArrayList<String> levels = new ArrayList<>();
        ArrayList<AllWords> base = new ArrayList<>();
        levels.add(level.get(0));
        for (int i = 1; i < level.size(); i++)
            if (level.get(i) != level.get(i - 1))
                levels.add(level.get(i));
        for (int i = 0; i < allWords.size(); i++)
            if (lvl.equals(level.get(i)))
                base.add(allWords.get(i));
        return base;
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent in = new Intent(this, MainActivity.class);
        in.putExtra("load", 1);
        startActivity(in);
    }
}
