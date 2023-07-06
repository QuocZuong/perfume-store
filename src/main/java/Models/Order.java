package Models;

import java.util.Date;

public class Order {
  private int ID;
  private int clientID;
  private Date date;
  private String Address;
  private String PhoneNumber;
  private String Note;
  private int sum;

  public Order() {
  }

  public Order(int iD, int clientID, Date date, String address, String phoneNumber, String note, int sum) {
    ID = iD;
    this.clientID = clientID;
    this.date = date;
    Address = address;
    PhoneNumber = phoneNumber;
    Note = note;
    this.sum = sum;
  }

  public void setPhoneNumber(String phoneNumber) {
    PhoneNumber = phoneNumber;
  }

  public String getNote() {
    return Note;
  }

  public void setNote(String note) {
    Note = note;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public int getClientID() {
    return clientID;
  }

  public void setClientID(int clientID) {
    this.clientID = clientID;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getSum() {
    return sum;
  }

  public void setSum(int sum) {
    this.sum = sum;
  }

  public String getAddress() {
    return Address;
  }

  public void setAddress(String address) {
    Address = address;
  }

  public String getPhoneNumber() {
    return PhoneNumber;
  }
}
