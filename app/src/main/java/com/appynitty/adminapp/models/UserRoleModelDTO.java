package com.appynitty.adminapp.models;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserRoleModelDTO extends BaseObservable implements Serializable {

    @SerializedName("EmpId")
    @Expose
    private Integer empId;

    @SerializedName("EmpName")
    @Expose
    private String empName;

    @SerializedName("EmpNameMar")
    @Expose
    private String empNameMar = null;

    @SerializedName("LoginId")
    @Expose
    private String loginId;

    @SerializedName("Password")
    @Expose
    private String password;

    @SerializedName("EmpMobileNumber")
    @Expose
    private String empMobileNumber;

    @SerializedName("EmpAddress")
    @Expose
    private String empAddress;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    @SerializedName("isActiveULB")
    @Expose
    private String isActiveULB;

    @SerializedName("LastModifyDateEntry")
    @Expose
    private String lastModifyDateEntry;


    public UserRoleModelDTO(Integer empId, String empNameMar, String empMobileNumber, String type, Boolean isActive) {
        this.empId = empId;
        this.empNameMar = empNameMar;
        this.empMobileNumber = empMobileNumber;
        this.type = type;
        this.isActive = isActive;
    }

    public UserRoleModelDTO() {

    }

    public String getEmpId() {
        return String.valueOf(empId);
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNameMar() {
        return empNameMar;
    }

    public void setEmpNameMar(String empNameMar) {
        this.empNameMar = empNameMar;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmpMobileNumber() {
        return empMobileNumber;
    }

    public void setEmpMobileNumber(String empMobileNumber) {
        this.empMobileNumber = empMobileNumber;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getIsActiveULB() {
        return isActiveULB;
    }

    public void setIsActiveULB(String isActiveULB) {
        this.isActiveULB = isActiveULB;
    }

    public String getLastModifyDateEntry() {
        return lastModifyDateEntry;
    }

    public void setLastModifyDateEntry(String lastModifyDateEntry) {
        this.lastModifyDateEntry = lastModifyDateEntry;
    }

    @Override
    public String toString() {
        return "UserRoleModelDTO{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empNameMar='" + empNameMar + '\'' +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", empMobileNumber='" + empMobileNumber + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", type='" + type + '\'' +
                ", isActive=" + isActive +
                ", isActiveULB='" + isActiveULB + '\'' +
                ", lastModifyDateEntry='" + lastModifyDateEntry + '\'' +
                '}';
    }
}
