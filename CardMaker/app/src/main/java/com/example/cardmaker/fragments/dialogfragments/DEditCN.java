package com.example.cardmaker.fragments.dialogfragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.cardmaker.R;

public class DEditCN extends AppCompatDialogFragment {

    private static final String TAG = "DEditCN";

    ProgressBar progressBar;
    EditText input_cn ;

    private DialogEditCNListener listener;



    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    /*public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }*/

    public interface DialogEditCNListener{
        void applyText(String input);
    }

    // Use this instance of the interface to deliver action events
    //NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (DialogEditCNListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement DialogEditCNListener");
        }
    }




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        //requireActivity()
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.edit_cn, null);

        builder.setView(view)


                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        // Send the positive button event back to the host activity
                        //listener.onDialogPositiveClick(DEditCN.this);

                        String inputText = input_cn.getText().toString();
                        Log.d(TAG, "onClick: DECN" + inputText);
                        listener.applyText(inputText);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //listener.onDialogNegativeClick(DEditCN.this);
                        //DEditCN.this.getDialog().cancel();
                    }
                });
        input_cn = view.findViewById(R.id.input_cn);
        //progressBar = view.findViewById(R.id.progressBar);

        // Create the AlertDialog object and return it
        return builder.create();

    }
}
