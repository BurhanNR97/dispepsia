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
import com.spk.dispepsia.Database.SolusiDB;
import com.spk.dispepsia.R;

import org.w3c.dom.Text;

public class SolusiAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SolusiAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_solusi, viewGroup, false);
        SolusiAdapter.MyHolder holder = new SolusiAdapter.MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDsolusirow);
        holder.ListKodeSolusi = (TextView)v.findViewById(R.id.listkodesolusirow);
        holder.ListNamaSolusi = (TextView)v.findViewById(R.id.listnamasolusirow);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        SolusiAdapter.MyHolder holder = (SolusiAdapter.MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(SolusiDB.row_id)));
        holder.ListKodeSolusi.setText(cursor.getString(cursor.getColumnIndex(SolusiDB.row_kdSolusi)));
        holder.ListNamaSolusi.setText(cursor.getString(cursor.getColumnIndex(SolusiDB.row_nmSolusi)));
    }

    class MyHolder {
        TextView ListID;
        TextView ListKodeSolusi;
        TextView ListNamaSolusi;
    }
}
