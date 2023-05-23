package com.appynitty.adminapp.models;

public class FilterDTO {
    String fromDate;
    String toDate;
    String userId;

    public FilterDTO(String fromDate, String toDate, String userId) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.userId = userId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FilterDTO{" +
                "fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
