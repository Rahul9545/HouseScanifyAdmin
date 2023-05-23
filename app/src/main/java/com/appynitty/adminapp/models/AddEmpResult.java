package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddEmpResult {
    @SerializedName("ID")
    @Expose
    private Integer ID;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("messageMar")
    @Expose
    private String messageMar;

    @SerializedName("isAttendanceOff")
    @Expose
    private boolean isAttendenceOff;


    public AddEmpResult(Integer ID, String status, String message, String messageMar, boolean isAttendenceOff) {
        this.ID = ID;
        this.status = status;
        this.message = message;
        this.messageMar = messageMar;
        this.isAttendenceOff = isAttendenceOff;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

    public String getMessageMar() {
        return messageMar;
    }

    public void setMessageMar(String messageMar) {
        this.messageMar = messageMar;
    }

    public boolean isAttendenceOff() {
        return isAttendenceOff;
    }

    public void setAttendenceOff(boolean attendenceOff) {
        isAttendenceOff = attendenceOff;
    }

    @Override
    public String toString() {
        return "AddEmpResult{" +
                "ID=" + ID +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", messageMar='" + messageMar + '\'' +
                ", isAttendanceOff=" + isAttendenceOff +
                '}';
    }
}
