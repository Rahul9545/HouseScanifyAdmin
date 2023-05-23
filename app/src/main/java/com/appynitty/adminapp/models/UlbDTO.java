package com.appynitty.adminapp.models;

//[{"ulb":"Wardha Nagar Parishad","appid":3087},{"ulb":"Pulgaon Nagar Parishad","appid":3090}]
public class UlbDTO {
    private String ulbName;
    private int appId;

    public UlbDTO(String ulbName, int appId) {
        this.ulbName = ulbName;
        this.appId = appId;
    }

    public String getUlbName() {
        return ulbName;
    }

    public void setUlbName(String ulbName) {
        this.ulbName = ulbName;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }
}
