package com.appynitty.adminapp.fragments;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.DashboardActivity;
import com.appynitty.adminapp.activities.HomeActivity;
import com.appynitty.adminapp.adapters.AttendanceAdapter;
import com.appynitty.adminapp.databinding.FragmentAttendanceBinding;
import com.appynitty.adminapp.dialogs.FilterDialog;
import com.appynitty.adminapp.dialogs.FilterDialogFragment;
import com.appynitty.adminapp.models.AttendanceDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.utils.MyApplication;
import com.appynitty.adminapp.utils.MyViewModelFactory;
import com.appynitty.adminapp.viewmodels.AttendanceViewModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;


public class AttendanceFragment extends Fragment {
    private static final String TAG = "AttendanceFragment";
    private String empType = "";
    private Context context;
    private View view;
    private LinearLayoutManager layoutManager;
    private ProgressBar loader;
    private TextView txtEntries, txtNoData;
    private ImageView imgClear;
    ImageButton homeButton;  //swaps
    private EditText edtSearchText;
    private RecyclerView recyclerAttendance;
    private AttendanceAdapter adapter;
    private FilterDialogFragment filterDialog;
    private AttendanceViewModel attendanceViewModel;
    private FragmentAttendanceBinding attendanceBinding;
    HomeActivity activity;
    MyApplication application;
    private String appId, ulbName;
    private List<AttendanceDTO> attendanceDTOList;
    Bundle filterExtras;

    /*@Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!MainUtils.IS_CONNECTED)
            Toast.makeText(MainUtils.mainApplicationConstant, getResources().getString(R.string.strNoInternet), Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {

            attendanceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendance, container, false);
            view = attendanceBinding.getRoot();
            //here data must be an instance of the class MarsDataProvider
            attendanceBinding.setLifecycleOwner(this);

            /*view = inflater.inflate(R.layout.fragment_attendance, container, false);*/
            init();
        }
        return view;
    }

    private void init() {
        /*context = getActivity();*/
        activity = (HomeActivity) getActivity();
        assert activity != null;
        activity.checkTheNet();
        Bundle results = activity.getUlbData();
        appId = results.getString("val1");
        ulbName = results.getString("val2");
        Log.e(TAG, "AppID: " + appId + " ULB: " + ulbName);

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

        context = getActivity();

        attendanceDTOList = new ArrayList<>();
        recyclerAttendance = view.findViewById(R.id.recycler_attendance);
        loader = view.findViewById(R.id.progress_circular);
        txtNoData = view.findViewById(R.id.txt_no_data);
        edtSearchText = view.findViewById(R.id.edt_search_text_at);
        imgClear = view.findViewById(R.id.img_close);
        txtEntries = view.findViewById(R.id.txt_entries);
        layoutManager = new LinearLayoutManager(context);
        txtNoData.setVisibility(View.VISIBLE);
        imgClear.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);


        attendanceViewModel = new ViewModelProvider(getActivity(), new MyViewModelFactory(appId)).get(AttendanceViewModel.class);
        attendanceBinding.setAttendanceViewModel(attendanceViewModel);

        attendanceViewModel.mProgressMutableData.observe(activity, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                loader.setVisibility(integer);
            }
        });

        attendanceViewModel.getProgress().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                loader.setVisibility(integer);
            }
        });

        attendanceViewModel.getAttendanceResponseLiveData().observe(getViewLifecycleOwner(), new Observer<List<AttendanceDTO>>() {
            @Override
            public void onChanged(List<AttendanceDTO> attendanceDTOS) {
                Log.e(TAG, "onChanged: " + attendanceDTOS);
                attendanceDTOList.clear();
                attendanceBinding.progressCircular.setVisibility(View.VISIBLE);

                if (attendanceDTOList != null) {
                    attendanceBinding.recyclerAttendance.setVisibility(View.VISIBLE);
                    attendanceBinding.progressCircular.setVisibility(View.GONE);
                    attendanceBinding.txtNoData.setVisibility(View.GONE);
                    for (AttendanceDTO emp : attendanceDTOS) {
                        attendanceDTOList.add(new AttendanceDTO(emp.getStartDate(), emp.getStartTime(),
                                emp.getEndDate(), emp.getEndTime(), emp.getUserName(), emp.getHouseCount(),
                                emp.getLiquidCount(), emp.getStreetCount(), emp.getDumpYardCount()));
                    }
                    setRecycler(attendanceDTOList);
                } else {
                    attendanceBinding.recyclerAttendance.setVisibility(View.GONE);
                    attendanceBinding.progressCircular.setVisibility(View.GONE);
                    attendanceBinding.txtNoData.setVisibility(View.VISIBLE);
                }
            }
        });

        attendanceViewModel.getAttendanceFilterMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<AttendanceDTO>>() {
            @Override
            public void onChanged(List<AttendanceDTO> attendanceDTOS) {
                Log.e(TAG, "onChanged: " + attendanceDTOS);
                attendanceDTOList.clear();
                attendanceBinding.progressCircular.setVisibility(View.VISIBLE);

                if (attendanceDTOList != null) {
                    attendanceBinding.recyclerAttendance.setVisibility(View.VISIBLE);
                    attendanceBinding.progressCircular.setVisibility(View.GONE);
                    attendanceBinding.txtNoData.setVisibility(View.GONE);
                    for (AttendanceDTO emp : attendanceDTOS) {
                        attendanceDTOList.add(new AttendanceDTO(emp.getStartDate(), emp.getStartTime(),
                                emp.getEndDate(), emp.getEndTime(), emp.getUserName(), emp.getHouseCount(),
                                emp.getLiquidCount(), emp.getStreetCount(), emp.getDumpYardCount()));
                    }
                    setRecycler(attendanceDTOList);
                } else {
                    attendanceBinding.recyclerAttendance.setVisibility(View.GONE);
                    attendanceBinding.progressCircular.setVisibility(View.GONE);
                    attendanceBinding.txtNoData.setVisibility(View.VISIBLE);
                }
            }
        });

        /*setRecycler(attendanceDTOList);*/
        setDate();


        attendanceBinding.edtSearchTextAt.addTextChangedListener(new TextWatcher() {
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

        /*attendanceViewModel.getAttendanceFilterMutableLiveData().observe(getActivity(), new Observer<SpecificUlbDTO>() {
            @Override
            public void onChanged(SpecificUlbDTO specificUlbDTO) {
                Log.e(TAG, "onChanged: " + specificUlbDTO.getTotalHouse());
            }
        });*/


        attendanceViewModel.getAttendanceResponseLiveData().observe(getViewLifecycleOwner(), new Observer<List<AttendanceDTO>>() {
            @Override
            public void onChanged(List<AttendanceDTO> attendanceDTOS) {
//                Log.e(TAG, "onChanged: " + attendanceDTOS.get(0));
            }
        });

        setOnClick();

    }

    private void setOnClick() {
        attendanceBinding.cardFilterAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
                Bundle extras = new Bundle();
                extras.putString("fromDate", "07-06-2022");
                extras.putString("toDate", "07-06-2022");
                extras.putString("userId", "3098");
            }
        });
    }

    private void setDate() {

    }


    @SuppressLint("NotifyDataSetChanged")
    private void setRecycler(List<AttendanceDTO> attendanceDTOList) {
        attendanceBinding.txtNoData.setVisibility(View.GONE);
        attendanceBinding.progressCircular.setVisibility(View.GONE);
        adapter = new AttendanceAdapter(context, attendanceDTOList);
        adapter.notifyDataSetChanged();
        attendanceBinding.recyclerAttendance.setLayoutManager(layoutManager);
        attendanceBinding.recyclerAttendance.setAdapter(adapter);

    }


    private void filter(String text) {
        List<AttendanceDTO> filteredList = new ArrayList<>();

        for (AttendanceDTO item : attendanceDTOList) {
            if (item.getUserName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    private void openDialog() {
        filterDialog = new FilterDialogFragment("AttendanceFrag");

        filterDialog.setFilterDialogListener(new FilterDialog.FilterDialogInterface() {
            @Override
            public void onFilterDialogDismiss(String frmDate, String toDate, String userId) {
                Log.e(TAG, "onFilterDialogDismiss: fromDate: " + frmDate + " toDate: " + toDate + " userId: " + userId);
                filterExtras = new Bundle();
                filterExtras.putString("fromDate", frmDate);
                filterExtras.putString("toDate", toDate);
                filterExtras.putString("userId", userId);
                filterExtras.putString("appId", Prefs.getString(MainUtils.APP_ID, null));
                attendanceViewModel.setFilteredData(filterExtras);
            }
        });

        filterDialog.show(getChildFragmentManager(), TAG);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            adapter.notifyDataSetChanged();
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            Log.i("IsRefresh", "Yes");
        }
    }
}