package com.appynitty.adminapp.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.entities.OffLocation;
import com.appynitty.adminapp.models.UserLocationDTO;
import com.appynitty.adminapp.repositories.OfflineLocationRepo;
import com.appynitty.adminapp.repositories.SendLocationRepo;
import com.appynitty.adminapp.utils.MainUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.Task;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

public class LocationService extends Service {
    private static final String TAG = "LocationService";
    private LocationRequest locationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final String CHANNEL_ID = "my_service";
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    private static final int LOCATION_SERVICE_NOTIF_ID = 1001;
    long notify_interval = 1000 * 60 * 10;     // 10 min. => 1000 * 60 * 10
    LocationCallback locationCallback1;
    private long updatedTime = 0;
    private Timer mTimer = null;
    SendLocationRepo locationRepo = SendLocationRepo.getInstance();
    OfflineLocationRepo offlineLocationRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public LocationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        prepareForegroundNotification();
        startLocationUpdates();

        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToSendLocation(), 10, notify_interval);
        return START_STICKY;
    }

    private void prepareForegroundNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Location Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
        Bitmap largeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.backgroundwork))
                .setContentText(getString(R.string.app_notification_description))

                .setSmallIcon(R.drawable.ic_noti_icon)
                .setLargeIcon(largeBitmap)
                .setColor(getResources().getColor(R.color.colorPrimary, getResources().newTheme()))
                .setPriority(Notification.PRIORITY_MAX)
                .build();
        startForeground(LOCATION_SERVICE_NOTIF_ID, notification);
    }

    public void startLocationUpdates() {
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
        mFusedLocationClient.requestLocationUpdates(this.locationRequest,
                this.locationCallback1, Looper.myLooper());
    }

    private void init() {
        offlineLocationRepo = new OfflineLocationRepo();
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new
                LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());
        mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(getApplicationContext());

        locationCallback1 = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    Log.e(TAG, "onLocationResult: got null coordinates!");
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        Prefs.putString(MainUtils.LAT, String.valueOf(location.getLatitude()));
                        Prefs.putString(MainUtils.LONG, String.valueOf(location.getLongitude()));
//                        Log.e(TAG, "onLocationResult: Lat: " + location.getLatitude() + ", Long: " + location.getLongitude());
                    }
                }
            }
        };
    }

    private class TimerTaskToSendLocation extends TimerTask {
        @Override
        public void run() {
            sendLocation();
        }
    }

    private void sendLocation() {
        Log.e(TAG, "sendLocation: at: " + MainUtils.getDate() + "-" + MainUtils.getTime() + " " + Prefs.getString(MainUtils.LAT) + ", Long: " + Prefs.getString(MainUtils.LONG));

        UserLocationDTO locationDTO = new UserLocationDTO(Prefs.getString(MainUtils.LONG), 0, MainUtils.getServerDateTime(), "",
                true, Prefs.getString(MainUtils.LAT), Prefs.getString(MainUtils.EMP_ID));
        if (MainUtils.isNetworkAvailable(MainUtils.mainApplicationConstant)) {
            locationRepo.send10MinLocation(locationDTO, new SendLocationRepo.ILocationResponse() {
                @Override
                public void onResponse(Response<List<UserLocationDTO>> locationResponse) {
                    if (locationResponse.code() == 200) {
                        /*assert locationResponse.body() != null;*/
                        if (locationResponse.body().size() > 0)
                            Log.e(TAG, "onResponse: " + locationResponse.body().get(0).getMessage());
                    } else if (locationResponse.code() == 500)
                        Log.e(TAG, "onResponse: errorMsg" + locationResponse.message());
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });
        } else {
            Log.e(TAG, "Inserting into the database: ");
            offlineLocationRepo.insertLocation(new OffLocation(locationDTO, MainUtils.getServerDateTime()));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFusedLocationClient.removeLocationUpdates(locationCallback1);
    }
}
