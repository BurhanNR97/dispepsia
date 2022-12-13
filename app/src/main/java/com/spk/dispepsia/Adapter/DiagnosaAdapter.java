package com.spk.dispepsia.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

import org.w3c.dom.Text;

public class DiagnosaAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DiagnosaAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_diagnosa, viewGroup, false);
        DiagnosaAdapter.MyHolder holder = new DiagnosaAdapter.MyHolder();
        holder.ListBobot = (TextView)v.findViewById(R.id.listBobot_diagnosarow);
        holder.ListGejala = (CheckBox) v.findViewById(R.id.listgejaladiagnosarow);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        DiagnosaAdapter.MyHolder holder = (DiagnosaAdapter.MyHolder)view.getTag();
        holder.ListBobot.setText(cursor.getString(cursor.getColumnIndex(GejalaDB.row_bobot)));
        holder.ListGejala.setText(cursor.getString(cursor.getColumnIndex(GejalaDB.row_kdGejala))+" - "+cursor.getString(cursor.getColumnIndex(GejalaDB.row_nmGejala)));
    }

    class MyHolder {
        TextView ListBobot;
        CheckBox ListGejala;
    }
}
