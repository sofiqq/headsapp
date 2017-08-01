package com.example.n17r.headsapp;

import android.Manifest;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorManager;

import android.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.n17r.headsapp.Fragments.LevelFragment;
import com.example.n17r.headsapp.models.AllWords;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String DBCHECK = "DBCHECK";

    public static ArrayList<String> level = new ArrayList<>();
    private static ArrayList<AllWords> allWords = new ArrayList<>();

    //private DataBaseHelper dbHelper;
    private SQLiteDatabase database;
    public SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int permissionAudioCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        int permissionVideoCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int permissionStorageCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Data");
        reference.keepSynced(true);
        Log.e("getKey", "kakdela");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("getKey", "kakdela3");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.e("getKey", "kakdela2");
                    for (DataSnapshot dss : ds.getChildren()) {
                        level.add(ds.getKey());
                        Log.e("getKey", ds.getKey());
                        AllWords word = dss.getValue(AllWords.class);
                        allWords.add(word);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LevelFragment fragment = new LevelFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();


    }

    @Override
    public void onBackPressed() {
        LevelFragment fragment = new LevelFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
//    asdasdasdas
//    dasas
    public static ArrayList<AllWords> getBase(int intLevel) {
        intLevel--;
        ArrayList<String> levels = new ArrayList<>();
        ArrayList<AllWords> base = new ArrayList<>();
        Log.e("tag", intLevel + "");
        levels.add(level.get(0));
        for (int i = 1; i < level.size(); i++)
            if (level.get(i) != level.get(i - 1))
                levels.add(level.get(i));
        for (int i = 0; i < allWords.size(); i++)
            if (levels.get(intLevel).equals(level.get(i)))
                base.add(allWords.get(i));
        return base;
    }
}
