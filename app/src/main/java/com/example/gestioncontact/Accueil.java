package com.example.gestioncontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Accueil extends AppCompatActivity {

    private TextView tvusername;
    private Button btnAjout,btnaff,btnq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        tvusername=findViewById(R.id.tAcc);
        btnAjout=findViewById(R.id.btn_Ajout);
        btnaff=findViewById(R.id.btn_aff);
        btnq=findViewById(R.id.btn_quitter);

        Intent x=this.getIntent();
        Bundle b=x.getExtras();
        String u=b.getString("USER");
        tvusername.setText("Accueil de M."+u);

        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
                {
                    Intent i=new Intent(Accueil.this, Ajout.class);
                    startActivity(i);

                }
        });

        btnaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(Accueil.this, Affichage.class);
                startActivity(i);

            }
        });
        btnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accueil.this.finish();
            }
        });



    }
}