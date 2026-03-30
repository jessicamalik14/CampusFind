package com.example.campusfind;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPostsActivity extends AppCompatActivity {

    RecyclerView recyclerViewPosts;
    TextView tvEmpty; // 🔥 NEW (empty state)
    DatabaseHelper databaseHelper;
    ArrayList<Post> postList;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        tvEmpty = findViewById(R.id.tvEmpty); // 🔥 NEW

        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        postList = new ArrayList<>();

        // 🔥 Create adapter ONCE
        postAdapter = new PostAdapter(this, postList, true);
        recyclerViewPosts.setAdapter(postAdapter);

        loadMyPosts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMyPosts(); // 🔥 refresh
    }

    private void loadMyPosts() {

        postList.clear();
        postList.addAll(databaseHelper.getMyPosts());

        postAdapter.notifyDataSetChanged();

        // 🔥 SHOW EMPTY MESSAGE IF NO POSTS
        if (postList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerViewPosts.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerViewPosts.setVisibility(View.VISIBLE);
        }
    }
}