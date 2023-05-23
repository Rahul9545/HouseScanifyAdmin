package com.appynitty.adminapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.AddUserDetailsActivity;
import com.appynitty.adminapp.databinding.ItemUserRightsListBinding;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.utils.MainUtils;

import java.util.List;


public class UserRightsAdapter extends RecyclerView.Adapter<UserRightsAdapter.MyViewHolder> {

    private Context context;
    private static final String TAG = "UserRightsAdapter";
    private List<UserRoleModelDTO> userRoleModelDTOList;
    private String empType = "";

    public UserRightsAdapter(Context context, List<UserRoleModelDTO> userRoleModelDTOList) {
        this.context = context;
        this.userRoleModelDTOList = userRoleModelDTOList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user_rights_list, parent, false));
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemUserRightsListBinding userRightsListBinding = ItemUserRightsListBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(userRightsListBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserRoleModelDTO userRoleModelDTO = userRoleModelDTOList.get(position);
        holder.userRightsListBinding.setUserRoleItem(userRoleModelDTO);
        holder.userRightsListBinding.executePendingBindings();
        holder.userRightsListBinding.txtEmpName.setText(MainUtils.firstLatterCap(userRoleModelDTOList.get(position).getEmpName()));

        if (userRoleModelDTO.getType().equals("A")){
            Log.e("RahulDev", "getType: "+userRoleModelDTO.getType().toString() );
            holder.userRightsListBinding.txtUserRole.setText(context.getResources().getString(R.string.str_user_role_admin));
        }else if (userRoleModelDTO.getType().equals("SA")){
            Log.e("RahulDev", "getType: "+userRoleModelDTO.getType().toString() );
            holder.userRightsListBinding.txtUserRole.setText(context.getResources().getString(R.string.str_usr_role_sub_admin));
        }else if (userRoleModelDTO.getType().equals("SUA")){
            Log.e("RahulDev", "getType: "+userRoleModelDTO.getType().toString() );
            holder.userRightsListBinding.txtUserRole.setText(context.getResources().getString(R.string.str_usr_role_super_admin));
        }

        holder.userRightsListBinding.imgEditUserRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*empType = Prefs.getString(MainUtils.EMP_TYPE);*/
                if (userRoleModelDTO.getType().equals("Sub-Admin") && userRoleModelDTO.getType() != null) {
                    Intent intent = new Intent(context, AddUserDetailsActivity.class);
                    intent.putExtra("userRoleRightsDetails", userRoleModelDTOList.get(holder.getAdapterPosition()));

                    context.startActivity(intent);
                    Log.e(TAG, "onClick: User Role Right Info:- " + userRoleModelDTOList.get(holder.getAdapterPosition()).toString());
                    Log.e(TAG, "adapter clicked User Role Rights  Id:- " + userRoleModelDTO.getEmpId() + "  " + userRoleModelDTOList.get(holder.getAdapterPosition()).getEmpName()
                            + "  " + userRoleModelDTO.getType()
                            + " " + userRoleModelDTO.getIsActiveULB());

                }else if (userRoleModelDTO.getType().contains("SA") && !userRoleModelDTO.getType().isEmpty()) {
                    Intent intent = new Intent(context, AddUserDetailsActivity.class);
                    intent.putExtra("userRoleRightsDetails", userRoleModelDTOList.get(holder.getAdapterPosition()));

                    context.startActivity(intent);
                    Log.e(TAG, "onClick: User Role Right Info:- " + userRoleModelDTOList.get(holder.getAdapterPosition()).toString());
                    Log.e(TAG, "adapter clicked User Role Rights  Id:- " + userRoleModelDTO.getEmpId() + "  " + userRoleModelDTOList.get(holder.getAdapterPosition()).getEmpName()
                            + "  " + userRoleModelDTO.getType()
                            + " " + userRoleModelDTO.getIsActiveULB());

                } else if (userRoleModelDTO.getType().contains("SUA") && !userRoleModelDTO.getType().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.btn_dialog)
                            .setMessage("Super Admin ID Are Not Available To Edit")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.create().show();
                } else if (userRoleModelDTO.getType().contains("A") && !userRoleModelDTO.getType().isEmpty()) {
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.btn_dialog)
                            .setMessage("Admin ID Are Not Available To Edit")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.create().show();*/

                    Intent intent = new Intent(context, AddUserDetailsActivity.class);
                    intent.putExtra("userRoleRightsDetails", userRoleModelDTOList.get(holder.getAdapterPosition()));

                    context.startActivity(intent);
                    Log.e(TAG, "onClick: User Role Right Info:- " + userRoleModelDTOList.get(holder.getAdapterPosition()).toString());
                    Log.e(TAG, "adapter clicked User Role Rights  Id:- " + userRoleModelDTO.getEmpId() + "  " + userRoleModelDTOList.get(holder.getAdapterPosition()).getEmpName()
                            + "  " + userRoleModelDTO.getType()
                            + " " + userRoleModelDTO.getIsActiveULB());


                } else if (userRoleModelDTO.getType().contains("S") && !userRoleModelDTO.getType().isEmpty()) {
                    Intent intent = new Intent(context, AddUserDetailsActivity.class);
                    intent.putExtra("userRoleRightsDetails", userRoleModelDTOList.get(holder.getAdapterPosition()));

                    context.startActivity(intent);
                    Log.e(TAG, "onClick: User Role Right Info:- " + userRoleModelDTOList.get(holder.getAdapterPosition()).toString());
                    Log.e(TAG, "adapter clicked User Role Rights  Id:- " + userRoleModelDTO.getEmpId() + "  " + userRoleModelDTOList.get(holder.getAdapterPosition()).getEmpName()
                            + "  " + userRoleModelDTO.getType()
                            + " " + userRoleModelDTO.getIsActiveULB());

                }
            }
        });

    }

    public void inActiveList(List<UserRoleModelDTO> inActiveUserListData) {
        userRoleModelDTOList = inActiveUserListData;
        notifyDataSetChanged();
    }

    public void activeList(List<UserRoleModelDTO> activeUserListData) {
        userRoleModelDTOList = activeUserListData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userRoleModelDTOList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /*private TextView txtEmpName, txtStatus, txtUserRole, txtMobile;
        private ImageView imgEditUserRights;*/

        ItemUserRightsListBinding userRightsListBinding;

        public MyViewHolder(@NonNull ItemUserRightsListBinding userRightsListBinding) {
            super(userRightsListBinding.getRoot());
            this.userRightsListBinding = userRightsListBinding;

            /*imgEditUserRights = itemView.findViewById(R.id.img_edit_user_right);
            txtEmpName = itemView.findViewById(R.id.txt_emp_name);
            txtMobile = itemView.findViewById(R.id.txt_emp_mobile);
            txtUserRole = itemView.findViewById(R.id.txt_user_role);
            txtStatus = itemView.findViewById(R.id.txt_emp_status);*/
        }
    }
}
