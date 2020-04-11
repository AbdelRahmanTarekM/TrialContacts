package com.example.sherif.trialcontacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sherif on 6/26/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contacts;
    private Context context;
    private LayoutInflater inflater;

    public ContactAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        View convertView = inflater.inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.contactName.setText(contact.getContactName());
        holder.contactNumber.setText(contact.getContactNumber());
        if (contact.isPhone()) holder.contactPicture.setImageResource(R.drawable.ic_action_phone);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView contactName, contactNumber;
        private ImageView contactPicture;

        public ContactViewHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.txt_contact_name);
            contactNumber = itemView.findViewById(R.id.txt_contact_number);
            contactPicture = itemView.findViewById(R.id.img_contact);
        }
    }
}
