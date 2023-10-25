package DAOs;

import Exceptions.NotEnoughProductQuantityException;
import Exceptions.OperationEditFailedException;
import Exceptions.VoucherNotFoundException;
import Models.Order;
import Models.OrderDetail;
import Models.Product;
import Models.Stock;

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
import Lib.Generator;
import Models.OrderManager;
import Models.Voucher;

public class OrderDAO implements IOrderDAO {

    private Connection conn;

    public OrderDAO() {
        conn = DB.DBContext.getConnection();
    }

    @Override
    public Order orderFactory(ResultSet rs, operation op) throws SQLException {
        Order order = new Order();

        switch (op) {
            default:
                try {
                order.setId(rs.getInt(ORDER_Id));
                order.setCustomerId(rs.getInt(CUSTOMER_Id));
                order.setVoucherId(rs.getInt(VOUCHER_Id));
                order.setReceiverName(rs.getNString(ORDER_RECEIVER_NAME));
                order.setDeliveryAddress(rs.getNString(ORDER_DELIVERY_ADDRESS));
                order.setPhoneNumber(rs.getString(ORDER_PHONE_NUMBER));
                order.setNote(rs.getNString(ORDER_NOTE));
                order.setTotal(rs.getInt(ORDER_TOTAL));
                order.setDeductedPrice(rs.getInt(ORDER_DEDUCTED_PRICE));
                order.setStatus(rs.getString(ORDER_STATUS));
                order.setCreatedAt(rs.getLong(ORDER_CREATED_AT));
                order.setCheckoutAt(rs.getLong(ORDER_CHECKOUT_AT));
                order.setUpdateAt(rs.getLong(ORDER_UPDATE_AT));
                order.setUpdateByOrderManager(rs.getInt(ORDER_UPDATE_BY_ORDER_MANAGER));
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            ps.setLong(11, order.getCreatedAt());
            ps.setLong(12, order.getCheckoutAt());
            ps.setLong(13, order.getUpdateAt());
            ps.setInt(14, order.getUpdateByOrderManager());
        } else if (op == operation.UPDATE) {
            ps.setInt(1, order.getCustomerId());
            ps.setNString(3, order.getReceiverName());
            ps.setNString(4, order.getDeliveryAddress());
            ps.setString(5, order.getPhoneNumber());
            ps.setInt(7, order.getTotal());
            ps.setString(9, order.getStatus());
            ps.setLong(10, order.getCreatedAt());
            ps.setLong(11, order.getCheckoutAt());
            ps.setLong(12, order.getUpdateAt());
            ps.setInt(13, order.getUpdateByOrderManager());
            ps.setInt(14, order.getId());

            // Nullable
            if (order.getVoucherId() != 0) {
                ps.setInt(2, order.getVoucherId());
                ps.setInt(8, order.getDeductedPrice());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
                ps.setNull(8, java.sql.Types.INTEGER);
            }

            if (order.getNote() != null && !order.getNote().equals("")) {
                ps.setNString(6, order.getNote());
            } else {
                ps.setNull(6, java.sql.Types.NVARCHAR);
            }
        } else if (op == operation.CREATE) {
            // Not Null
            ps.setInt(1, order.getCustomerId());
            ps.setNString(3, order.getReceiverName());
            ps.setNString(4, order.getDeliveryAddress());
            ps.setString(5, order.getPhoneNumber());
            ps.setInt(7, order.getTotal());
            ps.setString(9, order.getStatus());
            ps.setLong(10, order.getCreatedAt());
            // Must null when create
            ps.setNull(11, java.sql.Types.BIGINT);
            ps.setNull(12, java.sql.Types.BIGINT);
            ps.setNull(13, java.sql.Types.INTEGER);
            // Nullable
            if (order.getVoucherId() != 0) {
                ps.setInt(2, order.getVoucherId());
                ps.setInt(8, order.getDeductedPrice());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
                ps.setNull(8, java.sql.Types.INTEGER);
            }

            if (order.getNote() != null && !order.getNote().equals("")) {
                ps.setNString(6, order.getNote());
            } else {
                ps.setNull(6, java.sql.Types.NVARCHAR);
            }
        }

        return ps;
    }

    /* ------------------------- CREATE SECTION ---------------------------- */
    public boolean addOrder(Order order) throws NullPointerException {
        if (order == null) {
            throw new NullPointerException("Order is null");
        }
        if (order.getOrderDetailList() == null) {
            throw new NullPointerException("OrderDetail list is null");
        }
        if (order.getOrderDetailList().isEmpty()) {
            throw new NullPointerException("OrderDetail list is empty");
        }

        boolean result = false;
        String sql = "	INSERT INTO [Order] (\n"
                + "    Customer_ID,\n"
                + "    Voucher_ID,\n"
                + "    Order_Receiver_Name,\n"
                + "    Order_Delivery_Address,\n"
                + "    Order_Phone_Number,\n"
                + "    Order_Note,\n"
                + "    Order_Total,\n"
                + "    Order_Deducted_Price,\n"
                + "    Order_Status,\n"
                + "    Order_Created_At,\n"
                + "    Order_Checkout_At,\n"
                + "    Order_Update_At,\n"
                + "    Order_Update_By_Order_Manager)\n"
                + "    VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps = fillPreparedStatement(ps, order, operation.CREATE);
            result = ps.executeUpdate() > 0;

            if (!result) {
                return false;
            }
            int orderId = DatabaseUtils.getLastIndentityOf("[Order]");

            for (int i = 0; i < order.getOrderDetailList().size(); i++) {
                order.getOrderDetailList().get(i).setOrderId(orderId);
            }
            OrderDetailDao odDAO = new OrderDetailDao();
            result = odDAO.addOrderDetail(order.getOrderDetailList());

            if (!result) {
                deleteOrder(DatabaseUtils.getLastIndentityOf("Order"));
                odDAO.deleteOrderDetail(order.getOrderDetailList());
                deleteOrder(orderId);
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
                result.add(orderFactory(rs, operation.READ));
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
                orders.add(orderFactory(rs, operation.READ));
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
                order = orderFactory(rs, operation.READ);
                OrderDetailDao odDAO = new OrderDetailDao();
                List<OrderDetail> orderDetailList = odDAO.getOrderDetail(orderId);
                order.setOrderDetailList(orderDetailList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return order;
    }

    public List<Product> getOrderForChartByOrderIdByGender() {
        String sql = "SELECT [Product].Product_Name, [Product].Product_Gender FROM [Order]\n"
                + "INNER JOIN [OrderDetail] ON [Order].Order_ID=[OrderDetail].Order_ID\n"
                + "INNER JOIN [Product] ON [OrderDetail].Product_ID = [Product].Product_ID";
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setName(rs.getString("Product_Name"));
                product.setGender(rs.getString("Product_Gender"));
                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    public List<Product> getOrderForChartByOrderIdByPrice() {
        String sql = "SELECT [Product].Product_Name, [Stock].Price, [Stock].Product_ID, [Stock].Quantity FROM [Order]\n"
                + "INNER JOIN [OrderDetail] ON [Order].Order_ID=[OrderDetail].Order_ID\n"
                + "INNER JOIN [Product] ON [OrderDetail].Product_ID = [Product].Product_ID\n"
                + "INNER JOIN [Stock] ON [Product].Product_ID = [Stock].Product_ID";
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                Stock stock = new Stock();
                stock.setPrice(rs.getInt("Price"));
                stock.setProductID(rs.getInt("Product_ID"));
                stock.setQuantity(rs.getInt("Quantity"));
                product.setName(rs.getString("Product_Name"));
                product.setStock(stock);
                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    public List<Order> getNumberOfOrderByDay(int day, int month, int year) {
        String sql = "SELECT * FROM [Order] WHERE DAY(Order_Created_At) = ? AND MONTH(Order_Created_At) = ? AND  YEAR(Order_Created_At) = ?";
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, day);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                orders.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    public List<Order> getNumberOfOrderByMonth(int month, int year) {
        String sql = "SELECT * FROM [Order] WHERE MONTH(Order_Created_At) = ? AND YEAR(Order_Created_At) = ?";
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                orders.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    public List<Order> getNumberOfOrderByYear(int year) {
        String sql = "SELECT * FROM [Order] WHERE YEAR(Order_Created_At) = ?";
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                orders.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    /* --------------------------- UPDATE SECTION --------------------------- */
    @Override
    public boolean updateOrder(Order order) throws NullPointerException {
        if (order == null) {
            throw new NullPointerException("Order is null");
        }
        if (order.getOrderDetailList() == null) {
            throw new NullPointerException("OrderDetail list is null");
        }
        if (order.getOrderDetailList().isEmpty()) {
            throw new NullPointerException("OrderDetail list is empty");
        }

        boolean result = false;
        String sql = "UPDATE [Order]\n"
                + "SET [Customer_ID] = ?,\n" //1
                + "[Voucher_ID] = ?,\n" //2
                + "[Order_Receiver_Name] = ?,\n" //3
                + "[Order_Delivery_Address] = ?,\n" //4
                + "[Order_Phone_Number] = ?,\n" //5
                + "[Order_Note] = ?,\n" //6
                + "[Order_Total] = ?,\n" //7
                + "[Order_Deducted_Price] = ?,\n" //8
                + "[Order_Status] = ?,\n" //9
                + "[Order_Created_At] = ?,\n" //10
                + "[Order_Checkout_At] = ?,\n" //11
                + "[Order_Update_At] = ?,\n" //12
                + "[Order_Update_By_Order_Manager] = ?\n" //13
                + "WHERE [Order_ID] = ?"; //14

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps = fillPreparedStatement(ps, order, operation.UPDATE);
            result = ps.executeUpdate() > 0;

            if (!result) {
                return false;
            }

            OrderDetailDao odDAO = new OrderDetailDao();
            result = odDAO.updateOrderDetail(order.getOrderDetailList());

            if (!result) {
                deleteOrder(DatabaseUtils.getLastIndentityOf("Order"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public boolean acceptOrder(Order order, int orderManagerId)
            throws NullPointerException,
            OperationEditFailedException {
        if (!order.getStatus().equals(status.PENDING.toString())) {
            System.out.println("Can not update status of an accepted order");
            throw new OperationEditFailedException();
        }

        long now = Generator.getCurrentTimeFromEpochMilli();

        order.setCheckoutAt(now);
        order.setUpdateAt(now);
        order.setUpdateByOrderManager(orderManagerId);
        order.setStatus(status.ACCEPTED.toString());

        return updateOrder(order);
    }

    public boolean rejectOrder(Order order, int orderManagerId)
            throws NullPointerException, OperationEditFailedException {
        if (!order.getStatus().equals(status.PENDING.toString())) {
            System.out.println("Can not update status of an rejected order");
            throw new OperationEditFailedException();
        }

        //check if the quantity stock is less than the order detail
        long now = Generator.getCurrentTimeFromEpochMilli();

        order.setUpdateAt(now);
        order.setUpdateByOrderManager(orderManagerId);
        order.setStatus(status.REJECTED.toString());
        return updateOrder(order);
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

    /* --------------------------- FILTER SECTION --------------------------- */
    public List<Order> searchOrder(String search) {
        if (search == null || search.equals("")) {
            search = "%";
        } else {
            search = "%" + search + "%";
        }

        ResultSet rs;
        List<Order> orderList = new ArrayList<>();

        try {
            String sql = "SELECT o.Order_ID,\n"
                    + "    o.Customer_ID,\n"
                    + "    u.User_Name,\n"
                    + "    o.Voucher_ID,\n"
                    + "    o.Order_Receiver_Name,\n"
                    + "    o.Order_Delivery_Address,\n"
                    + "    o.Order_Phone_Number,\n"
                    + "    o.Order_Note,\n"
                    + "    o.Order_Total,\n"
                    + "    o.Order_Deducted_Price,\n"
                    + "    o.Order_Status,\n"
                    + "    o.Order_Created_At,\n"
                    + "    o.Order_Checkout_At,\n"
                    + "    o.Order_Update_At,\n"
                    + "    o.Order_Update_By_Order_Manager\n"
                    + "FROM [Customer] c\n"
                    + "    JOIN [User] u ON c.User_ID = u.User_ID\n"
                    + "    JOIN [Order] o ON c.Customer_ID = o.Customer_ID\n"
                    + "WHERE o.Order_Receiver_Name LIKE ?\n"
                    + "    OR o.Order_Created_At LIKE ?\n"
                    + "    OR o.Order_Checkout_At LIKE ?\n"
                    + "    OR o.Order_Update_At LIKE ?\n"
                    + "    OR o.Order_Phone_Number LIKE ?\n"
                    + "    OR o.Order_Delivery_Address LIKE ?\n"
                    + "    OR u.User_Name LIKE ?\n"
                    + "    OR o.Order_Update_By_Order_Manager LIKE ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setNString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);
            ps.setString(5, search);
            ps.setNString(6, search);
            ps.setNString(7, search);
            ps.setNString(8, search);

            rs = ps.executeQuery();

            while (rs.next()) {
                orderList.add(orderFactory(rs, operation.SEARCH));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderList;
    }

    public List<Order> searchOrderActivityLog(String search) {
//        String sql = "SELECT [Order].Order_Update_At, [User].[User_Name],[User].[User_Email], [Order].Order_Created_At, [Order].Order_Receiver_Name, [Order].Order_Phone_Number, [Order].Order_Status FROM [Order]\n"
//                + "JOIN [Order_Manager] ON [Order].[Order_Update_By_Order_Manager] = [Order_Manager].[Order_Manager_ID]\n"
//                + "JOIN [Employee] ON [Order_Manager].[Employee_ID] = [Employee].[Employee_ID]\n"
//                + "JOIN [User] ON [Employee].[User_ID] = [User].[User_ID]\n"
//                + "WHERE [User].[User_Name] LIKE ? OR [User].[User_Username] LIKE ? OR [User].[User_Email] LIKE ? OR [Order].Order_Phone_Number LIKE ? OR [Order].Order_Receiver_Name LIKE ? ORDER BY [Order].Order_Update_At DESC";

        String sql = "SELECT * FROM [Order]\n"
                + "JOIN [Order_Manager] ON [Order].[Order_Update_By_Order_Manager] = [Order_Manager].[Order_Manager_ID]\n"
                + "JOIN [Employee] ON [Order_Manager].[Employee_ID] = [Employee].[Employee_ID]\n"
                + "JOIN [User] ON [Employee].[User_ID] = [User].[User_ID]\n"
                + "WHERE [User].[User_Name] LIKE ? OR [User].[User_Username] LIKE ? OR [User].[User_Email] LIKE ? OR [Order].Order_Phone_Number LIKE ? OR [Order].Order_Receiver_Name LIKE ? ORDER BY [Order].Order_Update_At DESC";
        List<Order> orderForActivityLogs = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            search = "%" + search + "%";
            ps.setNString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setNString(4, search);
            ps.setNString(5, search);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orderForActivityLogs.add(orderFactory(rs, operation.READ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductActivityLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderForActivityLogs;
    }
}
