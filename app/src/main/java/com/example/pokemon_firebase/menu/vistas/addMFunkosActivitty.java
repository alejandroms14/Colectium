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
import com.example.pokemon_firebase.menu.clases.Funko;
import com.example.pokemon_firebase.menu.controladores.FunkoFirebaseController;
import com.example.pokemon_firebase.menu.utilidades.ImageFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;

public class addMFunkosActivitty extends AppCompatActivity {

    EditText idFunkoRegistro;
    EditText nombreFunkoRegistro;
    EditText categoriaFunkoRegistro;
    ImageView img_insertar_foto;


    Funko f;
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
            Toast.makeText(addMFunkosActivitty.this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(addMFunkosActivitty.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mfunkos_activitty);
        idFunkoRegistro = (EditText) findViewById(R.id.edtIdFunko);
        nombreFunkoRegistro = (EditText) findViewById(R.id.edtNombreFunko);
        categoriaFunkoRegistro = (EditText) findViewById(R.id.edtCategoriaFunko);
        img_insertar_foto = (ImageView) findViewById(R.id.img_funkoUpdate);
        mAuth = FirebaseAuth.getInstance();
    }

    public void crearFunko(View view) {
        String idFunko = String.valueOf(idFunkoRegistro.getText());
        String nombreFunko = String.valueOf(nombreFunkoRegistro.getText());
        String categoriaFunko = String.valueOf(categoriaFunkoRegistro.getText());
        if(idFunko.isEmpty() || nombreFunko.isEmpty() || categoriaFunko.isEmpty()){
            Toast.makeText(addMFunkosActivitty.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }

        if(img_insertar_foto != null)
        {
            String email =  mAuth.getCurrentUser().getEmail();
            new ImageFirebase().subirFoto(new ImageFirebase.FotoStatus() {
                @Override
                public void FotoIsDownload(byte[] bytes) {
                }
                @Override
                public void FotoIsDelete() {
                }
                @Override
                public void FotoIsUpload() {
                    Toast.makeText(addMFunkosActivitty.this,"foto subida correcta",Toast.LENGTH_LONG).show();
                }
            },email, idFunko, img_insertar_foto);

            f = new Funko(idFunko,nombreFunko,categoriaFunko,email+"/"+String.valueOf(idFunko)+".png");
        }
        else{
            f = new Funko(idFunko,nombreFunko,categoriaFunko,null);
        }
        new FunkoFirebaseController().insertarFunko(new FunkoFirebaseController.FunkoStatus() {
            @Override
            public void funkoIsLoaded(List<Funko> pokemon, List<String> keys) {

            }

            @Override
            public void funkoIsAdd() {

            }

            @Override
            public void funkoIsUpdate() {

            }

            @Override
            public void funkoIsDelete() {

            }
        },f);


    }


    public void cambiarImagenFunko(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
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
}