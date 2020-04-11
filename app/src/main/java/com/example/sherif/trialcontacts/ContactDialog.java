package com.example.sherif.trialcontacts;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by Sherif on 6/26/2018.
 */

public class ContactDialog extends DialogFragment {
    private LayoutInflater inflater;
    private EditText name, number;
    private CheckBox checkBox;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(Contact result);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //referencing the builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //initializing the dialog view
        inflater = getActivity().getLayoutInflater();
        View fragmentView = inflater.inflate(R.layout.contact_info_dialog, null);
        checkBox = fragmentView.findViewById(R.id.cBox_phone);
        name = fragmentView.findViewById(R.id.eTxt_name);
        number = fragmentView.findViewById(R.id.eTxt_number);

        builder.setView(fragmentView)
                .setTitle("Add a new contact")
                .setPositiveButton(R.string.add_contact, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(new Contact(name.getText().toString(), number.getText().toString(), checkBox.isChecked()));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ContactDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
