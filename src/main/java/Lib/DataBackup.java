/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;

import DAOs.BrandDAO;
import DAOs.ProductDAO;
import DB.DataManager;
import Models.Product;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class DataBackup {

    public void backupBrandData() {

        Connection conn = DataManager.getConnection();

        ResultSet rs = null;
        String sql = "SELECT * FROM Brand";

        try ( OutputStream os = new FileOutputStream(
                "C:\\Users\\Acer\\OneDrive\\Desktop\\#SU23\\PRJ301\\SQLproject\\SQLproject\\src\\main\\java\\BackUp\\backUp_Brand_data.txt");  PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));) {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            StringBuilder strOUT = new StringBuilder("");

            Object obj;
            int columnCount = ps.getMetaData().getColumnCount();
            while (rs.next()) {
                strOUT.append("\"");
                for (int i = 1; i <= columnCount; i++) {
                    obj = rs.getObject(i) + "";
                    strOUT.append(obj);
                    if (i <= columnCount - 1) {
                        strOUT.append("~");
                    }
                }
                strOUT.append("\",");
                out.println(strOUT);
                strOUT.setLength(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException io) {

        }
    }

    public static void backupProductData() {
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDAO = new BrandDAO();
        List<Product> listProduct = pDAO.getAll();
        String sp = "~";

        try (
                 OutputStream os = new FileOutputStream(
                        "C:\\Users\\Acer\\OneDrive\\Desktop\\#SU23\\PRJ301\\SQLproject\\perfume-store\\src\\main\\java\\BackUp\\backup_Product_data.txt");  PrintWriter outPrint = new PrintWriter(
                        new OutputStreamWriter(os, "UTF-8"));) {
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
                strOUT.append(bDAO.getBrandName(pd.getBrandId()));
                strOUT.append(sp);
                strOUT.append(sp);
                strOUT.append(pd.getGender());
                strOUT.append(sp);
                strOUT.append(pd.getSmell());
                strOUT.append(sp);
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
}
