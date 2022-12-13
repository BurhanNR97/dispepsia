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

import com.spk.dispepsia.Activities.Aturan;
import com.spk.dispepsia.Database.AturanDB;
import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

import org.w3c.dom.Text;

public class AturanAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AturanAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_aturan, viewGroup, false);
       AturanAdapter.MyHolder holder = new AturanAdapter.MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDaturanrow);
        holder.ListKode = (TextView)v.findViewById(R.id.listkodeaturanarow);
        holder.ListAlternatif = (TextView)v.findViewById(R.id.listalternatifrow);
        holder.ListKriteria = (TextView)v.findViewById(R.id.listkriteriarow);
        holder.ListNilai = (TextView)v.findViewById(R.id.listnilairow);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        AturanAdapter.MyHolder holder = (AturanAdapter.MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(AturanDB.row_id)));
        holder.ListKode.setText(cursor.getString(cursor.getColumnIndex(AturanDB.row_kdRules)));
        holder.ListAlternatif.setText(cursor.getString(cursor.getColumnIndex(AturanDB.row_alternatif)));
        holder.ListKriteria.setText(cursor.getString(cursor.getColumnIndex(AturanDB.row_kriteria)));
        holder.ListNilai.setText(cursor.getString(cursor.getColumnIndex(AturanDB.row_nilai)));
    }

    class MyHolder {
        TextView ListID;
        TextView ListKode;
        TextView ListAlternatif;
        TextView ListKriteria;
        TextView ListNilai;
    }
}
