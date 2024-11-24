package com.example.gestioncontact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<String> contactsList;

    // Constructeur pour initialiser la liste des contacts
    public ContactsAdapter(List<String> contactsList) {
        this.contactsList = contactsList;
    }

    // Crée la vue pour chaque élément de la liste
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate le layout pour chaque élément
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    // Lie les données à la vue
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Récupère le contact à la position actuelle
        String contact = contactsList.get(position);
        // Définit le nom du contact dans le TextView
        holder.contactName.setText(contact);
    }

    // Retourne le nombre total d'éléments dans la liste
    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    // Classe ViewHolder pour détenir la vue d'un seul élément
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName;

        public ViewHolder(View itemView) {
            super(itemView);
            // Associe le TextView au layout simple_list_item_1
            contactName = itemView.findViewById(android.R.id.text1);
        }
    }
}
