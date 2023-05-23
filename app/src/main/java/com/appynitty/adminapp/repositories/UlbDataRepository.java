package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.SpecificUlbDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.UlbDataWebService;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UlbDataRepository {
    private static final String TAG = "UlbDataRepository";
    private static final UlbDataRepository instance = new UlbDataRepository();
    private final MutableLiveData<SpecificUlbDTO> specificUlbDTOMutableLiveData = new MutableLiveData<>();

    public static UlbDataRepository getInstance() {
        return instance;
    }

    public void getUlbDataInfo(String appId, IUlbDataResponse iUlbDataResponse) {
        String empType = Prefs.getString(MainUtils.EMP_TYPE);
        String userId = Prefs.getString(MainUtils.USER_ID);

        UlbDataWebService ulbDataWebService = RetrofitClient.createService(UlbDataWebService.class, MainUtils.BASE_URL);
        Call<SpecificUlbDTO> specificUlbDataCall = ulbDataWebService.getUlbData(MainUtils.CONTENT_TYPE, empType, userId, appId);
        specificUlbDataCall.enqueue(new Callback<SpecificUlbDTO>() {
            @Override
            public void onResponse(Call<SpecificUlbDTO> call, Response<SpecificUlbDTO> response) {
                if (response.code() == 200) {
                    specificUlbDTOMutableLiveData.setValue(response.body());
                    iUlbDataResponse.onResponse(specificUlbDTOMutableLiveData);
                    Log.e(TAG, "onResponse: " + response.body().toString());
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<SpecificUlbDTO> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iUlbDataResponse.onFailure(t);
            }
        });
    }

    public interface IUlbDataResponse {
        void onResponse(MutableLiveData<SpecificUlbDTO> specificUlbDataResponse);

        void onFailure(Throwable t);
    }
}
