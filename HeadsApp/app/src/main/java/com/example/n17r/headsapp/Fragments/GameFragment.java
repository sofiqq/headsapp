package com.example.n17r.headsapp.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n17r.headsapp.MainActivity;
import com.example.n17r.headsapp.R;
import com.example.n17r.headsapp.models.AllWords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sofiq on 6/30/17.
 */

public class GameFragment extends Fragment implements SensorEventListener {

    private final MediaRecorder recorder = new MediaRecorder();

    private boolean gameStart;

    private int id;
    private int cameraStop = 0;
    private SurfaceView sv;
    private int level;
    private int start = 0;
    private int startTimer = 0;
    private int answer = 0;
    private int gameFinished = 0;

    private ArrayList<AllWords> words;
    private ArrayList<Integer> questions = new ArrayList<>();
    private ArrayList<String> ansStr;
    private ArrayList<Integer> ansBoolean;

    private TextView gameDo;
    private TextView gameTimer;

    private SensorManager mSensorManager;
    private Sensor mOrientation;

    private LinearLayout linearLayout;

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;

    private int secondSound, trueSound, falseSound, startSound;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        gameDo = (TextView) rootView.findViewById(R.id.game_do);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        gameTimer = (TextView) rootView.findViewById(R.id.game_timer);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.ll);

        gameStart = false;
        id = 0;
                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }
        mAssetManager = getActivity().getAssets();
        secondSound = loadSound("second.mp3");
        trueSound = loadSound("correct.mp3");
        falseSound = loadSound("false.wav");
        startSound = loadSound("start.wav");
        ansStr = new ArrayList<>();
        ansBoolean = new ArrayList<>();

        Bundle bundle = getArguments();
        level = bundle.getInt("level");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        Log.v("level", level + "");
        if (level == 1) {
            words = MainActivity.getBase(2);
        }
        if (level == 2)
            words = MainActivity.getBase(1);
        for (int i = 0; i < words.size(); i++) {
            questions.add(i);
        }
        Collections.shuffle(questions);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[2] > 70) {
            linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
            if (startTimer == 0) {
                startTimer = 1;
                CountDownTimer timer = new CountDownTimer(3 * 1000, 900) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        playSound(secondSound);
                        gameDo.setText((millisUntilFinished + 200) / 1000 + "");
                    }

                    @Override
                    public void onFinish() {
                        gameStart = true;
                        gameTimer.setVisibility(View.VISIBLE);
                        if (id == words.size()) {
                            gameFinished++;
                            gameDo.setText("Вы угадали все слова");
                        } else {
                            gameDo.setText(words.get(questions.get(id)).getName());
                            id++;
                        }
                        //VIDEO RECORDER
                        Log.e("HERE", "SOUND Go");
                        playSound(startSound);
                        CountDownTimer timerGame = new CountDownTimer(10 * 1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                gameTimer.setText("Осталось " + millisUntilFinished / 1000 + " сек.");

                            }

                            @Override
                            public void onFinish() {
                                if (start == 1 && gameFinished == 0) {
                                    ansStr.add(words.get(questions.get(id - 1)).getName());
                                    Log.e("hello", "hello");
                                    ansBoolean.add(0);
                                }
                                FragmentManager fragmentManager = getFragmentManager();
                                if (fragmentManager != null) {
                                    ResultFragment fragment = new ResultFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putStringArrayList("keyString", ansStr);
                                    bundle.putIntegerArrayList("keyInt", ansBoolean);
                                    fragment.setArguments(bundle);
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, fragment)
                                            .commit();
                                }
                            }
                        }.start();
                    }
                }.start();
            } else {
                if (answer == 1) {
                    answer = 0;
                    if (id == words.size()) {
                        gameFinished++;
                        gameDo.setText("Вы угадали все слова");
                    } else {
                        gameDo.setText(words.get(questions.get(id)).getName());
                        id++;
                    }
                }
            }
            start = 1;
        }
        //Log.d("ANGLE", event.values[0] + " " + event.values[1] + " " + event.values[2]);
        if (start == 1 && gameStart == true) {
            if (Math.abs(event.values[2]) < 50 && Math.abs(event.values[1]) > 140) {
                String ansStringTrue = gameDo.getText().toString();
                if (ansStringTrue.equals("Вы угадали все слова")) {

                } else {
                    ansStr.add(ansStringTrue);
                    ansBoolean.add(1);
                    playSound(trueSound);
                    gameDo.setText("Правильно");
                    linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_true));
                    start = 0;
                    answer = 1;
                }
            } else if (Math.abs(event.values[2]) < 55 && Math.abs(event.values[1]) < 20) {
                String ansStringFalse = gameDo.getText().toString();
                if (ansStringFalse.equals("Вы угадали все слова")) {

                } else {
                    ansStr.add(ansStringFalse);
                    ansBoolean.add(0);
                    playSound(falseSound);
                    linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_false));
                    gameDo.setText("Пасс");
                    start = 0;
                    answer = 1;
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }
    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }
    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }
    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean isCheckPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                return false;
            }

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA},
                    1);
            return false;
            // MY_PERMISSIONS_REQUEST is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        return true;
    }

    public boolean isAudioCheckPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.RECORD_AUDIO)) {
                return false;
            }

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
            return false;
            // MY_PERMISSIONS_REQUEST is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        return true;
    }

    public boolean isStorageCheckPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                return false;
            }

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
            return false;
            // MY_PERMISSIONS_REQUEST is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        return true;
    }

    public void stopCamera() {
        try {
            recorder.prepare();
            recorder.stop();
            recorder.reset();

            recorder.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
