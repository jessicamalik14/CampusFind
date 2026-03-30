package com.example.campusfind;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    ArrayList<Post> postList;
    boolean showButtons; // 🔥 NEW (controls edit/delete visibility)

    public PostAdapter(Context context, ArrayList<Post> postList, boolean showButtons) {
        this.context = context;
        this.postList = postList;
        this.showButtons = showButtons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post = postList.get(position);

        holder.tvItemName.setText(post.getItemName());
        holder.tvType.setText(post.getType());
        holder.tvLocation.setText(post.getLocation());
        holder.tvDate.setText(post.getDate());

        DatabaseHelper db = new DatabaseHelper(context);

        // 🔥 SHOW / HIDE BUTTONS BASED ON SCREEN
        if (!showButtons) {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        } else {
            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);
        }

        // 🔴 DELETE BUTTON
        holder.btnDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Delete Post")
                    .setMessage("Are you sure you want to delete this post?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        int adapterPosition = holder.getAdapterPosition();

                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            db.deletePost(post.getId());

                            postList.remove(adapterPosition);
                            notifyItemRemoved(adapterPosition);
                            notifyItemRangeChanged(adapterPosition, postList.size());
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // ✏️ EDIT BUTTON
        holder.btnEdit.setOnClickListener(v -> {

            Intent intent = new Intent(context, AddPostActivity.class);

            intent.putExtra("id", post.getId());
            intent.putExtra("type", post.getType());
            intent.putExtra("itemName", post.getItemName());
            intent.putExtra("category", post.getCategory());
            intent.putExtra("location", post.getLocation());
            intent.putExtra("date", post.getDate());
            intent.putExtra("description", post.getDescription());
            intent.putExtra("contact", post.getContact());

            context.startActivity(intent);
        });

        // 👁️ VIEW DETAILS (CLICK CARD)
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName, tvType, tvLocation, tvDate;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvType = itemView.findViewById(R.id.tvType);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDate = itemView.findViewById(R.id.tvDate);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}