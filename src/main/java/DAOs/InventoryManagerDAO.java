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

    public InventoryManager getInventoryManager(String username) {
        EmployeeDAO empDAO = new EmployeeDAO();
        Employee emp = empDAO.getEmployee(username);
        InventoryManager inventoryManager = new InventoryManager(emp);

        String sql = "SELECT * FROM [Inventory_Manager] WHERE Employee_ID = ?;";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, inventoryManager.getEmployeeId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                inventoryManager.setInventoryManagerId(rs.getInt("Inventory_Manager_ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InventoryManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inventoryManager;
    }

    public InventoryManager getInventoryManager(int inventoryManagerId) {
        EmployeeDAO empDAO = new EmployeeDAO();

        String sql = "SELECT * FROM [Inventory_Manager] WHERE Inventory_Manager_ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, inventoryManagerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int empId = rs.getInt("Employee_ID");
                Employee emp = empDAO.getEmployee(empId);
                InventoryManager ivtrMana = new InventoryManager(emp);
                ivtrMana.setInventoryManagerId(inventoryManagerId);
                return ivtrMana;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InventoryManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
