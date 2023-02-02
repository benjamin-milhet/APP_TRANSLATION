package com.milhet.translationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class ParametreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);

        final ImageButton btnPagePrincipale = findViewById(R.id.btnPagePrincipale);
        final ImageButton btnHistorique = findViewById(R.id.btnHistorique);

        btnPagePrincipale.setOnClickListener(view -> {
            Intent intent = new Intent(ParametreActivity.this, MainActivity.class);
            ParametreActivity.this.startActivity(intent);
        });
        btnHistorique.setOnClickListener(view -> {
            Intent intent = new Intent(ParametreActivity.this, HistoriqueActivity.class);
            ParametreActivity.this.startActivity(intent);
        });

    }
}