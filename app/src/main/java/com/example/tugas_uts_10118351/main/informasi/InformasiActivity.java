/**
 * NIM            : 10118351
 * Nama           : Luthfi Alfandi
 * Kelas          : IF-8
 * Tgl Pengerjaan : 5 Juni 2021
 **/

package com.example.tugas_uts_10118351.main.informasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tugas_uts_10118351.R;
import com.example.tugas_uts_10118351.main.catatan.KategoriActivity;
import com.example.tugas_uts_10118351.main.profil.ProfilActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InformasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);

        this.setTitle("Informasi Aplikasi");
        setContentView(R.layout.activity_informasi);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.informasi);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.catatan:
                        Intent intent = new Intent(getApplicationContext(), KategoriActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.profil:
                        Intent intent2 = new Intent(getApplicationContext(), ProfilActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.informasi:
                        return true;
                }
                return false;
            }
        });
    }
}