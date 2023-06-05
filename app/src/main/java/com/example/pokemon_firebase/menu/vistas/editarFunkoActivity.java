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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class editarFunkoActivity extends AppCompatActivity {

    private EditText idFunkoUpdate;
    private EditText nombreFunkoUpdate;
    private EditText categoriaFunkoUpdate;
    private ImageView img_insertar_foto;


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
            Toast.makeText(editarFunkoActivity.this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(editarFunkoActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_funko);
        idFunkoUpdate = (EditText) findViewById(R.id.edtIdFunko2);
        nombreFunkoUpdate = (EditText) findViewById(R.id.edtNombreFunko2);
        categoriaFunkoUpdate = (EditText) findViewById(R.id.edtCategoriaFunko2);
        img_insertar_foto = (ImageView) findViewById(R.id.img_funkoUpdate2);
        mAuth = FirebaseAuth.getInstance();
    }

    public void actualizarFunko(View view) {
        String idFunko = String.valueOf(idFunkoUpdate.getText());
        String nombreFunko = String.valueOf(nombreFunkoUpdate.getText());
        String categoriaFunko = String.valueOf(categoriaFunkoUpdate.getText());
        //------------------------------- Validacion--------------------------------
        if(idFunko.isEmpty() || nombreFunko.isEmpty() || categoriaFunko.isEmpty()){
            Toast.makeText(editarFunkoActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(idFunko.isEmpty()){
            Toast.makeText(editarFunkoActivity.this, "Pon un id al Funko", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(nombreFunko.isEmpty()){
            Toast.makeText(editarFunkoActivity.this, "Pon un nombre al Funko", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(categoriaFunko.isEmpty()){
            Toast.makeText(editarFunkoActivity.this, "Pon una categoria al Funko", Toast.LENGTH_SHORT).show();
            return;
        }

        Funko f = new Funko(idFunko,nombreFunko,categoriaFunko);


        Toast.makeText(this,"Actualizacion correcta",Toast.LENGTH_LONG).show();
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
                img_insertar_foto.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}