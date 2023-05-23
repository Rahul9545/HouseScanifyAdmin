package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.UserLocationDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.UserLocationWebservice;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendLocationRepo {
    private static final String TAG = "SendLocationRepo";
    private static final SendLocationRepo instance = new SendLocationRepo();
    MutableLiveData<List<UserLocationDTO>> userLocationResponse = new MutableLiveData<>();
    OfflineLocationRepo offlineLocationRepo = new OfflineLocationRepo();

    public static SendLocationRepo getInstance() {
        return instance;
    }

    public void send10MinLocation(UserLocationDTO locationDTO, ILocationResponse iLocationResponse) {
        /*UserLocationDTO locationDTO = new UserLocationDTO(Prefs.getString(MainUtils.LONG), "0", MainUtils.getServerDateTime(), "",
                true, Prefs.getString(MainUtils.LAT), Prefs.getString(MainUtils.EMP_ID));*/

        Log.e(TAG, "send10MinLocation: locationDTO: " + locationDTO.ToString());
        List<UserLocationDTO> locationDTOList = new ArrayList<>();
        locationDTOList.add(locationDTO);
        UserLocationWebservice service = RetrofitClient.createService(UserLocationWebservice.class, MainUtils.BASE_URL);
        Call<List<UserLocationDTO>> serviceCall = service.sendLocation("1",
                "0", String.valueOf(MainUtils.getBatteryStatus()), MainUtils.CONTENT_TYPE, Prefs.getString(MainUtils.EMP_TYPE), locationDTOList);
        serviceCall.enqueue(new Callback<List<UserLocationDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserLocationDTO>> call, @NonNull Response<List<UserLocationDTO>> response) {
                iLocationResponse.onResponse(response);
                if (response.code() == 200) {
                    /*assert response.body() != null;*/
                    if (response.body().size() > 0)
                        Log.e(TAG, "onResponse: " + response.body().get(0).getMessage());

                } else if (response.code() == 500)
                    Log.e(TAG, "onResponse: " + response.message());
            }

            @Override
            public void onFailure(@NonNull Call<List<UserLocationDTO>> call, @NonNull Throwable t) {
                iLocationResponse.onFailure(t);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void sendOfflineLocations(List<UserLocationDTO> locationsList, ILocationResponse iLocationResponse) {

        for (UserLocationDTO userLoc : locationsList
        ) {
            Log.e(TAG, "sendOfflineLocations: offlineId: " + userLoc.getOfflineId());
        }
        UserLocationWebservice service = RetrofitClient.createService(UserLocationWebservice.class, MainUtils.BASE_URL);
        Call<List<UserLocationDTO>> serviceCall = service.sendOfflineLocation("1",
                "0", String.valueOf(MainUtils.getBatteryStatus()), MainUtils.CONTENT_TYPE, Prefs.getString(MainUtils.EMP_TYPE), locationsList);
        serviceCall.enqueue(new Callback<List<UserLocationDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserLocationDTO>> call, @NonNull Response<List<UserLocationDTO>> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    iLocationResponse.onResponse(response);
                    Log.e(TAG, "onResponse: " + response.message());
                } else if (response.code() == 500)
                    Log.e(TAG, "onResponse: " + response.message());
            }

            @Override
            public void onFailure(@NonNull Call<List<UserLocationDTO>> call, @NonNull Throwable t) {
                iLocationResponse.onFailure(t);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public interface ILocationResponse {
        void onResponse(Response<List<UserLocationDTO>> locationResponse);

        void onFailure(Throwable t);
    }
}
