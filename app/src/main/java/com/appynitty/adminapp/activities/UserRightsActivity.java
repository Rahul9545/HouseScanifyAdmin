package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.UserRightsAdapter;
import com.appynitty.adminapp.databinding.ActivityUserRightsBinding;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.repositories.EmpDRepository;
import com.appynitty.adminapp.repositories.UserRoleRepository;
import com.appynitty.adminapp.utils.MyViewModelFactory;
import com.appynitty.adminapp.viewmodels.AddEmpViewModel;
import com.appynitty.adminapp.viewmodels.EmpDViewModel;
import com.appynitty.adminapp.viewmodels.UserRightViewModel;
import com.appynitty.adminapp.viewmodels.UserRoleViewModel;

import java.util.ArrayList;
import java.util.List;


public class UserRightsActivity extends AppCompatActivity {
    private static final String TAG = "UserRightsActivity";
    private Context context;
    private LinearLayoutManager layoutManager;
    private ProgressBar loader;
    private TextView txtEntries, txtNoData;

    private EditText edtSearchText;
    private RecyclerView recyclerUserRights;
    private CardView crdAddEmp;
    private UserRightsAdapter adapter;
    private UserRightViewModel userRightViewModel;
    private ActivityUserRightsBinding binding;
    private Toolbar toolbar;
    List<UserRoleModelDTO> userRoleModelDTOList, activeUserList, inactiveUserList;
    private UserRoleViewModel userRoleViewModel;
    private UserRoleRepository userRoleRepository;
    private RadioGroup rdGroup;
    private RadioButton rdActiveUser, rdInActiveUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        context = this;
        binding = DataBindingUtil.setContentView(UserRightsActivity.this, R.layout.activity_user_rights);
        binding.setLifecycleOwner(this);

        binding.toolbar.setTitle("User Rights");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
               // startActivity(new Intent(UserRightsActivity.this, DashboardActivity.class));
            }
        });


        activeUserList = new ArrayList<>();
        inactiveUserList = new ArrayList<>();
        userRoleModelDTOList = new ArrayList<>();
        userRoleRepository = new UserRoleRepository();

        context = this;
        recyclerUserRights = findViewById(R.id.recycler_user_rights);
        loader = findViewById(R.id.progress_circular);
        rdGroup = findViewById(R.id.rd_group);
        rdActiveUser = findViewById(R.id.rd_active_user);
        rdInActiveUser = findViewById(R.id.rd_inactive_user);
        txtNoData = findViewById(R.id.txt_no_data);
        edtSearchText = findViewById(R.id.edt_search_user_right);

        crdAddEmp = findViewById(R.id.card_addEmp_user_right);
        txtEntries = findViewById(R.id.txt_entries);
        layoutManager = new LinearLayoutManager(context);
        txtNoData.setVisibility(View.VISIBLE);
        loader.setVisibility(View.VISIBLE);

        userRoleViewModel = new ViewModelProvider(this).get(UserRoleViewModel.class);
        binding.setUserRole(userRoleViewModel);

        binding.rdActiveUser.setChecked(true);
        Log.e(TAG, " reBtnActive call");
        binding.progressCircular.setVisibility(View.VISIBLE);
        userRoleRepository.getUserRoleActiveList(true, new UserRoleRepository.IUserRoleResponse() {
            @Override
            public void onResponse(MutableLiveData<List<UserRoleModelDTO>> userRoleResponse) {
                binding.progressCircular.setVisibility(View.GONE);
                binding.txtNoData.setVisibility(View.GONE);
                activeUserList =  userRoleResponse.getValue();
                Log.e(TAG, "onResponse active list : " + activeUserList);
                setOnRecycler(activeUserList);
                txtEntries.setText(userRoleResponse.getValue().size() + " " + "Entries");
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressCircular.setVisibility(View.GONE);
                binding.txtNoData.setVisibility(View.VISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        binding.edtSearchUserRight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkList(editable.toString());
            }
        });

        binding.rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {

                switch (checked){
                    case R.id.rd_active_user:
                        if (binding.rdActiveUser.isChecked()){
                            Log.e(TAG, " reBtnActive call");
                            binding.progressCircular.setVisibility(View.VISIBLE);
                            userRoleRepository.getUserRoleActiveList(true, new UserRoleRepository.IUserRoleResponse() {
                                @Override
                                public void onResponse(MutableLiveData<List<UserRoleModelDTO>> userRoleResponse) {
                                    binding.progressCircular.setVisibility(View.GONE);
                                    binding.txtNoData.setVisibility(View.GONE);
                                    activeUserList =  userRoleResponse.getValue();
                                    Log.e(TAG, "onResponse active list : " + activeUserList);
                                    setOnRecycler(activeUserList);
                                    txtEntries.setText(userRoleResponse.getValue().size() + " " + "Entries");
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    binding.progressCircular.setVisibility(View.GONE);
                                    binding.txtNoData.setVisibility(View.VISIBLE);
                                    Log.e(TAG, "onFailure: " + t.getMessage());
                                }
                            });

                        }
                        break;
                    case R.id.rd_inactive_user:

                        if (binding.rdInactiveUser.isChecked()){
                            Log.e(TAG, " reBtnInActive call");
                            binding.progressCircular.setVisibility(View.VISIBLE);
                            userRoleRepository.getUserRoleInactiveList(false, new UserRoleRepository.IUserRoleResponse() {
                                @Override
                                public void onResponse(MutableLiveData<List<UserRoleModelDTO>> userRoleResponse) {
                                    binding.progressCircular.setVisibility(View.GONE);
                                    binding.txtNoData.setVisibility(View.GONE);
                                    inactiveUserList =  userRoleResponse.getValue();
                                    Log.e(TAG, "onResponse active list : " + inactiveUserList);
                                    setOnRecycler(inactiveUserList);
                                    txtEntries.setText(userRoleResponse.getValue().size() + " " + "Entries");
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    binding.progressCircular.setVisibility(View.GONE);
                                    binding.txtNoData.setVisibility(View.VISIBLE);
                                    Log.e(TAG, "onFailure: " + t.getMessage());
                                }
                            });
                        }
                        break;
                    default:

                }
            }
        });


        setOnClick();

    }

    private void setOnClick() {

        crdAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AddUserDetailsActivity.class));
            }
        });

    }

    private void setOnRecycler(List<UserRoleModelDTO> userRoleModelDTOList) {
        binding.progressCircular.setVisibility(View.GONE);
        binding.txtNoData.setVisibility(View.GONE);
        adapter = new UserRightsAdapter(context,userRoleModelDTOList);
        binding.recyclerUserRights.setLayoutManager(layoutManager);
        binding.recyclerUserRights.setAdapter(adapter);
    }

    private void checkList(String text){

        switch(binding.rdGroup.getCheckedRadioButtonId()){
            case R.id.rd_active_user:
                filterActive(text);
                break;
            case R.id.rd_inactive_user:
                filterInActive(text);
                break;
            default:
        }
    }

    private void filterInActive(String text) {
        List<UserRoleModelDTO> searchedList = new ArrayList<>();

        for (UserRoleModelDTO item : inactiveUserList) {
            if (item.getEmpName().toLowerCase().contains(text.toLowerCase()) || item.getEmpMobileNumber().toLowerCase().contains(text.toLowerCase())) {
                searchedList.add(item);
            }
        }
        adapter.inActiveList(searchedList);
    }


    private void filterActive(String text) {
        List<UserRoleModelDTO> searchedList = new ArrayList<>();

        if (searchedList != null){
            for (UserRoleModelDTO item : activeUserList) {
                if (item != null){
                    if (item.getEmpName().toLowerCase().contains(text.toLowerCase()) || item.getEmpMobileNumber().toLowerCase().contains(text.toLowerCase())) {
                        searchedList.add(item);
                    }
                }

            }
        }

        adapter.activeList(searchedList);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: refresh");
        init();
    }


}