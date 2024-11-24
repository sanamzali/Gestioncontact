package com.example.gestioncontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateContactActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private EditText editName, editPseudo, editPhone;
    private Button updateButton;
    private String currentPhone; // Le téléphone actuel du contact pour la mise à jour


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        databaseHelper = new DatabaseHelper(this);
        editName = findViewById(R.id.et_nom);
        editPseudo = findViewById(R.id.et_pseudo);
        editPhone = findViewById(R.id.et_telephone);
        updateButton = findViewById(R.id.updateButton);

        // Récupérer les données du contact à partir de l'intent
        Intent intent = getIntent();
        currentPhone = intent.getStringExtra("contactPhone"); // Le téléphone actuel du contact
        editName.setText(intent.getStringExtra("contactName"));
        editPseudo.setText(intent.getStringExtra("contactPseudo"));
        editPhone.setText(currentPhone); // Remplir le champ téléphone

        updateButton.setOnClickListener(v -> {
            // Mettre à jour le contact dans la base de données
            String name = editName.getText().toString();
            String pseudo = editPseudo.getText().toString();
            String phone = editPhone.getText().toString();

            boolean isUpdated = databaseHelper.updateContact(currentPhone, name, pseudo, phone);

            // Retourner un résultat à l'activité d'affichage
            Intent returnIntent = new Intent();
            if (isUpdated) {
                returnIntent.putExtra("updatedPhone", phone); // Vous pouvez passer le nouveau téléphone
                setResult(RESULT_OK, returnIntent);
            } else {
                setResult(RESULT_CANCELED);
            }
            finish(); // Cela ferme l'activité
        });
    }

}
