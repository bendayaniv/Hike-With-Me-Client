package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hike_with_me_client.Adapters.RouteItemAdapter;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RoutesListFragment;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.ListOfRoutes;
import com.example.hike_with_me_client.Utils.SavedLastClick;
import com.example.hike_with_me_client.Utils.SharedViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class RoutesListFragment extends Fragment {

    ArrayList<Route> routes;
    private RecyclerView fragmentRoutesRV;
    private RouteItemAdapter routeItemAdapter;
    private Callback_RoutesListFragment callback_routesListFragment;
    private SharedViewModel sharedViewModel;

    public void setCallbackRoutesListFragment(Callback_RoutesListFragment callback_routesListFragment) {
        this.callback_routesListFragment = callback_routesListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes_list, container, false);

        routes = new ArrayList<>();

        findViews(view);

        initializing();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializing() {
        if (!ListOfRoutes.getInstance().getRoutes().isEmpty()) {
            initRouteRV();
        } else {
            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    this::initRouteRV,
                    10000);
        }
    }

    private void initRouteRV() {
        initAndScroll();

        setCallbackRouteItemForAdapter();
    }

    private void initAndScroll() {
        routes = ListOfRoutes.getInstance().getRoutes();
        routeItemAdapter = new RouteItemAdapter(getContext(), routes);
        fragmentRoutesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentRoutesRV.setAdapter(routeItemAdapter);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getRecyclerViewState().observe(getViewLifecycleOwner(), this::onChanged);
    }

    private void setCallbackRouteItemForAdapter() {
        routeItemAdapter.setCallbackRouteItem((route, position) -> {
            int lastPosition = SavedLastClick.getInstance().getPosition();
            SavedLastClick.getInstance().setLastClickedRoute(route);
            if (lastPosition != position) {
                Log.d("RoutesListFragment", "itemClicked: different position - " + position);
                SavedLastClick.getInstance().setPosition(position);
                if (callback_routesListFragment != null) {
                    callback_routesListFragment.sendLocation(route.getLocation().getLatitude(), route.getLocation().getLongitude());
                }
            } else {
                Log.d("RoutesListFragment", "itemClicked: same position");
                callback_routesListFragment.goToRoutePage(route);
            }
        });
    }

    public void onLeavingFragment() {
        Parcelable state = Objects.requireNonNull(fragmentRoutesRV.getLayoutManager()).onSaveInstanceState();
        sharedViewModel.setRecyclerViewState(state);
    }

    private void findViews(View view) {
        fragmentRoutesRV = view.findViewById(R.id.fragmentRoutesRV);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        onLeavingFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void onChanged(Parcelable state) {
        if (state != null) {
            Objects.requireNonNull(fragmentRoutesRV.getLayoutManager()).onRestoreInstanceState(state);
        }
    }
}