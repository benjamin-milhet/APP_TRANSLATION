package com.milhet.translationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton btnHistorique = findViewById(R.id.btnHistorique);
        final ImageButton btnParametres = findViewById(R.id.btnParametres);

        btnHistorique.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HistoriqueActivity.class);
            MainActivity.this.startActivity(intent);
        });
    }
}