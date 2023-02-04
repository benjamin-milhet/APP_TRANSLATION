package com.milhet.translationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * Classe représentant l'activité des paramètres avec la gestion de la clé API DEEPL
 * @author Milhet et Ghys
 */
public class ParametreActivity extends AppCompatActivity {

    String token; // clé API DEEPL
    SharedPreferences preferencesFile; // fichier de préférences
    ImageButton btnPagePrincipale; // bouton pour retourner à la page principale
    ImageButton btnHistorique; // bouton pour aller à l'historique
    Button btnChargerClef; // bouton pour charger la clé API DEEPL
    EditText tokenEditText; // champ pour entrer la clé API DEEPL
    TextView idCaracUtiliser; // nombre de caractères utilisés
    TextView idCaracTotal; // nombre de caractères total

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);

        this.preferencesFile = getSharedPreferences("API-DEEPL", MODE_PRIVATE);
        this.token = this.preferencesFile.getString("token", "-1");

        this.btnPagePrincipale = findViewById(R.id.btnPagePrincipale);
        this.btnHistorique = findViewById(R.id.btnHistorique);
        this.btnChargerClef = findViewById(R.id.btnChargerClef);

        this.idCaracUtiliser = findViewById(R.id.idCaracUtiliser);
        this.idCaracTotal = findViewById(R.id.idCaracTotal);

        this.tokenEditText = findViewById(R.id.editClefDEEPL);

        //si le token est -1, on désactive les boutons
        if (this.token.equals("-1")) {
            this.btnPagePrincipale.setEnabled(false);
            this.btnHistorique.setEnabled(false);
        } else {
            this.btnPagePrincipale.setEnabled(true);
            this.btnHistorique.setEnabled(true);

            tokenEditText.setText(this.token);
        }

        //si on clique sur le bouton de la page principale, on va sur la page principale
        this.btnPagePrincipale.setOnClickListener(view -> {
            Intent intent = new Intent(ParametreActivity.this, MainActivity.class);
            ParametreActivity.this.startActivity(intent);
        });

        //si on clique sur le bouton de l'historique, on va sur la page de l'historique
        this.btnHistorique.setOnClickListener(view -> {
            Intent intent = new Intent(ParametreActivity.this, HistoriqueActivity.class);
            ParametreActivity.this.startActivity(intent);
        });

        this.btnChargerClef.setOnClickListener(view -> this.verifierConnection(tokenEditText.getText().toString()));

        //verifier la connection
        this.verifierConnection(this.token);
        //recuperer les données de l'API
        this.recupererDataAPI();
    }


    //methode pour verifier la connection
    public void verifierConnection(String tokenTemp) {

        AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization", "DeepL-Auth-Key " + tokenTemp)
                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    //creation d'un toast
                    Toast toast;
                    @Override
                    public void onResponse(Response response) {

                        switch (response.code()){
                            //si la connection est reussi, on enregistre le token dans le fichier de préférence
                            case 200:
                                SharedPreferences.Editor editor = preferencesFile.edit();
                                editor.putString("token", tokenTemp);
                                editor.apply();

                                //on enregistre le token dans la variable token du fichier de préférence
                                token = preferencesFile.getString("token", "-1");

                                if (token.equals("-1")) {
                                    btnPagePrincipale.setEnabled(false);
                                    btnHistorique.setEnabled(false);
                                } else {
                                    btnPagePrincipale.setEnabled(true);
                                    btnHistorique.setEnabled(true);
                                }

                                recupererDataAPI();


                                //le toast affiche "Connexion reussi"
                                toast = Toast.makeText(getApplicationContext(), "Connexion reussi", Toast.LENGTH_SHORT);
                                toast.show();
                                break;

                            //si la connection echoue, on affiche un toast avec le code d'erreur
                            case 404:
                                btnPagePrincipale.setEnabled(false);
                                btnHistorique.setEnabled(false);

                                toast = Toast.makeText(getApplicationContext(), "Page introuvable", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            //si la connection echoue, on affiche un toast avec le code d'erreur
                            case 429:
                                btnPagePrincipale.setEnabled(false);
                                btnHistorique.setEnabled(false);

                                toast = Toast.makeText(getApplicationContext(), "Trop de requete", Toast.LENGTH_SHORT);
                                toast.show();
                                break;

                            //si la connection echoue, on affiche un toast avec le code d'erreur
                            case 456:
                                btnPagePrincipale.setEnabled(false);
                                btnHistorique.setEnabled(false);

                                toast = Toast.makeText(getApplicationContext(), "Nombre de traduction depasser", Toast.LENGTH_SHORT);
                                toast.show();
                                break;

                            //si la connection echoue, on affiche un toast avec le code d'erreur
                            case 500:
                                btnPagePrincipale.setEnabled(false);
                                btnHistorique.setEnabled(false);

                                toast = Toast.makeText(getApplicationContext(), "Erreur temporaire de l'API DEEPL", Toast.LENGTH_SHORT);
                                toast.show();
                                break;

                            //par défaut, on affiche un toast avec le code d'erreur
                            default:
                                //on laisse les boutons désactiver
                                btnPagePrincipale.setEnabled(false);
                                btnHistorique.setEnabled(false);
                                //le toast affiche "La clef saisie est incorrect"
                                toast = Toast.makeText(getApplicationContext(), "La clef saisie est incorrect", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    //methode pour recuperer les données de l'API
    public void recupererDataAPI(){
        AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization", "DeepL-Auth-Key " + this.token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //on parcours le tableau de l'API
                            for (int i = 0; i < response.length(); i++) {
                                //on affiche les données dans les textView
                                idCaracUtiliser.setText(String.valueOf(response.getInt("character_count")));
                                idCaracTotal.setText(String.valueOf(response.getInt("character_limit")));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        try {
                            throw anError;
                        } catch (ANError e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }


}