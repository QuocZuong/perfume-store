package DB;

import java.sql.*;
import java.util.HashMap;

public class DataManager {

  public static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
  // public static final String URL =
  // PODO:1433;databaseName=projectPRJ;user=sa;password=sa;
  // 35.240.240.157:1433;databaseName=projectPRJ;user=chuongHeo;password=heoQuipdeq;
  // 35.240.240.157:1433;databaseName=projectPRJ;user=chuongHeo;password=heoQuipdeq;
  public static final String SERVER_NAME = "LAPTOP-CSJAMOBT";
  public static final String USER = "sa";
  public static final String PASSWORD = "sa";

  public static final String DB_CONFIG = SERVER_NAME
      + ":1433;databaseName=projectPRJ;user=" + USER + ";password=" + PASSWORD + ";";
  public static final String URL = "jdbc:sqlserver://" + DB_CONFIG + "encrypt=true;trustServerCertificate=true;";

  private static Connection conn;

  // --------------------- Init server and create connection ---------------------
  // \\

  static {
    try {
      Class.forName(DRIVER); // Tao doi tuong
      // Change this url if you want to connect to other server.
      conn = DriverManager.getConnection(URL);
      // ResultSet resultSet = statement.executeQuery(sql);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // --------------------- Init variable --------------------- \\
  public static final String[] DDL_COMMANDS = { "CREATE", "ALTER", "DROP" };
  public static final String[] TABLES = {
      "Product",
      "Brand",
      "User",
      "UserProducts",
      "Admin"
  };
  public static final String[] TABLES_COLUMNS = {
      "Code,Name,BrandCode,Price,Gender,Smell,Quantity,ReleaseYear,Volume,Description", // Product
      "Code,Name", // Brand
      "ID,Name,UserName,Password,PhoneNumber,Email,Address", // User
      "UserID,ProductID,SellQuantity", // UserProducts
      "ID,Name,Password,PhoneNumber" // Admin
  };
  public static final String Separator = "~";

  public static final HashMap<String, String> TableMap = new HashMap<String, String>() {
    {
      for (int i = 0; i < TABLES.length; i++) {
        this.put(TABLES[i], TABLES_COLUMNS[i]);
      }
    }
  };

  public static void displayColumns() {
    for (String key : TableMap.keySet()) {
      System.out.println(key + ": " + TableMap.get(key));
    }
  }

  // --------------------- Data INSERT --------------------- \\
  /**
   * The function inserts data into a specified table in a SQL database.
   *
   * @param table The name of the table where the data will be inserted.
   * @param data  A string containing the data to be inserted into the table.
   *              The data should be in the format of comma-separated values, with
   *              each
   *              value corresponding to a column in the table.
   * @return The method is returning a String that represents an SQL INSERT
   *         statement.
   */
  public static String insert(String table, String data) throws Exception {
    // thuc thi cau lenh truy van va luu ket qua

    StringBuilder sql = new StringBuilder();
    table = table.trim();
    data = data.trim();

    String columns = TableMap.get(table);
    String dataCell[] = data.split(Separator);
    int lenValue = dataCell.length;

    if (columns.substring(0, 2).equals("ID")) {
      columns = columns.substring(3, columns.length());
    }
    String columnsCell[] = columns.split(",");

    if (lenValue != columnsCell.length) {
      throw new Exception("Unequal amount of data cells compared to columns");
    }

    sql.append("INSERT INTO ");
    sql.append(table);
    sql.append(" VALUES ");
    sql.append("(");

    // for (int i = 0; i < columnsCell.length; i++) {
    // sql.append("?");
    // if (i < columnsCell.length) {
    // sql.append(",");
    // }
    // }
    // sql.append(")");

    byte[] isWhatDataType = new byte[lenValue];
    byte isNumber = 0;
    byte isDate = 1;
    byte isString = 2;

    for (int i = 0; i < lenValue; i++) {
      if (columnsCell[i].endsWith("ID")) {
        isWhatDataType[i] = isNumber;
      } else if (columnsCell[i].endsWith("Date")) {
        isWhatDataType[i] = isDate;
      } else if (columnsCell[i].equals("Price")) {
        dataCell[i] = MoneyToInteger(dataCell[i]);
        IntegerToMoney(Integer.parseInt(dataCell[i]));
      } else {
        isWhatDataType[i] = isString;
      }

      if (isWhatDataType[i] == isString) {
        sql.append("N'");
      }
      sql.append(dataCell[i]);
      if (isWhatDataType[i] == isString) {
        sql.append("'");
      }
      if (i < lenValue - 1) {
        sql.append(",");
      }
    }

    sql.append(");");

    // conn.prepareStatement(sql.toString()).executeUpdate();
    conn.createStatement().executeUpdate(sql.toString());
    return sql.toString();
  }

  // --------------------- Data DELETE --------------------- \\
  // DELETE FROM Customers WHERE CustomerName='Mustafa Mbari'AND ;
  public static String delete(String table, String condition)
      throws SQLException {
    StringBuilder sql = new StringBuilder();
    table = table.trim();
    condition = condition.trim();

    sql.append("DELETE FROM ");
    sql.append(table);
    if (!condition.equals("")) {
      sql.append(" WHERE ");
      sql.append(condition);
    }
    sql.append(";");

    conn.prepareStatement(sql.toString()).executeUpdate();
    return sql.toString();
  }

  // --------------------- Data SELECT --------------------- \\
  /**
   * The function generates a SELECT query string for a given table, columns,
   * and condition.
   *
   * @param table     The name of the table from which data is to be selected.
   * @param condition A string representing the condition to be used in the
   *                  SQL query's WHERE clause. It can be an empty string if no
   *                  condition is
   *                  needed.
   * @return The method returns a SQL SELECT query as a String, based on the
   *         input parameters. The query selects specific columns from a specified
   *         table, with an optional condition.
   *
   *         example select(sinhvien, "") return SELECT * FROM sinhvien or
   *         select(sinhvien, "WHERE ABCD", "id", "name") return SELECT id, name
   *         FROM
   *         sinhvien WHERE ABDC
   * @throws SQLException
   */
  public static String select(
      String table,
      String condition,
      String... columns) throws SQLException {

    String OUTQuery = "SELECT";
    String columnsCell[] = columns;

    if (columns.length == 0) {
      OUTQuery += " *";
      columnsCell = TableMap.get(table).split(",");
    } else {
      OUTQuery += " " + columns[0];
      for (int i = 1; i < columns.length; i++) {
        OUTQuery += ", " + columns[i];
      }
    }
    OUTQuery += " FROM " + table;
    if (!condition.equals("")) {
      OUTQuery += " " + condition;
    }

    OUTQuery += ';';

    ResultSet resultSet = conn.prepareStatement(OUTQuery).executeQuery();
    while (resultSet.next()) {

      for (int i = 0; i < columnsCell.length; i++) {
        if (columnsCell[i].substring(0, 1).equals("[")) {
          columnsCell[i] = columnsCell[i].substring(1, columnsCell[i].length() - 1);
        }
        System.out.println(columnsCell[i] + ": "
            + resultSet.getObject(columnsCell[i]));
      }

      System.out.println("--------------------------------------------------");
    }

    return OUTQuery;
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

  // --------------------- Setters and Getters --------------------- \\
  public static Connection getConnection() {
    return conn;
  }

}
