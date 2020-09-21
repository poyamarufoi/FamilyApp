package com.t.familyapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.t.familyapp.R;
import com.t.familyapp.Model.beans.UserData;

import java.util.List;

public class NightTimerAdapter extends CommonAdapter {

    public NightTimerAdapter(Context context, int resource, List<UserData> items) {
        super(context, resource, items);
    }

    @Override
    public @NonNull
    View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(mResource, null);
        }
        TextView userNameNight = view.findViewById(R.id.user_name_night);
        Button buttonNightTimer = view.findViewById(R.id.button_come_timer);
        CheckBox checkBoxDinner = view.findViewById(R.id.checkbox_dinner);
        TextView comeHome = view.findViewById(R.id.text_come_home);
        buttonNightTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, R.id.button_come_timer);
            }
        });
        checkBoxDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, R.id.checkbox_dinner);
            }
        });
        comeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, R.id.text_come_home);
            }
        });

        UserData userData = getItem(position);
        if (userData != null) {
            userNameNight.setText(userData.getName());
            if (userData.getNightTime() != null) {
                String nightTime = userData.getNightTime();
                buttonNightTimer.setText(nightTime);
                if(nightTime.equals(R.string.already_come)){
                    buttonNightTimer.setBackgroundColor(Color.RED);
                }
                else{
                    buttonNightTimer.setBackgroundColor(Color.WHITE);
                }
            }
            checkBoxDinner.setChecked(userData.getDinnerCheck());
        }
        return view;
    }
}
