package com.appynitty.adminapp.viewmodels;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.AttendanceDTO;
import com.appynitty.adminapp.repositories.AttendanceRepository;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

public class AttendanceViewModel extends ViewModel {
    private static final String TAG = "AttendanceViewModel";
    String appId = Prefs.getString(MainUtils.APP_ID, null);
    public MutableLiveData<Integer> totalEntries = new MutableLiveData<>();
    public MutableLiveData<List<AttendanceDTO>> attendanceResponseLiveData;
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    public MutableLiveData<Integer> mProgressMutableData1 = new MutableLiveData<>();
    public MutableLiveData<List<AttendanceDTO>> attendanceListLiveData = new MutableLiveData<>();
    public AttendanceRepository attendanceRepository = AttendanceRepository.getInstance();

    public AttendanceViewModel(String appId) {
        mProgressMutableData.postValue(View.VISIBLE);
        attendanceRepository.getListOfAttendance(appId,new AttendanceRepository.IAttendanceResponse() {
            @Override
            public void onResponse(MutableLiveData<List<AttendanceDTO>> attendanceResponse) {
                mProgressMutableData.setValue(View.INVISIBLE);
                totalEntries.setValue(attendanceResponse.getValue().size());
                Log.e(TAG,"Attendance List size: " + attendanceResponse.getValue().size());
                attendanceResponseLiveData.setValue(attendanceResponse.getValue());
                Log.e(TAG, "onResponse: " + attendanceResponse.getValue()/*.get(0).getUserName()
                        + attendanceResponse.getValue().get(1).getStartDate() + attendanceResponse.getValue().get(2).getEndDate()
                        + attendanceResponse.getValue().get(3).getHouseCount()
                        + attendanceResponse.getValue().get(4).getDumpYardCount()
                        + attendanceResponse.getValue().get(5).getLiquidCount()
                        + attendanceResponse.getValue().get(6).getStreetCount()*/);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.setValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });
    }


    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

    public MutableLiveData<List<AttendanceDTO>> getAttendanceResponseLiveData() {
        if (attendanceResponseLiveData == null) {
            attendanceResponseLiveData = new MutableLiveData<>();
        }
        return attendanceResponseLiveData;
    }

    public void setOnClick() {

    }

    public void setFilteredData(Bundle bundle) {
        Log.e(TAG, "getFilteredItem: FromDate: " + bundle.get("frmDate") + ", toDate: " + bundle.get("toDate")
                + ", UserId:" + bundle.get("userId") + ", appId from bundle: " + bundle.get("appId") + ", appId from Prefs: " + appId);
        String frmDate = bundle.get("fromDate").toString();
        String toDate = bundle.get("toDate").toString();
        String userId = bundle.get("userId").toString();

        attendanceRepository.getFilteredEmpDetails(frmDate, toDate, appId, userId, new AttendanceRepository.IAttendanceResponse() {
            @Override
            public void onResponse(MutableLiveData<List<AttendanceDTO>> attendanceResponse) {
                mProgressMutableData1.setValue(View.INVISIBLE);
                Log.e(TAG, "onResponse: filter Data AT = " + attendanceResponse.getValue().toString());
                Log.e(TAG, "onResponse: filter list size AT = " + attendanceResponse.getValue().size());
                totalEntries.setValue(attendanceResponse.getValue().size());
                attendanceListLiveData.setValue(attendanceResponse.getValue());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData1.setValue(View.INVISIBLE);
                Log.e(TAG, "onResponse: filter Data At= " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<AttendanceDTO>> getAttendanceFilterMutableLiveData() {
        if (attendanceListLiveData == null) {
            attendanceListLiveData = new MutableLiveData<>();
        }
        return attendanceListLiveData;
    }

    /*private Fragment getActivity() {
        AttendanceFragment attendanceFragment = new AttendanceFragment();
        return attendanceFragment;
    }*/

}
