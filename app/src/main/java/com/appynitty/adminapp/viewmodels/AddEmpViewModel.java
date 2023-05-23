package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.repositories.AddEmpRepository;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

public class AddEmpViewModel extends ViewModel {
    private static final String TAG = "AddEmpViewModel";
    String appId = Prefs.getString(MainUtils.APP_ID, null);
    public MutableLiveData<String> qrEmpId = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> qrEmpName = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> qrEmpMobileNumber = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> qrEmpAddress = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> qrEmpLoginId = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> qrEmpPassword = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> qrImoNo = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> addAndUpdateEmp = new MutableLiveData<>(); //liveData with dataBinding

    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();

    public MutableLiveData<AddEmpDTO> addEmpMutableLiveData;
    public MutableLiveData<List<AddEmpResult>> addEmpResultMutableData = new MutableLiveData<>();
    public AddEmpRepository addEmpRepository = new AddEmpRepository();
    private Boolean cbClearLogin = false;
    private Boolean cbIsActive = false;
    private boolean isValid;

    public MutableLiveData<AddEmpDTO> getAddEmpMutableLiveData() {
        if (addEmpMutableLiveData == null) {
            addEmpMutableLiveData = new MutableLiveData<>();
        }
        return addEmpMutableLiveData;
    }


    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_btn_save:
                Log.e(TAG, "save button : ");

                AddEmpDTO addEmpData = new AddEmpDTO(qrEmpId.getValue(), qrEmpName.getValue(), qrEmpMobileNumber.getValue(),
                        qrEmpAddress.getValue(), qrEmpLoginId.getValue(), qrEmpPassword.getValue(),
                        qrImoNo.getValue(), cbIsActive.toString());
                addEmpMutableLiveData.setValue(addEmpData);

                if (qrEmpName.getValue() != null && qrEmpName.getValue() != null && qrEmpMobileNumber.getValue() != null && qrEmpAddress.getValue() != null
                        && qrEmpLoginId.getValue() != null && qrEmpPassword.getValue() != null
                        && qrImoNo.getValue() != null) {
                    addEmpRepository.addEmpRemote(addEmpMutableLiveData, new AddEmpRepository.IAddEmpResponse() {
                        @Override
                        public void onResponse(List<AddEmpResult> addEmpResponse) {
                            mProgressMutableData.postValue(View.INVISIBLE);
                            if (addEmpResponse != null) {
                                addEmpResultMutableData.setValue(addEmpResponse);
                                Log.e(TAG, "onResponse: " + addEmpResponse.get(0).getMessage());
                                clearData();

                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            mProgressMutableData.postValue(View.INVISIBLE);
                            Log.e(TAG, "onFailure: " + t.getMessage());
                            clearData();
                        }
                    });
                }
                addAndUpdateEmp.setValue("Data saved successfully!");
                break;
            case R.id.cb_clear_login:
                cbClearLogin = ((CheckBox) view).isChecked();
                Log.e(TAG, "checked: " + cbClearLogin);
                break;
            case R.id.cb_isActive:
                cbIsActive = ((CheckBox) view).isChecked();
                Log.e(TAG, "checked: " + cbIsActive);
                break;
            default:
                // code block
        }
    }

    public void clearData() {
        qrEmpId.setValue("");
        qrEmpName.setValue("");
        qrEmpMobileNumber.setValue("");
        qrEmpAddress.setValue("");
        qrEmpLoginId.setValue("");
        qrEmpPassword.setValue("");
        qrImoNo.setValue("");
        cbIsActive = false;
    }

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

    public LiveData<List<AddEmpResult>> postAddEmpResponse() {
        return addEmpResultMutableData;
    }

    public void updateEmpDetails(EmpDModelDTO empDetails) {
        Log.d(TAG, "updateEmpDetails: " + empDetails.toString());
        addEmpRepository.updateEmpDetails(empDetails, new AddEmpRepository.IAddEmpResponse() {
            @Override
            public void onResponse(List<AddEmpResult> addEmpResponse) {
                Log.e(TAG, "onResponse: " + addEmpResponse.get(0).getMessage());
                addEmpResultMutableData.setValue(addEmpResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<AddEmpResult>> getAddEmpResultMutableData() {
        return addEmpResultMutableData;
    }
}
