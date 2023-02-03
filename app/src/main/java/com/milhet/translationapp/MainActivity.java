package com.milhet.translationapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.milhet.translationapp.models.Language;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    String token;
    SharedPreferences preferencesFile;

    ArrayList<Language> languages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.preferencesFile = getSharedPreferences("API-DEEPL", MODE_PRIVATE);
        this.token = this.preferencesFile.getString("token", "-1");

        if (this.token.equals("-1")) {
            Intent intent = new Intent(MainActivity.this, ParametreActivity.class);
            MainActivity.this.startActivity(intent);
        }

        final ImageButton btnHistorique = findViewById(R.id.btnHistorique);
        final ImageButton btnParametres = findViewById(R.id.btnParametres);

        btnHistorique.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HistoriqueActivity.class);
            MainActivity.this.startActivity(intent);

        });

        btnParametres.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ParametreActivity.class);
            MainActivity.this.startActivity(intent);
        });


        //this.token = "0da5a1b3-1637-fd73-e550-79b8954cf379:fx";

        //ajout d'une langue vide pour le spinner de la langue source pour eviter de traduire sans que la langue aie été volontairement choisie par l'utilisateur
        this.languages.add(new Language("", ""));
        loadLanguage();

    }

    public void loadLanguage() {
        Context that = this;
        //requete pour récupérer les langues
        AndroidNetworking.get("https://api-free.deepl.com/v2/languages")
                .addHeaders("Authorization", "DeepL-Auth-Key " + this.token)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // on récupère le nom et le code de la langue
                            String lName;
                            String lCode;

                            // on ajoute les langues dans la liste
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject languageI = response.getJSONObject(i);
                                lName = languageI.getString("name");
                                lCode = languageI.getString("language");
                                languages.add(new Language(lCode, lName));

                            }
                            // on ajoute la liste dans le spinner via un adapter
                            ArrayAdapter<Language> adapter = new ArrayAdapter<>(that, android.R.layout.simple_spinner_dropdown_item, languages);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            Spinner spinner = findViewById(R.id.spinnerListeLangue);
                            spinner.setAdapter(adapter);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        try {
                            throw (anError);
                        } catch (ANError e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    public void translateButton(View view) {
        Context that = this;
        // on récupère les éléments de la vue
        // on récupère la langue sélectionnée dans le spinner
        Spinner spinner = findViewById(R.id.spinnerListeLangue);
        Language language = (Language) spinner.getSelectedItem();

        //et le texte à traduire
        EditText textATraduire = findViewById(R.id.editTexteATraduire);

        //paramètres de la requête:
        String textToTranslate = textATraduire.getText().toString();
        String target_lang = language.getLanguage();

        // on récupère les éléments de la vue
        TextView textTraduit = findViewById(R.id.textViewTexteTraduit);
        TextView detected_source_language = findViewById(R.id.affichageLangueDetectee);

        // on vérifie que les champs ne sont pas vides
        if (!textToTranslate.isEmpty() && !target_lang.isEmpty()) {
            translate(textToTranslate, target_lang, language.getName(), textTraduit, detected_source_language);

        // gestion des erreurs
        } else if (textToTranslate.isEmpty() && target_lang.isEmpty()) {
            textTraduit.setText("Veuillez entrer un texte à traduire et une langue de traduction");
            textTraduit.setTextColor(that.getColor(R.color.red));


        } else if (textToTranslate.isEmpty()) {
            textTraduit.setText("Veuillez entrer un texte à traduire");
            textTraduit.setTextColor(that.getColor(R.color.red));

        } else {
            textTraduit.setText("Veuillez entrer une langue de traduction");
            textTraduit.setTextColor(that.getColor(R.color.red));
        }
    }


    public void translate(String textToTranslate, String target_lang, String target_lang_full, TextView textTraduit, TextView detected_source_language) {
        Context that = this;
        // requête pour traduire le texte
        AndroidNetworking.post("https://api-free.deepl.com/v2/translate")
                .addHeaders("Authorization", "DeepL-Auth-Key " + token) // on ajoute le token dans les headers
                .addBodyParameter("text", textToTranslate)  //on passe le texte à traduire
                .addBodyParameter("target_lang", target_lang) //et la langue de traduction
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() { // on récupère la réponse sous forme de JSONObject
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // on récupère la traduction et la langue détectée
                            String traduction = response.getJSONArray("translations")
                                    .getJSONObject(0)
                                    .getString("text");

                            // on récupère le code de la langue détectée
                            String langueDetectee = response.getJSONArray("translations")
                                    .getJSONObject(0)
                                    .getString("detected_source_language");

                            // On affiche la traduction dans le TextView
                            textTraduit.setText(traduction);
                            textTraduit.setTextColor(that.getColor(R.color.black));

                            // On cherche le nom de la langue dans la liste des langues disponibles pour l'afficher
                            String detectedLanguageName = "";
                            for (Language language : languages) {
                                if (language.getLanguage().equals(langueDetectee)) {
                                    detectedLanguageName = language.getName();
                                    break;
                                }
                            }
                            // On affiche le nom de la langue dans le TextView
                            detected_source_language.setText(detectedLanguageName);

                            // On sauvegarde la traduction dans l'historique
                            saveTraductions(detectedLanguageName, target_lang_full, textToTranslate, traduction);


                        } catch (JSONException e) {

                            System.out.println("exception traduction");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("erreur traduction");
                    }
                });
    }

    // fonction de sauvegarde de la traduction dans l'historique
    public void saveTraductions(String langueSource, String langueCible, String textSource, String textTraduit) {
        // on récupère les préférences de l'historique
        SharedPreferences sharedPreferences = getSharedPreferences("historiqueLocal", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //on incrémente le nombre de traductions
        int nbTransactions = sharedPreferences.getInt("nbTransactions", 0);

        // on sauvegarde les informations de la traduction
        for (int i = 0; i < nbTransactions; i++) {
            String langueSourceI = sharedPreferences.getString("historiqueLangueSource" + i, "");
            String langueCibleI = sharedPreferences.getString("historiqueLangueCible" + i, "");
            String textSourceI = sharedPreferences.getString("historiqueTexteSource" + i, "");
            String textTraduitI = sharedPreferences.getString("historiqueTexteCible" + i, "");

            // on décale les traductions précédentes
            if (i < 9) {
                editor.putString("historiqueLangueSource" + (i + 1), langueSourceI);
                editor.putString("historiqueLangueCible" + (i + 1), langueCibleI);
                editor.putString("historiqueTexteSource" + (i + 1), textSourceI);
                editor.putString("historiqueTexteCible" + (i + 1), textTraduitI);
            }
        }

        // on sauvegarde la traduction actuelle
        editor.putString("historiqueLangueSource0", langueSource);
        editor.putString("historiqueLangueCible0", langueCible);
        editor.putString("historiqueTexteSource0", textSource);
        editor.putString("historiqueTexteCible0", textTraduit);

        // on limite le nombre de traductions à 10
        nbTransactions = Math.min(nbTransactions + 1, 10);

        // on sauvegarde le nombre de traductions
        editor.putInt("nbTransactions", nbTransactions);
        editor.apply();
    }


}
