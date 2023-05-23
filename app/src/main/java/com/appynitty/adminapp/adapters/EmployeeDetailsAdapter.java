package com.appynitty.adminapp.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.EmpDetailsRowItemBinding;
import com.appynitty.adminapp.fragments.HouseDetailsFragment;
import com.appynitty.adminapp.models.EmployeeDetailsDTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class EmployeeDetailsAdapter extends RecyclerView.Adapter<EmployeeDetailsAdapter.MyViewHolder> {
    private static final String TAG = "TableRowLiveDataAdapter";
    private Context context;
    List<EmployeeDetailsDTO> employeeDetailsList;

    public EmployeeDetailsAdapter(Context context, List<EmployeeDetailsDTO> employeeDetailsList) {
        this.context = context;
        this.employeeDetailsList = employeeDetailsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        EmpDetailsRowItemBinding binding = EmpDetailsRowItemBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final EmployeeDetailsDTO empDetails = employeeDetailsList.get(position);
        holder.binding.setEmployee(empDetails);
        holder.binding.executePendingBindings();
        holder.binding.rowTxtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, empDetails.getEmpName(), Toast.LENGTH_LONG).show();
                /*BottomNavigationView bottomNavigationView;
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
                bottomNavigationView.setSelectedItemId(R.id.nav_house_details);
                Fragment myFragment = new HouseDetailsFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_frame_layout, myFragment).addToBackStack(null).commit();*/

            }
        });
    }

    public void filterList(List<EmployeeDetailsDTO> filteredList) {
        employeeDetailsList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return employeeDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EmpDetailsRowItemBinding binding;

        public MyViewHolder(@NonNull EmpDetailsRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
