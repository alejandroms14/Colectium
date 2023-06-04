package com.example.pokemon_firebase.menu.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pokemon_firebase.R;
import com.example.pokemon_firebase.menu.clases.Pokemon;
import com.example.pokemon_firebase.menu.controladores.PokemonFirebaseController;
import com.example.pokemon_firebase.menu.utilidades.ImageFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;

public class addPokemonActivitty extends AppCompatActivity {

    EditText edt_clavePokemonAdd;
    EditText idPokemonRegistro;
    EditText nombrePokemonRegistro;
    EditText ataquePokemonRegistro;
    EditText defensaPokemonRegistro;
    EditText debilidadPokemonReigistro;
    ImageView img_insertar_foto;


    Pokemon p;
    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_seleccionada = null;
    private FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(addPokemonActivitty.this, "Debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(addPokemonActivitty.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pokemon_activitty);
        //edt_clavePokemonAdd = (EditText) findViewById(R.id.edt_clavePokemonCrear);
        idPokemonRegistro = (EditText) findViewById(R.id.edt_idPokemonUpdate);
        nombrePokemonRegistro = (EditText) findViewById(R.id.edt_nombrePokemonUpdate);
        ataquePokemonRegistro = (EditText) findViewById(R.id.edt_ataqueRegistroUpdate);
        defensaPokemonRegistro = (EditText) findViewById(R.id.edt_defensaRegistroUpdate);
        debilidadPokemonReigistro = (EditText) findViewById(R.id.edt_debilidadPokemonUpdate);
        img_insertar_foto = (ImageView) findViewById(R.id.img_pokemonUpdate);
        mAuth = FirebaseAuth.getInstance();

    }

    //Con este metodo se crea los pokemon
    public void crearPokemon(View view) {
        boolean errores = false;
        String idPokemon = idPokemonRegistro.getText().toString();
        String nombrePokemon = String.valueOf(nombrePokemonRegistro.getText());
        String ataquePokemon = ataquePokemonRegistro.getText().toString();
        String defensaPokemon = ataquePokemonRegistro.getText().toString();
        String debilidadPokemon = String.valueOf(debilidadPokemonReigistro.getText());

        if(idPokemon.isEmpty() || nombrePokemon.isEmpty() || ataquePokemon.isEmpty() || defensaPokemon.isEmpty() ||debilidadPokemon.isEmpty()){
            Toast.makeText(addPokemonActivitty.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }

        //Se utiliza para insertar una foto
        if(img_insertar_foto != null) {
            String email = mAuth.getCurrentUser().getEmail();
            new ImageFirebase().subirFoto(new ImageFirebase.FotoStatus() {
                @Override
                public void FotoIsDownload(byte[] bytes) {
                }

                @Override
                public void FotoIsDelete() {
                }

                @Override
                public void FotoIsUpload() {
                    Toast.makeText(addPokemonActivitty.this, "foto subida correcta", Toast.LENGTH_LONG).show();
                }
            }, email, idPokemon, img_insertar_foto);

            p = new Pokemon(idPokemon, nombrePokemon, ataquePokemon, defensaPokemon, debilidadPokemon, email + "/" + String.valueOf(idPokemon) + ".png");
        }
        else{
            p = new Pokemon(idPokemon,nombrePokemon,ataquePokemon,defensaPokemon,debilidadPokemon,null);
        }
        new PokemonFirebaseController().insertarPokemon(new PokemonFirebaseController.PokemonStatus() {
            @Override
            public void pokemonIsLoaded(List<Pokemon> pokemon, List<String> keys) {

            }

            @Override
            public void pokemonIsAdd() {
                // aquí hay que poner cuando se haya insertado bien qué hacer
                Toast.makeText(addPokemonActivitty.this,"insercion correcta",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void pokemonIsUpdate() {

            }

            @Override
            public void pokemonIsDelete() {

            }
        },p);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK) {
            imagen_seleccionada = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagen_seleccionada);
                img_insertar_foto.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void cambiarImagenPokemon(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }
}