package com.example.checked.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.checked.R;
import com.example.checked.infra.CheckListPersistence;
import com.example.checked.infra.ItemListPersistence;

public class ViewDialog {
    private EditText editTextTitle;
    private String title;
    private TextView ok;
    private TextView no;
    private TextView titleView;
    private TextView messageView;


    public Dialog showDialogNewTask(Activity activity, long idChecklist){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        editTextTitle = (EditText) dialog.findViewById(R.id.title);
        ok = (TextView) dialog.findViewById(R.id.btn_yes);
        no = (TextView) dialog.findViewById(R.id.btn_no);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ItemListPersistence(activity.getBaseContext())).createItemlist(editTextTitle.getText().toString(), idChecklist, false);
                dialog.dismiss();
            }
        });
        no.setOnClickListener((view -> {dialog.dismiss();}));

        dialog.show();

        return dialog;
    }

    public Dialog showDialogNewChecklist(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        editTextTitle = (EditText) dialog.findViewById(R.id.title);
        ok = (TextView) dialog.findViewById(R.id.btn_yes);
        no = (TextView) dialog.findViewById(R.id.btn_no);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new CheckListPersistence(editTextTitle.getContext())).createChecklist(editTextTitle.getText().toString(),false);
                dialog.dismiss();
            }
        });
        no.setOnClickListener((view -> {dialog.dismiss();}));

        dialog.show();

        return dialog;
    }

    public Dialog showMessageDialog(Activity activity, String title, String message){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_msg);

        titleView = (TextView) dialog.findViewById(R.id.txt_title);
        messageView = (TextView) dialog.findViewById(R.id.message);

        ok = (TextView) dialog.findViewById(R.id.btn_yes);

        titleView.setText(title);
        messageView.setText(message);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

        return dialog;
    }
}
