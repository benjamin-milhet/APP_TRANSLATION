package com.milhet.translationapp;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< Updated upstream
=======

import android.widget.ArrayAdapter;
>>>>>>> Stashed changes
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< Updated upstream
=======
import com.androidnetworking.AndroidNetworking;

import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


>>>>>>> Stashed changes
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
        btnParametres.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ParametreActivity.class);
            MainActivity.this.startActivity(intent);
        });
<<<<<<< Updated upstream
=======

        token = "0da5a1b3-1637-fd73-e550-79b8954cf379:fx";
        loadLanguage();

    }

    public void loadLanguage() {
         Context that = this;
        AndroidNetworking.get("https://api-free.deepl.com/v2/languages?auth_key=" + token)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<String> languages = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject language = response.getJSONObject(i);
                                languages.add(language.getString("name"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(that, android.R.layout.simple_spinner_item, languages);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            Spinner spinner = findViewById(R.id.spinnerListeLangue);
                            spinner.setAdapter(adapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


>>>>>>> Stashed changes
    }
}