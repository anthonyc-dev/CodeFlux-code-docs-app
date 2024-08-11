package com.example.codeflux;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Objects;

public class user_profile extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private LinearLayout clear;
    private LinearLayout privatePo;
    private LinearLayout about;

    ImageView button, btn, imgView, coverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Set the title of the activity
        setTitle("Profile");

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(R.color.darkyellow)));

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        clear = findViewById(R.id.clear);
        privatePo = findViewById(R.id.privatePo);
        about = findViewById(R.id.about);

        button = findViewById(R.id.cambtn);

        Switch switchDarkMode = findViewById(R.id.darkModeSwitch);
        switchDarkMode.setChecked(sharedPreferences.getBoolean("darkMode", false));
        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleDarkMode(isChecked);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        privatePo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogExtra("Private Policy", "");
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogExtraOne("About", "");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
                Toast.makeText(user_profile.this, "Change successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        openGallery.launch(intent);

    }

    //change profile
    ActivityResultLauncher<Intent> openGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Uri uriImage = result.getData().getData();
                    imgView.setImageURI(uriImage);

                }
            });

    private void showLogoutConfirmationDialog() {
        String title = "Clear Cache";

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("Are you sure you want to Clear Cache?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Show loading dialog
                        final ProgressDialog progressDialog = new ProgressDialog(user_profile.this);
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        // Simulate some background work before logging out
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // Dismiss the loading dialog
                                        progressDialog.dismiss();

                                        // User clicked OK, so go to the login activity
                                        Toast.makeText(user_profile.this, "Cache has been cleared", Toast.LENGTH_SHORT).show();

                                    }
                                },
                                2000); // Delay for 2 seconds
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDialogOne(String message) {
        // Create AlertDialog.Builder and set the message
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Use getContext() in Fragment

        builder.setMessage(message)
                .setTitle("Clear Cache");

        // Add a button to dismiss the dialog
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button, dismiss the dialog
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialogExtraOne(String title, String... messages) {
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.custom_dialog_about, null);

        // Find the message container in the dialog layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout messageContainer = dialogView.findViewById(R.id.messageContainer);

        // Add a TextView for each message
        for (String message : messages) {
            TextView textView = new TextView(this);
            textView.setText(message);
            textView.setPadding(0, 0, 0, 16); // Optional: Add padding between messages
            messageContainer.addView(textView);
        }

        // Create AlertDialog.Builder and set the custom view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button, dismiss the dialog
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialogExtra(String title, String... messages) {
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_message_layout, null);

        // Find the message container in the dialog layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout messageContainer = dialogView.findViewById(R.id.messageContainer);

        // Add a TextView for each message
        for (String message : messages) {
            TextView textView = new TextView(this);
            textView.setText(message);
            textView.setPadding(0, 0, 0, 16); // Optional: Add padding between messages
            messageContainer.addView(textView);
        }

        // Create AlertDialog.Builder and set the custom view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Private Policy")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button, dismiss the dialog
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void toggleDarkMode(boolean enable) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("darkMode", enable);
        editor.apply();
        AppCompatDelegate.setDefaultNightMode(enable ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        recreate(); // Recreate the activity to apply the theme changes
    }
}
