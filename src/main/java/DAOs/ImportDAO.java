/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.Import;
import Models.ImportDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ImportDAO implements IImportDAO {

    private Connection conn;

    public ImportDAO() {
        conn = DB.DataManager.getConnection();
    }

    // CRUD
    /* ------------------------- CREATE SECTION ---------------------------- */
    @Override
    public int addImport(Import ip) {
        ImportDetailDAO ipdDAO = new ImportDetailDAO();
        String sql = "INSERT INTO [Import] (Import_ID, Import_Total_Quantity, Import_Total_Cost, Supplier_Name, Import_At, Delivered_At, Import_By_Inventory_Manager) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ip.getId());
            ps.setInt(2, ip.getTotalQuantity());
            ps.setInt(3, ip.getTotalCost());
            ps.setString(4, ip.getSupplierName());
            ps.setDate(5, ip.getImportAt());
            ps.setDate(6, ip.getDeliveredAt());
            ps.setInt(7, ip.getImportByInventoryManager());
            int addImportDetailResult = ipdDAO.addAllImportDetailOfImport(ip.getImportDetail());
            if (addImportDetailResult != 0) {
                return ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /* ------------------------- READ SECTION ---------------------------- */
    @Override
    public ArrayList<Import> getAllImport() {
        ArrayList<Import> arrImport = new ArrayList();
        ArrayList<ImportDetail> ipD;
        Import ip;
        ImportDetailDAO ipdDAO = new ImportDetailDAO();
        ResultSet rs;
        String sql = "SELECT * FROM [Import]";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ip = new Import();
                int ipId = rs.getInt("Import_ID");
                ipD = ipdDAO.getAllImportDetailOfImport(ipId);
                int total_quan = ipdDAO.getTotalQuantityImportDetail(ipD);
                int total_cost = ipdDAO.getTotalCostImportDetail(ipD);
                ip.setId(ipId);
                ip.setTotalQuantity(total_quan);
                ip.setTotalCost(total_cost);
                ip.setSupplierName(rs.getNString("Supplier_Name"));
                ip.setImportAt(rs.getDate("Import_At"));
                ip.setDeliveredAt(rs.getDate("Delivered_At"));
                ip.setImportByInventoryManager(rs.getInt("Import_By_Inventory_Manager"));
                ip.setImportDetail(ipD);
                arrImport.add(ip);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrImport;
    }

    @Override
    public Import getImport(int ipId) {
        ArrayList<ImportDetail> ipD;
        Import ip = null;
        ImportDetailDAO ipdDAO = new ImportDetailDAO();
        ResultSet rs;
        String sql = "SELECT * FROM [Import] WHERE Import_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ip = new Import();
                ipD = ipdDAO.getAllImportDetailOfImport(ipId);
                ip.setId(ipId);
                ip.setTotalQuantity(ipdDAO.getTotalQuantityImportDetail(ipD));
                ip.setTotalCost(ipdDAO.getTotalCostImportDetail(ipD));
                ip.setSupplierName(rs.getNString("Supplier_Name"));
                ip.setImportAt(rs.getDate("Import_At"));
                ip.setDeliveredAt(rs.getDate("Delivered_At"));
                ip.setImportByInventoryManager(rs.getInt("Import_By_Inventory_Manager"));
                ip.setImportDetail(ipD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ip;
    }

}
