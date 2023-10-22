/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import Exceptions.AccountDeactivatedException;
import Exceptions.InvalidInputException;
import Exceptions.WrongPasswordException;
import Interfaces.DAOs.IUserDAO;
import Models.User;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Acer
 */
public class UserDAOTest {

    public UserDAOTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * UTCID01
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID01LoginByEmail() throws Exception {
        System.out.println("testUTCID01LoginByEmail");
        String loginString = "test12345";
        String password = "test123456789";
        IUserDAO.loginType Type = IUserDAO.loginType.Username;
        UserDAO instance = new UserDAO();
        User expResult = new User();

        expResult.setName("Test");
        expResult.setUsername("test12345");
        expResult.setEmail("test@email.com");
        expResult.setType("Employee");

        User result = instance.login(loginString, password, Type);

        assertEquals(expResult.getName(), result.getName());
        assertEquals(expResult.getUsername(), result.getUsername());
        assertEquals(expResult.getEmail(), result.getEmail());
        assertEquals(expResult.getType(), result.getType());
    }

    /**
     * UTCID02
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID02LoginWrongPassword() throws Exception {
        System.out.println("testUTCID02LoginWrongPassword");
        String loginString = "test12345";
        String password = "12345"; // Wrong password
        IUserDAO.loginType Type = IUserDAO.loginType.Username;
        UserDAO instance = new UserDAO();
        assertThrows(WrongPasswordException.class, () -> {
            instance.login(loginString, password, Type);
        });
    }

    /**
     * UTCID03 Password null
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID03LoginPasswordNull() throws Exception {
        System.out.println("testUTCID03LoginEmailInvalid");
        String loginString = "test@gmail"; // invalid email
        String password = null;
        IUserDAO.loginType Type = IUserDAO.loginType.Email;
        UserDAO instance = new UserDAO();
        assertNull(instance.login(loginString, password, Type));
    }

    /**
     * UTCID04 LoginType null
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID04LoginLoginTypeNulll() throws Exception {
        System.out.println("testUTCID03LoginEmailInvalid");
        String loginString = "test@gmail"; // invalid email
        String password = null;
        IUserDAO.loginType Type = IUserDAO.loginType.Email;
        UserDAO instance = new UserDAO();
        assertNull(instance.login(loginString, password, Type));
    }

    /**
     * UTCID05
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID05LoginEmailInvalid() throws Exception {
        System.out.println("testUTCID03LoginEmailInvalid");
        String loginString = "test@gmail"; // invalid email
        String password = "test123456789";
        IUserDAO.loginType Type = IUserDAO.loginType.Email;
        UserDAO instance = new UserDAO();
        assertThrows(InvalidInputException.class, () -> {
            instance.login(loginString, password, Type);
        });
    }

    /**
     * UTCID06 Boundary for login string fail case, loginString length is 50
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID06LoginLongLoginString() throws Exception {
        System.out.println("testUTCID04LoginLongLoginString");
        String loginString = "1234567890123456789012345678901234567890@gmail.com"; //long email
        String password = "test123456789";
        IUserDAO.loginType Type = IUserDAO.loginType.Email;
        UserDAO instance = new UserDAO();
        assertThrows(InvalidInputException.class, () -> {
            instance.login(loginString, password, Type);
        });
    }

    /**
     * UTCID07 Boundary for login string accept case, loginString length is 49
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID07LoginLongLoginString() throws Exception {
        System.out.println("testUTCID05LoginLongLoginString");
        String loginString = "123456789012345678901234567890123456789@gmail.com";
        String password = "test123456789";
        IUserDAO.loginType Type = IUserDAO.loginType.Email;
        UserDAO instance = new UserDAO();
        assertThrows(WrongPasswordException.class, () -> {
            instance.login(loginString, password, Type);
        });
    }

    /**
     * UTCID08 login method Username
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID08LoginByUsername() throws Exception {
        System.out.println("testUTCID06LoginByUsername");
        String loginString = "test12345";
        String password = "test123456789";
        IUserDAO.loginType Type = IUserDAO.loginType.Username;
        UserDAO instance = new UserDAO();
        User expResult = new User();

        expResult.setName("Test");
        expResult.setUsername("test12345");
        expResult.setEmail("test@email.com");
        expResult.setType("Employee");

        User result = instance.login(loginString, password, Type);

        assertEquals(expResult.getName(), result.getName());
        assertEquals(expResult.getUsername(), result.getUsername());
        assertEquals(expResult.getEmail(), result.getEmail());
        assertEquals(expResult.getType(), result.getType());
    }

    /**
     * UTCID09 login method Username
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID09LoginByUsername() throws Exception {
        System.out.println("testUTCID06LoginByUsername");
        String loginString = "test12345";
        String password = "12345";
        IUserDAO.loginType Type = IUserDAO.loginType.Username;
        UserDAO instance = new UserDAO();

        assertThrows(WrongPasswordException.class, () -> {
            instance.login(loginString, password, Type);
        });
    }

    /**
     * UTCID10 login String is null, Abnormal case
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID10LoginLoginStringIsNull() throws Exception {
        System.out.println("testUTCID08LoginLoginStringIsNull");
        String loginString = null;
        String password = "12345";
        IUserDAO.loginType Type = IUserDAO.loginType.Username;
        UserDAO instance = new UserDAO();

        assertNull(instance.login(loginString, password, Type));
    }

    /**
     * UTCID11 login String is not character or number, Abnormal case
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID11LoginLoginStringNotCharacterOrNumber() throws Exception {
        System.out.println("testUTCID09LoginLoginStringNotCharacterOrNumber");
        String loginString = "test123+-*/";
        String password = "test123456789";
        IUserDAO.loginType Type = IUserDAO.loginType.Username;
        UserDAO instance = new UserDAO();

        assertThrows(InvalidInputException.class, () -> {
            instance.login(loginString, password, Type);
        });
    }

    /**
     * UTCID12 login String is Username, length = 50
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID12LoginByUsername() throws Exception {
        System.out.println("testUTCID10LoginByUsername");
        String loginString = "oneoftheesiestexampleisoneofthemostpowerfultestcas";
        String password = "test123456789";
        IUserDAO.loginType Type = IUserDAO.loginType.Username;
        UserDAO instance = new UserDAO();

        assertThrows(InvalidInputException.class, () -> {
            instance.login(loginString, password, Type);
        });
    }

    /**
     * UTCID13 login String is Username, length = 49
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID13LoginByUsername() throws Exception {
        System.out.println("testUTCID11LoginByUsername");
        String loginString = "oneoftheesiestexampleisoneofthemostpowerfultestca";
        String password = "test123456789";
        IUserDAO.loginType Type = IUserDAO.loginType.Username;
        UserDAO instance = new UserDAO();

        assertThrows(WrongPasswordException.class, () -> {
            instance.login(loginString, password, Type);
        });
    }

    /**
     * UTCID14 Account is deactivated
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID14LoginDeactivatedAccount() throws Exception {
        System.out.println("testUTCID11LoginByUsername");
        String loginString = "thinhthinh123123@gmail.com";
        String password = "thinhthinh";
        IUserDAO.loginType Type = IUserDAO.loginType.Email;
        UserDAO instance = new UserDAO();

        assertThrows(AccountDeactivatedException.class, () -> {
            instance.login(loginString, password, Type);
        });
    }

    /**
     * UTCID15 LoginType is other string, abnormal
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void testUTCID15LoginLoginTypeIsOther() throws Exception {
        System.out.println("testUTCID11LoginByUsername");
        String loginString = "thinhthinh123123@gmail.com";
        String password = "thinhthinh";
        IUserDAO.loginType Type = null;
        UserDAO instance = new UserDAO();

        assertNull(instance.login(loginString, password, Type));
    }
}
