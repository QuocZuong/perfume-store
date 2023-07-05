package Models;

import java.util.Date;

public class Order {
  private int ID;
  private int clientID;
  private Date date;
  private int sum;

  public Order() {
  }

  public Order(int ID, int clientID, Date date, int sum) {
    this.ID = ID;
    this.clientID = clientID;
    this.date = date;
    this.sum = sum;
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
}
