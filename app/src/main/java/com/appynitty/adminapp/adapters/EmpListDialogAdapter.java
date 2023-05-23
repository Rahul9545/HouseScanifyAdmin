package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;

public class EmpListDialogAdapter extends RecyclerView.Adapter<EmpListDialogAdapter.MyViewHolder> {
    private Context context;

    public EmpListDialogAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EmpListDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_emp_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmpListDialogAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
