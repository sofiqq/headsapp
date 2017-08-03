package com.miras.mirasapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofiq on 8/2/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context mycontext;
    public static String DBNAME = "miras.db";
    public static String DBLOCATION = "/data/data/com.miras.mirasapp/databases/";
    public SQLiteDatabase myDataBase;

    public DataBaseHelper(Context context) throws IOException {
        super(context, DBNAME, null, 1);
        this.mycontext = context;

    }

    public void opendatabase() {
        //Open the database
        String dbPath = mycontext.getDatabasePath(DBNAME).getPath();
        if (myDataBase != null && myDataBase.isOpen()) {
            return;
        }
        myDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public void closedatabase() {
        if (myDataBase != null) {
            myDataBase.close();
        }
    }
    public List<Quiz> getQuiz() {
        Quiz quiz = null;
        ArrayList<Quiz> questions = new ArrayList<>();
        opendatabase();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM questions", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            quiz = new Quiz(cursor.getString(0), cursor.getString(1));
            cursor.moveToNext();
            questions.add(quiz);
        }
        cursor.close();
        closedatabase();
        return questions;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
