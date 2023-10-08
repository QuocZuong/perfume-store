package DAOs;

import Models.Voucher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Interfaces.DAOs.IVoucherDAO;

public class VoucherDAO implements IVoucherDAO {

    private static Connection conn = null;

    public VoucherDAO() {
        conn = DB.DBContext.getConnection();
    }

    // CRUD
    /* ------------------------- CREATE SECTION ---------------------------- */
    @Override
    public int addVoucher(Voucher v) {
        int result = 0;
        String sql = "IINSERT INTO [Voucher] (\n"
                + "	Voucher_ID, \n"
                + "	Voucher_Code, \n"
                + "	Voucher_Quantity, \n"
                + "	Voucher_Discount_Percent, \n"
                + "	Voucher_Discount_Max, \n"
                + "	Voucher_Created_At, \n"
                + "	Voucher_Expired_At, \n"
                + "	Voucher_Created_By_Admin\n"
                + "	) \n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, v.getId());
            ps.setNString(2, v.getCode());
            ps.setInt(3, v.getQuantity());
            ps.setInt(4, v.getDiscountPercent());
            ps.setInt(5, v.getDiscountMax());
            ps.setDate(6, v.getCreatedAt());
            ps.setDate(7, v.getExpiredAt());
            ps.setInt(8, v.getCreatedByAdmin());
            if (addApprovedProduct(v) != 0) {
                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int addApprovedProduct(Voucher v) {
        String sql = "INSERT INTO [Voucher_Product] (Voucher_ID, Product_ID) VALUES (?, ?)";
        int result = 0;
        if (v != null && v.getApprovedProductId() != null && !v.getApprovedProductId().isEmpty()) {
            try {
                ArrayList<Integer> arrApprovedProductId = v.getApprovedProductId();
                PreparedStatement ps = conn.prepareStatement(sql);
                //set value for ?
                for (int i = 0; i < arrApprovedProductId.size(); i++) {
                    ps.setInt(1, v.getId());
                    ps.setInt(2, arrApprovedProductId.get(i));
                    ps.addBatch();
                }
                int[] batchResult = ps.executeBatch();
                for (int i = 0; i < batchResult.length; i++) {
                    if (batchResult[i] == PreparedStatement.EXECUTE_FAILED) {
                        return 0;
                    }
                    result += batchResult[i];
                }
            } catch (SQLException ex) {
                Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    /* ------------------------- READ SECTION ---------------------------- */
    @Override
    public Voucher getVoucher(int vId) {
        ResultSet rs;
        String sql = "SELECT * FROM [Voucher] WHERE Voucher_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                Voucher v = new Voucher();
                v.setId(vId);
                v.setCode(rs.getNString("Voucher_Code"));
                v.setQuantity(rs.getInt("Voucher_Quantity"));
                v.setDiscountPercent(rs.getInt("Voucher_Discount_Percent"));
                v.setDiscountMax(rs.getInt("Voucher_Discount_Max"));
                v.setCreatedAt(rs.getDate("Voucher_Created_At"));
                v.setExpiredAt(rs.getDate("Voucher_Expired_At"));
                v.setCreatedByAdmin(rs.getInt("Voucher_Created_By_Admin"));
                v.setApprovedProductId(getAllApprovedProductIdByVoucherId(vId));
                return v;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Voucher> getAllVoucher() {
        String sql = "SELECT * FROM [Voucher]";
        ArrayList<Voucher> arrVoucher = new ArrayList();
        Voucher v;
        ResultSet rs;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                v = new Voucher();
                int vId = rs.getInt("Voucher_ID");
                v.setId(vId);
                v.setCode(rs.getNString("Voucher_Code"));
                v.setQuantity(rs.getInt("Voucher_Quantity"));
                v.setDiscountPercent(rs.getInt("Voucher_Discount_Percent"));
                v.setDiscountMax(rs.getInt("Voucher_Discount_Max"));
                v.setCreatedAt(rs.getDate("Voucher_Created_At"));
                v.setExpiredAt(rs.getDate("Voucher_Expired_At"));
                v.setCreatedByAdmin(rs.getInt("Voucher_Created_By_Admin"));
                v.setApprovedProductId(getAllApprovedProductIdByVoucherId(vId));
                arrVoucher.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVoucher;
    }

    //get all productID that approved for a voucher
    @Override
    public ArrayList<Integer> getAllApprovedProductIdByVoucherId(int vId) {
        ResultSet rs;
        ArrayList<Integer> arrProductId = new ArrayList();
        String sql = "SELECT Product_ID FROM Voucher_Product WHERE Voucher_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                arrProductId.add(rs.getInt("Product_ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrProductId;
    }
}
