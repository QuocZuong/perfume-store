package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Models.Employee;
import Models.InventoryManager;

public class InventoryManagerDAO {
  private Connection conn;

  public InventoryManagerDAO() {
    conn = DB.DBContext.getConnection();
  }

  public InventoryManager getAdmin(String username) {
    EmployeeDAO empDAO = new EmployeeDAO();
    Employee emp = empDAO.getEmployee(username);
    InventoryManager inventoryManager = new InventoryManager(emp);

    String sql = "SELECT * FROM [Inventory_Manager] WHERE Employee_ID = ?;";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, inventoryManager.getEmployeeId());
      ResultSet rs = ps.executeQuery();

      inventoryManager.setId(rs.getInt("Inventory_Manager_ID"));
    } catch (SQLException ex) {
      Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return inventoryManager;
  }
}
