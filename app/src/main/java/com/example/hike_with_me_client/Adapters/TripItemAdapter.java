package com.example.hike_with_me_client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_TripItem;
import com.example.hike_with_me_client.Models.Trip.trip;
import com.example.hike_with_me_client.R;
import com.google.android.material.textview.MaterialTextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TripItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<trip> trips;
    private final Context context;
    private Callback_TripItem callback_tripItem;

    public TripItemAdapter(ArrayList<trip> trips, Context context) {
        this.trips = trips;
        this.context = context;
    }

    public void setCallbackTripItem(Callback_TripItem callback_tripItem) {
        this.callback_tripItem = callback_tripItem;
    }

    @Override
    public int getItemViewType(int position) {
        if (trips.get(position).getImagesUrls() != null && !trips.get(position).getImagesUrls().isEmpty()) {
            return 1;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list_item_without_image, parent, false);
            return new TripItemWithoutImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list_item_with_image, parent, false);
            return new TripItemWithImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            ((TripItemWithoutImageViewHolder) holder).name_text_view.setText(trips.get(position).getName());
        } else {
            trip trip = trips.get(position);
            if (trip.getImagesUrls() != null && !trip.getImagesUrls().isEmpty()) {
                Glide.with(context).load(trip.getImagesUrls().get(0)).into(((TripItemWithImageViewHolder) holder).card_background_image);
            }
            ((TripItemWithImageViewHolder) holder).name_text_view.setText(trip.getName());
        }
    }

    public String getFileNameFromUrl(String url) {
        try {
            URL uri = new URL(url);
            String path = uri.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return trips == null ? 0 : trips.size();
    }

    private trip getItem(int position) {
        return trips.get(position);
    }

    public class TripItemWithImageViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView card_background_image;
        private final MaterialTextView name_text_view;

        public TripItemWithImageViewHolder(@NonNull View itemView) {
            super(itemView);

            card_background_image = itemView.findViewById(R.id.card_background_image);
            name_text_view = itemView.findViewById(R.id.name_text_view);


            itemView.setOnClickListener(v -> {
                if (callback_tripItem != null) {
                    callback_tripItem.itemClicked(trips.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    public class TripItemWithoutImageViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView name_text_view;

        public TripItemWithoutImageViewHolder(@NonNull View itemView) {
            super(itemView);

            name_text_view = itemView.findViewById(R.id.name_text_view);

            itemView.setOnClickListener(v -> {
                if (callback_tripItem != null) {
                    callback_tripItem.itemClicked(trips.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
