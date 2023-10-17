package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Models.Admin;
import Models.Employee;
import Models.OrderManager;

public class OrderManagerDAO {

  private Connection conn;

  public OrderManagerDAO() {
    conn = DB.DBContext.getConnection();
  }

  public OrderManager getOrderManager(String username) {
    EmployeeDAO empDAO = new EmployeeDAO();
    Employee emp = empDAO.getEmployee(username);
    OrderManager orderManager = new OrderManager(emp);
    
    String sql = "SELECT * FROM [Order_Manager] WHERE Employee_ID = ?;";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, orderManager.getEmployeeId());
      ResultSet rs = ps.executeQuery();

      orderManager.setId(rs.getInt("Order_Manager_ID"));
    } catch (SQLException ex) {
      Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return orderManager;
  }
}
