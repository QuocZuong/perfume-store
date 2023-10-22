package DAOs;

import Exceptions.EmailDuplicationException;
import Interfaces.DAOs.ICustomerDAO;
import Lib.Generator;
import Models.Customer;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO extends UserDAO implements ICustomerDAO {

    private Connection conn;

    public CustomerDAO() {
        conn = DB.DBContext.getConnection();
    }

    @Override
    public int addCustomer(Customer customer) {
        /*
         * BEGIN TRANSACTION
         * GO
         * 
         * CREATE PROCEDURE Insert_Customer
         * 
         * @User_Name nvarchar(50),
         * 
         * @User_Username varchar(50),
         * 
         * @User_Password varchar(32),
         * 
         * @User_Email varchar(100),
         * 
         * @Customer_Credit_Point int
         * AS
         * BEGIN
         * -- Insert into table User first
         * INSERT INTO [User](
         * [User_Name],
         * User_Username,
         * User_Password,
         * User_Email,
         * User_Type
         * )
         * VALUES(
         * 
         * @User_Name,
         * 
         * @User_Username,
         * 
         * @User_Password,
         * 
         * @User_Email,
         * 'Customer'
         * )
         * 
         * -- Get the userID which has just been generated
         * DECLARE @UserID int = (
         * Select TOP 1 [User].[User_ID] from [User]
         * ORDER BY [User].[User_ID] DESC
         * );
         * 
         * 
         * 
         * -- Insert into table Customer
         * INSERT INTO Customer([User_ID], Customer_Credit_Point)
         * VALUES(@UserID,@Customer_Credit_Point)
         * 
         * END
         * GO
         * -- Example: insert role
         * 
         * EXEC Insert_Customer
         * '<name>',
         * '<username>',
         * '<password>',
         * '<email>',
         * 200
         * GO
         * SELECT * FROM [User]
         * SELECT * FROM [Customer]
         * GO
         * ROLLBACK
         */
        String sql = "EXEC Insert_Customer ?, ?, ?, ?, ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, customer.getName());
            ps.setNString(2, customer.getUsername());
            ps.setString(3, customer.getPassword());
            ps.setString(4, customer.getEmail());
            ps.setInt(5, customer.getCustomerCreditPoint());

            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public Customer getCustomer(int customerId) {

        ResultSet rs;
        String sql = "SELECT * FROM Customer c \n"
                + "JOIN [User] u\n"
                + "ON c.User_ID = u.User_ID\n"
                + "WHERE c.Customer_ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Customer customer = customerFactory(rs);
                return customer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Customer getCustomerByUserId(int userId) {
        ResultSet rs;

        String sql = "SELECT * FROM Customer cus\n"
                + "JOIN [User] ON cus.[User_ID] = [User].[User_ID] \n"
                + "WHERE [User].[User_ID] = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Customer customer = customerFactory(rs);
                return customer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Customer getCustomer(String username) {
        ResultSet rs;
        String sql = "SELECT * FROM Customer cus\n"
                + "JOIN [User] ON cus.[User_ID] = [User].[User_ID] \n"
                + "WHERE [User].[User_Username] = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                Customer customer = customerFactory(rs);
                return customer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isCustomer(String username) {
        User user = getUser(username);
        return user != null && user.getType().equals("Customer");
    }

    @Override
    public boolean register(String email) throws EmailDuplicationException {
        if (super.getUserByEmail(email) != null) {
            throw new EmailDuplicationException();
        }
        String newUsername = Generator.generateUsername(email);
        String newPassword = Generator.generatePassword(8);
        Customer customer = new Customer();
        customer.setUsername(newUsername);
        customer.setPassword(newPassword);
        customer.setEmail(email);
        customer.setCustomerCreditPoint(0);

        return addCustomer(customer) > 0;
    }

    /**
     * Create a new {@link Customer} object from a {@link ResultSet}.
     *
     * @param queryResult The {@code ResultSet} containing the customer's
     *                    information.
     * @return An {@code Customer} object containing the customer's information.
     * @throws SQLException If an error occurs while retrieving the data.
     */
    public Customer customerFactory(ResultSet queryResult) throws SQLException {
        DeliveryAddressDAO daDAO = new DeliveryAddressDAO();
        Customer customer = new Customer();

        customer.setId(queryResult.getInt(USER_ID));
        customer.setCustomerId(queryResult.getInt(CUSTOMER_ID));
        customer.setName(queryResult.getNString(USER_NAME));
        customer.setUsername(queryResult.getString(USER_USERNAME));
        customer.setPassword(queryResult.getString(USER_PASSWORD));
        customer.setEmail(queryResult.getString(USER_EMAIL));
        customer.setActive(queryResult.getBoolean(USER_ACTIVE));
        customer.setType(queryResult.getString(USER_TYPE));

        customer.setCustomerCreditPoint(queryResult.getInt(CUSTOMER_CREDIT_POINT));
        customer.setDeliveryAddress(daDAO.getAll(customer.getCustomerId()));

        return customer;
    }

}
