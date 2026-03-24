package com.example.campusfind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAddLost, btnAddFound, btnViewPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddLost = findViewById(R.id.btnAddLost);
        btnAddFound = findViewById(R.id.btnAddFound);
        btnViewPosts = findViewById(R.id.btnViewPosts);

        btnAddLost.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
            intent.putExtra("type", "Lost");
            startActivity(intent);
        });

        btnAddFound.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
            intent.putExtra("type", "Found");
            startActivity(intent);
        });

        btnViewPosts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewPostsActivity.class);
            startActivity(intent);
        });
    }
}