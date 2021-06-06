/**
 * NIM            : 10118351
 * Nama           : Luthfi Alfandi
 * Kelas          : IF-8
 * Tgl Pengerjaan : 5 Juni 2021
 **/

package com.example.tugas_uts_10118351.splash_screen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.tugas_uts_10118351.R;
import com.example.tugas_uts_10118351.main.catatan.KategoriActivity;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {
    
    private static int dtk = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, KategoriActivity.class);
                startActivity(intent);
                finish();
            }
        }, dtk);
    }
}