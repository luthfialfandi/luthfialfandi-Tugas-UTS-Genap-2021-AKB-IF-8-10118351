/**
 * NIM            : 10118351
 * Nama           : Luthfi Alfandi
 * Kelas          : IF-8
 * Tgl Pengerjaan : 5 Juni 2021
 **/

package com.example.tugas_uts_10118351.main.catatan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

public class UpdateCatatanActivity extends AppCompatActivity {

    private EditText titleInput;
    private EditText contentInput;
    private Button btn_simpan, btn_kembali;
    private Cursor cursor;
    private Date dt = new Date();
    SqliteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_catatan);

        this.setTitle("Catatan");
        setContentView(R.layout.activity_update_catatan);

        database = new SqliteDatabase(this);
        titleInput = findViewById(R.id.title_note);
        contentInput = findViewById(R.id.content_note);
        btn_simpan = findViewById(R.id.btn_submit);
        btn_kembali = findViewById(R.id.btn_back);

        SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm a");

        Intent get_ID = getIntent();
        int ID = get_ID.getIntExtra("id", 0);

        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM note WHERE id = " +
                ID, null);

        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            titleInput.setText(cursor.getString(3));
            contentInput.setText(cursor.getString(4));
        }

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = database.getWritableDatabase();
                db.execSQL("update note set tanggal='"+
                        date.format(dt) +"', waktu='" +
                        time.format(dt) +"', judul='"+
                        titleInput.getText().toString() +"', isi='" +
                        contentInput.getText().toString() +"' where id= "+ID);
                Toast.makeText(getApplicationContext(), "Berhasil diubah", Toast.LENGTH_LONG).show();
                DaftarCatatanActivity.ln.RefreshListNote();
                finish();
            }
        });

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}