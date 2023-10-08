package DAOs;

import Interfaces.DAOs.ICustomerDAO;
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
        return super.addUser(customer);
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
    public boolean restoreCustomer(Customer customer) {
        return super.restoreUser(customer);
    }

    @Override
    public boolean disableCustomer(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
