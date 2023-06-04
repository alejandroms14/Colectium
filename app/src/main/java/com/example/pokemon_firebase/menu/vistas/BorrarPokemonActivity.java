package com.example.pokemon_firebase.menu.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokemon_firebase.R;
import com.example.pokemon_firebase.menu.clases.ListaPokemonAdapter;
import com.example.pokemon_firebase.menu.clases.Pokemon;
import com.example.pokemon_firebase.menu.controladores.PokemonFirebaseController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BorrarPokemonActivity extends AppCompatActivity {

    EditText edt_claveBorrarPokemon;
    private ListaPokemonAdapter mAdapter;
    private Pokemon pokemon1;
    private List<Pokemon> pokemons1;
    private List<String> keys1;
    private RecyclerView rv_pokemon = null;

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
            Toast.makeText(BorrarPokemonActivity.this, "Debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(BorrarPokemonActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_pokemon);
        edt_claveBorrarPokemon = findViewById(R.id.edt_claveBorrar);
        rv_pokemon = (RecyclerView) findViewById(R.id.rv_funkoMostrar);
        mAdapter = new ListaPokemonAdapter(this);
        mAuth = FirebaseAuth.getInstance();
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
        //----------------------------------------------------
        pokemons1 = new ArrayList<Pokemon>();
        keys1 = new ArrayList<String>();
        pokemon1 = new Pokemon();
        //----------------------------------------------------
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        String pokemonId = "-MxH3SqwoIiLF1MzTDg";
        leerPokemon(pokemonId, new MyPokemon() {
            @Override
            public void leerPokemon(Pokemon a) {
                pokemon1=a;
                Log.i("firebasedb", "El pokemon unico leido vale " + pokemon1);
            }
        });

        //----------------------------------------------------------------------------------

        leerpokemons(new MyPokemons() {
            @Override
            public void leerPokemons(List<String> keys, List<Pokemon> pokemons) {
                keys1 = keys;
                pokemons1 = pokemons;
                Log.i("firebasedb", "claves leidas");
                for(String k: keys1)
                {
                    Log.i("firebasedb", "clave leida " + k);
                    // txt_resultadofirebase.setText(String.valueOf(txt_resultadofirebase.getText())+" -> " + k);

                }
                Log.i("firebasedb", "usuarios leidos");
                for(Pokemon p: pokemons1)
                {
                    Log.i("firebasedb", "pokemons leido " + p.toString());
                    //    txt_resultadofirebase.setText(String.valueOf(txt_resultadofirebase.getText())+" -> " + a.toString());
                }
            }
        });

    }

    public void borrar_pokemon_firebase(View view) {
        String key = String.valueOf(edt_claveBorrarPokemon.getText());
        if(key.isEmpty()){
            Toast.makeText(BorrarPokemonActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Pokemon").child(key).removeValue();
        Toast.makeText(this,"Borrado correcto ", Toast.LENGTH_LONG).show();
    }

    //---------------------------------------------------------------------------------------------------
    public interface MyPokemon{
        void leerPokemon(Pokemon a);
    }

    public void leerPokemon(String key, MyPokemon myPokemons)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("pokemon").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.i("firebasedb", "Error getting data", task.getException());
                }
                else {
                    myPokemons.leerPokemon(task.getResult().getValue(Pokemon.class));
                }
            }
        });
    }

    //--------------------------------------------------------------------------------------
    public interface MyPokemons{
        void leerPokemons(List<String> keys, List<Pokemon> pokemons);
    }

    public void leerpokemons(MyPokemons misPokemons)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Pokemon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> keys = new ArrayList<String>();
                List<Pokemon> pokemons = new ArrayList<Pokemon>();
                for(DataSnapshot keynode: snapshot.getChildren()) {
                    keys.add(keynode.getKey());
                    pokemons.add(keynode.getValue(Pokemon.class));
                }
                Log.i("firebasedb", "claves leidas");
                for(String k: keys1)
                {
                    Log.i("firebasedb", "clave leida " + k);
                }
                Log.i("firebasedb", "usuarios leidos");
                for(Pokemon a: pokemons1)
                {
                    Log.i("firebasedb", "pokemon leido " + a.toString());
                }
                misPokemons.leerPokemons(keys,pokemons);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}