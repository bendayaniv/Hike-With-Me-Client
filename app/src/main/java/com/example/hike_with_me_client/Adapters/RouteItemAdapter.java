package com.example.hike_with_me_client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RouteItem;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RouteItemAdapter extends RecyclerView.Adapter<RouteItemAdapter.RouteItemViewHolder> {

    private Context context;
    private ArrayList<Route> routes;
    private Callback_RouteItem callback_routeItem;

    public RouteItemAdapter(Context context, ArrayList<Route> routes) {
        this.context = context;
        this.routes = routes;
    }

    public RouteItemAdapter setCallbackRouteItem(Callback_RouteItem callback_routeItem) {
        this.callback_routeItem = callback_routeItem;
        return this;
    }

    @NonNull
    @Override
    public RouteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_list_item, parent, false);
        RouteItemViewHolder routeItemViewHolder = new RouteItemViewHolder(view);
        return routeItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RouteItemAdapter.RouteItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return routes == null ? 0 : routes.size();
    }

    private Route getItem(int position) {
        return routes.get(position);
    }

    public class RouteItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView route_IMG_image;
        private MaterialTextView route_LBL_name;
        public RouteItemViewHolder(@NonNull View itemView) {
            super(itemView);

            route_IMG_image = itemView.findViewById(R.id.route_IMG_image);
            route_LBL_name = itemView.findViewById(R.id.route_LBL_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback_routeItem != null) {
                        callback_routeItem.itemClicked(routes.get(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }
    }
}
