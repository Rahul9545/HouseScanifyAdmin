package com.appynitty.adminapp.repositories;

import android.nfc.Tag;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.AddUserRoleRightDTO;
import com.appynitty.adminapp.models.AddUserRoleRightResult;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.AddUserRoleRightWebService;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserRoleRepository {
    private static final String TAG = "AddUserRoleRepository";
    private static final AddUserRoleRepository instance = new AddUserRoleRepository();
    String appId = Prefs.getString(MainUtils.APP_ID, null);
    Gson gson = new Gson();
    List<AddUserRoleRightDTO> addUserDTOS = new ArrayList<>();

    public static AddUserRoleRepository getInstance() {
        return instance;
    }

    public void addUserSave(MutableLiveData<AddUserRoleRightDTO> addEmpData, IAddUserResponse iAddUserResponse){
       Log.e(TAG, "data" +addEmpData.getValue().toString());
        AddUserRoleRightWebService addUserWebService = RetrofitClient.createService(AddUserRoleRightWebService.class, MainUtils.BASE_URL);
        if (!addUserDTOS.isEmpty()){
            addUserDTOS.clear();
        }
        addUserDTOS.add(addEmpData.getValue());

        Call<List<AddUserRoleRightResult>> initialAddEmp = addUserWebService.addNewUserRoleRightHS(MainUtils.CONTENT_TYPE, addUserDTOS);
        initialAddEmp.enqueue(new Callback<List<AddUserRoleRightResult>>() {
            @Override
            public void onResponse(Call<List<AddUserRoleRightResult>> call, Response<List<AddUserRoleRightResult>> response) {
                if (response.code() == 200) {
                    iAddUserResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body());
                } else if (response.code() == 500) {
                    iAddUserResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<AddUserRoleRightResult>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iAddUserResponse.onFailure(t);
            }
        });
    }

    public void addUserUpdate(UserRoleModelDTO empDetails,IAddUserResponse iAddUserResponse){
        Log.d(TAG, "updateUserRoleDetails: " + empDetails.toString());
        List<UserRoleModelDTO> empDetails1 = new ArrayList<>();
        empDetails1.add(empDetails);
        AddUserRoleRightWebService addUserWebService = RetrofitClient.createService(AddUserRoleRightWebService.class, MainUtils.BASE_URL);

        Call<List<AddUserRoleRightResult>> initialUpdateEmp = addUserWebService.updateUserRoleRightHS(MainUtils.CONTENT_TYPE, empDetails1);

        initialUpdateEmp.enqueue(new Callback<List<AddUserRoleRightResult>>() {
            @Override
            public void onResponse(Call<List<AddUserRoleRightResult>> call, Response<List<AddUserRoleRightResult>> response) {
                Log.e(TAG, "onResponse: " + response.body().get(0).getMessage());
                iAddUserResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<AddUserRoleRightResult>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public interface IAddUserResponse {
        void onResponse(List<AddUserRoleRightResult>  addUserResponse);

        void onFailure(Throwable t);
    }
}
