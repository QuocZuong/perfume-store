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

import Exceptions.CitizenIDDuplicationException;
import Exceptions.EmailDuplicationException;
import Exceptions.PhoneNumberDuplicationException;
import Exceptions.UsernameDuplicationException;
import Interfaces.DAOs.IEmployeeDAO;
import Lib.Converter;
import Models.Role;

public class EmployeeDAO extends UserDAO implements IEmployeeDAO {

    private Connection conn;

    /**
     * Constructs a new {@code CartDAO} object.
     */
    public EmployeeDAO() {
        conn = DB.DBContext.getConnection();
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

        super.checkDuplication(employee);

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
        Role role = new Role();
        role.setId(rs.getInt("Role_ID"));
        role.setName(rs.getString("Role_Name"));
        employee.setRole(role);
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
        Role role = new Role();
        role.setId(rs.getInt("Role_ID"));
        role.setName(rs.getString("Role_Name"));
        employee.setRole(role);
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

    @Override
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

    @Override
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

    @Override
    public Employee getEmployeeByUserId(int userId) {
        ResultSet rs;

        String sql = "SELECT * FROM Employee emp\n"
                + "JOIN [User] ON emp.[User_ID] = [User].[User_ID] \n"
                + "JOIN [Employee_Role] empR ON emp.Employee_Role = empR.Role_ID \n"
                + "WHERE [User].[User_ID] = ?";
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
    @Override
    public int addEmployee(Employee employee) throws UsernameDuplicationException, EmailDuplicationException,
            PhoneNumberDuplicationException, CitizenIDDuplicationException {
        int result = -1;
        if (checkDuplicate(employee)) {
            /*
            EXEC Insert_Employee 
	'<name>', 
	'<username>', 
	'<password>', 
	'<email>', 
	'<empCitzenID>', 
	'2003-9-23', -- DoB
	'<phoneNumber>', 
	'<address>', 
	'2023-9-30', -- Joined Date
	null, -- Retire Date
	'Order Manager' -- Role name
            
             */
            String sql = "EXEC Insert_Employee \n"
                    + "	?, \n" //1. name
                    + "	?, \n" //2. username
                    + "	?, \n" //3. password
                    + "	?, \n" //4. email
                    + "	?, \n" //5. empCitzenID
                    + "	?, \n" //6. DoB
                    + "	?, \n" //7. phoneNumber
                    + "	?, \n" //8. address
                    + "	?, \n" //9. Joined Date
                    + "	?, \n" //10. Retire Date
                    + "	?"; // 11. Role name
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setNString(1, employee.getName());
                ps.setString(2, employee.getUsername());
                ps.setString(3, Converter.convertToMD5Hash(employee.getPassword()));
                ps.setString(4, employee.getEmail());
                ps.setString(5, employee.getCitizenId());
                ps.setDate(6, employee.getDayOfBirth());
                ps.setString(7, employee.getPhoneNumber());
                ps.setNString(8, employee.getAddress());
                ps.setDate(9, employee.getJoinDate());
                ps.setDate(10, employee.getRetireDate());
                ps.setInt(11, employee.getRole().getId());
                result = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    /*--------------------- UPDATE SECTION ---------------------  */
    @Override
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
                ps.setInt(5, employee.getRole().getId());
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
