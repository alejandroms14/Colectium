package com.example.pokemon_firebase.menu.clases;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemon_firebase.menu.vistas.MostrarDetallesFunkoActivity;
import com.example.pokemon_firebase.R;

import java.util.List;

public class FunkoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public static final String EXTRA_OBJETO_Funko = "hola1";
    public static final String EXTRA_OBJETO_Funko_KEY = "adios1";
    public TextView txt_rv_nombreFunko;
    public TextView txt_rv_categoriaFunko;
    public TextView txt_rv_idFunko;
    public ImageView image_rv_imgFunko;
    ListaFunkoAdapter lcAdapter;

    public FunkoViewHolder(View itemView, ListaFunkoAdapter lcAdapter) {
        super(itemView);
        txt_rv_idFunko = (TextView) itemView.findViewById(R.id.txt_rv_Funko_idFunko);
        txt_rv_nombreFunko = (TextView) itemView.findViewById(R.id.txt_rv_Funko_nombre);
        txt_rv_categoriaFunko = (TextView) itemView.findViewById(R.id.txt_rv_Funko_categoria);
        image_rv_imgFunko = (ImageView) itemView.findViewById(R.id.img_rv_fotoFunko);

        this.lcAdapter = lcAdapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int mPosition = getAdapterPosition();
        List<Funko> funkos = this.lcAdapter.getListaFunko();
        List<String> keys = this.lcAdapter.getKeys();
        Funko funko = funkos.get(mPosition);
        String key = keys.get(mPosition);
        Intent intent = new Intent(lcAdapter.getC(), MostrarDetallesFunkoActivity.class);
        intent.putExtra(EXTRA_OBJETO_Funko, funko);
        intent.putExtra(EXTRA_OBJETO_Funko_KEY, key);
        lcAdapter.getC().startActivity(intent);
    }

}
