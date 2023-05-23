package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.ZoomViewActivity;
import com.appynitty.adminapp.models.HouseDetailsImageDTO;
import com.bumptech.glide.Glide;

import java.util.List;

public class HouseDetailsAdapter extends RecyclerView.Adapter<HouseDetailsAdapter.MyViewHolder> {
    private static final String TAG = "HouseDetailsAdapter";
    private List<HouseDetailsImageDTO> imageDataList;
    MyClickListener myClickListener;
    HouseDetailsImageDTO houseDetailsItem = new HouseDetailsImageDTO();
    String imageBytes = "";
    private Context context;
    private int listItemCount = 0;
    boolean isChecked = true;
    public static int imgAccept = 1;
    public static int imgReject = 2;

    public HouseDetailsAdapter(Context context, List<HouseDetailsImageDTO> imageDataList1,
                               MyClickListener myClickListener) {
        this.context = context;
        imageDataList = imageDataList1;
        this.myClickListener = myClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_house_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        houseDetailsItem = imageDataList.get(position);
//        imageBytes = imageDataList.get(position).getqRCodeImage();

        AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        Glide.with(context)
                .load(imageDataList.get(holder.getAdapterPosition()).getQRCodeImage())
                .fitCenter()
                .into(holder.imgPhoto);

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e(TAG, "onClick: image:====" + imageDataList.get(position).getqRCodeImage());
                Intent intent = new Intent(context, ZoomViewActivity.class);
                intent.putExtra("qrImage", String.valueOf(imageDataList.get(holder.getAdapterPosition()).getQRCodeImage()));
                context.startActivity(intent);
            }
        });

        if (imageDataList.get(holder.getAdapterPosition()).getQRStatus() != null) {
            if (imageDataList.get(holder.getAdapterPosition()).getQRStatus().equals(true)) {
                holder.cardImgAccept.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorONDutyGreen));
                holder.txtImgAccept.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.cardImgReject.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
                holder.txtImgReject.setTextColor(ContextCompat.getColor(context, R.color.black));

            } else if (imageDataList.get(holder.getAdapterPosition()).getQRStatus().equals(false)) {
                holder.cardImgReject.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorOFFDutyRed));
                holder.txtImgReject.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.cardImgAccept.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
                holder.txtImgAccept.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
        } /*else {
            Log.e(TAG, "onBindViewHolder: Null HPSBA-Id: " + imageDataList.get(holder.getAdapterPosition()).getReferanceId());
        }*/

        holder.cardImgAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cardImgAccept.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorONDutyGreen));
                holder.txtImgAccept.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.cardImgReject.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
                holder.txtImgReject.setTextColor(ContextCompat.getColor(context, R.color.black));
                myClickListener.onItemClicked(imageDataList.get(holder.getAdapterPosition()).getReferanceId(), true);
            }
        });

        holder.cardImgReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cardImgReject.setCardBackgroundColor(context.getResources().getColor(R.color.colorOFFDutyRed));
                holder.txtImgReject.setTextColor(context.getResources().getColor(R.color.white));
                holder.cardImgAccept.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                holder.txtImgAccept.setTextColor(context.getResources().getColor(R.color.black));
                myClickListener.onItemClicked(imageDataList.get(holder.getAdapterPosition()).getReferanceId(), false);
            }
        });

        holder.txtLatitude.setText(imageDataList.get(holder.getAdapterPosition()).getLat());
        holder.txtLongitude.setText(imageDataList.get(holder.getAdapterPosition()).getLong());

        holder.cardDirections.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Log.e(TAG, "onClick: lat: " + imageDataList.get(holder.getAdapterPosition()).getLat() + ", long: "
                        + imageDataList.get(holder.getAdapterPosition()).getLong());

                String lat = imageDataList.get(holder.getAdapterPosition()).getLat();
                String lon = imageDataList.get(holder.getAdapterPosition()).getLong();
                String usersLocation = "geo:0,0?q=" + lat + "," + lon + "?z=21";

                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse(usersLocation);

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Log.e(TAG, "onClick: maps is unavailable!");
                }

            }
        });

        holder.txtDateTime.setText(imageDataList.get(position).getModifiedDate());
        holder.txtEmpName.setText(imageDataList.get(position).getName());
        holder.txtRefId.setText(imageDataList.get(position).getReferanceId());


        if (imageDataList.get(position).getReferanceId() != null && imageDataList.get(position).getReferanceId().substring(0, 5).matches("^[SsSsSsBbAa]+$")){
            holder.txtHs.setText("Street Id");
        }else if (imageDataList.get(position).getReferanceId() != null && imageDataList.get(position).getReferanceId().substring(0, 5).matches("^[LlWwSsBbAa]+$")){
            holder.txtHs.setText("Liquid Id");
        }else if (imageDataList.get(position).getReferanceId() != null && imageDataList.get(position).getReferanceId().substring(0, 5).matches("^[DdYySsBbAa]+$")){
            holder.txtHs.setText("DumpYard Id");
        }else if (imageDataList.get(position).getReferanceId() != null && imageDataList.get(position).getReferanceId().substring(0, 5).matches("^[HhPpSsBbAa]+$")){
            holder.txtHs.setText("House Id");
        }

    }


    @Override
    public int getItemCount() {
        return imageDataList.size();
    }

    public void dumpYardList(List<HouseDetailsImageDTO> dumpYardList) {
        imageDataList.clear();
        imageDataList = dumpYardList;
        notifyDataSetChanged();
    }

    public void getLiquidList(List<HouseDetailsImageDTO> liquidList) {
        imageDataList.clear();
        imageDataList = liquidList;
        notifyDataSetChanged();
    }

    public void getStreetList(List<HouseDetailsImageDTO> streetList) {
//        imageDataList.clear();
        imageDataList = streetList;
        notifyDataSetChanged();
    }

    public void filterList(List<HouseDetailsImageDTO> filteredList) {
        imageDataList = filteredList;
        notifyDataSetChanged();
        listItemCount = imageDataList.size();
    }

    interface OnTextClickListener {
//        void onTextClick(listItemCount);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtDateTime, txtRefId, txtEmpName, txtLatitude, txtLongitude, txtHs;
        private final ImageView imgPhoto;
        private final TextView txtImgAccept, txtImgReject;
        private final CardView cardImgAccept, cardImgReject, cardDirections;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDateTime = itemView.findViewById(R.id.txt_date_time);
            txtRefId = itemView.findViewById(R.id.txt_houseId);
            txtHs = itemView.findViewById(R.id.txt_hs);
            txtEmpName = itemView.findViewById(R.id.txt_empName);
            txtLatitude = itemView.findViewById(R.id.txt_latitude);
            txtLongitude = itemView.findViewById(R.id.txt_longitude);
            imgPhoto = itemView.findViewById(R.id.img_photos);
            cardDirections = itemView.findViewById(R.id.card_getDirections);
            txtImgAccept = itemView.findViewById(R.id.txt_img_accept);
            txtImgReject = itemView.findViewById(R.id.txt_img_reject);
            cardImgAccept = itemView.findViewById(R.id.card_img_accept);
            cardImgReject = itemView.findViewById(R.id.card_img_reject);
        }
    }

    public interface MyClickListener {
        void onItemClicked(String houseId, Boolean status);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
