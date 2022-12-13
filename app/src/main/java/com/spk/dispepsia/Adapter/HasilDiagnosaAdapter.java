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

import com.spk.dispepsia.Activities.Diagnosa;
import com.spk.dispepsia.Activities.HasilDiagnosa;
import com.spk.dispepsia.Database.DiagnosaDB;
import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

import org.w3c.dom.Text;

public class HasilDiagnosaAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public HasilDiagnosaAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_hasil, viewGroup, false);
        HasilDiagnosaAdapter.MyHolder holder = new HasilDiagnosaAdapter.MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDhasil);
        holder.ListKode = (TextView)v.findViewById(R.id.listkodehasil);
        holder.ListTanggal = (TextView)v.findViewById(R.id.listtanggalhasil);
        holder.ListHasil = (TextView)v.findViewById(R.id.listhasildiagnosa);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        HasilDiagnosaAdapter.MyHolder holder = (HasilDiagnosaAdapter.MyHolder)view.getTag();
        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_id)));
        holder.ListKode.setText(cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_kdDiagnosa)));
        holder.ListTanggal.setText(cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_tgl_diagnosa)));
        holder.ListHasil.setText(cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_hasil_diagnosa)));
    }

    class MyHolder {
        TextView ListID;
        TextView ListKode;
        TextView ListTanggal;
        TextView ListHasil;
    }
}
