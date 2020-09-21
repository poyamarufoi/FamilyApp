package com.t.familyapp.View.sub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t.familyapp.R;
import com.t.familyapp.View.main.ListActivity;
import com.t.familyapp.Model.beans.UserData;

/**
 * 追加画面の出力・処理を担当するクラス
 *
 */
public class AddUserActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    EditText titleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        titleEditText = (EditText) findViewById(R.id.title);
    }

    public void save(View v) {
        String name = titleEditText.getText().toString();
        String key = reference.push().getKey();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        UserData userData = new UserData(key, name);
        userData.setMorningTimeStart(getResources().getString(R.string.timer_default));
        userData.setMorningTimeEnd(getResources().getString(R.string.timer_default));
        userData.setNightTime(getResources().getString(R.string.timer_default));

        reference.child("users").child(uid).child("User").child(key).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
    }
}