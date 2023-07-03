
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HomeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        if (path.equals("/")) {
            request.getRequestDispatcher("/HOME_PAGE/homePage.jsp").forward(request, response);
            return;
        }
        if (path.equals("/home/introduction")) {
            request.getRequestDispatcher("/HOME_PAGE/introduction.jsp").forward(request, response);
            return;
        }
        if (path.equals("/home/brand")) {
            request.getRequestDispatcher("/HOME_PAGE/brand.jsp").forward(request, response);
            return;
        }

//        if (path.equals("/home/login")) {
//            request.getRequestDispatcher("/HOME_PAGE/logIn.jsp").forward(request, response);
//            return;
//        }
        request.getRequestDispatcher("/HOME_PAGE/homePage.jsp").forward(request, response);
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

    }
}
