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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.AddEmpActivity;
import com.appynitty.adminapp.activities.DashboardActivity;
import com.appynitty.adminapp.activities.HomeActivity;
import com.appynitty.adminapp.adapters.EmpDetailsAdapter;
import com.appynitty.adminapp.databinding.FragmentEmpDetailsBinding;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.repositories.EmpDRepository;
import com.appynitty.adminapp.utils.MyApplication;
import com.appynitty.adminapp.utils.MyViewModelFactory;
import com.appynitty.adminapp.viewmodels.EmpDViewModel;
import com.appynitty.adminapp.viewmodels.UlbDataViewModel;

import java.util.ArrayList;
import java.util.List;


public class EmpDetailsFragment extends Fragment {
    private static final String TAG = "EmpDetailsFragment";
    private Context context;
    private View view;
    private LinearLayoutManager layoutManager;
    private ProgressBar loader;
    private TextView txtEntries, txtNoData;
    private ImageView imgClear;
    private EditText edtSearchText;
    private RecyclerView recyclerEmpDetails;
    private EmpDetailsAdapter adapter;
    private CardView crdAddEmp;
    ImageButton homeButton; //s
    //binding part
    private FragmentEmpDetailsBinding empDetailsBinding;
    HomeActivity activity;
    MyApplication application;
    private String appId, ulbName;
    Bundle filterExtras;
    List<EmpDModelDTO> empDModelList, activeList, inactiveList;
    UlbDataViewModel ulbDataViewModel;
    EmpDViewModel empDViewModel;

    EmpDRepository empDRepository;
    boolean isSelect = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            empDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_emp_details, container, false);
            //view =inflater.inflate(R.layout.fragment_emp_details, container, false);
            view = empDetailsBinding.getRoot();
//            empDetailsBinding.setLifecycleOwner(this);
            init();
        }
        return view;
    }

    private void init() {

        /*if (!MainUtils.IS_CONNECTED)
            Toast.makeText(MainUtils.mainApplicationConstant, getResources().getString(R.string.strNoInternet), Toast.LENGTH_SHORT).show();*/

        activity = (HomeActivity) getActivity();
        activity.checkTheNet();
        Bundle results = activity.getUlbData();
        appId = results.getString("val1");
        ulbName = results.getString("val2");
        Log.e(TAG, "AppID: " + appId + " ULB: " + ulbName);
        context = getActivity();
        empDRepository = new EmpDRepository();
        homeButton = getActivity().findViewById(R.id.ib_home);        //swaps
        homeButton.setImageResource(R.drawable.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DashboardActivity.class);
                startActivity(i);
//                activity.finish();
            }
        });
        empDModelList = new ArrayList<>();
        inactiveList = new ArrayList<>();
        activeList = new ArrayList<>();

        recyclerEmpDetails = view.findViewById(R.id.recycler_emp_details_frag);
        loader = view.findViewById(R.id.progress_circular);
        txtNoData = view.findViewById(R.id.txt_no_data);
        edtSearchText = view.findViewById(R.id.edt_search_empD);
        imgClear = view.findViewById(R.id.img_close);
        crdAddEmp = view.findViewById(R.id.card_add_empD);
        txtEntries = view.findViewById(R.id.txt_entries);
        layoutManager = new LinearLayoutManager(context);
        txtNoData.setVisibility(View.VISIBLE);
        imgClear.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        empDViewModel = new ViewModelProvider(getActivity(), new MyViewModelFactory(appId)).get(EmpDViewModel.class);
        empDetailsBinding.setEmpDlist(empDViewModel);


        empDetailsBinding.rdActiveED.setChecked(true);
        Log.e(TAG, " reBtnActive call");
        empDRepository.getEmpDList(true, appId, new EmpDRepository.IEmpDResponse() {
            @Override
            public void onResponse(MutableLiveData<List<EmpDModelDTO>> empDResponse) {
                loader.setVisibility(View.GONE);
                activeList = empDResponse.getValue();
                Log.e(TAG, "onResponse active list : " + activeList);
                setRecycler(activeList);
                txtEntries.setText(empDResponse.getValue().size() + " " + "Entries");
            }

            @Override
            public void onFailure(Throwable t) {
                loader.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

       /* empDetailsBinding.edtSearchEmpD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
              *//*if (empDetailsBinding.rdGroup.getCheckedRadioButtonId() == R.id.rd_active_ED){
                  filterActive((String) charSequence);
              }*//*

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filterInActive(editable.toString());
                *//*switch(view.getId()){
                    case R.id.rd_active_ED:
                        filterActive(editable.toString());
                        break;
                    case R.id.rd_inactive_ED:
                        filterInActive(editable.toString());
                        break;
                }*//*
            }
        });*/


        empDetailsBinding.edtSearchEmpD.addTextChangedListener(new TextWatcher() {
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


        empDetailsBinding.rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {

                switch (checked) {
                    case R.id.rd_active_ED:
                        if (empDetailsBinding.rdActiveED.isChecked()) {
                            Log.e(TAG, " reBtnActive call");
                            empDRepository.getEmpDList(true, appId, new EmpDRepository.IEmpDResponse() {
                                @Override
                                public void onResponse(MutableLiveData<List<EmpDModelDTO>> empDResponse) {
                                    loader.setVisibility(View.GONE);
                                    activeList = empDResponse.getValue();
                                    Log.e(TAG, "onResponse active list : " + activeList);
                                    setRecycler(activeList);
                                    txtEntries.setText(empDResponse.getValue().size() + " " + "Entries");
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    loader.setVisibility(View.GONE);
                                    Log.e(TAG, "onFailure: " + t.getMessage());
                                }
                            });

                        }
                        break;
                    case R.id.rd_inactive_ED:

                        if (empDetailsBinding.rdInactiveED.isChecked()) {
                            Log.e(TAG, " reBtnInActive call");
                            empDRepository.getEmpDListIN(false, appId, new EmpDRepository.IEmpDResponse() {
                                @Override
                                public void onResponse(MutableLiveData<List<EmpDModelDTO>> empDResponse) {
                                    loader.setVisibility(View.GONE);
                                    inactiveList = empDResponse.getValue();
                                    Log.e(TAG, "onResponse Inactive list : " + inactiveList);
                                    setRecycler(inactiveList);
                                    txtEntries.setText(empDResponse.getValue().size() + " " + "Entries");
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    loader.setVisibility(View.GONE);
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
        empDetailsBinding.cardAddEmpD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(getContext(), AddEmpActivity.class));
            }
        });
    }

    private void setRecycler(List<EmpDModelDTO> empDModelList) {
        empDetailsBinding.progressCircular.setVisibility(View.GONE);
        empDetailsBinding.txtNoData.setVisibility(View.GONE);
        adapter = new EmpDetailsAdapter(context, empDModelList);
        empDetailsBinding.recyclerEmpDetailsFrag.setLayoutManager(layoutManager);
        empDetailsBinding.recyclerEmpDetailsFrag.setAdapter(adapter);
    }


    private void checkList(String text) {

        switch (empDetailsBinding.rdGroup.getCheckedRadioButtonId()) {
            case R.id.rd_active_ED:
                filterActive(text);
                break;
            case R.id.rd_inactive_ED:
                filterInActive(text);
                break;
            default:
        }
    }

    private void filterInActive(String text) {
        List<EmpDModelDTO> searchedList = new ArrayList<>();

        for (EmpDModelDTO item : inactiveList) {
            if (item.getQrEmpName().toLowerCase().contains(text.toLowerCase()) || item.getQrEmpMobileNumber().toLowerCase().contains(text.toLowerCase())) {
                searchedList.add(item);
            }
        }
        adapter.inActiveList(searchedList);
    }


    private void filterActive(String text) {
        List<EmpDModelDTO> searchedList = new ArrayList<>();

        for (EmpDModelDTO item : activeList) {
            if (item.getQrEmpName().toLowerCase().contains(text.toLowerCase()) || item.getQrEmpMobileNumber().toLowerCase().contains(text.toLowerCase())) {
                searchedList.add(item);
            }
        }
        adapter.activeList(searchedList);
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }
}