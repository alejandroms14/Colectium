package com.example.pokemon_firebase.menu.vistas;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText edt_email = null;
    EditText edt_clave = null;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_email = (EditText) findViewById(R.id.edt_emailregistro);
        edt_clave = (EditText) findViewById(R.id.edt_passwordregistro);
        mAuth = FirebaseAuth.getInstance();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
    }



    public void login(View view) {
        String email = String.valueOf(edt_email.getText());
        String password = String.valueOf(edt_clave.getText());
        //------------------------------- Validacion--------------------------------
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(MainActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        if(email.isEmpty()){
            Toast.makeText(MainActivity.this, "Escriba un correo", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(password.isEmpty()){
            Toast.makeText(MainActivity.this, "Escriba una contraseña", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        else if(password.length() < 6){
            Toast.makeText(MainActivity.this, "Escriba una contraseña de mas de 6 caracteres", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("firebasedb", "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Iniciado sesion correctamente", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent intent = new Intent(MainActivity.this, MenuPrinci.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("firebasedb", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "No se pudo iniciar sesion", Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });

    }

    public void registro(View view) {
        Intent intent = new Intent(this, addEntrenador.class);
        startActivity(intent);

    }
}