package DAOs;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            result = ps.executeUpdate();
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

    public String getBrandName(int brandID) {
        String name = null;
        ResultSet rs = null;

        try {
            String sql = "Select * from Brand Where [id]=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, brandID);
            rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }

    public ResultSet getBrandNameByAlphabet(char a) {
        ResultSet rs = null;
        
        try {
            String sql = "SELECT * FROM Brand WHERE [Name] LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, a + "%");
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rs;
    }

    public boolean isExistedBrandName(String brandName) {
        return getBrandID(brandName) != -1;
    }

   
}
