package DAOs;

import Models.User;
import Lib.PasswordGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.login.AccountNotFoundException;

import Exceptions.AccountDeactivatedException;
import Exceptions.EmailDuplicationException;
import Exceptions.WrongPasswordException;
import Lib.EmailSender;
import java.io.UnsupportedEncodingException;

public class UserDAO {

  private Connection conn;

  /**
   * Constructs a new {@code UserDAO} object.
   */
  public UserDAO() {
    conn = DB.DataManager.getConnection();
  }

  /* --------------------- CREATE SECTION --------------------- */
  /**
   * Gets all the users in the database.
   *
   * @return A {@code ResultSet} containing all the users in the database.
   *         {@code null} if an error occurs.
   */
  public ResultSet getAll() {
    ResultSet rs = null;

    String sql = "SELECT * FROM [User]";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return rs;
  }

  /**
   * Gets the MD5 hash of a string.
   *
   * @param str The string to be hashed.
   * @return The MD5 hash of the string. {@code null} if an error occurs while
   *         hashing.
   */
  public String getMD5hash(String str) {

    MessageDigest md = null;
    String pwdMD5 = null;

    try {
      md = MessageDigest.getInstance("MD5");

    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    }

    md.update(str.getBytes());
    byte[] digest = md.digest();
    StringBuilder sb = new StringBuilder();
    for (byte b : digest) {
      sb.append(String.format("%02x", b & 0xff));
    }
    pwdMD5 = sb.toString();

    return pwdMD5;
  }

  /* --------------------- READ SECTION --------------------- */
  /**
   * Gets a user from the database.
   *
   * @param username The username of the user to be retrieved.
   * @return A {@code User} object containing the user's information.
   *         {@code null} if an error occurs.
   */
  public User getUser(String username) {
    if (username == null) {
      return null;
    }

    ResultSet rs = null;

    String sql = "SELECT * FROM [User] WHERE UserName = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, username);
      rs = ps.executeQuery();
      if (rs.next()) {
        return new User(
            rs.getInt("ID"),
            rs.getNString("Name"),
            rs.getString("UserName"),
            rs.getString("Password"),
            rs.getString("Email"),
            rs.getString("PhoneNumber"),
            rs.getNString("Address"),
            rs.getString("Role"),
            rs.getBoolean("Active"));
      }
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   * Gets a user from the database.
   *
   * @param ID The ID of the user to be retrieved.
   * @return A {@code User} object containing the user's information.
   *         {@code null} if an error occurs or the user is not found.
   */
  public User getUser(int ID) {
    ResultSet rs = null;

    String sql = "SELECT * FROM [User] WHERE ID = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, ID);
      rs = ps.executeQuery();
      if (rs.next()) {
        return new User(
            rs.getInt("ID"),
            rs.getNString("Name"),
            rs.getString("UserName"),
            rs.getString("Password"),
            rs.getString("Email"),
            rs.getString("PhoneNumber"),
            rs.getNString("Address"),
            rs.getString("Role"),
            rs.getBoolean("Active"));
      }
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   * Gets a user from the database.
   *
   * @param email The email of the user to be retrieved.
   * @return A {@code User} object containing the user's information.
   *         {@code null} if an error occurs or the user is not found.
   */
  public User getUserByEmail(String email) {
    if (email == null) {
      return null;
    }

    ResultSet rs = null;

    String sql = "SELECT * FROM [User] WHERE Email = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      
      ps.setString(1, email);
      rs = ps.executeQuery();
      if (rs.next()) {
        return new User(
            rs.getInt("ID"),
            rs.getNString("Name"),
            rs.getString("UserName"),
            rs.getString("Password"),
            rs.getString("Email"),
            rs.getString("PhoneNumber"),
            rs.getNString("Address"),
            rs.getString("Role"),
            rs.getBoolean("Active"));
      }
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  public int getMaxId() {
    ResultSet rs = null;

    String sql = "SELECT MAX(ID) AS MaxID FROM [User]";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      if (rs.next()) {
        return rs.getInt("MaxID");
      }
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return 0;
  }

  public ResultSet getFilteredUserForAdminSearch(int page, String Search) {
    String sql = "SELECT * FROM [User]\n"
        + "WHERE ID LIKE ? OR UserName LIKE ? OR Name LIKE ?\n"
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
      ps.setNString(3, "%" + Search + "%");
      ps.setInt(4, OFFSET);
      ps.setInt(5, ROWS);

      rs = ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return rs;
  }

  public int GetNumberOfUserForSearch(String Search) {

    ResultSet rs = null;

    String sql = "SELECT COUNT(*) AS CountRow FROM [User]\n"
        + "WHERE ID LIKE ?\n"
        + "OR UserName LIKE ?\n";
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

  public ResultSet getAllForAdmin() {
    ResultSet rs = null;
    String sql = "SELECT * FROM [User]";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
    } catch (SQLException ex) {
      Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return rs;
  }

  /* --------------------- UPDATE SECTION --------------------- */
  public int updateUser(User updateUser) {
    if (updateUser == null) {
      return 0;
    }

    String sql = "UPDATE [User] SET Name=?, Username=?, Password=?, Email=?, PhoneNumber=?, Address=?, Role=?\n"
        + "WHERE ID = ?";

    int kq = 0;

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setNString(1, updateUser.getName());
      ps.setNString(2, updateUser.getUsername());
      ps.setNString(3, updateUser.getPassword());
      ps.setNString(4, updateUser.getEmail());
      ps.setNString(5, updateUser.getPhoneNumber());
      ps.setNString(6, updateUser.getAddress());
      ps.setNString(7, updateUser.getRole());
      ps.setInt(8, updateUser.getID());

      kq = ps.executeUpdate();

      return kq;
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return kq;
  }

  public int checkout(Integer ClientID, Date Date, String Address, String PhoneNumber, String Note, Integer Sum) {
    if (ClientID == null || Date == null || Sum == null) {
      return 0;
    }
    /*
     * Attribute:
     * ID (int) (primary key Indentity (1,1))
     * ClientID (ID (int) (duplicate) )
     * Date (Date) not null
     * Address (nvarchar(500))
     * PhoneNumber (varchar(10))
     * Note (nvarchar(500))
     * Sum (int default 0)
     */

    String sql = "INSERT INTO [Order](ClientID, [Date], [Address], [PhoneNumber], [Note], [Sum]) VALUES (?, ?, ?, ?, ?, ?)";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, ClientID);
      ps.setDate(2, Date);
      ps.setNString(3, Address);
      ps.setNString(4, PhoneNumber);
      ps.setNString(5, Note);
      ps.setInt(6, Sum);

      return ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public int restoreUser(int id) {
    if (!isExistId(id)) {
      return 0;
    }

    String sql = "UPDATE [User] SET Active = ? WHERE ID = ?";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setBoolean(1, true);
      ps.setInt(2, id);

      return ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return 0;
  }

  /*--------------------- DELETE SECTION ---------------------  */
  /**
   * Deletes a user from the database.
   *
   * @param us The user to be deleted.
   * @return The number of rows affected by the query. {@code 0} if user
   *         cannot be deleted, or {@code username} doesn't exist.
   */
  public int deleteUser(String username) {
    if (username == null) {
      return 0;
    }

    String sql = "DELETE FROM [User] WHERE UserName = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, username);

      return ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return 0;
  }

  public int deactivateUser(int id) {
    if (!isExistId(id)) {
      return 0;
    }

    String sql = "UPDATE [User] SET Active = ? WHERE ID = ?";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setBoolean(1, false);
      ps.setInt(2, id);

      return ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return 0;
  }

  /*--------------------- VALIDATE SECTION ---------------------  */

  /*--------------------- AUTHORIZATION SECTION ---------------------  */
  /**
   * Checks if a given username is an admin.
   *
   * @param username The username of the user whose role needs to be checked.
   * @return {@code true} if the user is an admin. {@code false} otherwise.
   */
  public boolean isAdmin(String username) {
    if (username == null) {
      return false;
    }

    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE UserName = ? AND Role = 'Admin'";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, username);
      rs = ps.executeQuery();
      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  /**
   * Checks if a given username is a client.
   *
   * @param username The username of the user whose role needs to be checked.
   * @return {@code true} if the user is a client. {@code false} otherwise.
   */
  public boolean isClient(String username) {
    if (username == null) {
      return false;
    }

    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE UserName = ? AND Role = 'Client'";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, username);
      rs = ps.executeQuery();
      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  /*--------------------- AUTHENTICATION SECTION ---------------------  */
  public boolean login(String username, String password)
      throws AccountDeactivatedException, AccountNotFoundException, WrongPasswordException {
    if (username == null || password == null) {
      return false;
    }
    if (username.length() > 50) {
      return false;
    }
    if (getUser(username) == null) {
      throw new AccountNotFoundException();
    }

    if (getUser(username).isActive() == false) {
      System.out.println("account deactivated");
      throw new AccountDeactivatedException();
    }

    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE UserName = ? AND [Password] = ?";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, username);
      ps.setString(2, getMD5hash(password));
      rs = ps.executeQuery();
      if (rs.next()) {
        return true;
      } else {
        throw new WrongPasswordException();
      }
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public boolean isExistUsername(String username) {

    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE UserName = ?";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, username);

      rs = ps.executeQuery();

      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  public boolean isExistUsernameExceptItself(String username, int ID) {
    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE UserName = ? AND ID != ?";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, username);
      ps.setInt(2, ID);
      rs = ps.executeQuery();

      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  public boolean isExistId(int ID) {
    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE ID = ?";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setInt(1, ID);

      rs = ps.executeQuery();

      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  public boolean isExistPhoneExceptItself(String phone, int ID) {
    ResultSet rs = null;
    if (phone == null || phone.equals("")) {
      return false;
    }
    String sql = "SELECT * FROM [User] WHERE PhoneNumber = ? AND ID != ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setNString(1, phone);
      ps.setInt(2, ID);
      rs = ps.executeQuery();
      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public boolean isExistPhone(String phone) {
    ResultSet rs = null;
    if (phone == null || phone.equals("")) {
      return false;
    }
    String sql = "SELECT * FROM [User] WHERE PhoneNumber = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setNString(1, phone);
      rs = ps.executeQuery();
      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public boolean isExistEmail(String email) {

    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE Email = ?";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, email);

      rs = ps.executeQuery();

      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  public boolean isExistEmailExceptItself(String email, int ID) {

    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE Email = ? AND ID != ?";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, email);
      ps.setInt(2, ID);
      rs = ps.executeQuery();
      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  public boolean loginWithEmail(String email, String password) throws IllegalArgumentException {
    if (email == null || password == null) {
      throw new IllegalArgumentException("Parameter cannot be null.");
    }

    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE Email = ? AND [Password] = ?";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, email);
      ps.setString(2, getMD5hash(password));

      rs = ps.executeQuery();

      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  public boolean register(String email) throws EmailDuplicationException {
    // Existed email is not allowed
    if (getUserByEmail(email) != null) {
      System.out.println("Throwing email exception");
      throw new EmailDuplicationException();
    }

    String username = email.substring(0, email.indexOf('@'));

    int tempId = 0;
    int offset = getMaxId();

    if (isExistUsername(username)) {
      String tempUsername = username + offset;

      while (isExistUsername(tempUsername)) {
        tempId += offset;
        tempUsername = username + tempId + "";
      }

      username = tempUsername;
    }

    int result = 0;

    String generatedPassword = PasswordGenerator.generateStrongPassword(12);
    System.out.println("New password:" + generatedPassword);

    String sql = "Insert into [User] (Name, UserName, Password, PhoneNumber, Email, Address, Role) values (?, ?, ?, ?, ?, ?, ?)";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, "");
      ps.setString(2, username);
      ps.setString(3, getMD5hash(generatedPassword));
      ps.setString(4, "");
      ps.setString(5, email);
      ps.setString(6, "");
      ps.setString(7, "Client");

      result = ps.executeUpdate();

      if (result > 0) {
        System.out.println("Affect " + result + " row");
        System.out.println("Begin Sending mail");
        EmailSender es = new EmailSender();
        es.setEmailTo(email);
        es.sendToEmail(es.GENERATE_PASSWORD_SUBJECT, es.generatePasswordEmailHTML(generatedPassword));
        System.out.println("After Sending mail");
        return true;
      }

    } catch (SQLException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return false;
  }

}
