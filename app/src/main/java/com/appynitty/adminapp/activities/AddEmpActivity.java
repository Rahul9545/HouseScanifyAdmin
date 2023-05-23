package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ActivityAddEmpBinding;
import com.appynitty.adminapp.databinding.UpdateEmpLayoutBinding;
import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.AddEmpViewModel;

import java.util.List;


public class AddEmpActivity extends AppCompatActivity {
    String TAG = "AddEmpActivity";
    String reqStatus = "", message = "";
    private Context context;
    private ActivityAddEmpBinding activityAddEmpBinding;
    private UpdateEmpLayoutBinding updateEmpLayoutBinding;
    private View view;
    private Toolbar toolbar;
    private List<EmpDModelDTO> empDModelDTOList;
    private AddEmpDTO addEmpModelDto;
    private EmpDModelDTO empDetails;
    private AddEmpViewModel addEmpViewModel;
    String empName, empMobile, empAdd, empUsername, empPass, empLoginImei, empId;
    int empIdAdapter;
    String empNameAdapter;
    boolean isActiveChecked, isClearChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddEmpBinding = ActivityAddEmpBinding.inflate(getLayoutInflater());
        updateEmpLayoutBinding = UpdateEmpLayoutBinding.inflate(getLayoutInflater());
        addEmpViewModel = new ViewModelProvider(this).get(AddEmpViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra("qrEmpDetails")) {
            view = updateEmpLayoutBinding.getRoot();
            setContentView(view);
            empDetails = (EmpDModelDTO) getIntent().getSerializableExtra("qrEmpDetails");
            updateEmpLayoutBinding.setEmpDetails(empDetails);
            activityAddEmpBinding.setLifecycleOwner(this);

            //custom toolbar added
            updateEmpLayoutBinding.rlCustomToolbar.txtTitle.setText(R.string.updateEmpDetails);
            updateEmpLayoutBinding.rlCustomToolbar.imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();
                    Log.e(TAG, "onClick: back button!");
                }
            });

            updateEmpLayoutBinding.btnEmpUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    empDetails.setActive(updateEmpLayoutBinding.cbIsActive.isChecked());
                    addEmpViewModel.updateEmpDetails(empDetails);
                }
            });

            updateEmpLayoutBinding.cbClearLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    empDetails.setImoNo(null);
                }
            });

        } else {
            view = activityAddEmpBinding.getRoot();
            setContentView(view);
            activityAddEmpBinding.setLifecycleOwner(this);
//            addEmpViewModel = ViewModelProviders.of(this).get(AddEmpViewModel.class);
            activityAddEmpBinding.setAddEmpViewModel(addEmpViewModel);

            //custom toolbar added
            activityAddEmpBinding.rlCustomToolbar.txtTitle.setText(R.string.addEmp);
            activityAddEmpBinding.rlCustomToolbar.imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            init();
        }

        addEmpViewModel.getAddEmpResultMutableData().observe(this, new Observer<List<AddEmpResult>>() {
            @Override
            public void onChanged(List<AddEmpResult> addEmpResults) {
                Log.e(TAG, "onChanged: " + addEmpResults.get(0).getMessage());
                message = addEmpResults.get(0).getMessage();
                reqStatus = addEmpResults.get(0).getStatus();
                if (reqStatus.matches(MainUtils.STATUS_SUCCESS)) {
                    MainUtils.success(message, Toast.LENGTH_SHORT);
                    finish();
                } else {
                    MainUtils.warning(message, Toast.LENGTH_SHORT);
                }
            }
        });

    }

    private void init() {
        context = this;
        /*empDModelDTOList = new ArrayList<>();*/
        addEmpModelDto = new AddEmpDTO();
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e(TAG, "Device id: " + androidID);
        activityAddEmpBinding.edtEmpLoginNum.setText(androidID);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("imiNo", androidID);
        editor.apply();

        activityAddEmpBinding.cbIsActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        addEmpViewModel.getAddEmpMutableLiveData().observe(this, new Observer<AddEmpDTO>() {
            @Override
            public void onChanged(AddEmpDTO addEmpDTO) {
                addEmpDTO.setQrEmpId("");
                addEmpDTO.setImoNo(androidID);
                /*if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getQrEmpName())) {
                    activityAddEmpBinding.edtEmpName.setError("Please enter employee name!");
                    activityAddEmpBinding.edtEmpName.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getQrEmpMobileNumber())) {
                    activityAddEmpBinding.edtEmpMobile.setError("Please enter employee mobile number!");
                    activityAddEmpBinding.edtEmpMobile.requestFocus();
                }*//*else if (!addEmpDTO.isEmpMobileValid()) {
                    activityAddEmpBinding.edtEmpMobile.setError("please enter valid mobile number");
                    activityAddEmpBinding.edtEmpMobile.requestFocus();
                }*//*else if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getQrEmpAddress())) {
                    activityAddEmpBinding.edtEmpAddress.setError("Please enter employee address!");
                    activityAddEmpBinding.edtEmpAddress.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getQrEmpLoginId())) {
                    activityAddEmpBinding.edtEmpUsername.setError("Please enter employee username!");
                    activityAddEmpBinding.edtEmpUsername.requestFocus();
                }else if (!addEmpDTO.isEmpUsernameValid()) {
                    activityAddEmpBinding.edtEmpUsername.setError("Username must contain at least 4 digits");
                    activityAddEmpBinding.edtEmpUsername.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getQrEmpPassword())) {
                    activityAddEmpBinding.edtEmpPassword.setError("Please enter employee password!");
                    activityAddEmpBinding.edtEmpPassword.requestFocus();
                }else if (!addEmpDTO.isEmpPassValid()) {
                    activityAddEmpBinding.edtEmpPassword.setError("Password must contain at least 4 digits");
                    activityAddEmpBinding.edtEmpPassword.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getImoNo())) {
                    activityAddEmpBinding.edtEmpLoginNum.setError("Your device id not found!");
                    activityAddEmpBinding.edtEmpLoginNum.requestFocus();*/
                if (activityAddEmpBinding.edtEmpName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee name", Toast.LENGTH_SHORT).show();

                } else if (activityAddEmpBinding.edtEmpMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee mobile number", Toast.LENGTH_SHORT).show();
                } else if (activityAddEmpBinding.edtEmpMobile.getText().toString().length() < 10) {
                    Toast.makeText(context, "Please enter valid employee mobile number", Toast.LENGTH_SHORT).show();
                } else if (activityAddEmpBinding.edtEmpAddress.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee address", Toast.LENGTH_SHORT).show();
                } else if (activityAddEmpBinding.edtEmpUsername.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee username", Toast.LENGTH_SHORT).show();
                } else if (activityAddEmpBinding.edtEmpUsername.getText().toString().length() < 4) {
                    Toast.makeText(context, "Username must contain at least 4 digits", Toast.LENGTH_SHORT).show();
                } else if (activityAddEmpBinding.edtEmpPassword.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee password", Toast.LENGTH_SHORT).show();
                } else if (activityAddEmpBinding.edtEmpPassword.getText().toString().length() < 4) {
                    Toast.makeText(context, "Password must contain at least 4 digits", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "onChanged: qrEmpId: " + addEmpDTO.getQrEmpId() + " EmpName: " + addEmpDTO.getQrEmpName()
                            + " EmpMobile: " + addEmpDTO.getQrEmpMobileNumber() + " EmpAddress: " + addEmpDTO.getQrEmpAddress()
                            + " EmpUsername: " + addEmpDTO.getQrEmpLoginId() + " password: " + addEmpDTO.getQrEmpPassword()
                            + " empDeviceId: " + addEmpDTO.getImoNo() + " EmpIsActiveStatus: " + addEmpDTO.getIsActive()
                    );
                }
            }
        });

        addEmpViewModel.postAddEmpResponse().observe(this, new Observer<List<AddEmpResult>>() {
            @Override
            public void onChanged(List<AddEmpResult> addEmpResults) {
                if (addEmpResults != null && addEmpResults.get(0).getStatus() != null) {
                    Log.e(TAG, "onChanged: status: " + addEmpResults.get(0).getStatus());
                    reqStatus = addEmpResults.get(0).getStatus();
                    if (reqStatus.equals("success")) {
                        MainUtils.success(addEmpResults.get(0).getMessage(), Toast.LENGTH_SHORT);
                        finish();
                    } else if (reqStatus.equals("error")) {
                        MainUtils.error(addEmpResults.get(0).getMessage(), Toast.LENGTH_SHORT);
                    }
                } else {
                    MainUtils.error("an error has occurred", Toast.LENGTH_SHORT);
                }
            }
        });

    }


    private void setOnClick() {
        if (empDModelDTOList != null) {
            if (empId != null) {
                activityAddEmpBinding.txtBtnSave.setText("Updated");
                activityAddEmpBinding.txtBtnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        Toast.makeText(context, "Employee data updated successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        if (empDModelDTOList.isEmpty()) {
            activityAddEmpBinding.txtBtnSave.setText("Save");
            activityAddEmpBinding.txtBtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Toast.makeText(context, "Employee data saved successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void setData() {

    }
}