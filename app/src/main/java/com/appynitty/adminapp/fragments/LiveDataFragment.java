package com.appynitty.adminapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.DashboardActivity;
import com.appynitty.adminapp.activities.HomeActivity;
import com.appynitty.adminapp.adapters.EmployeeDetailsAdapter;
import com.appynitty.adminapp.databinding.DialogFilterBinding;
import com.appynitty.adminapp.databinding.FragmentLiveDataBinding;
import com.appynitty.adminapp.dialogs.FilterDialog;
import com.appynitty.adminapp.dialogs.FilterDialogFragment;
import com.appynitty.adminapp.models.EmployeeDetailsDTO;
import com.appynitty.adminapp.models.SpecificUlbDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.UlbDataViewModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;


public class LiveDataFragment extends Fragment {
    private static final String TAG = "LiveDataFragment";
    private Context context;
    private View view;
    ImageButton homeButton;
    private FragmentLiveDataBinding binding;
    private DialogFilterBinding filterBinding;
    private LinearLayoutManager layoutManager;
    private EmployeeDetailsAdapter adapter;
    private RecyclerView recyclerView;
    private String appId, ulbName;
    private TextView tvDate;
    private EditText etSearchEmp;
    private CardView btnFilter;
    private FilterDialogFragment filterDialog;
    private List<EmployeeDetailsDTO> employeeDetailsList;
    HomeActivity activity;
    UlbDataViewModel ulbDataViewModel, filterDataViewModel;
    private String frmDate, toDate, userId;
    Bundle filterExtras;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_live_data, container, false);
            view = binding.getRoot();
            binding.setLifecycleOwner(this);
            init();
        }
        return view;
    }

    private void init() {
        activity = (HomeActivity) getActivity();

        Bundle results = activity.getUlbData();
        appId = results.getString("val1");
        ulbName = results.getString("val2");
        Log.e(TAG, "AppID from bundle: " + appId + " ULB: " + ulbName);
        Log.e(TAG, "AppID from Prefs: " + Prefs.getString(MainUtils.APP_ID, null) + " ULB: " + ulbName);
        Prefs.putString("QR_APP_ID", results.getString("val1"));

        employeeDetailsList = new ArrayList<>();
        context = getActivity();
        homeButton = getActivity().findViewById(R.id.ib_home);        //swaps
        homeButton.setImageResource(R.drawable.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DashboardActivity.class);
                startActivity(i);
            }
        });
        recyclerView = view.findViewById(R.id.empRecyclerView);
//        filterDialog = new FilterDialogFragment();
        tvDate = view.findViewById(R.id.txt_date);
        etSearchEmp = view.findViewById(R.id.edt_search_text);
        btnFilter = view.findViewById(R.id.card_filter);
        tvDate.setText(MainUtils.getDate());
//        ulbDataViewModel = ViewModelProviders.of(this,
//                new MyViewModelFactory(appId)).get(UlbDataViewModel.class);

        ulbDataViewModel = new ViewModelProvider(this).get(UlbDataViewModel.class);
        ulbDataViewModel.init(appId);
        binding.setUlbData(ulbDataViewModel);

        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ulbDataViewModel.init(appId);
//                binding.swiperefresh.setRefreshing(false);
            }
        });


        etSearchEmp.addTextChangedListener(new TextWatcher() {
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

        ulbDataViewModel.getSpecificUlbMutableLiveData().observe(getActivity(), new Observer<SpecificUlbDTO>() {
            @Override
            public void onChanged(SpecificUlbDTO specificUlbDTO) {
                Log.e(TAG, "onChanged: " + specificUlbDTO.getTotalHouse());
            }
        });

        ulbDataViewModel.getEmpDetailsListLiveData().observe(getActivity(), new Observer<List<EmployeeDetailsDTO>>() {
            @Override
            public void onChanged(List<EmployeeDetailsDTO> employeeDetailsDTOS) {
                employeeDetailsList.clear();
                for (EmployeeDetailsDTO emp : employeeDetailsDTOS) {
                    employeeDetailsList.add(new EmployeeDetailsDTO(emp.getEmpName(),
                            emp.getHouseCount(), emp.getLiquidCount(),
                            emp.getStreetCount(), emp.getDumpCount()));
                }
                setRecycler(employeeDetailsList);
            }
        });

        ulbDataViewModel.getmProgressMutableData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean state) {
                binding.swiperefresh.setRefreshing(state);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

       /* homeButton = getActivity().findViewById(R.id.ib_home);
        homeButton.setOnClickListener(view -> startActivity(new Intent(getActivity(), DashboardActivity.class)));*/

    }

    private void setRecycler(List<EmployeeDetailsDTO> employeeDetailsList) {
        layoutManager = new LinearLayoutManager(context);
        adapter = new EmployeeDetailsAdapter(context, employeeDetailsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void filter(String text) {
        List<EmployeeDetailsDTO> filteredList = new ArrayList<>();

        for (EmployeeDetailsDTO item : employeeDetailsList) {
            if (item.getEmpName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    private void openDialog() {
        filterDialog = new FilterDialogFragment("liveDataFrag");

        filterDialog.setFilterDialogListener(new FilterDialog.FilterDialogInterface() {
            @Override
            public void onFilterDialogDismiss(String frmDate, String toDate, String userId) {
                Log.e(TAG, "onFilterDialogDismiss: fromDate: " + frmDate + " toDate: " + toDate + " userId: " + userId);
                filterExtras = new Bundle();
                filterExtras.putString("fromDate", frmDate);
                filterExtras.putString("toDate", toDate);
                filterExtras.putString("userId", userId);
                filterExtras.putString("appId", Prefs.getString(MainUtils.APP_ID, null));
                ulbDataViewModel.setFilteredData(filterExtras);
                tvDate.setText(MainUtils.serverDate(frmDate));
            }
        });

        filterDialog.show(getChildFragmentManager(), TAG);
    }

}