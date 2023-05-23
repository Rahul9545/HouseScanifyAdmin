package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.QREmployeeDTO;
import com.appynitty.adminapp.repositories.QREmpListRepo;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

public class QREmpListVM extends ViewModel {
    private static final String TAG = "QREmpListVM";
    public QREmpListRepo qrEmpListRepo = QREmpListRepo.getInstance();
    public MutableLiveData<String> text = new MutableLiveData<>();
    public MutableLiveData<ArrayList<QREmployeeDTO>> QREmpListLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> mProgressLiveData = new MutableLiveData<>();
    String appId = Prefs.getString(MainUtils.APP_ID);


    public QREmpListVM() {
        Log.e(TAG, "QREmpListVM: appId" + Prefs.getString(MainUtils.APP_ID));
        mProgressLiveData.postValue(View.VISIBLE);
        qrEmpListRepo.getQREmpsList(appId,new QREmpListRepo.IQREmpListResponse() {

            @Override
            public void onResponse(MutableLiveData<ArrayList<QREmployeeDTO>> qrEmpListLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                QREmpListLiveData.setValue(qrEmpListLiveData.getValue());
            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public MutableLiveData<ArrayList<QREmployeeDTO>> getQREmpListLiveData() {
        return QREmpListLiveData;
    }

    public MutableLiveData<Integer> getmProgressLiveData() {
        return mProgressLiveData;
    }
}
