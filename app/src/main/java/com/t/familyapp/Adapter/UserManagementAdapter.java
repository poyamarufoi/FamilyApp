package com.t.familyapp.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.t.familyapp.R;
import com.t.familyapp.Model.beans.UserData;

import java.util.List;

public class UserManagementAdapter extends CommonAdapter {

    ImageView editIcon;

    public UserManagementAdapter(Context context, int resource, List<UserData> items) {
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
        editIcon = view.findViewById(R.id.image_view_edit_icon);
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, R.id.image_view_edit_icon);
            }
        });

        UserData userData = getItem(position);
        if (userData != null) {
            TextView userName = view.findViewById(R.id.user_name);
            userName.setText(userData.getName());
        }
        return view;
    }
}
