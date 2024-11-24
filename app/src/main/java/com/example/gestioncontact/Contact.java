package com.example.gestioncontact;

public class Contact {
    private String nom;
    private String pseudo;
    private String telephone;

    public Contact(String nom, String pseudo, String telephone) {
        this.nom = nom;
        this.pseudo = pseudo;
        this.telephone = telephone;
    }

    public String getNom() {
        return nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getTelephone() {
        return telephone;
    }
}
