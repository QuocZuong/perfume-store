package DAOs;

import Models.User;
import Lib.PasswordGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 * {@code null} if an error occurs.
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
	 * hashing.
	 */
	private String getMD5hash(String str) {

		MessageDigest md = null;
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
		String pwdMD5 = sb.toString();

		return pwdMD5;
	}

	/* --------------------- READ SECTION --------------------- */

	/**
	 * Gets a user from the database.
	 *
	 * @param username The username of the user to be retrieved.
	 * @return A {@code User} object containing the user's information.
	 * {@code null} if an error occurs.
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
								rs.getNString("Role"));
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
	 * {@code null} if an error occurs or the user is not found.
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
								rs.getString("Name"),
								rs.getString("UserName"),
								rs.getString("Password"),
								rs.getString("Email"),
								rs.getString("PhoneNumber"),
								rs.getString("Address"),
								rs.getString("Role"));
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
	 * {@code null} if an error occurs or the user is not found.
	 */
	public User getUserByEmail(String email) {
		ResultSet rs = null;

		String sql = "SELECT * FROM [User] WHERE Email = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				return new User(
								rs.getInt("ID"),
								rs.getString("Name"),
								rs.getString("UserName"),
								rs.getString("Password"),
								rs.getString("Email"),
								rs.getString("PhoneNumber"),
								rs.getString("Address"),
								rs.getString("Role"));
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	/* --------------------- UPDATE SECTION --------------------- */
 /*--------------------- DELETE SECTION ---------------------  */
	/**
	 * Deletes a user from the database.
	 *
	 * @param us The user to be deleted.
	 * @return The number of rows affected by the query. {@code 0} if user cannot
	 * be deleted, or {@code username} doesn't exist.
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

	/*--------------------- AUTHENTICATION SECTION ---------------------  */
	public boolean login(String username, String password) {
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

	public boolean loginWithEmail(String email, String password) {
		if (email == null || password == null) {
			return false;
		}
		if (email.length() > 100) {
			return false;
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

	public boolean register(String email) {
		if (getUserByEmail(email) != null) {
			return false;
		}

		int result = 0;

		String generatedPassword = PasswordGenerator.generateStrongPassword(12);
		System.out.println("New password:" + generatedPassword);

		String sql = "Insert into [User] (UserName,Password, Email, Role) values (?, ?, ?, ?)";
		String username = email.substring(0, email.indexOf('@'));

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, getMD5hash(generatedPassword));
			ps.setString(3, email);
			ps.setString(4, "Client");

			result = ps.executeUpdate();
			return result > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
