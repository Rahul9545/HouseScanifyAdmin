package com.appynitty.adminapp.dialogs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.QREmpListAdapter;
import com.appynitty.adminapp.databinding.DialogFilterBinding;
import com.appynitty.adminapp.models.QREmployeeDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.QREmpListVM;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FilterDialogFragment extends DialogFragment {
    private static final String TAG = "FilterDialogFragment";
    private DialogFilterBinding binding;
    String callingFrag = "";
    private FilterDialog.FilterDialogInterface filterDialogListener;
    private EditText edtSelectToDate, edtSelectFromDate;
    private Spinner spnrSelectEmployee;
    private ProgressBar progressBar;
    private TextView txtBtnCancel, txtBtnApplyFilter;
    private String frmDate, toDate, empType, userId = "0";
    final Calendar myCalendar = Calendar.getInstance();
    QREmpListVM qrEmpListVM;
    private ArrayList<QREmployeeDTO> mQrEmpList;
    private QREmpListAdapter mAdapter;

    View view;

    public FilterDialogFragment(String fragIdentity) {
        callingFrag = fragIdentity;
    }

    public void setFilterDialogListener(FilterDialog.FilterDialogInterface filterDialogListener) {
        this.filterDialogListener = filterDialogListener;
    }

    public interface FilterDialogInterface {
        void onFilterDialogDismiss(String frmDate, String toDate, String userId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(View view) {

        mQrEmpList = new ArrayList<>();
        edtSelectFromDate = view.findViewById(R.id.edt_from_date);
        edtSelectToDate = view.findViewById(R.id.edt_to_date);
        spnrSelectEmployee = view.findViewById(R.id.spinner_EmpList);
        txtBtnApplyFilter = view.findViewById(R.id.txt_btn_app_filter);
        txtBtnCancel = view.findViewById(R.id.txt_btn_cancel);
        progressBar = view.findViewById(R.id.progress_filter);
        empType = Prefs.getString(MainUtils.EMP_TYPE);

        if (callingFrag.matches("houseDetails")) {
            edtSelectToDate.setVisibility(View.GONE);
        } else if (callingFrag.matches("EmpListFragment")) {
            edtSelectToDate.setVisibility(View.GONE);
            spnrSelectEmployee.setVisibility(View.GONE);
        }

        spnrSelectEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                QREmployeeDTO qrEmployee = (QREmployeeDTO) parent.getItemAtPosition(position);

                if (position > 0) {
                    userId = qrEmployee.getEmployeeID();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        qrEmpListVM = new ViewModelProvider(this).get(QREmpListVM.class);

        edtSelectFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        edtSelectFromDate.setText(getCurrentDate());
                        frmDate = getCurrentDate();
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        edtSelectToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        edtSelectToDate.setText(getCurrentDate());
                        toDate = getCurrentDate();
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        txtBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        txtBtnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (frmDate == null) {
                    MainUtils.warning("please select From Date", Toast.LENGTH_SHORT);
                } else if (toDate == null) {
                    if (callingFrag.matches("houseDetails")) {
                        filterDialogListener.onFilterDialogDismiss(frmDate, toDate, userId);
                        dismiss();
                    } else if (callingFrag.matches("EmpListFragment")) {
                        filterDialogListener.onFilterDialogDismiss(frmDate, toDate, userId);
                        dismiss();
                    } else {
                        MainUtils.warning("please select To Date", 0);
                    }

                } else {
                    filterDialogListener.onFilterDialogDismiss(frmDate, toDate, userId);
                    dismiss();
                }

            }
        });

        qrEmpListVM.getmProgressLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                progressBar.setVisibility(integer);
            }
        });

        qrEmpListVM.getQREmpListLiveData().observe(this, new Observer<List<QREmployeeDTO>>() {
            @Override
            public void onChanged(List<QREmployeeDTO> qrEmployeeDTOS) {
                Log.e(TAG, "onChanged: " + qrEmployeeDTOS.size());
                mQrEmpList.add(new QREmployeeDTO("Please select an employee", "0"));
                for (QREmployeeDTO qrEmployee : qrEmployeeDTOS) {
                    mQrEmpList.add(qrEmployee);
                }
//                initList();
                mAdapter = new QREmpListAdapter(getActivity(), mQrEmpList);
                spnrSelectEmployee.setAdapter(mAdapter);
            }

        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.dialog_filter, container, false);
        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getDialog().setCancelable(false);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }

    public String getCurrentDate() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(myCalendar.getTime());
        return strDate;
    }
}
