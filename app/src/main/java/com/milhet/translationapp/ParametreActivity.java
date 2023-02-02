package com.milhet.translationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ParametreActivity extends AppCompatActivity {

    String token;
    SharedPreferences preferencesFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);

        this.preferencesFile = getSharedPreferences("API-DEEPL", MODE_PRIVATE);
        this.token = this.preferencesFile.getString("token", "-1");

        final ImageButton btnPagePrincipale = findViewById(R.id.btnPagePrincipale);
        final ImageButton btnHistorique = findViewById(R.id.btnHistorique);
        final Button btnChargerClef = findViewById(R.id.btnChargerClef);

        final EditText tokenEditText = findViewById(R.id.editClefDEEPL);

        if (this.token.equals("-1")) {
            btnPagePrincipale.setEnabled(false);
            btnHistorique.setEnabled(false);
        } else {
            btnPagePrincipale.setEnabled(true);
            btnHistorique.setEnabled(true);

            tokenEditText.setText(this.token);
        }

        btnPagePrincipale.setOnClickListener(view -> {
            Intent intent = new Intent(ParametreActivity.this, MainActivity.class);
            ParametreActivity.this.startActivity(intent);
        });

        btnHistorique.setOnClickListener(view -> {
            Intent intent = new Intent(ParametreActivity.this, HistoriqueActivity.class);
            ParametreActivity.this.startActivity(intent);
        });

        btnChargerClef.setOnClickListener(view -> {
            SharedPreferences.Editor editor = this.preferencesFile.edit();
            editor.putString("token", tokenEditText.getText().toString());
            editor.apply();

            this.token = this.preferencesFile.getString("token", "-1");

            if (this.token.equals("-1")) {
                btnPagePrincipale.setEnabled(false);
                btnHistorique.setEnabled(false);
            } else {
                btnPagePrincipale.setEnabled(true);
                btnHistorique.setEnabled(true);
            }
        });

    }
}