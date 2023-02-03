package com.milhet.translationapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.milhet.translationapp.R;

import java.util.ArrayList;

public class TraductionAdapter extends ArrayAdapter<Traduction> {


ArrayList<Traduction> historiquetraductions;
Context context;
int resource;


    public TraductionAdapter(Context context, int resource, ArrayList<Traduction> historiquetraductions) {

        super(context, resource, historiquetraductions);
        this.context = context;
        this.resource = resource;
        this.historiquetraductions = historiquetraductions;

    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        TextView textLangSource = view.findViewById(R.id.idLangueSource);
        TextView textLangTarget = view.findViewById(R.id.idLangueDetectee);
        TextView textSource = view.findViewById(R.id.idTextSource);
        TextView textTarget = view.findViewById(R.id.idTextTraduit);

        //getting the hero of the specified position
        Traduction traduction = historiquetraductions.get(position);
        textLangSource.setText(traduction.getLang_source());
        textLangTarget.setText(traduction.getLang_target());
        textSource.setText(traduction.getText_source());
        textTarget.setText(traduction.getText_target());

        return view;
    }

}
