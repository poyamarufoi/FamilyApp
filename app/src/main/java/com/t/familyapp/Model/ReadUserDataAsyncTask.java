package com.t.familyapp.Model;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t.familyapp.Adapter.CommonAdapter;
import com.t.familyapp.Model.beans.UserData;

/**
 * Firebaseのユーザデータを読み込みAdapterに渡すクラス
 *
 */
public class ReadUserDataAsyncTask extends AsyncTask<Void, Void, Boolean> {

    ReadCallback mReadCallback;

    public FirebaseUser user;
    public String uid;

    public FirebaseDatabase database;
    public DatabaseReference reference;
    public CommonAdapter mBasicAdapter;

    public ReadUserDataAsyncTask(CommonAdapter basicAdapter, ReadCallback callback) {
        mBasicAdapter = basicAdapter;
        mReadCallback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(uid).child("User");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserData userData = dataSnapshot.getValue(UserData.class);
                mBasicAdapter.add(userData);
                mBasicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserData newUserData = dataSnapshot.getValue(UserData.class);
                for (int i = 0; i < mBasicAdapter.getCount(); i++) {
                    UserData userData = mBasicAdapter.getItem(i);
                    if (userData.getFirebaseKey().equals(newUserData.getFirebaseKey())) {
                        mBasicAdapter.remove(userData);
                    }
                }
                mBasicAdapter.add(newUserData);
                mBasicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                UserData result = dataSnapshot.getValue(UserData.class);
                if (result == null) return;
                UserData item = mBasicAdapter.getUserDataKey(result.getFirebaseKey());
                mBasicAdapter.remove(item);
                mBasicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return true;
    }

    @Override
    protected void onPostExecute(Boolean judge) {
        mReadCallback.onReadFinished();
    }
}
