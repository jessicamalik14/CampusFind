package com.example.campusfind;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    Context context;
    ArrayList<Post> postList;

    public PostAdapter(Context context, ArrayList<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.tvItemName.setText(post.getItemName());
        holder.tvType.setText("Type: " + post.getType());
        holder.tvLocation.setText("Location: " + post.getLocation());
        holder.tvDate.setText("Date: " + post.getDate());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("type", post.getType());
            intent.putExtra("itemName", post.getItemName());
            intent.putExtra("category", post.getCategory());
            intent.putExtra("location", post.getLocation());
            intent.putExtra("date", post.getDate());
            intent.putExtra("description", post.getDescription());
            intent.putExtra("contact", post.getContact());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvType, tvLocation, tvDate;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvType = itemView.findViewById(R.id.tvType);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}