
package Controllers;

import DAOs.BrandDAO;
import DAOs.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

public class ProductController extends HttpServlet {

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
        
        if (path.endsWith("/Product/List")) {
            getAllProduct(request, response);
            request.getRequestDispatcher("/PRODUCT_PAGE/list.jsp").forward(request, response);
            return;
        }
        
        response.sendRedirect("/");
    }
    
    private void getAllProduct(HttpServletRequest request, HttpServletResponse response) {
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDao = new BrandDAO();
        ResultSet rs = null, bdRs = null;
        String filter;
        String shop = "shop";

        // if ((filter = request.getParameter("filter_brand")) != null) {
        // rs = pDAO.getWithCondition(String.format("BrandID = '%s'", filter));
        // bdRs = bDao.getAll();
        // shop =
        // bDao.getBrandName(Integer.parseInt(request.getParameter("filter_brand")));
        // } else {
        // rs = pDAO.getAll();
        // bdRs = bDao.getAll();
        // }
        rs = pDAO.getAll();
        bdRs = bDao.getAll();
        
        request.setAttribute("shopName", shop);
        request.setAttribute("PDResultSet", rs);
        request.setAttribute("BDResultSet", bdRs);
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
