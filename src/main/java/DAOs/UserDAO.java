package DAOs;

import Models.User;
import java.security.MessageDigest;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

public class UserDAO {

  private Connection conn;

  /**
   * Constructs a new {@code UserDAO} object.
   */
  public UserDAO() {
    conn = DB.DataManager.getConnection();
  }

  /**
   * Gets all the users in the database.
   *
   * @return A {@code ResultSet} containing all the users in the database. {@code null} if an error occurs.
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
   * Checks if the provided username and password match a user's information or not.
   *
   * @param username the username of the user trying to log in.
   * @param password the password entered by the user trying to log in. this password must not be hasshed.
   * @return {@code true} if the username and password match a user's information. {@code false} otherwise.
   */
  public boolean validateUser(String username, String password) {
    if (username == null || password == null) {
      return false;
    }
    if (username.length() > 50) {
      return false;
    }

    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE UserName = ? AND [Password] = ?";

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, username);
      ps.setString(2, getMD5hash(password));
      rs = ps.executeQuery();
      return rs.next();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  /**
   * Gets a user from the database.
   *
   * @param username The username of the user to be retrieved.
   * @return A {@code User} object containing the user's information. {@code null} if an error occurs.
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
          rs.getNString("Role")
        );
      }
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   * Adds a new user to the database.
   *
   * @param us The user to be added.
   * @return The number of rows affected by the query. {@code 0} if user cannot be added.
   */
  public int addUser(User us) {
    if (us == null) {
      return 0;
    }

    String sql =
      "INSERT INTO User(Name, UserName, Password, Email, PhoneNumber, Address, Role) VALUES(?, ?, ?, ?, ?, ?, ?)";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setNString(1, us.getName());
      ps.setString(2, us.getUsername());
      ps.setString(3, getMD5hash(us.getPassword()));
      ps.setString(4, us.getEmail());
      ps.setString(5, us.getPhoneNumber());
      ps.setNString(6, us.getAddress());
      ps.setNString(7, us.getRole());

      return ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return 0;
  }

  /**
   * Deletes a user from the database.
   *
   * @param us The user to be deleted.
   * @return The number of rows affected by the query. {@code 0} if user cannot be deleted, or {@code username} doesn't exist.
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
   * Gets the MD5 hash of a string.
   *
   * @param str The string to be hashed.
   * @return The MD5 hash of the string. {@code null} if an error occurs while hashing.
   */
  private String getMD5hash(String str) {
    byte[] bytesOfMessage;

    try {
      bytesOfMessage = str.getBytes("UTF-8");
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] bytesOfDigest = md.digest(bytesOfMessage);
      return DatatypeConverter.printHexBinary(bytesOfDigest).toLowerCase();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

    return null;
  }
}
