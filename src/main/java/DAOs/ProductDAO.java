package DAOs;

import Models.Product;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {

  private Connection conn;

  public ProductDAO() {
    conn = DB.DataManager.getConnection();
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
      sql.append("[Name]");
      sql.append(",[BrandID]");
      sql.append(",[Price]");
      sql.append(",[Gender]");
      sql.append(",[Smell]");
      sql.append(",[Quantity]");
      sql.append(",[ReleaseYear]");
      sql.append(",[Volume]");
      sql.append(",[ImgURL]");
      sql.append(",[Description]");
      sql.append(")");

      sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?)");

      PreparedStatement ps = conn.prepareStatement(sql.toString());

      ps.setNString(1, pd.getName());
      ps.setInt(2, pd.getBrandID());
      ps.setInt(3, pd.getPrice());
      ps.setNString(4, pd.getGender());
      ps.setNString(5, pd.getSmell());
      ps.setInt(6, pd.getQuantity());
      ps.setInt(7, pd.getReleaseYear());
      ps.setInt(8, pd.getVolume());
      ps.setString(9, pd.getImgURL());
      ps.setNString(10, pd.getDescription());

      result = ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  public int addProduct(String data) {
    int result = 0;
    String datas[] = data.split(DB.DataManager.Separator);
    BrandDAO brDAO = new BrandDAO();
    // "NAME~BRANDNAME(string)~PRICE(INT)~Gender(string)~Smell(String)~Quantity(int)~ReleaseYear(smallint)~Volume(INT)~URL(Srtring)~Description",

    // Check if exist brand name
    if (!brDAO.isExistedBrandName(datas[1])) {
      brDAO.addBrand(datas[1]);
    }
    int brandID = brDAO.getBrandID(datas[1]);

    String name = datas[0];
    int price = Integer.parseInt(MoneyToInteger(datas[2]));
    String gender = datas[3];
    String smell = datas[4];
    int quantity = Integer.parseInt(datas[5]);
    int releaseYear = Integer.parseInt(datas[6]);
    int volume = Integer.parseInt(datas[7]);
    String imgURL = datas[8];
    String description = datas[9];

    Product pd = new Product(
      name,
      brandID,
      price,
      gender,
      smell,
      quantity,
      releaseYear,
      volume,
      imgURL,
      description
    );

    result = addProduct(pd);
    return result;
  }

  public void save_Backup_Data() {
    ProductDAO pDAO = new ProductDAO();
    BrandDAO bDAO = new BrandDAO();
    List<Product> listProduct = pDAO.getAll();
    String sp = DB.DataManager.Separator;

    try (
      OutputStream os = new FileOutputStream(
        "C:\\Users\\Acer\\OneDrive\\Desktop\\#SU23\\PRJ301\\SQLproject\\perfume-store\\src\\main\\java\\BackUp\\backup_Product_data.txt"
      );
      PrintWriter outPrint = new PrintWriter(
        new OutputStreamWriter(os, "UTF-8")
      );
    ) {
      StringBuilder strOUT = new StringBuilder("");
      String string = null;

      double currentProgress = 0;
      int lastProgress = 0;

      for (int i = 0; i < listProduct.size(); i++) {
        // "NAME~BRANDNAME(string)~PRICE(INT)~Gender(string)~Smell(String)~Quantity(int)~ReleaseYear(smallint)~Volume(INT)~URL(Srtring)~Description",
        // Open double quote
        strOUT.append("\"");

        Product pd = listProduct.get(i);

        strOUT.append(pd.getName());
        strOUT.append(sp);
        strOUT.append(bDAO.getBrandName(pd.getBrandID()));
        strOUT.append(sp);
        strOUT.append(ProductDAO.IntegerToMoney(pd.getPrice()));
        strOUT.append(sp);
        strOUT.append(pd.getGender());
        strOUT.append(sp);
        strOUT.append(pd.getSmell());
        strOUT.append(sp);
        strOUT.append(pd.getQuantity());
        strOUT.append(sp);
        strOUT.append(pd.getReleaseYear());
        strOUT.append(sp);
        strOUT.append(pd.getVolume());
        strOUT.append(sp);
        strOUT.append(pd.getImgURL());
        strOUT.append(sp);
        strOUT.append(pd.getDescription());

        if (i < listProduct.size() - 1) {
          strOUT.append("\",");
        } else {
          strOUT.append("\"");
        }
        // Close double quote
        string = strOUT.toString();
        // string = string.replace("'", "''");
        string = string.replace("’", "`");
        string = string.replace(" ", " ");
        string = string.replace("\\/", "\\\\/");
        string = string.replace("\u2013", "-");
        string = string.replace(System.getProperty("line.separator"), "");
        outPrint.println(string);
        strOUT.setLength(0);
        // Progress bar
        currentProgress = ((double) (i + 1) / listProduct.size()) * 100;
        if (lastProgress < (int) currentProgress) {
          lastProgress = (int) currentProgress;
          System.out.println("Saving: " + lastProgress + "%");
        }
      }
      System.out.println("Save backup successfully!");
    } catch (IOException io) {
      System.out.println(io.getMessage());
    }
  }

  public String convertToStringData(
    String pName,
    String bName,
    String pPrice,
    String Gender,
    String Smell,
    String Quantity,
    String ReleaseYear,
    String Volume,
    String ImgURL,
    String Description
  ) {
    // "NAME~BRANDNAME(string)~PRICE(INT)~Gender(string)~Smell(String)~Quantity(int)~ReleaseYear(smallint)~Volume(INT)~URL(Srtring)~Description",
    String sep = DB.DataManager.Separator;
    String out =
      pName +
      sep +
      bName +
      sep +
      pPrice +
      sep +
      Gender +
      sep +
      Smell +
      sep +
      Quantity +
      sep +
      ReleaseYear +
      sep +
      Volume +
      sep +
      ImgURL +
      sep +
      Description;
    return out;
  }

  /* --------------------------- READ SECTION --------------------------- */
  public List<Product> getProductByOrderID(int id) {
    ResultSet rs = null;
    String sql =
      "SELECT * FROM  Product, OrderDetail WHERE Product.ID = OrderDetail.ProductID";

    List<Product> pdList = new LinkedList<>();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setInt(1, id);
      rs = ps.executeQuery();

      if (rs.next()) {
        Product pd = null;
        pd = new Product();
        pd.setID(rs.getInt("ID"));
        pd.setName(rs.getNString("Name"));
        pd.setBrandID(rs.getInt("BrandID"));
        pd.setPrice(rs.getInt("Price"));
        pd.setGender(rs.getNString("Gender"));
        pd.setSmell(rs.getNString("Smell"));
        pd.setQuantity(rs.getInt("Quantity"));
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
        pd.setID(rs.getInt("ID"));
        pd.setName(rs.getNString("Name"));
        pd.setBrandID(rs.getInt("BrandID"));
        pd.setPrice(rs.getInt("Price"));
        pd.setGender(rs.getNString("Gender"));
        pd.setSmell(rs.getNString("Smell"));
        pd.setQuantity(rs.getInt("Quantity"));
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
        pd.setID(rs.getInt("ID"));
        pd.setName(rs.getNString("Name"));
        pd.setBrandID(rs.getInt("BrandID"));
        pd.setPrice(rs.getInt("Price"));
        pd.setGender(rs.getNString("Gender"));
        pd.setSmell(rs.getNString("Smell"));
        pd.setQuantity(rs.getInt("Quantity"));
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
        pd.setID(rs.getInt("ID"));
        pd.setName(rs.getNString("Name"));
        pd.setBrandID(rs.getInt("BrandID"));
        pd.setPrice(rs.getInt("Price"));
        pd.setGender(rs.getNString("Gender"));
        pd.setSmell(rs.getNString("Smell"));
        pd.setQuantity(rs.getInt("Quantity"));
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
    String Search
  ) {
    String sql =
      "SELECT \n" +
      "p.ID,\n" +
      "p.[Name],\n" +
      "p.[BrandID],\n" +
      "p.Price,\n" +
      "p.Gender,\n" +
      "p.Quantity,\n" +
      "p.ReleaseYear,\n" +
      "p.Volume,\n" +
      "p.ImgURL,\n" +
      "p.[Description],\n" +
      "p.Active\n" +
      "FROM Product p, Brand b\n" +
      "WHERE BrandID LIKE ?\n" + // 1
      "AND Gender LIKE ?\n" + // 2
      "AND Price between ? AND ?\n" + // 3,4
      "AND Active = 1\n" +
      "AND (p.[Name] LIKE ? OR b.[Name] LIKE ?)\n" + // 5, 6
      "AND p.BrandID = b.ID\n" +
      "ORDER BY p.ID\n" +
      "OFFSET ? ROWS\n" + // 7
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
    String sql =
      "SELECT * FROM Product\n" +
      "ORDER BY ID\n" +
      "OFFSET ? ROWS\n" +
      "FETCH NEXT ? ROWS ONLY";

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
    String sql =
      "SELECT * FROM Product\n" +
      "WHERE ID LIKE ? OR Name LIKE ?\n" +
      "ORDER BY ID\n" +
      "OFFSET ? ROWS\n" +
      "FETCH NEXT ? ROWS ONLY";

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

  public String getPrice(int ID) {
    String price = null;
    String sql = "SELECT Price FROM Product WHERE ID = ? AND Active = 1";
    ResultSet rs = null;

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, ID);
      rs = ps.executeQuery();

      if (rs.next()) {
        price = IntegerToMoney(rs.getInt("Price"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return price;
  }

  public int GetNumberOfProduct(
    String BrandID,
    String Gender,
    String price,
    String Search
  ) {
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

    String sql =
      "SELECT COUNT(*) AS CountRow FROM Product, Brand\n" +
      "WHERE BrandID LIKE ?\n" +
      "AND Gender LIKE ?\n" +
      "AND Price between ? AND ?\n" +
      "AND Active = 1\n" +
      "AND (Product.[Name] LIKE ? OR Brand.[Name] LIKE ?)\n" +
      "AND Product.BrandID = Brand.ID";

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
          high
        )
      );
      if (rs.next()) {
        return rs.getInt("CountRow");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return -1;
  }

  public int GetNumberOfProductForSearch(String Search) {
    ResultSet rs = null;

    String sql =
      "SELECT COUNT(*) AS CountRow FROM Product\n" +
      "WHERE ID LIKE ?\n" +
      "OR Name LIKE ?\n";
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
        pd.setID(rs.getInt("ID"));
        pd.setName(rs.getNString("Name"));
        pd.setBrandID(rs.getInt("BrandID"));
        pd.setPrice(rs.getInt("Price"));
        pd.setGender(rs.getNString("Gender"));
        pd.setSmell(rs.getNString("Smell"));
        pd.setQuantity(rs.getInt("Quantity"));
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

  /* --------------------------- UPDATE SECTION --------------------------- */
  public int updateProduct(int productID, String data) {
    int result = 0;
    String datas[] = data.split(DB.DataManager.Separator);
    BrandDAO brDAO = new BrandDAO();
    // "NAME~BRANDNAME(string)~PRICE(INT)~Gender(string)~Smell(String)~Quantity(int)~ReleaseYear(smallint)~Volume(INT)~URL(Srtring)~Description",

    // Check if exist brand name
    if (!brDAO.isExistedBrandName(datas[1])) {
      brDAO.addBrand(datas[1]);
    }
    int brandID = brDAO.getBrandID(datas[1]);

    String name = datas[0];
    int price = Integer.parseInt(MoneyToInteger(datas[2]));
    String gender = datas[3];
    String smell = datas[4];
    int quantity = Integer.parseInt(datas[5]);
    int releaseYear = Integer.parseInt(datas[6]);
    int volume = Integer.parseInt(datas[7]);
    String imgURL = datas[8];
    String description = datas[9];

    Product pd = new Product(
      productID,
      name,
      brandID,
      price,
      gender,
      smell,
      quantity,
      releaseYear,
      volume,
      imgURL,
      description
    );

    result = updateProduct(pd);
    return result;
  }

  public int updateProduct(Product product) {
    String sql =
      "UPDATE Product SET [Name]=?,\n" +
      " [BrandID]=?, [Price]=?, [Gender]=?,\n" +
      " [Smell]=?, [Quantity]=?,\n" +
      " [ReleaseYear]=?, [Volume]=?,\n" +
      " [ImgURL]=?, \n" +
      " [Description]=?\n" +
      " WHERE ID = ?";
    int result = 0;

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setNString(1, product.getName());
      ps.setInt(2, product.getBrandID());
      ps.setInt(3, product.getPrice());
      ps.setNString(4, product.getGender());
      ps.setNString(5, product.getSmell());
      ps.setInt(6, product.getQuantity());
      ps.setInt(7, product.getReleaseYear());
      ps.setInt(8, product.getVolume());
      ps.setNString(9, product.getImgURL());
      ps.setNString(10, product.getDescription());
      ps.setInt(11, product.getID());
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
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, productId);
      return ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /*--------------------------- CONVERSION SECTION --------------------------- */
  public static String MoneyToInteger(String moneyInput) {
    StringBuilder moneyInt = new StringBuilder(moneyInput);

    for (int i = 0; i < moneyInt.length(); i++) {
      if (moneyInt.charAt(i) == '.') {
        moneyInt.delete(i, i + 1);
      }
    }

    return moneyInt.toString();
  }

  public static String IntegerToMoney(int integer) {
    String out = "";
    String StringDu = "";
    int du;
    while (integer / 1000 != 0) {
      du = integer % 1000;
      integer = integer / 1000;
      StringDu = String.valueOf(du);
      while (StringDu.length() < 3) {
        StringDu = "0" + StringDu;
      }
      out = "." + StringDu + out;
    }
    out = integer + "" + out;
    return out;
  }

  /*--------------------------- FILE SECTION --------------------------- */
  public void copyImg(
    Part imagePart,
    String Local_destination,
    String Target_destination,
    String filename
  ) {
    File IMG = new File(Target_destination);
    if (!IMG.exists()) {
      IMG.mkdir();
    }
    try {
      InputStream imageInputStream = imagePart.getInputStream();
      OutputStream outputStream = new FileOutputStream(
        Local_destination + filename
      );
      OutputStream outputStreamTarget = new FileOutputStream(
        Target_destination + filename
      );
      byte[] buffer = new byte[1024];
      int bytes;
      while ((bytes = imageInputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytes);
        outputStreamTarget.write(buffer, 0, bytes);
      }
      imageInputStream.close();
      outputStream.close();
      outputStreamTarget.close();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
