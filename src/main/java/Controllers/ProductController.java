package Controllers;

import DAOs.BrandDAO;
import DAOs.ProductDAO;
import Models.Brand;
import Models.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

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
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
                || (path.startsWith(LIST_URI)
                        || path.startsWith(LIST_URI + "/BrandID")
                        || path.startsWith(LIST_URI + "/page"))
                        && (request.getQueryString() != null && !request.getQueryString().isEmpty()))) {
            int kq = -1;
            if (!path.endsWith("?errPNF=true")) {
                kq = ProductFilter(request, response);
            }
            // if ((int) request.getAttribute("page") > (int)
            // request.getAttribute("numberOfPage")) {
            // response.sendError(HttpServletResponse.SC_NOT_FOUND);
            // } else {
            // }
            if (kq == Action.Reset_Page.value) {
                if (request.getAttribute("redirectPath") != null) {
                    response.sendRedirect((String) request.getAttribute("redirectPath"));
                }
                return;
            }
            if (kq == Action.Throw404.value) {
                response.sendRedirect(LIST_URI + checkException(request));
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
        System.out.println("Search la:" + search);

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
        System.out.println("Current page:" + currentPage);

        if (search == null || search.equals("")) {
            search = "%";
        }
        // Search
        List<Product> searchProductList = pDAO.searchProduct(search);
        System.out.println("Product size:" + searchProductList.size());

        // Then filter
        List<Product> filteredProductList = pDAO.filterActiveProduct(searchProductList, brandId, gender, price);
        System.out.println("Filteredproduct size:" + filteredProductList.size());

        int numberOfPages = filteredProductList.size() / pDAO.ROWS + (filteredProductList.size() % pDAO.ROWS);
        if (filteredProductList.isEmpty()) {
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
        List<Brand> brandList = bDao.getAll();
        List<Product> pagingFilteredProductList = pDAO.pagingProduct(filteredProductList, currentPage);
        request.setAttribute("productList", pagingFilteredProductList);
        request.setAttribute("shopName", shopName);
        request.setAttribute("brandList", brandList);
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
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // ------------------------- EXEPTION HANDLING SECTION -------------------------
    private String checkException(HttpServletRequest request) {
        if (request.getAttribute("exceptionType") == null) {
            return "";
        }
        String exception = "?err";

        switch ((String) request.getAttribute("exceptionType")) {
            case "WrongPasswordException":
            case "AccountNotFoundException":
                exception += "AccNF";
                break;
            case "ProductNotFoundException":
                exception += "PNF";
                break;
            case "AccountDeactivatedException":
                exception += "AccD";
                break;
            case "EmailDuplicationException":
                exception += "Email";
                break;
            case "UsernameDuplicationException":
                exception += "Username";
                break;
            case "PhoneNumberDuplicationException":
                exception += "Phone";
            case "NotEnoughInformationException":
                exception += "NEInfo";

            default:
                break;
        }
        exception += "=true";
        return exception;
    }

}
