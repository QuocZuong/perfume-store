package DAOs;

import Models.Order;
import Models.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Interfaces.DAOs.IOrderDAO;
import Lib.DatabaseUtils;

public class OrderDAO implements IOrderDAO {

    private Connection conn;

    public OrderDAO() {
        conn = DB.DBContext.getConnection();
    }

    @Override
    public Order orderFactory(ResultSet rs) {
        Order order = new Order();

        try {
            order.setId(rs.getInt(ORDER_Id));
            order.setCustomerId(rs.getInt(CUSTOMER_Id));
            order.setVoucherId(rs.getInt(VOUCHER_Id));
            order.setReceiverName(rs.getString(ORDER_RECEIVER_NAME));
            order.setDeliveryAddress(rs.getString(ORDER_DELIVERY_ADDRESS));
            order.setPhoneNumber(rs.getString(ORDER_PHONE_NUMBER));
            order.setNote(rs.getString(ORDER_NOTE));
            order.setTotal(rs.getInt(ORDER_TOTAL));
            order.setDeductedPrice(rs.getInt(ORDER_DEDUCTED_PRICE));
            order.setStatus(rs.getString(ORDER_STATUS));
            order.setCreatedAt(rs.getDate(ORDER_CREATED_AT));
            order.setCheckoutAt(rs.getDate(ORDER_CHECKOUT_AT));
            order.setUpdateAt(rs.getDate(ORDER_UPDATE_AT));
            order.setUpdateByOrderManager(rs.getInt(ORDER_UPDATE_BY_ORDER_MANAGER));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }

    @Override
    public PreparedStatement fillPreparedStatement(PreparedStatement ps, Order order, operation op)
            throws NullPointerException, SQLException {
        if (ps == null || order == null) {
            throw new NullPointerException("PreparedStatement or Order is null");
        }

        if (op == operation.READ) {
            ps.setInt(1, order.getId());
            ps.setInt(2, order.getCustomerId());
            ps.setInt(3, order.getVoucherId());
            ps.setNString(4, order.getReceiverName());
            ps.setNString(5, order.getDeliveryAddress());
            ps.setString(6, order.getPhoneNumber());
            ps.setNString(7, order.getNote());
            ps.setInt(8, order.getTotal());
            ps.setInt(9, order.getDeductedPrice());
            ps.setString(10, order.getStatus());
            ps.setDate(11, order.getCreatedAt());
            ps.setDate(12, order.getCheckoutAt());
            ps.setDate(13, order.getUpdateAt());
            ps.setInt(14, order.getUpdateByOrderManager());
        } else if (op == operation.UPDATE) {
            ps.setInt(1, order.getCustomerId());
            ps.setInt(2, order.getVoucherId());
            ps.setNString(3, order.getReceiverName());
            ps.setNString(4, order.getDeliveryAddress());
            ps.setString(5, order.getPhoneNumber());
            ps.setNString(6, order.getNote());
            ps.setInt(7, order.getTotal());
            ps.setInt(8, order.getDeductedPrice());
            ps.setString(9, order.getStatus());
            ps.setDate(10, order.getCreatedAt());
            ps.setDate(11, order.getCheckoutAt());
            ps.setDate(12, order.getUpdateAt());
            ps.setInt(13, order.getUpdateByOrderManager());
            ps.setInt(14, order.getId());
        }

        return ps;
    }

    /* ------------------------- CREATE SECTION ---------------------------- */
    public boolean addOrder(Order order, List<OrderDetail> odList) throws NullPointerException {
        if (order == null) {
            throw new NullPointerException("Order is null");
        }
        if (odList == null) {
            throw new NullPointerException("OrderDetail list is null");
        }
        if (odList.isEmpty()) {
            throw new NullPointerException("OrderDetail list is empty");
        }

        boolean result = false;
        String sql = "INSERT INTO [Order] [(Order_ID), (Customer_ID), (Voucher_ID), (Order_Receiver_Name), (Order_Delivery_Address), (Order_Phone_Number), (Order_Note), (Order_Total), (Order_Deducted_Price), (Order_Status), (Order_Created_At), (Order_Checkout_At), (Order_Update_At), (Order_Update_By_Order_Manager)]"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps = fillPreparedStatement(ps, order, operation.CREATE);
            result = ps.executeUpdate() > 0;

            if (!result) {
                return false;
            }

            OrderDetailDao odDAO = new OrderDetailDao();
            result = odDAO.addOrderDetail(odList);

            if (!result) {
                deleteOrder(DatabaseUtils.getLastIndentityOf("Order"));
                odDAO.deleteOrderDetail(odList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /* --------------------------- READ SECTION --------------------------- */

    @Override
    public List<Order> getAll() {
        String sql = "SELECT * FROM [Order]";

        ResultSet rs;
        List<Order> result = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                result.add(orderFactory(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Order> getOrderByCustomerId(int customerId) {

        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer ID must be greater than 0");
        }

        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM [Order] where Customer_ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orderFactory(rs);
                Order order = orderFactory(rs);
                orders.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orders;
    }

    public Order getOrderByOrderId(int orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be greater than 0");
        }
        String sql = "SELECT * FROM [Order] where Order_ID = ?";
        Order order = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order = orderFactory(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return order;
    }

    // public List<String[]> getOrderDetailByOrderID(int OrderID) {
    // List<String[]> orders = new ArrayList<>();
    // String sql = "SELECT Product.[Name] as ProductName, Product.ImgURL as
    // ProductImgURL, OrderDetail.Quantity as Quantity, OrderDetail.Price as Price,
    // [User].[Address] as ClientAddress, OrderDetail.[Sum] as Total \n"
    // + "FROM Product, [Order], OrderDetail, [User] \n" +
    // "WHERE \n" +
    // "\t[Order].ID = ? AND \r\n" +
    // "\t[Order].ClientID = [User].ID AND \n" +
    // "\tProduct.ID = OrderDetail.ProductID AND \n" +
    // "\t[Order].ID = OrderDetail.OrderID";
    // try {
    // PreparedStatement ps = conn.prepareStatement(sql);
    // ps.setInt(1, OrderID);
    // ResultSet rs = ps.executeQuery();
    // while (rs.next()) {
    // String cell[] = new String[6];
    // cell[0] = rs.getNString("ProductName");
    // cell[1] = rs.getNString("ProductImgURL");
    // cell[2] = rs.getInt("Quantity") + ""; // Int
    // cell[3] = rs.getInt("Price") + ""; // Int
    // cell[4] = rs.getNString("ClientAddress");
    // cell[5] = rs.getInt("Total") + ""; // Int
    // orders.add(cell);
    // }
    // } catch (SQLException ex) {
    // Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
    // }
    // return orders;
    // }

    /* --------------------------- UPDATE SECTION --------------------------- */
    @Override
    public boolean updateOrder(Order order, List<OrderDetail> odList) throws NullPointerException {
        if (order == null) {
            throw new NullPointerException("Order is null");
        }
        if (odList == null) {
            throw new NullPointerException("OrderDetail list is null");
        }
        if (odList.isEmpty()) {
            throw new NullPointerException("OrderDetail list is empty");
        }

        boolean result = false;
        String sql = "UPDATE [Order]\n"
                + "SET [Customer_ID] = ?,\n"
                + "[Voucher_ID] = ?,\n"
                + "[Order_Receiver_Name] = ?,\n"
                + "[Order_Delivery_Address] = ?,\n"
                + "[Order_Phone_Number] = ?,\n"
                + "[Order_Note] = ?,\n"
                + "[Order_Total] = ?,\n"
                + "[Order_Deducted_Price] = ?,\n"
                + "[Order_Status] = ?,\n"
                + "[Order_Created_At] = ?,\n"
                + "[Order_Checkout_At] = ?,\n"
                + "[Order_Update_At] = ?,\n"
                + "[Order_Update_By_Order_Manager] = ?\n"
                + "WHERE [Order_ID] = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps = fillPreparedStatement(ps, order, operation.UPDATE);
            result = ps.executeUpdate() > 0;

            if (!result) {
                return false;
            }

            OrderDetailDao odDAO = new OrderDetailDao();
            result = odDAO.updateOrderDetail(odList);

            if (!result) {
                deleteOrder(DatabaseUtils.getLastIndentityOf("Order"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /* --------------------------- DELETE SECTION --------------------------- */
    public boolean deleteOrder(int orderID) {
        boolean result = false;
        String sql = "DELETE FROM [Order] WHERE Order_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderID);
            result = ps.executeUpdate() > 0;

            if (result) {
                OrderDetailDao odDAO = new OrderDetailDao();
                result = odDAO.deleteOrderDetail(orderID);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
