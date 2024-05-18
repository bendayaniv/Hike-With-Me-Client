package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hike_with_me_client.Adapters.RouteItemAdapter;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RouteItem;
import com.example.hike_with_me_client.Models.Objects.RouteList;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Route.RouteMethods;
import com.example.hike_with_me_client.R;

import java.util.ArrayList;

public class RoutesListFragment extends Fragment {

    private RouteList routeList = new RouteList();

    ArrayList<Route> routes;
    private RecyclerView fragmentRoutesRV;
    private RouteItemAdapter routeItemAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes_list, container, false);

        routes = new ArrayList<>();

        findViews(view);

        initRouteRV();

        return view;
    }

    private void initRouteRV() {
        RouteMethods.getAllRoutes(routes);

        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        if(routes != null && routes.size() > 0) {
                            routeItemAdapter = new RouteItemAdapter(getContext(), routes);
                            fragmentRoutesRV.setLayoutManager(new LinearLayoutManager(getContext()));
                            fragmentRoutesRV.setAdapter(routeItemAdapter);

                            routeItemAdapter.setCallbackRouteItem(new Callback_RouteItem() {
                                @Override
                                public void itemClicked(Route route, int position) {

                                }
                            });
                        }
                    }
                },
                5000);

//        routeItemAdapter = new RouteItemAdapter(getContext(), routes);
//        fragmentRoutesRV.setLayoutManager(new LinearLayoutManager(getContext()));
//        fragmentRoutesRV.setAdapter(routeItemAdapter);
//
//        routeItemAdapter.setCallbackRouteItem(new Callback_RouteItem() {
//            @Override
//            public void itemClicked(Route route, int position) {
//
//            }
//        });
    }

    private void findViews(View view) {
        fragmentRoutesRV = view.findViewById(R.id.fragmentRoutesRV);
    }
}