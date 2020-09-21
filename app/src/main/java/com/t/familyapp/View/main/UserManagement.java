package com.t.familyapp.View.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.t.familyapp.Adapter.UserManagementAdapter;
import com.t.familyapp.Constants;
import com.t.familyapp.Model.ReadCallback;
import com.t.familyapp.Model.ReadUserDataAsyncTask;
import com.t.familyapp.R;
import com.t.familyapp.View.sub.AddUserActivity;
import com.t.familyapp.View.sub.EditUserActivity;
import com.t.familyapp.Model.beans.UserData;

import java.util.ArrayList;
import java.util.List;

public class UserManagement extends Fragment implements ReadCallback {

    ListView mListView;
    List<UserData> mUserList = new ArrayList<>();

    public UserManagementAdapter mUserManagementAdapter;
    public ReadUserDataAsyncTask mReadUserDataAsyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) getView().findViewById(R.id.list_view_user_management);
        mUserManagementAdapter = new UserManagementAdapter(getContext(), R.layout.fragment_list_user_management, mUserList);
        mReadUserDataAsyncTask = new ReadUserDataAsyncTask(mUserManagementAdapter, this);
        mReadUserDataAsyncTask.execute();


        FloatingActionButton fab = getView().findViewById(R.id.fab_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddUserActivity.class);
                startActivity(intent);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EditUserActivity.class);
                UserData userData = (UserData) parent.getItemAtPosition(position);
                switch (view.getId()) {
                    case R.id.image_view_edit_icon:
                        //編集処理
                        intent.putExtra(Constants.ACTION, Constants.EDIT);
                        intent.putExtra(Constants.USER_DATA, userData);
                        startActivity(intent);
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
        mListView.setAdapter(mUserManagementAdapter);
    }
}