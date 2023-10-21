/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

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
     * Test of getUser method, of class UserDAO.
     */
    @org.junit.jupiter.api.Test
    public void testLogin() throws Exception {
        System.out.println("getUser");
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
}
