package Models;

import java.sql.Date;

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
  private Date createdAt;
  private Date checkoutAt;
  private Date updateAt;
  private int updateByOrderManager;

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

  /**
   * @return Date return the createdAt
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * @param createdAt the createdAt to set
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * @return Date return the checkoutAt
   */
  public Date getCheckoutAt() {
    return checkoutAt;
  }

  /**
   * @param checkoutAt the checkoutAt to set
   */
  public void setCheckoutAt(Date checkoutAt) {
    this.checkoutAt = checkoutAt;
  }

  /**
   * @return Date return the updateAt
   */
  public Date getUpdateAt() {
    return updateAt;
  }

  /**
   * @param updateAt the updateAt to set
   */
  public void setUpdateAt(Date updateAt) {
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

}
