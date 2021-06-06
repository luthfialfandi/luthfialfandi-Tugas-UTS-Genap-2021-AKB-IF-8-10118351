/**
 * NIM            : 10118351
 * Nama           : Luthfi Alfandi
 * Kelas          : IF-8
 * Tgl Pengerjaan : 5 Juni 2021
 **/

package com.example.tugas_uts_10118351.main.catatan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tugas_uts_10118351.R;
import com.example.tugas_uts_10118351.main.SqliteDatabase;
import com.example.tugas_uts_10118351.main.informasi.InformasiActivity;
import com.example.tugas_uts_10118351.main.profil.ProfilActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class KategoriActivity extends AppCompatActivity {

    String[] daftar, id_kategori;
    ListView listView;
    private Cursor cursor;
    SqliteDatabase database;
    public static KategoriActivity ka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        this.setTitle("Daftar Kategori");
        setContentView(R.layout.activity_kategori);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.catatan);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.catatan:
                        return true;
                    case  R.id.profil:
                        Intent intent2 = new Intent(getApplicationContext(), ProfilActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.informasi:
                        Intent intent3 = new Intent(getApplicationContext(), InformasiActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriActivity.this, SaveKategoriActivity.class);
                startActivity(intent);
            }
        });

        ka = this;
        database = new SqliteDatabase(this);
        RefreshList();
    }

    public void RefreshList() {
        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM kategori", null);
        id_kategori = new String[cursor.getCount()];
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            id_kategori[cc] = cursor.getString(0).toString();
            daftar[cc] = cursor.getString(1).toString();
        }

        listView = findViewById(R.id.listKategori);

        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final int selection = Integer.parseInt(id_kategori[arg2]);
                Intent intent = new Intent(KategoriActivity.this, DaftarCatatanActivity.class);
                intent.putExtra("kategori", selection);
                startActivity(intent);
            }});

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id2) {
                final int which_item = position;

                final CharSequence[] dialogitem = {"Edit Kategori", "Hapus Kategori"};
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(KategoriActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(KategoriActivity.this);
                                builder1.setTitle("Edit Kategori");
                                View view = getLayoutInflater().inflate(R.layout.update_kategori, null);

                                //init
                                EditText namaKategori = view.findViewById(R.id.namaKategori);

                                builder1.setView(view);

                                builder1.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = database.getWritableDatabase();
                                        db.execSQL("update kategori set kategori='"+ namaKategori +"' " +
                                                "WHERE id_kategori = "+ id_kategori[which_item] +"");
                                        RefreshList();
                                    }
                                });
                                builder1.setNegativeButton("Batal", null);
                                builder1.create().show();
                                break;
                            case 1 :
                                AlertDialog.Builder builder = new AlertDialog.Builder(KategoriActivity.this);
                                builder.setTitle("Delete Kategori");
                                builder.setMessage("Yakin menghapus kategori ini?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = database.getWritableDatabase();
                                        db.execSQL("delete from kategori where id_kategori ="+ id_kategori[which_item]);
                                        RefreshList();
                                    }
                                });
                                builder.setNegativeButton("No", null);
                                builder.create().show();
                                break;
                        }
                    }
                });
                builder.create().show();
                return true;
            }});
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.kategori, parent, false);

            TextView kategoriTitle = convertView.findViewById(R.id.kategori_title);
            kategoriTitle.setText(daftar[position]);

            return convertView;
        }
    }
}