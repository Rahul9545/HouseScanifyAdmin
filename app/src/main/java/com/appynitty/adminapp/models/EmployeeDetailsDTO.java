package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmployeeDetailsDTO {
    @SerializedName("qrEmpId")
    @Expose
    private Integer Emp_Id;

    @SerializedName("qrEmpName")
    @Expose
    private String EmpName;

    @SerializedName("qrEmpMobileNumber")
    @Expose
    private String EmpMobileNumber;

    @SerializedName("qrEmpAddress")
    @Expose
    private String EmpAddress;

    @SerializedName("qrEmpLoginId")
    @Expose
    private String EmpLoginId;

    @SerializedName("qrEmpPassword")
    @Expose
    private String EmpPassword;

    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;

    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    private Integer HouseCount;

    private Integer LiquidCount;

    private Integer StreetCount;

    private Integer DumpCount;

    private Integer PointCount;

    public EmployeeDetailsDTO(String empName, Integer houseCount, Integer liquidCount, Integer streetCount, Integer dumpCount) {
        EmpName = empName;
        HouseCount = houseCount;
        LiquidCount = liquidCount;
        StreetCount = streetCount;
        DumpCount = dumpCount;
    }


    public Integer getEmp_Id() {
        return Emp_Id;
    }

    public void setEmp_Id(Integer emp_Id) {
        Emp_Id = emp_Id;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getEmpMobileNumber() {
        return EmpMobileNumber;
    }

    public void setEmpMobileNumber(String empMobileNumber) {
        EmpMobileNumber = empMobileNumber;
    }

    public String getEmpAddress() {
        return EmpAddress;
    }

    public void setEmpAddress(String empAddress) {
        EmpAddress = empAddress;
    }

    public String getEmpLoginId() {
        return EmpLoginId;
    }

    public void setEmpLoginId(String empLoginId) {
        EmpLoginId = empLoginId;
    }

    public String getEmpPassword() {
        return EmpPassword;
    }

    public void setEmpPassword(String empPassword) {
        EmpPassword = empPassword;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getHouseCount() {
        return HouseCount;
    }

    public void setHouseCount(Integer houseCount) {
        HouseCount = houseCount;
    }

    public Integer getLiquidCount() {
        return LiquidCount;
    }

    public void setLiquidCount(Integer liquidCount) {
        LiquidCount = liquidCount;
    }

    public Integer getStreetCount() {
        return StreetCount;
    }

    public void setStreetCount(Integer streetCount) {
        StreetCount = streetCount;
    }

    public Integer getDumpCount() {
        return DumpCount;
    }

    public void setDumpCount(Integer dumpCount) {
        DumpCount = dumpCount;
    }

    public Integer getPointCount() {
        return PointCount;
    }

    public void setPointCount(Integer pointCount) {
        PointCount = pointCount;
    }

    @Override
    public String toString() {
        return "EmployeeDetailsDTO{" +
                "Emp_Id=" + Emp_Id +
                ", EmpName='" + EmpName + '\'' +
                ", EmpMobileNumber='" + EmpMobileNumber + '\'' +
                ", EmpAddress='" + EmpAddress + '\'' +
                ", EmpLoginId='" + EmpLoginId + '\'' +
                ", EmpPassword='" + EmpPassword + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", isActive=" + isActive +
                ", HouseCount=" + HouseCount +
                ", LiquidCount=" + LiquidCount +
                ", StreetCount=" + StreetCount +
                ", DumpCount=" + DumpCount +
                ", PointCount=" + PointCount +
                '}';
    }
}
