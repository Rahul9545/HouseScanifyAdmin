package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardDTO {

    // These are the required fields For request
    private String EmpType;
    private String userId;

    public DashboardDTO(String empType, String userId) {
        EmpType = empType;
        this.userId = userId;
    }

    public DashboardDTO() {
    }

    public String getEmpType() {
        return EmpType;
    }

    public void setEmpType(String empType) {
        EmpType = empType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // These are the required fields For response
    @SerializedName("ulb")
    @Expose
    private String ulb;
    @SerializedName("appid")
    @Expose
    private Integer appid;

    public String getUlb() {
        return ulb;
    }

    public void setUlb(String ulb) {
        this.ulb = ulb;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }
}
