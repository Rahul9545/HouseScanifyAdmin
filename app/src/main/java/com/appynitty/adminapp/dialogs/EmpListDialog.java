package com.appynitty.adminapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.EmpListDialogAdapter;

public class EmpListDialog extends Dialog {
    private Context context;
    private RecyclerView recyclerEmpList;
    private LinearLayoutManager layoutManager;
    private ProgressBar loader;
    private TextView txtNoData;
    private EmpListDialogAdapter adapter;
    private EmpListDialogInterface listener;

    public EmpListDialog(@NonNull Context context, EmpListDialogInterface listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    public interface EmpListDialogInterface {
        void onDialogDismissed(String fromDate, String toDate, int userId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_emp_list);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(context.getResources().getColor(R.color.colorWhite));
        }
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        init();

    }

    private void init() {
        txtNoData = findViewById(R.id.txt_no_data);
        txtNoData.setVisibility(View.GONE);
        loader = findViewById(R.id.progress_circular);
        loader.setVisibility(View.GONE);
        recyclerEmpList = findViewById(R.id.recycler_emp_list);
        layoutManager = new LinearLayoutManager(context);
        setOnRecycler();
    }

    private void setOnRecycler() {
        adapter = new EmpListDialogAdapter(context);
        recyclerEmpList.setLayoutManager(layoutManager);
        recyclerEmpList.setAdapter(adapter);
    }
}
