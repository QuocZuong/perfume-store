package Interfaces.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import Models.Order;
import Models.OrderDetail;

public interface IOrderDetailDAO {

  String TABLE_NAME = "OrderDetail";
  String ORDER_ID = "Order_ID";
  String PRODUCT_ID = "Product_ID";
  String QUANTITY = "Quantity";
  String PRICE = "Price";
  String TOTAL = "Total";

  enum inputType {
    orderId,
    customerId
  }

  public OrderDetail orderDetailFactory(ResultSet rs) throws NullPointerException;

  public PreparedStatement fillOrderDetail(PreparedStatement ps, OrderDetail orderDetail) throws NullPointerException;

  public boolean addOrderDetail(OrderDetail orderDetail) throws NullPointerException;

  public boolean addOrderDetail(List<OrderDetail> list) throws NullPointerException;

  public List<OrderDetail> getOrderDetail(int orderId, inputType type);

  public boolean updateOrderDetail(OrderDetail orderDetail) throws NullPointerException;

  public int deleteOrderDetail(int orderId);
}
