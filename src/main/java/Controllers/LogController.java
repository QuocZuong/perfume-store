package Controllers;

// For Exception
import DAOs.CustomerDAO;
import DAOs.EmployeeDAO;
import Exceptions.AccountDeactivatedException;
import Exceptions.EmailDuplicationException;
import Exceptions.WrongPasswordException;

import DAOs.UserDAO;
import Exceptions.EmailDoesntExist;
import Exceptions.InvalidInputException;
import Interfaces.DAOs.IUserDAO;
import Lib.Converter;
import Lib.EmailSender;
import Lib.ExceptionUtils;
import Lib.Generator;
import Models.Employee;
import Models.User;

import java.io.IOException;
import jakarta.servlet.ServletException;
// For Exception

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogController extends HttpServlet {

    public static final String LOGIN_URI = "/Log/Login";
    public static final String LOGOUT_URI = "/Log/Logout";
    public static final String FORGOT_PASSWORD_URI = "/Log/ForgotPassword";

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith(LOGIN_URI)) {
            request.getRequestDispatcher("/LOGIN_PAGE/logIn.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(FORGOT_PASSWORD_URI)) {
            request.getRequestDispatcher("/LOGIN_PAGE/forgotPassword.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(LOGOUT_URI)) {
            System.out.println("going logout");
            logout(request, response);
            response.sendRedirect("/");
            return;
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Login
        if (request.getParameter("submitBtn") != null && request.getParameter("submitBtn").equals("submitLogin")) {
            if (login(request, response)) {
                //redirect in the login method
            } else {
                System.out.println("Login failed. Going to /Log/Login");
                response.sendRedirect(LOGIN_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }
        // Register
        if (request.getParameter("submitBtn") != null && request.getParameter("submitBtn").equals("submitRegister")) {
            if (register(request, response)) {
                System.out.println("Register Successfully. Going to /Product/List");
                response.sendRedirect("/Product/List");
            } else {
                System.out.println("Register failed. Going to /Log/Login");
                response.sendRedirect(LOGIN_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        // Forgot password
        if (request.getParameter("submitBtn") != null && request.getParameter("submitBtn").equals("submitForgotPassword")) {
            if (forgotPassword(request, response)) {
                System.out.println("Forgot password successfully");
                response.sendRedirect("/Log/Login");
            } else {
                System.out.println("Forgot password failed. Going to /Log/Login");
                System.out.println(FORGOT_PASSWORD_URI + request.getAttribute("errUserID")
                        + ExceptionUtils.generateExceptionQueryString(request));
                response.sendRedirect(FORGOT_PASSWORD_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }
    }

    // ------------------------- AUNTHENTICATION SECTION -------------------------
    public boolean login(HttpServletRequest request, HttpServletResponse response) {
        String us = request.getParameter("txtUsername");
        String pw = request.getParameter("txtPassword");
        boolean rememberPw = request.getParameter("txtRememberPassword") != null;
        UserDAO uDAO = new UserDAO();

        User user;
        // Try to login first
        try {
            user = uDAO.login(us, pw, us.contains("@") ? IUserDAO.loginType.Email : IUserDAO.loginType.Username);
        } catch (AccountDeactivatedException e) {
            request.setAttribute("exceptionType", "AccountDeactivatedException");
            return false;
        } catch (WrongPasswordException e) {
            request.setAttribute("exceptionType", "WrongPasswordException");
            return false;
        } catch (InvalidInputException ex) {
            request.setAttribute("exceptionType", "InvalidInputException");
            return false;
        }

        if (user == null) {
            return false;
        }

        // Set cookie
        String cookieKey = "";
        String cookieValue = user.getUsername();

        String userType = user.getType();
        String URI = "/Product/List";
        if (userType.equals("Customer")) {
            cookieKey = "Customer";
        } else if (userType.equals("Employee")) {
            EmployeeDAO empDAO = new EmployeeDAO();
            Employee emp = empDAO.getEmployeeByUserId(user.getId());
            cookieKey = emp.getRole().getName();
            cookieKey = cookieKey.replaceAll(" ", "");
            URI = "/" + cookieKey;
        }
        Cookie c = new Cookie(cookieKey, cookieValue);
        if (rememberPw) {
            c.setMaxAge(24 * 60 * 60 * 30);
        } else {
            c.setMaxAge(24 * 60 * 60);
        }
        c.setPath("/");
        request.getSession().setAttribute("userCookie", c);
        response.addCookie(c);
        //send redirect
        try {
            response.sendRedirect(URI);
        } catch (IOException ex) {
            Logger.getLogger(LogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean register(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("txtEmail");
        CustomerDAO cusDAO = new CustomerDAO();

        try {
            if (cusDAO.register(email)) {
                String username = cusDAO.getUserByEmail(email).getUsername();

                Cookie c = new Cookie("Customer", username);
                c.setMaxAge(3 * 24 * 60 * 60);
                c.setPath("/");

                response.addCookie(c);

                return true;
            }
        } catch (EmailDuplicationException e) {
            request.setAttribute("exceptionType", "EmailDuplicationException");
        }

        return false;
    }

    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        for (Cookie c : cookies) {
            if (c.getName().equals("Customer")
                    || c.getName().equals("Admin")
                    || c.getName().equals("OrderManager")
                    || c.getName().equals("InventoryManager")) {
                c.setMaxAge(0);
                c.setPath("/");
                response.addCookie(c);
            }
        }

        return true;
    }

    public boolean forgotPassword(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String email = request.getParameter("txtEmail");
        UserDAO userDAO = new UserDAO();

        try {
            if (userDAO.isExistEmail(email)) {
                String randomPassword = Generator.generateForgotPassword();
                User user = userDAO.getUserByEmail(email);
                user.setPassword(Converter.convertToMD5Hash(randomPassword));
                userDAO.updateUser(user);

                //send mail
                EmailSender eSender = new EmailSender();
                eSender.setEmailTo(email);
                eSender.sendEmailByThread(eSender.FORGOT_PASSWORD_NOTFICATION, eSender.forgotPasswordEmailHTML(randomPassword));
                return true;
            } else {
                throw new EmailDoesntExist();
            }
        } catch (EmailDoesntExist e) {
            request.setAttribute("exceptionType", "EmailDoesntExist");
        }

        return false;
    }

}
