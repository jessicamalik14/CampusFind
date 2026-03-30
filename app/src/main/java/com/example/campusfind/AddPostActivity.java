package com.example.campusfind;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class AddPostActivity extends AppCompatActivity {

    TextView tvAddPostTitle;
    EditText etItemName, etCategory, etLocation, etDate, etDescription, etContact;
    Button btnSavePost;
    DatabaseHelper databaseHelper;
    String postType;

    int postId = -1; // 🔥 for edit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        tvAddPostTitle = findViewById(R.id.tvAddPostTitle);
        etItemName = findViewById(R.id.etItemName);
        etCategory = findViewById(R.id.etCategory);
        etLocation = findViewById(R.id.etLocation);
        etDate = findViewById(R.id.etDate);
        etDescription = findViewById(R.id.etDescription);
        etContact = findViewById(R.id.etContact);
        btnSavePost = findViewById(R.id.btnSavePost);

        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();

        postType = intent.getStringExtra("type");
        if (postType == null) {
            postType = "Lost";
        }

        // 🔥 EDIT MODE
        if (intent.hasExtra("id")) {

            postId = intent.getIntExtra("id", -1);

            etItemName.setText(intent.getStringExtra("itemName"));
            etCategory.setText(intent.getStringExtra("category"));
            etLocation.setText(intent.getStringExtra("location"));
            etDate.setText(intent.getStringExtra("date"));
            etDescription.setText(intent.getStringExtra("description"));
            etContact.setText(intent.getStringExtra("contact"));

            tvAddPostTitle.setText("Edit Post");
            btnSavePost.setText("Update Post");

        } else {
            tvAddPostTitle.setText("Add " + postType + " Item");
        }

        btnSavePost.setOnClickListener(v -> {

            String itemName = etItemName.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String contact = etContact.getText().toString().trim();

            if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(category) ||
                    TextUtils.isEmpty(location) || TextUtils.isEmpty(date) ||
                    TextUtils.isEmpty(description) || TextUtils.isEmpty(contact)) {

                Snackbar.make(v, "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // 🔥 UPDATE MODE
            if (postId != -1) {

                Post post = new Post(
                        postId,
                        postType,
                        itemName,
                        category,
                        location,
                        date,
                        description,
                        contact
                );

                databaseHelper.updatePost(post);

                Toast.makeText(this, "Post updated successfully", Toast.LENGTH_SHORT).show();

            } else {

                // 🔥 ADD MODE (NOW SAVES AS "MY POST")
                boolean inserted = databaseHelper.insertPost(
                        postType,
                        itemName,
                        category,
                        location,
                        date,
                        description,
                        contact,
                        1 // ✅ THIS IS THE NEW FEATURE (MY POST)
                );

                if (inserted) {
                    Toast.makeText(this, postType + " item saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(v, "Error saving post", Snackbar.LENGTH_SHORT).show();
                    return;
                }
            }

            finish(); // close screen
        });
    }
}