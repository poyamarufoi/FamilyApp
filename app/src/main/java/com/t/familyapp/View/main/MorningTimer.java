package com.t.familyapp.View.main;

import android.app.TimePickerDialog;
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
import com.t.familyapp.Adapter.MorningTimerAdapter;
import com.t.familyapp.Model.ReadCallback;
import com.t.familyapp.Model.ReadUserDataAsyncTask;
import com.t.familyapp.R;
import com.t.familyapp.Model.beans.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * 「朝の時間」画面の出力・処理を担当するクラス
 */
public class MorningTimer extends Fragment implements ReadCallback {

    ListView mListView;
    List<UserData> mItems = new ArrayList<>();

    MorningTimerAdapter mMorningTimerAdapter;
    ReadUserDataAsyncTask mReadUserDataAsyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        return inflater.inflate(R.layout.fragment_morning, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = requireView().findViewById(R.id.list_view_morning_timer);
        mMorningTimerAdapter = new MorningTimerAdapter(getContext(), R.layout.fragment_list_morning_timer, mItems);
        mReadUserDataAsyncTask = new ReadUserDataAsyncTask(mMorningTimerAdapter, this);
        mReadUserDataAsyncTask.execute();

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
                    case R.id.button_morning_timer_start:
                        final Button button1 = parent.findViewById(R.id.button_morning_timer_start);
                        TimePickerDialog dialog1 = new TimePickerDialog(
                                getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        String morningTimeStart = String.format("%02d:%02d", hourOfDay, minute);
                                        button1.setText(morningTimeStart);
                                        userData.setMorningTimeStart(morningTimeStart);
                                        reference.child(firebaseKey).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void v) {

                                            }
                                        });
                                    }
                                }, 0, 0, true);
                        dialog1.show();
                        break;
                    case R.id.button_morning_timer_end:
                        final Button button2 = parent.findViewById(R.id.button_morning_timer_end);
                        TimePickerDialog dialog2 = new TimePickerDialog(
                                getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        String morningTimeEnd = String.format("%02d:%02d", hourOfDay, minute);
                                        button2.setText(morningTimeEnd);
                                        userData.setMorningTimeEnd(morningTimeEnd);
                                        reference.child(firebaseKey).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void v) {
                                            }
                                        });
                                    }
                                }, 0, 0, true);
                        dialog2.show();
                        break;
                    case R.id.checkbox_breakfast:
                        CheckBox checkBoxBreakfast = view.findViewById(R.id.checkbox_breakfast);
                        userData.setBreakfastCheck(checkBoxBreakfast.isChecked());
                        reference.child(firebaseKey).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    public void onReadFinished() {
        mListView.setAdapter(mMorningTimerAdapter);
    }
}