package com.example.codeflux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    ImageView getImageView;
    TextView tvGeTitle,tvGetDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getImageView = findViewById(R.id.getImageView);
        tvGeTitle = findViewById(R.id.tvGeTitle);
        tvGetDesc = findViewById(R.id.tvGetDesc);

        Intent intent = getIntent();

        String getHeader = intent.getStringExtra("title");
        String getDesc = intent.getStringExtra("desc");
        int getImgname = intent.getIntExtra("icon", 0);

        getImageView.setImageResource(getImgname);
        tvGeTitle.setText(getHeader);
        tvGetDesc.setText(getDesc);


    }
}