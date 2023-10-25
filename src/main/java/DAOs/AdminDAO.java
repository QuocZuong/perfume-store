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

    public Admin getAdmin(int adminId) {
        EmployeeDAO empDAO = new EmployeeDAO();

        String sql = "SELECT * FROM [Admin] WHERE Admin_ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, adminId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int empId = rs.getInt("Employee_ID");
                Employee emp = empDAO.getEmployee(empId);
                Admin admin = new Admin(emp);
                admin.setAdminId(adminId);
                return admin;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
            if (rs.next()) {
                admin.setAdminId(rs.getInt("Admin_ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return admin;
    }
}
