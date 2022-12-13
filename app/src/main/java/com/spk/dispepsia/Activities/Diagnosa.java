package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.common.primitives.Ints;
import com.spk.dispepsia.Database.AturanDB;
import com.spk.dispepsia.Database.DiagnosaDB;
import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Diagnosa extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    GejalaDB gejalaDB;
    JenisPenyakitDB jenisPenyakitDB;
    AturanDB aturanDB;
    DiagnosaDB diagnosaDB;
    List<String> ci = new ArrayList<>();
    List<String> nn = new ArrayList<>();
    List<Integer> bb = new ArrayList<>();
    String[] kriteria = null;
    int[] bobot = null;
    String[] alternatif = null;
    int[][] alternatifkriteria;
    String zz = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);

        Toolbar toolbar = findViewById(R.id.toolbardiagnosa);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Diagnosa");

        String nik = getIntent().getStringExtra("nik");
        String nama = getIntent().getStringExtra("nama");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formatdate = df.format(c.getTime());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

        diagnosaDB = new DiagnosaDB(getBaseContext());

        diagnosaDB = new DiagnosaDB(this);
        gejalaDB = new GejalaDB(this);
        jenisPenyakitDB = new JenisPenyakitDB(this);
        aturanDB = new AturanDB(this);

        listView = (ListView)findViewById(R.id.list_diagnosa);
        listView.setOnItemClickListener(this);

        Button proses = (Button)findViewById(R.id.btn_prosesdiagnosa);
        Button hitung = (Button)findViewById(R.id.hitung);
        TextView hasilDiagnosa = (TextView)findViewById(R.id.editHasilDiagnosa);

        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listView.getCheckedItemCount()<2){
                    Toast toast = Toast.makeText(Diagnosa.this, "Minimal memilih 2 gejala", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    //Alternatif
                    Cursor cr = jenisPenyakitDB.allData();
                    nn = jenisPenyakitDB.alternatif();
                    if(cr.moveToNext()){
                        int jml = cr.getColumnCount();
                        for (int i=0; i-1<jml-1; i++){
                            alternatif = nn.toArray(new String[i]);
                        }
                    }

                    //Kriteria
                    for (int i=0; i<listView.getCount(); i++){
                        if (listView.isItemChecked(i)){
                            ci.add(listView.getItemAtPosition(i).toString().substring(0,3));
                            for (int j=0; j-1<listView.getCheckedItemCount()-1; j++){
                                kriteria = ci.toArray(new String[j]);
                            }
                        }
                    }

                    //Bobot
                    for (int i=0; i<listView.getCount(); i++){
                        if (listView.isItemChecked(i)){
                            String data = listView.getItemAtPosition(i).toString();
                            int a = data.length()-2;
                            String b = listView.getItemAtPosition(i).toString().substring(a);
                            bb.add(Integer.parseInt(b.substring(0,1)));
                            for (int j=0; j-1<listView.getCheckedItemCount()-1; j++){
                                bobot = Ints.toArray(bb);
                            }
                        }
                    }

                    //Nilai Atribut
                    alternatifkriteria = new int[alternatif.length][kriteria.length];
                    for (int i=0; i<alternatif.length; i++){
                        for (int k=0; k<kriteria.length; k++){
                            String alt = alternatif[i];
                            String kri = kriteria[k];
                            Cursor cur = aturanDB.altkri(alt, kri);
                            if (cur.moveToNext()){
                                int nilai = Integer.parseInt(cur.getString(cur.getColumnIndex(AturanDB.row_nilai)));
                                alternatifkriteria[i][k] = nilai;
                            }
                        }
                    }

                    //Pembagi
                    double[] pembagi = new double[kriteria.length];
                    for (int i=0; i<kriteria.length; i++){
                        pembagi[i] = 0;
                        for (int j=0; j<alternatif.length; j++){
                            pembagi[i] = pembagi[i] + (alternatifkriteria[j][i] * alternatifkriteria[j][i]);
                        }
                        pembagi[i] = Math.sqrt(pembagi[i]);
                    }

                    //Normalisasi Matriks
                    double[][] normalisasi = new double[alternatif.length][kriteria.length];
                    for (int i=0; i<nn.size(); i++){
                        for (int j=0; j<kriteria.length; j++){
                            normalisasi[i][j] = alternatifkriteria[i][j] / pembagi[j];
                        }
                    }

                    //Normalisasi Terbobot
                    double[][] terbobot = new double[alternatif.length][kriteria.length];
                    for (int i=0; i<alternatif.length; i++){
                        for (int j=0; j<kriteria.length; j++){
                            terbobot[i][j] = normalisasi[i][j] * bobot[j];
                        }
                    }

                    //Solusi ideal positif
                    double[] aplus = new double[kriteria.length];
                    for (int i=0; i<kriteria.length; i++){
                        for (int j=0; j<alternatif.length; j++){
                            if ((j==0) || (aplus[i] < terbobot[j][i])) {
                                aplus[i] = terbobot[j][i];
                            }
                        }
                    }

                    //Solusi ideal negatif
                    double[] amin = new double[kriteria.length];
                    for (int i=0; i<kriteria.length; i++){
                        for (int j=0; j<alternatif.length; j++){
                            if ((j==0) || (amin[i] > terbobot[j][i])) {
                                amin[i] = terbobot[j][i];
                            }
                        }
                    }

                    //D+
                    double[] dplus = new double[alternatif.length];
                    for (int m=0; m<alternatif.length; m++) {
                        dplus[m] = 0;
                        for (int n=0; n<kriteria.length; n++){
                            dplus[m] = dplus[m] + ((terbobot[m][n] - aplus[n]) * (terbobot[m][n] - aplus[n]));
                        }
                        dplus[m] = Math.sqrt(dplus[m]);
                    }

                    //D-
                    double[] dmin = new double[alternatif.length];
                    for (int i=0; i<alternatif.length; i++) {
                        dmin[i] = 0;
                        for (int j=0; j<kriteria.length; j++){
                            dmin[i] = dmin[i] + ((terbobot[i][j] - amin[j]) * (terbobot[i][j] - amin[j]));
                        }
                        dmin[i] = Math.sqrt(dmin[i]);
                    }

                    //Preferensi
                    double[] hasil = new double[alternatif.length];
                    for (int i=0; i<alternatif.length; i++){
                        hasil[i] = dmin[i] / (dmin[i] + dplus[i]);
                    }

                    //Rangking
                    String[] alternatifRangking = new String[alternatif.length];
                    double[] hasilRangking = new double[alternatif.length];
                    for (int i=0; i<alternatif.length; i++){
                        hasilRangking[i] = hasil[i];
                        alternatifRangking[i] = alternatif[i];
                    }
                    for (int i=0; i<alternatif.length; i++){
                        for (int j=i; j<alternatif.length; j++){
                            if (hasilRangking[j] > hasilRangking[i]){
                                double tmphasil = hasilRangking[i];
                                String tmpAlternatif = alternatifRangking[i];
                                hasilRangking[i] = hasilRangking[j];
                                alternatifRangking[i] = alternatifRangking[j];
                                hasilRangking[j] = tmphasil;
                                alternatifRangking[j] = tmpAlternatif;
                            }
                        }
                    }

                    //Solusi Pengobatan
                    String solusi = "";
                    if (alternatifRangking[0].equals("A1")){
                        solusi = "1. Mengatur pola makan \n"+
                                "2. Menghindari makanan yang terlalu pedis \n"+
                                "3. Hindari obat yang bisa mengiritasi lambung \n";
                    } else
                    if (alternatifRangking[0].equals("A2")){
                        solusi = "1. Menghindari makanan yang mengandung lemak yang tinggi, misalnya keju dan cokelat \n"+
                                "2. Menghindari makanan yang menimbulkan gas seperti kol, kubis dan kentang \n"+
                                "3. Mengonsumsi makanan lebih sering dengan porsi lebih sedikit. Perut yang kosong kadang dapat menyebabkan sakit perut non-ulkus. Perut yang kosong dengan asam dapat membuat Anda mual. Cobalah untuk mengonsumsi cemilan, seperti cracker atau buah-buahan. \n"+
                                "4. Kunyah makanan dengan perlahan hingga halus. Luangkan waktu untuk makan dengan perlahan.";
                    } else
                    if (alternatifRangking[0].equals("A3")){
                        solusi = "1. Menghindari makanan yang terlalu pedis \n"+
                                "2. Menghindari rokok, alkohol, dan minuman dengan kadar kafein tinggi \n"+
                                "3. Hindari stress yang berlebihan \n";
                    }

                    //Hasil Diagnosa
                    String teksHasil = "";
                    double ambil = hasilRangking[0] * 100;
                    DecimalFormat persen = new DecimalFormat("##.#");
                    Cursor diag = jenisPenyakitDB.hasil(alternatifRangking[0]);
                    if (diag.moveToFirst()){
                        zz = diag.getString(diag.getColumnIndex(JenisPenyakitDB.row_nmPenyakit));
                        teksHasil = "Berdasarkan gejala-gejala yang dipilih. Hasil diagnosa anda "+persen.format(ambil)+"% "+
                                zz+"\n\n"+
                                "Solusi Pencegahan : \n"+solusi;
                    }

                    Cursor kode = diagnosaDB.kode();
                    String kd_diagnosa = "";
                    if (kode.moveToNext()) {
                        String a = kode.getString(1).substring(1);
                        int b = Integer.parseInt(a);
                        int c = b + 1;
                        String d = String.valueOf(c);
                        kd_diagnosa="D"+c;
                    } else
                    {
                        kd_diagnosa="D100001";
                    }

                    ContentValues dataDiag = new ContentValues();
                    dataDiag.put(DiagnosaDB.row_kdDiagnosa, kd_diagnosa);
                    dataDiag.put(DiagnosaDB.row_nik_pasien, nik);
                    dataDiag.put(DiagnosaDB.row_nama_pasien, nama);
                    dataDiag.put(DiagnosaDB.row_hasil_diagnosa, persen.format(ambil)+"% "+zz);
                    dataDiag.put(DiagnosaDB.row_tgl_diagnosa, formatdate);
                    diagnosaDB.insertData(dataDiag);

                    TextView txNormalisasi = (TextView) findViewById(R.id.editNormalisasi);
                    TextView bantuan = findViewById(R.id.teksBantuan);

                    bantuan.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    hasilDiagnosa.setVisibility(View.VISIBLE);
                    hasilDiagnosa.setText(teksHasil);
                    hitung.setVisibility(View.VISIBLE);
                    proses.setVisibility(View.GONE);
                    getSupportActionBar().setTitle("Hasil Diagnosa");

                    txNormalisasi.setText("Alternatif : "+Arrays.toString(alternatif)+"\n"+
                            "Kriteria : "+Arrays.toString(kriteria)+"\n"+
                            "Nilai bobot : "+Arrays.toString(bobot)+"\n\n"+
                            "Nilai Atribut : \n"+Arrays.deepToString(alternatifkriteria)+"\n\n"+
                            "Nilai Pembagi : \n"+Arrays.toString(pembagi)+"\n\n"+
                            "Matriks ternormalisasi : \n"+Arrays.deepToString(normalisasi)+"\n\n"+
                            "Matriks ternormalisasi terbobot : \n"+Arrays.deepToString(terbobot)+"\n\n"+
                            "Matriks solusi ideal Positif : \n"+Arrays.toString(aplus)+"\n\n"+
                            "Matriks solusi ideal negatif : \n"+Arrays.toString(amin)+"\n\n"+
                            "Jarak antara nilai alternatif dan solusi ideal :\n"+
                            "D+: \n"+Arrays.toString(dplus)+"\n"+"D-: \n"+Arrays.toString(dmin)+"\n"+
                            "Nilai Preferensi: \n"+Arrays.toString(hasil)+"\n\n" +
                            "Hasil Rangking :"+"\n"+
                                Arrays.toString(alternatifRangking)+"\n"+
                                Arrays.toString(hasilRangking)+"\n\n"+
                            "Didapat hasil perhitungan dengan nilai tertinggi '"+alternatifRangking[0]+"' yaitu "+zz+
                            " dengan nilai kepastian "+hasilRangking[0]);
                    
                    hitung.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txNormalisasi.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_diagnosa, menu);
        return true;
    }

    public void setListView(){
        /*Cursor cursor = gejalaDB.allData();
        DiagnosaAdapter customCursorAdapter = new DiagnosaAdapter(this, cursor, 1);
        listView.setAdapter(customCursorAdapter); */

        List<String> data = gejalaDB.ambilData();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, data);
        listView.setAdapter(dataAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }
}