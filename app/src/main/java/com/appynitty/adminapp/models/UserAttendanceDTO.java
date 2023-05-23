package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAttendanceDTO {

    @SerializedName("qrEmpDaId")
    @Expose
    private long qrEmpDaId;

    @SerializedName("qrEmpId")
    @Expose
    private long qrEmpId;

    @SerializedName("startLat")
    @Expose
    private Object startLat;

    @SerializedName("startLong")
    @Expose
    private Object startLong;

    @SerializedName("endLat")
    @Expose
    private Object endLat;

    @SerializedName("endLong")
    @Expose
    private Object endLong;

    @SerializedName("startTime")
    @Expose
    private String startTime;

    @SerializedName("endTime")
    @Expose
    private String endTime;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("startNote")
    @Expose
    private Object startNote;

    @SerializedName("endNote")
    @Expose
    private Object endNote;

    @SerializedName("batteryStatus")
    @Expose
    private Object batteryStatus;

    @SerializedName("OutbatteryStatus")
    @Expose
    private Object outbatteryStatus;

    @SerializedName("OfflineId")
    @Expose
    private double offlineId;

    @SerializedName("EmployeeType")
    @Expose
    private String employeeType;

    @SerializedName("IpAddress")
    @Expose
    private String ipAddress;

    @SerializedName("LoginDevice")
    @Expose
    private String loginDevice;

    @SerializedName("HostName")
    @Expose
    private String hostName;

    @SerializedName("EmployeeName")
    @Expose
    private String EmployeeName;

    @SerializedName("Status")
    @Expose
    private boolean Status;



    public UserAttendanceDTO(long qrEmpDaId, long qrEmpId, String startTime, String endTime, String startDate, String endDate, String ipAddress, String loginDevice, String hostName, String EmployeeName, boolean Status) {
        this.qrEmpDaId = qrEmpDaId;
        this.qrEmpId = qrEmpId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ipAddress = ipAddress;
        this.loginDevice = loginDevice;
        this.hostName = hostName;
        this.EmployeeName = EmployeeName;
        this.Status = Status;
    }

    public UserAttendanceDTO() {

    }

    public long getQrEmpDaId() {
        return qrEmpDaId;
    }

    public void setQrEmpDaId(long qrEmpDaId) {
        this.qrEmpDaId = qrEmpDaId;
    }

    public long getQrEmpId() {
        return qrEmpId;
    }

    public void setQrEmpId(long qrEmpId) {
        this.qrEmpId = qrEmpId;
    }

    public Object getStartLat() {
        return startLat;
    }

    public void setStartLat(Object startLat) {
        this.startLat = startLat;
    }

    public Object getStartLong() {
        return startLong;
    }

    public void setStartLong(Object startLong) {
        this.startLong = startLong;
    }

    public Object getEndLat() {
        return endLat;
    }

    public void setEndLat(Object endLat) {
        this.endLat = endLat;
    }

    public Object getEndLong() {
        return endLong;
    }

    public void setEndLong(Object endLong) {
        this.endLong = endLong;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Object getStartNote() {
        return startNote;
    }

    public void setStartNote(Object startNote) {
        this.startNote = startNote;
    }

    public Object getEndNote() {
        return endNote;
    }

    public void setEndNote(Object endNote) {
        this.endNote = endNote;
    }

    public Object getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(Object batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public Object getOutbatteryStatus() {
        return outbatteryStatus;
    }

    public void setOutbatteryStatus(Object outbatteryStatus) {
        this.outbatteryStatus = outbatteryStatus;
    }

    public double getOfflineId() {
        return offlineId;
    }

    public void setOfflineId(double offlineId) {
        this.offlineId = offlineId;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLoginDevice() {
        return loginDevice;
    }

    public void setLoginDevice(String loginDevice) {
        this.loginDevice = loginDevice;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }


    @Override
    public String toString() {
        return "UserAttendanceDTO{" +
                "qrEmpDaId=" + qrEmpDaId +
                ", qrEmpId=" + qrEmpId +
                ", startLat=" + startLat +
                ", startLong=" + startLong +
                ", endLat=" + endLat +
                ", endLong=" + endLong +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startNote=" + startNote +
                ", endNote=" + endNote +
                ", batteryStatus=" + batteryStatus +
                ", outbatteryStatus=" + outbatteryStatus +
                ", offlineId=" + offlineId +
                ", employeeType='" + employeeType + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", loginDevice='" + loginDevice + '\'' +
                ", hostName='" + hostName + '\'' +
                ", EmployeeName='" + EmployeeName + '\'' +
                ", Status=" + Status +
                '}';
    }
}
