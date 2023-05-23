package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceDTO {
    @SerializedName("qrEmpDaId")
    @Expose
    private int qrEmpDaId;

    @SerializedName("EmpId")
    @Expose
    private int qrEmpId;

    @SerializedName("startLat")
    @Expose
    private String startLat;

    @SerializedName("startLong")
    @Expose
    private String startLong;

    @SerializedName("endLat")
    @Expose
    private String endLat;

    @SerializedName("endLong")
    @Expose
    private String endLong;

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
    private String startNote;

    @SerializedName("endNote")
    @Expose
    private String endNote;

    @SerializedName("batteryStatus")
    @Expose
    private String batteryStatus = null;

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("CompareDate")
    @Expose
    private String CompareDate;

    @SerializedName("HouseCount")
    @Expose
    private int HouseCount;

    @SerializedName("LiquidCount")
    @Expose
    private int LiquidCount;

    @SerializedName("StreetCount")
    @Expose
    private int StreetCount;

    @SerializedName("daDateTIme")
    @Expose
    private String daDateTIme;

    @SerializedName("DumpYardCount")
    @Expose
    private int DumpYardCount;

    public AttendanceDTO(String startDate, String startTime, String endDate,String endTime, String userName, int houseCount, int liquidCount, int streetCount, int dumpYardCount) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.userName = userName;
        HouseCount = houseCount;
        LiquidCount = liquidCount;
        StreetCount = streetCount;
        DumpYardCount = dumpYardCount;
    }

    public int getQrEmpDaId() {
        return qrEmpDaId;
    }

    public void setQrEmpDaId(int qrEmpDaId) {
        this.qrEmpDaId = qrEmpDaId;
    }

    public int getQrEmpId() {
        return qrEmpId;
    }

    public void setQrEmpId(int qrEmpId) {
        this.qrEmpId = qrEmpId;
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

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLong() {
        return endLong;
    }

    public void setEndLong(String endLong) {
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

    public String getStartNote() {
        return startNote;
    }

    public void setStartNote(String startNote) {
        this.startNote = startNote;
    }

    public String getEndNote() {
        return endNote;
    }

    public void setEndNote(String endNote) {
        this.endNote = endNote;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompareDate() {
        return CompareDate;
    }

    public void setCompareDate(String compareDate) {
        CompareDate = compareDate;
    }

    public int getHouseCount() {
        return HouseCount;
    }

    public void setHouseCount(int houseCount) {
        HouseCount = houseCount;
    }

    public int getLiquidCount() {
        return LiquidCount;
    }

    public void setLiquidCount(int liquidCount) {
        LiquidCount = liquidCount;
    }

    public int getStreetCount() {
        return StreetCount;
    }

    public void setStreetCount(int streetCount) {
        StreetCount = streetCount;
    }

    public String getDaDateTIme() {
        return daDateTIme;
    }

    public void setDaDateTIme(String daDateTIme) {
        this.daDateTIme = daDateTIme;
    }

    public int getDumpYardCount() {
        return DumpYardCount;
    }

    public void setDumpYardCount(int dumpYardCount) {
        DumpYardCount = dumpYardCount;
    }


    @Override
    public String toString() {
        return "AttendanceDTO{" +
                "qrEmpDaId=" + qrEmpDaId +
                ", EmpId=" + qrEmpId +
                ", startLat='" + startLat + '\'' +
                ", startLong='" + startLong + '\'' +
                ", endLat='" + endLat + '\'' +
                ", endLong='" + endLong + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startNote='" + startNote + '\'' +
                ", endNote='" + endNote + '\'' +
                ", batteryStatus='" + batteryStatus + '\'' +
                ", userName='" + userName + '\'' +
                ", CompareDate='" + CompareDate + '\'' +
                ", HouseCount=" + HouseCount +
                ", LiquidCount=" + LiquidCount +
                ", StreetCount=" + StreetCount +
                ", daDateTIme='" + daDateTIme + '\'' +
                ", DumpYardCount=" + DumpYardCount +
                '}';
    }
}
