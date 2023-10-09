package DAOs;

import Models.DeliveryAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryAddressDAO implements Interfaces.DAOs.IDeliveryAddressDAO {

    final String TABLE_NAME = "DeliveryAddress";
    final String CUSTOMER_ID = "Customer_ID";
    final String RECEIVER_NAME = "Receiver_Name";
    final String PHONE_NUMBER = "Phone_Number";
    final String ADDRESS = "Address";
    final String STATUS = "Status";
    final String CREATE_AT = "Create_At";
    final String MODIFIED_AT = "Modified_At";

    private Connection conn;

    public DeliveryAddressDAO() {
        conn = DB.DBContext.getConnection();
    }

    private DeliveryAddress deliveryAddressFactory(ResultSet rs) throws SQLException, NullPointerException {
        if (rs == null) {
            throw new NullPointerException("ResultSet is null");
        }

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

    /* --------------------- CREATE SECTION --------------------- */

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

    /* --------------------- READ SECTION --------------------- */

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

    /* --------------------- UPDATE SECTION --------------------- */

    public boolean updateDeliveryAddress(DeliveryAddress deliveryAddress) throws NullPointerException {
        if (deliveryAddress == null) {
            throw new NullPointerException("DeliveryAddress is null");
        }

        String sql = "UPDATE [DeliveryAddress]\n"
                + "SET [Receiver_Name] = ?, [Phone_Number] = ?, [Address] = ?, [Status] = ?, [Create_At] = ?, [Modified_At] = ?\n"
                + "WHERE [Customer_ID] = ?";

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);

            ps.setNString(1, deliveryAddress.getReceiverName());
            ps.setString(2, deliveryAddress.getPhoneNumber());
            ps.setNString(3, deliveryAddress.getAddress());
            ps.setNString(4, deliveryAddress.getStatus());
            ps.setDate(5, deliveryAddress.getCreateAt());
            ps.setDate(6, deliveryAddress.getModifiedAt());
            ps.setInt(7, deliveryAddress.getCustomerId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryAddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean updateDeliveryAddress(List<DeliveryAddress> dList) throws NullPointerException {
        if (dList == null) {
            throw new NullPointerException("DeliveryAddress List is null");
        }
        if (dList.isEmpty()) {
            throw new NullPointerException("DeliveryAddress List is empty");
        }
        for (DeliveryAddress dAddress : dList) {
            if (dAddress == null) {
                throw new NullPointerException("One of the DeliveryAddress in the list provided is null");
            }
        }

        String sql = "UPDATE [DeliveryAddress]\n"
                + "SET [Receiver_Name] = ?, [Phone_Number] = ?, [Address] = ?, [Status] = ?, [Create_At] = ?, [Modified_At] = ?\n"
                + "WHERE [Customer_ID] = ?";

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < dList.size(); i++) {
                DeliveryAddress deliveryAddress = dList.get(i);

                ps.setNString(1, deliveryAddress.getReceiverName());
                ps.setString(2, deliveryAddress.getPhoneNumber());
                ps.setNString(3, deliveryAddress.getAddress());
                ps.setNString(4, deliveryAddress.getStatus());
                ps.setDate(5, deliveryAddress.getCreateAt());
                ps.setDate(6, deliveryAddress.getModifiedAt());
                ps.setInt(7, deliveryAddress.getCustomerId());

                ps.addBatch();
                ps.clearParameters();
            }

            for (int result : ps.executeBatch()) {
                if (result < 0) {
                    return false;
                }
            }

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryAddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /* --------------------- DELETE SECTION --------------------- */

    public boolean deleteDeliveryAddress(DeliveryAddress deliveryAddress) throws NullPointerException {
        if (deliveryAddress == null) {
            throw new NullPointerException("DeliveryAddress is null");
        }

        String sql = "DELETE FROM [DeliveryAddress]\n"
                + "WHERE [Customer_ID] = ?";

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);

            ps.setInt(1, deliveryAddress.getCustomerId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryAddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean deleteDeliveryAddress(List<DeliveryAddress> dList) throws NullPointerException {
        if (dList == null) {
            throw new NullPointerException("DeliveryAddress List is null");
        }
        if (dList.isEmpty()) {
            throw new NullPointerException("DeliveryAddress List is empty");
        }
        for (DeliveryAddress dAddress : dList) {
            if (dAddress == null) {
                throw new NullPointerException("One of the DeliveryAddress in the list provided is null");
            }
        }

        String sql = "DELETE FROM [DeliveryAddress]\n"
                + "WHERE [Customer_ID] = ?";

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < dList.size(); i++) {
                DeliveryAddress deliveryAddress = dList.get(i);

                ps.setInt(1, deliveryAddress.getCustomerId());

                ps.addBatch();
                ps.clearParameters();
            }

            for (int result : ps.executeBatch()) {
                if (result < 0) {
                    return false;
                }
            }

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryAddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}
