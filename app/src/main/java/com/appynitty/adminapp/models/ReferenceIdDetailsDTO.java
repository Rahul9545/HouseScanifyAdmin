package com.appynitty.adminapp.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferenceIdDetailsDTO {

    int houseId;
    String Lat;

    @SerializedName("Long")
    @Expose
    String Lon;

    String ReferanceId;
    String Name;
    String QRCodeImage;
    String modifiedDate;
    int totalRowCount;
    Boolean QRStatus;
    String QRStatusDate;

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLon() {
        return Lon;
    }

    public void setLong(String aLong) {
        Lon = aLong;
    }

    public String getReferanceId() {
        return ReferanceId;
    }

    public void setReferanceId(String referanceId) {
        ReferanceId = referanceId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQRCodeImage() {
        return QRCodeImage;
    }

    public void setQRCodeImage(String QRCodeImage) {
        this.QRCodeImage = QRCodeImage;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getTotalRowCount() {
        return totalRowCount;
    }

    public void setTotalRowCount(int totalRowCount) {
        this.totalRowCount = totalRowCount;
    }

    public Boolean getQRStatus() {
        return QRStatus;
    }

    public void setQRStatus(Boolean QRStatus) {
        this.QRStatus = QRStatus;
    }

    public String getQRStatusDate() {
        return QRStatusDate;
    }

    public void setQRStatusDate(String QRStatusDate) {
        this.QRStatusDate = QRStatusDate;
    }

    @BindingAdapter("houseImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().fitCenter())
                .into(view);
    }

    public String ToString() {
        return "{" +
                "houseId=" + houseId +
                ", Lat='" + Lat + '\'' +
                ", Long='" + Lon + '\'' +
                ", ReferanceId='" + ReferanceId + '\'' +
                ", Name='" + Name + '\'' +
                ", QRCodeImage='" + QRCodeImage + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                ", totalRowCount=" + totalRowCount +
                ", QRStatus=" + QRStatus +
                ", QRStatusDate='" + QRStatusDate + '\'' +
                '}';
    }
}
