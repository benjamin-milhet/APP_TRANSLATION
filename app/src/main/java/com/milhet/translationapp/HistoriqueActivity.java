package com.milhet.translationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HistoriqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        final ImageButton btnPagePrincipale = findViewById(R.id.btnPagePrincipale);
        final ImageButton btnParametres = findViewById(R.id.btnParametres);

        btnPagePrincipale.setOnClickListener(view -> {
            Intent intent = new Intent(HistoriqueActivity.this, MainActivity.class);
            HistoriqueActivity.this.startActivity(intent);
        });
    }
}