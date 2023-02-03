package com.milhet.translationapp.models;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.milhet.translationapp.R;

import java.util.ArrayList;


/**
 * Classe permettant d'adapter un spinner en mettant une image et un texte
 * @author Milhet et Ghys
 */
public class SpinnerAdapter extends ArrayAdapter<Language> {
    
    final private Context context; //Permet de récupérer le contexte de l'activité
    final private ArrayList<Language> languages; //Permet de récupérer la liste des langues
    
    public SpinnerAdapter(@NonNull Context context, int resource, ArrayList<Language> languages) {
        super(context, resource, languages);
        this.languages = languages;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull android.view.ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull android.view.ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        //Permet d'avoit une image et un texte dans le spinner
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.sous_spinner, parent, false);

        //On vérifie que la position n'est pas nulle
        if (position != 0) {
            TextView nomLangue = row.findViewById(R.id.textLangue); //On récupère le widget texte
            ImageView flag = row.findViewById(R.id.imgFlag); //On récupère le widget image

            nomLangue.setText(this.languages.get(position).getName()); //On met le nom de la langue dans le widget texte

            Resources res = context.getResources(); //On récupère les ressources
            String drawableName = this.languages.get(position).getLanguage().toLowerCase(); //On récupère le nom de l'image
            int resId = res.getIdentifier(drawableName, "drawable", context.getPackageName()); //On récupère l'id de l'image
            Drawable drawable = res.getDrawable(resId); //On récupère l'image
            flag.setImageDrawable(drawable); //On met l'image dans le widget image
        }

        return row; //On retourne la vue de la ligne

    }


}
