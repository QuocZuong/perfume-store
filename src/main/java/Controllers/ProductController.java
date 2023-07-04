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

        String path = request.getRequestURI()
                + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
        // System.out.println(request.getQueryString());
        // System.out.println(request.getRequestURL().toString());

        if (path.endsWith(LIST_URL)) {
            // System.out.println("path.endsWith(LIST_URL)");
            getAllProduct(request, response);
            request.getRequestDispatcher("/PRODUCT_PAGE/list.jsp").forward(request, response);
            return;
        }
        if (path.startsWith(DETAIL_URL)) {
            // System.out.println("path.startsWith(DETAIL_URL)");
            if (handleProductDetail(request, response)) {
                request.getRequestDispatcher("/PRODUCT_PAGE/detail.jsp").forward(request, response);
            } else {
                response.sendRedirect(LIST_URL);
            }
            return;
        }

        // Filter
        // Product/List?Gender=Male&priceRange=1500000-3000000
        // Product/List/page/2
        // Product/List/BrandID/2
        // Product/List/BrandID/1?Gender=Male&priceRange=1500000-3000000
        // Product/List/BrandID/2/page/2
        // Product/List/BrandID/page/2?Gender=Male&priceRange=1500000-3000000
        if ((path.startsWith(LIST_URL) && (request.getQueryString() != null && !request.getQueryString().isEmpty()))
                || path.startsWith(LIST_URL + "/BrandID")
                || path.startsWith(LIST_URL + "/page")) {
            // System.out.println("hello world");
            ProductFilter(request, response);
            request.getRequestDispatcher("/PRODUCT_PAGE/list.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(LIST_URL);
    }

    private void getAllProduct(HttpServletRequest request, HttpServletResponse response) {
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDao = new BrandDAO();
        ResultSet rs = null, bdRs = null;

        String shop = "shop";

        bdRs = bDao.getAll();
        rs = pDAO.getAll();

        request.setAttribute("shopName", shop);
        request.setAttribute("PDResultSet", rs);
        request.setAttribute("BDResultSet", bdRs);
    }

    /**
     * Get the attributes from the request URL then set it to the request.
     * Product/List/BrandID/2?Gender=Male&Price=low
     *
     */
    private void ProductFilter(HttpServletRequest request, HttpServletResponse response) {
        String gender = request.getParameter("Gender");
        String price = request.getParameter("priceRange");

        String path = request.getRequestURL().toString();
        String data[] = path.split("/");

        String brandID = null;
        int page = 1;

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("BrandID")) {
                brandID = data[i + 1];
            } else if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }

        // System.out.println(String.format("BrandID: %s, gender: %s, price: %s",
        // brandID, gender, price));
        request.setAttribute("BrandID", brandID);
        request.setAttribute("gender", gender);
        request.setAttribute("price", price);
        request.setAttribute("page", page);

        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDao = new BrandDAO();
        ResultSet bdRs = bDao.getAll();
        String shop = "shop";

        if (brandID != null && (bDao.getBrandName(Integer.parseInt(brandID)) != null)) {
            shop = bDao.getBrandName(Integer.parseInt(brandID));
        }

        request.setAttribute("PDResultSet", pDAO.getFilteredProduct(brandID, gender, price, page));
        request.setAttribute("shopName", shop);
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
