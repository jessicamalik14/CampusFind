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
        Button btnMyPosts = findViewById(R.id.btnMyPosts);

        btnMyPosts.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MyPostsActivity.class));
        });

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

        Button btnRequests = findViewById(R.id.btnRequests);

        btnRequests.setOnClickListener(v -> {
            startActivity(new Intent(this, RequestsActivity.class));
        });

        DatabaseHelper db = new DatabaseHelper(this);

        // only insert once
        if (db.getAllPosts().size() == 0) {

            db.insertPost("Lost", "Wallet", "Wallet", "Library", "01/03/2026",
                    "Black leather wallet", "123456789", 0);

            db.insertPost("Found", "Keys", "Keys", "Cafeteria", "02/03/2026",
                    "Set of car keys", "987654321", 0);

            db.insertPost("Lost", "Laptop", "Electronics", "Room 101", "03/03/2026",
                    "Dell laptop missing", "111222333", 0);
        }
    }
}
