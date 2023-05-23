package com.appynitty.adminapp.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.DialogFilterBinding;
import com.appynitty.adminapp.viewmodels.QREmpListVM;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FilterDialog extends Dialog {
    private static final String TAG = "FilterDialog";
    private Context context;
    private EditText edtSelectToDate, edtSelectFromDate;
    Spinner spinnerEmpList;
    private TextView txtBtnCancel, txtBtnApplyFilter;
    private FilterDialogInterface filterDialogListener;
    private CardView crdSelectFromD, crdSelectToD, crdSelectEmp;
    private EmpListDialog empListDialog;
    DialogFilterBinding filterBinding;
    private String frmDate, toDate, userId = "0";
    final Calendar myCalendar = Calendar.getInstance();
    QREmpListVM qrEmpListVM;

    public FilterDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void setFilterDialogListener(FilterDialogInterface filterDialogListener) {
        this.filterDialogListener = filterDialogListener;
    }

    public interface FilterDialogInterface {
        void onFilterDialogDismiss(String frmDate, String toDate, String userId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        filterBinding = DialogFilterBinding.inflate(LayoutInflater.from(context), (ViewGroup) filterBinding.getRoot());
        setContentView(R.layout.dialog_filter);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(context.getResources().getColor(R.color.colorWhite));
        }
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        init();
    }

    private void init() {

//        qrEmpListVM = ViewModelProviders.of().get(QREmpListVM.class);
        edtSelectFromDate = findViewById(R.id.edt_from_date);
        edtSelectToDate = findViewById(R.id.edt_to_date);
        spinnerEmpList = findViewById(R.id.spinner_EmpList);
        txtBtnApplyFilter = findViewById(R.id.txt_btn_app_filter);
        txtBtnCancel = findViewById(R.id.txt_btn_cancel);
        crdSelectFromD = findViewById(R.id.card_select_from_date);
        crdSelectToD = findViewById(R.id.card_select_to_date);
        crdSelectEmp = findViewById(R.id.card_select_emp);

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
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        setOnClick();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(context, R.color.colorWhite));
        }

    }

    private void setOnClick() {

        txtBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        txtBtnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialogListener.onFilterDialogDismiss(frmDate, toDate, userId);
                dismiss();
            }
        });

        crdSelectEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmpListDialog();
            }
        });

        crdSelectFromD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Clicked Calender!", Toast.LENGTH_SHORT).show();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        edtSelectFromDate.setText(dayOfMonth + "" + monthOfYear + "" + year);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        crdSelectToD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        edtSelectToDate.setText(dayOfMonth + "" + monthOfYear + "" + year);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    private void openEmpListDialog() {
        empListDialog = new EmpListDialog(context, new EmpListDialog.EmpListDialogInterface() {
            @Override
            public void onDialogDismissed(String fromDate, String toDate, int userId) {
                Log.e(TAG, "onDialogDismissed: fromDate: " + fromDate + " toDate: " + toDate);
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        empListDialog.setCancelable(true);
        empListDialog.show();
    }

    public String getCurrentDate() {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = format.format(myCalendar.getTime());
        return strDate;
    }

}
