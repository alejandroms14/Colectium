package com.example.pokemon_firebase.menu.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pokemon_firebase.R;
import com.google.firebase.auth.FirebaseAuth;

public class menuPokemon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pokemon);
    }

    public void addPokemon(View view) {
        Intent intent = new Intent(menuPokemon.this, addPokemonActivitty.class);
        startActivity(intent);
    }

    public void editarPokemon(View view) {
        Intent intent = new Intent(menuPokemon.this, editarPokemonActivity.class);
        startActivity(intent);
    }

    public void borrarPokemon(View view) {
        Intent intent = new Intent(menuPokemon.this, BorrarPokemonActivity.class);
        startActivity(intent);
    }

    public void mostrarPokemon(View view) {
        Intent intent = new Intent(menuPokemon.this, mostrarPokemonActivity.class);
        startActivity(intent);
    }

    public void salirmenu(View view) {
        Intent intent = new Intent(menuPokemon.this, MainActivity.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }
}