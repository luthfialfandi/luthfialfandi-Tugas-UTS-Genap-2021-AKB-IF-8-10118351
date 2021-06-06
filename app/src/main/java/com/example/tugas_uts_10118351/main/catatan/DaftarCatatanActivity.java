/**
 * NIM            : 10118351
 * Nama           : Luthfi Alfandi
 * Kelas          : IF-8
 * Tgl Pengerjaan : 5 Juni 2021
 **/

package com.example.tugas_uts_10118351.main.catatan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tugas_uts_10118351.R;
import com.example.tugas_uts_10118351.main.SqliteDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DaftarCatatanActivity extends AppCompatActivity {

    String[] id, daftar, daftar2, daftar3, daftar4, daftar5;
    ListView listView;
    int kategori;
    private Cursor cursor;
    SqliteDatabase database;
    public static DaftarCatatanActivity ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_catatan);

        this.setTitle("Daftar Catatan");
        setContentView(R.layout.activity_daftar_catatan);

        Intent getKategori = getIntent();
        kategori = getKategori.getIntExtra("kategori", 0);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarCatatanActivity.this, CatatanActivity.class);
                intent.putExtra("kategori", kategori);
                startActivity(intent);
            }
        });
        ln = this;
        database = new SqliteDatabase(this);
        RefreshListNote();
    }

    public void RefreshListNote() {
        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM note WHERE id_kategori = "+kategori, null);
        id = new String[cursor.getCount()];
        daftar = new String[cursor.getCount()];
        daftar2 = new String[cursor.getCount()];
        daftar3 = new String[cursor.getCount()];
        daftar4 = new String[cursor.getCount()];
        daftar5 = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            id[cc] = cursor.getString(0).toString();
            daftar[cc] = cursor.getString(1).toString();
            daftar2[cc] = cursor.getString(2).toString();
            daftar3[cc] = cursor.getString(3).toString();
            daftar4[cc] = cursor.getString(4).toString();
            daftar5[cc] = cursor.getString(5).toString();
        }

        listView = findViewById(R.id.listNote);

        DaftarCatatanActivity.MyAdapter adapter = new DaftarCatatanActivity.MyAdapter();
        listView.setAdapter(adapter);
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final int selection = Integer.parseInt(id[arg2]);
                Intent in = new Intent(getApplicationContext(), UpdateCatatanActivity.class);
                in.putExtra("id", selection);
                startActivity(in);
            }});
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id2) {
                final int which_item = position;

                AlertDialog.Builder builder = new AlertDialog.Builder(DaftarCatatanActivity.this);
                builder.setTitle("Hapus Catatan");
                builder.setMessage("Ingin menghapus catatan ini?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = database.getWritableDatabase();
                        db.execSQL("delete from note where id ="+ id[which_item]);
                        RefreshListNote();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
                return true;
            }
        });
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
            convertView = getLayoutInflater().inflate(R.layout.items_catatan, parent, false);

            TextView noteTitle = convertView.findViewById(R.id.text_title);
            TextView noteDesc = convertView.findViewById(R.id.text_content);
            TextView noteDate = convertView.findViewById(R.id.text_date);
            TextView noteTime = convertView.findViewById(R.id.text_time);

            noteDate.setText(daftar[position]);
            noteTime.setText(daftar2[position]);
            noteTitle.setText(daftar3[position]);
            noteDesc.setText(daftar4[position]);

            return convertView;
        }
    }
}