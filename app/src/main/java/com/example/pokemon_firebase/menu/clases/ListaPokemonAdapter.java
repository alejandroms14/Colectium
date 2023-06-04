package com.example.pokemon_firebase.menu.clases;

import static com.example.pokemon_firebase.menu.utilidades.ImagenesBlobBitmap.decodeSampledBitmapFrombyteArray;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemon_firebase.R;
import com.example.pokemon_firebase.menu.utilidades.ImageFirebase;

import java.util.ArrayList;
import java.util.List;

public class ListaPokemonAdapter extends RecyclerView.Adapter<PokemonViewHolder> {


    private Context c;
    private List<Pokemon> listaPokemon;
    private List<String> keys;
    private LayoutInflater mInflater;


    public void setC(Context c) {
        this.c = c;
        this.listaPokemon = new ArrayList<Pokemon>();
    }
    public ListaPokemonAdapter(Context c, List<Pokemon> listaPokemon,List<String> keys) {
        this.c = c;
        this.listaPokemon = listaPokemon;
        this.keys = keys;
        mInflater = LayoutInflater.from(c);
    }

    public Context getC() {
        return c;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<Pokemon> getListaPokemon() {
        return listaPokemon;
    }

    public void setListaPokemon(List<Pokemon> listaPokemon) {
        this.listaPokemon = listaPokemon;
        notifyDataSetChanged();
    }

    public ListaPokemonAdapter(Context c) {
        this.c = c;
        mInflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_recycleview_pokemon, parent, false);
        return new PokemonViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        if(listaPokemon!=null) {
            Pokemon pokemon_actual = listaPokemon.get(position);
            holder.txt_rv_idPokemon.setText("idPoke:" + pokemon_actual.getIdPokemon());
            holder.txt_rv_nombrePokemon.setText("Nombre: " + pokemon_actual.getNombrePokemon());
            holder.txt_rv_debilidadPokemon.setText("Debilidad: " + pokemon_actual.getDebilidad());
            if (pokemon_actual.getFoto() != null) {
                new ImageFirebase().descargarFoto(new ImageFirebase.FotoStatus() {
                    @Override
                    public void FotoIsDownload(byte[] bytes) {
                        if(bytes != null) {
                            Log.i("firebasedb","foto descargada correctamente");
                            Bitmap fotob = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                            holder.image_rv_imgPokemon.setImageBitmap(fotob);
                        }
                        else{
                            Log.i("firebasedb","foto no descargada correctamente");
                        }
                    }
                    @Override
                    public void FotoIsUpload() {
                    }
                    @Override
                    public void FotoIsDelete() {
                    }
                },pokemon_actual.getFoto());

            }
            else{
                // holder.img_rv_viaje_foto.setImageResource(R.drawable.foto_viaje);
                // holder.img_rv_viaje_foto.setBackgroundResource(R.drawable.foto_viaje);
            }
        }
    }



    @Override
    public int getItemCount() {
        if (listaPokemon != null)
            return listaPokemon.size();
        else return 0;
    }

}
