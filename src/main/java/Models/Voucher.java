package Models;

import java.util.List;

import Lib.Generator;

public class Voucher {

  private int Id;
  private String code;
  private int quantity;
  private int discountPercent;
  private int discountMax;
  private long createdAt;
  private long expiredAt;
  private int createdByAdmin;
  private List<Integer> approvedProductId;

  public int getId() {
    return Id;
  }

  public void setId(int Id) {
    this.Id = Id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getDiscountPercent() {
    return discountPercent;
  }

  public void setDiscountPercent(int discountPercent) {
    this.discountPercent = discountPercent;
  }

  public int getDiscountMax() {
    return discountMax;
  }

  public void setDiscountMax(int discountMax) {
    this.discountMax = discountMax;
  }

  public String getCreatedAt(Generator.DatePattern pattern) {
    return Generator.getDateTime(createdAt, pattern);
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public String getExpiredAt(Generator.DatePattern pattern) {
    return Generator.getDateTime(expiredAt, pattern);
  }

  public long getExpiredAt() {
    return expiredAt;
  }

  public void setExpiredAt(long expiredAt) {
    this.expiredAt = expiredAt;
  }

  public int getCreatedByAdmin() {
    return createdByAdmin;
  }

  public void setCreatedByAdmin(int createdByAdmin) {
    this.createdByAdmin = createdByAdmin;
  }

  public List<Integer> getApprovedProductId() {
    return approvedProductId;
  }

  public void setApprovedProductId(List<Integer> approvedProductId) {
    this.approvedProductId = approvedProductId;
  }

}
