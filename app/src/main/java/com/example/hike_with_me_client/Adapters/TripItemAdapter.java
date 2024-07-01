package com.example.hike_with_me_client.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_TripItem;
import com.example.hike_with_me_client.Models.Trip.Trip;
import com.example.hike_with_me_client.R;
import com.google.android.material.textview.MaterialTextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TripItemAdapter extends RecyclerView.Adapter<TripItemAdapter.TripItemViewHolder> {

    private final ArrayList<Trip> trips;
    private final Context context;
    private Callback_TripItem callback_tripItem;

    public TripItemAdapter(ArrayList<Trip> trips, Context context) {
        this.trips = trips;
        this.context = context;
    }

    public void setCallbackTripItem(Callback_TripItem callback_tripItem) {
        this.callback_tripItem = callback_tripItem;
    }

    @NonNull
    @Override
    public TripItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list_item, parent, false);
        return new TripItemViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull TripItemViewHolder holder, int position) {
        Trip trip = getItem(position);

        if (trip.getImagesUrls() != null && !trip.getImagesUrls().isEmpty()) {
            Glide.with(context).load(trip.getImagesUrls().get(0)).into(holder.card_background_image);

//            String name = getFileNameFromUrl(trip.getImagesUrls().get(0));

        } else {
            holder.card_background_image.setVisibility(View.GONE);
        }
        holder.name_text_view.setText(trip.getName());
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

    private Trip getItem(int position) {
        return trips.get(position);
    }

    public class TripItemViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView card_background_image;
        private final MaterialTextView name_text_view;

        public TripItemViewHolder(@NonNull View itemView) {
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

}
