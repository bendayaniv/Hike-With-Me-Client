package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hike_with_me_client.Adapters.RouteItemAdapter;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RouteItem;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RoutesListFragment;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Route.RouteMethods;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.SavedLastClick;

import java.util.ArrayList;

public class RoutesListFragment extends Fragment {

    ArrayList<Route> routes;
    private RecyclerView fragmentRoutesRV;
    private RouteItemAdapter routeItemAdapter;
    private Callback_RoutesListFragment callback_routesListFragment;

    public void setCallbackRoutesListFragment(Callback_RoutesListFragment callback_routesListFragment) {
        this.callback_routesListFragment = callback_routesListFragment;
    };

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

                            int lastPosition = SavedLastClick.getInstance().getPosition();
                            if(lastPosition != -1) {
                                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                fragmentRoutesRV.smoothScrollToPosition(lastPosition);
                                                Route route = SavedLastClick.getInstance().getLastClickedRoute();
                                                callback_routesListFragment.sendLocation(route.getLocation().getLatitude(), route.getLocation().getLongitude());
                                            }
                                        },
                                        5000);
                            }

                            routeItemAdapter.setCallbackRouteItem(new Callback_RouteItem() {
                                @Override
                                public void itemClicked(Route route, int position) {
                                    int lastPosition = SavedLastClick.getInstance().getPosition();
                                    SavedLastClick.getInstance().setLastClickedRoute(route);
                                    if(lastPosition != position) {
                                        Log.d("RoutesListFragment", "itemClicked: different position - " + position);
                                        SavedLastClick.getInstance().setPosition(position);
                                        if (callback_routesListFragment != null) {
                                            callback_routesListFragment.sendLocation(route.getLocation().getLatitude(), route.getLocation().getLongitude());
                                        }
                                    } else {
                                        Log.d("RoutesListFragment", "itemClicked: same position");
                                        callback_routesListFragment.goToRoutePage(route);
                                    }
                                }
                            });
                        }
                    }
                },
                100);
    }

    private void findViews(View view) {
        fragmentRoutesRV = view.findViewById(R.id.fragmentRoutesRV);
    }
}