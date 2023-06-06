package com.example.myapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.webkit.JavascriptInterface;

import androidx.appcompat.app.AlertDialog;


public class AndroidInterface {
    private Context context;
    public AndroidInterface(Context context) {
        this.context = context;
    }
        @JavascriptInterface
        public void onJQueryLoaded(boolean isJQueryLoaded) {
            if (isJQueryLoaded) {
                // jQuery is loaded
                showAlert("jQuery is working!");
            } else {
                // jQuery is not loaded
                showAlert("jQuery is not loaded.");
            }
        }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle("Alert")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    }