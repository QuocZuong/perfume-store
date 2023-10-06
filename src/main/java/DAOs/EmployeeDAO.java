package DAOs;

import Models.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDAO extends UserDAO{
    private Connection conn;

    /**
     * Constructs a new {@code CartDAO} object.
     */
    public EmployeeDAO() {
        conn = DB.DataManager.getConnection();
    }
    
    
    
    @Override
    public ArrayList getAll() {
        ResultSet rs;
        ArrayList<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM Employee JOIN [User] ON Employee.User_ID = [User].[User_ID]";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Employee employee = new Employee();
                
                employee.setId(rs.getInt("User_ID"));
                employee.setName(rs.getNString("User_Name"));
                employee.setUsername(rs.getString("User_Username"));
                employee.setPassword(rs.getString("User_Password"));
                employee.setEmail(rs.getString("User_Email"));
                employee.setActive(rs.getBoolean("User_Active"));
                employee.setType(rs.getNString("User_Type"));
                
                employee.setId(rs.getInt("Employee_ID"));
                employee.setCitizenId(rs.getNString("Employee_Citizen_ID"));
                employee.setDayOfBirth(rs.getDate("Employee_DoB"));
                employee.setPhoneNumber(rs.getString("Employee_Phone_Number"));
                employee.setAddress(rs.getNString("Employe e_Address"));
                employee.setRole(rs.getInt("Employee_Role"));
                employee.setJoinDate(rs.getDate("Employee_Join_Date"));
                employee.setRetireDate(rs.getDate("Employee_Retire_Date"));
                
                employeeList.add(employee);
            }
            
            return employeeList;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
}
