package com.bantczak.munchifyseller.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.bantczak.munchifyseller.R;

public class MessageDialog {

    static public void showDialog(final Context context, final String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing ...
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    static public void showDialog(final Context context, @StringRes final int resource) {
        showDialog(context, context.getString(resource));
    }
}
