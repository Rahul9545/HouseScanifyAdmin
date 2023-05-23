package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLocationDTO {

    @SerializedName("Long")
    @Expose
    private String _long;
    @SerializedName("OfflineId")
    @Expose
    private int offlineId;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("isOffline")
    @Expose
    private Boolean isOffline;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isAttendanceOff")
    @Expose
    private Boolean isAttendanceOff;

    public UserLocationDTO(String _long, int offlineId, String datetime, String distance, Boolean isOffline, String lat, String userId) {
        this._long = _long;
        this.offlineId = offlineId;
        this.datetime = datetime;
        this.distance = distance;
        this.isOffline = isOffline;
        this.lat = lat;
        this.userId = userId;
    }

    public String get_long() {
        return _long;
    }

    public void set_long(String _long) {
        this._long = _long;
    }

    public int getOfflineId() {
        return offlineId;
    }

    public void setOfflineId(int offlineId) {
        this.offlineId = offlineId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Boolean getOffline() {
        return isOffline;
    }

    public void setOffline(Boolean offline) {
        isOffline = offline;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAttendanceOff() {
        return isAttendanceOff;
    }

    public void setAttendanceOff(Boolean attendanceOff) {
        isAttendanceOff = attendanceOff;
    }


    public String ToString() {
        return "{" +
                "_long='" + _long + '\'' +
                ", offlineId='" + offlineId + '\'' +
                ", datetime='" + datetime + '\'' +
                ", distance='" + distance + '\'' +
                ", isOffline=" + isOffline +
                ", lat='" + lat + '\'' +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", isAttendanceOff=" + isAttendanceOff +
                '}';
    }
}
