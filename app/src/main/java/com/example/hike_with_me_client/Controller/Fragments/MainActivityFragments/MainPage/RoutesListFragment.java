package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.hike_with_me_client.Adapters.RouteItemAdapter;
import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.RouteDetailsFragment;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RoutesListFragment;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ErrorMessageFromServer;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ListOfRoutes;
import com.example.hike_with_me_client.Utils.MainPageFragment.SavedLastClick;
import com.example.hike_with_me_client.Utils.MainPageFragment.SharedViewModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Objects;

public class RoutesListFragment extends Fragment {

    ArrayList<Route> routes;
    private RecyclerView fragmentRoutesRV;
    private RouteItemAdapter routeItemAdapter;
    private MaterialTextView emptyRoutesListTV;
    private ProgressBar progressBarRoutesList;
    private Callback_RoutesListFragment callback_routesListFragment;
    private SharedViewModel sharedViewModel;
    private RouteDetailsFragment routeFragment;
    private FragmentManager fragmentManager;
    private MainPageFragment mainPageFragment;

    private Handler handler;
    private Runnable retryRunnable;

    public void setCallbackRoutesListFragment(Callback_RoutesListFragment callback_routesListFragment) {
        this.callback_routesListFragment = callback_routesListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes_list, container, false);

        findViews(view);

        initializing();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializing() {
        handler = new Handler(Looper.getMainLooper());
        routeFragment = new RouteDetailsFragment();
        fragmentRoutesRV.setVisibility(View.GONE);
        emptyRoutesListTV.setVisibility(View.GONE);
        progressBarRoutesList.setVisibility(View.VISIBLE);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        retryRunnable = new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                ArrayList<Route> loadedRoutes = ListOfRoutes.getInstance().getRoutes();

                if (loadedRoutes != null && !loadedRoutes.isEmpty()) {
                    routes.clear();
                    routes.addAll(loadedRoutes);
                    fragmentRoutesRV.setVisibility(View.VISIBLE);
                    emptyRoutesListTV.setVisibility(View.GONE);
                    progressBarRoutesList.setVisibility(View.GONE);
                    routeItemAdapter.notifyDataSetChanged();
                } else if (ErrorMessageFromServer.getInstance().getErrorMessageFromServer() != null &&
                        !ErrorMessageFromServer.getInstance().getErrorMessageFromServer().isEmpty()) {
                    fragmentRoutesRV.setVisibility(View.GONE);
                    emptyRoutesListTV.setVisibility(View.VISIBLE);
                    emptyRoutesListTV.setText(ErrorMessageFromServer.getInstance().getErrorMessageFromServer());
                    progressBarRoutesList.setVisibility(View.GONE);
                }
                else {
                    handler.postDelayed(retryRunnable, Constants.RETRY_INTERVAL);
                }
            }
        };
        handler.post(retryRunnable);
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
                routeFragment.setMainPageFragment(mainPageFragment);
                routeFragment.setRoute(route);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, routeFragment).commit();
            }
        });
    }

    public void onLeavingFragment() {
        Parcelable state = Objects.requireNonNull(fragmentRoutesRV.getLayoutManager()).onSaveInstanceState();
        sharedViewModel.setRecyclerViewState(state);
    }

    private void findViews(View view) {
        routes = new ArrayList<>();
        routeItemAdapter = new RouteItemAdapter(getContext(), routes);
        setCallbackRouteItemForAdapter();
        fragmentRoutesRV = view.findViewById(R.id.fragmentRoutesRV);
        fragmentRoutesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentRoutesRV.setItemAnimator(new DefaultItemAnimator());
        fragmentRoutesRV.setAdapter(routeItemAdapter);
        emptyRoutesListTV = view.findViewById(R.id.emptyRoutesListTV);
        progressBarRoutesList = view.findViewById(R.id.progressBarRoutesList);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getRecyclerViewState().observe(getViewLifecycleOwner(), this::onChanged);
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
    public void onDestroy() {
        super.onDestroy();
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

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setMainPageFragment(MainPageFragment mainPageFragment) {
        this.mainPageFragment = mainPageFragment;
    }
}