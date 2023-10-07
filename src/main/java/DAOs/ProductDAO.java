package DAOs;

import Interfaces.DAOs.IProductDAO;
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
import Lib.DatabaseUtils;
import Models.Brand;
import Models.Stock;
import java.util.Collection;
import java.util.stream.Collectors;

public class ProductDAO implements IProductDAO {

    private final Connection conn;
    private final int ROWS = 20;

    public ProductDAO() {
        conn = DB.DataManager.getConnection();
    }

    public enum Table {
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
    @Override
    public int addProduct(Product pd) {
        int result = 0;
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO Product");

            sql.append("(");
            sql.append(Table.Product_Name.toString());
            sql.append(Table.Brand_ID.toString());
            sql.append(Table.Product_Gender.toString());
            sql.append(Table.Product_Smell.toString());
            sql.append(Table.Product_Release_Year.toString());
            sql.append(Table.Product_Volume.toString());
            sql.append(Table.Product_Img_URL.toString());
            sql.append(Table.Product_Description.toString());
            sql.append(")");

            sql.append(" VALUES(?,?,?,?,?,?,?,?)");

            PreparedStatement ps = conn.prepareStatement(sql.toString());

            ps.setNString(1, pd.getName());
            ps.setInt(2, pd.getBrandId());
            ps.setNString(3, pd.getGender());
            ps.setNString(4, pd.getSmell());
            ps.setInt(5, pd.getReleaseYear());
            ps.setInt(6, pd.getVolume());
            ps.setString(7, pd.getImgURL());
            ps.setNString(8, pd.getDescription());

            result = ps.executeUpdate();

            //Only when backup data
            if (pd.getStock() != null) {
                StockDAO stkDAO = new StockDAO();
                int lastID = DatabaseUtils.getLastIndentityOf("Product");
                pd.getStock().setProductID(lastID);
                stkDAO.addStock(pd.getStock());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int addProduct(String data) {
        int result = 0;
        String datas[] = data.split("~");
        BrandDAO brDAO = new BrandDAO();
        // "0         ~1                                  ~2                  ~3                       ~4                     ~5                  ~6                                   ~7                   ~8                   ~9
        // "NAME~BRANDNAME(string)~PRICE(INT)~Gender(string)~Smell(String)~Quantity(int)~ReleaseYear(smallint)~Volume(INT)~URL(Srtring)~Description",

        String name = datas[0];

        // Check if exist brand name
        Brand brand = brDAO.getBrand(datas[1]);
        if (brand == null) {
            brand = new Brand();
            brand.setName(datas[1]);
            brDAO.addBrand(brand);
        }
        int brandId = DatabaseUtils.getLastIndentityOf("Brand");

        int price = Integer.parseInt(datas[2]);
        String gender = datas[3];
        String smell = datas[4];
        int quantity = Integer.parseInt(datas[5]);
        int releaseYear = Integer.parseInt(datas[6]);
        int volume = Integer.parseInt(datas[7]);
        String imgURL = datas[8];
        String description = datas[9];

        Stock stock = new Stock();
        stock.setPrice(price);
        stock.setQuantity(quantity);

        Product pd = new Product();
        pd.setName(name);
        pd.setBrandId(brandId);
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
    @Override
    public List<Product> getAll() {
        ResultSet rs;
        String sql = "SELECT * FROM Product";
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(Table.Product_ID.toString()));
                product.setName(rs.getNString(Table.Product_Name.toString()));
                product.setBrandId(rs.getInt(Table.Brand_ID.toString()));
                product.setGender(rs.getNString(Table.Product_Smell.toString()));
                product.setReleaseYear(rs.getInt(Table.Product_Release_Year.toString()));
                product.setVolume(rs.getInt(Table.Product_Volume.toString()));
                product.setImgURL(rs.getNString(Table.Product_Img_URL.toString()));
                product.setDescription(rs.getNString(Table.Product_Description.toString()));
                product.setActive(rs.getBoolean(Table.Product_Active.toString()));

                productList.add(product);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }

    @Override
    public Product getProduct(int productId) {
        ResultSet rs;
        String sql = "SELECT * FROM Product WHERE Product_ID  = ?";

        Product product = null;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt(Table.Product_ID.toString()));
                product.setName(rs.getNString(Table.Product_Name.toString()));
                product.setBrandId(rs.getInt(Table.Brand_ID.toString()));
                product.setGender(rs.getNString(Table.Product_Smell.toString()));
                product.setReleaseYear(rs.getInt(Table.Product_Release_Year.toString()));
                product.setVolume(rs.getInt(Table.Product_Volume.toString()));
                product.setImgURL(rs.getNString(Table.Product_Img_URL.toString()));
                product.setDescription(rs.getNString(Table.Product_Description.toString()));
                product.setActive(rs.getBoolean(Table.Product_Active.toString()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return product;
    }

    @Override
    public Product getActiveProduct(int productId) {
        Product product = getProduct(productId);
        return product.isActive() ? product : null;
    }

    // TODO: Refactor later because lack of model Order
    @Override
    public List<Product> getProductByOrderID(int id) {
        ResultSet rs = null;
        String sql = "SELECT * FROM  Product, OrderDetail WHERE Product.ID = OrderDetail.ProductID";

        List<Product> pdList = new LinkedList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Product pd;
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
    /* --------------------------- FILTER SECTION --------------------------- */
    @Override
    public List<Product> searchProduct(String search) {
        ResultSet rs;
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT \n"
                    + "p.Product_ID,\n"
                    + "p.Product_Name,\n"
                    + "p.Brand_ID as Product_Brand_ID,\n"
                    + "stk.Price,\n"
                    + "p.Product_Gender,\n"
                    + "stk.Quantity,\n"
                    + "p.Product_Release_Year,\n"
                    + "p.Product_Volume,\n"
                    + "p.Product_Smell,\n"
                    + "p.Product_Img_URL,\n"
                    + "p.Product_Description,\n"
                    + "p.Product_Active\n"
                    + "FROM Product p, Brand b, Stock stk\n"
                    + "WHERE p.Brand_ID = b.Brand_ID\n"
                    + "AND stk.Product_ID = p.Product_ID\n"
                    + "AND (p.[Product_Name] LIKE ? OR b.Brand_Name LIKE ?)\n"
                    + "ORDER BY p.Product_ID";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, search);
            ps.setNString(2, search);

            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(Table.Product_ID.toString()));
                product.setName(rs.getNString(Table.Product_Name.toString()));
                product.setBrandId(rs.getInt(Table.Brand_ID.toString()));
                product.setGender(rs.getNString(Table.Product_Smell.toString()));
                product.setReleaseYear(rs.getInt(Table.Product_Release_Year.toString()));
                product.setVolume(rs.getInt(Table.Product_Volume.toString()));
                product.setImgURL(rs.getNString(Table.Product_Img_URL.toString()));
                product.setDescription(rs.getNString(Table.Product_Description.toString()));
                product.setActive(rs.getBoolean(Table.Product_Active.toString()));

                Stock stock = new Stock();
                stock.setProductID(rs.getInt(Table.Product_ID.toString()));
                stock.setPrice(rs.getInt("Price"));
                stock.setQuantity(rs.getInt("Quantity"));

                product.setStock(stock);
                productList.add(product);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }

    @Override
    public List<Product> pagingProduct(List<Product> productList, int page) {
        final int OFFSET = ROWS * (page - 1);
        List<Product> productSubList = productList.subList(OFFSET, ROWS);
        return productSubList;
    }

    @Override
    public List<Product> filterProduct(
            List<Product> productList,
            Integer brandId,
            String gender,
            String price) {
        final String GENDER = gender;
        // Format price range
        final int LOW = (price == null) ? 0 : Integer.parseInt(price.split("-")[0]);
        final int HIGH = (price == null) ? 100000000 : Integer.parseInt(price.split("-")[1]);

        productList.stream()
                .filter(product
                        -> (brandId == null || product.getBrandId() == brandId)
                && (GENDER == null || product.getGender().equals(GENDER))
                && product.getStock().getPrice() >= LOW && product.getStock().getPrice() <= HIGH)
                .collect(Collectors.toList());
        return productList;
    }

    @Override
    public List<Product> filterActiveProduct(
            List<Product> productList,
            Integer brandId,
            String gender,
            String price) {
        List<Product> filteredProduct = filterProduct(productList, brandId, gender, price).stream()
                .filter(product -> product.isActive())
                .collect(Collectors.toList());
        return filteredProduct;
    }

    @Override
    public List<Product> filterProductByBrand(Brand brand) {
        List<Product> productList = getAll().stream()
                .filter(product -> ((Product) product).getBrandId() == brand.getId())
                .collect(Collectors.toList());
        return productList;
    }

    /* --------------------------- UPDATE SECTION --------------------------- */
    @Override
    public int updateProduct(Product product) {
        String sql = "UPDATE Product\n"
                + "SET Product_Name = ?,\n" //1
                + "Brand_ID = ?,\n" //2
                + "Product_Gender = ?,\n" //3
                + "Product_Smell = ?,\n" //4
                + "Product_Release_Year = ?,\n" //5
                + "Product_Volume = ?,\n" //6
                + "Product_Img_URL = ?,\n" //7
                + "Product_Description = ?,\n" //8
                + "Product_Active = ?\n" //9
                + "WHERE Product_ID = ?"; //10
        int result = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, product.getName());
            ps.setInt(2, product.getBrandId());
            ps.setNString(3, product.getGender());
            ps.setNString(4, product.getSmell());
            ps.setInt(5, product.getReleaseYear());
            ps.setInt(6, product.getVolume());
            ps.setNString(7, product.getImgURL());
            ps.setNString(8, product.getDescription());
            ps.setBoolean(9, product.isActive());
            ps.setInt(10, product.getId());
            result = ps.executeUpdate();

            Stock stock = product.getStock();
            if (stock != null) {
                StockDAO stkDAO = new StockDAO();
                stkDAO.updateStock(stock);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int restoreProduct(Product product) {
        product.setActive(true);
        return updateProduct(product);
    }

    /* --------------------------- DELETE SECTION --------------------------- */
    @Override
    public int disableProduct(Product product) {
        product.setActive(false);
        return updateProduct(product);
    }
}
