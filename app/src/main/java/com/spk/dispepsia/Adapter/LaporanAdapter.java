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
import com.spk.dispepsia.Database.DiagnosaDB;
import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

import org.w3c.dom.Text;

public class LaporanAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public LaporanAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_laporan, viewGroup, false);
        LaporanAdapter.MyHolder holder = new LaporanAdapter.MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDlaporan);
        holder.ListKode = (TextView)v.findViewById(R.id.listkodelaporan);
        holder.ListTanggal = (TextView)v.findViewById(R.id.listtanggallaporan);
        holder.ListNama = (TextView)v.findViewById(R.id.listnamalaporan);
        holder.ListHasil = (TextView)v.findViewById(R.id.listhasillaporan);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        LaporanAdapter.MyHolder holder = (LaporanAdapter.MyHolder)view.getTag();
        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_id)));
        holder.ListKode.setText("("+cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_kdDiagnosa))+") "+cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_tgl_diagnosa)));
        holder.ListTanggal.setText(cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_nik_pasien)));
        holder.ListNama.setText(cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_nama_pasien)));
        holder.ListHasil.setText(cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_hasil_diagnosa)));
    }

    class MyHolder {
        TextView ListID;
        TextView ListKode;
        TextView ListTanggal;
        TextView ListNama;
        TextView ListHasil;
    }
}
