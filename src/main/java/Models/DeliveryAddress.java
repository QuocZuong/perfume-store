package Models;

import java.sql.Date;

public class DeliveryAddress {

    private int customerId;
    private String receiverName;
    private String phoneNumber;
    private String address;
    private String status;
    private Date Create_At;
    private Date Modified_At;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreate_At() {
        return Create_At;
    }

    public void setCreate_At(Date Create_At) {
        this.Create_At = Create_At;
    }

    public Date getModified_At() {
        return Modified_At;
    }

    public void setModified_At(Date Modified_At) {
        this.Modified_At = Modified_At;
    }

}
