/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Lib.Generator;
import Models.Admin;
import Models.Product;
import Models.ProductActivityLog;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Acer
 */
public class ProductActivityLogDAO {

    private Connection conn;

    public ProductActivityLogDAO() {
        conn = DB.DBContext.getConnection();
    }

    public enum Operation {
        Add,
        Disable,
        Update,
        Restore,
    };

    public List<ProductActivityLog> getAll() {
        String sql = "SELECT * FROM Product_Activity_Log ORDER BY Updated_At DESC;";
        List<ProductActivityLog> palList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductActivityLog pal = new ProductActivityLog();
                pal.setProductId(rs.getInt("Product_ID"));
                pal.setAction(rs.getNString("Action"));
                pal.setDescription(rs.getNString("Description"));
                pal.setUpdatedByAdmin(rs.getInt("Updated_By_Admin"));
                pal.setUpdatedAt(rs.getDate("Updated_At"));

                palList.add(pal);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductActivityLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return palList;
    }

    public List<ProductActivityLog> filterProductActivityLog(List<ProductActivityLog> palList, int productId) {
        List<ProductActivityLog> subPalList = palList
                .stream()
                .filter(p -> p.getProductId() == productId)
                .collect(Collectors.toList());
        return subPalList;
    }

    public int addProductActivityLog(Operation op, Product product, Admin admin, String description) {
        String sql = "INSERT INTO Product_Activity_Log\n"
                + "VALUES(?,?,?,?,?);";
        //        Product_ID (int not null) (duplicate)
        //Action (nvarchar(10) not null)
        //Description (nvarchar(max) default null)
        //Updated_By_Admin (int not null)
        //Updated_At (Datetime)
        int result = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, product.getId());
            ps.setNString(2, op.toString());
            ps.setNString(3, description);
            ps.setInt(4, admin.getAdminId());
            // Fix later
            ps.setString(5, Generator.generateDateTime());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductActivityLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

}
