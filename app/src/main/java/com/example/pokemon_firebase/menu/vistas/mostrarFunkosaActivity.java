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
import com.example.pokemon_firebase.menu.clases.Funko;
import com.example.pokemon_firebase.menu.clases.ListaFunkoAdapter;
import com.example.pokemon_firebase.menu.controladores.FunkoFirebaseController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class mostrarFunkosaActivity extends AppCompatActivity {


    private RecyclerView rv_funko = null;
    private ListaFunkoAdapter mAdapter;
    private ArrayList<Funko> Funkos;
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
            Toast.makeText( mostrarFunkosaActivity.this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent( mostrarFunkosaActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void salir(View view) {
        Intent intent = new Intent(mostrarFunkosaActivity.this, MenuFunkos.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_funkosa);

        mAuth = FirebaseAuth.getInstance();
        rv_funko = (RecyclerView) findViewById(R.id.rv_funkoMostrar);
        mAdapter = new ListaFunkoAdapter(this);
        new FunkoFirebaseController().obtener_funko(new FunkoFirebaseController.FunkoStatus() {
            @Override
            public void funkoIsLoaded(List<Funko> funkos, List<String> keys) {
                mAdapter.setListaFunko(funkos);
                mAdapter.setKeys(keys);
            }

            @Override
            public void funkoIsAdd() {

            }

            @Override
            public void funkoIsUpdate() {

            }

            @Override
            public void funkoIsDelete() {

            }


        });

        //------------------------------------------------------------
        rv_funko.setAdapter(mAdapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv_funko.setLayoutManager(new LinearLayoutManager(this));
        } else {
            // rv_viajes.setLayoutManager(new GridLayoutManager(this, NUMERO_DE_COLUMNAS));
        }
        // rv_viajes.setLayoutManager(new LinearLayoutManager(this));
        //------------------------------------------------------------

    }

    public void addFunko(View view) {
        Intent intent = new Intent(this,addMFunkosActivitty.class);
        startActivity(intent);
    }
}