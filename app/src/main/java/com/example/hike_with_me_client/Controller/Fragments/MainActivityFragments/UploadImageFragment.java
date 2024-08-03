package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hike_with_me_client.Models.Trip.TripMethods;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;

import java.util.ArrayList;
import java.util.List;

public class UploadImageFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST_1 = 1;
    private static final int PICK_IMAGE_REQUEST_2 = 2;
    private static final int PERMISSION_REQUEST_CODE = 3;
    private static final String TAG = "UploadImageFragment";

    private ImageView imageView1;
    private ImageView imageView2;
    private Button btnUploadImages;
    private ProgressBar progressBar;
    private Uri imageUri1;
    private Uri imageUri2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_image, container, false);

        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        btnUploadImages = view.findViewById(R.id.btnUploadImages);
        progressBar = view.findViewById(R.id.progressBar);

        imageView1.setOnClickListener(v -> openImageChooser(PICK_IMAGE_REQUEST_1));
        imageView2.setOnClickListener(v -> openImageChooser(PICK_IMAGE_REQUEST_2));
        btnUploadImages.setOnClickListener(v -> uploadImages());

        return view;
    }

    // ... Keep the existing onViewCreated and onRequestPermissionsResult methods ...
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed with image selection
            } else {
                Toast.makeText(getContext(), "Permission denied. Cannot select images.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openImageChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST_1) {
                imageUri1 = data.getData();
                imageView1.setImageURI(imageUri1);
                Log.d(TAG, "Image 1 selected: " + imageUri1.toString());
            } else if (requestCode == PICK_IMAGE_REQUEST_2) {
                imageUri2 = data.getData();
                imageView2.setImageURI(imageUri2);
                Log.d(TAG, "Image 2 selected: " + imageUri2.toString());
            }
        } else {
            Log.d(TAG, "Image selection failed or was cancelled");
        }
    }

//    private void uploadImages() {
//        List<Uri> imageUris = new ArrayList<>();
//        if (imageUri1 != null) {
//            imageUris.add(imageUri1);
//        }
//        if (imageUri2 != null) {
//            imageUris.add(imageUri2);
//        }
//
//        if (!imageUris.isEmpty()) {
//            String userName = CurrentUser.getInstance().getUser().getName(); // Replace with actual user name
//            String tripName = "tripName4"; // TODO - Replace with actual trip name
//
//            Log.d(TAG, "Attempting to upload images: " + imageUris.toString());
//
//            try {
//                TripMethods.uploadImages(imageUris, userName, tripName, requireContext());
//            } catch (Exception e) {
//                Log.e(TAG, "Error during upload: ", e);
//                Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Log.d(TAG, "No images selected for upload");
//            Toast.makeText(getContext(), "Please select at least one image", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void uploadImages() {
        List<Uri> imageUris = new ArrayList<>();
        if (imageUri1 != null) {
            imageUris.add(imageUri1);
        }
        if (imageUri2 != null) {
            imageUris.add(imageUri2);
        }

        if (!imageUris.isEmpty()) {
            String userName = CurrentUser.getInstance().getUser().getName();
            String tripName = "tripName4"; // TODO - Replace with actual trip name

            Log.d(TAG, "Attempting to upload images: " + imageUris.toString());

            // Show ProgressBar
            progressBar.setVisibility(View.VISIBLE);

            try {
                // Assuming TripMethods.uploadImages is an asynchronous operation
                // You might need to modify this part based on how TripMethods.uploadImages is implemented
                TripMethods.uploadImages(imageUris, userName, tripName, requireContext(), progressBar);
            } catch (Exception e) {
                // Hide ProgressBar
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Error during upload: ", e);
                Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "No images selected for upload");
            Toast.makeText(getContext(), "Please select at least one image", Toast.LENGTH_SHORT).show();
        }
    }

}