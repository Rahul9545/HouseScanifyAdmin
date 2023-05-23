package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.EmpDWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpDRepository {
    private static final String TAG = "EmpDRepository";

    private static final EmpDRepository instance = new EmpDRepository();
    private final MutableLiveData<List<EmpDModelDTO>> empDLiveData = new MutableLiveData<>();

    public static EmpDRepository getInstance() {
        return instance;
    }

    public void getEmpDList(Boolean status, String appId, IEmpDResponse iEmpDResponse) {
        Log.e(TAG, "getEmpDList: ");
        String empType = Prefs.getString(MainUtils.EMP_TYPE);
        String userId = Prefs.getString(MainUtils.USER_ID);
        /*String empId = Prefs.getString(MainUtils.EMP_ID);*/
        String empId = "0";

        EmpDWebService empDWebService = RetrofitClient.createService(EmpDWebService.class, MainUtils.BASE_URL);
        Call<List<EmpDModelDTO>> empDDTOCall = empDWebService.getEmpDList(MainUtils.CONTENT_TYPE,
                empType, userId, empId, appId, status);
        empDDTOCall.enqueue(new Callback<List<EmpDModelDTO>>() {
            @Override
            public void onResponse(Call<List<EmpDModelDTO>> call, Response<List<EmpDModelDTO>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {

                        empDLiveData.setValue(response.body());
                        iEmpDResponse.onResponse(empDLiveData);
                        Log.e(TAG, "onResponse: " + response.body());
                        Log.e(TAG, "iAttendanceResponse: " + iEmpDResponse);
                    }
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<EmpDModelDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iEmpDResponse.onFailure(t);
            }
        });

    }

    public void getEmpDListIN(Boolean status, String appId, IEmpDResponse iEmpDResponse) {
        Log.e(TAG, "getEmpDList: ");
        String empType = Prefs.getString(MainUtils.EMP_TYPE);
        String userId = Prefs.getString(MainUtils.USER_ID);
        /*String empId = Prefs.getString(MainUtils.EMP_ID);*/
        String empId = "0";

        EmpDWebService empDWebService = RetrofitClient.createService(EmpDWebService.class, MainUtils.BASE_URL);
        Call<List<EmpDModelDTO>> empDDTOCall = empDWebService.getEmpDList(MainUtils.CONTENT_TYPE,
                empType, userId, empId, appId, status);
        empDDTOCall.enqueue(new Callback<List<EmpDModelDTO>>() {
            @Override
            public void onResponse(Call<List<EmpDModelDTO>> call, Response<List<EmpDModelDTO>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {

                        empDLiveData.setValue(response.body());
                        iEmpDResponse.onResponse(empDLiveData);
                        Log.e(TAG, "onResponse: " + response.body());
                        Log.e(TAG, "iAttendanceResponse: " + iEmpDResponse);
                    }
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<EmpDModelDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iEmpDResponse.onFailure(t);
            }
        });

    }

    public interface IEmpDResponse {
        void onResponse(MutableLiveData<List<EmpDModelDTO>> empDResponse);

        void onFailure(Throwable t);
    }

}
