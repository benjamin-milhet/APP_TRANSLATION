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


public class SpinnerAdapter extends ArrayAdapter<Language> {
    
    final private Context context;
    final private ArrayList<Language> languages;
    
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

        System.out.println("position : " + position);
        System.out.println("languages : " + languages);
        System.out.println("languages.get(position) : " + languages.get(position));
        System.out.println("languages.get(position).getName() : " + languages.get(position).getName());
        System.out.println("languages.get(position).getLanguage() : " + languages.get(position).getLanguage());

        if (false) {
            TextView nomLangue = row.findViewById(R.id.textLangue);
            ImageView flag = row.findViewById(R.id.imgFlag);

            nomLangue.setText(this.languages.get(position).getName());

            Resources res = context.getResources();
            String drawableName = this.languages.get(position).getLanguage().toLowerCase();
            int resId = res.getIdentifier(drawableName, "drawable", context.getPackageName());
            Drawable drawable = res.getDrawable(resId);
            flag.setImageDrawable(drawable);
        }

        return row;

    }


}
