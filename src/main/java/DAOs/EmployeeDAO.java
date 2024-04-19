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
import Lib.Generator;
import Models.Role;

public class EmployeeDAO extends UserDAO implements IEmployeeDAO {

    private Connection conn;
    private final boolean DEBUG = false;

    /**
     * Constructs a new {@code CartDAO} object.
     */
    public EmployeeDAO() {
        conn = DB.DBContext.getConnection();
    }

    /*--------------------- DUPLICATE CHECKING SECTION ---------------------  */
    @Override
    public boolean isExistCitizen(String citizenID) {
        if (citizenID == null || citizenID.isEmpty()) {
            throw new IllegalArgumentException("Citizen ID cannot be null or empty");
        }

        ResultSet rs;
        String sql = "SELECT * FROM Employee WHERE Employee_Citizen_ID = ?";

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
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }

        ResultSet rs;
        String sql = "SELECT * FROM Employee WHERE Employee_Phone_Number = ?";

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

    public boolean isAdmin(String username) {
        Employee employee = getEmployee(username);
        return employee != null && employee.getRole().getName().equals("Admin");
    } 

    public boolean isOrderManager(String username) {
        Employee employee = getEmployee(username);
        return employee != null && employee.getRole().getName().equals("Order Manager");
    }

    public boolean isInventoryManager(String username) {
        Employee employee = getEmployee(username);
        return employee != null && employee.getRole().getName().equals("Inventory Manager");
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
        employee.setDateOfBirth(Converter.getNullOrValue(rs.getLong("Employee_DoB")));
        employee.setPhoneNumber(rs.getString("Employee_Phone_Number"));
        employee.setAddress(rs.getNString("Employee_Address"));
        Role role = new Role();
        role.setId(rs.getInt("Role_ID"));
        role.setName(rs.getString("Role_Name"));
        employee.setRole(role);
        employee.setJoinDate(Converter.getNullOrValue(rs.getLong("Employee_Join_Date")));
        employee.setRetireDate(Converter.getNullOrValue(rs.getLong("Employee_Retire_Date")));

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

        employee.setEmployeeId(rs.getInt("Employee_ID"));
        employee.setCitizenId(rs.getNString("Employee_Citizen_ID"));
        employee.setDateOfBirth(Converter.getNullOrValue(rs.getLong("Employee_DoB")));
        employee.setPhoneNumber(rs.getString("Employee_Phone_Number"));
        employee.setAddress(rs.getNString("Employee_Address"));
        Role role = new Role();
        role.setId(rs.getInt("Role_ID"));
        role.setName(rs.getString("Role_Name"));
        employee.setRole(role);
        employee.setJoinDate(Converter.getNullOrValue(rs.getLong("Employee_Join_Date")));
        employee.setRetireDate(Converter.getNullOrValue(rs.getLong("Employee_Retire_Date")));

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

        String sql = "SELECT * FROM Employee emp\n"
                + "            JOIN [User] ON emp.[User_ID] = [User].[User_ID]\n"
                + "              JOIN [Employee_Role] empR ON emp.Employee_Role = empR.Role_ID\n"
                + "              WHERE emp.Employee_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, EmployeeId);
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

    @Override
    public Employee getEmployeeByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID cannot be less than or equal to 0");
        }

        System.out.println("userId :" + userId);
        ResultSet rs;

        String sql = "SELECT * FROM Employee emp\n"
                + "JOIN [User] ON emp.[User_ID] = [User].[User_ID] \n"
                + "JOIN [Employee_Role] empR ON emp.Employee_Role = empR.Role_ID \n"
                + "WHERE [User].[User_ID] = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            Employee employee;

            if (rs.next()) {
                employee = generateFullyEmployeeByResultSet(rs);
                System.out.println("employee id in EmployeeDAO: " + employee.getEmployeeId());

                return employee;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Employee getEmployee(String username) {
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("Username cannot be less than or equal to 0 or null");
        }

        ResultSet rs;

        String sql = "SELECT * FROM Employee emp\n"
                + "JOIN [User] ON emp.[User_ID] = [User].[User_ID] \n"
                + "JOIN [Employee_Role] empR ON emp.Employee_Role = empR.Role_ID \n"
                + "WHERE [User].[User_Username] = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
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

    public Employee getEmployeeByInventoryManagerId(int inventoryManagerId) {
        if (inventoryManagerId <= 0) {
            throw new IllegalArgumentException("inventoryManagerId cannot be less than or equal to 0");
        }

        ResultSet rs;

        String sql = "SELECT * FROM Inventory_Manager\n"
                + "JOIN Employee ON [Inventory_Manager].Employee_ID = [Employee].Employee_ID\n"
                + "JOIN [User] ON [Employee].User_ID = [User].User_ID\n"
                + "JOIN [Employee_Role] ON [Employee].Employee_Role = [Employee_Role].Role_ID\n"
                + "WHERE [Inventory_Manager].[Inventory_Manager_ID] = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, inventoryManagerId);
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

    public Role getRole(int roleId) {
        if (roleId < 1 && roleId > 3) {
            throw new IllegalArgumentException("Invalid role id");
        }

        ResultSet rs;

        String sql = "SELECT * FROM [Employee_Role] WHERE Role_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, roleId);
            rs = ps.executeQuery();
            Role role = new Role();
            if (rs.next()) {
                role.setId(rs.getInt("Role_ID"));
                role.setName(rs.getString("Role_Name"));
            }
            return role;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*--------------------- ADD SECTION ---------------------  */
    @Override
    public int addEmployee(Employee employee) {
        int result = -1;
        /*
         * EXEC Insert_Employee
         * '<name>',
         * '<username>',
         * '<password>',
         * '<email>',
         * '<empCitzenID>',
         * '2003-9-23', -- DoB
         * '<phoneNumber>',
         * '<address>',
         * '2023-9-30', -- Joined Date
         * null, -- Retire Date
         * 'Order Manager' -- Role name
         * 
         */
        String sql = "EXEC Insert_Employee \n"
                + "	?, \n" // 1. name
                + "	?, \n" // 2. username
                + "	?, \n" // 3. password
                + "	?, \n" // 4. email
                + "	?, \n" // 5. empCitzenID
                + "	?, \n" // 6. DoB
                + "	?, \n" // 7. phoneNumber
                + "	?, \n" // 8. address
                + "	?, \n" // 9. Joined Date
                + "	?, \n" // 10. Retire Date
                + "	?"; // 11. Role name
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            if (DEBUG) System.out.printf("Emp password: %s\n", employee.getPassword()); 

            ps.setNString(1, employee.getName());
            ps.setString(2, employee.getUsername());
            ps.setString(3, Converter.convertToMD5Hash(employee.getPassword()));
            ps.setString(4, employee.getEmail());
            ps.setString(5, employee.getCitizenId());
            ps.setLong(6, employee.getDateOfBirth());
            ps.setString(7, employee.getPhoneNumber());
            ps.setNString(8, employee.getAddress());
            ps.setLong(9, employee.getJoinDate());
            if (employee.getRetireDate() == null) {
                ps.setNull(10, java.sql.Types.BIGINT);
            } else {
                ps.setLong(10, employee.getRetireDate());
            }
            ps.setNString(11, employee.getRole().getName());

            result = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int addRole(Role role) {
        int result = -1;

        String sql = "INSERT INTO Employee_Role (Role_Name) VALUES (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, role.getName());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /*--------------------- UPDATE SECTION ---------------------  */
    @Override
    public int updateEmployee(Employee employee) {
        int result = -1;

        String sql = "UPDATE Employee SET Employee_Citizen_ID = ?, Employee_DoB = ?, Employee_Phone_Number = ?, Employee_Address = ?, Employee_Join_Date = ?, Employee_Retire_Date = ? WHERE Employee_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getCitizenId());
            ps.setLong(2, employee.getDateOfBirth());
            ps.setString(3, employee.getPhoneNumber());
            ps.setString(4, employee.getAddress());
            ps.setLong(5, employee.getJoinDate());
            if (employee.getRetireDate() == null) {
                ps.setNull(6, java.sql.Types.BIGINT);
            } else {
                ps.setLong(6, employee.getRetireDate());
            }
            ps.setInt(7, employee.getEmployeeId());

            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public int updateEmployeeRetireDate(int employeeId, long retireDate) {
        int result = -1;

        String sql = "UPDATE Employee SET Employee_Retire_Date = ? WHERE Employee_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            System.out.println("employeeId: " + employeeId);
            System.out.println("retireDate: " + retireDate);
            ps.setLong(1, retireDate);
            ps.setInt(2, employeeId);
            result = ps.executeUpdate();
            System.out.println("result of updateEmployeeRetireDate: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /*--------------------- DELETE SECTION ---------------------  */
    public boolean disableEmployee(Employee employee) {
        try {
            employee.setRetireDate(Generator.getCurrentTimeFromEpochMilli());
            updateEmployeeRetireDate(employee.getEmployeeId(), employee.getRetireDate());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*--------------------- RESTORE SECTION ---------------------  */
    public boolean restoreEmployee(Employee employee) {
        try {
            employee.setRetireDate(Long.valueOf(0));
            updateEmployeeRetireDate(employee.getEmployeeId(), employee.getRetireDate());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
