package com.example.lenovo.androidtimer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TimerAdapter extends ArrayAdapter<Timers> {
    ArrayList<Timers> timers = new ArrayList<>();

    public TimerAdapter(@NonNull Context context, ArrayList<Timers> timers) {
        super(context, 0, timers);
        this.timers = timers;
    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.timer_view, null);
        }

        Timers timer = getItem(position);

        if (timer != null) {
            TextView name = convertView.findViewById(R.id.timer_name);
            TextView date = convertView.findViewById(R.id.timer_date);
            TextView duration = convertView.findViewById(R.id.timer_duration);

            name.setText(timer.getName());
            date.setText(timer.getDate());
            duration.setText(timer.getDuration().toString() + " secs");

        }
        return convertView;
    }

    @Nullable
    @Override
    public Timers getItem(int position) {
        return timers.get(position);
    }
}
