package com.example.codeflux;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button btnlogin;
    TextView signin;
    DBHelper DB;
    ImageView pol;

    private static final String PREF_LAST_VISITED_PAGE = "last_visited_page";
    private static final String DEFAULT_PAGE = "home_page";

    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        pol = (ImageView) findViewById(R.id.pol);

        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);
        btnlogin = (Button) findViewById(R.id.login);
        signin = findViewById(R.id.per_signup);
        DB = new DBHelper(this);



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                } else {
                    Boolean checkuserpass = DB.checkUsernamePassword(user, pass);
                    if (checkuserpass == true) {
                        username.setText("");
                        password.setText("");


                        // Show loading dialog
                        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Log in...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        // Simulate some background work before logging out
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // Dismiss the loading dialog
                                        progressDialog.dismiss();


                                        Toast.makeText(MainActivity.this, "Log in successfull", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                },
                                2000); // Delay for 2 seconds

                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Sign_up.class);
                startActivity(intent);
                finish();
            }
        });

//        pol.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialogExtra("Private Policy","");
//            }
//        });

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
}