package Controllers;

import DAOs.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogController extends HttpServlet {

    public static final String LOGIN_URI = "/Log/Login";
    public static final String LOGOUT_URI = "/Log/Logout";

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
            System.out.println("going logout controller");
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

        if (request.getParameter("submitBtn") != null && request.getParameter("submitBtn").equals("submitLogin")) {
            if (login(request, response)) {
                System.out.println("Login Successfully. Going to /Product/List");
                response.sendRedirect("/Product/List");
            } else {
                System.out.println("Login failed. Going to /Log/Login");
                response.sendRedirect("/Log/Login");
            }
            return;
        }

        if (request.getParameter("submitBtn") != null && request.getParameter("submitBtn").equals("submitRegister")) {
            if (register(request, response)) {
                System.out.println("Register Successfully. Going to /Product/List");
                response.sendRedirect("/Product/List");
            } else {
                System.out.println("Register failed. Going to /Log/Login");
                response.sendRedirect("/Log/Login");
            }
            return;
        }
    }

    public boolean login(HttpServletRequest request, HttpServletResponse response) {
        String us = request.getParameter("txtUsername");
        String pw = request.getParameter("txtPassword");

        UserDAO dao = new UserDAO();

        boolean hasUser = false;
        String username = us;

        if (us.contains("@")) {
            hasUser = dao.loginWithEmail(us, pw);
            username = dao.getUserByEmail(us).getUsername();
        } else {
            hasUser = dao.login(us, pw);
        }

        if (hasUser) {
            boolean isAdmin = dao.isAdmin(username);

            Cookie c = new Cookie(isAdmin == true ? "Admin" : "Client", username);
            c.setMaxAge(3 * 24 * 60 * 60);
            c.setPath("/");
            request.getSession().setAttribute("userCookie", c);
            response.addCookie(c);
            return true;
        }

        return false;
    }

    public boolean register(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("txtEmail");
        UserDAO dao = new UserDAO();

        if (dao.register(email)) {
            return true;
        }

        return false;
    }

    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        for (Cookie c : cookies) {
            if (c.getName().equals("Admin") || c.getName().equals("Client")) {
                c.setMaxAge(0);
                c.setPath("/");
                response.addCookie(c);
            }
        }

        return true;
    }
}
