package com.appynitty.adminapp.fragments;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.directionMap.DirectionHelper;
import com.appynitty.adminapp.directionMap.DirectionJsonParser;
import com.appynitty.adminapp.utils.MainUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * Created by Rahul Rokade
 * 10/12/2022
 * */
public class MapsFragment extends Fragment  implements OnMapReadyCallback{
    private static final String TAG = "MapsFragment";
    private Context context;
    private GoogleMap mMap, mMapDestination;
    private LatLng mOrigin;
    private LatLng mDestination;
    private Polyline mPolyline;
    ArrayList<LatLng> mMarkerPoints;
    DirectionJsonParser directionJsonParser;
    private Double startLat, startLong, endLat, endLong;
    private Double startCameraLat, startCameraLong;
    private String userMobile;
    String userMobileList;
    String empName, deviceLogin;
    private View view;
    public LatLng markerLatLog;
    private ImageView mapMoveMarker;
    private TextView txtNormalMap, txtSatelliteMap;
    List<Marker> markerNewDrag = new ArrayList<Marker>();
    LatLng source, destination;

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        init();
        return view;
    }

    private void init() {
        context = getActivity();
        mapMoveMarker = view.findViewById(R.id.confirm_address_marker);
        txtNormalMap = view.findViewById(R.id.map_normal);
        txtSatelliteMap = view.findViewById(R.id.map_satellite);
        Intent intent = requireActivity().getIntent();
        Intent intentDest = requireActivity().getIntent();
        if (intent != null){
            userMobileList = intent.getStringExtra("userAttendanceMobileList");

            startLat = Double.valueOf(intent.getStringExtra("startLat"));
            startLong = Double.valueOf(intent.getStringExtra("startLog"));
            /*endLat = Double.valueOf(intent.getStringExtra("endLat"));
            endLong = Double.valueOf(intent.getStringExtra("endLong"));*/
            empName = intent.getStringExtra("EmpName");
            deviceLogin = intent.getStringExtra("device_login");


            Log.d(TAG, "UserAttendanceMobileData: " + userMobileList);
            Log.d(TAG, "startLat: " + startLat);
            Log.d(TAG, "startLog: " + startLong);
            /*Log.d(TAG, "endLat: " + endLat);
            Log.d(TAG, "endLong: " + endLong);*/
        }

        if (intentDest != null){
            userMobileList = intentDest.getStringExtra("userAttendanceMobileList");

           /* startLat = Double.valueOf(intent.getStringExtra("startLat"));
            startLong = Double.valueOf(intent.getStringExtra("startLog"));*/

            if (" ".equalsIgnoreCase(intentDest.getStringExtra("endLat")) && " ".equalsIgnoreCase(intentDest.getStringExtra("endLong"))){
                endLat = Double.valueOf(intentDest.getStringExtra("endLat"));
                endLong = Double.valueOf(intentDest.getStringExtra("endLong"));
            }else {
                endLat = ParseDouble(intentDest.getStringExtra("endLat"));
                endLong = ParseDouble(intentDest.getStringExtra("endLong"));
            }
            /*endLat = Double.valueOf(intentDest.getStringExtra("endLat"));
            endLong = Double.valueOf(intentDest.getStringExtra("endLong"));*/
            empName = intentDest.getStringExtra("EmpName");
            deviceLogin = intentDest.getStringExtra("device_login");


            /*Log.d(TAG, "UserAttendanceMobileData: " + userMobileList);
            Log.d(TAG, "startLat: " + startLat);
            Log.d(TAG, "startLog: " + startLong);*/
            Log.d(TAG, "endLat: " + endLat);
            Log.d(TAG, "endLong: " + endLong);


        }


    }

    @SuppressLint({"UseCompatLoadingForDrawables", "MissingPermission"})
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMapDestination = googleMap;


       /*
        //move map and get lat long static marker
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(@NonNull CameraPosition cameraPosition) {
                startCameraLat = cameraPosition.target.latitude;
                startCameraLong = cameraPosition.target.longitude;
                LatLng cameraLatLong = new LatLng(startCameraLat, startCameraLong);
                Toast.makeText(context, ""+cameraLatLong, Toast.LENGTH_SHORT).show();
            }
        });*/

        if (startLat != null && startLong != null){
            source = new LatLng(startLat, startLong);
            mMap.addMarker(new MarkerOptions().position(source)
                    .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.start_point))
                    .title(empName+" Start point"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(source));


            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(source, 12F));
           googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // set the map type
           // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            /*txtSatelliteMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            });
            txtNormalMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            });

            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDrag(@NonNull Marker marker) {
                    marker.setDraggable(true);
                    markerLatLog = marker.getPosition();
                    Toast.makeText(context, ""+markerLatLog, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onMarkerDragEnd(@NonNull Marker marker) {
                    marker.setDraggable(true);
                    markerLatLog = marker.getPosition();
                    Toast.makeText(context, ""+markerLatLog, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMarkerDragStart(@NonNull Marker marker) {
                    marker.setDraggable(true);
                    markerLatLog = marker.getPosition();
                    Toast.makeText(context, ""+markerLatLog, Toast.LENGTH_SHORT).show();
                }
            });
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    marker.isDraggable();
                    marker.setDraggable(true);
                    return true;
                }
            });
            mapMoveMarker.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View view, DragEvent dragEvent) {

                    return false;
                }
            });
            googleMap.setMyLocationEnabled(true);

            ma.setPosition(source);

            MainUtils.animateMarker(mMap,ma,source,true);*/
        }

        if (endLat != null && endLong != null) {
             destination = new LatLng(endLat, endLong);
            mMap.addMarker(new MarkerOptions().position(destination)
                    .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.end_point))
                    .title(empName + " destination point"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));

            /*mMapDestination.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    marker.remove();
                    return true;
                }
            });*/

            /*googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Destination, 10F));
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // set the map type*/
        }

        Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(source, destination).width(5).color(Color.RED));
        PolylineOptions options = new PolylineOptions();
        options.addAll(line.getPoints());
        mMap.addPolyline(options);

        /*Circle circle = googleMap.addCircle(new CircleOptions()
                .center(source)
                .radius(10000)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));*/


    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private BitmapDescriptor bitmapDescriptorFromVectorTwo(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    public void onCameraMove(Marker marker) {
        LatLng center = mMap.getCameraPosition().target;
        marker.setPosition(center);
    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }


    // draw multiple line
    /*JSONObject jsonObject;
    List<List<HashMap<String, String>>> routes = null;

            try {
        jsonObject = new JSONObject();
        // Starts parsing data
        DirectionHelper helper = new DirectionHelper();
        routes = helper.parse(jsonObject);
        Log.e(TAG, "Executing Routes : "*//*, routes.toString()*//*);


        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;

        // Traversing through all the routes
        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = routes.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(10);
            lineOptions.color(Color.BLUE);

            Log.e(TAG, "PolylineOptions Decoded");
        }

        // Drawing polyline in the Google Map for the i-th route
        if (lineOptions != null) {
            return lineOptions;
        } else {
            return null;
        }

    } catch (Exception e) {
        Log.e(TAG, "Exception in Executing Routes : " + e.toString());
        return null;
    }*/





}