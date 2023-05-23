package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificUlbDTO {
    @SerializedName("AppId")
    @Expose
    private Integer AppId;

    @SerializedName("AppName")
    @Expose
    private String AppName;

    @SerializedName("TotalHouse")
    @Expose
    private Integer TotalHouse;

    @SerializedName("TotalHouseUpdated")
    @Expose
    private Integer TotalHouseUpdated;

    @SerializedName("TotalHouseUpdated_CurrentDay")
    @Expose
    private Integer TotalHouseUpdated_CurrentDay;

    @SerializedName("TotalPoint")
    @Expose
    private Integer TotalPoint;

    @SerializedName("TotalPointUpdated")
    @Expose
    private Integer TotalPointUpdated;

    @SerializedName("TotalPointUpdated_CurrentDay")
    @Expose
    private Integer TotalPointUpdated_CurrentDay;

    @SerializedName("TotalDump")
    @Expose
    private Integer TotalDump;

    @SerializedName("TotalDumpUpdated")
    @Expose
    private Integer TotalDumpUpdated;

    @SerializedName("TotalDumpUpdated_CurrentDay")
    @Expose
    private Integer TotalDumpUpdated_CurrentDay;

    @SerializedName("TotalLiquid")
    @Expose
    private Integer TotalLiquid;

    @SerializedName("TotalLiquidUpdated")
    @Expose
    private Integer TotalLiquidUpdated;

    @SerializedName("TotalLiquidUpdated_CurrentDay")
    @Expose
    private Integer TotalLiquidUpdated_CurrentDay;

    @SerializedName("TotalStreet")
    @Expose
    private Integer TotalStreet;

    @SerializedName("TotalStreetUpdated")
    @Expose
    private Integer TotalStreetUpdated;

    @SerializedName("TotalStreetUpdated_CurrentDay")
    @Expose
    private Integer TotalStreetUpdated_CurrentDay;

    public Integer getAppId() {
        return AppId;
    }

    public void setAppId(Integer appId) {
        AppId = appId;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public Integer getTotalHouse() {
        return TotalHouse;
    }

    public void setTotalHouse(Integer totalHouse) {
        TotalHouse = totalHouse;
    }

    public Integer getTotalHouseUpdated() {
        return TotalHouseUpdated;
    }

    public void setTotalHouseUpdated(Integer totalHouseUpdated) {
        TotalHouseUpdated = totalHouseUpdated;
    }

    public Integer getTotalHouseUpdated_CurrentDay() {
        return TotalHouseUpdated_CurrentDay;
    }

    public void setTotalHouseUpdated_CurrentDay(Integer totalHouseUpdated_CurrentDay) {
        TotalHouseUpdated_CurrentDay = totalHouseUpdated_CurrentDay;
    }

    public Integer getTotalPoint() {
        return TotalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        TotalPoint = totalPoint;
    }

    public Integer getTotalPointUpdated() {
        return TotalPointUpdated;
    }

    public void setTotalPointUpdated(Integer totalPointUpdated) {
        TotalPointUpdated = totalPointUpdated;
    }

    public Integer getTotalPointUpdated_CurrentDay() {
        return TotalPointUpdated_CurrentDay;
    }

    public void setTotalPointUpdated_CurrentDay(Integer totalPointUpdated_CurrentDay) {
        TotalPointUpdated_CurrentDay = totalPointUpdated_CurrentDay;
    }

    public Integer getTotalDump() {
        return TotalDump;
    }

    public void setTotalDump(Integer totalDump) {
        TotalDump = totalDump;
    }

    public Integer getTotalDumpUpdated() {
        return TotalDumpUpdated;
    }

    public void setTotalDumpUpdated(Integer totalDumpUpdated) {
        TotalDumpUpdated = totalDumpUpdated;
    }

    public Integer getTotalDumpUpdated_CurrentDay() {
        return TotalDumpUpdated_CurrentDay;
    }

    public void setTotalDumpUpdated_CurrentDay(Integer totalDumpUpdated_CurrentDay) {
        TotalDumpUpdated_CurrentDay = totalDumpUpdated_CurrentDay;
    }

    public Integer getTotalLiquid() {
        return TotalLiquid;
    }

    public void setTotalLiquid(Integer totalLiquid) {
        TotalLiquid = totalLiquid;
    }

    public Integer getTotalLiquidUpdated() {
        return TotalLiquidUpdated;
    }

    public void setTotalLiquidUpdated(Integer totalLiquidUpdated) {
        TotalLiquidUpdated = totalLiquidUpdated;
    }

    public Integer getTotalLiquidUpdated_CurrentDay() {
        return TotalLiquidUpdated_CurrentDay;
    }

    public void setTotalLiquidUpdated_CurrentDay(Integer totalLiquidUpdated_CurrentDay) {
        TotalLiquidUpdated_CurrentDay = totalLiquidUpdated_CurrentDay;
    }

    public Integer getTotalStreet() {
        return TotalStreet;
    }

    public void setTotalStreet(Integer totalStreet) {
        TotalStreet = totalStreet;
    }

    public Integer getTotalStreetUpdated() {
        return TotalStreetUpdated;
    }

    public void setTotalStreetUpdated(Integer totalStreetUpdated) {
        TotalStreetUpdated = totalStreetUpdated;
    }

    public Integer getTotalStreetUpdated_CurrentDay() {
        return TotalStreetUpdated_CurrentDay;
    }

    public void setTotalStreetUpdated_CurrentDay(Integer totalStreetUpdated_CurrentDay) {
        TotalStreetUpdated_CurrentDay = totalStreetUpdated_CurrentDay;
    }

    @Override
    public String toString() {
        return "SpecificUlbDTO{" +
                "AppId=" + AppId +
                ", AppName=" + AppName +
                ", TotalHouse=" + TotalHouse +
                ", TotalHouseUpdated=" + TotalHouseUpdated +
                ", TotalHouseUpdated_CurrentDay=" + TotalHouseUpdated_CurrentDay +
                ", TotalPoint=" + TotalPoint +
                ", TotalPointUpdated=" + TotalPointUpdated +
                ", TotalPointUpdated_CurrentDay=" + TotalPointUpdated_CurrentDay +
                ", TotalDump=" + TotalDump +
                ", TotalDumpUpdated=" + TotalDumpUpdated +
                ", TotalDumpUpdated_CurrentDay=" + TotalDumpUpdated_CurrentDay +
                ", TotalLiquid=" + TotalLiquid +
                ", TotalLiquidUpdated=" + TotalLiquidUpdated +
                ", TotalLiquidUpdated_CurrentDay=" + TotalLiquidUpdated_CurrentDay +
                ", TotalStreet=" + TotalStreet +
                ", TotalStreetUpdated=" + TotalStreetUpdated +
                ", TotalStreetUpdated_CurrentDay=" + TotalStreetUpdated_CurrentDay +
                '}';
    }
}
