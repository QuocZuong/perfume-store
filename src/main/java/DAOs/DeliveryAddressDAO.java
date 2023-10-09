package DAOs;

import Models.DeliveryAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryAddressDAO {

    private Connection conn;

    public DeliveryAddressDAO() {
        conn = DB.DBContext.getConnection();
    }

    private DeliveryAddress deliveryAddressFactory(ResultSet rs) throws SQLException {
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        if (rs.next()) {
            deliveryAddress.setCustomerId(rs.getInt("Customer_ID"));
            deliveryAddress.setReceiverName(rs.getNString("Receiver_Name"));
            deliveryAddress.setPhoneNumber(rs.getString("Phone_Number"));
            deliveryAddress.setAddress(rs.getNString("Address"));
            deliveryAddress.setStatus(rs.getNString("Status"));
            deliveryAddress.setCreateAt(rs.getDate("Create_At"));
            deliveryAddress.setModifiedAt(rs.getDate("Modified_At"));
        }
        return deliveryAddress;
    }

    public List<DeliveryAddress> getAll(int customerId) {
        String sql = "SELECT * FROM DeliveryAddress WHERE Customer_ID = ?";

        ResultSet rs;
        List<DeliveryAddress> result = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();

            while (rs.next()) {
                result.add(deliveryAddressFactory(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int addDeliveryAddress(DeliveryAddress deliveryAddress) {
        String sql = "INSERT INTO DeliveryAddress VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, deliveryAddress.getCustomerId());
            ps.setNString(2, deliveryAddress.getReceiverName());
            ps.setString(3, deliveryAddress.getPhoneNumber());
            ps.setNString(4, deliveryAddress.getAddress());
            ps.setNString(5, deliveryAddress.getStatus());
            ps.setDate(6, deliveryAddress.getCreateAt());
            ps.setDate(7, deliveryAddress.getModifiedAt());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

}
