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
import com.spk.dispepsia.R;

import org.w3c.dom.Text;

public class GejalaAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public GejalaAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_gejala, viewGroup, false);
        GejalaAdapter.MyHolder holder = new GejalaAdapter.MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDgejalarow);
        holder.ListKodeGejala = (TextView)v.findViewById(R.id.listkodegejalarow);
        holder.ListNamaGejala = (TextView)v.findViewById(R.id.listnamagejalarow);
        holder.ListBobot = (TextView)v.findViewById(R.id.listbobotrow);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        GejalaAdapter.MyHolder holder = (GejalaAdapter.MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(GejalaDB.row_id)));
        holder.ListKodeGejala.setText(cursor.getString(cursor.getColumnIndex(GejalaDB.row_kdGejala)));
        holder.ListNamaGejala.setText(cursor.getString(cursor.getColumnIndex(GejalaDB.row_nmGejala)));
        holder.ListBobot.setText(cursor.getString(cursor.getColumnIndex(GejalaDB.row_bobot)));
    }

    class MyHolder {
        TextView ListID;
        TextView ListKodeGejala;
        TextView ListNamaGejala;
        TextView ListBobot;
    }
}
