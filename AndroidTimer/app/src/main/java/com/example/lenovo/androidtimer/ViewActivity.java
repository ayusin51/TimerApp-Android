package com.example.lenovo.androidtimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //Toast.makeText(getApplicationContext(), "Timers Page", Toast.LENGTH_SHORT).show();

        ListView timerList = findViewById(R.id.timerList);
        DBConnectivity db = new DBConnectivity(getApplicationContext());
        ArrayList<String> list = db.getTimers();

        if(list.size() == 0) {
            Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);

        timerList.setAdapter(adapter);
    }
}
