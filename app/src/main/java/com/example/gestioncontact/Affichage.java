package com.example.gestioncontact;
import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Affichage extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private ArrayList<Contact> contactList;
    private MyContactAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Button btnAjoutList,btnLogout;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "PrefsFile";
    private String pendingPhoneNumber;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.id_search);
        databaseHelper = new DatabaseHelper(this);
        contactList = new ArrayList<>();
        btnAjoutList=findViewById(R.id.btnAjoutList);
        btnLogout=findViewById(R.id.btn_logout);
        adapter = new MyContactAdapter(this, contactList, databaseHelper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        sharedPreferences=getSharedPreferences(PREFS_NAME, MODE_PRIVATE);


        // Charger les contacts depuis la base de données
        loadContacts();

        // Set up SearchView listener for dynamic searching
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Optionally handle search submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter contacts as the user types
                filterContacts(newText);
                return true;
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Effacer les informations utilisateur de SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("USER");
                editor.apply();

                // Rediriger vers l'écran de connexion
                Intent intent = new Intent(Affichage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnAjoutList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(Affichage.this, Ajout.class);
                startActivity(i);

            }
        });
    }

    private void loadContacts() {
        Cursor cursor = databaseHelper.getAllData();
        contactList.clear(); // Vider la liste avant de la remplir
        if (cursor.getCount() == 0) {
            // Aucune donnée
        } else {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                String pseudo = cursor.getString(2);
                String phone = cursor.getString(3);
                contactList.add(new Contact(name, pseudo, phone));
            }
        }
        adapter.notifyDataSetChanged(); // Notifier l'adaptateur de la mise à jour
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts(); // Rechargez les contacts
    }

    // Method to filter contacts based on user input
    private void filterContacts(String query) {
        ArrayList<Contact> filteredContacts = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.getNom().toLowerCase().contains(query.toLowerCase()) ||
                    contact.getPseudo().toLowerCase().contains(query.toLowerCase()) ||
                    contact.getTelephone().contains(query)) {
                filteredContacts.add(contact);  // Add matching contacts to the filtered list
            }
        }

        adapter.filterList(filteredContacts);  // Update adapter with the filtered list
    }
    //
    public void handleCallPermission(String phoneNumber) {
        pendingPhoneNumber = phoneNumber;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            makePhoneCall(phoneNumber);
        }
    }

    private void makePhoneCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission a été accordée
                makePhoneCall(pendingPhoneNumber);
            } else {
                // Permission refusée
                Toast.makeText(this, "Permission refusée pour passer des appels", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
