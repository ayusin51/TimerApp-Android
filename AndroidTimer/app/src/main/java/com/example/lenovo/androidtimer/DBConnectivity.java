package com.example.lenovo.androidtimer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class DBConnectivity {

    Context context;
    SQLiteDatabase db;
    static int count = 0;

    public DBConnectivity(Context context) {
        this.context = context;
        db = context.openOrCreateDatabase("timerDB",MODE_PRIVATE,null);
        db.execSQL("create table if not exists Timers (timer_date VARCHAR, timer_name VARCHAR, timer_duration VARCHAR)");
    }

    void AddTimer(String duration, String name){

        Log.d("timer", "func called");

        if(name.isEmpty()) {
            String cnt = Integer.toString(count);
            name = "Timer " + cnt;
            Log.d("name", "empty");
        }
        try {
            /*Cursor c = db.rawQuery("select date() as date", null);
            int index = c.getColumnIndex("date");
            String sysdate = c.getString(index);*/

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            Log.d("Time Now", dateFormat.format(date));
            String sysdate = dateFormat.format(date);

            db.execSQL("insert into Timers values('"+sysdate+"', '" + name + "', '"+ duration +"')");
            Log.i("Timer", "Success");
            Log.d("SYSDATE", sysdate);
            count++;

        }catch (Exception e){
            e.printStackTrace();
            Log.d("Error", "exception");
        }

    }

    ArrayList<String> getTimers() {

        Log.d("gettimer", "func called");

        ArrayList<String> timers = new ArrayList<>();
        try{
            Cursor c = db.rawQuery("SELECT * FROM Timers",null);
            if(c.moveToFirst()) {
                do {
                    int index = c.getColumnIndex("timer_date");
                    String date = c.getString(index);

                    index = c.getColumnIndex("timer_name");
                    String name = c.getString(index);

                    index = c.getColumnIndex("timer_duration");
                    String duration = c.getString(index);

                    Log.d("gettimer", "func middle");

                    timers.add(date + " " + name + " " + duration);
                } while (c.moveToNext());
               // Toast.makeText(context, "Timers got successfully", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e) {
            e.printStackTrace();
            //Toast.makeText(context, "Timers didn't get successfully", Toast.LENGTH_SHORT).show();
        }

        Log.d("gettimer", "func end");

        return  timers;
    }
}