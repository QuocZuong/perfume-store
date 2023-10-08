package DAOs;

import Interfaces.DAOs.IBrandDAO;
import Models.Brand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrandDAO implements IBrandDAO {

    private static Connection conn = null;

    public BrandDAO() {
        conn = DB.DBContext.getConnection();
    }

    //Create
    @Override
    public int addBrand(Brand brand) {
        int result = 0;
        try {
            String sql = "INSERT INTO Brand("
                    + "[Brand_Name], "
                    + "[Brand_Logo], "
                    + "[Brand_Img]) "
                    + "VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, brand.getName());
            ps.setNString(2, brand.getLogo());
            ps.setNString(3, brand.getImgURL());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    //Read
    @Override
    public ArrayList<Brand> getAll() {
        ResultSet rs;
        String sql = "SELECT * FROM Brand";
        ArrayList<Brand> brandList = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("Brand_ID"));
                brand.setName(rs.getNString("Brand_Name"));
                brand.setLogo(rs.getNString("Brand_Logo"));
                brand.setImgURL(rs.getNString("Brand_Img"));
                brand.setTotalProduct(rs.getInt("Brand_Total_Product"));

                brandList.add(brand);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BrandDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return brandList;
    }

    @Override
    public ArrayList<Brand> getBrandNameByAlphabet(char a) {

        ResultSet rs = null;
        ArrayList<Brand> brandList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Brand WHERE [Brand_Name] LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, a + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("Brand_ID"));
                brand.setName(rs.getNString("Brand_Name"));
                brand.setLogo(rs.getNString("Brand_Logo"));
                brand.setImgURL(rs.getNString("Brand_Img"));
                brand.setTotalProduct(rs.getInt("Brand_Total_Product"));

                brandList.add(brand);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brandList;
    }

    @Override
    public Brand getBrand(int brandID) {
        ResultSet rs = null;

        try {
            String sql = "Select * from Brand Where [Brand_ID]=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, brandID);
            rs = ps.executeQuery();
            Brand brand = new Brand();
            if (rs.next()) {
                brand.setId(rs.getInt("Brand_ID"));
                brand.setName(rs.getNString("Brand_Name"));
                brand.setLogo(rs.getNString("Brand_Logo"));
                brand.setImgURL(rs.getNString("Brand_Img"));
                brand.setTotalProduct(rs.getInt("Brand_Total_Product"));
            }
            return brand;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Brand getBrand(String brandName) {
        ResultSet rs = null;

        try {
            String sql = "Select * from Brand Where [Brand_Name]=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, brandName);
            rs = ps.executeQuery();
            Brand brand = new Brand();
            if (rs.next()) {
                brand.setId(rs.getInt("Brand_ID"));
                brand.setName(rs.getNString("Brand_Name"));
                brand.setLogo(rs.getNString("Brand_Logo"));
                brand.setImgURL(rs.getNString("Brand_Img"));
                brand.setTotalProduct(rs.getInt("Brand_Total_Product"));
            }
            return brand;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateBrand(Brand brand) {
        int result = 0;
        try {
            String sql = "UPDATE Brand\n"
                    + "SET Brand_Name  = ?,\n"
                    + "Brand_Logo = ?,\n"
                    + "Brand_Img = ?\n"
                    + "WHERE Brand_ID  = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, brand.getName());
            ps.setNString(2, brand.getLogo());
            ps.setNString(3, brand.getImgURL());
            ps.setInt(4, brand.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
