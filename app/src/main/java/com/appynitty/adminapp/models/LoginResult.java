package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("name")
    @Expose
    private String userName;

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("userLoginId")
    @Expose
    private String userLoginId;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;
    @SerializedName("imiNo")
    @Expose
    private String imiNo;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("EmpType")
    @Expose
    private String empType;
    @SerializedName("typeId")
    @Expose
    private Integer typeId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("messageMar")
    @Expose
    private String messageMar;
    @SerializedName("gtFeatures")
    @Expose
    private Boolean gtFeatures;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getImiNo() {
        return imiNo;
    }

    public void setImiNo(String imiNo) {
        this.imiNo = imiNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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

    public Boolean getGtFeatures() {
        return gtFeatures;
    }

    public void setGtFeatures(Boolean gtFeatures) {
        this.gtFeatures = gtFeatures;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
