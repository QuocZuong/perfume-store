package DAOs;

import Models.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Exceptions.AccountDeactivatedException;
import Exceptions.EmailDuplicationException;
import Exceptions.InvalidInputException;
import Exceptions.UsernameDuplicationException;
import Exceptions.WrongPasswordException;
import Interfaces.DAOs.IUserDAO;
import Lib.Converter;
import java.util.List;

import java.util.regex.*;

public class UserDAO implements IUserDAO {

    private final Connection conn;

    /**
     * Constructs a new {@code UserDAO} object.
     */
    public UserDAO() {
        conn = DB.DBContext.getConnection();
    }

    /* --------------------- CREATE SECTION --------------------- */
    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
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
    @Override
    public User getUser(String username) {
        if (username == null) {
            return null;
        }

        ResultSet rs;
        String sql = String.format("SELECT * FROM [%s] WHERE %s = ?", TABLE_NAME, USER_USERNAME);

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next()) {
                User us = userFactory(rs);
                return us;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public User getUserForAdminActivityLogs(int adminId) {
        if (adminId < 0) {
            return null;
        }

        ResultSet rs;
        String sql = "SELECT [User].User_ID, [User].[User_Name], [User].User_Username, [User].User_Password, [User].User_Email, [User].User_Active, [User].User_Type FROM Product_Activity_Log\n"
                + "JOIN [Admin] ON Product_Activity_Log.Updated_By_Admin = [Admin].[Admin_ID]\n"
                + "JOIN [Employee] ON [Admin].[Employee_ID] = [Employee].[Employee_ID]\n"
                + "JOIN [User] ON [Employee].[User_ID] = [User].[User_ID]\n"
                + "WHERE [Admin].Admin_ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, adminId);

            rs = ps.executeQuery();

            if (rs.next()) {
                User us = userFactory(rs);
                return us;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public User getUserForOrderActivityLogs(int orderManagerId) {
        if (orderManagerId < 0) {
            return null;
        }

        ResultSet rs;
        String sql = "SELECT * FROM [Order]\n"
                + "JOIN [Order_Manager] ON [Order].Order_Update_By_Order_Manager = [Order_Manager].[Order_Manager_ID]\n"
                + "JOIN [Employee] ON [Order_Manager].[Employee_ID] = [Employee].Employee_ID\n"
                + "JOIN [User] ON [Employee].[User_ID] = [User].[User_ID]\n"
                + "WHERE [Order_Manager].[Order_Manager_ID] = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderManagerId);

            rs = ps.executeQuery();

            if (rs.next()) {
                User us = userFactory(rs);
                return us;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public User getUser(int ID) {
        String sql = String.format("SELECT * FROM [User] WHERE USER_ID = ?");
        ResultSet rs;
        System.out.println("go to getUser");
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);

            rs = ps.executeQuery();

            if (rs.next()) {
                User us = userFactory(rs);
                return us;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean isExistUsername(String username) {
        String sql = "SELECT * FROM [User] WHERE User_Username = ?";

        ResultSet rs;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, username);

            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isExistEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE User_Email = ?";

        ResultSet rs;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, email);

            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            return null;
        }

        ResultSet rs;
        String sql = String.format("SELECT * FROM [%s] WHERE %s = ?", TABLE_NAME, USER_EMAIL);

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                User us = userFactory(rs);
                return us;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    // loginString is username or email
    @Override
    public User login(String loginString, String password, loginType Type)
            throws WrongPasswordException, AccountDeactivatedException, InvalidInputException {
        if (loginString == null || password == null || Type == null) {
            System.out.println("Parameter is null, Aborting operation login");
            return null;
        }
        if (!((Type == loginType.Email || Type == loginType.Username)
                && checkRegex(loginString, Type)
                && checkRegexPassword(password))) {
            throw new InvalidInputException();
        }

        User user = null;
        ResultSet rs;
        String sql = "";
        if (Type == loginType.Email) {
            sql = "SELECT * FROM [User]\n"
                    + "WHERE User_Email = ?\n"
                    + "AND User_Password = ?";
        } else if (Type == loginType.Username) {
            sql = "SELECT * FROM [User]\n"
                    + "WHERE User_Username = ?\n"
                    + "AND User_Password = ?";
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loginString);
            ps.setString(2, Converter.convertToMD5Hash(password));
            rs = ps.executeQuery();

            if (rs.next()) {
                user = userFactory(rs);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        checkAccount(user, Type);
        return user;
    }

    public boolean checkAccount(User user, loginType Type)
            throws WrongPasswordException, AccountDeactivatedException {
        if (user == null) {
            throw new WrongPasswordException();
        }
        if (!user.isActive()) {
            throw new AccountDeactivatedException();
        }
        return true;
    }

    // Check input 2-side
    public boolean checkRegex(String loginString, loginType Type) {
        if (loginString.length() >= 50) {
            return false;
        }
        final Pattern EMAIL_REGEX = Pattern.compile(
                "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
                Pattern.CASE_INSENSITIVE);
        final Pattern USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9]+$", Pattern.CASE_INSENSITIVE);

        if (Type == loginType.Username) {
            return USERNAME_REGEX.matcher(loginString).matches();
        } else if (Type == loginType.Email) {
            return EMAIL_REGEX.matcher(loginString).matches();
        }

        return false;
    }
    // Check input 2-side

    public boolean checkRegexPassword(String password) {
        final Pattern PASSWORD_REGEX = Pattern.compile("^.+$", Pattern.CASE_INSENSITIVE);
        return PASSWORD_REGEX.matcher(password).matches();
    }

    @Override
    public List<User> searchUser(String search) {
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM [User]\n"
                + "WHERE [User_Name] LIKE ?\n"
                + "OR User_Username LIKE ?\n"
                + "OR [User_Email] LIKE ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            search = "%" + search + "%";
            ps.setNString(1, search);
            ps.setNString(2, search);
            ps.setNString(3, search);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = userFactory(rs);
                userList.add(user);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }

    /* --------------------- UPDATE SECTION --------------------- */
    @Override
    public int updateUser(User updateUser) {
        if (updateUser == null) {
            return -1;
        }

        String sql = String.format("UPDATE [%s] SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE_NAME, USER_NAME, USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_ACTIVE, USER_TYPE, USER_ID);
        int kq = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setNString(1, updateUser.getName());
            ps.setString(2, updateUser.getUsername());
            ps.setString(3, updateUser.getPassword());
            ps.setString(4, updateUser.getEmail());
            ps.setBoolean(5, updateUser.isActive());
            ps.setNString(6, updateUser.getType());
            ps.setInt(7, updateUser.getId());

            kq = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return kq;
    }

    @Override
    public boolean checkout(Integer customerId, Date Date, String address, String phoneNumber, String note,
            Integer total) {
        if (customerId == null || Date == null || address == null || phoneNumber == null || note == null
                || total == null) {
            return false;
        }
        throw new UnsupportedOperationException();
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
        // String sql = "INSERT INTO [Order](ClientID, [Date], [Address], [PhoneNumber],
        // [Note], [Sum]) VALUES (?, ?, ?, ?, ?, ?)";
        // try {
        // PreparedStatement ps = conn.prepareStatement(sql);
        // ps.setInt(1, ClientID);
        // ps.setDate(2, Date);
        // ps.setNString(3, Address);
        // ps.setNString(4, PhoneNumber);
        // ps.setNString(5, Note);
        // ps.setInt(6, Sum);
        //
        // return ps.executeUpdate();
        // } catch (SQLException e) {
        // e.printStackTrace();
        // }
        // return 0;
    }

    /*--------------------- RESTORE SECTION ---------------------  */
    @Override
    public int restoreUser(User user) {
        user.setActive(true);
        return updateUser(user);
    }

    /*--------------------- DELETE SECTION ---------------------  */
    @Override
    public int disableUser(User user) {
        user.setActive(false);
        return updateUser(user);
    }

    /*--------------------- VALIDATE SECTION ---------------------  */
    @Override
    public boolean checkDuplication(User user) throws UsernameDuplicationException, EmailDuplicationException {
        if (getUser(user.getUsername()) != null) {
            throw new UsernameDuplicationException();
        }

        if (getUserByEmail(user.getEmail()) != null) {
            throw new EmailDuplicationException();
        }
        return true;
    }

    /*--------------------- UTILITY METHODS ---------------------  */
    /**
     * Create a new {@link User} object from a {@link ResultSet}.
     *
     * @param queryResult The {@code ResultSet} containing the user's
     * information.
     * @return An {@code User} object containing the user's information.
     * @throws SQLException If an error occurs while retrieving the data.
     */
    public User userFactory(ResultSet queryResult) throws SQLException {
        User user = new User();
        user.setId(queryResult.getInt(USER_ID));
        user.setName(queryResult.getNString(USER_NAME));
        user.setUsername(queryResult.getString(USER_USERNAME));
        user.setPassword(queryResult.getString(USER_PASSWORD));
        user.setEmail(queryResult.getString(USER_EMAIL));
        user.setActive(queryResult.getBoolean(USER_ACTIVE));
        user.setType(queryResult.getString(USER_TYPE));

        return user;
    }
}
