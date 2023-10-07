/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.DAOs;

import Models.User;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface IUserDAO {

    public final String TABLE_NAME = "User";
    public final String USER_ID = "User_ID";
    public final String USER_NAME = "User_Name";
    public final String USER_USERNAME = "User_Username";
    public final String USER_PASSWORD = "User_Password";
    public final String USER_EMAIL = "User_Email";
    public final String USER_ACTIVE = "User_Active";
    public final String USER_TYPE = "User_Type";

    public enum loginType {
        Email,
        Username
    }

    public ArrayList<User> getAll();

    public User getUser(String username);

    public User getUserByEmail(String email);

    public User getUser(String loginString, String password, loginType Type);

    public List<User> searchUser(String search);

    public List<User> pagingUser(List<User> users, int page);

    /**
     *
     * @param updateUser
     * @return the affected rows of executeUpdate()
     */
    public int updateUser(User updateUser);

    public int checkout(Integer customerId, Date Date, String address, String phoneNumber, String note, Integer total);

    /**
     *
     * @param user
     * @return the affected rows of executeUpdate()
     */
    public int restoreUser(User user);

    public int disableUser(User user);

    /**
     * Check all duplication. If Email is duplication throw
     * EmailDuplicationException and so on, ...
     *
     * @param user
     * @return {@code true} if it has a duplication otherwise
     */
    public boolean checkDuplication(User user);

    public boolean isExistUsername(String username);

    public boolean isExistUsernameExceptItself(String username, int ID);

    public boolean isExistId(int ID);

    public boolean isExistPhoneExceptItself(String phone, int ID);

    public boolean isExistPhone(String phone);

    public boolean isExistEmail(String email);

    public boolean isExistEmailExceptItself(String email, int ID);

}
