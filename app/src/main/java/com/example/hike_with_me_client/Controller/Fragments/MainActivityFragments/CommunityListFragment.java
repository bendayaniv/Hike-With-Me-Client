package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hike_with_me_client.Adapters.UserItemAdapter;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_UserItem;
import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;
import com.example.hike_with_me_client.Utils.Singleton.ErrorMessageFromServer;
import com.example.hike_with_me_client.Models.Objects.UserWithDistance;
import com.example.hike_with_me_client.Models.User.UserMethods;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Constants;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CommunityListFragment extends Fragment {

    ArrayList<UserWithDistance> users;
    private RecyclerView fragmentCommunityRV;
    private MaterialTextView emptyCommunityListTV;
    private UserItemAdapter userItemAdapter;
    private ProgressBar progressBarCommunityList;

    private Handler handler;
    private Runnable retryRunnable;

    Callback_UserItem callback_userItem = (user, position) -> Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community_list, container, false);
        findViews(view);
        initializing();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializing() {
        UserMethods.getAllUsers();
        handler = new Handler(Looper.getMainLooper());
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        fragmentCommunityRV.setVisibility(View.GONE);
        emptyCommunityListTV.setVisibility(View.GONE);
        progressBarCommunityList.setVisibility(View.VISIBLE);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        retryRunnable = new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                ArrayList<UserWithDistance> loadedUsers = CurrentUser.getInstance().getUsersWithDistance();
                if (loadedUsers != null && !loadedUsers.isEmpty()) {
                    users.clear();
                    users.addAll(loadedUsers);
                    emptyCommunityListTV.setVisibility(View.GONE);
                    fragmentCommunityRV.setVisibility(View.VISIBLE);
                    progressBarCommunityList.setVisibility(View.GONE);
                    userItemAdapter.notifyDataSetChanged();
                } else if (ErrorMessageFromServer.getInstance().getErrorMessageFromServer() != null &&
                        !ErrorMessageFromServer.getInstance().getErrorMessageFromServer().isEmpty()) {
                    emptyCommunityListTV.setVisibility(View.VISIBLE);
                    emptyCommunityListTV.setText(ErrorMessageFromServer.getInstance().getErrorMessageFromServer());
                    fragmentCommunityRV.setVisibility(View.GONE);
                    progressBarCommunityList.setVisibility(View.GONE);
                } else {
                    handler.postDelayed(retryRunnable, Constants.RETRY_INTERVAL);
                }
            }
        };
        handler.post(retryRunnable);
    }


    private void findViews(View view) {
        users = new ArrayList<>();
        userItemAdapter = new UserItemAdapter(users);
        userItemAdapter.setCallbackUserItem(callback_userItem);
        fragmentCommunityRV = view.findViewById(R.id.fragmentCommunityRV);
        fragmentCommunityRV.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentCommunityRV.setAdapter(userItemAdapter);
        emptyCommunityListTV = view.findViewById(R.id.emptyCommunityListTV);
        progressBarCommunityList = view.findViewById(R.id.progressBarCommunityList);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        CurrentUser.getInstance().setUsersWithDistance(null);
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CurrentUser.getInstance().setUsersWithDistance(null);
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CurrentUser.getInstance().setUsersWithDistance(null);
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        CurrentUser.getInstance().setUsersWithDistance(null);
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }
}