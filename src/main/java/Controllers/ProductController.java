package Controllers;

import DAOs.BrandDAO;
import DAOs.ProductDAO;
import Exceptions.ProductNotFoundException;
import Lib.ExceptionUtils;
import Lib.Generator;
import Models.Brand;
import Models.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductController extends HttpServlet {

    public static final String LIST_URI = "/Product/List";
    public static final String DETAIL_URI = "/Product/Detail";

    public enum Action {
        Reset_Page(0),
        Throw404(404),
        Success(1);

        private final int value;

        private Action(int value) {
            this.value = value;
        }
    }

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
        if ((path.endsWith(LIST_URI)
                || ((path.startsWith(LIST_URI) && (request.getQueryString() != null && !request.getQueryString().isEmpty()))
                || path.startsWith(LIST_URI + "/BrandID")
                || path.startsWith(LIST_URI + "/page")))) {
            int kq = -1;
            if (!path.endsWith("?errPNF=true")) {
                kq = ProductFilter(request, response);
            }
            if (kq == Action.Reset_Page.value) {
                if (request.getAttribute("redirectPath") != null) {
                    response.sendRedirect((String) request.getAttribute("redirectPath"));
                }
                return;
            }
            if (kq == Action.Throw404.value) {
                response.sendRedirect(LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
                return;
            }

            BrandDAO bDao = new BrandDAO();
            List<Brand> brandList = bDao.getAll();
            request.setAttribute("brandList", brandList);
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

    /**
     * Get the attributes from the request URL then set it to the request.
     * Product/List/BrandID/2?Gender=Male&Price=low
     */
    private int ProductFilter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDao = new BrandDAO();

        String gender = request.getParameter("Gender");
        String price = request.getParameter("priceRange");
        String search = request.getParameter("txtSearch");
        String path = request.getRequestURL().toString();
        String data[] = path.split("/");

        // Get the brandID and the page in the URL, redirect to product list when occur
        // an exception
        int brandId = -1;
        int currentPage = 1;
        try {
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals("BrandID")) {
                    brandId = Integer.parseInt(data[i + 1]);
                } else if (data[i].equals("page")) {
                    currentPage = Integer.parseInt(data[i + 1]);
                }
            }
        } catch (NumberFormatException ex) {
            response.sendRedirect("/Product/List");
            return Action.Reset_Page.value;
        }

        if (search == null || search.equals("")) {
            search = "%";
        } else {
            search = "%" + search + "%";
        }
        // Search
        System.out.println("Search la:" + search);
        System.out.println("brandId:" + brandId);
        System.out.println("currentPage:" + currentPage);

        List<Product> searchProductList;
        List<Product> filteredProductList;
        int numberOfPages;
        int rows = 20;
        try {
            searchProductList = pDAO.searchProduct(search);

            // Then filter
            filteredProductList = pDAO.filterActiveProduct(searchProductList, brandId, gender, price);

            numberOfPages = (filteredProductList.size() / rows) + (filteredProductList.size() % rows == 0 ? 0 : 1);

        } catch (ProductNotFoundException ex) {
            request.setAttribute("exceptionType", "ProductNotFoundException");
            return Action.Throw404.value;
        }
        // If user in the page the greater than numberOfPages then reset it back to 1
        if (numberOfPages < currentPage && !filteredProductList.isEmpty()) {
            String redirectPath = path;
            redirectPath = redirectPath.replace("/page/" + currentPage, "/page/1")
                    + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
            request.setAttribute("redirectPath", redirectPath);
            return Action.Reset_Page.value;
        }

        // Send data to JSP
        String root = path.replace(request.getRequestURI(), "");
        String currentURL = root + LIST_URI;
        currentURL += (brandId == -1 ? "" : "/BrandID/" + brandId);

        request.setAttribute("brandId", brandId);
        request.setAttribute("gender", gender);
        request.setAttribute("price", price);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentURL", currentURL);

        String shopName = "shop";
        Brand brand = bDao.getBrand(brandId);
        if (brand != null) {
            shopName = brand.getName();
        }
        // Pagination
        List<Product> pagingFilteredProductList = Generator.pagingList(filteredProductList, currentPage, rows);
        request.setAttribute("productList", pagingFilteredProductList);
        request.setAttribute("shopName", shopName);
        return Action.Success.value;
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
                try {
                    int id = Integer.parseInt(data[i + 1]);
                    Product p = pDAO.getActiveProduct(id);

                    if (p != null) {
                        request.setAttribute("product", p);
                        return true;
                    }
                } catch (ProductNotFoundException ex) {
                    request.setAttribute("exceptionType", "ProductNotFoundException");
                    return false;
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

    // ------------------------- EXEPTION HANDLING SECTION -------------------------
}
