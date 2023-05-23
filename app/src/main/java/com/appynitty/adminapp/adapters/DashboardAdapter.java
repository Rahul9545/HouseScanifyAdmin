package com.appynitty.adminapp.adapters;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.activities.HomeActivity;
import com.appynitty.adminapp.databinding.DashboardItemListBinding;
import com.appynitty.adminapp.models.UlbDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;


public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {
    private static final String TAG = "DashboardAdapter";
    private Context context;
    List<UlbDTO> ulbList;

    public DashboardAdapter(Context context, List<UlbDTO> ulbList) {
        this.context = context;
        this.ulbList = ulbList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        DashboardItemListBinding itemListBinding = DashboardItemListBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(itemListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UlbDTO ulb = ulbList.get(position);
        holder.itemListBinding.setUlb(ulb);
        holder.itemListBinding.executePendingBindings();
        ObjectAnimator objAnimator = ObjectAnimator.ofFloat(holder.itemListBinding.imgDot, "alpha", 0f, 1f);
        objAnimator.setDuration(1000);
        objAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objAnimator.setRepeatCount(Animation.INFINITE);
        objAnimator.start();
    }

    @Override
    public int getItemCount() {
        return ulbList.size();
    }

    public void filterList(List<UlbDTO> filteredList) {
        ulbList = filteredList;
        notifyDataSetChanged();
    }

    public void afterFilter(List<UlbDTO> afterFilterList){
        ulbList = afterFilterList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        DashboardItemListBinding itemListBinding;

        public MyViewHolder(@NonNull DashboardItemListBinding itemListBinding) {
            super(itemListBinding.getRoot());
            this.itemListBinding = itemListBinding;
            itemListBinding.cardDashItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    Prefs.putString(MainUtils.APP_ID, String.valueOf(ulbList.get(getAdapterPosition()).getAppId()));
                    intent.putExtra("appId", String.valueOf(ulbList.get(getAdapterPosition()).getAppId()));
                    intent.putExtra("ulbName", String.valueOf(ulbList.get(getAdapterPosition()).getUlbName()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
