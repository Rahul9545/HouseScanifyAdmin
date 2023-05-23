package com.appynitty.adminapp.viewmodels;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.models.EmployeeDetailsDTO;
import com.appynitty.adminapp.models.FilterDTO;
import com.appynitty.adminapp.models.SpecificUlbDTO;
import com.appynitty.adminapp.repositories.EmployeeDetailsRepository;
import com.appynitty.adminapp.repositories.UlbDataRepository;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

public class UlbDataViewModel extends ViewModel {
    private static final String TAG = "UlbDataViewModel";
    String appId = Prefs.getString(MainUtils.APP_ID, null);
    public UlbDataRepository ulbDataRepository = UlbDataRepository.getInstance();
    public EmployeeDetailsRepository employeeDetailsRepository = EmployeeDetailsRepository.getInstance();
    public MutableLiveData<SpecificUlbDTO> specificUlbMutableLiveData;
    public MutableLiveData<List<EmployeeDetailsDTO>> empDetailsListLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    public MutableLiveData<Integer> mProgressMutableData1 = new MutableLiveData<>();
    public MutableLiveData<Boolean> mSwipeStatus = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalHouseLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalHouseUpdatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalHouseUpdated_CurrentDayLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalPointLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalPointUpdatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalPointUpdated_CurrentDayLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalDumpLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalDumpUpdatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalDumpUpdated_CurrentDayLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalLiquidLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalLiquidUpdatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalLiquidUpdated_CurrentDayLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalStreetLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalStreetUpdatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalStreetUpdated_CurrentDayLiveData = new MutableLiveData<>();
    public MutableLiveData<String> edtFrmDateLiveData = new MutableLiveData<>();
    public MutableLiveData<String> edtToDateLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> btnFilterStateLiveData = new MutableLiveData<>();
    public MutableLiveData<FilterDTO> FilterLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> btnCancelStateLiveData = new MutableLiveData<>();


    public void init(String appId) {

        mProgressMutableData.setValue(View.VISIBLE);
        mProgressMutableData1.setValue(View.VISIBLE);

        ulbDataRepository.getUlbDataInfo(appId, new UlbDataRepository.IUlbDataResponse() {
            @Override
            public void onResponse(MutableLiveData<SpecificUlbDTO> specificUlbDataResponse) {
                mProgressMutableData.setValue(View.INVISIBLE);
                specificUlbMutableLiveData.setValue(specificUlbDataResponse.getValue());

                TotalHouseLiveData.setValue(specificUlbDataResponse.getValue().getTotalHouse());
                TotalHouseUpdatedLiveData.setValue(specificUlbDataResponse.getValue().getTotalHouseUpdated());
                TotalHouseUpdated_CurrentDayLiveData.setValue(specificUlbDataResponse.getValue().getTotalHouseUpdated_CurrentDay());

                TotalLiquidLiveData.setValue(specificUlbDataResponse.getValue().getTotalLiquid());
                TotalLiquidUpdatedLiveData.setValue(specificUlbDataResponse.getValue().getTotalLiquidUpdated());
                TotalLiquidUpdated_CurrentDayLiveData.setValue(specificUlbDataResponse.getValue().getTotalLiquidUpdated_CurrentDay());

                TotalStreetLiveData.setValue(specificUlbDataResponse.getValue().getTotalStreet());
                TotalStreetUpdatedLiveData.setValue(specificUlbDataResponse.getValue().getTotalStreetUpdated());
                TotalStreetUpdated_CurrentDayLiveData.setValue(specificUlbDataResponse.getValue().getTotalStreetUpdated_CurrentDay());

                TotalDumpLiveData.setValue(specificUlbDataResponse.getValue().getTotalDump());
                TotalDumpUpdatedLiveData.setValue(specificUlbDataResponse.getValue().getTotalDumpUpdated());
                TotalDumpUpdated_CurrentDayLiveData.setValue(specificUlbDataResponse.getValue().getTotalDumpUpdated_CurrentDay());
                Log.e(TAG, "onResponse: " + specificUlbDataResponse.getValue().getTotalHouse());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.setValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        employeeDetailsRepository.getEmpDetailsList(appId, new EmployeeDetailsRepository.IEmpDetailsListener() {
            @Override
            public void onResponse(MutableLiveData<List<EmployeeDetailsDTO>> empDetailsResponse) {
                mProgressMutableData1.setValue(View.INVISIBLE);
                mSwipeStatus.setValue(false);
                Log.e(TAG, "onResponse: EmpDetails= " + empDetailsResponse.getValue().toString());
                empDetailsListLiveData.setValue(empDetailsResponse.getValue());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData1.setValue(View.INVISIBLE);
                mSwipeStatus.setValue(false);
                Log.e(TAG, "onResponse: EmpDetails= " + t.getMessage());
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            /*case R.id.edt_from_date:
                Log.e(TAG, "onClick: frmDate!");
                edtFrmDateLiveData.setValue("");
                break;

            case R.id.edt_to_date:
                Log.e(TAG, "onClick: toDate!");
                edtToDateLiveData.setValue("");
                break;*/
            case R.id.txt_btn_cancel:
                Log.e(TAG, "onClick: cancel!");
                edtFrmDateLiveData.setValue("");
                break;

            case R.id.txt_btn_app_filter:
                Log.e(TAG, "onClick: Button!");
//                btnFilterStateLiveData.setValue(true);
                FilterDTO filterDTO = new FilterDTO(edtFrmDateLiveData.getValue(), edtToDateLiveData.getValue(), "0");
                FilterLiveData.setValue(filterDTO);
//                Log.e(TAG, "onClick: " + FilterLiveData.getValue().toString());
                break;

            default:
                // code block
        }


    }

    public MutableLiveData<SpecificUlbDTO> getSpecificUlbMutableLiveData() {
        if (specificUlbMutableLiveData == null) {
            specificUlbMutableLiveData = new MutableLiveData<>();
        }
        return specificUlbMutableLiveData;
    }

    public MutableLiveData<List<EmployeeDetailsDTO>> getEmpDetailsListLiveData() {
        if (empDetailsListLiveData == null) {
            empDetailsListLiveData = new MutableLiveData<>();
        }
        return empDetailsListLiveData;
    }

    public MutableLiveData<Boolean> getmProgressMutableData() {
        return mSwipeStatus;
    }

    public LiveData<String> getEdtFrmDateState() {
        return edtFrmDateLiveData;
    }

    public LiveData<String> getEdtToDateState() {
        return edtToDateLiveData;
    }

    public MutableLiveData<Boolean> getBtnFilterStateLiveData() {
        return btnFilterStateLiveData;
    }

    public MutableLiveData<FilterDTO> getFilterLiveData() {
        return FilterLiveData;
    }

    public void setFilteredData(Bundle bundle) {
        Log.e(TAG, "getFilteredItem: FromDate: " + bundle.get("frmDate") + ", toDate: " + bundle.get("toDate")
                + ", UserId:" + bundle.get("userId") + ", appId from bundle: " + bundle.get("appId") + ", appId from Prefs: " + appId);
        String frmDate = bundle.get("fromDate").toString();
        String toDate = bundle.get("toDate").toString();
        String userId = bundle.get("userId").toString();

        employeeDetailsRepository.getFilteredEmpDetails(frmDate, toDate, bundle.get("appId").toString(), userId, new EmployeeDetailsRepository.IEmpDetailsListener() {
            @Override
            public void onResponse(MutableLiveData<List<EmployeeDetailsDTO>> empDetailsResponse) {
                mProgressMutableData1.setValue(View.INVISIBLE);
                Log.e(TAG, "onResponse: EmpDetails= " + empDetailsResponse.getValue().toString());
                empDetailsListLiveData.setValue(empDetailsResponse.getValue());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData1.setValue(View.INVISIBLE);
                Log.e(TAG, "onResponse: EmpDetails= " + t.getMessage());
            }
        });

    }
}
