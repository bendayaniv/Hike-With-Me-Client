package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hike_with_me_client.Models.Trip.Trip;
import com.example.hike_with_me_client.Models.Trip.TripMethods;
import com.example.hike_with_me_client.R;

import java.util.ArrayList;
import java.util.List;

public class UploadImageFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private static final String TAG = "UploadImageFragment";

    private ImageView imageView;
    private Button btnSelectImage;
    private Button btnUploadImage;
    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_image, container, false);

        imageView = view.findViewById(R.id.imageView);
        btnSelectImage = view.findViewById(R.id.btnSelectImage);
        btnUploadImage = view.findViewById(R.id.btnUploadImage);

        btnSelectImage.setOnClickListener(v -> openImageChooser());
        btnUploadImage.setOnClickListener(v -> uploadImage());

        return view;
    }

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

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            Log.d(TAG, "Image selected: " + imageUri.toString());
        } else {
            Log.d(TAG, "Image selection failed or was cancelled");
        }
    }

//    private void uploadImage() {
//        if (imageUri != null) {
//            List<Uri> imageUris = new ArrayList<>();
//            imageUris.add(imageUri);
//
//            String userName = "exampleUser"; // Replace with actual user name
//            String tripName = "exampleTrip"; // Replace with actual trip name
//
//            Log.d(TAG, "Attempting to upload image: " + imageUri.toString());
//
//            try {
//                // Call the upload method
//                TripMethods.uploadImages(imageUris, userName, tripName, requireContext());
//            } catch (Exception e) {
//                Log.e(TAG, "Error during upload: ", e);
//                Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Log.d(TAG, "No image selected for upload");
//            Toast.makeText(getContext(), "Please select an image first", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void uploadImage() {
//        if (imageUri != null) {
//            List<Uri> imageUris = new ArrayList<>();
//            imageUris.add(imageUri);
//
//            String userName = "exampleUser"; // Replace with actual user name
//            String tripName = "exampleTrip"; // Replace with actual trip name
//
//            Log.d(TAG, "Attempting to upload image: " + imageUri.toString());
//
//            try {
//                TripMethods.uploadImages(imageUris, userName, tripName, requireContext());
//            } catch (Exception e) {
//                Log.e(TAG, "Error during upload: ", e);
//                Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Log.d(TAG, "No image selected for upload");
//            Toast.makeText(getContext(), "Please select an image first", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void uploadImage() {
        if (imageUri != null) {
            List<Uri> imageUris = new ArrayList<>();
            imageUris.add(imageUri);

            String userName = "exampleUser"; // Replace with actual user name
            String tripName = "exampleTrip"; // Replace with actual trip name

            Log.d(TAG, "Attempting to upload image: " + imageUri.toString());

            try {
                TripMethods.uploadImages(imageUris, userName, tripName, requireContext());
            } catch (Exception e) {
                Log.e(TAG, "Error during upload: ", e);
                Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "No image selected for upload");
            Toast.makeText(getContext(), "Please select an image first", Toast.LENGTH_SHORT).show();
        }
    }
}