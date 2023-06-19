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
import Models.Product;

public class ProductDAO {

    private Connection conn;

    public ProductDAO() {
        conn = DB.DataManager.getConnection();
    }

    public ResultSet getAll() {
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

    public ResultSet getWithCondition(String Condition) {
        ResultSet rs = null;
        String sql = "SELECT * FROM Product WHERE " + Condition;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        }
        return rs;
    }

    public String getPrice(int ID) {
        String price = null;
        String sql = "SELECT Price FROM Product WHERE ID = ?";
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

    /*
     * "ID,Name,BrandID,Price,Gender,Smell,Quantity,ReleaseYear,Volume,ImgURL,Description",
     * // Product
     */
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
        // "NAME~BRANDNAME(string)~PRICE(REAL)~Gender(string)~Smell(String)~Quantity(int)~ReleaseYear(smallint)~Volume(INT)~URL(Srtring)~Description",

        //Check if exist brand name
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
                description);

        result = addProduct(pd);
        return result;
    }

    public void save_Backup_Data() {
        ResultSet rs = null;
        String sql = "SELECT * FROM Product";

        try ( OutputStream os = new FileOutputStream(
                "C:\\Users\\Acer\\OneDrive\\Desktop\\#SU23\\PRJ301\\SQLproject\\SQLproject\\src\\main\\java\\BackUp\\backup_Product_data.txt");  PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));) {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            StringBuilder strOUT = new StringBuilder("");
            String string = null;

            Object obj;
            int columnCount = ps.getMetaData().getColumnCount();
            while (rs.next()) {
                strOUT.append("\"");
                for (int i = 1; i <= columnCount; i++) {
                    if (i == 4) {
                        obj = IntegerToMoney((int) rs.getObject(i));
                    }
                    obj = rs.getObject(i) + "";
                    strOUT.append(obj);
                    if (i <= columnCount - 1) {
                        strOUT.append(DataManager.Separator);
                    }
                }
                strOUT.append("\",");
                string = strOUT.toString().replace("'", "''");
                out.println(string);
                strOUT.setLength(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException io) {

        }
    }

}
