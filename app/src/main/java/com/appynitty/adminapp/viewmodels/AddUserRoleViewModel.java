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
import com.appynitty.adminapp.models.AddUserRoleRightDTO;
import com.appynitty.adminapp.models.AddUserRoleRightResult;
import com.appynitty.adminapp.models.DashboardDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.repositories.AddEmpRepository;
import com.appynitty.adminapp.repositories.AddUserRoleRepository;
import com.appynitty.adminapp.repositories.DashboardRepository;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

public class AddUserRoleViewModel extends ViewModel {
    private static final String TAG = "AddUserRoleViewModel";
    String appId = Prefs.getString(MainUtils.APP_ID, null);
    public MutableLiveData<String> EmpId = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> EmpName = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> LoginId = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> Password = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> EmpMobileNumber = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> EmpAddress = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> type = new MutableLiveData<>(); //liveData with dataBinding
    /*public MutableLiveData<String> isActive = new MutableLiveData<>(); //liveData with dataBinding*/
    public MutableLiveData<String> isActiveULB = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<List<DashboardDTO>> dashboardResponseLiveData = new MutableLiveData<>();
    public DashboardRepository dashboardRepository = DashboardRepository.getInstance();
    public MutableLiveData<String> addAndUpdateEmp = new MutableLiveData<>(); //liveData with dataBinding
    private Boolean status = false;


    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();

    public MutableLiveData<AddUserRoleRightDTO> addUserRoleLiveData;
    public MutableLiveData<List<AddUserRoleRightResult>> addUserRoleResultData = new MutableLiveData<>();
    public AddUserRoleRepository addUserRoleRepository = new AddUserRoleRepository();
    private Boolean cbIsActive = false;

    public MutableLiveData<AddUserRoleRightDTO> addUserRoleMutableLiveData() {
        if (addUserRoleLiveData == null) {
            addUserRoleLiveData = new MutableLiveData<>();
        }
        return addUserRoleLiveData;
    }


    public AddUserRoleViewModel(){
        getUlbData();
    }


    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_btn_save:
                Log.e(TAG, "save button : ");


                AddUserRoleRightDTO addUserRoleData = new AddUserRoleRightDTO(EmpId.getValue(),EmpName.getValue(),
                        EmpMobileNumber.getValue(),EmpAddress.getValue(),LoginId.getValue(),Password.getValue(),
                        type.getValue(),cbIsActive.toString(),isActiveULB.getValue());
                addUserRoleLiveData.setValue(addUserRoleData);

                if (EmpId.getValue() != null || EmpName.getValue() != null
                        || EmpMobileNumber.getValue() != null || EmpAddress.getValue() != null
                        || LoginId.getValue() != null || Password.getValue() != null
                        || type.getValue() != null || isActiveULB.getValue() != null) {
                    addUserRoleRepository.addUserSave(addUserRoleLiveData, new AddUserRoleRepository.IAddUserResponse() {
                        @Override
                        public void onResponse(List<AddUserRoleRightResult> addUserResponse) {
                            mProgressMutableData.postValue(View.INVISIBLE);
                            if (addUserResponse != null) {
                                addUserRoleResultData.setValue(addUserResponse);
                                Log.e(TAG, "onResponse: " + addUserResponse.get(0).getMessage());
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
            case R.id.cb_isActive:
                cbIsActive = ((CheckBox) view).isChecked();
                Log.e(TAG, "checked: " + cbIsActive);
                break;
            default:
                // code block
        }
    }

    public void clearData() {
        EmpId.setValue("");
        EmpName.setValue("");
        EmpMobileNumber.setValue("");
        EmpAddress.setValue("");
        LoginId.setValue("");
        Password.setValue("");
        type.setValue("");
        cbIsActive = false;
        isActiveULB.setValue("");
    }

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

    public LiveData<List<AddUserRoleRightResult>> postAddEmpResponse() {
        return addUserRoleResultData;
    }


    public void updateUserRoleDetails(UserRoleModelDTO userRoleDetails) {
        Log.d(TAG, "updateUserRoleDetails: " + userRoleDetails.toString());
        addUserRoleRepository.addUserUpdate(userRoleDetails, new AddUserRoleRepository.IAddUserResponse() {
            @Override
            public void onResponse(List<AddUserRoleRightResult> addUserResponse) {
                Log.e(TAG, "onResponse: " + addUserResponse.get(0).getMessage());
                addUserRoleResultData.setValue(addUserResponse);

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<AddUserRoleRightResult>> getAddUserResultMutableData() {
        return addUserRoleResultData;
    }

    private void getUlbData() {
        mProgressMutableData.postValue(View.VISIBLE);
        dashboardRepository.getListOfULBs(status, new DashboardRepository.IDashboardResponse() {

            @Override
            public void onResponse(MutableLiveData<List<DashboardDTO>> dashboardResponse) {
                mProgressMutableData.setValue(View.INVISIBLE);
                dashboardResponseLiveData.setValue(dashboardResponse.getValue());
                Log.e(TAG, "add user rights : "+dashboardResponse.getValue());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.setValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<DashboardDTO>> getDashboardResponse() {
        if (dashboardResponseLiveData == null) {
            dashboardResponseLiveData = new MutableLiveData<>();
        }
        return dashboardResponseLiveData;
    }


}
