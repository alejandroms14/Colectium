package com.example.pokemon_firebase.menu.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pokemon_firebase.R;
import com.google.firebase.auth.FirebaseAuth;

public class MenuFunkos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_funkos);
    }

    public void addFunkos(View view) {
        Intent intent = new Intent(MenuFunkos.this, addMFunkosActivitty.class);
        startActivity(intent);
    }

    public void editarFunko(View view) {
        Intent intent = new Intent(MenuFunkos.this, editarFunkoActivity.class);
        startActivity(intent);
    }

    public void borrarFunko(View view) {
        Intent intent = new Intent(MenuFunkos.this, BorrarFunkoActivity.class);
        startActivity(intent);
    }

    public void mostrarFunko(View view) {
        Intent intent = new Intent(MenuFunkos.this, mostrarFunkosaActivity.class);
        startActivity(intent);
    }

    public void salirmenu(View view) {
        Intent intent = new Intent(MenuFunkos.this, MainActivity.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }
}