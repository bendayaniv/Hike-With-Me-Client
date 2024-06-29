package com.example.hike_with_me_client.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_TripItem;
import com.example.hike_with_me_client.Models.Trip.Trip;
import com.example.hike_with_me_client.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.util.ArrayList;

public class TripItemAdapter extends RecyclerView.Adapter<TripItemAdapter.TripItemViewHolder> {

    private final ArrayList<Trip> trips;
    private Callback_TripItem callback_tripItem;

    public TripItemAdapter(ArrayList<Trip> trips) {
        this.trips = trips;
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

    @Override
    public void onBindViewHolder(@NonNull TripItemViewHolder holder, int position) {
        Trip trip = getItem(position);

        // TODO - need to give one image of the trip
        holder.card_background_image.setImageResource(R.drawable.ic_launcher_background);

        holder.name_text_view.setText(trip.getName());
        holder.start_date_text_view.setText(trip.getStartDate());
        holder.end_date_text_view.setText(trip.getEndDate());
    }

    @Override
    public int getItemCount() {
        return trips == null ? 0 : trips.size();
    }

    private Trip getItem(int position) {
        return trips.get(position);
    }

    public class TripItemViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView card_background_image;
        private final MaterialTextView name_text_view;
        private final MaterialTextView start_date_text_view;
        private final MaterialTextView end_date_text_view;

        public TripItemViewHolder(@NonNull View itemView) {
            super(itemView);

            card_background_image = itemView.findViewById(R.id.card_background_image);
            name_text_view = itemView.findViewById(R.id.name_text_view);
            start_date_text_view = itemView.findViewById(R.id.start_date_text_view);
            end_date_text_view = itemView.findViewById(R.id.end_date_text_view);

            // TODO - need to get all the images of the trip
            ArrayList<File> images = new ArrayList<>();

            itemView.setOnClickListener(v -> {
                if (callback_tripItem != null) {
                    callback_tripItem.itemClicked(trips.get(getAdapterPosition()), images, getAdapterPosition());
                }
            });
        }
    }

}
