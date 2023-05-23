package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddUserRoleRightDTO {

    @SerializedName("EmpId")
    @Expose
    private String empId;

    @SerializedName("EmpName")
    @Expose
    private String empName;

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
    private String isActive;

    @SerializedName("isActiveULB")
    @Expose
    private String isActiveULB;

    /**
     *
     * @param empId
     * @param password
     * @param loginId
     * @param isActiveULB
     * @param empName
     * @param empMobileNumber
     * @param empAddress
     * @param type
     * @param isActive
     */
    public AddUserRoleRightDTO(String empId, String empName,String empMobileNumber,String empAddress, String loginId, String password, String type, String isActive, String isActiveULB) {
        super();
        this.empId = empId;
        this.empName = empName;
        this.empMobileNumber = empMobileNumber;
        this.empAddress = empAddress;
        this.loginId = loginId;
        this.password = password;
        this.type = type;
        this.isActive = isActive;
        this.isActiveULB = isActiveULB;
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public AddUserRoleRightDTO() {

    }


    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsActiveULB() {
        return isActiveULB;
    }

    public void setIsActiveULB(String isActiveULB) {
        this.isActiveULB = isActiveULB;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AddUserRoleRightDTO.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("empId");
        sb.append('=');
        sb.append(((this.empId == null)?"<null>":this.empId));
        sb.append(',');
        sb.append("empName");
        sb.append('=');
        sb.append(((this.empName == null)?"<null>":this.empName));
        sb.append(',');
        sb.append("loginId");
        sb.append('=');
        sb.append(((this.loginId == null)?"<null>":this.loginId));
        sb.append(',');
        sb.append("password");
        sb.append('=');
        sb.append(((this.password == null)?"<null>":this.password));
        sb.append(',');
        sb.append("empMobileNumber");
        sb.append('=');
        sb.append(((this.empMobileNumber == null)?"<null>":this.empMobileNumber));
        sb.append(',');
        sb.append("empAddress");
        sb.append('=');
        sb.append(((this.empAddress == null)?"<null>":this.empAddress));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("isActive");
        sb.append('=');
        sb.append(((this.isActive == null)?"<null>":this.isActive));
        sb.append(',');
        sb.append("isActiveULB");
        sb.append('=');
        sb.append(((this.isActiveULB == null)?"<null>":this.isActiveULB));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
