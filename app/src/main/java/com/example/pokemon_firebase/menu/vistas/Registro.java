package com.example.pokemon_firebase.menu.vistas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.pokemon_firebase.R;
import com.example.pokemon_firebase.menu.clases.Usuario;
import com.example.pokemon_firebase.menu.controladores.UsuarioController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {

    private EditText edt_dni, edt_nombre, edt_apellidos, edt_localidad, edt_direccion, edt_telefono;
    private FirebaseAuth mAuth;
    private String dni,nombre, apellidos, localidad, direccion;
    private int telefono;
    //metodo para cuando se quiere retroceder de pantalla
    @Override public void onBackPressed() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
        alerta.setMessage("Si vuelve a la pantalla de inicio sus datos no quedaran registrados")
                .setCancelable(true) //quita el mensaje si pulsas fuera del cuadro
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //borrar el usuario actual
                        FirebaseUser user = mAuth.getCurrentUser();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("firebasedb", "User account deleted.");
                                }
                            }
                        });
                        finish(); //cierra la pantalla actual
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("¿Esta seguro que desea volver atras?");
        titulo.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edt_dni = (EditText) (findViewById(R.id.edt_dni));
        edt_nombre = (EditText) (findViewById(R.id.edt_nombre));
        edt_apellidos = (EditText) (findViewById(R.id.edt_apellidos));
        edt_localidad = (EditText) (findViewById(R.id.edt_localidad));
        edt_direccion = (EditText) (findViewById(R.id.edt_direccion));
        edt_telefono = (EditText) (findViewById(R.id.edt_telefono));
        mAuth = FirebaseAuth.getInstance();
    }

    public void record(View view) {
        boolean errores = false;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String correo = String.valueOf(currentUser.getEmail());
        dni = String.valueOf(edt_dni.getText());
        nombre = String.valueOf(edt_nombre.getText());
        apellidos = String.valueOf(edt_apellidos.getText());
        localidad = String.valueOf(edt_localidad.getText());
        direccion = String.valueOf(edt_direccion.getText());
        try {
            telefono = Integer.valueOf(String.valueOf(edt_telefono.getText()));
        }catch (Exception e){
            telefono = 0;
        }

        Usuario usu = new Usuario(dni,nombre,apellidos,localidad,direccion,correo,telefono);

        if (dni.isEmpty()) {
            edt_dni.setError("Debes de escribir un dni");
            errores = true;
        }else if(nombre.isEmpty()){
            edt_nombre.setError("Debes de escribir un nombre");
            errores = true;
        }else if (apellidos.isEmpty()){
            edt_apellidos.setError("Debes de escribir los apellidos");
            errores = true;
        }else if(localidad.isEmpty()){
            edt_localidad.setError("Debes de escribir una localidad");
            errores = true;
        }else if(direccion.isEmpty()){
            edt_direccion.setError("Debes de escribir una direccion");
            errores = true;
        }else if(telefono == 0){
            edt_telefono.setError("Debes de escribir un numero de telefono");
            errores = true;
        }

        double num_tel = Math.floor(Math.log10(Math.abs(telefono)) + 1);
        if(dni.length() != 9){
            edt_dni.setError("Debes de escribir el dni correctamente");
            errores = true;
        } else if(num_tel != 9){
            edt_telefono.setError("Debes de escribir el numero de telefono correctamente");
            errores = true;
        }

        if (errores == false) {
            new UsuarioController().registerUsu(new UsuarioController.usuarioStatus() {
                @Override
                public void usuIsAdd() {
                    Toast.makeText(Registro.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registro.this, MenuPrinci.class);
                    startActivity(intent);
                }
            }, usu);
        }
    }
}