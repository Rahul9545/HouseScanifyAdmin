package com.appynitty.adminapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.ZoomViewActivity;
import com.appynitty.adminapp.databinding.FragmentReferenceIdDetailsBinding;
import com.appynitty.adminapp.models.ReferenceIdDetailsDTO;
import com.appynitty.adminapp.viewmodels.RefIdDetailsVM;
import java.util.List;

public class ReferenceIdDetailsFragment extends Fragment {
  private static final String TAG = "ReferenceIdDetailsFragm";
  FragmentReferenceIdDetailsBinding binding;
  private View view;
  RefIdDetailsVM refIdDetailsVM;
  private AppCompatActivity activity;
  ImageButton homeButton;
  ReferenceIdDetailsDTO refIdDetailssss;
  CardView cvGetDirections;

  public ReferenceIdDetailsFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    if (view == null) {
      binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reference_id_details, container,
          false);
      view = binding.getRoot();
      binding.setLifecycleOwner(this);
      init();
    }
    return view;
  }

  private void init() {
    String refId = getArguments().getString("refId");
    Log.e(TAG, "onCreateView: " + refId);
    activity = (AppCompatActivity) view.getContext();
    refIdDetailssss = new ReferenceIdDetailsDTO();
    homeButton = getActivity().findViewById(R.id.ib_home);
    homeButton.setImageResource(R.drawable.ic_back);
    cvGetDirections = activity.findViewById(R.id.card_getDirections);
    homeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        Fragment myFragment = new EmpListFragment();
        activity.getSupportFragmentManager().beginTransaction()
            .replace(R.id.container_frame_layout, myFragment)
            .commit();
      }
    });

    refIdDetailsVM = new ViewModelProvider(this).get(RefIdDetailsVM.class);
    refIdDetailsVM.getRefIdDetails(refId);

    refIdDetailsVM.getReferenceIdDetailsLiveData()
        .observe(getViewLifecycleOwner(), new Observer<List<ReferenceIdDetailsDTO>>() {
          @Override
          public void onChanged(List<ReferenceIdDetailsDTO> referenceIdDetailsDTOS) {
            for (ReferenceIdDetailsDTO refIdDetails : referenceIdDetailsDTOS
            ) {
              binding.setRefIdDetails(refIdDetails);
              refIdDetailssss = refIdDetails;
            }
          }
        });

    binding.cardRefIdDetails.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(activity, ZoomViewActivity.class);
        intent.putExtra("qrImage", refIdDetailssss.getQRCodeImage());
        activity.startActivity(intent);
      }
    });

    binding.cardGetDirections.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        view.startAnimation(buttonClick);

        Log.e(TAG, "onClick: lat: " + refIdDetailssss.getLat() + ", long: "
            + refIdDetailssss.getLon());

        String lat = refIdDetailssss.getLat();
        String lon = refIdDetailssss.getLon();
        String usersLocation = "geo:0,0?q=" + lat + "," + lon + "?z=21";

        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse(usersLocation);

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {

        } else {
          activity.startActivity(mapIntent);
        }
      }
    });

    binding.cardGetDirections.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Log.e(TAG, "onClick: Latitude: " + binding.txtLatitude.getText());
        String lat = binding.txtLatitude.getText().toString();
        String lon = binding.txtLongitude.getText().toString();
        String usersLocation = "geo:0,0?q=" + lat + "," + lon + "?z=21";

        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse(usersLocation);

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
          activity.startActivity(mapIntent);
        } else {
          Log.e(TAG, "onClick: maps is unavailable!");
        }
      }
    });
  }
}