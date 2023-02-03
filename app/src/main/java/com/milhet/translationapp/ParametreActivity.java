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

public class ParametreActivity extends AppCompatActivity {

    String token;
    SharedPreferences preferencesFile;
    ImageButton btnPagePrincipale;
    ImageButton btnHistorique;
    Button btnChargerClef;
    EditText tokenEditText;
    TextView idCaracUtiliser;
    TextView idCaracTotal;

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

        if (this.token.equals("-1")) {
            this.btnPagePrincipale.setEnabled(false);
            this.btnHistorique.setEnabled(false);
        } else {
            this.btnPagePrincipale.setEnabled(true);
            this.btnHistorique.setEnabled(true);

            tokenEditText.setText(this.token);
        }

        this.btnPagePrincipale.setOnClickListener(view -> {
            Intent intent = new Intent(ParametreActivity.this, MainActivity.class);
            ParametreActivity.this.startActivity(intent);
        });

        this.btnHistorique.setOnClickListener(view -> {
            Intent intent = new Intent(ParametreActivity.this, HistoriqueActivity.class);
            ParametreActivity.this.startActivity(intent);
        });

        this.btnChargerClef.setOnClickListener(view -> {

            if (this.token.equals("-1")) {
                this.btnPagePrincipale.setEnabled(false);
                this.btnHistorique.setEnabled(false);
            } else {
                this.btnPagePrincipale.setEnabled(true);
                this.btnHistorique.setEnabled(true);
            }

            this.verifierConnection(tokenEditText.getText().toString());
            this.recupererDataAPI();
        });

        this.verifierConnection(this.token);
        this.recupererDataAPI();
    }

    public void verifierConnection(String tokenTemp) {
        AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization", "DeepL-Auth-Key " + tokenTemp)
                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    Toast toast;
                    @Override
                    public void onResponse(Response response) {

                        switch (response.code()){
                            case 200:
                                SharedPreferences.Editor editor = preferencesFile.edit();
                                editor.putString("token", tokenTemp);
                                editor.apply();

                                token = preferencesFile.getString("token", "-1");

                                toast = Toast.makeText(getApplicationContext(), "Connexion reussi", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            case 404:
                                btnPagePrincipale.setEnabled(false);
                                btnHistorique.setEnabled(false);

                                toast = Toast.makeText(getApplicationContext(), "Page introuvable", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            case 429:
                                btnPagePrincipale.setEnabled(false);
                                btnHistorique.setEnabled(false);

                                toast = Toast.makeText(getApplicationContext(), "Trop de requete", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            case 456:
                                btnPagePrincipale.setEnabled(false);
                                btnHistorique.setEnabled(false);

                                toast = Toast.makeText(getApplicationContext(), "Nombre de traduction depasser", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            case 500:
                                btnPagePrincipale.setEnabled(false);
                                btnHistorique.setEnabled(false);

                                toast = Toast.makeText(getApplicationContext(), "Erreur temporaire de l'API DEEPL", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            default:
                                btnPagePrincipale.setEnabled(false);
                                btnHistorique.setEnabled(false);

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

    public void recupererDataAPI(){
        AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization", "DeepL-Auth-Key " + this.token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
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