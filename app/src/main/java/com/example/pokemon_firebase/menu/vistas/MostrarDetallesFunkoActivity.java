package com.example.pokemon_firebase.menu.vistas;

import static com.example.pokemon_firebase.menu.clases.FunkoViewHolder.EXTRA_OBJETO_Funko;
import static com.example.pokemon_firebase.menu.clases.FunkoViewHolder.EXTRA_OBJETO_Funko_KEY;
import static com.example.pokemon_firebase.menu.utilidades.ImagenesBlobBitmap.decodeSampledBitmapFrombyteArray;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.pokemon_firebase.menu.clases.Funko;


import com.example.pokemon_firebase.menu.controladores.FunkoFirebaseController;
import com.example.pokemon_firebase.menu.utilidades.ImageFirebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MostrarDetallesFunkoActivity extends AppCompatActivity {


    private EditText edt_detalle_idFunko;
    private EditText edt_detalle_nombreFunko1;
    private EditText edt_detalle_categoriaFunko;
    private ImageView img_detalle_fotoFunko;


    private String key;
    private Funko f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_detalles_funko);
        edt_detalle_idFunko = (EditText) findViewById(R.id.edt_detalle_idFunko);
        edt_detalle_nombreFunko1 = (EditText) findViewById(R.id.edt_detalle_nombre);
        edt_detalle_categoriaFunko = (EditText) findViewById(R.id.edt_detalle_categoria);
        img_detalle_fotoFunko = (ImageView) findViewById(R.id.img_detalle_funko);
        Intent intent = getIntent();
        if (intent != null) {
            f = (Funko) intent.getSerializableExtra(EXTRA_OBJETO_Funko);
            key = intent.getStringExtra(EXTRA_OBJETO_Funko_KEY);
            edt_detalle_idFunko.setText(String.valueOf(f.getIdFunko()));
            edt_detalle_nombreFunko1.setText(f.getNombreFunko());
            edt_detalle_categoriaFunko.setText(String.valueOf(f.getCategoria()));
            if (f.getFoto() != null) {
                new ImageFirebase().descargarFoto(new ImageFirebase.FotoStatus() {
                    @Override
                    public void FotoIsDownload(byte[] bytes) {
                        if (bytes != null) {
                            Log.i("firebasedb", "foto descargada correctamente");
                            Bitmap fotob = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                            img_detalle_fotoFunko.setImageBitmap(fotob);
                        } else {
                            Log.i("firebasedb", "foto no descargada correctamente");
                        }
                    }

                    @Override
                    public void FotoIsUpload() {
                    }

                    @Override
                    public void FotoIsDelete() {
                    }
                }, f.getFoto());
            }
            else{

            }
        }

    }

    //-----------------------------------------------------------------------------------------------

    public void borrar_Funko(View view) {

        new FunkoFirebaseController().borrarFunko(new FunkoFirebaseController.FunkoStatus() {
            @Override
            public void funkoIsLoaded(List<Funko> funkos, List<String> keys) {

            }

            @Override
            public void funkoIsAdd() {

            }

            @Override
            public void funkoIsUpdate() {

            }

            @Override
            public void funkoIsDelete() {
                Toast.makeText(MostrarDetallesFunkoActivity.this, "borrado correcto", Toast.LENGTH_LONG).show();
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
                Toast.makeText(MostrarDetallesFunkoActivity.this, "foto eliminada correcto", Toast.LENGTH_LONG).show();
            }
        },f.getFoto());*/

    }
    //-----------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------
    public void actualizarFunko(View view) {
        String idFunko = String.valueOf(edt_detalle_idFunko.getText());
        String nombreFunko = String.valueOf(edt_detalle_nombreFunko1.getText());
        String categoriaFunko = String.valueOf(edt_detalle_categoriaFunko.getText());
        //------------------------------- Validacion--------------------------------
        if(idFunko.isEmpty()){
            Toast.makeText(MostrarDetallesFunkoActivity.this, "Pon un id al Funko", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(nombreFunko.isEmpty()){
            Toast.makeText(MostrarDetallesFunkoActivity.this, "Pon un nombre al Funko", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(categoriaFunko.isEmpty()){
            Toast.makeText(MostrarDetallesFunkoActivity.this, "Pon una categoria al Funko", Toast.LENGTH_SHORT).show();
            return;
        }


        Funko f = new Funko(idFunko,nombreFunko,categoriaFunko);
        new FunkoFirebaseController().actualizarfunko(new FunkoFirebaseController.FunkoStatus() {
            @Override
            public void funkoIsLoaded(List<com.example.pokemon_firebase.menu.clases.Funko> funkos, List<String> keys) {

            }

            @Override
            public void funkoIsAdd() {

            }

            @Override
            public void funkoIsUpdate() {
                Toast.makeText(MostrarDetallesFunkoActivity.this, "actualizacion correcta", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void funkoIsDelete() {

            }
        },key,f);
    }
























    public void salirDetalle(View view) {
        Intent intent = new Intent(this, MenuFunkos.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }
    //-----------------------------------------------------------------------------------------------
}