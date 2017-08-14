package com.example.n17r.headsapp;

import android.Manifest;
import android.app.FragmentManager;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.example.n17r.headsapp.Fragments.LevelFragment;
import com.example.n17r.headsapp.Fragments.LoadFragment;
import com.example.n17r.headsapp.models.AllWords;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> level = new ArrayList<>();
    private static ArrayList<AllWords> allWords = new ArrayList<>();

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
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dss : ds.getChildren()) {
                        level.add(ds.getKey());
                        AllWords word = dss.getValue(AllWords.class);
                        allWords.add(word);
                    }

                }
                //LoadImage();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        LoadFragment loadFragment = new LoadFragment();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.container, loadFragment)
                .commit();

        CountDownTimer timer = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                LevelFragment fragment = new LevelFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }
        }.start();


    }

    public static ArrayList<AllWords> getBase(int intLevel) {
        intLevel--;
        ArrayList<String> levels = new ArrayList<>();
        ArrayList<AllWords> base = new ArrayList<>();
        levels.add(level.get(0));
        for (int i = 1; i < level.size(); i++)
            if (level.get(i) != level.get(i - 1))
                levels.add(level.get(i));
        for (int i = 0; i < allWords.size(); i++)
            if (levels.get(intLevel).equals(level.get(i)))
                base.add(allWords.get(i));
        return base;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
