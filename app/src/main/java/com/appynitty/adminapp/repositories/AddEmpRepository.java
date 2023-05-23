package com.appynitty.adminapp.repositories;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.AddEmpWebService;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmpRepository {
    private static final String TAG = "AddEmpRepository";
    private static final AddEmpRepository instance = new AddEmpRepository();
    String appId = Prefs.getString(MainUtils.APP_ID, null);
    Gson gson = new Gson();
    List<AddEmpDTO> addEmpDTO = new ArrayList<>();

    public static AddEmpRepository getInstance() {
        return instance;
    }

    public void addEmpRemote(MutableLiveData<AddEmpDTO> addEmpBody, IAddEmpResponse iAddEmpResponse) {
        AddEmpWebService empWebService = RetrofitClient.createService(AddEmpWebService.class, MainUtils.BASE_URL);
        if (!addEmpDTO.isEmpty())
            addEmpDTO.clear();

        addEmpDTO.add(addEmpBody.getValue());

        Call<List<AddEmpResult>> initiateAddEmp = empWebService.addNewEmpHS(MainUtils.CONTENT_TYPE, appId, addEmpDTO);

        initiateAddEmp.enqueue(new Callback<List<AddEmpResult>>() {
            @Override
            public void onResponse(Call<List<AddEmpResult>> call, Response<List<AddEmpResult>> response) {
                if (response.code() == 200) {
                    iAddEmpResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body());
                } else if (response.code() == 500) {
                    iAddEmpResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<AddEmpResult>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iAddEmpResponse.onFailure(t);
            }
        });


    }


    public void updateEmpDetails(EmpDModelDTO empDetails, IAddEmpResponse iAddEmpResponse) {
        Log.d(TAG, "updateEmpDetails: " + empDetails.toString());
        List<EmpDModelDTO> empDetails1 = new ArrayList<>();
        empDetails1.add(empDetails);
        AddEmpWebService empWebService = RetrofitClient.createService(AddEmpWebService.class, MainUtils.BASE_URL);
        Call<List<AddEmpResult>> updateEmployeeCall = empWebService.updateEmployeeDetails(MainUtils.CONTENT_TYPE, appId, empDetails1);
        updateEmployeeCall.enqueue(new Callback<List<AddEmpResult>>() {
            @Override
            public void onResponse(Call<List<AddEmpResult>> call, Response<List<AddEmpResult>> response) {
                Log.e(TAG, "onResponse: " + response.body().get(0).getMessage());

                iAddEmpResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<AddEmpResult>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public interface IAddEmpResponse {
        void onResponse(List<AddEmpResult> addEmpResponse);

        void onFailure(Throwable t);
    }

}
