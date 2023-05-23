package com.appynitty.adminapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.DashboardActivity;
import com.appynitty.adminapp.adapters.EmpListRecyclerAdapter;
import com.appynitty.adminapp.adapters.ReferenceIdSearchAdapter;
import com.appynitty.adminapp.databinding.FragmentEmpListBinding;
import com.appynitty.adminapp.dialogs.FilterDialog;
import com.appynitty.adminapp.dialogs.FilterDialogFragment;
import com.appynitty.adminapp.models.EmployeeDetailsDTO;
import com.appynitty.adminapp.models.ReferenceIdDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.repositories.EmployeeDetailsRepository;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.GetReferenceIdListWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpListFragment extends Fragment {
    private static final String TAG = "EmpListFragment";
    private LinearLayoutManager layoutManager;
    private EmpListRecyclerAdapter adapter;
    FragmentEmpListBinding binding;
    String fromDate = "";
    private List<EmployeeDetailsDTO> employeeDetailsList;
    public EmployeeDetailsRepository employeeDetailsRepository;
    AppCompatActivity activity;
    String appId;
    ImageButton homeButton;
    View view;
    private FilterDialogFragment filterDialog;

    //ReferenceID search variables goes here...
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private ReferenceIdSearchAdapter referenceIdSearchAdapter;
    String selectedText = "";

    public EmpListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEmpListBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        init();
        return view;
    }

    private void init() {
        if (!MainUtils.IS_CONNECTED)
            Toast.makeText(MainUtils.mainApplicationConstant, getResources().getString(R.string.strNoInternet), Toast.LENGTH_SHORT).show();


        employeeDetailsRepository = EmployeeDetailsRepository.getInstance();
        employeeDetailsList = new ArrayList<>();
        appId = Prefs.getString(MainUtils.APP_ID);
        binding.progressBar.setVisibility(View.VISIBLE);
        filterDialog = new FilterDialogFragment("EmpListFragment");
        activity = (AppCompatActivity) view.getContext();
        setReferenceIdFilter();
        homeButton = getActivity().findViewById(R.id.ib_home);
        homeButton.setImageResource(R.drawable.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DashboardActivity.class);
                startActivity(i);
//                activity.finish();
            }
        });
        getEmpList();
        binding.filterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.setFilterDialogListener(new FilterDialog.FilterDialogInterface() {
                    @Override
                    public void onFilterDialogDismiss(String frmDate, String toDate, String userId) {
                        Log.e(TAG, "onFilterDialogDismiss: fromDate: " + frmDate);

                        fromDate = frmDate;
                        binding.progressBar.setVisibility(View.VISIBLE);
                        employeeDetailsRepository.getFilteredEmpDetails(frmDate, frmDate, appId, String.valueOf(0), new EmployeeDetailsRepository.IEmpDetailsListener() {
                            @Override
                            public void onResponse(MutableLiveData<List<EmployeeDetailsDTO>> empDetailsResponse) {
                                employeeDetailsList.clear();
                                for (EmployeeDetailsDTO emp : Objects.requireNonNull(empDetailsResponse.getValue())) {
                                    Log.d(TAG, "onResponse: Name: " + emp.getEmpName());
                                    employeeDetailsList.add(emp);
                                }
                                binding.progressBar.setVisibility(View.GONE);
                                setRecyclerView(employeeDetailsList);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                binding.progressBar.setVisibility(View.GONE);
                                Log.e(TAG, "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });
                filterDialog.show(getChildFragmentManager(), TAG);
            }
        });
    }

    private void setReferenceIdFilter() {
        final AutoCompleteTextView referenceIdFilterView = binding.searchRefId;
        Bundle args = new Bundle();
        //Setting up the adapter for AutoSuggest
        referenceIdSearchAdapter = new ReferenceIdSearchAdapter(activity, android.R.layout.simple_dropdown_item_1line);
        referenceIdFilterView.setThreshold(2);
        referenceIdFilterView.setAdapter(referenceIdSearchAdapter);
        referenceIdFilterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedText = referenceIdSearchAdapter.getObject(position).toString();

                Log.e(TAG, "onItemClick: " + selectedText);
                args.putString("refId", selectedText);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ReferenceIdDetailsFragment();
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frame_layout, myFragment)
                        .commit();
            }
        });

        referenceIdFilterView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(binding.searchRefId.getText())) {
//                        makeApiCall(autoCompleteTextView.getText().toString());
                        Log.e(TAG, "handleMessage: " + binding.searchRefId.getText());
                        GetReferenceIdListWebService service = RetrofitClient.createService(GetReferenceIdListWebService.class, MainUtils.BASE_URL);
                        Call<List<ReferenceIdDTO>> referenceIdListCall = service.getReferenceIdList(Prefs.getString(MainUtils.APP_ID),
                                binding.searchRefId.getText().toString());
                        referenceIdListCall.enqueue(new Callback<List<ReferenceIdDTO>>() {
                            @Override
                            public void onResponse(Call<List<ReferenceIdDTO>> call, Response<List<ReferenceIdDTO>> response) {
                                assert response.body() != null;
                                List<String> refIdsList = new ArrayList<>();
                                for (ReferenceIdDTO referenceId : response.body()) {
                                    Log.e(TAG, "onResponse: " + referenceId.getHouseid());
                                    refIdsList.add(referenceId.getHouseid());
                                }
                                referenceIdSearchAdapter.setData(refIdsList);
                                referenceIdSearchAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<List<ReferenceIdDTO>> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                return false;
            }
        });
    }

    private void setRecyclerView(List<EmployeeDetailsDTO> employeeDetailsList) {
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new EmpListRecyclerAdapter(getActivity(), employeeDetailsList, fromDate);
        binding.empRecyclerView.setLayoutManager(layoutManager);
        binding.empRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void getEmpList() {
        employeeDetailsRepository.getEmpDetailsList(appId, new EmployeeDetailsRepository.IEmpDetailsListener() {

            @Override
            public void onResponse(MutableLiveData<List<EmployeeDetailsDTO>> empDetailsResponse) {
                for (EmployeeDetailsDTO emp :
                        Objects.requireNonNull(empDetailsResponse.getValue())) {
                    Log.d(TAG, "onResponse: " + emp.getEmpName() + "\n");
                    employeeDetailsList.add(emp);
                }
                binding.progressBar.setVisibility(View.GONE);
                setRecyclerView(employeeDetailsList);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}