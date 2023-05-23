package com.appynitty.adminapp.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.QrImageStatusDTO;
import com.appynitty.adminapp.repositories.QrImageStatusRepo;

import java.util.List;

public class QrImageStatusViewModel extends ViewModel {
    private static final String TAG = "QrImageStatusViewModel";
    QrImageStatusRepo imageStatusRepo = QrImageStatusRepo.getInstance();
    MutableLiveData<List<QrImageStatusDTO>> QrImageStatusLivedata = new MutableLiveData<>();
    MutableLiveData<String> ErrorMessage = new MutableLiveData<>();
    MutableLiveData<Integer> ProgressStatus = new MutableLiveData<>();

    public void initApi(String appId, String houseId, Boolean status) {
        ProgressStatus.setValue(View.VISIBLE);
        imageStatusRepo.updateImageStatus(appId, houseId, status, new QrImageStatusRepo.IQrStatusUpdateResponse() {
            @Override
            public void onResponse(MutableLiveData<List<QrImageStatusDTO>> qrImgStatResponse) {
                ProgressStatus.setValue(View.GONE);
                QrImageStatusLivedata.setValue(qrImgStatResponse.getValue());
            }

            @Override
            public void onFailure(Throwable t) {
                ProgressStatus.setValue(View.GONE);
                ErrorMessage.setValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<List<QrImageStatusDTO>> getQrImageStatusLivedata() {
        return QrImageStatusLivedata;
    }

    public MutableLiveData<String> getErrorMessage() {
        return ErrorMessage;
    }

    public MutableLiveData<Integer> getProgressStatus() {
        return ProgressStatus;
    }
}
