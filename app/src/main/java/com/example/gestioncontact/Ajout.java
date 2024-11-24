package com.example.gestioncontact;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ajout extends AppCompatActivity {
    private EditText etNom, etPseudo, etTelephone;
    private Button btnAjouter, btnAfficher;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        etNom = findViewById(R.id.et_nom);
        etPseudo = findViewById(R.id.et_pseudo);
        etTelephone = findViewById(R.id.et_telephone);
        btnAjouter = findViewById(R.id.btn_ajouter);
        btnAfficher = findViewById(R.id.btn_afficher);
        databaseHelper = new DatabaseHelper(this);

        // Ajouter un contact dans la base
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = etNom.getText().toString();
                String pseudo = etPseudo.getText().toString();
                String telephone = etTelephone.getText().toString();

                if (nom.isEmpty() || pseudo.isEmpty() || telephone.isEmpty()) {
                    Toast.makeText(Ajout.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = databaseHelper.insertData(nom, pseudo, telephone);
                    if (isInserted) {
                        Toast.makeText(Ajout.this, "Contact ajouté", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Ajout.this, "Erreur d'ajout", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Bouton pour aller à l'activité d'affichage
        btnAfficher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ajout.this, Affichage.class);
                startActivity(intent);
            }
        });
    }
}
