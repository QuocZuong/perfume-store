package DAOs;

import Models.User;
import Lib.PasswordGenerator;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.login.AccountNotFoundException;

import Exceptions.AccountDeactivatedException;
import java.io.UnsupportedEncodingException;
import Exceptions.EmailDuplicationException;
import Exceptions.WrongPasswordException;
import Lib.EmailSender;
import Lib.Converter;

public class UserDAO {

    private Connection conn;

    private final String TABLE_NAME = "User";
    private final String USER_ID = "User_ID";
    private final String USER_NAME = "User_Name";
    private final String USER_USERNAME = "User_Username";
    private final String USER_PASSWORD = "User_Password";
    private final String USER_EMAIL = "User_Email";
    private final String USER_ACTIVE = "User_Active";
    private final String USER_TYPE = "User_Type";

    /**
     * Constructs a new {@code UserDAO} object.
     */
    public UserDAO() {
        conn = DB.DataManager.getConnection();
    }

    /* --------------------- CREATE SECTION --------------------- */

    /**
     * Get all {@link User}s in database.
     * 
     * @return An {@code ArrayList} containing all {@code User} in database.
     */
    public ArrayList<User> getAll() {
        ArrayList<User> result = new ArrayList<>();
        ResultSet rs;

        String sql = String.format("SELECT * FROM [%s]", TABLE_NAME);

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                User us = userFactory(rs);
                result.add(us);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /* --------------------- READ SECTION --------------------- */

    /**
     * Gets an {@link User} from the database.
     *
     * @param username A {@code String} that represents the username of the
     *                 {@code User} to be retrieved.
     * @return A {@code User} object containing the user's information, or
     *         {@code null} if an error occurs.
     */
    public User getUser(String username) {
        if (username == null) {
            return null;
        }

        ResultSet rs;
        String sql = String.format("SELECT * FROM [%s] WHERE %s = ?", TABLE_NAME, USER_USERNAME);

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, username);

            rs = ps.executeQuery();
            User us = new User();

            if (rs.next()) {
                us = userFactory(rs);
            }

            return us;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Gets a {@link User} from the database.
     *
     * @param ID The ID of the user to be retrieved.
     * @return A {@code User} object containing the user's information.
     *         {@code null} if an error occurs or the user is not found.
     */
    public User getUser(int ID) {
        String sql = String.format("SELECT * FROM [%s] WHERE %s", TABLE_NAME, USER_ID);
        ResultSet rs;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            User us = new User();

            if (rs.next()) {
                us = userFactory(rs);
            }

            return us;
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

        ResultSet rs;

        String sql = "SELECT * FROM [User] WHERE User_Email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            rs = ps.executeQuery();
            User us = new User();
            if (rs.next()) {
                us.setId(rs.getInt("User_ID"));
                us.setName(rs.getString("User_Name"));
                us.setUsername(rs.getString("User_Username"));
                us.setPassword(rs.getString("User_Password"));
                us.setEmail(rs.getString("User_Email"));
                us.setActive(rs.getBoolean("User_Active"));
                us.setType(rs.getString("User_Type"));
            }
            return us;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets max id in database.
     * 
     * @return A {@code id} which is the largest id.
     *         {@code 0} if an error occurs or there is no user in database.
     */
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
                + "WHERE User_ID LIKE ? OR User_Username LIKE ? OR User_Name LIKE ?\n"
                + "ORDER BY User_ID\n"
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

        String sql = "UPDATE [User] SET User_Name=?, User_Username=?, User_Password=?, User_Email=?, User_Type=?";

        int kq = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, updateUser.getName());
            ps.setNString(2, updateUser.getUsername());
            ps.setNString(3, updateUser.getPassword());
            ps.setNString(4, updateUser.getEmail());
            ps.setNString(5, updateUser.getType());

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
            throws AccountDeactivatedException, AccountNotFoundException, WrongPasswordException, SQLException {
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
            ps.setString(2, Converter.convertToMD5Hash(password));
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
            ps.setString(2, Converter.convertToMD5Hash(password));

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
            ps.setString(3, Converter.convertToMD5Hash(generatedPassword));
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

    /*--------------------- UTILITY METHODS ---------------------  */

    /**
     * Create a new {@link User} object from a {@link ResultSet}.
     * 
     * @param queryResult The {@code ResultSet} containing the user's information.
     * @return An {@code User} object containing the user's information.
     * @throws SQLException If an error occurs while retrieving the data.
     */
    public User userFactory(ResultSet queryResult) throws SQLException {
        User user = new User();

        user.setId(queryResult.getInt(USER_ID));
        user.setName(queryResult.getString(USER_NAME));
        user.setUsername(queryResult.getString(USER_USERNAME));
        user.setPassword(queryResult.getString(USER_PASSWORD));
        user.setEmail(queryResult.getString(USER_EMAIL));
        user.setActive(queryResult.getBoolean(USER_ACTIVE));
        user.setType(queryResult.getString(USER_TYPE));

        return user;
    }
}
