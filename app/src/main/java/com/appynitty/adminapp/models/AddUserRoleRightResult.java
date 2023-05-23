package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddUserRoleRightResult {

    @SerializedName("ID")
    @Expose
    private int id;

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


    /**
     * No args constructor for use in serialization
     *
     */
    public AddUserRoleRightResult() {

    }

    /**
     *
     * @param isAttendenceOff
     * @param id
     * @param message
     * @param messageMar
     * @param status
     */
    public AddUserRoleRightResult(int id, String status, String message, String messageMar, boolean isAttendenceOff) {
        super();
        this.id = id;
        this.status = status;
        this.message = message;
        this.messageMar = messageMar;
        this.isAttendenceOff = isAttendenceOff;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        StringBuilder sb = new StringBuilder();
        sb.append(AddUserRoleRightResult.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(this.id);
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("message");
        sb.append('=');
        sb.append(((this.message == null)?"<null>":this.message));
        sb.append(',');
        sb.append("messageMar");
        sb.append('=');
        sb.append(((this.messageMar == null)?"<null>":this.messageMar));
        sb.append(',');
        sb.append("isAttendanceOff");
        sb.append('=');
        sb.append(this.isAttendenceOff);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
