package com.example.codeflux;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Sign_up extends AppCompatActivity {

    EditText username, password, repassword;
    Button signup;
    TextView signin;
    DBHelper DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.userSignup);
        password = (EditText) findViewById(R.id.passSignup);
        repassword = (EditText) findViewById(R.id.repassSignup);
        signup = (Button) findViewById(R.id.signup);
        signin = findViewById(R.id.btnsignin);
        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(Sign_up.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                    repassword.setText("");
                } else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkUsername(user);
                        if (checkuser == false) {
                            Boolean insert = DB.insertData(user, pass);
                            if (insert == true) {
                                Toast.makeText(Sign_up.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                username.setText("");
                                password.setText("");
                                repassword.setText("");
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Sign_up.this, "Your username and password most be 7 length", Toast.LENGTH_SHORT).show();
                                username.setText("");
                                password.setText("");
                                repassword.setText("");
                            }
                        } else {
                            Toast.makeText(Sign_up.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                            username.setText("");
                            password.setText("");
                            repassword.setText("");
                        }
                    } else {
                        Toast.makeText(Sign_up.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        repassword.setText("");
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}