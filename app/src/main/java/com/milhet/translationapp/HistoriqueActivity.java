package com.milhet.translationapp;

import static com.milhet.translationapp.R.id.listView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
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
        this.preferencesFile = getSharedPreferences("API-DEEPL", MODE_PRIVATE);
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
        traductionAdapter = new TraductionAdapter(this,R.layout.sous_historique, traductions);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(traductionAdapter);


    }
    public void loadTraductions() {
        SharedPreferences preferencesFile = getSharedPreferences("API-DEEPL", MODE_PRIVATE);
        Traduction traduction;
        /*
        for (int i = 0; i < 10; i++) {
            traduction = new Traduction(
                    preferencesFile.getString("lang_source" + i, ""),
                    preferencesFile.getString("lang_target" + i, "-1"),
                    preferencesFile.getString("text_source" + i, "-1"),
                    preferencesFile.getString("text_target" + i, "-1")
            );
            traductions.add(traduction);



        }*/
        //TODO

    }
}