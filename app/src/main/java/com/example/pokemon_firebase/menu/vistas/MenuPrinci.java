package com.example.pokemon_firebase.menu.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokemon_firebase.R;
import com.example.pokemon_firebase.menu.mapa.activity_infApp;

public class MenuPrinci extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_princi);
    }

    public void menuPokemon(View view) {
        Intent intent = new Intent(this, menuPokemon.class);
        startActivity(intent);
    }

    public void menuFunkos(View view) {
        Intent intent = new Intent(this, MenuFunkos.class);
        startActivity(intent);
    }

    public void menuSalir(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void menuInfo(View view) {
        Intent intent = new Intent(this, activity_infApp.class);
        startActivity(intent);
    }
}