package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hike_with_me_client.Adapters.RouteItemAdapter;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RouteItem;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Route.RouteMethods;
import com.example.hike_with_me_client.R;

import java.util.ArrayList;

public class MainPageFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        return view;
    }
}