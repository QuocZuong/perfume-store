package Models;

import java.sql.Date;

public class Order {
  private int Id;
  private int customerId;
  private int orderDeliveryAddressId;
  private int orderReceiverName;
  private String orderPhoneNumber;
  private int total;
  private String orderStatus;
  private Date createdAt;
  private Date checkoutAt;
  private Date updateAt;

  public int getId() {
    return Id;
  }

  public void setId(int Id) {
    this.Id = Id;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getOrderDeliveryAddressId() {
    return orderDeliveryAddressId;
  }

  public void setOrderDeliveryAddressId(int orderDeliveryAddressId) {
    this.orderDeliveryAddressId = orderDeliveryAddressId;
  }

  public int getOrderReceiverName() {
    return orderReceiverName;
  }

  public void setOrderReceiverName(int orderReceiverName) {
    this.orderReceiverName = orderReceiverName;
  }

  public String getOrderPhoneNumber() {
    return orderPhoneNumber;
  }

  public void setOrderPhoneNumber(String orderPhoneNumber) {
    this.orderPhoneNumber = orderPhoneNumber;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getCheckoutAt() {
    return checkoutAt;
  }

  public void setCheckoutAt(Date checkoutAt) {
    this.checkoutAt = checkoutAt;
  }

  public Date getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(Date updateAt) {
    this.updateAt = updateAt;
  }
}
