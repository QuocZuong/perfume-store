package DAOs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import DB.DataManager;

public class BrandDAO {
    private static Connection conn = null;

    public BrandDAO() {
        conn = DB.DataManager.getConnection();
    }

    public ResultSet getAll() {
        ResultSet rs = null;
        String sql = "SELECT * FROM Brand";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(BrandDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    public int addBrand(String brandName) {
        int result = 0;

        try {
            String sql = "INSERT INTO Brand([Name]) VALUES(?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setNString(1, brandName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getBrandID(String BrandName) {
        int id = -1;
        ResultSet rs = null;
        
        try {
            String sql = "Select * from Brand Where [Name]=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, BrandName);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = Integer.parseInt(rs.getString("ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public boolean isExistedBrandName(String brandName) {
        return getBrandID(brandName) != -1;
    }

    public void save_Backup_Data() {
        ResultSet rs = null;
        String sql = "SELECT * FROM Brand";

        try (OutputStream os = new FileOutputStream(
                "C:\\Users\\Acer\\OneDrive\\Desktop\\#SU23\\PRJ301\\SQLproject\\SQLproject\\src\\main\\java\\BackUp\\backUp_Brand_data.txt");
                PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));) {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            StringBuilder strOUT = new StringBuilder("");

            Object obj;
            int columnCount = ps.getMetaData().getColumnCount();
            while (rs.next()) {
                strOUT.append("\"");
                for (int i = 1; i <= columnCount; i++) {
                    obj = rs.getObject(i) + "";
                    strOUT.append(obj);
                    if (i <= columnCount - 1) {
                        strOUT.append(DataManager.Separator);
                    }
                }
                strOUT.append("\",");
                out.println(strOUT);
                strOUT.setLength(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException io) {

        }
    }
}
