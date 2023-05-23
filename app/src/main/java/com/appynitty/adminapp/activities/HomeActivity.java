package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ActivityHomeBinding;
import com.appynitty.adminapp.fragments.AttendanceFragment;
import com.appynitty.adminapp.fragments.EmpDetailsFragment;
import com.appynitty.adminapp.fragments.EmpListFragment;
import com.appynitty.adminapp.fragments.HouseDetailsFragment;
import com.appynitty.adminapp.fragments.LiveDataFragment;
import com.appynitty.adminapp.models.ConnectionModel;
import com.appynitty.adminapp.utils.ConnectionLiveData;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.UlbDataViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private Context context;
    private BottomNavigationView navigationView;
    private ImageButton btnHome;
    private FrameLayout frameLayout;
    private LiveDataFragment liveDataFragment;
    private HouseDetailsFragment houseDetailsFragment;
    private AttendanceFragment attendanceFragment;
    private EmpDetailsFragment empDetailsFragment;
    UlbDataViewModel ulbDataViewModel;
    ActivityHomeBinding binding;
    private String appId, ulbName;
    AlertDialog.Builder builder;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setupWindowAnimations();
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setEnterTransition(slide);
    }

    public Bundle getUlbData() {
        Bundle ulbData = new Bundle();
        ulbData.putString("val1", appId);
        ulbData.putString("val2", ulbName);
        return ulbData;
    }

    public void checkTheNet() {
        if (!MainUtils.IS_CONNECTED)
            Toast.makeText(context, getResources().getString(R.string.strNoInternet), Toast.LENGTH_LONG).show();
    }

    private void init() {
        context = this;
        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(ConnectionModel connection) {
                MainUtils.IS_CONNECTED = connection.getIsConnected();
                if (!connection.getIsConnected())
                    Toast.makeText(context, getResources().getString(R.string.strNoInternet), Toast.LENGTH_LONG).show();
            }
        });


        Intent intent = getIntent();
        appId = intent.getStringExtra("appId");
        ulbName = intent.getStringExtra("ulbName");
//        ulbDataViewModel = ViewModelProviders.of(this).get(UlbDataViewModel.class);
        binding.txtUlbName.setText(ulbName);
        btnHome = findViewById(R.id.ib_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: Home Clicked!");
            }
        });

        if (Prefs.getString(MainUtils.EMP_TYPE, null).matches("A")) {
            binding.txtAdminType.setText(getResources().getString(R.string.adminTitle));
        }else if (Prefs.getString(MainUtils.EMP_TYPE, null).matches("SUA")) {
            binding.txtAdminType.setText(getResources().getString(R.string.str_usr_role_super_admin));
        }else if (Prefs.getString(MainUtils.EMP_TYPE, null).matches("SA")){
            binding.txtAdminType.setText(getResources().getString(R.string.subAdmin));
            Log.e(TAG, "init: " + Prefs.getString(MainUtils.EMP_TYPE_ADMIN, null));
        }
        builder = new AlertDialog.Builder(this);
        frameLayout = findViewById(R.id.container_frame_layout);
        liveDataFragment = new LiveDataFragment();
        houseDetailsFragment = new HouseDetailsFragment();
        attendanceFragment = new AttendanceFragment();
        empDetailsFragment = new EmpDetailsFragment();


        binding.imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

        setOnClick();

        loadFragment(new LiveDataFragment());

    }

    public HashMap<String, String> getResult() {
        HashMap<String, String> map = new HashMap<>();
        map.put("appId", appId);
        map.put("ulbName", ulbName);
        return map;
    }

    private void setOnClick() {
        binding.bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_live_date:
                        fragment = new LiveDataFragment();
                        break;
                    case R.id.nav_house_details:
//                        fragment = new HouseDetailsFragment();
                        fragment = new EmpListFragment();
                        break;
                    case R.id.nav_attendance:
                        fragment = new AttendanceFragment();
                        break;
                    case R.id.nav_emp_details:
                        fragment = new EmpDetailsFragment();
                        break;
                }
                return loadFragment(fragment);
            }
        });

        binding.ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, DashboardActivity.class));
            }
        });

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_frame_layout, fragment);
            ft.commit();
            return true;
        }
        return false;
    }

    public void logoutUser() {

        builder.setMessage("Do you want to logout ?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Prefs.remove(MainUtils.USER_ID);
                        Prefs.remove(MainUtils.EMP_TYPE);
                        Prefs.putBoolean(MainUtils.IS_LOGIN, false);
                        startActivity(new Intent(context, LoginActivity.class));

                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();
        //Setting the title manually
        alert.setTitle("Logout!");
        alert.show();
    }

}