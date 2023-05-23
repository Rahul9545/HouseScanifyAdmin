package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ItemUserAttendancePcBinding;
import com.appynitty.adminapp.models.UserAttendanceDTO;

import java.util.List;

/***
 * Created by Rahul Rokade
 * 21/11/2022
 * */
public class UserAPcAdapter extends RecyclerView.Adapter<UserAPcAdapter.MyViewHolder> {

    private Context context;
    private static final String TAG = "UserAPcAdapter";
    private List<UserAttendanceDTO> userPcList;
    private String qrEmpId;
    private String qrEmpName;

    public void setEmpIdPList(String qrEmpName) {
        this.qrEmpId = qrEmpId;
        this.qrEmpName = qrEmpName;
    }

    public UserAPcAdapter(Context context, List<UserAttendanceDTO> userPcList) {
        this.context = context;
        this.userPcList = userPcList;
    }

    @NonNull
    @Override
    public UserAPcAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemUserAttendancePcBinding userAttendancePcBinding = ItemUserAttendancePcBinding.inflate(layoutInflater,parent,false);
        return new MyViewHolder(userAttendancePcBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAPcAdapter.MyViewHolder holder, int position) {
        UserAttendanceDTO userAttendanceDTO = userPcList.get(position);
        holder.userAttendancePcBinding.setUserPcItem(userAttendanceDTO);
        holder.userAttendancePcBinding.executePendingBindings();

        holder.userAttendancePcBinding.txtEmpNameAt.setText(userAttendanceDTO.getEmployeeName());
        holder.userAttendancePcBinding.txtStartDateAt.setText(userAttendanceDTO.getStartDate());
        holder.userAttendancePcBinding.txtStartTimeAt.setText(userAttendanceDTO.getStartTime());
        holder.userAttendancePcBinding.txtEndDateAt.setText(userAttendanceDTO.getEndDate());
        holder.userAttendancePcBinding.txtEntTimeAt.setText(userAttendanceDTO.getEndTime());

        if (userAttendanceDTO.getEmployeeType().equals("A")){
            Log.e("RahulDev", "getType: "+userAttendanceDTO.getEmployeeType() );
            holder.userAttendancePcBinding.txtUaPcUserType.setText(context.getResources().getString(R.string.str_user_role_admin));
        }else if (userAttendanceDTO.getEmployeeType().equals("SA")){
            Log.e("RahulDev", "getType: "+userAttendanceDTO.getEmployeeType().toString() );
            holder.userAttendancePcBinding.txtUaPcUserType.setText(context.getResources().getString(R.string.str_usr_role_sub_admin));
        }else if (userAttendanceDTO.getEmployeeType().equals("SUA")){
            Log.e("RahulDev", "getType: "+userAttendanceDTO.getEmployeeType().toString() );
            holder.userAttendancePcBinding.txtUaPcUserType.setText(context.getResources().getString(R.string.str_usr_role_super_admin));
        }

        if (userAttendanceDTO.getLoginDevice().equals("PC")){
            Log.e("RahulDev", "getLoginDevice: "+userAttendanceDTO.getLoginDevice().toString() );
            holder.userAttendancePcBinding.txtLoginDevice.setText(context.getResources().getString(R.string.str_pc));
            holder.userAttendancePcBinding.txtHostName.setText(context.getResources().getString(R.string.str_web_ser));
            holder.userAttendancePcBinding.txtIpAddress.setText(userAttendanceDTO.getIpAddress());
           // holder.userAttendancePcBinding.txtStatus.setText(userAttendanceDTO.get());
        }else if (userAttendanceDTO.getLoginDevice().equals("MB")){
            Log.e("RahulDev", "getLoginDevice: "+userAttendanceDTO.getLoginDevice().toString() );
            holder.userAttendancePcBinding.txtLoginDevice.setText(context.getResources().getString(R.string.str_mobile_browser));
            holder.userAttendancePcBinding.txtHostName.setText(context.getResources().getString(R.string.str_mobile));
            holder.userAttendancePcBinding.txtIpAddress.setText(userAttendanceDTO.getIpAddress());
           // holder.userAttendancePcBinding.txtStatus.setText(userAttendanceDTO.get());
        }

        if (userAttendanceDTO.isStatus()){
            holder.userAttendancePcBinding.txtStatus.setText("Active");
        }else {
            holder.userAttendancePcBinding.txtStatus.setText("InActive");
        }


    }

    @Override
    public int getItemCount() {
        return userPcList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemUserAttendancePcBinding userAttendancePcBinding;
        public MyViewHolder(@NonNull ItemUserAttendancePcBinding userAttendancePcBinding) {
            super(userAttendancePcBinding.getRoot());
            this.userAttendancePcBinding = userAttendancePcBinding;
        }
    }
}
