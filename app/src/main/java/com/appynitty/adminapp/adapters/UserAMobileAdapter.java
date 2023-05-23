package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.MapActivity;
import com.appynitty.adminapp.activities.ZoomViewActivity;
import com.appynitty.adminapp.databinding.ItemUserAttendanceMobileBinding;
import com.appynitty.adminapp.models.UserAttendanceDTO;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

/***
 * Created by Rahul Rokade
 * 21/11/2022
 * */
public class UserAMobileAdapter extends RecyclerView.Adapter<UserAMobileAdapter.MyViewHolder> {

    private Context context;
    private static final String TAG = "UserAMobileAdapter";
    private List<UserAttendanceDTO> userMobileList;

    private String qrEmpIdM;
    private String qrEmpNameM;

    public void setEmpIdMList(String qrEmpNameM) {
        this.qrEmpIdM = qrEmpIdM;
        this.qrEmpNameM = qrEmpNameM;
    }

    public UserAMobileAdapter(Context context, List<UserAttendanceDTO> userMobileList) {
        this.context = context;
        this.userMobileList = userMobileList;
    }


    @NonNull
    @Override
    public UserAMobileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemUserAttendanceMobileBinding itemUserMobileBinding = ItemUserAttendanceMobileBinding.inflate(layoutInflater,parent,false);

        return new MyViewHolder(itemUserMobileBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAMobileAdapter.MyViewHolder holder, int position) {
        UserAttendanceDTO userAttendanceDTO = userMobileList.get(position);
        AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        holder.itemUserMobileBinding.setUserMobileItem(userAttendanceDTO);
        holder.itemUserMobileBinding.executePendingBindings();

        holder.itemUserMobileBinding.txtEmpNameAt.setText(userAttendanceDTO.getEmployeeName());
        if (userAttendanceDTO.getEmployeeType().equals("A")){
            Log.e("RahulDev", "getType: "+userAttendanceDTO.getEmployeeType() );
            holder.itemUserMobileBinding.txtMoUserRole.setText(context.getResources().getString(R.string.str_user_role_admin));
        }else if (userAttendanceDTO.getEmployeeType().equals("SA")){
            Log.e("RahulDev", "getType: "+userAttendanceDTO.getEmployeeType().toString() );
            holder.itemUserMobileBinding.txtMoUserRole.setText(context.getResources().getString(R.string.str_usr_role_sub_admin));
        }else if (userAttendanceDTO.getEmployeeType().equals("SUA")){
            Log.e("RahulDev", "getType: "+userAttendanceDTO.getEmployeeType().toString() );
            holder.itemUserMobileBinding.txtMoUserRole.setText(context.getResources().getString(R.string.str_usr_role_super_admin));
        }

        holder.itemUserMobileBinding.txtStartDateAt.setText(userAttendanceDTO.getStartDate());
        holder.itemUserMobileBinding.txtStartTimeAt.setText(userAttendanceDTO.getStartTime());
        holder.itemUserMobileBinding.txtEndDateAt.setText(userAttendanceDTO.getEndDate());
        holder.itemUserMobileBinding.txtEntTimeAt.setText(userAttendanceDTO.getEndTime());

        if (userAttendanceDTO.getIpAddress() == null){
            holder.itemUserMobileBinding.txtLoginDevice.setText(context.getResources().getString(R.string.str_mobile));

        }
        if (userAttendanceDTO.isStatus()){
            holder.itemUserMobileBinding.txtStatus.setText("Active");
        }else {
            holder.itemUserMobileBinding.txtStatus.setText("InActive");
        }

        holder.itemUserMobileBinding.imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("userAttendanceMobileList", String.valueOf(userMobileList.get(holder.getAdapterPosition())));
                intent.putExtra("startLat", String.valueOf(userMobileList.get(holder.getAdapterPosition()).getStartLat()));
                intent.putExtra("startLog", String.valueOf(userMobileList.get(holder.getAdapterPosition()).getStartLong()));
                intent.putExtra("endLat", String.valueOf(userMobileList.get(holder.getAdapterPosition()).getEndLat()));
                intent.putExtra("endLong", String.valueOf(userMobileList.get(holder.getAdapterPosition()).getEndLong()));
                intent.putExtra("EmpName", String.valueOf(userMobileList.get(holder.getAdapterPosition()).getEmployeeName()));
                intent.putExtra("device_login", String.valueOf(userMobileList.get(holder.getAdapterPosition()).getLoginDevice()));
                context.startActivity(intent);
            }
        });

        /*holder.itemUserMobileBinding.imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Log.e(TAG, "onClick: startLat: " + userMobileList.get(holder.getAdapterPosition()).getStartLat() + ", startLong: "
                        + userMobileList.get(holder.getAdapterPosition()).getStartLong());

                String startLat = (String) userMobileList.get(holder.getAdapterPosition()).getStartLat();
                String startLon = (String) userMobileList.get(holder.getAdapterPosition()).getStartLong();
                String usersLocationStart = "geo:0,0?q=" + startLat + "," + startLon + "?z=21";

                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUriStart = Uri.parse(usersLocationStart);

                Log.e(TAG, "onClick: endLat: " + userMobileList.get(holder.getAdapterPosition()).getEndLat() + ", endLong: "
                        + userMobileList.get(holder.getAdapterPosition()).getEndLong());

                String endLat = (String) userMobileList.get(holder.getAdapterPosition()).getEndLat();
                String endLon = (String) userMobileList.get(holder.getAdapterPosition()).getEndLong();
                String usersLocationEnd = "geo:0,0?q=" + startLat + "," + startLon + "?z=21";

                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUriEnd = Uri.parse(usersLocationEnd);



                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUriStart);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Log.e(TAG, "onClick: maps is unavailable!");
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return userMobileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemUserAttendanceMobileBinding itemUserMobileBinding;
        public MyViewHolder(@NonNull ItemUserAttendanceMobileBinding itemUserMobileBinding) {
            super(itemUserMobileBinding.getRoot());
            this.itemUserMobileBinding = itemUserMobileBinding;
        }
    }
}
