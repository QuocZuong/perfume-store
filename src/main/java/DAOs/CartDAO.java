package DAOs;

import Models.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartDAO {

    private Connection conn;

    /**
     * Constructs a new {@code CartDAO} object.
     */
    public CartDAO() {
        conn = DB.DataManager.getConnection();
    }

    /**
     * Adds a new cart item to the database.
     *
     * @param cart The cart item to be added.
     * @return The number of rows affected by the query. {@code 0} if the cart item
     *         cannot be added.
     */
    public int addToCart(int ClientID, int ProductID, int Quantity) {
        String sql = "exec INSERT_TO_CART ?, ?, ?";
        int currentQuantity = getCartQuantity(ClientID, ProductID);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ClientID);
            ps.setInt(2, ProductID);
            ps.setInt(3, currentQuantity + Quantity);

            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getCartQuantity(int ClientID, int ProductID) {
        String sql = "SELECT * FROM Cart WHERE ClientID = ? AND ProductID = ?";
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ClientID);
            ps.setInt(2, ProductID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Quantity");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Deletes a cart item from the database.
     *
     * @param clientID  The client ID of the cart item to be deleted.
     * @param productID The product ID of the cart item to be deleted.
     * @return The number of rows affected by the query. {@code 0} if the cart item
     *         cannot be deleted, or the cart item doesn't exist.
     */
    public int deleteCart(int clientID, int productID) {
        String sql = "DELETE FROM Cart WHERE ClientID = ? AND ProductID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientID);
            ps.setInt(2, productID);

            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Retrieves all cart items from the database for a given client ID.
     *
     * @param clientID The client ID for which to retrieve cart items.
     * @return A list of {@code Cart} objects representing the cart items. An empty
     *         list if no cart items are found or an error occurs.
     */
    public List<Cart> getAllClientProduct(int clientID) {
        List<Cart> cartList = new ArrayList<>();

        String sql = "SELECT * FROM Cart WHERE ClientID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cart cart = new Cart();
                cart.setClientID(rs.getInt("ClientID"));
                cart.setProductID(rs.getInt("ProductID"));
                cart.setQuantity(rs.getInt("Quantity"));
                cart.setPrice(rs.getInt("Price"));
                cart.setSum(rs.getInt("Sum"));

                cartList.add(cart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cartList;
    }

    /**
     * Changes the quantity of a product in the cart.
     *
     * @param clientID  The client ID of the cart item.
     * @param productID The product ID of the cart item.
     * @param quantity  The new quantity of the product.
     * @return The number of rows affected by the query. {@code 0} if the quantity
     *         cannot be changed, or the cart item doesn't exist.
     */
    public int changeProductQuantity(int clientID, int productID, int quantity) {
        String sql = "exec INSERT_TO_CART ?, ?, ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientID);
            ps.setInt(2, productID);
            ps.setInt(3, quantity);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getCartTotal(int ClientID) {
        String sql = "SELECT SUM([Sum]) as Total FROM Cart WHERE ClientID = ?";
        ResultSet rs = null;
        int total = -1;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ClientID);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public ArrayList<int[]> getAllCartProductQuantity(int ClientID) {
        ResultSet rs = null;
        ArrayList<int[]> out = new ArrayList<>();
        String sql = "SELECT Cart.ProductID, Cart.Quantity FROM Cart WHERE ClientID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ClientID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int cell[] = new int[2];
                int ProductID = rs.getInt("ProductID");
                int Quantity = rs.getInt("Quantity");
                cell[0] = ProductID;
                cell[1] = Quantity;
                out.add(cell);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

}
