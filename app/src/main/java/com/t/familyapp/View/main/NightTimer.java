package com.t.familyapp.View.main;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t.familyapp.Adapter.NightTimerAdapter;
import com.t.familyapp.Model.ReadCallback;
import com.t.familyapp.Model.ReadUserDataAsyncTask;
import com.t.familyapp.R;
import com.t.familyapp.Model.beans.UserData;

import java.util.ArrayList;
import java.util.List;

public class NightTimer extends Fragment implements ReadCallback {

    ListView mListView;
    List<UserData> mItems = new ArrayList<>();
    NightTimerAdapter mNightTimerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        return inflater.inflate(R.layout.fragment_night,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = getView().findViewById(R.id.list_view_night_timer);
        mNightTimerAdapter = new NightTimerAdapter(getContext(),R.layout.fragment_list_night_timer,mItems);
        ReadUserDataAsyncTask readUserDataAsyncTask = new ReadUserDataAsyncTask(mNightTimerAdapter, this);
        readUserDataAsyncTask.execute();
        mListView.setAdapter(mNightTimerAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final UserData userData = (UserData) parent.getItemAtPosition(position);
                final String firebaseKey = userData.getFirebaseKey();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String uid = user.getUid();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference reference = database.getReference("users").child(uid).child("User");

                switch (view.getId()) {
                    case R.id.button_come_timer:
                        final Button button = parent.findViewById(R.id.button_come_timer);
                        TimePickerDialog dialog1 = new TimePickerDialog(
                                getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        String nightTimer = String.format("%02d:%02d",hourOfDay,minute);
                                        button.setText(nightTimer);
                                        userData.setNightTime(nightTimer);
                                        reference.child(firebaseKey).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void v) {
                                            }
                                        });
                                    }
                                },0,0,true);
                        dialog1.show();
                        break;

                    case R.id.checkbox_dinner:
                        CheckBox checkBoxDinner = parent.findViewById(R.id.checkbox_dinner);
                        checkBoxDinner.setChecked(!userData.getDinnerCheck());
                        userData.setDinnerCheck(checkBoxDinner.isChecked());
                        reference.child(firebaseKey).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                            }
                        });
                        break;
                    case R.id.text_come_home:
                        Button buttonComeTime = parent.getChildAt(position).findViewById(R.id.button_come_timer);
                        buttonComeTime.setText(R.string.already_come);
                        buttonComeTime.setBackgroundColor(Color.RED);
                        userData.setNightTime(getResources().getString(R.string.already_come));
                        break;
                    default:
                        //追加実装
                        break;
                }
            }
        });
    }

    @Override
    public void onReadFinished() {
        mListView.setAdapter(mNightTimerAdapter);
    }
}
