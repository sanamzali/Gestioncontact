package com.example.gestioncontact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //declaration des composantes
    private Button btnval;
    private Button btnqte;
    EditText ednom,edmp;
    private CheckBox checkBoxConnecter;  // Declare CheckBox
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "PrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ednom=findViewById((R.id.nomAuth));
        edmp=findViewById(R.id.prenomAuth);
        btnval=findViewById(R.id.btn_auth);
        btnqte=findViewById(R.id.btn_q);
        checkBoxConnecter=findViewById(R.id.checkR);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);


        // Check if preferences were saved before
        String savedUser = sharedPreferences.getString("user", null);
        if (savedUser != null) {
            Intent i = new Intent(MainActivity.this, Accueil.class);
            i.putExtra("USER", savedUser);
            startActivity(i);
            finish();  // Close the login activity
        }


        //ecouteur d'action
        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = ednom.getText().toString();
                String mp = edmp.getText().toString();
                if (nom.equalsIgnoreCase("sana") && mp.equals("000")) {
                    // Sauvegarder dans SharedPreferences si la case est cochée avant de changer d'activité
                    if (checkBoxConnecter.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USER", nom);
                        editor.apply();  // Sauvegarde immédiate
                    }
                    // Ensuite, changer d'activité
                    Intent i = new Intent(MainActivity.this, Accueil.class);
                    i.putExtra("USER", nom);
                    startActivity(i);
                    finish();  // Fermer l'activité actuelle
                } else {
                    Toast.makeText(MainActivity.this, "valeur non valide", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }}