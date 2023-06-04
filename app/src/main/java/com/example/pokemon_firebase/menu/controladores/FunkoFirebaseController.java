package com.example.pokemon_firebase.menu.controladores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pokemon_firebase.menu.clases.Funko;
import com.example.pokemon_firebase.menu.clases.Pokemon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunkoFirebaseController {

    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private List<Funko> funko;

    public interface FunkoStatus
    {
        void funkoIsLoaded(List<Funko> funkos, List<String> keys);
        void funkoIsAdd();
        void funkoIsUpdate();
        void funkoIsDelete();
    }

    public FunkoFirebaseController() {
        this.mDatabase  = FirebaseDatabase.getInstance();
        this.myRef = mDatabase.getReference("Funko");
        this.funko  = new ArrayList<Funko>();
    }

    public void obtener_funko(final FunkoFirebaseController.FunkoStatus funkoStatus)
    {
        this.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                funko.clear();
                List<String> keys = new ArrayList<String>();
                for(DataSnapshot keynode: snapshot.getChildren())
                {
                    keys.add(keynode.getKey());
                    Funko f = keynode.getValue(Funko.class);
                    funko.add(f);
                }
                funkoStatus.funkoIsLoaded(funko,keys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //---------------------------------------------------------------------------------
    public void insertarFunko(final FunkoFirebaseController.FunkoStatus funkoStatus, Funko f)
    {
        //this.myRef.child(String.valueOf(p.getIdPokemon())).setValue(p);
        this.myRef.child(f.getIdFunko()).setValue(f).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // si todo va bien
                        funkoStatus.funkoIsAdd();
                        Log.i("firebasedb", "insercion correcta");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception p) {
                        // si hay un fallo
                        Log.i("firebasedb", "insercion incorrecta");
                    }
                });
    }
    //---------------------------------------------------------------------------------
    public void borrarFunko(final FunkoFirebaseController.FunkoStatus funkoStatus, String key)
    {
        this.myRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // si todo va bien
                        funkoStatus.funkoIsDelete();
                        Log.i("firebasedb", "borrado correcto");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // si hay un fallo
                        Log.i("firebasedb", "borrado incorrecto");
                    }
                });
    }
    //---------------------------------------------------------------------------------
    public void actualizarfunko(final FunkoFirebaseController.FunkoStatus funkoStatus, String key, Funko v)
    {
        Map<String, Object> nuevoFunko = new HashMap<String,Object>();
        nuevoFunko.put(key,v);
        myRef.updateChildren(nuevoFunko).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // si todo va bien
                        funkoStatus.funkoIsUpdate();
                        Log.i("firebasedb", "actualizacion correcta2");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // si hay un fallo
                        Log.i("firebasedb", "actualizacion incorrecta2");
                    }
                });
    }
    //---------------------------------------------------------------------------------
}
