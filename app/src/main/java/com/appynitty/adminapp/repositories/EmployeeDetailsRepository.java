package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.EmployeeDetailsDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.EmployeeDetailsWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeDetailsRepository {

    private static final String TAG = "EmployeeDetailRepository";
    private static final EmployeeDetailsRepository instance = new EmployeeDetailsRepository();
    private final MutableLiveData<List<EmployeeDetailsDTO>> empDetailsList = new MutableLiveData<>();

    public static EmployeeDetailsRepository getInstance() {
        return instance;
    }

    public void getEmpDetailsList(String appId, IEmpDetailsListener iEmpDetailsListener) {
        Log.e(TAG, "getEmpDetailsList: ");
        String userId = Prefs.getString(MainUtils.USER_ID);  // ByDefault it should be "0" for list of all the employees for the current ULB
        String date = MainUtils.getDate();

        EmployeeDetailsWebService empDetailsWebService = RetrofitClient.createService(EmployeeDetailsWebService.class, MainUtils.BASE_URL);
        Call<List<EmployeeDetailsDTO>> empDetailsCall = empDetailsWebService.getEmployeesDetails(MainUtils.CONTENT_TYPE, date, date, appId, 0);
        empDetailsCall.enqueue(new Callback<List<EmployeeDetailsDTO>>() {
            @Override
            public void onResponse(Call<List<EmployeeDetailsDTO>> call, Response<List<EmployeeDetailsDTO>> response) {
                if (response.code() == 200) {
                    Log.e(TAG, "onResponse: empDetails:- " + response.body().toString());
                    empDetailsList.setValue(response.body());
                    iEmpDetailsListener.onResponse(empDetailsList);
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeDetailsDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iEmpDetailsListener.onFailure(t);
            }
        });
    }

    public void getFilteredEmpDetails(String frmDate, String toDate, String appId, String userId, IEmpDetailsListener iEmpDetailsListener) {
        EmployeeDetailsWebService empDetailsWebService = RetrofitClient.createService(EmployeeDetailsWebService.class, MainUtils.BASE_URL);
        Call<List<EmployeeDetailsDTO>> empDetailsCall = empDetailsWebService.getEmployeesDetailsUpdated(MainUtils.CONTENT_TYPE, frmDate, toDate, appId, Integer.parseInt(userId));
        empDetailsCall.enqueue(new Callback<List<EmployeeDetailsDTO>>() {
            @Override
            public void onResponse(Call<List<EmployeeDetailsDTO>> call, Response<List<EmployeeDetailsDTO>> response) {
                if (response.code() == 200) {
                    Log.e(TAG, "onResponse: empDetails:- " + response.body().toString());
                    empDetailsList.setValue(response.body());
                    iEmpDetailsListener.onResponse(empDetailsList);
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeDetailsDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iEmpDetailsListener.onFailure(t);
            }
        });
    }

    public interface IEmpDetailsListener {
        void onResponse(MutableLiveData<List<EmployeeDetailsDTO>> empDetailsResponse);

        void onFailure(Throwable t);
    }

}
