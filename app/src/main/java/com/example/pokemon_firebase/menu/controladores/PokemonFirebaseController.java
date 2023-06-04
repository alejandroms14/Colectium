package com.example.pokemon_firebase.menu.controladores;

import android.util.Log;

import androidx.annotation.NonNull;

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

public class PokemonFirebaseController {

    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private List<Pokemon> pokemon;

    public interface PokemonStatus
    {
        void pokemonIsLoaded(List<Pokemon> pokemons, List<String> keys);
        void pokemonIsAdd();
        void pokemonIsUpdate();
        void pokemonIsDelete();
    }

    public PokemonFirebaseController() {
        this.mDatabase  = FirebaseDatabase.getInstance();
        this.myRef = mDatabase.getReference("Pokemon");
        this.pokemon  = new ArrayList<Pokemon>();
    }

    public void obtener_pokemon(final PokemonStatus pokemonStatus)
    {
        this.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pokemon.clear();
                List<String> keys = new ArrayList<String>();
                for(DataSnapshot keynode: snapshot.getChildren())
                {
                    keys.add(keynode.getKey());
                    Pokemon p = keynode.getValue(Pokemon.class);
                    pokemon.add(p);
                }
                pokemonStatus.pokemonIsLoaded(pokemon,keys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //---------------------------------------------------------------------------------
    public void insertarPokemon(final PokemonStatus pokemonStatus, Pokemon p)
    {
        //this.myRef.child(String.valueOf(p.getIdPokemon())).setValue(p);
        this.myRef.child(p.getIdPokemon()).setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // si todo va bien
                pokemonStatus.pokemonIsAdd();
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
    public void borrarPokemon(final PokemonStatus pokemonStatus, String key)
    {
        this.myRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // si todo va bien
                pokemonStatus.pokemonIsDelete();
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
    public void actualizarPokemon(final PokemonStatus pokemonStatus, String key, Pokemon v)
    {
        Map<String, Object> nuevoPokemon = new HashMap<String,Object>();
        nuevoPokemon.put(key,v);
        myRef.updateChildren(nuevoPokemon).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // si todo va bien
                pokemonStatus.pokemonIsUpdate();
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
