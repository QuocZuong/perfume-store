package Lib;

import java.sql.*;

import DAOs.BrandDAO;
import DAOs.ProductDAO;
import DB.DataManager;

public class Test {

  public static void main(String[] args) {
    // DataManager.displayColumns();
    // DataManager.insert("User", "", "Hi", args);
    try {
      ProductDAO pr =new ProductDAO();

      // for (int i = 0; i < BrandData.length; i++) {
      // System.out.println(DataManager.insert("Brand", BrandData[i]));
      // }
      System.out.println("he");
      // for (int i = 0; i < data.length; i++) {
      //   System.out.println(pr.addProduct(data[i]));
      // }

      // System.out.println(DataManager.select("Product",
      // ""));
      // System.out.println(DataManager.delete("Brand", ""));
      // System.out.println(DataManager.delete("Product", ""));

      // ProductDAO pd = new ProductDAO();
      // pd.save_Backup_Data();
      // BrandDAO bd = new BrandDAO();
      // bd.save_Backup_Data();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static String BrandData[] = {
      "AZR~Azzaro",
      "BKI~By Kilian",
      "CHL~chloé",
      "CRD~Creed",
      "DTQ~Diptyque",
      "GIO~Giorgio Armani",
      "LLB~LE LABO",
      "NASO~Nasomatto",
      "TFD~Tom Ford",
      "YSL~Yves Saint Laurent",
      "ZOO~Zoologist"
  };

  static String data[] = {
      "Rosa Nobile~Acqua di Parma~3.600.000~Nữ~Hoa hồng, Hương hoa,~18~2014~50~https://xxivstore.com/wp-content/uploads/2020/07/ROSA-NOBILE-Eau-de-Parfum-1-600x600.png~Như một sự tôn vinh dành cho “nữ hoàng của các loài hoa”, Acqua di Parma đã tạo ra bản eau de parfum Rosa Nobile, một loại nước hoa thanh lịch quý giá tôn vinh sự quyến rũ của nữ tính bẩm sinh. Một niềm vui cho tất cả các giác quan!"
  };
}
