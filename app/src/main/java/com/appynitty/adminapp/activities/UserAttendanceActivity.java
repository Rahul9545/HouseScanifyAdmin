package com.appynitty.adminapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.UserAMobileAdapter;
import com.appynitty.adminapp.adapters.UserAPcAdapter;
import com.appynitty.adminapp.adapters.UserRightsAdapter;
import com.appynitty.adminapp.databinding.ActivityUserAttendanceBinding;
import com.appynitty.adminapp.dialogs.FilterDialog;
import com.appynitty.adminapp.dialogs.FilterDialogFragment;
import com.appynitty.adminapp.models.UserAttendanceDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.repositories.UserAttendanceRepository;
import com.appynitty.adminapp.repositories.UserRoleRepository;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.UserAttendanceViewModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by Rahul Rokade
 * 21/11/2022
 * */
public class UserAttendanceActivity extends AppCompatActivity {
    private static final String TAG = "UserAttendanceActivity";
    private Context context;
    private LinearLayoutManager layoutManagerM, layoutManagerP;
    private ProgressBar loader;
    private TextView txtEntries, txtNoData;
    private ActivityUserAttendanceBinding binding;
    private UserRoleModelDTO userRoleRightDetails;
    private EditText edtSearchText;
    private RecyclerView recyclerUserAttendance;
    private Toolbar toolbar;
    private RadioGroup rdGroup;
    private RadioButton rdMobileUser, rdPcUser;
    private FilterDialogFragment filterDialog;
    private UserAttendanceViewModel userAttendanceViewModel;
    private UserAMobileAdapter userAMobileAdapter;
    private UserAPcAdapter userAPcAdapter;
    private RecyclerView mRecyclerView;
    private UserAttendanceRepository userAttendanceRepository;
    private List<UserAttendanceDTO> userMobileList, userPcList;
    private String qrEmpIdPc, qrEmpIdM;
    Bundle filterExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        /*setContentView(R.layout.activity_user_attendance);*/
    }

    private void init(){
        context = this;
        userAttendanceViewModel = new UserAttendanceViewModel();
        binding = DataBindingUtil.setContentView(UserAttendanceActivity.this, R.layout.activity_user_attendance);

        binding.toolbar.setTitle("User Attendance");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
             //   startActivity(new Intent(UserAttendanceActivity.this, DashboardActivity.class));
            }
        });

        userMobileList = new ArrayList<>();
        userPcList = new ArrayList<>();
        layoutManagerM = new LinearLayoutManager(context);
        layoutManagerP = new LinearLayoutManager(context);
        userAttendanceRepository  = new UserAttendanceRepository();

        binding.rdMobileUser.setChecked(true);
        binding.progressCircular.setVisibility(View.VISIBLE);
        userAttendanceRepository.getUserAttendanceMobileList(true, new UserAttendanceRepository.IUserAttendanceResponse() {
            @Override
            public void onResponse(MutableLiveData<List<UserAttendanceDTO>> userAttendanceResponse) {

                if (userAttendanceResponse != null){
                    binding.progressCircular.setVisibility(View.GONE);
                    binding.txtNoData.setVisibility(View.GONE);
                    userMobileList =  userAttendanceResponse.getValue();
                    Log.e(TAG, "onResponse userMobileList list : " + userMobileList);
                    setOnRecyclerViewM(userMobileList);
                    binding.txtEntries.setText(userAttendanceResponse.getValue().size() + " " + "Entries");
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                binding.progressCircular.setVisibility(View.GONE);
                binding.txtNoData.setVisibility(View.VISIBLE);
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });

        setOnRecyclerViewM(userMobileList);

        setOnClick();

    }

    private void setOnClick() {
        binding.rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                switch (checked){
                    case R.id.rd_mobile_user:
                        if (binding.rdMobileUser.isChecked()){
                            Log.e(TAG, " rdMobileUser call");
                            binding.progressCircular.setVisibility(View.VISIBLE);
                            userAttendanceRepository.getUserAttendanceMobileList(true, new UserAttendanceRepository.IUserAttendanceResponse() {
                                @Override
                                public void onResponse(MutableLiveData<List<UserAttendanceDTO>> userAttendanceResponse) {
                                        if (userAttendanceResponse != null){
                                            binding.progressCircular.setVisibility(View.GONE);
                                            binding.txtNoData.setVisibility(View.GONE);
                                            userMobileList =  userAttendanceResponse.getValue();
                                            Log.e(TAG, "onResponse userMobileList list : " + userMobileList);
                                            qrEmpIdM = String.valueOf(userPcList.get(1).getQrEmpId());
                                            setOnRecyclerViewM(userMobileList);
                                            binding.txtEntries.setText(userAttendanceResponse.getValue().size() + " " + "Entries");
                                        }
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    binding.progressCircular.setVisibility(View.GONE);
                                    binding.txtNoData.setVisibility(View.VISIBLE);
                                    Log.e(TAG, "onFailure: " + throwable.getMessage());
                                }
                            });

                        }
                        break;
                    case R.id.rd_pc_user:

                        if (binding.rdPcUser.isChecked()){
                            Log.e(TAG, " rdPcUser call");
                            binding.progressCircular.setVisibility(View.VISIBLE);
                            userAttendanceRepository.getUserAttendancePcList(false, new UserAttendanceRepository.IUserAttendanceResponse() {
                                @Override
                                public void onResponse(MutableLiveData<List<UserAttendanceDTO>> userAttendanceResponse) {
                                    if (userAttendanceResponse != null){
                                        binding.progressCircular.setVisibility(View.GONE);
                                        binding.txtNoData.setVisibility(View.GONE);
                                        userPcList =  userAttendanceResponse.getValue();
                                        Log.e(TAG, "onResponse userPcList list : " + userPcList);
                                        qrEmpIdPc = String.valueOf(userPcList.get(1).getQrEmpId());
                                        setOnRecyclerViewP(userPcList);
                                        binding.txtEntries.setText(userAttendanceResponse.getValue().size() + " " + "Entries");

                                    }
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    binding.progressCircular.setVisibility(View.GONE);
                                    binding.txtNoData.setVisibility(View.VISIBLE);
                                    Log.e(TAG, "onFailure: " + throwable.getMessage());
                                }
                            });
                        }
                        break;
                    default:
                }
            }
        });

        binding.cardFilterUserA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // openDialog();
            }
        });
    }


    private void setOnRecyclerViewM(List<UserAttendanceDTO> userMobileList){
        binding.progressCircular.setVisibility(View.GONE);
        binding.txtNoData.setVisibility(View.GONE);
        userAMobileAdapter = new UserAMobileAdapter(context,userMobileList);
        if (userRoleRightDetails != null){
            String userRoleEmpId = userRoleRightDetails.getEmpId();
            String empName = userRoleRightDetails.getEmpName();
            Log.i("Rahul", "setOnRecyclerViewM: "+userRoleEmpId+","+" "+qrEmpIdM);
            if (userRoleEmpId.matches(qrEmpIdM) ) {
                userAMobileAdapter.setEmpIdMList(empName);
            }
        }
        binding.recyclerUserAttendance.setLayoutManager(layoutManagerM);
        binding.recyclerUserAttendance.setAdapter(userAMobileAdapter);


    }
    private void setOnRecyclerViewP(List<UserAttendanceDTO> userPcList){
        binding.progressCircular.setVisibility(View.GONE);
        binding.txtNoData.setVisibility(View.GONE);
        userAPcAdapter = new UserAPcAdapter(context,userPcList);
        if (userRoleRightDetails != null){
            String userRoleEmpId = userRoleRightDetails.getEmpId();
            String empName = userRoleRightDetails.getEmpName();
            Log.i("Rahul", "setOnRecyclerViewP: "+userRoleEmpId+","+" "+qrEmpIdPc);
            if (userRoleEmpId.matches(qrEmpIdPc) ) {
                userAPcAdapter.setEmpIdPList(empName);
            }
        }
        binding.recyclerUserAttendance.setLayoutManager(layoutManagerP);
        binding.recyclerUserAttendance.setAdapter(userAPcAdapter);
    }

    private void openDialog() {
        filterDialog = new FilterDialogFragment("UserAttendanceFrag");

        filterDialog.setFilterDialogListener(new FilterDialog.FilterDialogInterface() {
            @Override
            public void onFilterDialogDismiss(String frmDate, String toDate, String userId) {
                Log.e(TAG, "onFilterDialogDismiss: fromDate: " + frmDate + " toDate: " + toDate + " userId: " + userId);
                filterExtras = new Bundle();
                filterExtras.putString("fromDate", frmDate);
                filterExtras.putString("toDate", toDate);
                filterExtras.putString("userId", userId);
                filterExtras.putString("appId", Prefs.getString(MainUtils.APP_ID, null));
                //attendanceViewModel.setFilteredData(filterExtras);
            }
        });

        //filterDialog.show(getChildFragmentManager(), TAG);
    }
}