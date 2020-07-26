package com.example.covidslotbooking;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String message ="●\t100% advance to be paid at the hospital before check in.\n" +
                "●\tPatients shall be transferred to hospital based on the medical condition subject to availability of foods.\n" +
                "●\tNo refund under any circumstances.\n" +
                "●\tAttenders not allowed.\n";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Terms And Conditions").setMessage(message).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
