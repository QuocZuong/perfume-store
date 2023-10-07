/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.DAOs.IImportDetailDAO;
import Models.Import;
import Models.ImportDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ImportDetailDAO implements IImportDetailDAO{

    private Connection conn;

    public ImportDetailDAO() {
        conn = DB.DataManager.getConnection();
    }

    // CRUD
    /* ------------------------- CREATE SECTION ---------------------------- */
    @Override
    public int addImportDetail(ImportDetail ipD) {
        String sql = "INSERT INTO [Import_Detail] (Import_ID, Product_ID, Quantity, Cost) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ipD.getImportId());
            ps.setInt(2, ipD.getProductId());
            ps.setInt(3, ipD.getQuantity());
            ps.setInt(4, ipD.getCost());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    //This function add all import detail of an inport
    @Override
    public int addAllImportDetailOfImport(ArrayList<ImportDetail> arrIpd) {
        String sql = "INSERT INTO [Import_Detail] (Import_ID, Product_ID, Quantity, Cost) VALUES";
        String value = " (?, ?, ?, ?)";
        int result = 0;
        try {
            if (arrIpd != null && !arrIpd.isEmpty()) {
                //append (?, ?, ?, ?) to sql
                for (int i = 1; i <= arrIpd.size(); i++) {
                    if (i != arrIpd.size()) {
                        sql = sql + value + ", ";
                    } else {
                        sql = sql + value;
                    }
                }
                 PreparedStatement ps = conn.prepareStatement(sql);
                // set value for any ? in value (?, ?, ?, ?)
                for (int i = 0; i < arrIpd.size(); i++) {
                    ps.setInt(4 * i + 1, arrIpd.get(i).getImportId());
                    ps.setInt(4 * i + 2, arrIpd.get(i).getProductId());
                    ps.setInt(4 * i + 3, arrIpd.get(i).getQuantity());
                    ps.setInt(4 * i + 4, arrIpd.get(i).getCost());
                }
                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /* ------------------------- UPDATE SECTION ---------------------------- */
    @Override
    public int updateImportDetail(ImportDetail ipD) {
        String sql = "UPDATE Import_Detail SET\n"
                + "Quantity = ?,\n"
                + "Cost = ?\n"
                + "WHERE Import_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(3, ipD.getImportId());
            ps.setInt(4, ipD.getProductId());
            ps.setInt(1, ipD.getQuantity());
            ps.setInt(2, ipD.getCost());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            ps.setInt(3, ipD.getImportId());
            ps.setInt(4, ipD.getProductId());
            ps.setInt(1, ipD.getQuantity());
            ps.setInt(2, ipD.getCost());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ipD;
    }

    @Override
    public ArrayList<ImportDetail> getAllImportDetailOfImport(int ipId) {
        ResultSet rs;
        ArrayList<ImportDetail> arrImportDetail = new ArrayList();
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
                arrImportDetail.add(ipD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrImportDetail;
    }

    @Override
    public int getTotalQuantityImportDetail(ArrayList<ImportDetail> arrIpD) {
        int total_quan = 0;
        if (arrIpD != null && !arrIpD.isEmpty()) {
            for (int i = 0; i < arrIpD.size(); i++) {
                total_quan += arrIpD.get(i).getQuantity();
            }
        }
        return total_quan;
    }

    @Override
    public int getTotalCostImportDetail(ArrayList<ImportDetail> arrIpD) {
        int total_cost = 0;
        if (arrIpD != null && !arrIpD.isEmpty()) {
            for (int i = 0; i < arrIpD.size(); i++) {
                total_cost += arrIpD.get(i).getCost();
            }
        }
        return total_cost;
    }

}
