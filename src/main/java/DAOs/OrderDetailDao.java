package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Interfaces.DAOs.IOrderDetailDAO;
import Models.OrderDetail;

public class OrderDetailDao implements IOrderDetailDAO {

  Connection conn;

  public OrderDetailDao() {
    conn = DB.DataManager.getConnection();
  }

  @Override
  public boolean addOrderDetail(OrderDetail orderDetail) throws NullPointerException {
    if (orderDetail == null) {
      throw new NullPointerException("OrderDetail is null");
    }

    String sql = "INSERT INTO [OrderDetail]\n"
        + " VALUES(?, ?, ?, ?, ?)";
    PreparedStatement ps = null;
    boolean result = false;

    try {
      ps = conn.prepareStatement(sql);

      ps.setInt(1, orderDetail.getOrderId());
      ps.setInt(2, orderDetail.getProductId());
      ps.setInt(3, orderDetail.getQuantity());
      ps.setInt(4, orderDetail.getPrice());
      ps.setInt(5, orderDetail.getTotal());

      result = ps.executeUpdate() > 0;
    } catch (SQLException ex) {
      Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return result;
  }

  @Override
  public boolean addOrderDetail(List<OrderDetail> list) throws NullPointerException {
    if (list == null) {
      throw new NullPointerException("List<OrderDetail> is null");
    }
    if (list.isEmpty()) {
      throw new NullPointerException("List<OrderDetail> is empty");
    }
    for (OrderDetail oDetail : list) {
      if (oDetail == null) {
        throw new NullPointerException("One of the OrderDetail in the list provided is null");
      }
    }

    boolean result = false;
    PreparedStatement ps = null;
    String sql = "INSERT INTO [OrderDetail]\n"
        + " VALUES(?, ?, ?, ?, ?)";

    try {
      ps = conn.prepareStatement(sql);

      for (int i = 0; i < list.size(); i++) {
        OrderDetail orderDetail = list.get(i);

        ps.setInt(1, orderDetail.getOrderId());
        ps.setInt(2, orderDetail.getProductId());
        ps.setInt(3, orderDetail.getQuantity());
        ps.setInt(4, orderDetail.getPrice());
        ps.setInt(5, orderDetail.getTotal());

        ps.addBatch();
        ps.clearParameters();
      }

      result = ps.executeUpdate() > 0;
    } catch (SQLException ex) {
      Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return result;
  }

  @Override
  public List<OrderDetail> getOrderDetail(int orderId) {
    
    List<OrderDetail> list = new ArrayList<>();
    String sql = "SELECT * FROM [OrderDetail] WHERE Order_ID = ?";

    PreparedStatement ps = null;

    try {
      ps = conn.prepareStatement(sql);
      ps.setInt(1, orderId);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        OrderDetail orderDetail = orderDetailFactory(rs);
        list.add(orderDetail);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return list;
  }

  /* --------------------------- UPDATE SECTION --------------------------- */

  @Override
  public boolean updateOrderDetail(OrderDetail orderDetail) throws NullPointerException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateOrderDetail'");
  }

  /* --------------------------- DELETE SECTION --------------------------- */

  public boolean deleteOrderDetail(int orderID) {
    boolean result = false;
    String sql = "DELETE FROM [OrderDetail] WHERE Order_ID = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, orderID);
      result = ps.executeUpdate() > 0;
    } catch (SQLException ex) {
      Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  @Override
  public OrderDetail orderDetailFactory(ResultSet rs) throws NullPointerException {
    if (rs == null) {
      throw new NullPointerException("ResultSet is null");
    }

    try {
      if (rs.next()) {
        OrderDetail orderDetail = orderDetailFactory(rs);
        return orderDetail;
      }
    } catch (Exception ex) {
      Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  @Override
  public PreparedStatement fillOrderDetail(PreparedStatement ps, OrderDetail orderDetail) throws NullPointerException {
    if (ps == null) {
      throw new NullPointerException("PreparedStatement is null");
    }
    if (orderDetail == null) {
      throw new NullPointerException("OrderDetail is null");
    }

    try {
      ps.setInt(1, orderDetail.getOrderId());
      ps.setInt(2, orderDetail.getProductId());
      ps.setInt(3, orderDetail.getQuantity());
      ps.setInt(4, orderDetail.getPrice());
      ps.setInt(5, orderDetail.getTotal());

    } catch (Exception ex) {
      Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return ps;
  }

}
