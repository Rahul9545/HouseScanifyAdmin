package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.activities.AddEmpActivity;
import com.appynitty.adminapp.databinding.ItemEmpDetailsListBinding;
import com.appynitty.adminapp.models.AttendanceDTO;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.utils.MainUtils;

import java.util.List;


public class EmpDetailsAdapter extends RecyclerView.Adapter<EmpDetailsAdapter.MyViewHolder> {
    private static final String TAG = "EmpDetailsAdapter";
    private Context context;
    private List<EmpDModelDTO> empDModelDTOList;

    public EmpDetailsAdapter(Context context, List<EmpDModelDTO> empDModelDTOList) {
        this.context = context;
        this.empDModelDTOList = empDModelDTOList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemEmpDetailsListBinding itemEmpDListBinding = ItemEmpDetailsListBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(itemEmpDListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final EmpDModelDTO empDModelDTO = empDModelDTOList.get(position);

        String firstName = empDModelDTO.getQrEmpName();
        holder.itemEmpDListBinding.txtEmpName.setText(MainUtils.capitalizeString(firstName));
        holder.itemEmpDListBinding.setEmpDItem(empDModelDTO);
        holder.itemEmpDListBinding.executePendingBindings();

        holder.itemEmpDListBinding.imgEditEmpDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* context.startActivity(new Intent(context, AddEmpActivity.class).putExtra("qrEmpId", empDModelDTOList.get(holder.getAdapterPosition()).getEmpId())
                        .putExtra("qrEmpName", empDModelDTOList.get(holder.getAdapterPosition()).getQrEmpName()));*/
                Intent intent = new Intent(context, AddEmpActivity.class);
                intent.putExtra("qrEmpDetails", empDModelDTOList.get(holder.getAdapterPosition()));
//                intent.putExtra("qrEmpName", empDModelDTOList.get(position).getQrEmpName());
                context.startActivity(intent);
                Log.e(TAG, "onClick: Emp Info:- " + empDModelDTOList.get(holder.getAdapterPosition()).toString());
                Log.e(TAG, "adapter clicked emp Id:- " + empDModelDTO.getQrEmpId() + "  " + empDModelDTOList.get(holder.getAdapterPosition()).getQrEmpName());

            }
        });

    }

    public void inActiveList(List<EmpDModelDTO> inActiveListData) {
        empDModelDTOList = inActiveListData;
        notifyDataSetChanged();
    }

    public void activeList(List<EmpDModelDTO> activeListData) {
        empDModelDTOList = activeListData;
        notifyDataSetChanged();
    }

    public void searchListAdapter(List<EmpDModelDTO> searchedList) {
        empDModelDTOList = searchedList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return empDModelDTOList.size();
        /*return 20;*/
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /*private ImageView imgAddEmp;
        private TextView txtEmpName, txtEmpMobile, txtAddress, txtStatus;*/
        ItemEmpDetailsListBinding itemEmpDListBinding;

        public MyViewHolder(@NonNull ItemEmpDetailsListBinding itemEmpDListBinding) {
            super(itemEmpDListBinding.getRoot());
            this.itemEmpDListBinding = itemEmpDListBinding;
            /*Prefs.putString(MainUtils.EMP_ID, String.valueOf(empDModelDTOList.get(getAdapterPosition()).getEmpId()));
            Log.e(TAG, "QrEmpID: " + empDModelDTOList.get(getAdapterPosition()).getEmpId());*/

            /*imgAddEmp = itemView.findViewById(R.id.img_edit_emp_details);
            txtEmpName = itemView.findViewById(R.id.txt_emp_name);
            txtEmpMobile = itemView.findViewById(R.id.txt_emp_mobile);
            txtAddress = itemView.findViewById(R.id.txt_emp_address);
            txtStatus = itemView.findViewById(R.id.txt_emp_status);*/
        }
    }
}
