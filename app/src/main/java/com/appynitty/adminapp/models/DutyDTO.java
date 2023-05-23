package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DutyDTO {

    @Expose
    @SerializedName("qrEmpId")
    String EmpId;

    @Expose
    @SerializedName("startLat")
    String startLat;

    @Expose
    @SerializedName("startLong")
    String startLong;

    @Expose
    @SerializedName("endLat")
    String endLatitude;

    @Expose
    @SerializedName("endLong")
    String endLongitude;

    @Expose
    @SerializedName("startTime")
    String startTime;

    @Expose
    @SerializedName("startDate")
    String startDate;

    @Expose
    @SerializedName("endTime")
    String endTime;

    @Expose
    @SerializedName("endDate")
    String endDate;


    @Expose
    @SerializedName("EmployeeType")
    String EmployeeType;

    @Expose
    @SerializedName("batteryStatus")
    String batteryStatus;

    @Expose
    @SerializedName("status")
    String status;

    @Expose
    @SerializedName("message")
    String message;

    @Expose
    @SerializedName("isAttendanceOff")
    Boolean isAttendanceOff;

    @Expose
    @SerializedName("isAttendenceOn")
    Boolean isAttendanceOn;

    String onFailureMsg;

    public DutyDTO(String startLat, String startLong, String endLatitude, String endLongitude,
                   String startTime, String startDate, String endTime, String endDate) {
        this.startLat = startLat;
        this.startLong = startLong;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endTime = endTime;
        this.endDate = endDate;
    }

    public DutyDTO() {

    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        this.EmpId = empId;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLong() {
        return startLong;
    }

    public void setStartLong(String startLong) {
        this.startLong = startLong;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEmployeeType() {
        return EmployeeType;
    }

    public void setEmployeeType(String employeeType) {
        EmployeeType = employeeType;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
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

    public Boolean getIsAttendanceOff() {
        return isAttendanceOff;
    }

    public void setIsAttendanceOff(Boolean isAttendanceOff) {
        this.isAttendanceOff = isAttendanceOff;
    }

    public Boolean getIsAttendanceOn() {
        return isAttendanceOn;
    }

    public void setAttendanceOn(Boolean attendanceOn) {
        isAttendanceOn = attendanceOn;
    }

    public String getOnFailureMsg() {
        return onFailureMsg;
    }

    public void setOnFailureMsg(String onFailureMsg) {
        this.onFailureMsg = onFailureMsg;
    }
}
