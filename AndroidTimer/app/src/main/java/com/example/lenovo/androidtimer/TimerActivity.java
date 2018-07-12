package com.example.lenovo.androidtimer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.lenovo.androidtimer.R.raw.*;
import static com.example.lenovo.androidtimer.R.raw.beep;

public class TimerActivity extends AppCompatActivity {

    TextView time_text_view;
    int time;
    SeekBar seekBar;
    DBConnectivity db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        seekBar = findViewById(R.id.timer_seekbar);
        Button start = findViewById(R.id.start_stop_button);

        db = new DBConnectivity(getApplicationContext());

        // Changes time view whenever seekbar time changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                time_text_view = findViewById(R.id.time_text_view);
               // Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
                time_text_view.setText(Integer.toString(progress) + " secs");
                time = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });

        // Starts times on button click
        start.setOnClickListener(new View.OnClickListener() {

            final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.alarm);

            @Override

            public void onClick(View view) {
                time_text_view = findViewById(R.id.time_text_view);
                EditText timerName = findViewById(R.id.timerName);

                db.AddTimer(time_text_view.getText().toString(), timerName.getText().toString());

                new CountDownTimer(time*1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                        seekBar.setProgress(time);
                        time_text_view.setText(Integer.toString(--time) + " secs");
                    }

                    public void onFinish() {

                        time_text_view.setText("0 secs");
                        seekBar.setProgress(0);
                        mp.start();
                    }
                }.start();
            }
        });
    }
}
