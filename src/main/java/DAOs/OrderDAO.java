package DAOs;

import Models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {
    private Connection conn;

    public OrderDAO() {
        conn = DB.DataManager.getConnection();
    }

    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM [Order]";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setID(rs.getInt("ID"));
                order.setClientID(rs.getInt("ClientID"));
                order.setDate(rs.getDate("Date"));
                order.setSum(rs.getInt("Sum"));
                orders.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    public int addOrder(Order order) {
        String sql = "INSERT INTO [Order] (ClientID, Date, Sum) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, order.getClientID());
            ps.setDate(2, new java.sql.Date(order.getDate().getTime()));
            ps.setInt(3, order.getSum());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int deleteOrder(int orderID) {
        String sql = "DELETE FROM [Order] WHERE ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderID);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
