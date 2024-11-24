package com.example.gestioncontact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Noms des colonnes et de la table
    private static final String TABLE_NAME = "contacts";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "PSEUDO";
    private static final String COL_4 = "PHONE";

    // Constructeur
    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    // Méthode appelée lors de la création de la base de données
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " TEXT);";
        db.execSQL(createTable);
    }

    // Méthode appelée lors de la mise à jour de la base de données
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Méthode pour insérer un contact
    public boolean insertData(String name, String pseudo, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, pseudo);
        contentValues.put(COL_4, phone);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Retourne true si l'insertion a réussi
    }

    // Méthode pour mettre à jour un contact
    public boolean updateContact(String currentPhone, String newName, String newPseudo, String newPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, newName);
        contentValues.put(COL_3, newPseudo);
        contentValues.put(COL_4, newPhone);

        // Mise à jour basée sur le téléphone actuel
        int result = db.update(TABLE_NAME, contentValues, COL_4 + " = ?", new String[]{currentPhone});
        return result > 0; // Retourne true si la mise à jour a réussi
    }

    // Méthode pour récupérer tous les contacts
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Méthode pour supprimer un contact
    public Integer deleteContact(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_4 + " = ?", new String[]{phone}); // Suppression basée sur le numéro de téléphone
    }
}
