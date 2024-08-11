package com.example.codeflux;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    private LinearLayout clear;
    private LinearLayout privatePo;
    private LinearLayout about;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the title of the activity hosting the fragment
        requireActivity().setTitle("Settings");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        // Check if dark mode is enabled
        boolean isDarkModeEnabled = sharedPreferences.getBoolean("darkMode", false);
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        clear = view.findViewById(R.id.clear);
        privatePo = view.findViewById(R.id.privatePo);
        about = view.findViewById(R.id.about);


        Switch switchDarkMode = view.findViewById(R.id.darkModeSwitch);
        // Set switch state based on dark mode status
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
//                showDialogTwo("Privacy Policy","");
                showDialogExtra("Private Policy", "");
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogExtraOne("About", "");
            }
        });

        return view;
    }

    private void showLogoutConfirmationDialog() {
        String title = "Clear Cache";

        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage("Are you sure you want to Clear Cache?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Show loading dialog
                        final ProgressDialog progressDialog = new ProgressDialog(requireContext());
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
                                        Toast.makeText(requireContext(), "Cache has been cleared", Toast.LENGTH_SHORT).show();

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); // Use getContext() in Fragment

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
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.custom_dialog_about, null);

        // Find the message container in the dialog layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout messageContainer = dialogView.findViewById(R.id.messageContainer);

        // Add a TextView for each message
        for (String message : messages) {
            TextView textView = new TextView(getContext());
            textView.setText(message);
            textView.setPadding(0, 0, 0, 16); // Optional: Add padding between messages
            messageContainer.addView(textView);
        }

        // Create AlertDialog.Builder and set the custom view
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_message_layout, null);

        // Find the message container in the dialog layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout messageContainer = dialogView.findViewById(R.id.messageContainer);

        // Add a TextView for each message
        for (String message : messages) {
            TextView textView = new TextView(getContext());
            textView.setText(message);
            textView.setPadding(0, 0, 0, 16); // Optional: Add padding between messages
            messageContainer.addView(textView);
        }

        // Create AlertDialog.Builder and set the custom view
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    // Method to toggle dark mode
    public void toggleDarkMode(boolean enable) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("darkMode", enable);
        editor.apply();
        // Update the dark mode setting without recreating the activity
        AppCompatDelegate.setDefaultNightMode(enable ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        // Apply the changes without recreating the activity
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }
}
