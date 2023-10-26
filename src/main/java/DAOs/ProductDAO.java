package DAOs;

import Exceptions.InvalidInputException;
import Exceptions.ProductNotFoundException;
import Interfaces.DAOs.IProductDAO;
import Lib.Converter;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Lib.DatabaseUtils;
import Lib.Generator;
import Models.Admin;
import Models.Brand;
import Models.Stock;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ProductDAO implements IProductDAO {

    private final Connection conn;

    public ProductDAO() {
        conn = DB.DBContext.getConnection();
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

    enum operation {
        GET, SEARCH
    }

    public Product productFactory(ResultSet rs, operation o) throws SQLException, NullPointerException {
        if (rs == null) {
            throw new NullPointerException("ResultSet is null");
        }

        Product product = new Product();

        switch (o) {
            case GET:
                product.setId(rs.getInt(Table.Product_ID.toString()));
                product.setName(rs.getNString(Table.Product_Name.toString()));
                product.setBrandId(rs.getInt(Table.Brand_ID.toString()));
                product.setGender(rs.getNString(Table.Product_Gender.toString()));
                product.setSmell(rs.getNString(Table.Product_Smell.toString()));
                product.setReleaseYear(rs.getInt(Table.Product_Release_Year.toString()));
                product.setVolume(rs.getInt(Table.Product_Volume.toString()));
                product.setImgURL(rs.getNString(Table.Product_Img_URL.toString()));
                product.setDescription(rs.getNString(Table.Product_Description.toString()));
                product.setActive(rs.getBoolean(Table.Product_Active.toString()));

                return product;

            case SEARCH:
                product.setId(rs.getInt(Table.Product_ID.toString()));
                product.setName(rs.getNString(Table.Product_Name.toString()));
                product.setBrandId(rs.getInt("Product_Brand_ID"));
                product.setGender(rs.getNString(Table.Product_Gender.toString()));
                product.setReleaseYear(rs.getInt(Table.Product_Release_Year.toString()));
                product.setVolume(rs.getInt(Table.Product_Volume.toString()));
                product.setSmell(rs.getNString(Table.Product_Smell.toString()));
                product.setImgURL(rs.getNString(Table.Product_Img_URL.toString()));
                product.setDescription(rs.getNString(Table.Product_Description.toString()));
                product.setActive(rs.getBoolean(Table.Product_Active.toString()));

                return product;
        }

        return null;
    }

    // CRUD
    /*
     * "ID,Name,BrandID,Price,Gender,Smell,Quantity,ReleaseYear,Volume,ImgURL,Description",
     * // Product
     */
 /* ------------------------- CREATE SECTION ---------------------------- */
    @Override
    public int addProduct(Product pd, Admin admin) throws InvalidInputException {
        validateProduct(pd);

        int result = 0;
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO Product");

            sql.append("(");
            sql.append(Table.Product_Name.toString()).append(",");
            sql.append(Table.Brand_ID.toString()).append(",");
            sql.append(Table.Product_Gender.toString()).append(",");
            sql.append(Table.Product_Smell.toString()).append(",");
            sql.append(Table.Product_Release_Year.toString()).append(",");
            sql.append(Table.Product_Volume.toString()).append(",");
            sql.append(Table.Product_Img_URL.toString()).append(",");
            sql.append(Table.Product_Description.toString());
            sql.append(")");
            System.out.println(sql);

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

            // Only when backup data
            int lastID = DatabaseUtils.getLastIndentityOf("Product");
            pd.setId(lastID);
            if (pd.getStock() != null) {
                StockDAO stkDAO = new StockDAO();
                pd.getStock().setProductID(lastID);
                stkDAO.addStock(pd.getStock());
            }
            // Track admin
            if (admin != null) {
                ProductActivityLogDAO palDAO = new ProductActivityLogDAO();
                palDAO.addProductActivityLog(ProductActivityLogDAO.Operation.Add, pd, admin, "Add " + pd.getName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int addProduct(String data, Admin admin) throws InvalidInputException {
        int result = 0;
        String datas[] = data.split("~");
        BrandDAO brDAO = new BrandDAO();
        // "0 ~1 ~2 ~3 ~4 ~5 ~6 ~7 ~8 ~9
        // "NAME~BRANDNAME(string)~PRICE(INT)~Gender(string)~Smell(String)~Quantity(int)~ReleaseYear(smallint)~Volume(INT)~URL(Srtring)~Description",

        String name = datas[0];

        // Check if exist brand name
        if (brDAO.getBrand(datas[1]) == null) {
            Brand brand = new Brand();
            brand.setName(datas[1]);
            brDAO.addBrand(brand);
        }
        int brandId = brDAO.getBrand(datas[1]).getId();

        int price = Integer.parseInt(Converter.convertMoneyToInteger(datas[2]));
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

        result = addProduct(pd, admin);
        return result;
    }

    /* --------------------------- READ SECTION --------------------------- */
    @Override
    public List<Product> getAll() {
        ResultSet rs;
        String sql = "SELECT * FROM Product p\n"
                + "JOIN Stock stk ON stk.Product_ID = p.Product_ID";
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = productFactory(rs, operation.GET);

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

    public List<Product> getAllSimplified() {
        ResultSet rs;
        String sql = "SELECT * FROM Product p\n"
                + "JOIN Stock stk ON stk.Product_ID = p.Product_ID";
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = productFactory(rs, operation.GET);

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
    public Product getProduct(int productId) throws ProductNotFoundException {
        ResultSet rs;
        String sql = "SELECT * FROM Product p\n"
                + "JOIN Stock stk ON stk.Product_ID = p.Product_ID\n"
                + "WHERE p.Product_ID  = ?";

        Product product = null;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                product = productFactory(rs, operation.GET);

                Stock stock = new Stock();
                stock.setProductID(rs.getInt(Table.Product_ID.toString()));
                stock.setPrice(rs.getInt("Price"));
                stock.setQuantity(rs.getInt("Quantity"));

                product.setStock(stock);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (product == null) {
            throw new ProductNotFoundException();
        }
        return product;
    }

    @Override
    public Product getActiveProduct(int productId) throws ProductNotFoundException {
        Product product = getProduct(productId);
        if (!product.isActive()) {
            throw new ProductNotFoundException();
        }
        return product;
    }

    public static boolean isContain(Product p, List<Product> arrP) {
        if (arrP != null && !arrP.isEmpty() && p != null) {
            for (int i = 0; i < arrP.size(); i++) {
                if (arrP.get(i).getId() == p.getId()) {
                    return true;
                }
            }
        }
        return false;
    }


    /* --------------------------- FILTER SECTION --------------------------- */
    @Override
    public List<Product> searchProduct(String search) throws ProductNotFoundException {
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
                Product product = productFactory(rs, operation.SEARCH);

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
        if (productList.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return productList;
    }

    @Override
    public List<Product> filterProduct(
            List<Product> productList,
            int brandId,
            String gender,
            String price)
            throws ProductNotFoundException {
        final String GENDER = gender;
        // Format price range
        final int LOW = (price == null) ? 0 : Integer.parseInt(price.split("-")[0]);
        final int HIGH = (price == null) ? 100000000 : Integer.parseInt(price.split("-")[1]);

        List<Product> filteredProduct = productList.stream()
                .filter(product -> (brandId == -1 || product.getBrandId() == brandId)
                && (GENDER == null || product.getGender().equals(GENDER))
                && product.getStock().getPrice() >= LOW && product.getStock().getPrice() <= HIGH)
                .collect(Collectors.toList());

        if (productList.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return filteredProduct;
    }

    @Override
    public List<Product> filterActiveProduct(
            List<Product> productList,
            int brandId,
            String gender,
            String price)
            throws ProductNotFoundException {
        List<Product> filteredProductList = filterProduct(productList, brandId, gender, price);
        List<Product> filteredActiveProductList = filteredProductList.stream()
                .filter(product -> product.isActive())
                .collect(Collectors.toList());

        if (productList.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return filteredActiveProductList;
    }

    @Override
    public List<Product> filterProductByBrand(Brand brand) throws ProductNotFoundException {
        List<Product> productList = getAll().stream()
                .filter(product -> product.getBrandId() == brand.getId())
                .collect(Collectors.toList());

        if (productList.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return productList;
    }

    /* --------------------------- UPDATE SECTION --------------------------- */
    @Override
    public int updateProduct(Product product, Admin admin) throws InvalidInputException {
        validateProduct(product);

        String sql = "UPDATE Product\n"
                + "SET Product_Name = ?,\n" // 1
                + "Brand_ID = ?,\n" // 2
                + "Product_Gender = ?,\n" // 3
                + "Product_Smell = ?,\n" // 4
                + "Product_Release_Year = ?,\n" // 5
                + "Product_Volume = ?,\n" // 6
                + "Product_Img_URL = ?,\n" // 7
                + "Product_Description = ?,\n" // 8
                + "Product_Active = ?\n" // 9
                + "WHERE Product_ID = ?"; // 10

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

            String description = detectChange(product);
            result = ps.executeUpdate();

            Stock stock = product.getStock();
            if (stock != null) {
                StockDAO stkDAO = new StockDAO();
                stkDAO.updateStock(stock);
            }

            // Track admin when update all filed
            if (admin != null) {
                ProductActivityLogDAO palDAO = new ProductActivityLogDAO();
                palDAO.addProductActivityLog(ProductActivityLogDAO.Operation.Update, product, admin, description);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int restoreProduct(Product product, Admin admin) throws ProductNotFoundException, InvalidInputException {
        if (product == null) {
            throw new ProductNotFoundException();
        }
        product.setActive(true);
        return updateProduct(product, admin);
    }

    /* --------------------------- DELETE SECTION --------------------------- */
    @Override
    public int disableProduct(Product product, Admin admin) throws ProductNotFoundException, InvalidInputException {
        if (product == null) {
            throw new ProductNotFoundException();
        }
        product.setActive(false);
        return updateProduct(product, admin);
    }

    /* --------------------------- OTHER SECTION --------------------------- */
    public boolean validateProduct(Product product) throws InvalidInputException {

        Stock stock = product.getStock();
        if (stock == null) {
            System.out.println("Stock is null");
            throw new InvalidInputException();
        }
        if (stock.getPrice() < 0 || stock.getQuantity() < 0) {
            throw new InvalidInputException();
        }

        return true;
    }

    public String detectChange(Product updateProduct) throws ProductNotFoundException {
        Product oldProduct = getProduct(updateProduct.getId());

        if (oldProduct == null) {
            return "Product not found.";
        }

        StringBuilder strBuilder = new StringBuilder("Changes detected:\n");

        if (!oldProduct.getName().equals(updateProduct.getName())) {
            strBuilder.append("Name : \"").append(oldProduct.getName())
                    .append("\"=>\"").append(updateProduct.getName()).append("\"\n");
        }

        if (oldProduct.getBrandId() != updateProduct.getBrandId()) {
            BrandDAO bDAO = new BrandDAO();
            String oldBrand = bDAO.getBrand(oldProduct.getBrandId()).getName();
            String newBrand = bDAO.getBrand(updateProduct.getBrandId()).getName();

            strBuilder.append("Brand Name : \"").append(oldBrand)
                    .append("\"=>\"").append(newBrand).append("\"\n");
        }

        if (!oldProduct.getGender().equals(updateProduct.getGender())) {
            strBuilder.append("Gender : \"").append(oldProduct.getGender())
                    .append("\"=>\"").append(updateProduct.getGender()).append("\"\n");
        }

        if (!oldProduct.getSmell().equals(updateProduct.getSmell())) {
            strBuilder.append("Smell : \"").append(oldProduct.getSmell())
                    .append("\"=>\"").append(updateProduct.getSmell()).append("\"\n");
        }

        if (oldProduct.getReleaseYear() != updateProduct.getReleaseYear()) {
            strBuilder.append("Release Year : \"").append(oldProduct.getReleaseYear())
                    .append("\"=>\"").append(updateProduct.getReleaseYear()).append("\"\n");
        }

        if (oldProduct.getVolume() != updateProduct.getVolume()) {
            strBuilder.append("Volume : \"").append(oldProduct.getVolume())
                    .append("\"=>\"").append(updateProduct.getVolume()).append("\"\n");
        }

        if (!oldProduct.getImgURL().equals(updateProduct.getImgURL())) {
            strBuilder.append("Image URL : \"").append(oldProduct.getImgURL())
                    .append("\"=>\"").append(updateProduct.getImgURL()).append("\"\n");
        }

        if (!oldProduct.getDescription().equals(updateProduct.getDescription())) {
            strBuilder.append("Description : \"").append(oldProduct.getDescription())
                    .append("\"=>\"").append(updateProduct.getDescription()).append("\"\n");
        }

        if (oldProduct.isActive() != updateProduct.isActive()) {
            strBuilder.append("Active status : \"").append(oldProduct.isActive())
                    .append("\"=>\"").append(updateProduct.isActive()).append("\"\n");
        }

        if (oldProduct.getStock().getPrice() != updateProduct.getStock().getPrice()) {
            strBuilder.append("Price : \"").append(oldProduct.getStock().getPrice())
                    .append("\"=>\"").append(updateProduct.getStock().getPrice()).append("\"\n");
        }

        if (oldProduct.getStock().getQuantity() != updateProduct.getStock().getQuantity()) {
            strBuilder.append("Quantity : \"").append(oldProduct.getStock().getQuantity())
                    .append("\"=>\"").append(updateProduct.getStock().getQuantity()).append("\"\n");
        }

        return strBuilder.toString();
    }

}
