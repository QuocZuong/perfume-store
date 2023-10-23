package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            if (rs.next()) {
                orderManager.setOrderManagerId(rs.getInt("Order_Manager_ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orderManager;
    }

    public OrderManager getOrderManager(int orderManagerId) {
        EmployeeDAO empDAO = new EmployeeDAO();

        String sql = "SELECT * FROM [Order_Manager] Where Order_Manager_ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderManagerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int emId = rs.getInt("Employee_ID");
                Employee emp = empDAO.getEmployee(emId);
                OrderManager orderManager = new OrderManager(emp);
                orderManager.setOrderManagerId(orderManagerId);

                return orderManager;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
