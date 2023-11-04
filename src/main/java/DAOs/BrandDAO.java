package DAOs;

import Exceptions.BrandNotFoundException;
import Exceptions.OperationDeleteBrandFailedCauseOfExistedProduct;
import Exceptions.OperationEditFailedException;
import Interfaces.DAOs.IBrandDAO;
import Models.Brand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    public List<Brand> getAll() {
        ResultSet rs;
        String sql = "SELECT * FROM Brand";
        List<Brand> brandList = new ArrayList<>();

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
    public List<Brand> getBrandNameByAlphabet(char a) {

        ResultSet rs = null;
        List<Brand> brandList = new ArrayList<>();
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
            if (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("Brand_ID"));
                brand.setName(rs.getNString("Brand_Name"));
                brand.setLogo(rs.getNString("Brand_Logo"));
                brand.setImgURL(rs.getNString("Brand_Img"));
                brand.setTotalProduct(rs.getInt("Brand_Total_Product"));
                return brand;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Brand getBrand(String brandName) {
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM Brand WHERE [Brand_Name] = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, brandName);
            rs = ps.executeQuery();
            if (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("Brand_ID"));
                brand.setName(rs.getNString("Brand_Name"));
                brand.setLogo(rs.getNString("Brand_Logo"));
                brand.setImgURL(rs.getNString("Brand_Img"));
                brand.setTotalProduct(rs.getInt("Brand_Total_Product"));
                return brand;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getBrandTotalProduct(int brandId) {
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(Product_ID) as Total_Product FROM [Product]\n"
                    + "WHERE Brand_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, brandId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total_Product");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int updateBrand(Brand brand) throws OperationEditFailedException, BrandNotFoundException {
        if (brand == null || getBrand(brand.getId()) == null) {
            throw new BrandNotFoundException();
        }

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
        if (result < 1) {
            throw new OperationEditFailedException();
        }
        return result;
    }

//    public int deleteBrand(Brand brand) throws OperationEditFailedException, BrandNotFoundException, OperationDeleteBrandFailedCauseOfExistedProduct {
//        if (brand == null || getBrand(brand.getId()) == null) {
//            throw new BrandNotFoundException();
//        }
//        int total = getBrandTotalProduct(brand.getId());
//        System.out.println("Total product: " + total);
//        if (total == -1 || total > 0) {
//            throw new OperationDeleteBrandFailedCauseOfExistedProduct();
//        }
//        int result = 0;
//        try {
//            String sql = "DELETE FROM [Brand]\n"
//                    + "WHERE Brand_ID = ?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, brand.getId());
//            result = ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        if (result < 1) {
//            throw new OperationEditFailedException();
//        }
//        return result;
//    }

    public List<Brand> searchBrand(String search) throws BrandNotFoundException {
        ResultSet rs;
        List<Brand> brandList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Brand\n"
                    + "WHERE Brand_Name LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, search);

            rs = ps.executeQuery();

            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("Brand_ID"));
                brand.setName(rs.getNString("Brand_Name"));

                brandList.add(brand);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (brandList.isEmpty()) {
            throw new BrandNotFoundException();
        }
        return brandList;
    }
}
