package com.appynitty.adminapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.Task;
import com.pixplicity.easyprefs.library.Prefs;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainUtils {
    public static final String LAT = "lat";
    public static final String LONG = "lan";
    public static final long LOCATION_INTERVAL_MINUTES = 10 * 60 * 1000;
    private static final String TAG = "MainUtils";
    //Staging Server
//    public static final String BASE_URL = "http://183.177.126.33:6560";
//    public static final String BASE_URL = "http://202.65.157.254:5049/";
    public static final String BASE_URL = "http://202.65.157.254:6560";  //Temp url
//    public static final String BASE_URL = "http://202.65.157.253:6560";  //Main url

    //Live Server
//    public static final String BASE_URL = "https://ictsbm.com:30443";
    public static final String CONTENT_TYPE = "application/json";
    public static final String EMP_TYPE_ADMIN = "A";
    public static final String EMP_TYPE = "EmpType";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String APP_ID = "appId";
    public static final String EMP_ID = "qrEmpId";
    public static final String IS_LOGIN = "IsLoggedIn";
    private static final String IN_PUNCH_DATE = "IN_PUNCH_DATE";
    public static boolean IS_GPS_DIALOG_VISIBLE = false;
    public static boolean IS_CONNECTED = false;
    public static boolean IS_SNACKBAR_ON = false;
    public static final String IS_ATTENDANCE_OFF = "isAttendenceOff";
    public static final String IS_ATTENDANCE_ON = "isAttendanceOn";
    public static final String SERVER_DATE_FORMATE = "MM-dd-yyyy";
    public static final String SERVER_DATE_FORMATE_LOCAL = "yyyy-MM-dd";
    public static final String SERVER_DATE_TIME_FORMATE = "MM-dd-yyyy HH:mm:ss";
    public static final String SERVER_DATE_TIME_FORMATE_LOCAL = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_ERROR = "error";
    private static final String EMP_SERVER_DATE_FORMATE = "dd-MM-yyyy";
    private static final String EMP_DATE_FORMAT = "dd/MM/yyyy";
    private static final String EMP_DATE_FORMAT_DASH = "dd-MM-yyyy";
    private static final String TITLE_DATE_FORMATE = "dd MMM yyyy";
    private static final String SEMI_MONTH_FORMATE = "MMM";
    private static final String DATE_VALUE_FORMATE = "dd";
    private static final String SERVER_TIME_FORMATE = "hh:mm a";
    private static final String SERVER_TIME_24HR_FORMATE = "HH:mm";
    public static boolean isRecreate = false;
    public static Context context;
    public static Application mainApplicationConstant;
    private static HashMap<String, String> menuNavigation = null;
    private static ArrayList<String> menuNavigationList = null;

    public static String getDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        SimpleDateFormat df = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE_LOCAL, Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getTime() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat(MainUtils.SERVER_TIME_FORMATE, Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getDateDash() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat(MainUtils.EMP_DATE_FORMAT_DASH, Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String serverDateFromLocal(String date) {

        SimpleDateFormat local = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        SimpleDateFormat server = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        try {
            return server.format(local.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return server.format(new Date());
    }

    public static String serverDate(String date) {

        SimpleDateFormat local = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        SimpleDateFormat server = new SimpleDateFormat(MainUtils.EMP_DATE_FORMAT, Locale.ENGLISH);
        try {
            return server.format(local.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return server.format(new Date());
    }

    public static String firstLatterCap(String firstCap){
        String upperString = firstCap.substring(0, 1).toUpperCase() + firstCap.substring(1).toLowerCase();
        return upperString;
    }

    public static String capitalizeString(String str) {
        String retStr = str;
        try { // We can face index out of bound exception if the string is null
            retStr = str.substring(0, 1).toUpperCase() + str.substring(1);
        }catch (Exception e){}
        return retStr;
    }

    public static String getServerTime() {

        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_TIME_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getLocalDate() {

        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTime() {

        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_DATE_TIME_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static void setInPunchDate(Calendar calendar) {
        Prefs.putString(IN_PUNCH_DATE, getServerDate(calendar));
    }

    public static String getInPunchDate() {
        return Prefs.getString(IN_PUNCH_DATE, getServerDate(Calendar.getInstance()));
    }

    private static String getServerDate(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        return format.format(calendar.getTime());
    }

    public static Calendar getCurrentTime() {
        Calendar now = Calendar.getInstance();

        return now;
    }

    public static String getServerDate() {
        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static Calendar getDutyEndTime() {
        Calendar cl = Calendar.getInstance();

        cl.set(Calendar.HOUR_OF_DAY, 23);
        cl.set(Calendar.MINUTE, 50);
        cl.set(Calendar.SECOND, 0);

        return cl;
    }

    public static String getCurrentDateDutyOffTime() {

        DateFormat dateFormat = new SimpleDateFormat(MainUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 50);
        cal.set(Calendar.SECOND, 0);

        return dateFormat.format(cal.getTime());
    }

    public static String getPreviousDateDutyOffTime() {

        DateFormat dateFormat = new SimpleDateFormat(MainUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 50);
        cal.set(Calendar.SECOND, 0);

        return dateFormat.format(cal.getTime());
    }

    public static String getServerDateTimeWithMilliesSecond() {

        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTimeLocal() {

        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static Date parse(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String getTitleDateFormat(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(TITLE_DATE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractDate(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(DATE_VALUE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractMonth(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(SEMI_MONTH_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatDate(String date, String fromFormat, String toFormat) {

        SimpleDateFormat providedFormat = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(toFormat, Locale.ENGLISH);

        try {
            return requiredFormat.format(providedFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getEmpTimeLineFormat(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_TIME_24HR_FORMATE, Locale.ENGLISH);
        SimpleDateFormat timelineFormat = new SimpleDateFormat(SERVER_TIME_FORMATE, Locale.ENGLISH);

        try {
            return timelineFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTitleDateFormatEmp(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(EMP_SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(TITLE_DATE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setEmpType(String empType) {
        Prefs.putString(PREFS.EMPLOYEE_TYPE, empType);
    }
    public static void setUserRoleType(String type) {
        Prefs.putString(PREFS.USER_ROLE_TYPE, type);
    }

    public static String getUserRoleType() {
        Prefs.getString(PREFS.USER_ROLE_TYPE, "");
        return null;
    }

    public interface PREFS {
        String APP_ID = "appId";
        String EMPLOYEE_TYPE = "EmpType";
        String USER_ROLE_TYPE = "type";
    }

    public static void error(String msg, int duration) {
        MDToast mdToast = MDToast.makeText(mainApplicationConstant, msg, duration, MDToast.TYPE_ERROR);
        mdToast.show();
    }

    public static void success(String msg, int duration) {
        MDToast mdToast = MDToast.makeText(mainApplicationConstant, msg, duration, MDToast.TYPE_SUCCESS);
        mdToast.show();
    }

    public static void info(String msg, int duration) {
        MDToast mdToast = MDToast.makeText(mainApplicationConstant, msg, duration, MDToast.TYPE_INFO);
        mdToast.show();
    }

    public static void warning(String msg, int duration) {
        MDToast mdToast = MDToast.makeText(mainApplicationConstant, msg, duration, MDToast.TYPE_WARNING);
        mdToast.show();
    }


    public static int getBatteryStatus() {
        BatteryManager batteryManager = (BatteryManager) mainApplicationConstant.getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Activity mActivity) {
        ActivityManager manager = (ActivityManager) mActivity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void showDialog(Context context, String message, DialogInterface.OnClickListener positiveListener, @Nullable DialogInterface.OnClickListener negativeListener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", positiveListener);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", negativeListener);
        alertDialog.show();
    }

    public static Boolean isOnDuty() {

        if (Prefs.getBoolean(MainUtils.IS_ATTENDANCE_ON))
            return true;
        else
            return false;
    }

    public static boolean isGPSEnable(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void gpsStatusCheck(Context ctx) {
        IS_GPS_DIALOG_VISIBLE = true;
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10);
        mLocationRequest.setSmallestDisplacement(10);
        mLocationRequest.setFastestInterval(10);
        mLocationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new
                LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(ctx).checkLocationSettings(builder.build());


        task.addOnCompleteListener(task1 -> {
            try {
                LocationSettingsResponse response = task1.getResult(ApiException.class);
                // All location settings are satisfied. The client can initialize location
                // requests here.

            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                    (Activity) ctx,
                                    101);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        } catch (ClassCastException e) {
                            // Ignore, should be an impossible error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    /***
     * Created by Rahul Rokade
     * 10/12/2022
     * */
    public static void downloadFile(String url, File outputFile) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch(FileNotFoundException e) {
            return; // swallow a 404
        } catch (IOException e) {
            return; // swallow a 404
        }
    }
    /***
     * Created by Rahul Rokade
     * 10/12/2022
     * */
    public static void drawRout( GoogleMap mapView, boolean shadow, LatLng source, LatLng destination){

        Paint mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2);

        String gP1 = String.valueOf(source);
        String gP2 = String.valueOf(destination);

        Point p1 = new Point();
        Point p2 = new Point();
        Path path = new Path();

        /*Projection projection=mapView.getProjection();
        projection.toPixels(gP1, p1);
        projection.toPixels(gP2, p2);*/

        path.moveTo(p2.x, p2.y);
        path.lineTo(p1.x,p1.y);
        Canvas canvas = new Canvas();
        canvas.drawPath(path, mPaint);
    }
    /***
     * Created by Rahul Rokade
     * 10/12/2022
     * */
    public static void animateMarker(GoogleMap googleMap, final Marker marker, final LatLng toPosition, final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = googleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final LinearInterpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                marker.setDraggable(true);

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }
}
