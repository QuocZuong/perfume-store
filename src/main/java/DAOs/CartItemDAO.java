/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.DAOs.ICartItemDAO;
import Models.CartItem;
import Models.Product;
import Models.Stock;
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
public class CartItemDAO implements ICartItemDAO {

    private Connection conn;

    public CartItemDAO() {
        conn = DB.DBContext.getConnection();
    }

    // CRUD
    /* ------------------------- CREATE SECTION ---------------------------- */
    @Override
    public int addCartItem(CartItem ci) {
        String sql = "INSERT INTO [CartItem] (Customer_ID , Product_ID, Quantity, Price, Sum) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ci.getCustomerId());
            ps.setInt(2, ci.getProductId());
            ps.setInt(3, ci.getQuantity());
            ps.setInt(4, ci.getPrice());
            ps.setInt(5, ci.getSum());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /* ------------------------- UPDATE SECTION ---------------------------- */
    @Override
    public int updateImportStashItem(CartItem ci) {
        String sql = "UPDATE CartItem SET\n"
                + "Quantity = ?,\n"
                + "Price = ?\n"
                + "Sum = ?\n"
                + "WHERE Customer_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(4, ci.getCustomerId());
            ps.setInt(5, ci.getProductId());
            ps.setInt(1, ci.getQuantity());
            ps.setInt(2, ci.getPrice());
            ps.setInt(3, ci.getSum());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /* ------------------------- DELETE SECTION ---------------------------- */
    @Override
    public int deleteCartItem(CartItem ci) {
        String sql = "DELETE FROM CartItem\n"
                + "WHERE Customer_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ci.getCustomerId());
            ps.setInt(2, ci.getProductId());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int deleteAllCartItemOfCustomer(int customerId) {
        String sql = "DELETE FROM CartItem\n"
                + "WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int deleteAllDeletedProduct() {
        String sql = "DELETE FROM CartItem\n"
                + "Where Product_ID IN (SELECT Product_ID from Product Where Product_Active = 0)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /* ------------------------- READ SECTION ---------------------------- */
    @Override
    public CartItem getCartItem(int customerId, int pdId) {
        ResultSet rs;
        CartItem ci = null;
        String sql = "SELECT * FROM CartItem\n"
                + "WHERE Customer_ID = ? AND Product_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setInt(2, pdId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ci = new CartItem();
                ci.setCustomerId(customerId);
                ci.setProductId(pdId);
                ci.setQuantity(rs.getInt("Quantity"));
                ci.setPrice(rs.getInt("Price"));
                ci.setSum(rs.getInt("Sum"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ci;
    }

    @Override
    public ArrayList<CartItem> getAllCartItemOfCustomer(int customerId) {
        ResultSet rs;
        ArrayList<CartItem> arrCartItem = new ArrayList();
        CartItem ci;
        String sql = "SELECT * FROM CartItem\n"
                + "WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ci = new CartItem();
                ci.setCustomerId(customerId);
                ci.setProductId(rs.getInt("Product_ID"));
                ci.setQuantity(rs.getInt("Quantity"));
                ci.setPrice(rs.getInt("Price"));
                ci.setSum(rs.getInt("Sum"));
                arrCartItem.add(ci);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrCartItem;
    }

    @Override
    public ArrayList<Product> getAllOutOfStockProductFromCart(int customerId) {
        ResultSet rs;
        ArrayList<Product> arrProduct = new ArrayList<>();
        Product pd;
        Stock st;
        try {
            // String sql = "SELECT Cart.ProductID, Cart.Quantity as CartQuantity FROM Cart WHERE ClientID = ?";
            String sql = "SELECT \n"
                    + "		Product.Product_ID,\n"
                    + "		Product.Product_Name,\n"
                    + "		Product.Brand_ID,\n"
                    + "		Product.Product_Gender,\n"
                    + "		Product.Product_Smell,\n"
                    + "		Product.Product_Release_Year,\n"
                    + "		Product.Product_Volume,\n"
                    + "		Product.Product_Img_URL,\n"
                    + "		Product.Product_Description,\n"
                    + "		Product.Product_Active\n"
                    + "		Stock.Price\n"
                    + "		Stock.Quantity\n"
                    + "	FROM CartItem, Product, Stock\n"
                    + "	WHERE \n"
                    + "		CartItem.Customer_ID = 1 AND\n"
                    + "		CartItem.Product_ID = Product.Product_ID AND\n"
                    + "		Product.Product_ID = Stock.Product_ID AND\n"
                    + "		(Stock.Quantity = 0 OR CartItem.Quantity > Stock.Quantity)\n"
                    + "		ORDER BY Product.Brand_ID";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                pd = new Product();
                st = new Stock();
                st.setProductID(rs.getInt("Product_ID"));
                st.setPrice(rs.getInt("Price"));
                st.setQuantity(rs.getInt("Quantity"));
                pd.setId(rs.getInt("Product_ID"));
                pd.setName(rs.getNString("Product_Name"));
                pd.setBrandId(rs.getInt("Brand_ID"));
                pd.setGender(rs.getNString("Product_Gender"));
                pd.setSmell(rs.getNString("Product_Smell"));
                pd.setReleaseYear(rs.getInt("Product_Release_Year"));
                pd.setVolume(rs.getInt("Product_Volume"));
                pd.setImgURL(rs.getNString("Product_Img_URL"));
                pd.setDescription(rs.getNString("Product_Description"));
                pd.setActive(rs.getBoolean("Product_Active"));
                pd.setStock(st);
                arrProduct.add(pd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrProduct;
    }

}
