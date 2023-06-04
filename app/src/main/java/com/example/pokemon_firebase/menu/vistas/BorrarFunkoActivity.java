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
import com.example.pokemon_firebase.menu.clases.Funko;
import com.example.pokemon_firebase.menu.clases.ListaFunkoAdapter;
import com.example.pokemon_firebase.menu.clases.Pokemon;
import com.example.pokemon_firebase.menu.controladores.FunkoFirebaseController;
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

public class BorrarFunkoActivity extends AppCompatActivity {

    EditText edt_claveBorrarFunko1;
    private ListaFunkoAdapter mAdapter;
    private Funko funko;
    private List<Funko> funkoList;
    private List<String> keys1;
    private RecyclerView rv_Funko = null;

    private FirebaseAuth mAuth;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(BorrarFunkoActivity.this, "Debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(BorrarFunkoActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_funko);
        edt_claveBorrarFunko1 = findViewById(R.id.edtClaveBorrarFunko);
        rv_Funko = (RecyclerView) findViewById(R.id.rv_mostrarFunkoBorrar);
        mAdapter = new ListaFunkoAdapter(this);
        mAuth = FirebaseAuth.getInstance();
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
        rv_Funko.setAdapter(mAdapter);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rv_Funko.setLayoutManager(new LinearLayoutManager(this));
        } else{

        }
        //------------------------------------------------------------
        //------------------------------------------------------------
        funkoList = new ArrayList<Funko>();
        keys1 = new ArrayList<String>();
        funko = new Funko();
        //-----------------------------------------------------------
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        String funkoId = "-MxH3SqwoIiLF1MzTDg";
        leerFunko(funkoId, new MyFunkos(){
            @Override
            public void leerFunko(Funko a) {
                funko=a;
                Log.i("firebasedb", "El funko unico leido vale " + funko);
            }

        });

        //----------------------------------------------------------------------------------

        leerfunkos1(new MyFukos1() {
            @Override
            public void leerFunkos1(List<String> Keys, List<Funko> funkos1) {
                keys1 = Keys;
                funkoList = funkos1;
                Log.i("firebasedb", "claves leidas");
                for (String k : keys1) {
                    Log.i("firebasedb", "clave leida " + k);
                    // txt_resultadofirebase.setText(String.valueOf(txt_resultadofirebase.getText())+" -> " + k);

                }
                Log.i("firebasedb", "usuarios leidos");
                for (Funko f : funkoList) {
                    Log.i("firebasedb", "pokemons leido " + f.toString());
                    //    txt_resultadofirebase.setText(String.valueOf(txt_resultadofirebase.getText())+" -> " + a.toString());
                }
            }
        });



    }
    public void borrar_funko_firebase(View view) {
        String key = String.valueOf(edt_claveBorrarFunko1.getText());
        if(key.isEmpty()){
            Toast.makeText(BorrarFunkoActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show(); //mensaje que se muestra al usuario
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Funko").child(key).removeValue();
        Toast.makeText(this,"Borrado correcto ", Toast.LENGTH_LONG).show();
    }

    public interface MyFunkos{
        void leerFunko(Funko a);
    }

    public void leerFunko(String key, MyFunkos myFunkos){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("funkos").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.i("firebasedb", "Error getting data", task.getException());
                }
                else{
                    myFunkos.leerFunko(task.getResult().getValue(Funko.class));
                }
            }
        });
    }
    //--------------------------------------------------------------------------------------

    public interface MyFukos1 {
        void leerFunkos1(List<String> Keys, List<Funko> funkos1);
    }
    public void leerfunkos1(MyFukos1 misFunkos1){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Funkos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> keys = new ArrayList<String>();
                List<Funko> funkos = new ArrayList<Funko>();
                for(DataSnapshot keynode: snapshot.getChildren()) {
                    keys.add(keynode.getKey());
                    funkos.add(keynode.getValue(Funko.class));
                }
                Log.i("firebasedb", "claves leidas");
                for(String k: keys1)
                {
                    Log.i("firebasedb", "clave leida " + k);
                }
                Log.i("firebasedb", "usuarios leidos");
                for(Funko a: funkos)
                {
                    Log.i("firebasedb", "pokemon leido " + a.toString());
                }
                misFunkos1.leerFunkos1(keys,funkos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }













}