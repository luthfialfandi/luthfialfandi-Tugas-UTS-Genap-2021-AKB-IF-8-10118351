/**
 * NIM            : 10118351
 * Nama           : Luthfi Alfandi
 * Kelas          : IF-8
 * Tgl Pengerjaan : 5 Juni 2021
 **/

package com.example.tugas_uts_10118351.main.catatan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tugas_uts_10118351.R;
import com.example.tugas_uts_10118351.main.SqliteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CatatanActivity extends AppCompatActivity {

    private EditText titleInput;
    private EditText contentInput;
    private Button btn_simpan, btn_kembali;
    int kategori;
    Date dt = new Date();
    SqliteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan);

        this.setTitle("Catatan Harian");
        setContentView(R.layout.activity_catatan);

        database = new SqliteDatabase(this);
        titleInput = findViewById(R.id.title_note);
        contentInput = findViewById(R.id.content_note);
        btn_simpan = findViewById(R.id.btn_submit);
        btn_kembali = findViewById(R.id.btn_back);

        SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm a");

        Intent getKategori = getIntent();
        kategori = getKategori.getIntExtra("kategori", 0);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = database.getWritableDatabase();
                db.execSQL("insert into note(tanggal, waktu, judul, isi, id_kategori) values('"+ date.format(dt) +"','" +
                        time.format(dt) + "','" +
                        titleInput.getText().toString() + "','"+
                        contentInput.getText().toString() +"','" +
                        kategori + "')");
                Toast.makeText(getApplicationContext(), "Catatan telah disimpan", Toast.LENGTH_LONG).show();
                DaftarCatatanActivity.ln.RefreshListNote();
                finish();
            }
        });

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

    }
}