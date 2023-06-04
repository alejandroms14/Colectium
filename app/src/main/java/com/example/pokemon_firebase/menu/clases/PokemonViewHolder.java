package com.example.pokemon_firebase.menu.clases;



import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemon_firebase.menu.vistas.MostrarDetallesPokemonActivity;
import com.example.pokemon_firebase.R;

import java.util.List;


public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public static final String EXTRA_OBJETO_POKEMON = "hola";
    public static final String EXTRA_OBJETO_POKEMON_KEY = "adios";
    public TextView txt_rv_nombrePokemon;
    public TextView txt_rv_debilidadPokemon;
    public TextView txt_rv_idPokemon;
    public ImageView image_rv_imgPokemon;
    ListaPokemonAdapter lcAdapter;

    public PokemonViewHolder(View itemView, ListaPokemonAdapter lcAdapter) {
        super(itemView);
        txt_rv_idPokemon = (TextView) itemView.findViewById(R.id.txt_rv_Funko_idFunko);
        txt_rv_nombrePokemon = (TextView) itemView.findViewById(R.id.txt_rv_Funko_nombre);
        txt_rv_debilidadPokemon = (TextView) itemView.findViewById(R.id.txt_rv_Funko_categoria);
        image_rv_imgPokemon = (ImageView) itemView.findViewById(R.id.img_rv_fotoFunko);

        this.lcAdapter = lcAdapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int mPosition = getAdapterPosition();
        List<Pokemon> pokemons = this.lcAdapter.getListaPokemon();
        List<String> keys = this.lcAdapter.getKeys();
        Pokemon pokemon = pokemons.get(mPosition);
        String key = keys.get(mPosition);
        Intent intent = new Intent(lcAdapter.getC(), MostrarDetallesPokemonActivity.class);
        intent.putExtra(EXTRA_OBJETO_POKEMON, pokemon);
        intent.putExtra(EXTRA_OBJETO_POKEMON_KEY, key);
        lcAdapter.getC().startActivity(intent);
    }
}
