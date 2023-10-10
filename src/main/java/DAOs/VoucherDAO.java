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
import Lib.DatabaseUtils;
import java.sql.Date;

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
                + "	Voucher_Code, \n"
                + "	Voucher_Quantity, \n"
                + "	Voucher_Discount_Percent, \n"
                + "	Voucher_Discount_Max, \n"
                + "	Voucher_Created_At, \n"
                + "	Voucher_Expired_At, \n"
                + "	Voucher_Created_By_Admin\n"
                + "	) \n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, v.getCode());
            ps.setInt(2, v.getQuantity());
            ps.setInt(3, v.getDiscountPercent());
            ps.setInt(4, v.getDiscountMax());
            ps.setDate(5, v.getCreatedAt());
            ps.setDate(6, v.getExpiredAt());
            ps.setInt(7, v.getCreatedByAdmin());
            result = ps.executeUpdate();
            if (result != 0) {
                if (addApprovedProduct(v) == 0) {
                    removeAllVoucherOfVoucherId(v.getId());
                    System.out.println("remove all remain voucher product of voucher id" + v.getId());
                    removeVoucherOnly(v.getId());
                    System.out.println("add voucher detail fail so delete voucher id:" + v.getId());
                }
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
                        System.out.println("add voucher detail fail");
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

    @Override
    public ArrayList<Voucher> getValidVoucherOfProduct(int productId) {
        ResultSet rs;
        Voucher v;
        ArrayList<Voucher> arrVoucher = new ArrayList();
        Date now = new Date(System.currentTimeMillis());

        String sql
                = "SELECT \n"
                + "	Voucher.Voucher_ID, \n"
                + "	Voucher.Voucher_Code,\n"
                + "	Voucher.Voucher_Quantity, \n"
                + "	Voucher.Voucher_Discount_Percent, \n"
                + "	Voucher.Voucher_Discount_Max, \n"
                + "	Voucher.Voucher_Created_At,\n"
                + "	Voucher.Voucher_Expired_At,\n"
                + "	Voucher.Voucher_Created_By_Admin\n"
                + "FROM Voucher, Voucher_Product\n"
                + "WHERE \n"
                + "	Voucher.Voucher_ID = Voucher_Product.Voucher_ID AND\n"
                + "	Voucher_Product.Product_ID = ? AND\n"
                + "	? BETWEEN Voucher.Voucher_Created_At AND Voucher.Voucher_Expired_At AND\n"
                + "	Voucher.Voucher_Quantity > 0";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setDate(2, now);
            rs = ps.executeQuery();
            while (rs.next()) {
                v = new Voucher();
                v.setId(rs.getInt("Voucher_ID"));
                v.setCode(rs.getNString("Voucher_Code"));
                v.setQuantity(rs.getInt("Voucher_Quantity"));
                v.setDiscountPercent(rs.getInt("Voucher_Discount_Percent"));
                v.setDiscountMax(rs.getInt("Voucher_Discount_Max"));
                v.setCreatedAt(rs.getDate("Voucher_Created_At"));
                v.setExpiredAt(rs.getDate("Voucher_Expired_At"));
                v.setCreatedByAdmin(rs.getInt("Voucher_Created_By_Admin"));
                arrVoucher.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVoucher;
    }

    @Override
    public ArrayList<Integer> getUsedVoucherOfCustomer(int CustomerId) {
        ResultSet rs;
        int vId;
        ArrayList<Integer> arrVoucherId = new ArrayList();
        String sql = "SELECT Voucher_ID FROM [Order] WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                vId = rs.getInt("Voucher_ID");
                arrVoucherId.add(vId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVoucherId;
    }

    /* ------------------------- DELETE SECTION ---------------------------- */
    public int removeVoucherOnly(int vId) {
        String sql = "DELETE FROM [Voucher] WHERE Voucher_ID = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            result = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int removeAllVoucherOfVoucherId(int vId) {
        String sql = "DELETE FROM [Voucher_Product] WHERE Voucher_ID = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, vId);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
