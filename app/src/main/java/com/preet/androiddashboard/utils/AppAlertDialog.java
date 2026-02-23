package com.preet.androiddashboard.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.preet.androiddashboard.R;

import androidx.appcompat.app.AlertDialog;

public class AppAlertDialog {

    public static void showMessage(Context context, String title, String message) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton("OK", null).setCancelable(false).show();
    }

    public interface OnPositiveClickListener {
        void onClick();
    }

    public static void showLogoutDialog(Context context, OnPositiveClickListener listener) {

        new AlertDialog.Builder(context)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    if (listener != null) {
                        listener.onClick();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

    public static void showConfirmDialog(Context context, String title, String message, String positiveText, String negativeText, OnPositiveClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, (dialog, which) -> {
                    dialog.dismiss();
                    if (listener != null) {
                        listener.onClick();
                    }
                })
                .setNegativeButton(negativeText, (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }


   /* public static void showExitDialog(Context context, OnPositiveClickListener listener) {

        new AlertDialog.Builder(context)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    if (listener != null) {
                        listener.onClick();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }*/


    public static void showExitDialog(Activity activity, OnPositiveClickListener listener) {

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView btnYes = dialog.findViewById(R.id.btnYes);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onClick();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}

