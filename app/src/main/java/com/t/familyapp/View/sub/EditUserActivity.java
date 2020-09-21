package com.t.familyapp.View.sub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t.familyapp.Model.Constants;
import com.t.familyapp.R;
import com.t.familyapp.View.main.ListActivity;
import com.t.familyapp.Model.beans.UserData;

/**
 * 編集画面の出力・処理を担当するクラス
 *
 */
public class EditUserActivity extends AppCompatActivity {

    EditText userName;

    public FirebaseUser user;
    public String uid;
    public FirebaseDatabase database;
    public DatabaseReference reference;
    public String firebaseKey;
    public UserData mUserData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Intent intent = getIntent();
        mUserData = (UserData) intent.getSerializableExtra(Constants.USER_DATA);

        userName = (EditText) findViewById(R.id.title);
        userName.setText(mUserData.getName());

        firebaseKey = mUserData.getFirebaseKey();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(uid).child("User");
    }

    public void update(View view) {
        mUserData.setName(userName.getText().toString());
        reference.child(firebaseKey).setValue(mUserData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void delete(View view) {
        new AlertDialog.Builder(view.getContext())
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_message)
                .setPositiveButton(R.string.delete_dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        reference.child(firebaseKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.delete_dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}