package com.appynitty.adminapp.models;

public class QREmployeeDTO {
    private String EmployeeName;
    private String EmployeeID;

    public QREmployeeDTO(String employeeName, String employeeID) {
        EmployeeName = employeeName;
        EmployeeID = employeeID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    @Override
    public String toString() {
        return "QREmployeeDTO{" +
                "EmployeeName='" + EmployeeName + '\'' +
                ", EmployeeID='" + EmployeeID + '\'' +
                '}';
    }
}
