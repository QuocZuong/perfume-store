package Models;

import java.sql.Date;
import java.util.ArrayList;

public class Voucher {
  private int Id;
  private String code;
  private int quantity;
  private int discountPercent;
  private int discountMax;
  private Date createdAt;
  private Date expiredAt;
  private int createdByAdmin;
  private ArrayList<Integer> approvedProductId;
  

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

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getExpiredAt() {
    return expiredAt;
  }

  public void setExpiredAt(Date expiredAt) {
    this.expiredAt = expiredAt;
  }

  public int getCreatedByAdmin() {
    return createdByAdmin;
  }

  public void setCreatedByAdmin(int createdByAdmin) {
    this.createdByAdmin = createdByAdmin;
  }

    public ArrayList<Integer> getApprovedProductId() {
        return approvedProductId;
    }

    public void setApprovedProductId(ArrayList<Integer> approvedProductId) {
        this.approvedProductId = approvedProductId;
    }
  
  
}
