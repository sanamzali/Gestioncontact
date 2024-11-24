package com.example.gestioncontact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyContactAdapter extends RecyclerView.Adapter<MyContactAdapter.ViewHolder> {
    private ArrayList<Contact> contactList;
    private DatabaseHelper databaseHelper;
    private Context context;

    public MyContactAdapter(Context context, ArrayList<Contact> contactList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.contactList = contactList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.tvNom.setText(contact.getNom());
        holder.tvPseudo.setText(contact.getPseudo());
        holder.tvTelephone.setText(contact.getTelephone());

        // Bouton Supprimer
        holder.btnDelete.setOnClickListener(v -> {
            databaseHelper.deleteContact(contact.getTelephone());  // Supprime le contact de la base de données
            contactList.remove(position);  // Supprime de la liste
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, contactList.size());
        });

        // Bouton Modifier
        holder.btnUpdate.setOnClickListener(v -> {
            if (context instanceof Affichage) { // Assurez-vous que le contexte est une instance d'Affichage
                Intent intent = new Intent(context, UpdateContactActivity.class);
                intent.putExtra("contactName", contact.getNom());
                intent.putExtra("contactPseudo", contact.getPseudo());
                intent.putExtra("contactPhone", contact.getTelephone());
                ((Affichage) context).startActivityForResult(intent, 1); // Utilisez startActivityForResult ici
            }
        });

        // Bouton Appeler
        holder.btnCall.setOnClickListener(v -> {
            if (context instanceof Affichage) {
                ((Affichage) context).handleCallPermission(contact.getTelephone());
            }

        });
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNom, tvPseudo, tvTelephone;
        ImageButton btnDelete, btnUpdate, btnCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tv_nom);
            tvPseudo = itemView.findViewById(R.id.tv_pseudo);
            tvTelephone = itemView.findViewById(R.id.tv_telephone);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnCall = itemView.findViewById(R.id.btn_call);
        }
    }
    // Method to update the list of contacts in the adapter
    public void filterList(ArrayList<Contact> filteredContacts) {
        this.contactList = filteredContacts;
        notifyDataSetChanged();  // Refresh the RecyclerView
    }
}
