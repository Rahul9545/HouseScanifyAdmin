package com.appynitty.adminapp.activities;

import static com.appynitty.adminapp.utils.MainUtils.isOnDuty;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.DashboardAdapter;
import com.appynitty.adminapp.databinding.ActivityDashboard1Binding;
import com.appynitty.adminapp.entities.OffLocation;
import com.appynitty.adminapp.models.ConnectionModel;
import com.appynitty.adminapp.models.DashboardDTO;
import com.appynitty.adminapp.models.DutyDTO;
import com.appynitty.adminapp.models.UlbDTO;
import com.appynitty.adminapp.models.UserLocationDTO;
import com.appynitty.adminapp.repositories.DashboardRepository;
import com.appynitty.adminapp.repositories.OfflineLocationRepo;
import com.appynitty.adminapp.repositories.SendLocationRepo;
import com.appynitty.adminapp.services.LocationService;
import com.appynitty.adminapp.utils.ConnectionLiveData;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.utils.MyApplication;
import com.appynitty.adminapp.viewmodels.DashboardViewModel;
import com.appynitty.adminapp.viewmodels.DutyOnOffViewModel;
import com.appynitty.adminapp.viewmodels.OfflineLocationVM;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.Priority;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    private static final int RC_BACKGROUND_LOCATION = 102;

    private String empType = "";
    private Context context;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager layoutManager;
    private ImageView recyclerViewIndicator;
    private DashboardAdapter adapter;

    private DashboardViewModel dashboardViewModel;
    private DutyOnOffViewModel dutyViewModel;
    private OfflineLocationVM offlineLocationVM;

    private SendLocationRepo sendLocationRepo;
    private OfflineLocationRepo offlineLocationRepo;

    private ActivityDashboard1Binding binding;
    private ImageView btnLogout;

    private List<UlbDTO> ulbList;
    List<UserLocationDTO> userLocationList;
    List<OffLocation> offLocationsList;

    AlertDialog.Builder builder;
    AlertDialog alert;
    public static final int MobileData = 2;
    public static final int WifiData = 1;
    boolean doubleBackToExitPressedOnce = false;
    String[] permsFineLocation = {Manifest.permission.ACCESS_FINE_LOCATION};
    String[] permsBackgroundLocation = {Manifest.permission.ACCESS_BACKGROUND_LOCATION};
    public static final int RC_FINE_LOCATION = 101;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private double wayLatitude;
    private double wayLongitude;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //added by rahul
        if (MainUtils.isMyServiceRunning(LocationService.class, DashboardActivity.this))
            ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();
        init();
        checkPermissions();
        checkServiceStatus();
        //getLocationForAttendance();
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }

    private void checkPermissions() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {


                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Background permission");
                    alertDialog.setMessage(getString(R.string.background_location_permission_message));

                    alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Start service anyway",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                    starServiceFunc();
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "Grant background Permission",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    requestBackgroundLocationPermission();
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.show();


                } else if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
//                    starServiceFunc();
                }
            } else {
            }

        } else if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
                alertDialog.setTitle("ACCESS_FINE_LOCATION");
                alertDialog.setMessage("Location permission required");

                alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                requestFineLocationPermission();
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            } else {
                requestFineLocationPermission();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, "onRequestPermissionsResult: " + requestCode);
        if (requestCode == RC_FINE_LOCATION) {

            if (grantResults.length != 0 /*grantResults.isNotEmpty()*/ && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Now, Please select Allow all the time!", Toast.LENGTH_LONG).show();
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    requestBackgroundLocationPermission();
                }

            } else {
                Toast.makeText(this, "ACCESS_FINE_LOCATION permission denied", Toast.LENGTH_LONG).show();
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                    startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            uri)
                    );

                }
            }

        } else if (requestCode == RC_BACKGROUND_LOCATION) {

            if (grantResults.length != 0 /*grantResults.isNotEmpty()*/ && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Background location Permission Granted", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Background location permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    private void requestBackgroundLocationPermission() {
        ActivityCompat.requestPermissions(this,
                permsBackgroundLocation,
                RC_BACKGROUND_LOCATION);
    }

    private void requestFineLocationPermission() {
        ActivityCompat.requestPermissions(this, permsFineLocation, RC_FINE_LOCATION);
    }

    private void checkServiceStatus() {
        if (isOnDuty()) {
            if (MainUtils.isMyServiceRunning(LocationService.class, this)) {
                Log.e(TAG, "checkServiceStatus: running = " + true);
            } else {
                ((MyApplication) MainUtils.mainApplicationConstant).startLocationTracking();
            }
        } else {
            Log.e(TAG, "checkServiceStatus: offDuty");

            if (MainUtils.isMyServiceRunning(LocationService.class, this))
                ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();
        }
    }

    private void init() {

        setupWindowAnimations();
        avoidBatteryOptimization();
        userLocationList = new ArrayList<>();
        offLocationsList = new ArrayList<>();
        mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
        offlineLocationRepo = new OfflineLocationRepo();

        context = DashboardActivity.this;

        if (!MainUtils.isGPSEnable(context)) {
            MainUtils.gpsStatusCheck(context);
        }
        ulbList = new ArrayList<>();
        adapter = new DashboardAdapter(context, ulbList);

        binding = DataBindingUtil.setContentView(DashboardActivity.this, R.layout.activity_dashboard1);
        binding.setLifecycleOwner(this);

        binding.liLUlbCount.setVisibility(View.VISIBLE);
        binding.chSelectBox.setVisibility(View.VISIBLE);
        recyclerViewIndicator = findViewById(R.id.ivIndicatorDown);


        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Scrolling up
//                    Log.e(TAG, "onScrolled: scrolled up!" );
                    recyclerViewIndicator.setAnimation(null);
                    recyclerViewIndicator.setVisibility(View.GONE);

                } else {
                    // Scrolling down
                }
            }

        });

        binding.cbActvInactivUlb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    recyclerViewIndicator.setAnimation(null);
                    recyclerViewIndicator.setVisibility(View.GONE);

                } else {
                    translate(recyclerViewIndicator);
                }

                if (compoundButton.isChecked()){
                    Log.d(TAG, "after search check checkbox: "+ulbList.size());
                    adapter.afterFilter(ulbList);
                    setOnRecycler(ulbList);
                }
            }
        });

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(@Nullable ConnectionModel connection) {
                MainUtils.IS_CONNECTED = connection.getIsConnected();
                if (connection.getIsConnected()) {

                    if (binding.rlSnackOffline.getVisibility() == View.VISIBLE)
                        binding.rlSnackOffline.setVisibility(View.GONE);
                    binding.txtNoData.setVisibility(View.GONE);
                    //binding.refreshLayout.setRefreshing(true);

                } else {
                    if (binding.rlSnackOnline.getVisibility() == View.VISIBLE)
                        binding.rlSnackOnline.setVisibility(View.GONE);
                    binding.txtNoData.setVisibility(View.VISIBLE);

                    MainUtils.IS_SNACKBAR_ON = true;
                    binding.rlSnackOffline.setVisibility(View.VISIBLE);
                }
            }
        });

        sendLocationRepo = SendLocationRepo.getInstance();
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        dutyViewModel = new ViewModelProvider(this).get(DutyOnOffViewModel.class);
        offlineLocationVM = new ViewModelProvider(this).get(OfflineLocationVM.class);
        offlineLocationVM.init();
        offlineLocationVM.getAllLocationsLiveData().observe(this, new Observer<List<OffLocation>>() {
            @Override
            public void onChanged(List<OffLocation> offLocations) {

                if (offLocations.size() > 0)
                    Log.e(TAG, "init: offline locations list: " + offLocations.get(0).getLocationObj().toString());
            }
        });

        setOfflineLocationIds();
        binding.setDashboardViewModel(dashboardViewModel);
        binding.setDutyViewModel(dutyViewModel);
        builder = new AlertDialog.Builder(this);

        empType = Prefs.getString(MainUtils.EMP_TYPE);
        binding.tvUserName.setText(getString(R.string.greetings) + " " + Prefs.getString(MainUtils.USER_NAME) + " !");
        if (!empType.isEmpty() && empType.matches("SA")) {
            binding.empType.setText(R.string.subAdmin);
            binding.rlUserBox.setVisibility(View.GONE);
            binding.dutyLayout.setVisibility(View.VISIBLE);
            hideViews(true);
        }else if (!empType.isEmpty() && empType.matches("SUA")) {
            binding.empType.setText(R.string.str_usr_role_super_admin);
            binding.crdUserRightsBtn.setVisibility(View.VISIBLE);
            binding.dutyLayout.setVisibility(View.GONE);
            binding.rlUserBox.setVisibility(View.VISIBLE);
            binding.liBoxUserBtnOne.setVisibility(View.VISIBLE);
            binding.liBoxUserBtnTwo.setVisibility(View.GONE);
            binding.crdUserRightsBtn.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            hideViews(false);

        }else if (!empType.isEmpty() && empType.matches("A")){
            binding.empType.setText(R.string.adminTitle);
            binding.crdUserRightsBtn.setVisibility(View.VISIBLE);
            binding.dutyLayout.setVisibility(View.GONE);
            binding.rlUserBox.setVisibility(View.GONE);
            binding.crdUserRightsBtn.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            hideViews(false);
        }

//        dutyViewModel.checkAttendance();  //checking attendance from server

        if (Prefs.contains(MainUtils.IS_ATTENDANCE_ON)) {
            if (isOnDuty()) {
                binding.btnSwitch.setChecked(true);
                hideViews(false);
            } else if (empType.matches("A")) {
                Log.e(TAG, "init: adminType: Admin!");
            }else if (empType.matches("SUA")) {
                Log.e(TAG, "init: adminType: Super Admin!");
            }
            else {
                binding.btnSwitch.setChecked(false);
                hideViews(true);
            }
        } else {
            Log.e(TAG, "init: adminType: " + Prefs.getString(MainUtils.EMP_TYPE));
        }

        refreshLayout = findViewById(R.id.refresh_layout);
        layoutManager = new LinearLayoutManager(context);
        btnLogout = findViewById(R.id.ivLogout);

        getLocationForAttendance();
        /*dutyViewModel.getAttendanceChk().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Prefs.putBoolean(MainUtils.IS_ATTENDANCE_ON, aBoolean);
                Log.e(TAG, "onChanged: Duty_off:" + aBoolean);
                if (aBoolean) {
                    binding.btnSwitch.setChecked(false);   // if attendance is off
                    hideViews(true);
                    ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();

                } else {
                    binding.btnSwitch.setChecked(true);     // if attendance if on
                    hideViews(false);
                }

            }
        });*/

        dutyViewModel.getProgressLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        dutyViewModel.getDutyDTOMutableLiveData().observe(this, new Observer<DutyDTO>() {
            @Override
            public void onChanged(DutyDTO dutyDTO) {
                Log.e(TAG, "onChanged: isAttendanceOn? ans: " + dutyDTO.getIsAttendanceOn());
                if (!dutyDTO.getIsAttendanceOn()) {
                    MainUtils.success(dutyDTO.getStatus(), Toast.LENGTH_SHORT);
                    Prefs.putBoolean(MainUtils.IS_ATTENDANCE_ON, dutyDTO.getIsAttendanceOn());
                    Prefs.remove(MainUtils.LAT);
                    Prefs.remove(MainUtils.LONG);
                    Log.e(TAG, "onChanged: removing lat-long");
                    binding.btnSwitch.setChecked(false);
                    hideViews(true);
                    if (MainUtils.isMyServiceRunning(LocationService.class, DashboardActivity.this))
                        ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();
                    recyclerViewIndicator.setVisibility(View.GONE);
                    recyclerViewIndicator.setAnimation(null);

                    if (mFusedLocationClient != null) {
                        mFusedLocationClient.removeLocationUpdates(locationCallback);
                    } else {
                        Log.e(TAG, "onChanged: mFusedLocationClient is null");
                    }
                } else {
                    MainUtils.success(dutyDTO.getStatus(), 1);
                    if (mFusedLocationClient != null) {
                        mFusedLocationClient.removeLocationUpdates(locationCallback);
                    } else {
                        Log.e(TAG, "onChanged: mFusedLocationClient is null");
                    }
                    Log.e(TAG, "onClick: removing location updates!");
                    Prefs.putBoolean(MainUtils.IS_ATTENDANCE_ON, dutyDTO.getIsAttendanceOn());
                    binding.btnSwitch.setChecked(true);
                    hideViews(false);
                    if (!MainUtils.isMyServiceRunning(LocationService.class, DashboardActivity.this))
                        ((MyApplication) MainUtils.mainApplicationConstant).startLocationTracking();
                    recyclerViewIndicator.setVisibility(View.VISIBLE);
                    translate(recyclerViewIndicator);
                    MainUtils.setInPunchDate(Calendar.getInstance());
                }
            }
        });

        dutyViewModel.getDutyErrorLiveData().observe(DashboardActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                MainUtils.error(s, Toast.LENGTH_SHORT);
                binding.btnSwitch.setChecked(isOnDuty());
                if (!isOnDuty() && MainUtils.isMyServiceRunning(LocationService.class, DashboardActivity.this)) {
                    ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();
                }
            }
        });

        binding.searchUlb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

                if (!charSequence.toString().isEmpty()){
                    binding.chSelectBox.setVisibility(View.GONE);
                    binding.liLUlbCount.setVisibility(View.GONE);
                    filter(charSequence.toString().trim());
                }else {
                    binding.chSelectBox.setVisibility(View.VISIBLE);
                    binding.liLUlbCount.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dashboardViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                binding.progressBar.setVisibility(visibility);
            }
        });

        dashboardViewModel.getLogoutLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Prefs.getBoolean(MainUtils.IS_ATTENDANCE_ON) || empType.matches("A")) {
                    logoutUser(s);
                }else if (!Prefs.getBoolean(MainUtils.IS_ATTENDANCE_ON) || empType.matches("SUA")) {
                    logoutUser(s);
                }
                else {
                    MainUtils.warning("Please turn off the Dashboard first!", Toast.LENGTH_SHORT);
                }

            }
        });

        dashboardViewModel.getUserRightStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                startActivity(new Intent(context, UserRightsActivity.class));
            }
        });

        dashboardViewModel.getUserAttendanceStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                startActivity(new Intent(context, UserAttendanceActivity.class));
            }
        });

        dashboardViewModel.getDashboardResponse().observe(this, new Observer<List<DashboardDTO>>() {

            @Override
            public void onChanged(List<DashboardDTO> dashboardResults) {
                ulbList.clear();
                for (int i = 0; i < dashboardResults.size(); i++) {
                    ulbList.add(new UlbDTO(dashboardResults.get(i).getUlb(),
                            dashboardResults.get(i).getAppid()));
                }

                if (ulbList.size() > 0 ){
                    Log.i("RahulCheck", "filter "+ulbList.size());
                    binding.txtNoData.setVisibility(View.GONE);
                }else {
                    binding.txtNoData.setVisibility(View.VISIBLE);
                }

                adapter.notifyDataSetChanged();
                setOnRecycler(ulbList);
            }
        });

        binding.btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: switch!");
                binding.btnSwitch.setChecked(Prefs.getBoolean(MainUtils.IS_ATTENDANCE_ON));
                if (MainUtils.isGPSEnable(context)) {
//                    getLocationForAttendance();

                    if (isOnDuty()) {
                        MainUtils.showDialog(context, "Are you sure you want to turn off the dashboard?",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dutyViewModel.changeDuty(false);
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        binding.btnSwitch.setChecked(true);
                                        dialogInterface.dismiss();
                                    }
                                });

                    } else {
                        if (Prefs.getString(MainUtils.LAT) == null || Prefs.getString(MainUtils.LAT).isEmpty()/*&& Prefs.getString(MainUtils.LAT) != null || Prefs.getString(MainUtils.LAT).isEmpty()*/) {
                            MainUtils.gpsStatusCheck(context);
                            if (!MainUtils.IS_GPS_DIALOG_VISIBLE) {
                                MainUtils.info("Please wait for a while as your gps is getting ready!", 1);
                            }
//                        dutyViewModel.changeDuty(true);
                        } else {
//                        MainUtils.info("Please wait for a while as your gps is getting ready!", 1);
                            dutyViewModel.changeDuty(true);

//                        MainUtils.gpsStatusCheck(context);
                        }
                        Log.e(TAG, "onClick: lat: " + Prefs.getString(MainUtils.LAT) + ", lon: " + Prefs.getString(MainUtils.LONG));
                    }

                } else {
                    MainUtils.gpsStatusCheck(context);
                }

            }
        });
        setOnClick();
        dutyScheduler();
    }

    @SuppressLint("MissingPermission")
    private void getLocationForAttendance() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3 * 1000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        Prefs.putString(MainUtils.LAT, String.valueOf(wayLatitude));
                        Prefs.putString(MainUtils.LONG, String.valueOf(wayLongitude));
                        Log.e(TAG, "onLocationResult: " + String.format(Locale.US, "%s -- %s", wayLatitude, wayLongitude));

                    }
                }
            }

        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void avoidBatteryOptimization() {
        Intent intent = new Intent();
        String packageName = getPackageName();
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + packageName));
            startActivity(intent);
        }
    }


    public void translate(View view) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 2.0f, 80.0f);
        translateAnimation.setDuration(800);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(translateAnimation);
    }

    /**
     * here we are setting the ID's to the offline locations so that after submitting
     * the offline locations to the server we can delete it from the local database
     */
    private void setOfflineLocationIds() {
        offLocationsList = offlineLocationRepo.getOfflineLocatnList();
        if (offLocationsList.size() > 0) {
            for (OffLocation offLocation : offLocationsList) {
                offLocation.getLocationObj().setOfflineId(offLocation.getId());
                offlineLocationVM.update(offLocation);
            }
            for (int i = 0; i < offLocationsList.size(); i++) {
                userLocationList.add(offLocationsList.get(i).getLocationObj());
            }
            sendOfflineLocations(userLocationList);
        }

    }


    /**
     * Here, we are sending offline locations to the server and after successful submission
     * we're deleting the locations one by one using the offline id (On background thread)
     */
    public void sendOfflineLocations(List<UserLocationDTO> listOfLocations) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background work here
                sendLocationRepo.sendOfflineLocations(listOfLocations, new SendLocationRepo.ILocationResponse() {
                    @Override
                    public void onResponse(Response<List<UserLocationDTO>> locationResponse) {
                        Log.e(TAG, "onResponse: " + locationResponse);
                        int mId = 0;
                        for (int i = 0; i < locationResponse.body().size(); i++) {
                            if (locationResponse.body().get(i).getStatus().matches(MainUtils.STATUS_SUCCESS)) {
                                mId = locationResponse.body().get(i).getOfflineId();
                                offlineLocationRepo.deleteLocationById(mId);
                                Log.e(TAG, "sendOfflineLocations: successfully deleted locationId: " + mId);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                    }
                });
            }
        });
    }

    private void hideViews(Boolean s) {
        if (s) {
            binding.recyclerView.setVisibility(View.GONE);
            binding.liLUlbList.setVisibility(View.GONE);
            binding.liLUlbCount.setVisibility(View.GONE);
            binding.cardDashItem.setVisibility(View.GONE);
            binding.ivIndicatorDown.setVisibility(View.GONE);
        } else {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.liLUlbList.setVisibility(View.VISIBLE);
            binding.liLUlbCount.setVisibility(View.VISIBLE);
            binding.cardDashItem.setVisibility(View.VISIBLE);
        }

    }

    private void setOnClick() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRestart();
                Log.e(TAG, "onRefresh: ");
                if (MainUtils.IS_CONNECTED) {
                    if (binding.progressBar.getVisibility() == View.VISIBLE)
                        binding.progressBar.setVisibility(View.GONE);

                    dashboardViewModel.getUlbData();

                } else {
                    Toast.makeText(context, getResources().getString(R.string.requestInternet), Toast.LENGTH_LONG).show();
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }

                }
            }
        });
    }

    private void setOnRecycler(List<UlbDTO> ulbList) {
        if (ulbList.size() > 10 && Prefs.getBoolean(MainUtils.IS_ATTENDANCE_ON)) {
            recyclerViewIndicator.setVisibility(View.VISIBLE);
            translate(recyclerViewIndicator);
        } else {
            recyclerViewIndicator.setAnimation(null);
            recyclerViewIndicator.setVisibility(View.GONE);
        }

        refreshLayout.setRefreshing(false);
        binding.recyclerView.setItemViewCacheSize(ulbList.size());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

    private void filter(String text) {
        List<UlbDTO> filteredList = new ArrayList<>();

        for (UlbDTO item : ulbList) {
            if (item != null){
                if (item.getUlbName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }

        adapter.filterList(filteredList);

        if (filteredList.size() > 0 ){
            Log.i("RahulCheck", "filter "+filteredList.size());
            binding.txtNoData.setVisibility(View.GONE);
        }else {
            binding.txtNoData.setVisibility(View.VISIBLE);
        }

    }

    public void logoutUser(String s) {

        MainUtils.showDialog(context, "Are you sure you want to logout the app?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Prefs.remove(MainUtils.USER_ID);
                Prefs.remove(MainUtils.EMP_TYPE);
                Prefs.putBoolean(MainUtils.IS_LOGIN, false);
                Prefs.putBoolean(MainUtils.IS_ATTENDANCE_ON, false);
                if (MainUtils.isMyServiceRunning(LocationService.class, DashboardActivity.this)) {
                    ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();
                }
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainUtils.success(s, Toast.LENGTH_SHORT);
                context.startActivity(intent);
                finish ();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        if (requestCode == 101) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // All required changes were successfully made
                    MainUtils.IS_GPS_DIALOG_VISIBLE = false;
                    binding.progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.progressBar.setVisibility(View.GONE);
                            Log.e(TAG, "run: lat: " + Prefs.getString(MainUtils.LAT));
                        }
                    }, 2000);

                    break;
                case Activity.RESULT_CANCELED:
                    // The user was asked to change settings, but chose not to
                    MainUtils.IS_GPS_DIALOG_VISIBLE = false;
                    Toast.makeText(DashboardActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                    if (MainUtils.isMyServiceRunning(LocationService.class, DashboardActivity.this)) {
                        ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();
                    }
                    this.finish();

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (isOnDuty()) {
            if (MainUtils.isMyServiceRunning(LocationService.class, this)) {
                Log.e(TAG, "onPostResume: service is running already!");
            } else {
                Log.e(TAG, "onPostResume: service is not running!");
                if (MainUtils.isGPSEnable(context))
                    ((MyApplication) MainUtils.mainApplicationConstant).startLocationTracking();
                else
                    MainUtils.gpsStatusCheck(context);
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dashboardViewModel.getProgress();
        Log.e(TAG, "onResume: ");
        if (Prefs.contains(MainUtils.LAT))
            stopLocationUpdates();
        else
            getLocationForAttendance();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public void dutyScheduler() {
        /*String currentTime = getTime();
        String endTime = "11:50 PM";
        String pattern = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);*/

        //hello there, whats up!

        String inDate = MainUtils.getInPunchDate();
        String currentDate = MainUtils.getServerDate();

        Calendar CurrentTime = MainUtils.getCurrentTime();
        Calendar DutyOffTime = MainUtils.getDutyEndTime();

        if (inDate.equals(currentDate) && CurrentTime.after(DutyOffTime)) {
            String dutyEndTime = MainUtils.getCurrentDateDutyOffTime();
        }

        if (!inDate.equals(currentDate)) {
            String dutyEndTime = MainUtils.getPreviousDateDutyOffTime();
        }

        /*try {
            Date mCurrentTime = sdf.parse(currentTime);
            Date mEndTime = sdf.parse(endTime);

            if (mCurrentTime.after(mEndTime)) {
//              current time has elapsed end-time
                Log.e(TAG, "dutyScheduler: elapsed!");
                Prefs.putBoolean(MainUtils.IS_ATTENDANCE_ON, false);
                Prefs.remove(MainUtils.LAT);
                Prefs.remove(MainUtils.LONG);
                Log.e(TAG, "onChanged: removing lat-long");
                binding.btnSwitch.setChecked(false);
                hideViews(true);
                if (MainUtils.isMyServiceRunning(LocationService.class, DashboardActivity.this))
                    ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();
                recyclerViewIndicator.setVisibility(View.GONE);
                recyclerViewIndicator.setAnimation(null);
                if (mFusedLocationClient != null) {
                    stopLocationUpdates();
                } else {
                    Log.e(TAG, "onChanged: mFusedLocationClient is null");
                }
            } else {
//                return false; yet to be elapsed
                Log.e(TAG, "dutyScheduler: duty time yet not elapsed!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
//        return false;
    }
}