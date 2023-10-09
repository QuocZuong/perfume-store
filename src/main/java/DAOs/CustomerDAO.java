package DAOs;

import Exceptions.EmailDuplicationException;
import Exceptions.UsernameDuplicationException;
import Interfaces.DAOs.ICustomerDAO;
import Lib.Converter;
import Lib.Generator;
import Models.Customer;
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
BEGIN TRANSACTION
GO

CREATE PROCEDURE Insert_Customer
	@User_Name nvarchar(50),
	@User_Username varchar(50),
	@User_Password varchar(32),
	@User_Email varchar(100),
	@Customer_Credit_Point int
AS
BEGIN
	-- Insert into table User first
	INSERT INTO [User](
		[User_Name], 
		User_Username, 
		User_Password, 
		User_Email, 
		User_Type
	)
	VALUES(
		@User_Name, 
		@User_Username, 
		@User_Password, 
		@User_Email, 
		'Customer'
	)

	-- Get the userID which has just been generated
	DECLARE @UserID int = (
		 Select TOP 1 [User].[User_ID] from [User]
		 ORDER BY [User].[User_ID] DESC
	);



	-- Insert into table Customer
	INSERT INTO Customer([User_ID], Customer_Credit_Point)
	VALUES(@UserID,@Customer_Credit_Point)

END
GO
-- Example: insert role

EXEC Insert_Customer 
	'<name>', 
	'<username>', 
	'<password>', 
	'<email>',
	200
GO
SELECT * FROM [User]
SELECT * FROM [Customer]
GO
ROLLBACK
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

            UserDAO uDAO = new UserDAO();
            Customer customer = new Customer(uDAO.userFactory(rs));
            customer.setCustomerCreditPoint(rs.getInt(CUSTOMER_CREDIT_POINT));
            return customer;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
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

}
