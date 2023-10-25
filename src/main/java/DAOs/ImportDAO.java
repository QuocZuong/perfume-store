package DAOs;

import Interfaces.DAOs.IImportDAO;
import Lib.DatabaseUtils;
import Models.Import;
import Models.ImportDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportDAO implements IImportDAO {

    private Connection conn;

    public ImportDAO() {
        conn = DB.DBContext.getConnection();
    }

    // CRUD
    /* ------------------------- CREATE SECTION ---------------------------- */
    @Override
    public int addImport(Import ip) {
        ImportDetailDAO ipdDAO = new ImportDetailDAO();
        int result = 0;
        String sql = "INSERT INTO [Import] (Import_Total_Quantity, Import_Total_Cost, Supplier_Name, Import_At, Delivered_At, Import_By_Inventory_Manager) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ip.getTotalQuantity());
            ps.setInt(2, ip.getTotalCost());
            ps.setString(3, ip.getSupplierName());
            ps.setLong(4, ip.getImportAt());
            ps.setLong(5, ip.getDeliveredAt());
            ps.setInt(6, ip.getImportByInventoryManager());
            result = ps.executeUpdate();
            int addImportDetailResult = ipdDAO.addAllImportDetailOfImport(ip.getImportDetail());
            if (addImportDetailResult == 0) {
                int importId = DatabaseUtils.getLastIndentityOf("[Import]");
                ipdDAO.removeAllImportDetailOfImport(importId);
                System.out.println("remove all remain import detail of import id:" + importId);
                removeImportOnly(importId);
                System.out.println("add import detail fail so delte import id:" + importId);
                result = 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("add import result :" + result);
        return result;
    }

    /* ------------------------- READ SECTION ---------------------------- */
    @Override
    public List<Import> getAllImport() {
        List<Import> arrImport = new ArrayList<>();
        List<ImportDetail> ipD;
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
                ip.setImportAt(rs.getLong("Import_At"));
                ip.setDeliveredAt(rs.getLong("Delivered_At"));
                ip.setImportByInventoryManager(rs.getInt("Import_By_Inventory_Manager"));
                ip.setModifiedAt(rs.getInt("Modified_At"));
                ip.setModifiedByAdmin(rs.getInt("Modified_By_Admin"));
                ip.setImportDetail(ipD);
                arrImport.add(ip);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrImport;
    }

    @Override
    public Import getImport(int ipId) {
        List<ImportDetail> ipDList;
        Import ip = null;
        ImportDetailDAO ipdDAO = new ImportDetailDAO();
        ResultSet rs;
        String sql = "SELECT * FROM [Import] WHERE Import_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ipId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ip = new Import();
                ipDList = ipdDAO.getAllImportDetailOfImport(ipId);
                ip.setId(ipId);
                ip.setTotalQuantity(rs.getInt("Import_Total_Quantity"));
                ip.setTotalCost(rs.getInt("Import_Total_Cost"));
                ip.setSupplierName(rs.getNString("Supplier_Name"));
                ip.setImportAt(rs.getLong("Import_At"));
                ip.setDeliveredAt(rs.getLong("Delivered_At"));
                ip.setImportByInventoryManager(rs.getInt("Import_By_Inventory_Manager"));
                ip.setModifiedAt(rs.getInt("Modified_At"));
                ip.setModifiedByAdmin(rs.getInt("Modified_By_Admin"));
                ip.setImportDetail(ipDList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ip;
    }

    public List<Import> searchImport(String search) {
        int digit = -1;
        if (search == null || search.equals("")) {
            search = "%";
        }

        if (search.matches("[0-9]+")) {
            digit = Integer.parseInt(search);
        }

        search = "%" + search + "%";

        List<Import> impList = new ArrayList<>();

        ResultSet rs;

        String sql = "SELECT * FROM [Import] WHERE \n"
                + "\n"
                + "Import_ID = ? OR\n"
                + "Import_Total_Cost = ? OR\n"
                + "Import_Total_Quantity = ? OR\n"
                + "Import_By_Inventory_Manager = ? OR\n"
                + "Modified_By_Admin = ? OR\n"
                + "Supplier_Name LIKE ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, digit);
            ps.setInt(2, digit);
            ps.setInt(3, digit);
            ps.setInt(4, digit);
            ps.setInt(5, digit);
            ps.setNString(6, search);
            rs = ps.executeQuery();
            while (rs.next()) {
                Import ip = new Import();
                ip.setId(rs.getInt("Import_ID"));
                ip.setTotalQuantity(rs.getInt("Import_Total_Quantity"));
                ip.setTotalCost(rs.getInt("Import_Total_Cost"));
                ip.setSupplierName(rs.getNString("Supplier_Name"));
                ip.setImportAt(rs.getLong("Import_At"));
                ip.setDeliveredAt(rs.getLong("Delivered_At"));
                ip.setImportByInventoryManager(rs.getInt("Import_By_Inventory_Manager"));
                ip.setModifiedAt(rs.getInt("Modified_At"));
                ip.setModifiedByAdmin(rs.getInt("Modified_By_Admin"));
                impList.add(ip);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return impList;
    }

    public List<Import> fillterImport(List<Import> importList, int inventoryManagerId) {
        List<Import> ipList = new ArrayList<>();
        if (importList != null && !importList.isEmpty()) {
            for (int i = 0; i < importList.size(); i++) {
                if (importList.get(i).getImportByInventoryManager() == inventoryManagerId) {
                    ipList.add(importList.get(i));
                }
            }
        }
        return ipList;
    }

    /* ------------------------- DELETE SECTION ---------------------------- */
    public int removeImportOnly(int ipId) {
        String sql = "DELETE FROM Import WHERE Import_ID = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ipId);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
