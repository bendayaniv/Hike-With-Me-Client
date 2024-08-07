package com.example.hike_with_me_client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RouteItem;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RouteItemAdapter extends RecyclerView.Adapter<RouteItemAdapter.RouteItemViewHolder> {

    private final Context context;
    private final ArrayList<Route> routes;
    private Callback_RouteItem callback_routeItem;

    public RouteItemAdapter(Context context, ArrayList<Route> routes) {
        this.context = context;
        this.routes = routes;
    }

    public void setCallbackRouteItem(Callback_RouteItem callback_routeItem) {
        this.callback_routeItem = callback_routeItem;
    }

    @NonNull
    @Override
    public RouteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_list_item, parent, false);
        return new RouteItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteItemAdapter.RouteItemViewHolder holder, int position) {
        Route route = getItem(position);
        holder.route_LBL_name.setText(route.getName());
        String imageUrl = route.getImageUrl();
        if (imageUrl != null) {
            Glide.with(context).load(imageUrl).into(holder.route_IMG_image);
        } else {
            holder.route_IMG_image.setImageResource(R.drawable.temporary_img);
        }
    }

    @Override
    public int getItemCount() {
        return routes == null ? 0 : routes.size();
    }

    private Route getItem(int position) {
        return routes.get(position);
    }

    public class RouteItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView route_IMG_image;
        private final MaterialTextView route_LBL_name;

        public RouteItemViewHolder(@NonNull View itemView) {
            super(itemView);

            route_IMG_image = itemView.findViewById(R.id.route_IMG_image);
            route_LBL_name = itemView.findViewById(R.id.route_LBL_name);

            itemView.setOnClickListener(v -> {
                if (callback_routeItem != null) {
                    callback_routeItem.itemClicked(routes.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
