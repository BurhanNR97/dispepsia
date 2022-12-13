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

import com.spk.dispepsia.Activities.JenisPenyakit;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

public class JenisPenyakitAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public JenisPenyakitAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_jenis_penyakit, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDjenisrow);
        holder.ListKodeJenis = (TextView)v.findViewById(R.id.listkodejenisrow);
        holder.ListNamaJenis = (TextView)v.findViewById(R.id.listnamajenisrow);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(JenisPenyakitDB.row_id)));
        holder.ListKodeJenis.setText(cursor.getString(cursor.getColumnIndex(JenisPenyakitDB.row_kdPenyakit)));
        holder.ListNamaJenis.setText(cursor.getString(cursor.getColumnIndex(JenisPenyakitDB.row_nmPenyakit)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListKodeJenis;
        TextView ListNamaJenis;
    }
}
