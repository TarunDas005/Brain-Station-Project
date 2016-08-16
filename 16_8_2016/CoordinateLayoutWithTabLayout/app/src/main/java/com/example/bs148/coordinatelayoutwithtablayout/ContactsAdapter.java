package com.example.bs148.coordinatelayoutwithtablayout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BS148 on 8/16/2016.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>{

    ArrayList<ContactsInformation> contactsInformations;

    public ContactsAdapter(ArrayList<ContactsInformation> contactsInformations) {
        this.contactsInformations = contactsInformations;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);
        ContactsViewHolder contactsViewHolder=new ContactsViewHolder(itemView);
        return contactsViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        ContactsInformation information=contactsInformations.get(position);
        holder.nameTextView.setText(information.getName());
        holder.phoneTextView.setText(information.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactsInformations.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView,phoneTextView;
        public ContactsViewHolder(View itemView) {
            super(itemView);
            nameTextView= (TextView) itemView.findViewById(R.id.nameTextView);
            phoneTextView=(TextView) itemView.findViewById(R.id.phoneTextView);
        }
    }
}
