/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.Admin;
import Models.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class AdminDAO extends EmployeeDAO {

    private Connection conn;

    public AdminDAO() {
        conn = DB.DBContext.getConnection();
    }

    public Admin getAdmin(String username) {
        EmployeeDAO empDAO = new EmployeeDAO();
        Employee emp = empDAO.getEmployee(username);
        Admin admin = new Admin(emp);
        String sql = "SELECT * FROM [Admin] WHERE Employee_ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, admin.getEmployeeId());
            ResultSet rs = ps.executeQuery();

            admin.setId(rs.getInt("Admin_ID"));
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return admin;
    }
}
