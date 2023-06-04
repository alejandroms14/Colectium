package com.example.pokemon_firebase.menu.controladores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pokemon_firebase.menu.clases.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UsuarioController {
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;


    public interface usuarioStatus
    {
        void usuIsAdd();
    }

    public UsuarioController() {
        this.mDatabase  = FirebaseDatabase.getInstance();
        this.myRef = mDatabase.getReference("Usuario");
    }

    public void registerUsu(final usuarioStatus cs, Usuario u) {
        this.myRef.push().setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // si todo va bien
                cs.usuIsAdd();
                Log.i("firebasedb", "usuario insertada correctamente");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // si hay un fallo
                        Log.i("firebasedb", "Error al insertar el usuario");
                    }
                });
    }
}
