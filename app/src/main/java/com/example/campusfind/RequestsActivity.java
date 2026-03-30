package com.example.campusfind;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper db;
    RequestAdapter adapter;
    ArrayList<Request> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        recyclerView = findViewById(R.id.recyclerViewRequests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);

        // TEMP DEMO MODE
        requestList = db.getAllRequests(); // we’ll add this next

        adapter = new RequestAdapter(this, requestList);
        recyclerView.setAdapter(adapter);
    }
}