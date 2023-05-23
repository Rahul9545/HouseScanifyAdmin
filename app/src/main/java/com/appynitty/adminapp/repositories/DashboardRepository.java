package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.DashboardDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.DashboardWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepository {
    private static final String TAG = "DashboardRepository";
    private static final DashboardRepository instance = new DashboardRepository();
    private final MutableLiveData<List<DashboardDTO>> ULBListLiveData = new MutableLiveData<>();

    public static DashboardRepository getInstance() {
        return instance;
    }

    public void getListOfULBs(Boolean status, IDashboardResponse iDashboardResponse) {
        Log.e(TAG, "getListOfULBs: ");
        String empType = Prefs.getString(MainUtils.EMP_TYPE);
        String userId = Prefs.getString(MainUtils.USER_ID);

        DashboardWebService dashboardWebService = RetrofitClient.createService(DashboardWebService.class, MainUtils.BASE_URL);
        Call<List<DashboardDTO>> dashboardDTOCall = dashboardWebService.getAll_ULBs(MainUtils.CONTENT_TYPE, empType, userId, status);
        dashboardDTOCall.enqueue(new Callback<List<DashboardDTO>>() {
            @Override
            public void onResponse(Call<List<DashboardDTO>> call, Response<List<DashboardDTO>> response) {
                if (response.code() == 200) {
                    ULBListLiveData.setValue(response.body());
                    iDashboardResponse.onResponse(ULBListLiveData);
                } else if (response.code() == 500) {

                    Log.e(TAG, "onResponse: " + response.body());
                }

            }

            @Override
            public void onFailure(Call<List<DashboardDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iDashboardResponse.onFailure(t);
            }
        });
    }

    public interface IDashboardResponse {
        void onResponse(MutableLiveData<List<DashboardDTO>> dashboardResponse);

        void onFailure(Throwable t);
    }
}
