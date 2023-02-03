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

/**
 * Classe représentant l'activité de l'historique
 * @author Milhet et Ghys
 */
public class HistoriqueActivity extends AppCompatActivity {
    ArrayList<Traduction> traductions = new ArrayList<>(); //liste de traductions
    TraductionAdapter traductionAdapter; //adapter pour la liste

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

        // on charge les traductions enregistrées dans la liste de traductions
        loadTraductions();

        //on crée l'adapter pour la liste
        this.traductionAdapter = new TraductionAdapter(this,R.layout.sous_historique, this.traductions);

        //on affiche la liste dans la listView
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(this.traductionAdapter);


    }

    //chargement des traductions
    public void loadTraductions() {
        //on récupère les données du fichier de préférences
        SharedPreferences preferencesFile = getSharedPreferences("historiqueLocal", MODE_PRIVATE);
        Traduction traduction;

        //on parcourt le nombre de traductions enregistrées
        for (int i = 0; i < preferencesFile.getInt("nbTransactions", 0); i++) {
            //pour chaque traduction, on récupère les données
            String langueSource = "  " + preferencesFile.getString("historiqueLangueSource" + i, "");
            String langueCible = "  " + preferencesFile.getString("historiqueLangueCible" + i, "");
            String texteSource = preferencesFile.getString("historiqueTexteSource" + i, "");
            String texteCible = preferencesFile.getString("historiqueTexteCible" + i, "");

            //on ajoute la traduction à la liste
            traduction = new Traduction(langueSource, langueCible, texteSource, texteCible);
            this.traductions.add(traduction);
        }





    }
}