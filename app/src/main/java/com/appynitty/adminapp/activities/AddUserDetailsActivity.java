package com.appynitty.adminapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.UlbListAdapter;
import com.appynitty.adminapp.databinding.ActivityAddUserDetailsBinding;
import com.appynitty.adminapp.databinding.ActivityUpdateUserDetailsBinding;
import com.appynitty.adminapp.models.AddUserRoleRightDTO;
import com.appynitty.adminapp.models.AddUserRoleRightResult;
import com.appynitty.adminapp.models.DashboardDTO;
import com.appynitty.adminapp.models.UlbDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.AddUserRoleViewModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;


/*****
 * Created by rahul rokade
 * */
public class AddUserDetailsActivity extends AppCompatActivity implements UlbListAdapter.UlbListAdapterInterface {
    String TAG = "AddUserDetailsActivity";
    String reqStatus = "", message = "";
    private Context context;
    private LinearLayoutManager layoutManager, layoutManagerUpdate;
    private ProgressBar loader;
    private TextView txtEntries, txtNoData;
    private ImageView imgClear;
    private EditText edtSearchText;
    private RecyclerView recyclerUlbList;
    private UlbListAdapter adapter, adapterUpdate;
    private ActivityAddUserDetailsBinding binding;
    private ActivityUpdateUserDetailsBinding updateUserDetailsBinding;
    private List<UserRoleModelDTO> userRoleModelDTOS;
    private UserRoleModelDTO userRoleRightDetails;
    private AddUserRoleRightDTO addUserRoleRightDTO;
    private AddUserRoleViewModel addUserRoleViewModel;
    private View view;
    private Toolbar toolbar;
    private Spinner spinner;
    private List<UlbDTO> ulbList, ulbListUp;
    private CheckBox cbSelectAll;
    String selection;
    boolean isSelect = true;
    public boolean isAllChecked = false;
    int position;
    String appIdData;
    int totalCheck = 0;
    String empType ;
    String userRoleType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserDetailsBinding.inflate(getLayoutInflater());
        updateUserDetailsBinding = ActivityUpdateUserDetailsBinding.inflate(getLayoutInflater());

        addUserRoleViewModel = new ViewModelProvider(this).get(AddUserRoleViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra("userRoleRightsDetails")) {
            view = updateUserDetailsBinding.getRoot();
            setContentView(view);
            userRoleRightDetails = (UserRoleModelDTO) getIntent().getSerializableExtra("userRoleRightsDetails");
            updateUserDetailsBinding.setUpdateUserRoleDetails(userRoleRightDetails);
            Log.d(TAG, "onCreate: "+userRoleRightDetails);
            updateUserDetailsBinding.setLifecycleOwner(this);

            //custom toolbar added
            updateUserDetailsBinding.rlCustomToolbar.txtTitle.setText("Update User Right");
            updateUserDetailsBinding.rlCustomToolbar.imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });


            init();

            if (userRoleRightDetails.getType().equals("A")){
                Log.e("RahulDev", "getType: "+userRoleRightDetails.getType().toString() );
                updateUserDetailsBinding.edtEmpType1.setText(getResources().getString(R.string.str_user_role_admin));
            }else if (userRoleRightDetails.getType().equals("SA")){
                Log.e("RahulDev", "getType: "+userRoleRightDetails.getType().toString() );
                updateUserDetailsBinding.edtEmpType1.setText(getResources().getString(R.string.str_usr_role_sub_admin));
                updateUserDetailsBinding.linearL.setVisibility(View.VISIBLE);
            }


        } else {
            view = binding.getRoot();
            setContentView(view);
            binding.setLifecycleOwner(this);
//            addEmpViewModel = ViewModelProviders.of(this).get(AddEmpViewModel.class);
            binding.setAddUserDetailsData(addUserRoleViewModel);

            //custom toolbar added
            binding.rlCustomToolbar.txtTitle.setText("Add User Role Right");
            binding.rlCustomToolbar.imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            init();
        }

        addUserRoleViewModel.getAddUserResultMutableData().observe(this, new Observer<List<AddUserRoleRightResult>>() {
            @Override
            public void onChanged(List<AddUserRoleRightResult> addUserRoleRightResults) {
                Log.e(TAG, "onChanged: " + addUserRoleRightResults.get(0).getMessage());
                message = addUserRoleRightResults.get(0).getMessage();
                reqStatus = addUserRoleRightResults.get(0).getStatus();

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
        addUserRoleRightDTO = new AddUserRoleRightDTO();
        ulbList = new ArrayList<>();
        ulbListUp = new ArrayList<>();
        recyclerUlbList = findViewById(R.id.recycler_ulb_chkbox);
        loader = findViewById(R.id.progress_circular);
        txtNoData = findViewById(R.id.txt_no_data);
        edtSearchText = findViewById(R.id.edt_search_text);
        spinner = findViewById(R.id.spinner);
        cbSelectAll = findViewById(R.id.check_select_all);
        imgClear = findViewById(R.id.img_close);
        txtEntries = findViewById(R.id.txt_entries);
        layoutManager = new GridLayoutManager(context, 2);
        layoutManagerUpdate = new GridLayoutManager(context, 2);
        txtNoData.setVisibility(View.VISIBLE);
        imgClear.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        userRoleType = MainUtils.getUserRoleType();


        binding.edtEmpType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, binding.edtEmpType);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.user_role, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        binding.edtEmpType.setText(item.getTitle());
                        if (item.getTitle().equals(getResources().getString(R.string.str_user_role_admin))){
                            binding.linearL.setVisibility(View.GONE);
                        }else if (item.getTitle().equals(getResources().getString(R.string.str_usr_role_sub_admin))){
                            binding.linearL.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

        updateUserDetailsBinding.edtEmpType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final PopupMenu popup = new PopupMenu(context, updateUserDetailsBinding.edtEmpType1);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.user_role, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        updateUserDetailsBinding.edtEmpType1.setText(item.getTitle());
                        if (item.getTitle().equals(getResources().getString(R.string.str_user_role_admin))){
                            updateUserDetailsBinding.linearL.setVisibility(View.GONE);
                        }else if (item.getTitle().equals(getResources().getString(R.string.str_usr_role_sub_admin))){
                            updateUserDetailsBinding.linearL.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

        String empType = binding.edtEmpType.getText().toString();
        if (empType.matches(getResources().getString(R.string.str_user_role_admin))){
            addUserRoleRightDTO.setType("A");
            Prefs.putString(MainUtils.PREFS.USER_ROLE_TYPE,addUserRoleRightDTO.getType());
        }else if (empType.matches(getResources().getString(R.string.str_usr_role_sub_admin))){
            addUserRoleRightDTO.setType("SA");
            Prefs.putString(MainUtils.PREFS.USER_ROLE_TYPE,addUserRoleRightDTO.getType());
        }


        binding.edtSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        updateUserDetailsBinding.edtSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterUpdate(editable.toString());
            }
        });

        addUserRoleViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                binding.progressCircular.setVisibility(visibility);
            }
        });

        addUserRoleViewModel.getDashboardResponse().observe(this, new Observer<List<DashboardDTO>>() {

            @Override
            public void onChanged(List<DashboardDTO> dashboardResults) {
                ulbList.clear();
                for (int i = 0; i < dashboardResults.size(); i++) {
                    ulbList.add(new UlbDTO(dashboardResults.get(i).getUlb(),
                            dashboardResults.get(i).getAppid()));
                }
                setOnRecycler(ulbList);
            }
        });

        addUserRoleViewModel.getDashboardResponse().observe(this, new Observer<List<DashboardDTO>>() {

            @Override
            public void onChanged(List<DashboardDTO> dashboardResults) {
                ulbListUp.clear();
                for (int i = 0; i < dashboardResults.size(); i++) {
                    ulbListUp.add(new UlbDTO(dashboardResults.get(i).getUlb(),
                            dashboardResults.get(i).getAppid()));
                }
                Log.d(TAG, "onChanged: "+ulbListUp);
                setOnRecyclerUpdated(ulbListUp);
            }
        });


        addUserRoleViewModel.addUserRoleMutableLiveData().observe(this, new Observer<AddUserRoleRightDTO>() {
            @Override
            public void onChanged(AddUserRoleRightDTO addUserRoleRightDTO) {

                if (binding.checkSelectAll.isChecked()) {
                    addUserRoleRightDTO.setEmpId("");

                    /*StringBuilder sb = new StringBuilder();
                    for (UlbDTO s : ulbList) {
                        sb.append(s.getAppId()+",");
                    }
                    String result = sb.toString();
                    String ss = result.replaceAll(",$", "");
                    ArrayList<String> list = new ArrayList<String>();
                    list.add(ss);
                    addUserRoleRightDTO.setIsActiveULB(ss);*/
                }else {
                    addUserRoleRightDTO.setIsActiveULB(appIdData);
                }

                String empType = binding.edtEmpType.getText().toString().trim();
                if (empType.matches(getResources().getString(R.string.str_user_role_admin))){
                    addUserRoleRightDTO.setType("A");
                    MainUtils.setUserRoleType(addUserRoleRightDTO.getType());
                }else if (empType.matches(getResources().getString(R.string.str_usr_role_sub_admin))){
                    addUserRoleRightDTO.setType("SA");
                    MainUtils.setUserRoleType(addUserRoleRightDTO.getType());
                }
                if (MainUtils.getUserRoleType() != null && MainUtils.getUserRoleType().matches("A")){
                    binding.linearL.setVisibility(View.GONE);
                }else if (MainUtils.getUserRoleType() != null && MainUtils.getUserRoleType().matches("SA")){
                    binding.linearL.setVisibility(View.VISIBLE);
                }

                Log.e(TAG, addUserRoleRightDTO.getType());


                if (binding.edtEmpName.getText().toString().trim().isEmpty()) {
                    MainUtils.firstLatterCap(String.valueOf(binding.edtEmpName.getText().toString().trim().isEmpty()));
                    Toast.makeText(context, "Please enter employee name", Toast.LENGTH_SHORT).show();

                } else if (binding.edtEmpMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee mobile number", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpMobile.getText().toString().length() < 10) {
                    Toast.makeText(context, "Please enter valid employee mobile number", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpAddress.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee address", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpUsername.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee username", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpUsername.getText().toString().length() < 4) {
                    Toast.makeText(context, "Username must contain at least 4 digits", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpPassword.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee password", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpPassword.getText().toString().length() < 4) {
                    Toast.makeText(context, "Password must contain at least 4 digits", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "onChanged value provide: EmpId: " + addUserRoleRightDTO.getEmpId() + " EmpName: " + addUserRoleRightDTO.getEmpName()
                            + " EmpMobile: " + addUserRoleRightDTO.getEmpMobileNumber() + " EmpAddress: " + addUserRoleRightDTO.getEmpAddress()
                            + " EmpUsername: " + addUserRoleRightDTO.getLoginId() + " password: " + addUserRoleRightDTO.getPassword()
                            + " EmpIsActiveStatus: " + addUserRoleRightDTO.getIsActive()
                            + " ActiveUlb: " + addUserRoleRightDTO.getIsActiveULB()
                            + " EmpType: " + addUserRoleRightDTO.getType()
                    );
                }
            }
        });


        addUserRoleViewModel.postAddEmpResponse().observe(this, new Observer<List<AddUserRoleRightResult>>() {
            @Override
            public void onChanged(List<AddUserRoleRightResult> addUserResults) {
                if (addUserResults != null && addUserResults.get(0).getStatus() != null) {
                    Log.e(TAG, "onChanged: status: " + addUserResults.get(0).getStatus());
                    reqStatus = addUserResults.get(0).getStatus();
                    if (reqStatus.equals("success")) {
                        MainUtils.success(addUserResults.get(0).getMessage(), Toast.LENGTH_SHORT);
                        finish();
                    } else if (reqStatus.equals("error")) {
                        MainUtils.error(addUserResults.get(0).getMessage(), Toast.LENGTH_SHORT);
                    }
                } else {
                    MainUtils.error("an error has occurred", Toast.LENGTH_SHORT);
                }
            }
        });

        setOnClick();
    }

    private void updatedUserRightsData(){

        empType = updateUserDetailsBinding.edtEmpType1.getText().toString().trim();
        if (empType.matches(getResources().getString(R.string.str_user_role_admin))){
            userRoleRightDetails.setType("A");
        }else if (empType.matches(getResources().getString(R.string.str_usr_role_sub_admin))){
            userRoleRightDetails.setType("SA");
        }

        updateUserDetailsBinding.cbIsActive.setSelected(isSelect);
        userRoleRightDetails.setEmpName(MainUtils.firstLatterCap(updateUserDetailsBinding.edtEmpName.getText().toString()));
        userRoleRightDetails.setEmpAddress(MainUtils.firstLatterCap(updateUserDetailsBinding.edtEmpAddress.getText().toString()));
        userRoleRightDetails.setEmpMobileNumber(updateUserDetailsBinding.edtEmpMobile.getText().toString());
        userRoleRightDetails.setLoginId(updateUserDetailsBinding.edtEmpUsername.getText().toString());
        userRoleRightDetails.setPassword(updateUserDetailsBinding.edtEmpPassword.getText().toString());
        userRoleRightDetails.setActive(updateUserDetailsBinding.cbIsActive.isChecked());
        userRoleRightDetails.setIsActiveULB(appIdData);
        addUserRoleViewModel.updateUserRoleDetails(userRoleRightDetails);

        Log.e(TAG, "Updated User Rights value is : EmpId: " + userRoleRightDetails.getEmpId() + " EmpName: " + userRoleRightDetails.getEmpName()
                + " EmpMobile: " + userRoleRightDetails.getEmpMobileNumber() + " EmpAddress: " + userRoleRightDetails.getEmpAddress()
                + " EmpUsername: " + userRoleRightDetails.getLoginId() + " password: " + userRoleRightDetails.getPassword()
                + " ActiveUlb: " + userRoleRightDetails.getIsActiveULB()
                + " EmpType: " + userRoleRightDetails.getType()
        );
    }

    private boolean isValid(){

        if (updateUserDetailsBinding.edtEmpName.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please enter employee name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (updateUserDetailsBinding.edtEmpMobile.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please enter employee mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (updateUserDetailsBinding.edtEmpMobile.getText().toString().length() < 10) {
            Toast.makeText(context, "Please enter valid employee mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (updateUserDetailsBinding.edtEmpAddress.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please enter employee address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (updateUserDetailsBinding.edtEmpUsername.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please enter employee username", Toast.LENGTH_SHORT).show();
            return false;
        } else if (updateUserDetailsBinding.edtEmpUsername.getText().toString().length() < 4) {
            Toast.makeText(context, "Username must contain at least 4 digits", Toast.LENGTH_SHORT).show();
            return false;
        } else if (updateUserDetailsBinding.edtEmpPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please enter employee password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (updateUserDetailsBinding.edtEmpPassword.getText().toString().length() < 4) {
            Toast.makeText(context, "Password must contain at least 4 digits", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setOnClick() {
        binding.txtBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.checkSelectAll.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                adapter.setAllChecked(binding.checkSelectAll.isChecked());
                adapter.notifyDataSetChanged();

                if (binding.checkSelectAll.isChecked()) {
                    Log.e(TAG, "all ulb selected :" + binding.checkSelectAll.isChecked() + "," + appIdData.trim());


                } else {
                    adapter.notifyDataSetChanged();
                    Log.e(TAG, "all ulb not selected :" + binding.checkSelectAll.isChecked());
                }
            }
        });

        updateUserDetailsBinding.txtBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()){
                    updatedUserRightsData();
                }

            }
        });

        updateUserDetailsBinding.cbSelectAll.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                adapterUpdate.setAllCheckedUpdate(updateUserDetailsBinding.cbSelectAll.isChecked());
                adapterUpdate.notifyDataSetChanged();

                if (updateUserDetailsBinding.cbSelectAll.isChecked()) {
                    Log.e(TAG, "all updated ulb selected is :" + updateUserDetailsBinding.cbSelectAll.isChecked() + "," + appIdData.trim());

                } else {
                    adapterUpdate.notifyDataSetChanged();
                    Log.e(TAG, "all ulb not selected is :" + updateUserDetailsBinding.cbSelectAll.isChecked());
                }
            }
        });
    }

    /****
     * Add user right
     * Fix scroll adapter as well as
     * fix item selection cache size.
     * */
    @SuppressLint("NotifyDataSetChanged")
    private void setOnRecycler(List<UlbDTO> ulbList) {
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.GONE);
        adapter = new UlbListAdapter(context, ulbList, new UlbListAdapter.UlbListAdapterInterface() {
            @Override
            public void onItemClicked(String list) {
                appIdData = String.valueOf(list.trim().replaceAll("\\s",""));
                Log.e(TAG, "onItemClicked: "+appIdData.trim().replaceAll("\\s",""));
            }

            @Override
            public void onItemClickedUpdate(String listUp) {
                appIdData = String.valueOf(listUp.trim().replaceAll("\\s",""));
                Log.e(TAG, "onItemClicked: "+appIdData.trim().replaceAll("\\s",""));
            }
        });
        adapter.notifyDataSetChanged();
        binding.recyclerUlbChkbox.setItemViewCacheSize(ulbList.size());
        binding.recyclerUlbChkbox.setLayoutManager(layoutManager);
        binding.recyclerUlbChkbox.setAdapter(adapter);

    }
    /****
     * update user rights
     * Fix scroll adapter as well as
     * fix item selection cache size.
     * */
    private void setOnRecyclerUpdated(List<UlbDTO> ulbListUp) {
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.GONE);
        adapterUpdate = new UlbListAdapter(context, ulbListUp, new UlbListAdapter.UlbListAdapterInterface() {
            @Override
            public void onItemClicked(String list) {
                appIdData = String.valueOf(list.trim().replaceAll("\\s",""));
                Log.e(TAG, "onItemClickedUpdate: "+ appIdData.trim().replaceAll("\\s",""));
            }

            @Override
            public void onItemClickedUpdate(String listUp) {
                appIdData = String.valueOf(listUp.trim().replaceAll("\\s",""));
                Log.e(TAG, "onItemClickedUpdate: "+ appIdData.replace(" ",""));
            }
        });

        adapterUpdate.notifyDataSetChanged();
        if (userRoleRightDetails != null){
            adapterUpdate.setAppIdList(userRoleRightDetails.getIsActiveULB().trim());
        }
        updateUserDetailsBinding.recyclerUlbChkbox.setItemViewCacheSize(ulbListUp.size());
        updateUserDetailsBinding.recyclerUlbChkbox.setLayoutManager(layoutManagerUpdate);
        updateUserDetailsBinding.recyclerUlbChkbox.setAdapter(adapterUpdate);

    }


    private void filter(String text) {
        List<UlbDTO> filteredList = new ArrayList<>();

        for (UlbDTO item : ulbList) {
            if (item.getUlbName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    private void filterUpdate(String text) {
        List<UlbDTO> filteredList = new ArrayList<>();

        for (UlbDTO item : ulbListUp) {
            if (item.getUlbName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapterUpdate.filterList(filteredList);
    }

    @Override
    public void onItemClicked(String list) {

        AddUserRoleRightDTO userRoleModelDTO = new AddUserRoleRightDTO();
        userRoleModelDTO.setIsActiveULB(String.valueOf(list.trim()));
        appIdData = String.valueOf(list.trim());
    }

    @Override
    public void onItemClickedUpdate(String listUp) {
        UserRoleModelDTO userRoleModelDTO = new UserRoleModelDTO();
        userRoleModelDTO.setIsActiveULB(String.valueOf(listUp.trim()));
        appIdData = String.valueOf(listUp.trim().replaceAll("\\s",""));
    }
}