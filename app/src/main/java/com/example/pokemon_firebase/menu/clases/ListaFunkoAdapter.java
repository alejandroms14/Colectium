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

public class ListaFunkoAdapter extends RecyclerView.Adapter<FunkoViewHolder>{

    private Context c;
    private List<Funko> listaFunko;
    private List<String> keys;
    private LayoutInflater mInflater;


    public void setC(Context c) {
        this.c = c;
        this.listaFunko = new ArrayList<Funko>();
    }
    public ListaFunkoAdapter(Context c, List<Funko> listaFunko,List<String> keys) {
        this.c = c;
        this.listaFunko = listaFunko;
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

    public List<Funko> getListaFunko() {
        return listaFunko;
    }

    public void setListaFunko(List<Funko> listaFunko) {
        this.listaFunko = listaFunko;
        notifyDataSetChanged();
    }

    public ListaFunkoAdapter(Context c) {
        this.c = c;
        mInflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public FunkoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_recycleview_funko, parent, false);
        return new FunkoViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FunkoViewHolder holder, int position) {
        if(listaFunko!=null) {
            Funko funko_actual = listaFunko.get(position);
            holder.txt_rv_idFunko.setText("idFunko:" + funko_actual.getIdFunko());
            holder.txt_rv_nombreFunko.setText("Nombre: " + funko_actual.getNombreFunko());
            holder.txt_rv_categoriaFunko.setText("Categoria: " + funko_actual.getCategoria());
            if (funko_actual.getFoto() != null) {
                new ImageFirebase().descargarFoto(new ImageFirebase.FotoStatus() {
                    @Override
                    public void FotoIsDownload(byte[] bytes) {
                        if(bytes != null) {
                            Log.i("firebasedb","foto descargada correctamente");
                            Bitmap fotob = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                            holder.image_rv_imgFunko.setImageBitmap(fotob);
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
                },funko_actual.getFoto());

            }
            else{
                // holder.img_rv_viaje_foto.setImageResource(R.drawable.foto_viaje);
                // holder.img_rv_viaje_foto.setBackgroundResource(R.drawable.foto_viaje);
            }
        }
    }


    @Override
    public int getItemCount() {
        if (listaFunko != null)
            return listaFunko.size();
        else return 0;
    }
}
