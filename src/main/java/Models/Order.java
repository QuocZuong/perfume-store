package Models;

import java.util.List;

import Lib.Generator;

public class Order {

    private int Id;
    private int customerId;
    private int voucherId;
    private String receiverName;
    private String deliveryAddress;
    private String phoneNumber;
    private String note;
    private int total;
    private int deductedPrice;
    private String status;
    private Long createdAt;
    private Long checkoutAt;
    private Long updateAt;
    private int updateByOrderManager;
    private List<OrderDetail> orderDetailList;

    /**
     * @return int return the Id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * @return int return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return int return the voucherId
     */
    public int getVoucherId() {
        return voucherId;
    }

    /**
     * @param voucherId the voucherId to set
     */
    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    /**
     * @return String return the receiverName
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * @param receiverName the receiverName to set
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * @return String return the deliveryAddress
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * @param deliveryAddress the deliveryAddress to set
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * @return String return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return String return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return int return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return int return the deductedPrice
     */
    public int getDeductedPrice() {
        return deductedPrice;
    }

    /**
     * @param deductedPrice the deductedPrice to set
     */
    public void setDeductedPrice(int deductedPrice) {
        this.deductedPrice = deductedPrice;
    }

    /**
     * @return String return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt(Generator.DatePattern pattern) {
        return Generator.getDateTime(createdAt, pattern);
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCheckoutAt(Generator.DatePattern pattern) {
        return Generator.getDateTime(checkoutAt, pattern);
    }

    public Long getCheckoutAt() {
        return checkoutAt;
    }

    public void setCheckoutAt(Long checkoutAt) {
        this.checkoutAt = checkoutAt;
    }

    public String getUpdateAt(Generator.DatePattern pattern) {
        return Generator.getDateTime(updateAt, pattern);
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * @return int return the updateByOrderManager
     */
    public int getUpdateByOrderManager() {
        return updateByOrderManager;
    }

    /**
     * @param updateByOrderManager the updateByOrderManager to set
     */
    public void setUpdateByOrderManager(int updateByOrderManager) {
        this.updateByOrderManager = updateByOrderManager;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

}
