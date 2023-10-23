package DAOs;

import Interfaces.DAOs.IImportStashItemDAO;
import Models.ImportStashItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportStashItemDAO implements IImportStashItemDAO {

    private Connection conn;

    public ImportStashItemDAO() {
        conn = DB.DBContext.getConnection();
    }

    // CRUD
    /* ------------------------- CREATE SECTION ---------------------------- */
    @Override
    public int addImportStashItem(ImportStashItem ipsi) {
        String sql = "INSERT INTO [Import_Stash_Item] (Inventory_Manager_ID , Product_ID, Quantity, Cost, SumCost) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ipsi.getInventoryManagerId());
            ps.setInt(2, ipsi.getProductId());
            ps.setInt(3, ipsi.getQuantity());
            ps.setInt(4, ipsi.getCost());
            ps.setInt(5, ipsi.getSumCost());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImportStashItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /* ------------------------- UPDATE SECTION ---------------------------- */
    @Override
    public int updateImportStashItem(ImportStashItem ipsi) {
        String sql = "UPDATE Import_Stash_Item SET\n"
                + "Quantity = ?,\n"
                + "Cost = ?,\n"
                + "SumCost = ?\n"
                + "WHERE Inventory_Manager_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(4, ipsi.getInventoryManagerId());
            ps.setInt(5, ipsi.getProductId());
            ps.setInt(1, ipsi.getQuantity());
            ps.setInt(2, ipsi.getCost());
            ps.setInt(3, ipsi.getSumCost());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImportStashItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /* ------------------------- DELETE SECTION ---------------------------- */
    @Override
    public int deleteImportStashItem(ImportStashItem ipsi) {
        String sql = "DELETE FROM Import_Stash_Item\n"
                + "WHERE Inventory_Manager_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ipsi.getInventoryManagerId());
            ps.setInt(2, ipsi.getProductId());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImportStashItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int deleteImportStashItem(int InventoryManagerId, int ProductId) {
        String sql = "DELETE FROM Import_Stash_Item\n"
                + "WHERE Inventory_Manager_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, InventoryManagerId);
            ps.setInt(2, ProductId);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImportStashItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int deleteAllImportStashItemOfManager(int inventoryManagerId) {
        String sql = "DELETE FROM Import_Stash_Item\n"
                + "WHERE Inventory_Manager_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, inventoryManagerId);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImportStashItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /* ------------------------- READ SECTION ---------------------------- */
    @Override
    public ImportStashItem getImportStashItem(int managerId, int pdId) {
        ResultSet rs;
        ImportStashItem ipsi = null;
        String sql = "SELECT * FROM Import_Stash_Item\n"
                + "WHERE Inventory_Manager_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, managerId);
            ps.setInt(2, pdId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ipsi = new ImportStashItem();
                ipsi.setInventoryManagerId(managerId);
                ipsi.setProductId(pdId);
                ipsi.setQuantity(rs.getInt("Quantity"));
                ipsi.setCost(rs.getInt("Cost"));
                ipsi.setSumCost(rs.getInt("SumCost"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportStashItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ipsi;
    }

    @Override
    public List<ImportStashItem> getAllImportStashItemOfManager(int managerId) {
        ResultSet rs;
        List<ImportStashItem> arrImportStashItem = new ArrayList();
        ImportStashItem ipsi;
        String sql = "SELECT * FROM Import_Stash_Item\n"
                + "WHERE Inventory_Manager_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, managerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ipsi = new ImportStashItem();
                ipsi.setInventoryManagerId(managerId);
                ipsi.setProductId(rs.getInt("Product_ID"));
                ipsi.setQuantity(rs.getInt("Quantity"));
                ipsi.setCost(rs.getInt("Cost"));
                ipsi.setSumCost(rs.getInt("SumCost"));
                arrImportStashItem.add(ipsi);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportStashItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrImportStashItem;
    }

    public int getImportCartTotalCost(List<ImportStashItem> listImportItem) {
        if (listImportItem == null || listImportItem.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < listImportItem.size(); i++) {
            total += listImportItem.get(i).getSumCost();
        }
        return total;
    }

    public int getImportCartTotalQuantity(List<ImportStashItem> listImportItem) {
        if (listImportItem == null || listImportItem.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < listImportItem.size(); i++) {
            total += listImportItem.get(i).getQuantity();
        }
        return total;
    }

}
