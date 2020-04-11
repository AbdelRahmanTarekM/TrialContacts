package com.example.sherif.trialcontacts;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class ContactActivity extends AppCompatActivity implements ContactDialog.NoticeDialogListener {
    private static final String PREF_FILE = "MyPreferences";
    private RecyclerView contactRecyclerView;
    private FloatingActionButton addingFAB;
    private CardView errorCard;
    private ContactAdapter adapter;
    private ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //initializing views
        addingFAB = findViewById(R.id.add_fab);
        contactRecyclerView = findViewById(R.id.contact_recycler);
        errorCard = findViewById(R.id.error_card);

        //getting contacts from storage
        SharedPreferences prefs = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        String jsonText = prefs.getString("contactData", null);
        if (jsonText != null && !jsonText.matches("")) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = gson.fromJson(jsonText, listType);
            initializeAdapter();
        } else {
            errorCard.setVisibility(View.VISIBLE);
        }

        //event Handlers
        addingFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDialog();
            }
        });

        contactRecyclerView.addOnItemTouchListener(new RecylerClickListener(this, contactRecyclerView, new RecylerClickListener.ClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(ContactActivity.this, "Clicked " + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View v, int position) {
                Toast.makeText(ContactActivity.this, "LONG Clicked " + position, Toast.LENGTH_LONG).show();
            }
        }));
    }

    private void launchDialog() {
        ContactDialog dialog = new ContactDialog();
        dialog.show(getSupportFragmentManager(), "AddingContact");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Converting contact list to json
        // string then saving it.
        if (contacts.size() > 0) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String json = gson.toJson(contacts);
            SharedPreferences.Editor editor = getSharedPreferences(PREF_FILE, MODE_PRIVATE).edit();
            editor.putString("contactData", json);
            editor.apply();
        }

    }

    @Override
    public void onDialogPositiveClick(Contact result) {

        contacts.add(result);
        if (adapter == null) {
            initializeAdapter();
            contactRecyclerView.setVisibility(View.VISIBLE);
            errorCard.setVisibility(View.GONE);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void initializeAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ContactAdapter(contacts, this);
        contactRecyclerView.setLayoutManager(layoutManager);
        contactRecyclerView.setAdapter(adapter);
        contactRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(contactRecyclerView.getContext(),
                layoutManager.getOrientation());
        contactRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}
