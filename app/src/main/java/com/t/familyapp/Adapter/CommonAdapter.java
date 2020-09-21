package com.t.familyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import com.t.familyapp.Model.beans.UserData;

import java.util.List;

/**
 * メイン画面の各アダプターの共通処理を持つクラス
 *
 */
public class CommonAdapter extends ArrayAdapter<UserData> {

    protected List<UserData> mUserDataList;
    protected int mResource;
    protected LayoutInflater mInflater;

    public CommonAdapter(Context context, int resource, List<UserData> items) {
        super(context, resource, items);
        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mUserDataList = items;
    }

    public UserData getUserDataKey(String key) {
        for (UserData UserData : mUserDataList) {
            if (UserData.getFirebaseKey().equals(key)) {
                return UserData;
            }
        }
        return null;
    }
}
