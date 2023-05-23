package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.fragments.EmpListFragment;
import com.appynitty.adminapp.fragments.HouseDetailsFragment;
import com.appynitty.adminapp.models.EmployeeDetailsDTO;
import com.appynitty.adminapp.utils.MainUtils;

import java.util.List;

public class EmpListRecyclerAdapter extends RecyclerView.Adapter<EmpListRecyclerAdapter.MyViewHolder> {
    private static final String TAG = "EmpListRecyclerAdapter";
    private Context context;
    String date;
    List<EmployeeDetailsDTO> employeeDetailsList;

    public EmpListRecyclerAdapter(Context context, List<EmployeeDetailsDTO> employeeDetailsList, String fromDate) {
        this.context = context;
        this.employeeDetailsList = employeeDetailsList;
        date = fromDate;
        Log.e(TAG, "EmpListRecyclerAdapter: " + employeeDetailsList.toString());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.emp_row_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EmpListFragment elf = new EmpListFragment();
        int houseCount, dumpCount, liquidCount, streetCount, totalCount;

        Bundle args = new Bundle();
        final EmployeeDetailsDTO empDetails = employeeDetailsList.get(position);
        houseCount = empDetails.getHouseCount();
        dumpCount = empDetails.getDumpCount();
        liquidCount = empDetails.getLiquidCount();
        streetCount = empDetails.getStreetCount();
        totalCount = houseCount + dumpCount + liquidCount + streetCount;
        args.putInt("houseCount", houseCount);
        args.putInt("dumpCount", dumpCount);
        args.putInt("liquidCount", liquidCount);
        args.putInt("streetCount", streetCount);
        args.putInt("totalCount", totalCount);
        args.putString("fromDate", date);

        holder.name.setText(empDetails.getEmpName());
        holder.totalCount.setText(String.valueOf(totalCount));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + empDetails.getEmp_Id());
                args.putString(MainUtils.EMP_ID, String.valueOf(empDetails.getEmp_Id()));
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new HouseDetailsFragment();
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frame_layout, myFragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, totalCount;

        public MyViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.txt_emp_name);
            totalCount = view.findViewById(R.id.txt_totalScan);
//            binding.tvName.setText("Swapnil");
        }
    }
}
