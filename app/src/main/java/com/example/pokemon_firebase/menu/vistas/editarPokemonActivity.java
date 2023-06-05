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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class editarPokemonActivity extends AppCompatActivity {

    private EditText edt_clavePokemonUpdate;
    private EditText edt_nombrePokemonUpdate1;
    private EditText edt_ataquePokemonUpdate;
    private EditText edt_defensaPokemonUpdate;
    private EditText edt_debilidadPokemonUpdate;
    private ImageView img_imgPokemonUpdate;

    public static final int UPDATE_IMAGEN = 1;
    Uri imagen_updateseleccionada = null;

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
            Toast.makeText(editarPokemonActivity.this, "Debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(editarPokemonActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pokemon);
        edt_nombrePokemonUpdate1 = (EditText) findViewById(R.id.edt_nombrePokemonUpdate);
        edt_ataquePokemonUpdate = (EditText) findViewById(R.id.edt_ataqueRegistroUpdate);
        edt_defensaPokemonUpdate = (EditText) findViewById(R.id.edt_defensaRegistroUpdate);
        edt_debilidadPokemonUpdate = (EditText) findViewById(R.id.edt_debilidadPokemonUpdate);
        img_imgPokemonUpdate = (ImageView) findViewById(R.id.img_pokemonUpdate);
        mAuth = FirebaseAuth.getInstance();
    }

    public void actualizarPokemon(View view) {
        String idPokemon = String.valueOf(edt_clavePokemonUpdate.getText());
        String nombrePokemon = String.valueOf(edt_nombrePokemonUpdate1.getText());
        String ataquePokemon = String.valueOf(edt_ataquePokemonUpdate.getText());
        String defensaPokemon = String.valueOf(edt_defensaPokemonUpdate.getText());
        String debilidadPokemon = String.valueOf(edt_debilidadPokemonUpdate.getText());

        //------------------------------- Validacion--------------------------------
        if(idPokemon.isEmpty() || nombrePokemon.isEmpty() || ataquePokemon.isEmpty() || defensaPokemon.isEmpty() ||debilidadPokemon.isEmpty()){
            Toast.makeText(editarPokemonActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        if(idPokemon.isEmpty()){
            Toast.makeText(editarPokemonActivity.this, "Pon un id de Pokemon", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(nombrePokemon.isEmpty()){
            Toast.makeText(editarPokemonActivity.this, "Pon un nombre al Pokemon", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(ataquePokemon.isEmpty()){
            Toast.makeText(editarPokemonActivity.this, "Pon un ataque al Pokemon", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(defensaPokemon.isEmpty()){
            Toast.makeText(editarPokemonActivity.this, "Pon una defensa al Pokemon", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(debilidadPokemon.isEmpty()){
            Toast.makeText(editarPokemonActivity.this, "Pon una debilidad al Pokemon", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }

        Pokemon p = new Pokemon(nombrePokemon,ataquePokemon,defensaPokemon,debilidadPokemon);


        Toast.makeText(this,"actualizacion correcta",Toast.LENGTH_LONG).show();
    }

    public void updateImagen(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, UPDATE_IMAGEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_IMAGEN && resultCode == Activity.RESULT_OK) {
            imagen_updateseleccionada = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagen_updateseleccionada);
                img_imgPokemonUpdate.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}