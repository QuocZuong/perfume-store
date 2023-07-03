package Controllers;

import DAOs.BrandDAO;
import DAOs.ProductDAO;
import Models.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

public class ProductController extends HttpServlet {
    
    public static final String LIST_URL = "/Product/List";
    public static final String DETAIL_URL = "/Product/Detail";

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
        
        if (path.endsWith(LIST_URL)) {
            getAllProduct(request, response);
            request.getRequestDispatcher("/PRODUCT_PAGE/list.jsp").forward(request, response);
            return;
        }
        if (path.startsWith(DETAIL_URL)) {
            // Set attribute
            if (handleProductDetail(request, response)) {
                // Forward
                request.getRequestDispatcher("/PRODUCT_PAGE/detail.jsp").forward(request, response);
            } else {
                response.sendRedirect(LIST_URL);
            }
            return;
        }
        
        response.sendRedirect(LIST_URL);
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
     * Set attribute cho request hoac session su dung ProductDAO, BrandDAO
     * Product/Detail/ID/1
     */
    private boolean handleProductDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDao = new BrandDAO();
        
        String data[] = request.getRequestURI().split("/");
        
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                int id = Integer.parseInt(data[i + 1]);
                Product p = pDAO.getProduct(id);
                
                if (p != null) {
                    request.setAttribute("product", p);
                    return true;
                }
                
            }
        }
        
        return false;
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
