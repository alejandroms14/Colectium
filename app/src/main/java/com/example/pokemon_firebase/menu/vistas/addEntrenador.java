package com.example.pokemon_firebase.menu.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokemon_firebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class addEntrenador extends AppCompatActivity {

    EditText edt_correoRegistro = null;
    EditText edt_claveRegistro = null;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entrenador);
        edt_correoRegistro = (EditText) findViewById(R.id.edt_correoRegistro);
        edt_claveRegistro= (EditText) findViewById(R.id.edt_claveRegistro);
        mAuth = FirebaseAuth.getInstance();

    }

    public void addEntrenador(View view) {
        String email = String.valueOf(edt_correoRegistro.getText());
        String password = String.valueOf(edt_claveRegistro.getText());
        //Comprueba si los campos estan vacios
        if(email.isEmpty() || password.isEmpty() || password.length() < 6){
            Toast.makeText(addEntrenador.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("firebasedb", "createUserWithEmail:success");
                            Toast.makeText(addEntrenador.this, "!Enorabuena¡ Ya te has registrado", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                            Intent intent = new Intent(addEntrenador.this, Registro.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("firebasedb", "createUserWithEmail:failure", task.getException());
                            AlertDialog.Builder alerta = new AlertDialog.Builder(addEntrenador.this);
                            alerta.setMessage("Debe asegurarse de que la contraseña contenga mas de 6 caracteres ." +
                                            " Si aun asi todavia persiste el error, entonces compruebe si existe el correo.")
                                    .setCancelable(true); //quita el mensaje si pulsas fuera del cuadro
                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("Error");
                            titulo.show(); //muestra una alerta si hay un error
                        }
                    }
                });
    }
}