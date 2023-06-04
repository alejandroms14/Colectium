package com.example.pokemon_firebase.menu.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pokemon_firebase.R;
import com.example.pokemon_firebase.menu.clases.ListaPokemonAdapter;
import com.example.pokemon_firebase.menu.clases.Pokemon;
import com.example.pokemon_firebase.menu.controladores.PokemonFirebaseController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class mostrarPokemonActivity extends AppCompatActivity {

    private RecyclerView rv_pokemon = null;
    private ListaPokemonAdapter mAdapter;
    private ArrayList<Pokemon> pokemons;
    private ArrayList<String> keys;
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
            Toast.makeText(mostrarPokemonActivity.this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(mostrarPokemonActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void salir(View view) {
        Intent intent = new Intent(mostrarPokemonActivity.this, menuPokemon.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_pokemon);
        mAuth = FirebaseAuth.getInstance();
        rv_pokemon = (RecyclerView) findViewById(R.id.rv_funkoMostrar);
        mAdapter = new ListaPokemonAdapter(this);
        new PokemonFirebaseController().obtener_pokemon(new PokemonFirebaseController.PokemonStatus() {
            @Override
            public void pokemonIsLoaded(List<Pokemon> pokemons, List<String> keys) {
                mAdapter.setListaPokemon(pokemons);
                mAdapter.setKeys(keys);
            }

            @Override
            public void pokemonIsAdd() {

            }

            @Override
            public void pokemonIsUpdate() {

            }

            @Override
            public void pokemonIsDelete() {

            }
        });


        //------------------------------------------------------------
        rv_pokemon.setAdapter(mAdapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv_pokemon.setLayoutManager(new LinearLayoutManager(this));
        } else {
            // rv_viajes.setLayoutManager(new GridLayoutManager(this, NUMERO_DE_COLUMNAS));
        }
        // rv_viajes.setLayoutManager(new LinearLayoutManager(this));
        //------------------------------------------------------------
    }

    //------------------------------------------------------------------
    public void addPokemon1(View view) {
        Intent intent = new Intent(this,addPokemonActivitty.class);
        startActivity(intent);
    }
}