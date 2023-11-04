package DAOs;

import Exceptions.InvalidVoucherException;
import Exceptions.NotEnoughVoucherQuantityException;
import Exceptions.OperationAddFailedException;
import Exceptions.VoucherCodeDuplication;
import Exceptions.VoucherNotFoundException;
import Models.Voucher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Interfaces.DAOs.IVoucherDAO;
import Lib.Converter;
import Lib.DatabaseUtils;
import Lib.Generator;
import Models.Admin;

public class VoucherDAO implements IVoucherDAO {

    private static Connection conn = null;

    public VoucherDAO() {
        conn = DB.DBContext.getConnection();
    }

    // CRUD
    /* ------------------------- CREATE SECTION ---------------------------- */
    @Override
    public int addVoucher(Voucher v) throws OperationAddFailedException, VoucherCodeDuplication {
        if (v == null) {
            throw new OperationAddFailedException();
        }
        if (getVoucher(v.getCode()) != null) {
            throw new VoucherCodeDuplication();
        }

        int result = 0;
        String sql = "INSERT INTO [Voucher] (\n"
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
            ps.setLong(5, v.getCreatedAt());
            ps.setLong(6, v.getExpiredAt());
            ps.setInt(7, v.getCreatedByAdmin());
            result = ps.executeUpdate();
            if (result != 0) {
                v.setId(DatabaseUtils.getLastIndentityOf("Voucher"));

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
    public int addApprovedProduct(Voucher v) throws OperationAddFailedException {
        String sql = "INSERT INTO [Voucher_Product] (Voucher_ID, Product_ID) VALUES (?, ?)";
        int result = 0;
        if (v != null && v.getApprovedProductId() != null && !v.getApprovedProductId().isEmpty()) {
            try {
                List<Integer> arrApprovedProductId = v.getApprovedProductId();
                PreparedStatement ps = conn.prepareStatement(sql);
                // set value for ?
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
        if (result == 0) {
            throw new OperationAddFailedException();
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
            ps.setInt(1, vId);

            rs = ps.executeQuery();
            if (rs.next()) {
                Voucher v = new Voucher();
                v.setId(vId);
                v.setCode(rs.getNString("Voucher_Code"));
                v.setQuantity(rs.getInt("Voucher_Quantity"));
                v.setDiscountPercent(rs.getInt("Voucher_Discount_Percent"));
                v.setDiscountMax(rs.getInt("Voucher_Discount_Max"));
                v.setCreatedAt(Converter.getNullOrValue(rs.getLong("Voucher_Created_At")));
                v.setExpiredAt(Converter.getNullOrValue(rs.getLong("Voucher_Expired_At")));
                v.setCreatedByAdmin(rs.getInt("Voucher_Created_By_Admin"));
                v.setApprovedProductId(getAllApprovedProductIdByVoucherId(vId));
                return v;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Voucher getVoucher(String vCode) {
        ResultSet rs;
        String sql = "SELECT * FROM [Voucher] WHERE Voucher_Code = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, vCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                Voucher v = new Voucher();
                v.setId(rs.getInt("Voucher_ID"));
                v.setCode(rs.getNString("Voucher_Code"));
                v.setQuantity(rs.getInt("Voucher_Quantity"));
                v.setDiscountPercent(rs.getInt("Voucher_Discount_Percent"));
                v.setDiscountMax(rs.getInt("Voucher_Discount_Max"));
                v.setCreatedAt(Converter.getNullOrValue(rs.getLong("Voucher_Created_At")));
                v.setExpiredAt(Converter.getNullOrValue(rs.getLong("Voucher_Expired_At")));
                v.setCreatedByAdmin(rs.getInt("Voucher_Created_By_Admin"));
                v.setApprovedProductId(getAllApprovedProductIdByVoucherId(v.getId()));
                return v;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Voucher> getAllVoucher() {
        String sql = "SELECT * FROM [Voucher]";
        List<Voucher> arrVoucher = new ArrayList<>();
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
                v.setCreatedAt(Converter.getNullOrValue(rs.getLong("Voucher_Created_At")));
                v.setExpiredAt(Converter.getNullOrValue(rs.getLong("Voucher_Expired_At")));
                v.setCreatedByAdmin(rs.getInt("Voucher_Created_By_Admin"));
                v.setApprovedProductId(getAllApprovedProductIdByVoucherId(vId));
                arrVoucher.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVoucher;
    }

    // get all productID that approved for a voucher
    public List<Voucher> searchVoucher(String code, int discountPercent) {
        ResultSet rs = null;
        List<Voucher> voucherList = new ArrayList<>();
        String sql = "SELECT \n"
                + "v.Voucher_ID,\n"
                + "v.Voucher_Code,\n"
                + "v.Voucher_Quantity,\n"
                + "v.Voucher_Discount_Percent,\n"
                + "v.Voucher_Discount_Max,\n"
                + "v.Voucher_Created_At,\n"
                + "v.Voucher_Expired_At,\n"
                + "v.Voucher_Created_By_Admin\n"
                + "FROM [Voucher] v\n"
                + "WHERE v.Voucher_Code LIKE ?\n"
                + "OR v.Voucher_Discount_Percent LIKE ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            ps.setInt(2, discountPercent);

            rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher();
                int vId = rs.getInt("Voucher_ID");
                v.setId(vId);
                v.setCode(rs.getNString("Voucher_Code"));
                v.setQuantity(rs.getInt("Voucher_Quantity"));
                v.setDiscountPercent(rs.getInt("Voucher_Discount_Percent"));
                v.setDiscountMax(rs.getInt("Voucher_Discount_Max"));
                v.setCreatedAt(Converter.getNullOrValue(rs.getLong("Voucher_Created_At")));
                v.setExpiredAt(Converter.getNullOrValue(rs.getLong("Voucher_Expired_At")));
                v.setCreatedByAdmin(rs.getInt("Voucher_Created_By_Admin"));
                v.setApprovedProductId(getAllApprovedProductIdByVoucherId(vId));
                voucherList.add(v);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return voucherList;
    }

    @Override
    public List<Integer> getAllApprovedProductIdByVoucherId(int vId) {
        ResultSet rs;
        List<Integer> arrProductId = new ArrayList<>();
        String sql = "SELECT Product_ID FROM Voucher_Product WHERE Voucher_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, vId);
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
    public List<Voucher> getValidVoucherOfProduct(int productId) {
        ResultSet rs;
        Voucher v;
        List<Voucher> arrVoucher = new ArrayList<>();
        long now = Generator.getCurrentTimeFromEpochMilli();

        String sql = "SELECT \n"
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
            ps.setLong(2, now);
            rs = ps.executeQuery();
            while (rs.next()) {
                v = new Voucher();
                v.setId(rs.getInt("Voucher_ID"));
                v.setCode(rs.getNString("Voucher_Code"));
                v.setQuantity(rs.getInt("Voucher_Quantity"));
                v.setDiscountPercent(rs.getInt("Voucher_Discount_Percent"));
                v.setDiscountMax(rs.getInt("Voucher_Discount_Max"));
                v.setCreatedAt(Converter.getNullOrValue(rs.getLong("Voucher_Created_At")));
                v.setExpiredAt(Converter.getNullOrValue(rs.getLong("Voucher_Expired_At")));
                v.setCreatedByAdmin(rs.getInt("Voucher_Created_By_Admin"));
                arrVoucher.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVoucher;
    }

    @Override
    public List<Integer> getUsedVoucherOfCustomer(int CustomerId) {
        ResultSet rs;
        int vId;
        List<Integer> arrVoucherId = new ArrayList<>();
        String sql = "SELECT Voucher_ID FROM [Order] WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, CustomerId);
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

    public boolean usedVoucher(int cusId, int vId) {
        ResultSet rs;
        String sql = "SELECT Voucher_ID FROM [Order] WHERE Customer_ID = ? AND Voucher_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cusId);
            ps.setInt(2, vId);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /* ------------------------- UPDATE SECTION ---------------------------- */
    public int updateVoucher(Voucher voucher) throws OperationAddFailedException, VoucherCodeDuplication {
        if (voucher == null) {
            throw new OperationAddFailedException();
        }
        //fix later, this must be not identical to other except itself
        if (isExistExcludeItself(voucher)) {
            throw new VoucherCodeDuplication();
        }

        String sql = "UPDATE [Voucher]\n"
                + "                 SET [Voucher_Code] = ?,  \n" //1
                + "                 [Voucher_Quantity] = ?,\n" // 2
                + "                 [Voucher_Discount_Percent] = ?,  \n"//3
                + "                 [Voucher_Discount_Max] = ?,\n"//4
                + "                 [Voucher_Created_At] = ?,  \n"//5
                + "                 [Voucher_Expired_At] = ?,\n"//6
                + "                 [Voucher_Created_By_Admin] = ? \n"//7
                + "                 WHERE [Voucher_ID] = ?  "; // 8
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, voucher.getCode());
            ps.setInt(2, voucher.getQuantity());
            ps.setInt(3, voucher.getDiscountPercent());
            ps.setInt(4, voucher.getDiscountMax());
            ps.setLong(5, voucher.getCreatedAt());
            ps.setLong(6, voucher.getExpiredAt());
            ps.setInt(7, voucher.getCreatedByAdmin());
            ps.setInt(8, voucher.getId());

            result = ps.executeUpdate();

            result += updateApproveProduct(voucher);

        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int updateApproveProduct(Voucher v) throws OperationAddFailedException {
        removeAllVoucherOfVoucherId(v.getId());
        //return 1 so that the voucher can have no approveProducts
        return addApprovedProduct(v);
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

    /* ------------------------- EXCEPTION SECTION ---------------------------- */
    public boolean checkValidVoucher(Voucher v, int cusId)
            throws VoucherNotFoundException, InvalidVoucherException, NotEnoughVoucherQuantityException {

        long now = Generator.getCurrentTimeFromEpochMilli();

        if (v == null) {
            System.out.println("voucher not found");
            throw new VoucherNotFoundException();
        }
        if (now > v.getExpiredAt() || now < v.getCreatedAt()) {
            System.out.println("invalid voucher");
            throw new InvalidVoucherException();
        }
        if (v.getQuantity() <= 0) {
            System.out.println("not enough voucher");
            throw new NotEnoughVoucherQuantityException();
        }
        if (usedVoucher(cusId, v.getId())) {
            System.out.println("used voucher");
            throw new InvalidVoucherException();
        }

        if (v.getApprovedProductId().isEmpty()) {
            System.out.println("invalid voucher");
            throw new InvalidVoucherException();
        }
        return true;
    }

    public boolean isExistExcludeItself(Voucher v) {
        if (v == null) {
            return false;
        }

        String sql = "SELECT Count(Voucher_ID) as Count_Voucher \n"
                + "FROM [Voucher]\n"
                + "WHERE [Voucher_Code] = ?\n"
                + "AND [Voucher_ID] != ? "; // 7
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, v.getCode());
            ps.setInt(2, v.getId());

            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("Count_Voucher");
                return count > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
