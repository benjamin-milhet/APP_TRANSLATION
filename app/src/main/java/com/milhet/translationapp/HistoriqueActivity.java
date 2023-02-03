package com.milhet.translationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.milhet.translationapp.models.Traduction;
import com.milhet.translationapp.models.TraductionAdapter;

import java.util.ArrayList;

public class HistoriqueActivity extends AppCompatActivity {
ArrayList<Traduction> traductions = new ArrayList<>();
TraductionAdapter traductionAdapter;
SharedPreferences preferencesFile;

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

        btnParametres.setOnClickListener(view -> {
            Intent intent = new Intent(HistoriqueActivity.this, ParametreActivity.class);
            HistoriqueActivity.this.startActivity(intent);
        });

        loadTraductions();
        this.traductionAdapter = new TraductionAdapter(this,R.layout.sous_historique, this.traductions);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(this.traductionAdapter);


    }
    public void loadTraductions() {
        SharedPreferences preferencesFile = getSharedPreferences("historiqueLocal", MODE_PRIVATE);
        Traduction traduction;

        for (int i = 0; i < preferencesFile.getInt("nbTransactions", 0); i++) {
            String langueSource = preferencesFile.getString("historiqueLangueSource" + i, "");
            String langueCible = preferencesFile.getString("historiqueLangueCible" + i, "");
            String texteSource = preferencesFile.getString("historiqueTexteSource" + i, "");
            String texteCible = preferencesFile.getString("historiqueTexteCible" + i, "");
            traduction = new Traduction(langueSource, langueCible, texteSource, texteCible);
            this.traductions.add(traduction);
        }





    }
}