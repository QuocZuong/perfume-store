package DAOs;

import Models.Order;
import Models.OrderDetail;
import java.sql.Connection;
import java.sql.Date;
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

    public List<Order> getOrderByClientId(int id) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM [Order] where ClientID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
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

    public List<String[]> getOrderDetailByOrderID(int OrderID) {
        List<String[]> orders = new ArrayList<>();
        String sql = "SELECT Product.[Name] as ProductName, Product.ImgURL as ProductImgURL, OrderDetail.Quantity as Quantity, OrderDetail.Price as Price, [User].[Address] as ClientAddress, OrderDetail.[Sum] as Total \n"
                + "FROM Product, [Order], OrderDetail, [User] \n" +
                "WHERE \n" +
                "\t[Order].ID = ? AND \r\n" +
                "\t[Order].ClientID = [User].ID AND \n" +
                "\tProduct.ID = OrderDetail.ProductID AND \n" +
                "\t[Order].ID = OrderDetail.OrderID";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, OrderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String cell[] = new String[6];
                cell[0] = rs.getNString("ProductName");
                cell[1] = rs.getNString("ProductImgURL");
                cell[2] = rs.getInt("Quantity") + ""; // Int
                cell[3] = rs.getInt("Price") + ""; // Int
                cell[4] = rs.getNString("ClientAddress");
                cell[5] = rs.getInt("Total") + ""; // Int
                orders.add(cell);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    public Order getOrderByOrderId(int id) {
        String sql = "SELECT * FROM [Order] where ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int OrderID = rs.getInt("ID");
                int ClientID = rs.getInt("ClientID");
                Date Date = rs.getDate("Date");
                String Address = rs.getNString("Address");
                String PhoneNumber = rs.getString("PhoneNumber");
                String Note = rs.getNString("Note");
                int Sum = rs.getInt("Sum");
                return new Order(OrderID, ClientID, Date, Address, PhoneNumber, Note, Sum);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public OrderDetail getOrderByOrderDetailId(int id) {
        String sql = "SELECT * FROM [OrderDetail] where OrderID = ?";
        OrderDetail order = new OrderDetail();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                order.setOrderID(rs.getInt("OrderID"));
                order.setOrderID(rs.getInt("ProductID"));
                order.setOrderID(rs.getInt("Quantity"));
                order.setOrderID(rs.getInt("Price"));
                order.setOrderID(rs.getInt("DetailSum"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
    }

}
