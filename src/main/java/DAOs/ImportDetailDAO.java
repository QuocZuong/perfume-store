/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.DAOs.IImportDetailDAO;
import Lib.DatabaseUtils;
import Models.ImportDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ImportDetailDAO implements IImportDetailDAO {

    private Connection conn;

    public ImportDetailDAO() {
        conn = DB.DBContext.getConnection();
    }

    // CRUD
    /* ------------------------- CREATE SECTION ---------------------------- */
    @Override
    public int addImportDetail(ImportDetail ipD) {
        String sql = "INSERT INTO [Import_Detail] (Import_ID, Product_ID, Quantity, Cost, Status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ipD.getImportId());
            ps.setInt(2, ipD.getProductId());
            ps.setInt(3, ipD.getQuantity());
            ps.setInt(4, ipD.getCost());
            ps.setNString(5, ipD.getStatus());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImportDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    //This function add all import detail of an inport
    @Override
    public int addAllImportDetailOfImport(List<ImportDetail> arrIpd) {
        String sql = "INSERT INTO [Import_Detail] (Import_ID, Product_ID, Quantity, Cost, Status) VALUES (?, ?, ?, ?, ?)";
        int ImportID = DatabaseUtils.getLastIndentityOf("[Import]");
        System.out.println("Last id:" + ImportID);
        int result = 0;
        try {
            if (arrIpd != null && !arrIpd.isEmpty()) {
                PreparedStatement ps = conn.prepareStatement(sql);
                // set value for any ? in value (?, ?, ?, ?)
                for (int i = 0; i < arrIpd.size(); i++) {
                    if (arrIpd.get(i) == null) {
                        return 0;
                    }
                    ps.setInt(1, ImportID);
                    ps.setInt(2, arrIpd.get(i).getProductId());
                    ps.setInt(3, arrIpd.get(i).getQuantity());
                    ps.setInt(4, arrIpd.get(i).getCost());
                    ps.setNString(5, arrIpd.get(i).getStatus());
                    ps.addBatch();
                }
                int[] batchResult = ps.executeBatch();
                for (int i = 0; i < batchResult.length; i++) {
                    if (batchResult[i] == PreparedStatement.EXECUTE_FAILED) {
                        System.out.println("add import detail element fail");
                        return 0;
                    }
                    result += batchResult[i];
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /* ------------------------- UPDATE SECTION ---------------------------- */
    @Override
    public int updateImportDetail(ImportDetail ipD) {
        String sql = "UPDATE Import_Detail SET\n"
                + "Quantity = ?,\n"
                + "Cost = ?\n"
                + "Status = ?\n"
                + "WHERE Import_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(4, ipD.getImportId());
            ps.setInt(5, ipD.getProductId());
            ps.setInt(1, ipD.getQuantity());
            ps.setInt(2, ipD.getCost());
            ps.setNString(3, ipD.getStatus());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImportDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /* ------------------------- DELETE SECTION ---------------------------- */
    @Override
    public int deleteImportDetail(ImportDetail ipD) {
        String sql = "DELETE FROM Import_Detail\n"
                + "WHERE Import_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ipD.getImportId());
            ps.setInt(2, ipD.getProductId());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImportDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int deleteImportDetailOfImport(int importId) {
        String sql = "DELETE FROM Import_Detail\n"
                + "WHERE Import_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, importId);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImportDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /* ------------------------- READ SECTION ---------------------------- */
    @Override
    public ImportDetail getImportDetail(int ipId, int pdId) {
        ResultSet rs;
        ImportDetail ipD = null;
        String sql = "SELECT * FROM Import_Detail\n"
                + "WHERE Import_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ipId);
            ps.setInt(2, pdId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ipD = new ImportDetail();
                ipD.setImportId(ipId);
                ipD.setProductId(pdId);
                ipD.setQuantity(rs.getInt("Quantity"));
                ipD.setCost(rs.getInt("Cost"));
                ipD.setStatus(rs.getNString("Status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ipD;
    }

    @Override
    public List<ImportDetail> getAllImportDetailOfImport(int ipId) {
        ResultSet rs;
        List<ImportDetail> arrImportDetail = new ArrayList();
        ImportDetail ipD;
        String sql = "SELECT * FROM Import_Detail\n"
                + "WHERE Import_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ipId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ipD = new ImportDetail();
                ipD.setImportId(ipId);
                ipD.setProductId(rs.getInt("Product_ID"));
                ipD.setQuantity(rs.getInt("Quantity"));
                ipD.setCost(rs.getInt("Cost"));
                ipD.setStatus(rs.getNString("Status"));
                arrImportDetail.add(ipD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrImportDetail;
    }

    @Override
    public int getTotalQuantityImportDetail(List<ImportDetail> arrIpD) {
        int total_quan = 0;
        if (arrIpD != null && !arrIpD.isEmpty()) {
            for (int i = 0; i < arrIpD.size(); i++) {
                total_quan += arrIpD.get(i).getQuantity();
            }
        }
        return total_quan;
    }

    @Override
    public int getTotalCostImportDetail(List<ImportDetail> arrIpD) {
        int total_cost = 0;
        if (arrIpD != null && !arrIpD.isEmpty()) {
            for (int i = 0; i < arrIpD.size(); i++) {
                total_cost += arrIpD.get(i).getCost();
            }
        }
        return total_cost;
    }

    /* ------------------------- DELETE SECTION ---------------------------- */
    public int removeAllImportDetailOfImport(int ipId) {
        String sql = "DELETE FROM [Import_Detail] WHERE Import_ID = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ipId);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImportDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
