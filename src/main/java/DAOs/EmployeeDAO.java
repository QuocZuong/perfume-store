package DAOs;

import Models.Employee;
import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.DuplicateKeyException;

import Exceptions.CitizenIDDuplicationException;
import Exceptions.EmailDuplicationException;
import Exceptions.PhoneNumberDuplicationException;
import Exceptions.UsernameDuplicationException;

public class EmployeeDAO extends UserDAO implements Interfaces.DAOs.IEmployeeDAO {

    private Connection conn;

    /**
     * Constructs a new {@code CartDAO} object.
     */
    public EmployeeDAO() {
        conn = DB.DataManager.getConnection();
    }

    /*--------------------- DUPLICATE CHECKING SECTION ---------------------  */
    public boolean isExistCitizen(String citizenID) {
        ResultSet rs;
        String sql = "SELECT * FROM [User] WHERE Employee_Citizen_ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, citizenID);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isExistPhoneNumber(String phoneNumber) {
        ResultSet rs;
        String sql = "SELECT * FROM [User] WHERE Employee_Phone_Number = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean checkDuplicate(Employee employee) throws UsernameDuplicationException, EmailDuplicationException,
            PhoneNumberDuplicationException, CitizenIDDuplicationException {

        if (isExistUsername(employee.getUsername())) {
            throw new UsernameDuplicationException();
        }

        if (isExistEmail(employee.getEmail())) {
            throw new EmailDuplicationException();
        }

        if (isExistPhoneNumber(employee.getPhoneNumber())) {
            throw new PhoneNumberDuplicationException();
        }

        if (isExistCitizen(employee.getCitizenId())) {
            throw new CitizenIDDuplicationException();
        }

        return true;
    }

    /*--------------------- UTILITY METHODS ---------------------  */
    /**
     * Use to generate an employee with employee information base on ResultSet
     * input.
     *
     * @param rs get a ResultSet.
     * @return return the employee that contain user information and employee
     * information.
     * @throws SQLException return error when execute SQL occur.
     */
    private Employee generateEmployeeByResultSet(ResultSet rs) throws SQLException {

        Employee employee = new Employee();

        employee.setId(rs.getInt("Employee_ID"));
        employee.setCitizenId(rs.getNString("Employee_Citizen_ID"));
        employee.setDayOfBirth(rs.getDate("Employee_DoB"));
        employee.setPhoneNumber(rs.getString("Employee_Phone_Number"));
        employee.setAddress(rs.getNString("Employee_Address"));
        employee.setRole(rs.getInt("Employee_Role"));
        employee.setJoinDate(rs.getDate("Employee_Join_Date"));
        employee.setRetireDate(rs.getDate("Employee_Retire_Date"));

        return employee;
    }

    /**
     * The function generates a fully populated Employee object by extracting
     * data from a ResultSet.
     *
     * @param rs The parameter "rs" is a ResultSet object, which is used to
     * retrieve data from a database query result. In this case, it is used to
     * retrieve data for an Employee object.
     * @return The method is returning an instance of the Employee class.
     */
    private Employee generateFullyEmployeeByResultSet(ResultSet rs) throws SQLException {

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
        employee.setAddress(rs.getNString("Employee_Address"));
        employee.setRole(rs.getInt("Employee_Role"));
        employee.setJoinDate(rs.getDate("Employee_Join_Date"));
        employee.setRetireDate(rs.getDate("Employee_Retire_Date"));

        return employee;
    }

    /*--------------------- READ SECTION ---------------------  */
    @Override
    public List<User> getAll() {
        ResultSet rs;
        List<User> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM Employee JOIN [User] ON Employee.User_ID = [User].[User_ID]";
        // ((Employee)employeeList.get(0)).getDayOfBirth();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = generateFullyEmployeeByResultSet(rs);
                employeeList.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return employeeList;
    }

    public List<Employee> getEmployee() {
        ResultSet rs;
        List<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM Employee";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = generateEmployeeByResultSet(rs);
                employeeList.add(employee);
            }

            return employeeList;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return employeeList;
    }

    public Employee getEmployee(int EmployeeId) {
        ResultSet rs;

        String sql = "SELECT * FROM Employee WHERE User_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, EmployeeId);
            rs = ps.executeQuery();
            Employee employee = null;
            if (rs.next()) {
                employee = generateEmployeeByResultSet(rs);
            }
            return employee;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Employee getEmployeeByUserId(int userId) {
        ResultSet rs;

        String sql = "SELECT * FROM Employee JOIN [User] ON Employee.User_ID = [User].[User_ID] WHERE [User].User_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            Employee employee = null;
            if (rs.next()) {
                employee = generateFullyEmployeeByResultSet(rs);
            }
            return employee;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*--------------------- ADD SECTION ---------------------  */
    public int addEmployee(Employee employee) throws UsernameDuplicationException, EmailDuplicationException,
            PhoneNumberDuplicationException, CitizenIDDuplicationException {
        int result = -1;
        if (checkDuplicate(employee)) {

            String sql = "INSERT INTO Employee (User_ID, Employee_Citizen_ID, Employee_DoB, Employee_Phone_Number, Employee_Address, Employee_Role, Employee_Join_Date, Employee_Retire_Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, employee.getUserId());
                ps.setString(2, employee.getCitizenId());
                ps.setDate(3, employee.getDayOfBirth());
                ps.setString(4, employee.getPhoneNumber());
                ps.setString(5, employee.getAddress());
                ps.setInt(6, employee.getRole());
                ps.setDate(7, employee.getJoinDate());
                ps.setDate(8, employee.getRetireDate());

                result = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    /*--------------------- UPDATE SECTION ---------------------  */
    public int updateEmployee(Employee employee) throws UsernameDuplicationException, EmailDuplicationException,
            PhoneNumberDuplicationException, CitizenIDDuplicationException {
        int result = -1;

        if (checkDuplicate(employee)) {
            String sql = "UPDATE Employee SET Employee_Citizen_ID = ?, Employee_DoB = ?, Employee_Phone_Number = ?, Employee_Address = ?, Employee_Role = ?, Employee_Join_Date = ?, Employee_Retire_Date = ? WHERE Employee_ID = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, employee.getCitizenId());
                ps.setDate(2, employee.getDayOfBirth());
                ps.setString(3, employee.getPhoneNumber());
                ps.setString(4, employee.getAddress());
                ps.setInt(5, employee.getRole());
                ps.setDate(6, employee.getJoinDate());
                ps.setDate(7, employee.getRetireDate());
                ps.setInt(8, employee.getEmployeeId());

                result = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return result;
    }

}
