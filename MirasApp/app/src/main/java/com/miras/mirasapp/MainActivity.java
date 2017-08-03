package com.miras.mirasapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int questionNumber = 0;
    private int wordNumber = 0;
    private int pressed = 0;
    private int correct = 0;

    private Button confirm, next;
    private TextView tv;
    private EditText et;
    private ImageView iv;
    private TextView result;

    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<Quiz> quizs;
    private boolean mistake = false;

    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        confirm = (Button) findViewById(R.id.button_confirm);
        next = (Button) findViewById(R.id.button_next);
        et = (EditText) findViewById(R.id.et);
        iv = (ImageView) findViewById(R.id.iv);
        result = (TextView) findViewById(R.id.result);

        try {
            dbHelper = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File database = getApplicationContext().getDatabasePath(DataBaseHelper.DBNAME);
        if (database.exists() == false) {
            dbHelper.getReadableDatabase();

            if (copydatabase(this)) {
                Toast.makeText(this, "copied", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "not copied", Toast.LENGTH_SHORT).show();

        }
        Log.e("here", "here");
        quizs = (ArrayList<Quiz>) dbHelper.getQuiz();
        tv.setText(quizs.get(0).getQuestion());

        String s = "";
        for (int i = 0; i < quizs.get(0).getWords().length(); i++) {
            if (quizs.get(0).getWords().charAt(i) == ',') {
                words.add(s);
                s = "";
            }
            else
                s += quizs.get(0).getWords().charAt(i);
        }
        words.add(s);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "enter answer", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (wordNumber != words.size()) {
                        Log.e("check", "check");
                        if (et.getText().toString().toLowerCase().matches(words.get(wordNumber)))
                            mistake = true;
                        wordNumber++;
                        if (wordNumber == words.size()) {
                            next.setVisibility(View.VISIBLE);
                            confirm.setVisibility(View.INVISIBLE);
                        }
                    }
                    String q = "";
                    int add = 0;
                    for (int i = 0; i < quizs.get(questionNumber).getQuestion().length(); i++) {
                        if (quizs.get(questionNumber).getQuestion().charAt(i) != '_')
                            q += quizs.get(questionNumber).getQuestion().charAt(i);
                        else {
                            if (add == 0) {
                                q += et.getText();
                                i += 2;
                                add = 1;
                            }
                            else
                                q += '_';
                        }
                    }
                    quizs.get(questionNumber).setQuestion(q);
                    tv.setText(q);
                    et.setText("");
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pressed == 0) {
                    iv.setVisibility(View.VISIBLE);
                    if (mistake == true) {
                        iv.setImageDrawable(getResources().getDrawable(R.drawable.true_answer));
                        correct++;
                    }
                    else
                        iv.setImageDrawable(getResources().getDrawable(R.drawable.false_answer));
                    pressed++;
                    next.setText("Next Question");
                } else {
                    questionNumber++;
                    if (questionNumber == quizs.size()) {
                        next.setVisibility(View.INVISIBLE);
                        confirm.setVisibility(View.INVISIBLE);
                        result.setText("Your result is " + correct + " of " + quizs.size());
                        return;
                    }
                    pressed = 0;
                    wordNumber = 0;
                    mistake = false;
                    tv.setText(quizs.get(questionNumber).getQuestion());
                    words.clear();
                    String s = "";
                    String q = quizs.get(questionNumber).getWords();
                    for (int i = 0; i < q.length(); i++) {
                        if (q.charAt(i) == ',') {
                            words.add(s);
                            s = "";
                        }
                        else
                            s += q.charAt(i);
                    }
                    words.add(s);
                    iv.setVisibility(View.INVISIBLE);

                    next.setText("Check answer");
                    next.setVisibility(View.INVISIBLE);
                    confirm.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private boolean copydatabase(Context context){
        //Open your local db as the input stream
        try {
            InputStream myinput = context.getAssets().open(DataBaseHelper.DBNAME);

            // Path to the just created empty db
            String outfilename = DataBaseHelper.DBLOCATION + DataBaseHelper.DBNAME;

            //Open the empty db as the output stream
            OutputStream myoutput = new FileOutputStream(outfilename);

            // transfer byte to inputfile to outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myinput.read(buffer)) > 0) {
                myoutput.write(buffer, 0, length);
            }

            //Close the streams
            myoutput.flush();
            myoutput.close();
            myinput.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
