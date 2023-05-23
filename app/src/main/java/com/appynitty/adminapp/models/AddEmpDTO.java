package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddEmpDTO {

    @SerializedName("EmpId")
    @Expose
    private String qrEmpId;

    @SerializedName("qrEmpName")
    @Expose
    private String qrEmpName;

    @SerializedName("qrEmpLoginId")
    @Expose
    private String qrEmpLoginId;

    @SerializedName("qrEmpPassword")
    @Expose
    private String qrEmpPassword;

    @SerializedName("qrEmpMobileNumber")
    @Expose
    private String qrEmpMobileNumber;

    @SerializedName("qrEmpAddress")
    @Expose
    private String qrEmpAddress;

    @SerializedName("imoNo")
    @Expose
    private String imoNo;

    @SerializedName("isActive")
    @Expose
    private String isActive;


    public AddEmpDTO(String qrEmpId, String qrEmpName,String qrEmpMobileNumber,String qrEmpAddress, String qrEmpLoginId, String qrEmpPassword, String imoNo, String isActive) {
        super();
        this.qrEmpId = qrEmpId;
        this.qrEmpName = qrEmpName;
        this.qrEmpMobileNumber = qrEmpMobileNumber;
        this.qrEmpAddress = qrEmpAddress;
        this.qrEmpLoginId = qrEmpLoginId;
        this.qrEmpPassword = qrEmpPassword;
        this.imoNo = imoNo;
        this.isActive = isActive;
    }

    public AddEmpDTO() {
    }

    public String getQrEmpId() {
        return qrEmpId;
    }

    public void setQrEmpId(String qrEmpId) {
        this.qrEmpId = qrEmpId;
    }

    public String getQrEmpName() {
        return qrEmpName;
    }

    public void setQrEmpName(String qrEmpName) {
        this.qrEmpName = qrEmpName;
    }

    public String getQrEmpLoginId() {
        return qrEmpLoginId;
    }

    public void setQrEmpLoginId(String qrEmpLoginId) {
        this.qrEmpLoginId = qrEmpLoginId;
    }

    public String getQrEmpPassword() {
        return qrEmpPassword;
    }

    public void setQrEmpPassword(String qrEmpPassword) {
        this.qrEmpPassword = qrEmpPassword;
    }

    public String getQrEmpMobileNumber() {
        return qrEmpMobileNumber;
    }

    public void setQrEmpMobileNumber(String qrEmpMobileNumber) {
        this.qrEmpMobileNumber = qrEmpMobileNumber;
    }

    public String getQrEmpAddress() {
        return qrEmpAddress;
    }

    public void setQrEmpAddress(String qrEmpAddress) {
        this.qrEmpAddress = qrEmpAddress;
    }

    public String getImoNo() {
        return imoNo;
    }

    public void setImoNo(String imoNo) {
        this.imoNo = imoNo;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }


    public boolean isEmpUsernameValid() {
        return getQrEmpLoginId().length() > 4;
    }
    /*public boolean isEmpMobileValid() {
        return getQrEmpMobileNumber().length() <= 10;
    }*/


    public boolean isEmpPassValid() {
        return getQrEmpPassword().length() > 4;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AddEmpDTO.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("EmpId");
        sb.append('=');
        sb.append(((this.qrEmpId == null)?"<null>":this.qrEmpId));
        sb.append(',');
        sb.append("qrEmpName");
        sb.append('=');
        sb.append(((this.qrEmpName == null)?"<null>":this.qrEmpName));
        sb.append(',');
        sb.append("qrEmpLoginId");
        sb.append('=');
        sb.append(((this.qrEmpLoginId == null)?"<null>":this.qrEmpLoginId));
        sb.append(',');
        sb.append("qrEmpPassword");
        sb.append('=');
        sb.append(((this.qrEmpPassword == null)?"<null>":this.qrEmpPassword));
        sb.append(',');
        sb.append("qrEmpMobileNumber");
        sb.append('=');
        sb.append(((this.qrEmpMobileNumber == null)?"<null>":this.qrEmpMobileNumber));
        sb.append(',');
        sb.append("qrEmpAddress");
        sb.append('=');
        sb.append(((this.qrEmpAddress == null)?"<null>":this.qrEmpAddress));
        sb.append(',');
        sb.append("imoNo");
        sb.append('=');
        sb.append(((this.imoNo == null)?"<null>":this.imoNo));
        sb.append(',');
        sb.append("isActive");
        sb.append('=');
        sb.append(((this.isActive == null)?"<null>":this.isActive));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
