package com.example.campusfind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    Context context;
    ArrayList<Request> list;
    DatabaseHelper db;

    public RequestAdapter(Context context, ArrayList<Request> list) {
        this.context = context;
        this.list = list;
        db = new DatabaseHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // ✅ FIXED HERE
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_request, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Request req = list.get(position);

        holder.tvMessage.setText(req.getMessage());
        holder.tvStatus.setText(req.getStatus());

        holder.btnAccept.setOnClickListener(v -> {
            db.updateRequestStatus(req.getId(), "accepted");
            holder.tvStatus.setText("accepted");
        });

        holder.btnReject.setOnClickListener(v -> {
            db.updateRequestStatus(req.getId(), "rejected");
            holder.tvStatus.setText("rejected");
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessage, tvStatus;
        Button btnAccept, btnReject;

        public ViewHolder(View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}