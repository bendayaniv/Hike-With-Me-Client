package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.RouteFragment;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RoutesListFragment;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;

public class MainPageFragment extends Fragment {

    private FragmentManager fragmentManager;
    private MapsFragment mapsFragment;
    private RouteFragment routeFragment;
    private RoutesListFragment routesListFragment;

    Callback_RoutesListFragment callback_routesListFragment = new Callback_RoutesListFragment() {
        @Override
        public void sendLocation(double latitude, double longitude) {
            mapsFragment.zoom(latitude, longitude);
        }

        @Override
        public void goToRoutePage(Route route) {
            routeFragment.setRoute(route);
            fragmentManager.beginTransaction().replace(R.id.main_fragment_container, routeFragment).commit();
        }
    };

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        initFragments();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initFragments() {
        mapsFragment = new MapsFragment();
        routesListFragment = new RoutesListFragment();

        mapsFragment.setContext(getContext());
        routesListFragment.setCallbackRoutesListFragment(callback_routesListFragment);

        routeFragment = new RouteFragment();

//        mapsFragment.initiateMapViewModel();

        fragmentManager.beginTransaction().replace(R.id.mainPageMapFragment, mapsFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.mainPageRoutesListFragment, routesListFragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("MyMainPageFragment", "onPause");
        routesListFragment.onLeavingFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyMainPageFragment", "onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void onBeforeNavigateAway() {
//        routesListFragment.onLeavingFragment();
        mapsFragment.saveMapState();
    }
}