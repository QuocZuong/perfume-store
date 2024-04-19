// /*
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//  * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
//  */
// package DAOs;

// import Exceptions.AccountDeactivatedException;
// import Exceptions.InvalidInputException;
// import Exceptions.WrongPasswordException;
// import Interfaces.DAOs.IUserDAO;
// import Models.User;
// import static org.junit.jupiter.api.Assertions.*;

// /**
//  *
//  * @author Acer
//  */
// public class UserDAOTest {

//     public UserDAOTest() {
//     }

//     @org.junit.jupiter.api.BeforeAll
//     public static void setUpClass() throws Exception {
//     }

//     @org.junit.jupiter.api.AfterAll
//     public static void tearDownClass() throws Exception {
//     }

//     @org.junit.jupiter.api.BeforeEach
//     public void setUp() throws Exception {
//     }

//     @org.junit.jupiter.api.AfterEach
//     public void tearDown() throws Exception {
//     }

//     /**
//      * UTCID01
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID01LoginByEmail() throws Exception {
//         System.out.println("testUTCID01LoginByEmail");
//         String loginString = "test@email.com";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Email;
//         UserDAO instance = new UserDAO();
//         User expResult = new User();

//         expResult.setName("Test");
//         expResult.setUsername("test12345");
//         expResult.setEmail("test@email.com");
//         expResult.setType("Employee");

//         User result = instance.login(loginString, password, Type);

//         assertEquals(expResult.getName(), result.getName());
//         assertEquals(expResult.getUsername(), result.getUsername());
//         assertEquals(expResult.getEmail(), result.getEmail());
//         assertEquals(expResult.getType(), result.getType());
//     }

//     /**
//      * UTCID02
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID02LoginByEmailInvalidInput() throws Exception {
//         System.out.println("testUTCID02LoginByEmailInvalidInput");
//         String loginString = "test@email.com";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Username;
//         UserDAO instance = new UserDAO();
//         assertThrows(InvalidInputException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID03 type null
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID03LoginByEmailTypeNull() throws Exception {
//         System.out.println("testUTCID03LoginByEmailTypeNull");
//         String loginString = "test@email.com";
//         String password = "test12345";
//         IUserDAO.loginType Type = null;
//         UserDAO instance = new UserDAO();
//         assertNull(instance.login(loginString, password, Type));
//     }

//     /**
//      * UTCID04 wrong password
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID04LoginByEmailWrongPassword() throws Exception {
//         System.out.println("testUTCID04LoginByEmailWrongPassword");
//         String loginString = "test@email.com";
//         String password = "thinhthinh";
//         IUserDAO.loginType Type = IUserDAO.loginType.Email;
//         UserDAO instance = new UserDAO();
//         assertThrows(WrongPasswordException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID05
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID05LoginByEmailWrongPasswordTypeNull() throws Exception {
//         System.out.println("testUTCID04LoginByEmailWrongPasswordTypeNull");
//         String loginString = "test@email.com"; // invalid email
//         String password = "thinhthinh";
//         IUserDAO.loginType Type = null;
//         UserDAO instance = new UserDAO();
//         assertNull(instance.login(loginString, password, Type));
//     }

//     /**
//      * UTCID06
//      *
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID06LoginByEmailPasswordNull() throws Exception {
//         System.out.println("testUTCID06LoginByEmailPasswordNull");
//         String loginString = "test@email.com";
//         String password = null;
//         IUserDAO.loginType Type = null;
//         UserDAO instance = new UserDAO();
//         assertNull(instance.login(loginString, password, Type));

//     }

//     /**
//      * UTCID07
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID07LoginByEmailPassword0char() throws Exception {
//         System.out.println("testUTCID07LoginByEmailPassword0char");
//         String loginString = "test@email.com";
//         String password = "";
//         IUserDAO.loginType Type = IUserDAO.loginType.Email;
//         UserDAO instance = new UserDAO();
//         assertThrows(InvalidInputException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID08
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID08LoginByInvalidEmail() throws Exception {
//         System.out.println("testUTCID08LoginByInvalidEmail");
//         String loginString = "test@gmail";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Email;
//         UserDAO instance = new UserDAO();
//         assertThrows(InvalidInputException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID09 51 character email
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID09LoginBy51LongEmail() throws Exception {
//         System.out.println("testUTCID09LoginBy51LongEmail");
//         String loginString = "12345678901234567890123456789012345678901@gmail.com";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Email;
//         UserDAO instance = new UserDAO();

//         assertThrows(InvalidInputException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID10 50 character email
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID10LoginBy50LongEmail() throws Exception {
//         System.out.println("testUTCID10LoginBy50LongEmail");
//         String loginString = "1234567890123456789012345678901234567890@gmail.com";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Email;
//         UserDAO instance = new UserDAO();
//         assertThrows(WrongPasswordException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID11 login by username
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID11LoginByUsername() throws Exception {
//         System.out.println("testUTCID11LoginByUsername");
//         String loginString = "test12345";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Username;
//         UserDAO instance = new UserDAO();

//         User expResult = new User();

//         expResult.setName("Test");
//         expResult.setUsername("test12345");
//         expResult.setEmail("test@email.com");
//         expResult.setType("Employee");

//         User result = instance.login(loginString, password, Type);

//         assertEquals(expResult.getName(), result.getName());
//         assertEquals(expResult.getUsername(), result.getUsername());
//         assertEquals(expResult.getEmail(), result.getEmail());
//         assertEquals(expResult.getType(), result.getType());
//     }

//     /**
//      * UTCID12 login String is digit Username
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID12LoginByDigitUsername() throws Exception {
//         System.out.println("testUTCID12LoginByDigitUsername");
//         String loginString = "99999999";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Username;
//         UserDAO instance = new UserDAO();

//         assertThrows(WrongPasswordException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID13 login String is character Username
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID13LoginByCharacterUsername() throws Exception {
//         System.out.println("testUTCID13LoginByCharacterUsername");
//         String loginString = "AAAAAAAA";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Username;
//         UserDAO instance = new UserDAO();

//         assertThrows(WrongPasswordException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID14 login String is null Username
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID14LoginByUsernameNull() throws Exception {
//         System.out.println("testUTCID11LoginByUsername");
//         String loginString = null;
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Username;
//         UserDAO instance = new UserDAO();
//         assertNull(instance.login(loginString, password, Type));

//     }

//     /**
//      * UTCID15 login String is UTF-8 Username
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID15LoginByUTF8UsernameNull() throws Exception {
//         System.out.println("testUTCID15LoginByUTF8UsernameNull");
//         String loginString = "わたしわきれいです";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Username;
//         UserDAO instance = new UserDAO();

//         assertThrows(InvalidInputException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID16
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID16LoginByUsername0char() throws Exception {
//         System.out.println("testUTCID16LoginByUsername0char");
//         String loginString = "";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Username;
//         UserDAO instance = new UserDAO();
//         assertThrows(InvalidInputException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID17 1 character email
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID17LoginBy1LongUsernamel() throws Exception {
//         System.out.println("testUTCID17LoginBy1LongUsernamel");
//         String loginString = "1";
//         String password = "test12345";
//         IUserDAO.loginType Type = IUserDAO.loginType.Username;
//         UserDAO instance = new UserDAO();
//         assertThrows(WrongPasswordException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }

//     /**
//      * UTCID18 1 login by deactivate email
//      *
//      * @throws Exception
//      */
//     @org.junit.jupiter.api.Test
//     public void testUTCID18LoginByDeactivateEmail() throws Exception {
//         System.out.println("testUTCID18LoginByDeactivateEmail");
//         String loginString = "thinhthinh123123@gmail.com";
//         String password = "thinhthinh";
//         IUserDAO.loginType Type = IUserDAO.loginType.Email;
//         UserDAO instance = new UserDAO();
//         assertThrows(AccountDeactivatedException.class, () -> {
//             instance.login(loginString, password, Type);
//         });
//     }
// }
