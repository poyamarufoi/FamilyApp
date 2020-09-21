package com.t.familyapp.Adapter;

import android.content.Context;
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

public class MorningTimerAdapter extends CommonAdapter {

    public MorningTimerAdapter(Context context, int resource, List<UserData> items) {
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
        TextView userNameMorning = view.findViewById(R.id.user_name_morning);
        Button buttonTimerStart = view.findViewById(R.id.button_morning_timer_start);
        Button buttonTimerEnd = view.findViewById(R.id.button_morning_timer_end);
        CheckBox checkBoxBreakfast = view.findViewById(R.id.checkbox_breakfast);
        buttonTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, R.id.button_morning_timer_start);
            }
        });
        buttonTimerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, R.id.button_morning_timer_end);
            }
        });
        checkBoxBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, R.id.checkbox_breakfast);
            }
        });

        UserData userData = getItem(position);
        if (userData != null) {
            userNameMorning.setText(userData.getName());
            if (userData.getMorningTimeStart() != null) {
                String morningTimeStart = userData.getMorningTimeStart();
                buttonTimerStart.setText(morningTimeStart);
            }
            if (userData.getMorningTimeEnd() != null) {
                String morningTimeEnd = userData.getMorningTimeEnd();
                buttonTimerEnd.setText(morningTimeEnd);
            }
            checkBoxBreakfast.setChecked(userData.getBreakfastCheck());
        }
        return view;
    }
}
