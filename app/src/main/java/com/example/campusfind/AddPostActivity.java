package com.example.campusfind;

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

        postType = getIntent().getStringExtra("type");
        if (postType == null) {
            postType = "Lost";
        }

        tvAddPostTitle.setText("Add " + postType + " Item");

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

            boolean inserted = databaseHelper.insertPost(postType, itemName, category,
                    location, date, description, contact);

            if (inserted) {
                Toast.makeText(this, postType + " item saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Snackbar.make(v, "Error saving post", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}