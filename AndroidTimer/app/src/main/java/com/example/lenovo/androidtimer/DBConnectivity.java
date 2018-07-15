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

    public DBConnectivity(Context context) {
        this.context = context;
        db = context.openOrCreateDatabase("timerDB",MODE_PRIVATE,null);
        db.execSQL("create table if not exists Timers (timer_date VARCHAR, timer_name VARCHAR, timer_duration number)");
    }

    void AddTimer(int duration, String name){

        Log.d("timer", "func called");

        if(name.isEmpty()) {
            Integer count = 0;
            try {
                Cursor c = db.rawQuery("SELECT count() as MAX FROM Timers", null);
                int index;

                if (c.moveToFirst()) {
                    index = c.getColumnIndex("MAX");
                    count = c.getInt(index) + 1;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            //String cnt = Integer.toString(count);
            name = "Timer " + Integer.toString(count);
            Log.d("name", "empty");
        }
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            Log.d("Time Now", dateFormat.format(date));
            String sysdate = dateFormat.format(date);

            db.execSQL("insert into Timers values('"+sysdate+"', '" + name + "', "+ duration +")");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    ArrayList<Timers> getTimers() {

        Log.d("gettimer", "func called");

        ArrayList<Timers> timers = new ArrayList<>();
        try{
            Cursor c = db.rawQuery("SELECT MAX(timer_duration) as MAX FROM Timers", null);
            int index;
            int max_dur = 0;
            if(c.moveToFirst()) {
                index = c.getColumnIndex("MAX");
                max_dur = c.getInt(index);
            }
            c = db.rawQuery("SELECT * FROM Timers",null);
            if(c.moveToFirst()) {
                do {
                    index = c.getColumnIndex("timer_date");
                    String date = c.getString(index);

                    index = c.getColumnIndex("timer_name");
                    String name = c.getString(index);

                    index = c.getColumnIndex("timer_duration");
                    Integer duration = c.getInt(index);

                    if(duration >= max_dur) {
                        name = name + " (HIGHEST)";
                    }

                    Log.d("gettimer", "func middle");

                    Timers timer = new Timers(name, date, duration.toString());

                    timers.add(timer);
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