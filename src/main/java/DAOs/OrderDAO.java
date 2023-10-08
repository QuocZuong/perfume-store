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

import Interfaces.DAOs.IOrderDAO;

public class OrderDAO implements IOrderDAO {
    private Connection conn;

    public OrderDAO() {
        conn = DB.DataManager.getConnection();
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
    public PreparedStatement fillPreparedStatement(PreparedStatement ps, Order order)
            throws NullPointerException, SQLException {
        if (ps == null || order == null) {
            throw new NullPointerException("PreparedStatement or Order is null");
        }

        ps.setInt(1, order.getId());
        ps.setInt(2, order.getCustomerId());
        ps.setInt(3, order.getVoucherId());
        ps.setString(4, order.getReceiverName());
        ps.setString(5, order.getDeliveryAddress());
        ps.setString(6, order.getPhoneNumber());
        ps.setString(7, order.getNote());
        ps.setInt(8, order.getTotal());
        ps.setInt(9, order.getDeductedPrice());
        ps.setString(10, order.getStatus());
        ps.setDate(11, order.getCreatedAt());
        ps.setDate(12, order.getCheckoutAt());
        ps.setDate(13, order.getUpdateAt());
        ps.setInt(14, order.getUpdateByOrderManager());

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
                + "VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps = fillPreparedStatement(ps, order);
            result = ps.executeUpdate() > 0;

            if (!result) {
                return false;
            }

            OrderDetailDao odDAO = new OrderDetailDao();
            result = odDAO.addOrderDetail(odList);

            if (!result) {
                // TODO: Delete order if add order detail failed
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /* --------------------------- READ SECTION --------------------------- */

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

    public int getMaxOrderID() {
        String sql = "Select MAX(ID) as maxOrderID from [Order]";
        int maxOrderID = -1;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                maxOrderID = rs.getInt("maxOrderID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxOrderID;
    }
    /* --------------------------- UPDATE SECTION --------------------------- */

    /* --------------------------- DELETE SECTION --------------------------- */
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

    @Override
    public Order getOrder(int Id) {
        throw new UnsupportedOperationException("Unimplemented method 'getOrder'");
    }

    @Override
    public List<Order> getOrderByCustomerId(int customerId) {
        throw new UnsupportedOperationException("Unimplemented method 'getOrderByCustomerId'");
    }

    @Override
    public boolean updateOrder(Order order) throws NullPointerException {
        throw new UnsupportedOperationException("Unimplemented method 'updateOrder'");
    }

    @Override
    public List<Order> getAll() {
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }
}
