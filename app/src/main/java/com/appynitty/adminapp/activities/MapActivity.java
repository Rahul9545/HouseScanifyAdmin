package com.appynitty.adminapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ActivityMapBinding;
import com.appynitty.adminapp.directionMap.DirectionJsonParser;
import com.appynitty.adminapp.fragments.MapsFragment;
import com.appynitty.adminapp.utils.MainUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/***
 * Created by Rahul Rokade
 * 10/12/2022
 * */
public class MapActivity extends AppCompatActivity {
    private static final String TAG = "MapActivity";
    private Context context;
    private ActivityMapBinding mapBinding;
    private MapsFragment mapsFragment;
    String userMobileList;
    String empName, deviceLogin;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapBinding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(mapBinding.getRoot());
        init();
    }

    private void init() {
        context = this;

        mapBinding.toolbar.setTitle("Map Activity");
        mapBinding.toolbar.setNavigationIcon(R.drawable.ic_back);

        setSupportActionBar(toolbar);

        mapBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //   startActivity(new Intent(UserAttendanceActivity.this, DashboardActivity.class));
            }
        });
        Intent intent = getIntent();
        userMobileList = intent.getStringExtra("userAttendanceMobileList");
        empName = intent.getStringExtra("EmpName");
        deviceLogin = intent.getStringExtra("device_login");
        mapsFragment = new MapsFragment();
        mapBinding.txtName.setText(empName);
        if (deviceLogin != null){
            mapBinding.txtLoginDevice.setText(getResources().getString(R.string.str_mobile));
        }

        loadFragment(mapsFragment);

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, fragment);
            ft.commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}