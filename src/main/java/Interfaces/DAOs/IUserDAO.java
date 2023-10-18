package Interfaces.DAOs;

import Exceptions.AccountDeactivatedException;
import Exceptions.EmailDuplicationException;
import Exceptions.UsernameDuplicationException;
import Exceptions.WrongPasswordException;
import Models.User;
import java.sql.Date;
import java.util.List;
import javax.security.auth.login.AccountNotFoundException;

public interface IUserDAO {

    /**
     * The name of the table in the database.
     */
    public final String TABLE_NAME = "User";
    /**
     * The user's Id column name.
     */
    public final String USER_ID = "User_Id";
    /**
     * The user's name column name.
     */
    public final String USER_NAME = "User_Name";
    /**
     * The user's username column name.
     */
    public final String USER_USERNAME = "User_Username";
    /**
     * The user's password column name.
     */
    public final String USER_PASSWORD = "User_Password";
    /**
     * The user's PhoneNumber number column name.
     */
    public final String USER_EMAIL = "User_Email";
    /**
     * The user's PhoneNumber number column name.
     */
    public final String USER_ACTIVE = "User_Active";
    /**
     * The user's PhoneNumber number column name.
     */
    public final String USER_TYPE = "User_Type";

    /**
     * The login type that used in
     * {@link IUserDAO#getUser(String loginString, String password, loginType Type)}.
     */
    public enum loginType {
        Email,
        Username
    }
    public final int ROWS = 20;

    /**
     * Get all {@link User}s in database.
     *
     * @return An {@code ArrayList} containing all {@code User} in database.
     */
    public List<User> getAll();

    /**
     * Gets an {@link User} from the database.
     *
     * @param username A {@code String} that represents the username of the
     * {@code User} to be retrieved.
     * @return A {@code User} object containing the user's information, or
     * {@code null} if an error occurs.
     */
    public User getUser(String username);

    /**
     * Gets a {@link User} from the database.
     *
     * @param Id The Id of the user to be retrieved.
     * @return A {@code User} object containing the user's information.
     * {@code null} if an error occurs or the user is not found.
     */
    public User getUser(int Id);

    /**
     * Gets a {@link User} from the database.
     *
     * @param email A {@code String} that represents the email of the
     * {@code User} to be retrieved.
     * @return A {@code User} object containing the user's information.
     * {@code null} if an error occurs or the user is not found.
     */
    public User getUserByEmail(String email);

    /**
     * Gets a {@link User} from the database.
     *
     * @param loginString A {@code String} that represents the {@code User}'s
     * username or email.
     * @param password A {@code String} that represents the password of the
     * {@code User} to be retrieved.
     * @param Type A {@code loginType} that represents the login type, as
     * defined in {@link loginType}.
     * @return A {@code User} object containing the user's information if the
     * login is successful, {@code null} otherwise.
     * @throws Exceptions.WrongPasswordException
     * @throws Exceptions.AccountDeactivatedException
     * @throws javax.security.auth.login.AccountNotFoundException
     */
    public User getUser(String loginString, String password, loginType Type) throws WrongPasswordException, AccountDeactivatedException, AccountNotFoundException;

    /**
     * Search for a list of {@link User}s in the database that match the
     * provIded requirements.
     *
     * @param search The search string.
     * @return A {@code List} of {@code User}s that match the search string.
     */
    public List<User> searchUser(String search);

    /**
     * Updates a {@link User}'s information in the database.
     *
     * @param updateUser The {@code User} to be updated.
     * @return {@code 0} if the {@code User}'s information is updated
     * sucessfully. {@code 1} if user cannot be updated, or {@code 2} if the
     * {@code User} doesn't exist.
     */
    public int  updateUser(User updateUser);

    /**
     * This function hanlde the checkout process for a customer.
     *
     * @param customerId An {@code integer} representing the unique Identifier
     * of the customer.
     * @param Date The {@link Date} parameter is used to specify the date of the
     * checkout.
     * @param address A {@code String} that represents the customer's address.
     * @param PhoneNumber A {@code String} that represents the customer's
     * PhoneNumber number.
     * @param note A {@code String} that can be used to add any additional
     * information or special instructions related to the checkout process.
     * @param total An {@code integer} represents the total amount of the
     * checkout.
     * @return {@code true} if the checkout process is successful, {@code fasle}
     * otherwise.
     */
    public boolean checkout(Integer customerId, Date Date, String address, String PhoneNumber, String note,
            Integer total);

    /**
     * Activate a {@link User} in the database.
     *
     * @param user The {@code User} to be activated.
     * @return {@code true} if the user is activated, {@code false} otherwise.
     */
    public int  restoreUser(User user);

    /**
     * Deactivate a {@link User} in the database.
     *
     * @param user The {@code User} to be deactivated.
     * @return {@code true} if the user is deactivated, {@code false} otherwise.
     */
    public int  disableUser(User user);

    /**
     * Check all duplication.If Email is duplication throw
     * EmailDuplicationException and so on, ...
     *
     * @param user The {@code User} to be checked.
     * @return {@code true} if it has a duplication, {@code false} otherwise.
     * @throws Exceptions.UsernameDuplicationException
     * @throws Exceptions.EmailDuplicationException
     */
    public boolean checkDuplication(User user) throws UsernameDuplicationException, EmailDuplicationException;

}
