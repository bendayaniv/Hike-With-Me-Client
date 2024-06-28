package com.example.hike_with_me_client.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_UserItem;
import com.example.hike_with_me_client.Models.Objects.UserWithDistance;

import com.example.hike_with_me_client.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.UserItemViewHolder> {

    private final ArrayList<UserWithDistance> users;
    private Callback_UserItem callback_userItem;

    public UserItemAdapter(ArrayList<UserWithDistance> users) {
        this.users = users;
    }

    public void setCallbackUserItem(Callback_UserItem callback_userItem) {
        this.callback_userItem = callback_userItem;
    }

    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_list_item, parent, false);
        return new UserItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserItemViewHolder holder, int position) {
        UserWithDistance user = getItem(position);
        holder.nameTextView.setText(user.getUser().getName());
        holder.hometownTextView.setText(user.getUser().getHometown());
        holder.distanceTextView.setText((int) user.getDistance() + "KM");
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    private UserWithDistance getItem(int position) {
        return users.get(position);
    }

    public class UserItemViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView nameTextView;
        private final MaterialTextView hometownTextView;
        private final MaterialTextView distanceTextView;

        public UserItemViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            hometownTextView = itemView.findViewById(R.id.hometownTextView);
            distanceTextView = itemView.findViewById(R.id.distanceTextView);

            itemView.setOnClickListener(v -> {
                if (callback_userItem != null) {
                    callback_userItem.itemClicked(users.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
