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

    public String getPrice(String code) {
        String price = null;
        String sql = "SELECT Price FROM Product WHERE Code = ?";
        ResultSet rs = null;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, code);
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
     * "Code,Name,BrandCode,Price,Gender,Smell,Quantity,ReleaseYear,Volume,Description",
     * // Product
     */
    public int addProduct(Product pd) {
        int result = 0;
        try {
            String sql = "INSERT INTO Product VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, pd.getCode());
            ps.setNString(2, pd.getName());
            ps.setNString(3, pd.getBrandCode());
            ps.setInt(4, pd.getPrice());
            ps.setNString(5, pd.getGender());
            ps.setNString(6, pd.getSmell());
            ps.setInt(7, pd.getQuantity());
            ps.setInt(8, pd.getRelease_Year());
            ps.setInt(9, pd.getVolume());
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
        Product pd = new Product(
                datas[0],
                datas[1],
                datas[2],
                Integer.parseInt(MoneyToInteger(datas[3])),
                datas[4],
                datas[5],
                Integer.parseInt(datas[6]),
                Integer.parseInt(datas[7]),
                Integer.parseInt(datas[8]),
                datas[9]);
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
