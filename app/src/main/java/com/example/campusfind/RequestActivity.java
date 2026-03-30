package com.example.campusfind;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RequestActivity extends AppCompatActivity {

    EditText etMessage;
    Button btnSend;
    DatabaseHelper db;
    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        db = new DatabaseHelper(this);

        // 🔥 Get post ID
        postId = getIntent().getIntExtra("postId", -1);

        btnSend.setOnClickListener(v -> {

            String message = etMessage.getText().toString().trim();

            if (TextUtils.isEmpty(message)) {
                etMessage.setError("Please describe your item");
                return;
            }

            // 🔥 FIX: include sender
            db.insertRequest(postId, message, "Jessica");

            Toast.makeText(this, "Request sent!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}