package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RouteItem;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Trip.trip;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;
import com.example.hike_with_me_client.Utils.Singleton.ErrorMessageFromServer;
import com.example.hike_with_me_client.Utils.Singleton.ListOfRoutes;
import com.example.hike_with_me_client.Utils.Singleton.ListOfTrips;
import com.example.hike_with_me_client.Adapters.RouteItemAdapter;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class TripFragment extends Fragment {

    private TripFragment tripFragment;
    private static final String ARG_TRIP_ID = "trip_id";
    private trip trip;
    private TextView tripNameText;
    private TextView tripDescriptionText;
    private TextView tripStartDateText;
    private TextView tripEndDateText;
    private RecyclerView recyclerViewRoutes;
    private RecyclerView recyclerViewImages;
    private Handler handler = new Handler();
    private Runnable retryRunnable;

    private FragmentManager fragmentManager;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getParentFragmentManager();
    }
    public static TripFragment newInstance(String tripId) {
        TripFragment fragment = new TripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TRIP_ID, tripId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        findViews(view);
        loadTripFromServer();
        return view;
    }

    private void findViews(View view) {
        tripNameText = view.findViewById(R.id.text_name_trip);
        tripDescriptionText = view.findViewById(R.id.text_description_trip);
        tripStartDateText = view.findViewById(R.id.text_start_date_trip);
        tripEndDateText = view.findViewById(R.id.text_end_date_trip);
        recyclerViewRoutes = view.findViewById(R.id.recycler_view_routes);
        recyclerViewImages = view.findViewById(R.id.recycler_view_images);
    }

    private void loadTripFromServer() {
        retryRunnable = new Runnable() {
            @Override
            public void run() {
                String tripId = getArguments().getString(ARG_TRIP_ID);
                ArrayList<trip> trips = ListOfTrips.getInstance().getTrips();

                if (trips != null) {
                    for (trip t : trips) {
                        if (t.getId().equals(tripId)) {
                            trip = t;
                            break;
                        }
                    }
                }

                if (trip != null) {
                    // Update UI with the loaded trip details
                    tripNameText.setText(trip.getName());
                    tripDescriptionText.setText(trip.getDescription());
                    tripStartDateText.setText(trip.getStartDate());
                    tripEndDateText.setText(trip.getEndDate());
                    trip.toString();
                    // Fetch routes by names
                    ArrayList<Route> routes = getRoutesByNames(trip.getRoutesNames());
                    if (routes.isEmpty()) {
                        // Handle the case where no routes are found
                        Log.d("TripFragment", "No routes found for trip.");
                    } else {
                        // Set up routes RecyclerView
                        RouteItemAdapter routeAdapter = new RouteItemAdapter(getContext(), routes);
                        routeAdapter.setCallbackRouteItem(new Callback_RouteItem() {
                            @Override
                            public void itemClicked(Route route, int position) {
                                Fragment routeDetailsFragment = RouteDetailsFragment.newInstance(route.getId());
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.main_fragment_container, routeDetailsFragment) // Replace with your actual container ID
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });
                        recyclerViewRoutes.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerViewRoutes.setAdapter(routeAdapter);
                    }

                } else if (ErrorMessageFromServer.getInstance().getErrorMessageFromServer() != null &&
                        !ErrorMessageFromServer.getInstance().getErrorMessageFromServer().isEmpty()) {
                    // Display error message if available
                    tripNameText.setText(R.string.trip_name);
                    tripDescriptionText.setText(R.string.trip_description);
                    tripStartDateText.setText(R.string.trip_start_date);
                    tripEndDateText.setText(R.string.trip_end_date);
                } else {
                    // Retry fetching trips if no data and no error message
                    handler.postDelayed(retryRunnable, Constants.RETRY_INTERVAL);
                }
            }
        };

        handler.post(retryRunnable);
    }

    private ArrayList<Route> getRoutesByNames(String[] routeNames) {
        ArrayList<Route> routes = new ArrayList<>();
        List<Route> allRoutes = ListOfRoutes.getInstance().getRoutes();
        for (String routeName : routeNames) {
            Log.d("Route found", routeName);
                    Route r = new Route();
                    r.setName(routeName);
                    routes.add(r);
                    break;
        }
        return routes;
    }
    public void setTrip(com.example.hike_with_me_client.Models.Trip.trip trip) {
        this.trip = trip;
    }
}
