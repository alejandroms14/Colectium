package com.example.pokemon_firebase.menu.vistas;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.pokemon_firebase.menu.clases.PokemonViewHolder.EXTRA_OBJETO_POKEMON;
import static com.example.pokemon_firebase.menu.clases.PokemonViewHolder.EXTRA_OBJETO_POKEMON_KEY;
import static com.example.pokemon_firebase.menu.utilidades.ImagenesBlobBitmap.decodeSampledBitmapFrombyteArray;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pokemon_firebase.R;
import com.example.pokemon_firebase.menu.clases.Configuracion;
import com.example.pokemon_firebase.menu.clases.Pokemon;
import com.example.pokemon_firebase.menu.controladores.PokemonFirebaseController;
import com.example.pokemon_firebase.menu.utilidades.ImageFirebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MostrarDetallesPokemonActivity extends AppCompatActivity {

    private EditText edt_detalle_idPokemon;
    private EditText edt_detalle_nombrePokemon;
    private EditText edt_detalle_ataquePokemon;
    private EditText edt_detalle_defensaPokemon;
    private EditText edt_detalle_debilidadPokemon;
    private ImageView img_detalle_fotoPokemon;

    private String key;
    private Pokemon p;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_detalles_pokemon);
        edt_detalle_idPokemon = (EditText) findViewById(R.id.edt_detalle_idPokemon);
        edt_detalle_nombrePokemon = (EditText) findViewById(R.id.edt_detalle_nombrePokemon);
        edt_detalle_ataquePokemon = (EditText) findViewById(R.id.edt_detalle_ataquePokemon);
        edt_detalle_defensaPokemon = (EditText) findViewById(R.id.edt_detalle_defensaPokemon);
        edt_detalle_debilidadPokemon = (EditText) findViewById(R.id.edt_detalle_debilidadPokemon);
        img_detalle_fotoPokemon = (ImageView) findViewById(R.id.img_detalle_fotoPokemon);
        Intent intent = getIntent();
        if (intent != null) {
            p = (Pokemon) intent.getSerializableExtra(EXTRA_OBJETO_POKEMON);
            key = intent.getStringExtra(EXTRA_OBJETO_POKEMON_KEY);
            edt_detalle_idPokemon.setText(String.valueOf(p.getIdPokemon()));
            edt_detalle_nombrePokemon.setText(p.getNombrePokemon());
            edt_detalle_ataquePokemon.setText(String.valueOf(p.getAtaque()));
            edt_detalle_defensaPokemon.setText(String.valueOf(p.getDefensa()));
            edt_detalle_debilidadPokemon.setText(String.valueOf(p.getDebilidad()));
            if (p.getFoto() != null) {
                new ImageFirebase().descargarFoto(new ImageFirebase.FotoStatus() {
                    @Override
                    public void FotoIsDownload(byte[] bytes) {
                        if(bytes != null) {
                            Log.i("firebasedb","foto descargada correctamente");
                            Bitmap fotob = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                            img_detalle_fotoPokemon.setImageBitmap(fotob);
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
                },p.getFoto());

            }
            else{

            }
        }


    }

    //-----------------------------------------------------------------------------------------------

    public void borrar_Pokemon(View view) {

        new PokemonFirebaseController().borrarPokemon(new PokemonFirebaseController.PokemonStatus() {
            @Override
            public void pokemonIsLoaded(List<Pokemon> pokemons, List<String> keys) {

            }

            @Override
            public void pokemonIsAdd() {

            }

            @Override
            public void pokemonIsUpdate() {

            }

            @Override
            public void pokemonIsDelete() {
                Toast.makeText(MostrarDetallesPokemonActivity.this, "borrado correcto", Toast.LENGTH_LONG).show();
                finish();
            }


        }, key);
        /*new ImageFirebase().borrarFoto(new ImageFirebase.FotoStatus() {
            @Override
            public void FotoIsDownload(byte[] bytes) {
            }
            @Override
            public void FotoIsDelete() {
            }
            @Override
            public void FotoIsUpload() {
                Toast.makeText(MostrarDetallesPokemonActivity.this, "foto eliminada correcto", Toast.LENGTH_LONG).show();
            }
        },p.getFoto());*/

    }
    //-----------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------
    public void Actualizar_pokemon(View view) {
        String idPokemonDetallePokemon = String.valueOf(edt_detalle_idPokemon.getText());
        String nombreDetallePokemon = String.valueOf(edt_detalle_nombrePokemon.getText());
        String ataqueDetallePokemon = String.valueOf(edt_detalle_ataquePokemon.getText());
        String defensaDetallePokemon = String.valueOf(edt_detalle_defensaPokemon.getText());
        String debilidadDetallePokemon = String.valueOf(edt_detalle_debilidadPokemon.getText());

        //------------------------------- Validacion--------------------------------
        if(idPokemonDetallePokemon.isEmpty()){
            Toast.makeText(MostrarDetallesPokemonActivity.this, "Pon un id de Pokemon", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(nombreDetallePokemon.isEmpty()){
            Toast.makeText(MostrarDetallesPokemonActivity.this, "Pon un nombre al Pokemon", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(ataqueDetallePokemon.isEmpty()){
            Toast.makeText(MostrarDetallesPokemonActivity.this, "Pon un ataque al Pokemon", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(defensaDetallePokemon.isEmpty()){
            Toast.makeText(MostrarDetallesPokemonActivity.this, "Pon una defensa al Pokemon", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(debilidadDetallePokemon.isEmpty()){
            Toast.makeText(MostrarDetallesPokemonActivity.this, "Pon una debilidad al Pokemon", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }

        Pokemon p = new Pokemon(idPokemonDetallePokemon,nombreDetallePokemon,ataqueDetallePokemon,defensaDetallePokemon,debilidadDetallePokemon);
        new PokemonFirebaseController().actualizarPokemon(new PokemonFirebaseController.PokemonStatus() {
            @Override
            public void pokemonIsLoaded(List<com.example.pokemon_firebase.menu.clases.Pokemon> pokemons, List<String> keys) {

            }

            @Override
            public void pokemonIsAdd() {

            }

            @Override
            public void pokemonIsUpdate() {

            }

            @Override
            public void pokemonIsDelete() {
                // aquí hay que poner cuando se haya actualizado bien qué hacer
                Toast.makeText(MostrarDetallesPokemonActivity.this,"actualizacion correcta",Toast.LENGTH_LONG).show();
                finish();
            }
        },key,p);
    }

    public void salirDetalle(View view) {
        Intent intent = new Intent(this, menuPokemon.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }
    //-----------------------------------------------------------------------------------------------

}
