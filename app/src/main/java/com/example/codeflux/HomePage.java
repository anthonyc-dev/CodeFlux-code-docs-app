package com.example.codeflux;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    VideoFragment userFragment = new VideoFragment();

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    View header;

    private Switch darkModeSwitch;
    private SharedPreferences sharedPreferences;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.main_parent);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //bottom navigation bar
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();

                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;
                } else if (itemId == R.id.vid) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, userFragment).commit();
                    return true;
                } else if (itemId == R.id.settings) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                    return true;
                }

                return false;
            }
        });

    }

    //Drawer navigation bar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int itemId = menuItem.getItemId();

        if (itemId == R.id.nav_home) {

            Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.library) {
            Intent intent = new Intent(getApplicationContext(), online_library_video.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.nav_share) {
            showBottomSheet();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (itemId == R.id.nav_about) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, new AboutFragment()).commit();
//            drawerLayout.closeDrawer(GravityCompat.START);
            showDialogExtraOne("About", "");
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (itemId == R.id.nav_logout) {

            showLogoutConfirmationDialog();

            return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLogoutConfirmationDialog() {
        String title = "Log out";

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Show loading dialog
                        final ProgressDialog progressDialog = new ProgressDialog(HomePage.this);
                        progressDialog.setMessage("Logging out...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        // Simulate some background work before logging out
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // Dismiss the loading dialog
                                        progressDialog.dismiss();

                                        // User clicked OK, so go to the login activity
                                        Intent intent = new Intent(HomePage.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish(); // Close the current activity
                                    }
                                },
                                2000); // Delay for 2 seconds
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    private void showBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HomePage.this);
        View view = LayoutInflater.from(HomePage.this).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(view);

        // Set click listeners for each item to dismiss the bottom sheet
        LinearLayout facebookItem = view.findViewById(R.id.fb);
        LinearLayout gmailItem = view.findViewById(R.id.gmail);
        LinearLayout instagramItem = view.findViewById(R.id.insta);
        LinearLayout whatsappItem = view.findViewById(R.id.what);

        View.OnClickListener itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = "";
                int id = v.getId();
                if (id == R.id.fb) {
                    packageName = "com.facebook.katana";
                } else if (id == R.id.gmail) {
                    packageName = "com.google.android.gm";
                } else if (id == R.id.insta) {
                    packageName = "com.instagram.android";
                } else if (id == R.id.what) {
                    packageName = "com.whatsapp";
                }

                shareApp(packageName);

                // Dismiss the bottom sheet
                bottomSheetDialog.dismiss();
            }
        };

        // Set the same click listener for all items
        facebookItem.setOnClickListener(itemClickListener);
        gmailItem.setOnClickListener(itemClickListener);
        instagramItem.setOnClickListener(itemClickListener);
        whatsappItem.setOnClickListener(itemClickListener);

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
//                Toast.makeText(HomePage.this, "Bottom sheet dismissed", Toast.LENGTH_SHORT).show();
            }
        });

        bottomSheetDialog.show();
    }
    private void shareApp(String packageName) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareMessage = "Check out this amazing app: file:///C:/Users/QHTF/Desktop/CodeFlux/app/build/outputs/apk/debug/app-debug.apk";
        intent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        if (!packageName.isEmpty()) {
            intent.setPackage(packageName);
        }

        try {
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "App not installed.", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}