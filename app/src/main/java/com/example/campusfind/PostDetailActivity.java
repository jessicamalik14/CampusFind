package com.example.campusfind;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostDetailActivity extends AppCompatActivity {

    TextView tvDetailType, tvDetailItem, tvDetailCategory, tvDetailLocation,
            tvDetailDate, tvDetailDescription, tvDetailContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        tvDetailType = findViewById(R.id.tvDetailType);
        tvDetailItem = findViewById(R.id.tvDetailItem);
        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailLocation = findViewById(R.id.tvDetailLocation);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        tvDetailContact = findViewById(R.id.tvDetailContact);

        tvDetailType.setText("Type: " + getIntent().getStringExtra("type"));
        tvDetailItem.setText("Item Name: " + getIntent().getStringExtra("itemName"));
        tvDetailCategory.setText("Category: " + getIntent().getStringExtra("category"));
        tvDetailLocation.setText("Location: " + getIntent().getStringExtra("location"));
        tvDetailDate.setText("Date: " + getIntent().getStringExtra("date"));
        tvDetailDescription.setText("Description: " + getIntent().getStringExtra("description"));
        tvDetailContact.setText("Contact: " + getIntent().getStringExtra("contact"));
    }
}