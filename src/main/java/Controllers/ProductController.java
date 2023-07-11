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

    public static final String LIST_URI = "/Product/List";
    public static final String DETAIL_URI = "/Product/Detail";

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

        // Filter
        // Product/List
        // Product/List?Gender=Male&priceRange=1500000-3000000
        // Product/List/page/2
        // Product/List/BrandID/2
        // Product/List/BrandID/1?Gender=Male&priceRange=1500000-3000000
        // Product/List/BrandID/2/page/2
        // Product/List/BrandID/page/2?Gender=Male&priceRange=1500000-3000000
        if (path.endsWith(LIST_URI)
                || (path.startsWith(LIST_URI)
                && (request.getQueryString() != null && !request.getQueryString().isEmpty()))
                || path.startsWith(LIST_URI + "/BrandID")
                || path.startsWith(LIST_URI + "/page")) {
            int kq = ProductFilter(request, response);
            // if ((int) request.getAttribute("page") > (int) request.getAttribute("numberOfPage")) {
            //     response.sendError(HttpServletResponse.SC_NOT_FOUND);
            // } else {
            // }
            if (kq == 0) {
                if (request.getAttribute("redirectPath") != null) {
                    response.sendRedirect((String)request.getAttribute("redirectPath"));
                }
                return;
            }
            request.getRequestDispatcher("/PRODUCT_PAGE/list.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(DETAIL_URI)) {
            // System.out.println("path.startsWith(DETAIL_URI)");
            if (handleProductDetail(request, response)) {
                request.getRequestDispatcher("/PRODUCT_PAGE/detail.jsp").forward(request, response);
            } else {
                response.sendRedirect(LIST_URI);
            }
            return;
        }

        // response.sendRedirect(LIST_URI);
    }

    private void getAllProduct(HttpServletRequest request, HttpServletResponse response) {
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDao = new BrandDAO();
        ResultSet rs = null, bdRs = null;

        String shop = "shop";

        bdRs = bDao.getAll();
        // rs = pDAO.getAll();

        request.setAttribute("shopName", shop);
        request.setAttribute("PDResultSet", rs);
        request.setAttribute("BDResultSet", bdRs);
    }

    /**
     * Get the attributes from the request URL then set it to the request.
     * Product/List/BrandID/2?Gender=Male&Price=low
     *
     */
    private int ProductFilter(HttpServletRequest request, HttpServletResponse response) {
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDao = new BrandDAO();
        ResultSet bdRs = bDao.getAll();
        String shop = "shop";

        String gender = request.getParameter("Gender");
        String price = request.getParameter("priceRange");
        String Search = request.getParameter("txtSearch");
        String path = request.getRequestURL().toString();
        String data[] = path.split("/");
        final int ROWS = 20;
        System.out.println("Search la:" + Search);
        String brandID = null;
        int page = 1;
        int tempPage = 1;
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("BrandID")) {
                brandID = data[i + 1];
            } else if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
                tempPage = page;
            }
        }
        if (Search == null || Search.equals("")) {
            Search = "%";
        }
        int NumberOfProduct = pDAO.GetNumberOfProduct(brandID, gender, price, Search);
        int NumberOfPage = NumberOfProduct / ROWS;
        NumberOfPage = (NumberOfProduct % ROWS == 0 ? NumberOfPage : NumberOfPage + 1);

        if (NumberOfPage < page) {
            String redirectPath = path;
            redirectPath = redirectPath.replace("/page/" + tempPage, "/page/1") + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
            request.setAttribute("redirectPath", redirectPath);
            return 0;
        }

        String root = path.replace(request.getRequestURI(), "");
        String currentURL = root + LIST_URI;
        currentURL += (brandID == null ? "" : "/BrandID/" + brandID);
        // currentURL += (page == 1 ? "" : "/page/" + page);
        // currentURL += (request.getQueryString() == null ? "" : "?" +
        // request.getQueryString());

        // System.out.println(String.format("BrandID: %s, gender: %s, price: %s",
        // brandID, gender, price));
        request.setAttribute("BrandID", brandID);
        request.setAttribute("gender", gender);
        request.setAttribute("price", price);
        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", NumberOfPage);
        request.setAttribute("currentURL", currentURL);

        if (brandID != null && (bDao.getBrandName(Integer.parseInt(brandID)) != null)) {
            shop = bDao.getBrandName(Integer.parseInt(brandID));
        }
        request.setAttribute("PDResultSet", pDAO.getFilteredProduct(brandID, gender, price, page, Search));
        request.setAttribute("shopName", shop);
        request.setAttribute("BDResultSet", bdRs);
        return 1;
    }

    /**
     * Set attribute cho request hoac session su dung ProductDAO, BrandDAO
     * Product/Detail/ID/1
     */
    private boolean handleProductDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDAO pDAO = new ProductDAO();

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
