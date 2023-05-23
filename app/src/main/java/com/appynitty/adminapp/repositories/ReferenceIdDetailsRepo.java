package com.appynitty.adminapp.repositories;

import android.util.Log;

import com.appynitty.adminapp.models.ReferenceIdDetailsDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.GetReferenceIdDetailsWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferenceIdDetailsRepo {
    private static final String TAG = "ReferenceIdDetailsRepo";
    private static final ReferenceIdDetailsRepo instance = new ReferenceIdDetailsRepo();

    public static ReferenceIdDetailsRepo getInstance() {
        return instance;
    }

    public void getReferenceIdDetails(String refId, IRefIdDetailsResponse iRefIdDetailsResponse) {
        GetReferenceIdDetailsWebService service = RetrofitClient.createService(GetReferenceIdDetailsWebService.class, MainUtils.BASE_URL);
        Call<List<ReferenceIdDetailsDTO>> referenceDetailsCall = service.getReferenceIdDetails(Prefs.getString(MainUtils.APP_ID), refId);
        referenceDetailsCall.enqueue(new Callback<List<ReferenceIdDetailsDTO>>() {
            @Override
            public void onResponse(Call<List<ReferenceIdDetailsDTO>> call, Response<List<ReferenceIdDetailsDTO>> response) {
                for (ReferenceIdDetailsDTO referenceIdDetails : response.body()) {
                    Log.e(TAG, "onResponse-Details: " + referenceIdDetails.ToString());
                    iRefIdDetailsResponse.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ReferenceIdDetailsDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iRefIdDetailsResponse.onFailure(t);
            }
        });
    }

    public interface IRefIdDetailsResponse {
        void onResponse(List<ReferenceIdDetailsDTO> refIdDetailsLive);

        void onFailure(Throwable errorLivedata);
    }
}
