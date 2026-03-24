package com.example.campusfind;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewPostsActivity extends AppCompatActivity {

    RecyclerView recyclerViewPosts;
    DatabaseHelper databaseHelper;
    ArrayList<Post> postList;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posts);

        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        postList = databaseHelper.getAllPosts();

        postAdapter = new PostAdapter(this, postList);
        recyclerViewPosts.setAdapter(postAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        postList = databaseHelper.getAllPosts();
        postAdapter = new PostAdapter(this, postList);
        recyclerViewPosts.setAdapter(postAdapter);
    }
}