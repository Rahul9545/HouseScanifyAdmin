package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.QrImageStatusDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.UpdateQrImageStatusWebservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrImageStatusRepo {
    private static final String TAG = "QrImageStatusRepo";
    private static final QrImageStatusRepo instance = new QrImageStatusRepo();
    private final MutableLiveData<QrImageStatusDTO> QrImageStatusResponse = new MutableLiveData<>();
    private final QrImageStatusDTO imageStatusObj = new QrImageStatusDTO();
    MutableLiveData<List<QrImageStatusDTO>> QrImgStatusLiveData = new MutableLiveData<>();
    private final List<QrImageStatusDTO> reqBody = new ArrayList<>();

    public static QrImageStatusRepo getInstance() {
        return instance;
    }

    public void updateImageStatus(String appId, String refId, Boolean status, IQrStatusUpdateResponse iQrStatusUpdateResponse) {
        imageStatusObj.setReferanceId(refId);
        imageStatusObj.setQRStatus(status);
        if (!reqBody.isEmpty()) {
            reqBody.clear();
        }
        reqBody.add(imageStatusObj);
        UpdateQrImageStatusWebservice service = RetrofitClient.createService(UpdateQrImageStatusWebservice.class, MainUtils.BASE_URL);
        Call<List<QrImageStatusDTO>> apiCall = service.setQrImageStatus(MainUtils.CONTENT_TYPE, appId, reqBody);
        apiCall.enqueue(new Callback<List<QrImageStatusDTO>>() {
            @Override
            public void onResponse(Call<List<QrImageStatusDTO>> call, @NonNull Response<List<QrImageStatusDTO>> response) {
                QrImgStatusLiveData.setValue(response.body());
                iQrStatusUpdateResponse.onResponse(QrImgStatusLiveData);
                Log.e(TAG, "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<QrImageStatusDTO>> call, Throwable t) {
                iQrStatusUpdateResponse.onFailure(t);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public interface IQrStatusUpdateResponse {
        void onResponse(MutableLiveData<List<QrImageStatusDTO>> qrImgStatResponse);

        void onFailure(Throwable t);
    }
}
