package com.spk.dispepsia.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.Database.UserDB;
import com.spk.dispepsia.R;

import org.w3c.dom.Text;

public class UserAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public UserAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.item_user, viewGroup, false);
        UserAdapter.MyHolder holder = new UserAdapter.MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.txt_iduserrow);
        holder.ListUsername = (TextView)v.findViewById(R.id.txt_usernamerow);
        holder.ListPassword = (TextView)v.findViewById(R.id.txt_passwordrow);
        holder.ListLevel = (TextView)v.findViewById(R.id.txt_levelrow);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        UserAdapter.MyHolder holder = (UserAdapter.MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(UserDB.row_id)));
        holder.ListUsername.setText(cursor.getString(cursor.getColumnIndex(UserDB.row_email)));
        holder.ListPassword.setText(cursor.getString(cursor.getColumnIndex(UserDB.row_password)));
        holder.ListLevel.setText(cursor.getString(cursor.getColumnIndex(UserDB.row_level)));
    }

    class MyHolder {
        TextView ListID;
        TextView ListUsername;
        TextView ListPassword;
        TextView ListLevel;
    }
}
