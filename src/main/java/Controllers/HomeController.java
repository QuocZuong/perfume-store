
package Controllers;

import static Controllers.CustomerController.CUSTOMER_CART_URI;
import Lib.EmailSender;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    // ---------------------------- URI DECLARATION SECTION ----------------------------//
    public static final String CLIENT_SUBSCRIBE_URL = "/home/subscribe";
    public static final String HOME_URI = "/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.equals("/")) {
            handleRole(request, response);
            request.getRequestDispatcher("/HOME_PAGE/homePage.jsp").forward(request, response);
            return;
        }
        if (path.equals("/home/introduction")) {
            handleRole(request, response);
            request.getRequestDispatcher("/HOME_PAGE/introduction.jsp").forward(request, response);
            return;
        }
        if (path.equals("/home/brand")) {
            handleRole(request, response);
            request.getRequestDispatcher("/HOME_PAGE/brand.jsp").forward(request, response);
            return;
        }

        // if (path.equals("/home/login")) {
        // request.getRequestDispatcher("/HOME_PAGE/logIn.jsp").forward(request,
        // response);
        // return;
        // }
        request.getRequestDispatcher("/HOME_PAGE/homePage.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.endsWith(CLIENT_SUBSCRIBE_URL)) {
            System.out.println("vao controller subscribe");
            if (request.getParameter("submitEmailBtn") != null
                    && request.getParameter("submitEmailBtn").equals("Submit")) {
                System.out.println("Going Subscribe");
                sendEmailSubscribe(request, response);
                response.sendRedirect(HOME_URI);
                return;
            }
        }
    }

    public String getSessionUserRole(HttpServletRequest request, HttpServletResponse response) {
         Cookie userCookie = (Cookie) request.getSession().getAttribute("userCookie");
         if(userCookie != null){
            return userCookie.getName();
         }
         return "Anonymous";
    }
    public void handleRole(HttpServletRequest request, HttpServletResponse response){
        String Role = getSessionUserRole(request, response);
        System.out.println("role:"+ Role);
        if(Role.equals("Client")){
            request.setAttribute("UserRole", "/Client/User");
        }else if(Role.equals("Admin")){
            request.setAttribute("UserRole", "/Admin");
        }
    }
    
     // ------------------------- SEND EMAIL SUBSCRIBE -------------------------
    private void sendEmailSubscribe(HttpServletRequest request, HttpServletResponse response){
        String emailTo = request.getParameter("txtEmailSubscribe");
        EmailSender es = new EmailSender();
        es.setEmailTo(emailTo);
        String html = es.getEmailSubscribe();
        String subject = "ĐĂNG KÝ NHẬN TIN TỪ XXVI STORE THÀNH CÔNG!";
        try {
            es.sendToEmail(subject, html);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("Send mail fail");
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
