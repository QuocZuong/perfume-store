package DAOs;

import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Exceptions.ProductNotFoundException;
import Models.Brand;
import Models.Stock;

public class ProductDAO {
    
    private Connection conn;
    
    public ProductDAO() {
        conn = DB.DataManager.getConnection();
    }
    
    enum Table {
        Product_ID,
        Product_Name,
        Brand_ID,
        Product_Gender,
        Product_Smell,
        Product_Release_Year,
        Product_Volume,
        Product_Img_URL,
        Product_Description,
        Product_Active
    }

    // CRUD
    /*
   * "ID,Name,BrandID,Price,Gender,Smell,Quantity,ReleaseYear,Volume,ImgURL,Description",
   * // Product
     */
 /* ------------------------- CREATE SECTION ---------------------------- */
    private int addProduct(Product pd) {
        int result = 0;
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO Product");
            
            sql.append("(");
            sql.append("[Product_Name]");
            sql.append(",[Brand_ID]");
            sql.append(",[Product_Gender]");
            sql.append(",[Product_Smell]");
            sql.append(",[Product_Release_Year]");
            sql.append(",[Product_Volume]");
            sql.append(",[Product_Img_URL]");
            sql.append(",[Product_Description]");
            sql.append(")");
            
            sql.append(" VALUES(?,?,?,?,?,?,?,?)");
            
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            
            ps.setNString(1, pd.getName());
            ps.setNString(2, pd.getGender());
            ps.setNString(3, pd.getSmell());
            ps.setInt(4, pd.getReleaseYear());
            ps.setInt(5, pd.getVolume());
            ps.setString(6, pd.getImgURL());
            ps.setNString(7, pd.getDescription());
            
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public int addProduct(String data) {
        int result = 0;
        String datas[] = data.split("~");
        BrandDAO brDAO = new BrandDAO();
        // "0         ~1                                  ~2                  ~3                       ~4   x`                ~5                  ~6                                   ~7                   ~8                   ~9"
        // "NAME~BRANDNAME(string)~PRICE(INT)~Gender(string)~Smell(String)~Quantity(int)~ReleaseYear(smallint)~Volume(INT)~URL(Srtring)~Description",

        String name = datas[0];

        // Check if exist brand name
        Brand brand = brDAO.getBrand(datas[1]);
        if (!brDAO.isExistedBrandName(brand)) {
            brDAO.addBrand(brand);
        }
        int brandID = brDAO.getBrand(datas[1]).getId();
        
        int price = Integer.parseInt(datas[2]);
        String gender = datas[3];
        String smell = datas[4];
        int quantity = Integer.parseInt(datas[5]);
        int releaseYear = Integer.parseInt(datas[6]);
        int volume = Integer.parseInt(datas[7]);
        String imgURL = datas[8];
        String description = datas[9];
        
        Stock stock = new Stock();
        stock.setProductID(price);
        stock.setQuantity(quantity);
        
        Product pd = new Product();
        pd.setName(name);
        pd.setBrandId(brandID);
        pd.setGender(gender);
        pd.setSmell(smell);
        pd.setReleaseYear(releaseYear);
        pd.setVolume(volume);
        pd.setImgURL(imgURL);
        pd.setDescription(description);
        pd.setStock(stock);
        
        result = addProduct(pd);
        return result;
    }

    /* --------------------------- READ SECTION --------------------------- */
    public List<Product> getProductByOrderID(int id) {
        ResultSet rs = null;
        String sql = "SELECT * FROM  Product, OrderDetail WHERE Product.ID = OrderDetail.ProductID";
        
        List<Product> pdList = new LinkedList<>();
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Product pd = null;
                pd = new Product();
                pd.setId(rs.getInt("ID"));
                pd.setName(rs.getNString("Name"));
                pd.setBrandId(rs.getInt("BrandID"));
                pd.setGender(rs.getNString("Gender"));
                pd.setSmell(rs.getNString("Smell"));
                pd.setReleaseYear(rs.getInt("ReleaseYear"));
                pd.setVolume(rs.getInt("Volume"));
                pd.setImgURL(rs.getNString("ImgURL"));
                pd.setDescription(rs.getNString("Description"));
                pdList.add(pd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pdList;
    }
    
    public ResultSet getAllForAdmin() {
        ResultSet rs = null;
        String sql = "SELECT * FROM Product";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public List<Product> getAll() {
        ResultSet rs = null;
        List<Product> listProduct = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product pd = new Product();
                pd.setId(rs.getInt("ID"));
                pd.setName(rs.getNString("Name"));
                pd.setBrandId(rs.getInt("BrandID"));
                pd.setGender(rs.getNString("Gender"));
                pd.setSmell(rs.getNString("Smell"));
                pd.setReleaseYear(rs.getInt("ReleaseYear"));
                pd.setVolume(rs.getInt("Volume"));
                pd.setImgURL(rs.getNString("ImgURL"));
                pd.setDescription(rs.getNString("Description"));
                pd.setActive(rs.getBoolean("Active"));
                listProduct.add(pd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduct;
    }
    
    public Product getProductForAdmin(int id) {
        ResultSet rs = null;
        String sql = "SELECT * FROM Product WHERE ID = ?";
        
        Product pd = null;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pd = new Product();
                pd.setId(rs.getInt("ID"));
                pd.setName(rs.getNString("Name"));
                pd.setBrandId(rs.getInt("BrandID"));
                pd.setGender(rs.getNString("Gender"));
                pd.setSmell(rs.getNString("Smell"));
                pd.setReleaseYear(rs.getInt("ReleaseYear"));
                pd.setVolume(rs.getInt("Volume"));
                pd.setImgURL(rs.getNString("ImgURL"));
                pd.setDescription(rs.getNString("Description"));
                pd.setActive(rs.getBoolean("Active"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pd;
    }
    
    public Product getProduct(int id) {
        ResultSet rs = null;
        String sql = "SELECT * FROM Product WHERE ID = ? And Active = 1";
        
        Product pd = null;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                pd = new Product();
                pd.setId(rs.getInt("ID"));
                pd.setName(rs.getNString("Name"));
                pd.setBrandId(rs.getInt("BrandID"));
                pd.setGender(rs.getNString("Gender"));
                pd.setSmell(rs.getNString("Smell"));
                pd.setReleaseYear(rs.getInt("ReleaseYear"));
                pd.setVolume(rs.getInt("Volume"));
                pd.setImgURL(rs.getNString("ImgURL"));
                pd.setDescription(rs.getNString("Description"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pd;
    }
    
    public ResultSet getFilteredProduct(
            String BrandID,
            String Gender,
            String price,
            int page,
            String Search) {
        String sql = "SELECT \n"
                + "p.ID,\n"
                + "p.[Name],\n"
                + "p.[BrandID],\n"
                + "p.Price,\n"
                + "p.Gender,\n"
                + "p.Quantity,\n"
                + "p.ReleaseYear,\n"
                + "p.Volume,\n"
                + "p.ImgURL,\n"
                + "p.[Description],\n"
                + "p.Active\n"
                + "FROM Product p, Brand b\n"
                + "WHERE BrandID LIKE ?\n"
                + // 1
                "AND Gender LIKE ?\n"
                + // 2
                "AND Price between ? AND ?\n"
                + // 3,4
                "AND Active = 1\n"
                + "AND (p.[Name] LIKE ? OR b.[Name] LIKE ?)\n"
                + // 5, 6
                "AND p.BrandID = b.ID\n"
                + "ORDER BY p.ID\n"
                + "OFFSET ? ROWS\n"
                + // 7
                "FETCH NEXT ? ROWS ONLY"; // 8

        final int ROWS = 20;
        final int OFFSET = ROWS * (page - 1);
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            BrandID = BrandID == null ? "%" : BrandID;
            Gender = Gender == null ? "%" : Gender;
            
            ps.setNString(1, BrandID);
            ps.setNString(2, Gender);

            // Format price range
            String low = "0";
            String high = "100000000";
            if (price != null) {
                String priceRange[] = price.split("-");
                low = priceRange[0];
                high = priceRange[1];
            }
            
            ps.setNString(3, low);
            ps.setNString(4, high);
            ps.setNString(5, "%" + Search + "%");
            ps.setNString(6, "%" + Search + "%");
            ps.setInt(7, OFFSET);
            ps.setInt(8, ROWS);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public ResultSet getFilteredProductForAdmin(int page) {
        String sql = "SELECT * FROM Product\n"
                + "ORDER BY ID\n"
                + "OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY";
        
        final int ROWS = 20;
        final int OFFSET = ROWS * (page - 1);
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, OFFSET);
            ps.setInt(2, ROWS);
            
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public ResultSet getFilteredProductForAdminSearch(int page, String Search) {
        String sql = "SELECT * FROM Product\n"
                + "WHERE ID LIKE ? OR Name LIKE ?\n"
                + "ORDER BY ID\n"
                + "OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY";
        
        final int ROWS = 20;
        final int OFFSET = ROWS * (page - 1);
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Search);
            ps.setNString(2, "%" + Search + "%");
            ps.setInt(3, OFFSET);
            ps.setInt(4, ROWS);
            
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public int GetNumberOfProduct(
            String BrandID,
            String Gender,
            String price,
            String Search) throws ProductNotFoundException {
        BrandID = BrandID == null ? "%" : BrandID;
        Gender = Gender == null ? "%" : Gender;
        String low = "0";
        String high = "100000000";
        
        if (price != null) {
            String priceRange[] = price.split("-");
            low = priceRange[0];
            high = priceRange[1];
        }
        
        ResultSet rs = null;
        
        String sql = "SELECT COUNT(*) AS CountRow FROM Product, Brand\n"
                + "WHERE BrandID LIKE ?\n"
                + "AND Gender LIKE ?\n"
                + "AND Price between ? AND ?\n"
                + "AND Active = 1\n"
                + "AND (Product.[Name] LIKE ? OR Brand.[Name] LIKE ?)\n"
                + "AND Product.BrandID = Brand.ID";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setNString(1, BrandID);
            ps.setNString(2, Gender);
            ps.setNString(3, low);
            ps.setNString(4, high);
            ps.setNString(5, "%" + Search + "%");
            ps.setNString(6, "%" + Search + "%");
            rs = ps.executeQuery();
            System.out.println(
                    String.format(
                            "BrandID: %s, gender: %s, low: %s, high: %s",
                            BrandID,
                            Gender,
                            low,
                            high));
            if (rs.next()) {
                if (rs.getInt("CountRow") == 0) {
                    throw new ProductNotFoundException();
                }
                return rs.getInt("CountRow");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public int GetNumberOfProductForSearch(String Search) {
        ResultSet rs = null;
        
        String sql = "SELECT COUNT(*) AS CountRow FROM Product\n"
                + "WHERE ID LIKE ?\n"
                + "OR Name LIKE ?\n";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, Search);
            ps.setNString(2, "%" + Search + "%");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("CountRow");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public List<Product> getAllProductActive() {
        ResultSet rs = null;
        List<Product> list = new LinkedList<>();
        String sql = "SELECT * FROM Product WHERE Active = 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product pd = new Product();
                pd.setId(rs.getInt("ID"));
                pd.setName(rs.getNString("Name"));
                pd.setBrandId(rs.getInt("BrandID"));
                pd.setGender(rs.getNString("Gender"));
                pd.setSmell(rs.getNString("Smell"));
                pd.setReleaseYear(rs.getInt("ReleaseYear"));
                pd.setVolume(rs.getInt("Volume"));
                pd.setImgURL(rs.getNString("ImgURL"));
                pd.setDescription(rs.getNString("Description"));
                list.add(pd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public int getMaxProductID() {
        String sql = "SELECT MAX(ID) as maxID FROM PRODUCT";
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("maxID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int getProductQuantityByID(int ID) {
        int price = -1;
        String sql = "SELECT Price FROM Product WHERE ID = ? AND Active = 1";
        ResultSet rs = null;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return price;
    }
    
    public List<Product> getProductsByBrandName(int brandID, String brandName) {
        String sql = "SELECT * FROM [projectPRJ].[dbo].[Product] WHERE BrandID = ? AND Active = 1 ORDER BY [Quantity] DESC";
        ResultSet rs = null;
        List<Product> list = new LinkedList<>();
        int count = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, brandID);
            rs = ps.executeQuery();
            while (rs.next() && count < 4) {
                if (!rs.getNString("Name").equals(brandName)) {
                    count++;
                    Product pd = new Product();
                    pd.setId(rs.getInt("ID"));
                    pd.setName(rs.getNString("Name"));
                    pd.setBrandId(rs.getInt("BrandID"));
                    pd.setGender(rs.getNString("Gender"));
                    pd.setSmell(rs.getNString("Smell"));
                    pd.setReleaseYear(rs.getInt("ReleaseYear"));
                    pd.setVolume(rs.getInt("Volume"));
                    pd.setImgURL(rs.getNString("ImgURL"));
                    pd.setDescription(rs.getNString("Description"));
                    pd.setActive(rs.getBoolean("Active"));
                    list.add(pd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /* --------------------------- UPDATE SECTION --------------------------- */
    public int updateProduct(int productID, String data) {
        int result = 0;
        String datas[] = data.split("~");
        BrandDAO brDAO = new BrandDAO();
        // "NAME~BRANDNAME(string)~PRICE(INT)~Gender(string)~Smell(String)~Quantity(int)~ReleaseYear(smallint)~Volume(INT)~URL(Srtring)~Description",

        String name = datas[0];
        // Check if exist brand name
        Brand brand = brDAO.getBrand(datas[1]);
        if (!brDAO.isExistedBrandName(brand)) {
            brDAO.addBrand(brand);
            
        }
        int brandID = brDAO.getBrand(brand.getName()).getId();
        int price = Integer.parseInt(datas[2]);
        String gender = datas[3];
        String smell = datas[4];
        int quantity = Integer.parseInt(datas[5]);
        int releaseYear = Integer.parseInt(datas[6]);
        int volume = Integer.parseInt(datas[7]);
        String imgURL = datas[8];
        String description = datas[9];
        
        Stock stock = new Stock();
        stock.setProductID(productID);
        stock.setPrice(price);
        stock.setQuantity(quantity);
        
        Product pd = new Product();
        pd.setId(productID);
        pd.setName(name);
        pd.setBrandId(brandID);
        pd.setGender(gender);
        pd.setSmell(smell);
        pd.setReleaseYear(releaseYear);
        pd.setVolume(volume);
        pd.setImgURL(imgURL);
        pd.setDescription(description);
        
        result = updateProduct(pd);
        return result;
    }
    
    public int updateProduct(Product product) {
        String sql = "UPDATE Product SET [Name]=?,\n"
                + " [BrandID]=?, [Price]=?, [Gender]=?,\n"
                + " [Smell]=?, [Quantity]=?,\n"
                + " [ReleaseYear]=?, [Volume]=?,\n"
                + " [ImgURL]=?, \n"
                + " [Description]=?\n"
                + " WHERE ID = ?";
        int result = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, product.getName());
            ps.setNString(4, product.getGender());
            ps.setNString(5, product.getSmell());
            ps.setInt(7, product.getReleaseYear());
            ps.setInt(8, product.getVolume());
            ps.setNString(9, product.getImgURL());
            ps.setNString(10, product.getDescription());
            ps.setInt(11, product.getId());
            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public int restoreProduct(int ProductID) {
        String sql = "UPDATE Product SET Active = 1 WHERE ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ProductID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* --------------------------- DELETE SECTION --------------------------- */
    public int deleteProduct(int productId) {
        String sql = "UPDATE Product SET Active = 0 WHERE ID = ?";
        int kq = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);

            // Delete all product in Cart too
            kq = ps.executeUpdate();
            CartDAO cDAO = new CartDAO();
            cDAO.deleteAllDeletedProduct();
            return kq;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
