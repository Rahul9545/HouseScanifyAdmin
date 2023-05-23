package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HouseDetailsImageDTO {

    @SerializedName("houseId")
    @Expose
    private Integer houseId;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Long")
    @Expose
    private String _long;
    @SerializedName("ReferanceId")
    @Expose
    private String referanceId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("QRCodeImage")
    @Expose
    private String qRCodeImage;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("totalRowCount")
    @Expose
    private Integer totalRowCount;
    @SerializedName("QRStatus")
    @Expose
    private Boolean qRStatus;
    @SerializedName("QRStatusDate")
    @Expose
    private Object qRStatusDate;

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getReferanceId() {
        return referanceId;
    }

    public void setReferanceId(String referanceId) {
        this.referanceId = referanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQRCodeImage() {
        return qRCodeImage;
    }

    public void setQRCodeImage(String qRCodeImage) {
        this.qRCodeImage = qRCodeImage;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getTotalRowCount() {
        return totalRowCount;
    }

    public void setTotalRowCount(Integer totalRowCount) {
        this.totalRowCount = totalRowCount;
    }

    public Boolean getQRStatus() {
        return qRStatus;
    }

    public void setQRStatus(Boolean qRStatus) {
        this.qRStatus = qRStatus;
    }

    public Object getQRStatusDate() {
        return qRStatusDate;
    }

    public void setQRStatusDate(Object qRStatusDate) {
        this.qRStatusDate = qRStatusDate;
    }

    /*private Integer dyId;
    private String dyLat;
    private String dyLong;

    @SerializedName("QRStatus")
    @Expose
    private Boolean qRStatus;

    @SerializedName("houseId")
    @Expose
    private Integer houseId;

    @SerializedName("SSId")
    @Expose
    private Integer ssId;

    @SerializedName("LWId")
    @Expose
    private Integer lwId;

    @SerializedName("HouseLat")
    @Expose
    private String houseLat;

    @SerializedName("HouseLong")
    @Expose
    private String houseLong;

    @SerializedName("SSLat")
    @Expose
    private String ssLat;

    @SerializedName("SSLong")
    @Expose
    private String ssLong;

    @SerializedName("LWLat")
    @Expose
    private String lwLat;

    @SerializedName("LWLong")
    @Expose
    private String lwLong;

    @SerializedName("ReferanceId")
    @Expose
    private String referanceId;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("QRCodeImage")
    @Expose
    private String qRCodeImage;

    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;

    @SerializedName("totalRowCount")
    @Expose
    private Integer totalRowCount;

    public Boolean getqRStatus() {
        return qRStatus;
    }

    public void setqRStatus(Boolean qRStatus) {
        this.qRStatus = qRStatus;
    }

    public Integer getDyId() {
        return dyId;
    }

    public void setDyId(Integer dyId) {
        this.dyId = dyId;
    }

    public String getDyLat() {
        return dyLat;
    }

    public void setDyLat(String dyLat) {
        this.dyLat = dyLat;
    }

    public String getDyLong() {
        return dyLong;
    }

    public void setDyLong(String dyLong) {
        this.dyLong = dyLong;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getSsId() {
        return ssId;
    }

    public void setSsId(Integer ssId) {
        this.ssId = ssId;
    }

    public Integer getLwId() {
        return lwId;
    }

    public void setLwId(Integer lwId) {
        this.lwId = lwId;
    }

    public String getHouseLat() {
        return houseLat;
    }

    public void setHouseLat(String houseLat) {
        this.houseLat = houseLat;
    }

    public String getHouseLong() {
        return houseLong;
    }

    public void setHouseLong(String houseLong) {
        this.houseLong = houseLong;
    }

    public String getSsLat() {
        return ssLat;
    }

    public void setSsLat(String ssLat) {
        this.ssLat = ssLat;
    }

    public String getSsLong() {
        return ssLong;
    }

    public void setSsLong(String ssLong) {
        this.ssLong = ssLong;
    }

    public String getLwLat() {
        return lwLat;
    }

    public void setLwLat(String lwLat) {
        this.lwLat = lwLat;
    }

    public String getLwLong() {
        return lwLong;
    }

    public void setLwLong(String lwLong) {
        this.lwLong = lwLong;
    }

    public String getReferanceId() {
        return referanceId;
    }

    public void setReferanceId(String referanceId) {
        this.referanceId = referanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getqRCodeImage() {
        return qRCodeImage;
    }

    public void setqRCodeImage(String qRCodeImage) {
        this.qRCodeImage = qRCodeImage;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getTotalRowCount() {
        return totalRowCount;
    }

    public void setTotalRowCount(Integer totalRowCount) {
        this.totalRowCount = totalRowCount;
    }

    @Override
    public String toString() {
        return "HouseDetailsImageDTO{" +
                "dyId=" + dyId +
                ", dyLat='" + dyLat + '\'' +
                ", dyLong='" + dyLong + '\'' +
                ", houseId=" + houseId +
                ", ssId=" + ssId +
                ", lwId=" + lwId +
                ", houseLat='" + houseLat + '\'' +
                ", houseLong='" + houseLong + '\'' +
                ", ssLat='" + ssLat + '\'' +
                ", ssLong='" + ssLong + '\'' +
                ", lwLat='" + lwLat + '\'' +
                ", lwLong='" + lwLong + '\'' +
                ", referanceId='" + referanceId + '\'' +
                ", name='" + name + '\'' +
                ", qRCodeImage='" + qRCodeImage + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                ", totalRowCount=" + totalRowCount +
                '}';
    }*/
}
