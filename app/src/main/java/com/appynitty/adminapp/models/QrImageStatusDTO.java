package com.appynitty.adminapp.models;

public class QrImageStatusDTO {
    private String ReferanceId;
    private Boolean QRStatus;
    private String status;
    private String message;
    private String messageMar;

    public QrImageStatusDTO(String referanceId, Boolean QRStatus) {
        ReferanceId = referanceId;
        this.QRStatus = QRStatus;
    }

    public QrImageStatusDTO() {

    }

    public String getReferanceId() {
        return ReferanceId;
    }

    public void setReferanceId(String referanceId) {
        ReferanceId = referanceId;
    }

    public Boolean getQRStatus() {
        return QRStatus;
    }

    public void setQRStatus(Boolean QRStatus) {
        this.QRStatus = QRStatus;
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

    public String getMessageMar() {
        return messageMar;
    }

    public void setMessageMar(String messageMar) {
        this.messageMar = messageMar;
    }

    @Override
    public String toString() {
        return "QrImageStatusDTO{" +
                "ReferanceId='" + ReferanceId + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", messageMar='" + messageMar + '\'' +
                '}';
    }
}
