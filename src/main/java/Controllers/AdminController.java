
package Controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DAOs.ProductDAO;
import DAOs.UserDAO;
import Models.User;
import Models.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class AdminController extends HttpServlet {

    // ----------------------- URI DECLARATION SECTION ----------------------------
    public static final String ADMIN_USER_URI = "/Admin";
    public static final String ADMIN_PRODUCT_LIST_URI = "/Admin/Product/List";
    public static final String ADMIN_PRODUCT_ADD_URI = "/Admin/Product/Add";
    public static final String ADMIN_PRODUCT_UPDATE_URI = "/Admin/Product/Update";
    public static final String ADMIN_PRODUCT_DELETE_URI = "/Admin/Product/Delete";
    public static final String ADMIN_PRODUCT_RESTORE_URI = "/Admin/Product/Restore";

    public static final String ADMIN_USER_LIST_URI = "/Admin/User/List";
    public static final String ADMIN_USER_ADD_URI = "/Admin/User/Add";
    public static final String ADMIN_USER_UPDATE_URI = "/Admin/User/Update";
    public static final String ADMIN_USER_DELETE_URI = "/Admin/User/Delete";
    public static final String ADMIN_USER_RESTORE_URI = "/Admin/User/Restore";

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
        String path = request.getRequestURI();
        if (path.startsWith(ADMIN_USER_URI)) {
            request.getRequestDispatcher("/ADMIN_PAGE/admin.jsp").forward(request, response);
            return;
        }
        if (path.startsWith(ADMIN_PRODUCT_LIST_URI) || path.startsWith(ADMIN_PRODUCT_LIST_URI + "/page")) {
            searchProduct(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/product/list.jsp").forward(request, response);
            return;
        }
        if (path.startsWith(ADMIN_PRODUCT_DELETE_URI)) {
            deleteProduct(request, response);
            response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
            return;
        }
        if (path.startsWith(ADMIN_PRODUCT_ADD_URI)) {
            request.getRequestDispatcher("/ADMIN_PAGE/product/add.jsp").forward(request, response);
            return;
        }
        if (path.startsWith(ADMIN_PRODUCT_UPDATE_URI)) {
            if (handleUpdateProduct(request, response)) {
                request.getRequestDispatcher("/ADMIN_PAGE/product/update.jsp").forward(request, response);
            } else {
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
            }
            return;
        }
        if (path.startsWith(ADMIN_PRODUCT_RESTORE_URI)) {
            restoreProduct(request, response);
            response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
            return;
        }

        if (path.startsWith(ADMIN_USER_LIST_URI) || path.startsWith(ADMIN_USER_LIST_URI + "/page")) {
            searchUser(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/user/list.jsp").forward(request, response);
            return;
        }

        

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

        if (path.startsWith(ADMIN_PRODUCT_ADD_URI)) {
            if (request.getParameter("btnAddProduct") != null
                    && request.getParameter("btnAddProduct").equals("Submit")) {
                addProduct(request, response);
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
            }
            return;
        }
        if (path.startsWith(ADMIN_PRODUCT_UPDATE_URI)) {
            if (request.getParameter("btnUpdateProduct") != null
                    && request.getParameter("btnUpdateProduct").equals("Submit")) {
                updateProduct(request, response);
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
            }
            return;
        }

    }

    /* CRUD */
    // ---------------------------- CREATE SECTION ----------------------------
    private void addProduct(HttpServletRequest request, HttpServletResponse response) {
        ProductDAO pDAO = new ProductDAO();
        String pName = request.getParameter("txtProductName");
        String bName = request.getParameter("txtBrandName");
        String pPrice = ProductDAO
                .IntegerToMoney(Integer.parseInt(request.getParameter("txtProductPrice").replace(",", "")));
        String Gender = request.getParameter("rdoGender");
        String Smell = request.getParameter("txtProductSmell");
        int Quantity = Integer.parseInt(request.getParameter("txtProductQuantity").replace(",", ""));
        int ReleaseYear = Integer.parseInt(request.getParameter("txtProductReleaseYear"));
        int Volume = Integer.parseInt(request.getParameter("txtProductVolume").replace(",", ""));
        String Description = request.getParameter("txtProductDescription");

        String filename = (pDAO.getMaxProductID() + 1) + ".png";

        String ImgURL = "\\RESOURCES\\images\\products\\" + filename;
        String addData = pDAO.convertToStringData(pName, bName, pPrice, Gender, Smell, Quantity + "", ReleaseYear + "",
                Volume + "", ImgURL, Description);
        int kq = pDAO.addProduct(addData);
        if (kq == 0) {
            System.out.println("Add failed, Some attribute may be duplicate");
            return;
        }
        try {
            String Local_destination = getServletContext().getRealPath("/").replace("target\\SQLproject-1\\",
                    "src\\main\\webapp\\RESOURCES\\images\\products\\");
            String Target_destination = getServletContext().getRealPath("/") + "RESOURCES\\images\\products";
            Part imagePart = request.getPart("fileProductImg");
            pDAO.copyImg(imagePart, Local_destination, Target_destination, filename);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        System.out.println("Add successfully");
        // Local
        // :C:\Users\Acer\OneDrive\Desktop\#SU23\PRJ301\SQLproject\perfume-store\src\main\webapp\RESOURCES\images\products
        // Target:
        // C:\Users\Acer\OneDrive\Desktop\#SU23\PRJ301\SQLproject\perfume-store\target\SQLproject-1\RESOURCES\images\products
    }

    // ---------------------------- READ SECTION ----------------------------

    private void searchProduct(HttpServletRequest request, HttpServletResponse response) {
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        String Search = request.getParameter("txtSearch");
        ProductDAO pDAO = new ProductDAO();
        ResultSet rs = null;
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }
        if (Search == null || Search.equals("")) {
            Search = "%";
        }
        rs = pDAO.getFilteredProductForAdminSearch(page, Search);
        List<Product> listProduct = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int brandID = rs.getInt("BrandID");
                int price = rs.getInt("Price");
                String gender = rs.getString("Gender");
                String smell = rs.getString("Smell");
                int quantity = rs.getInt("Quantity");
                int releaseYear = rs.getInt("ReleaseYear");
                int volume = rs.getInt("Volume");
                String imgURL = rs.getString("ImgURL");
                String description = rs.getString("Description");
                boolean active = rs.getBoolean("Active");

                // Create a new Product object and add it to the list
                Product product = new Product(id, name, brandID, price, gender, smell, quantity, releaseYear, volume,
                        imgURL, description, active);
                listProduct.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int NumberOfProduct = pDAO.GetNumberOfProductForSearch(Search);
        final int ROWS = 20;
        int NumberOfPage = NumberOfProduct / ROWS;
        NumberOfPage = (NumberOfProduct % ROWS == 0 ? NumberOfPage : NumberOfPage + 1);
        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", NumberOfPage);
        request.setAttribute("listUser", listProduct);
        request.setAttribute("Search", Search);
    }

    private void searchUser(HttpServletRequest request, HttpServletResponse response) {
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        String Search = request.getParameter("txtSearch");
        UserDAO uDAO = new UserDAO();
        ResultSet rs = null;

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }

        if (Search == null || Search.equals("")) {
            Search = "%";
        }

        rs = uDAO.getFilteredUserForAdminSearch(page, Search);
        List<User> listUser = new ArrayList<>();

        try {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String userName = rs.getString("UserName");
                String password = rs.getString("Password");
                String phoneNumber = rs.getString("PhoneNumber");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                String role = rs.getString("Role");
                boolean active = rs.getBoolean("Active");

                // Create a new User object and add it to the list
                User user = new User(id, name, userName, password, phoneNumber, email, address, role);

                if (!active) {
                    user.setActive(false);
                }

                listUser.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int NumberOfUser = uDAO.GetNumberOfUserForSearch(Search);
        final int ROWS = 20;
        int NumberOfPage = NumberOfUser / ROWS;
        NumberOfPage = (NumberOfUser % ROWS == 0 ? NumberOfPage : NumberOfPage + 1);
        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", NumberOfPage);
        request.setAttribute("listProduct", listUser);
        request.setAttribute("Search", Search);
    }

    // ---------------------------- UPDATE SECTION ----------------------------
    private void updateProduct(HttpServletRequest request, HttpServletResponse response) {
        Part imagePart = null;
        try {
            imagePart = request.getPart("fileProductImg");
            System.out.println("Part of the Product : " + imagePart.getName() + " ||| " + imagePart.getSize());
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        ProductDAO pDAO = new ProductDAO();
        int pID = Integer.parseInt(request.getParameter("txtProductID"));
        Product oldProduct = pDAO.getProduct(pID);

        String pName = request.getParameter("txtProductName");
        String bName = request.getParameter("txtBrandName");
        String pPrice = ProductDAO
                .IntegerToMoney(Integer.parseInt(request.getParameter("txtProductPrice").replace(",", "")));
        String Gender = request.getParameter("rdoGender");
        String Smell = request.getParameter("txtProductSmell");
        int Quantity = Integer.parseInt(request.getParameter("txtProductQuantity").replace(",", ""));
        int ReleaseYear = Integer.parseInt(request.getParameter("txtProductReleaseYear"));
        int Volume = Integer.parseInt(request.getParameter("txtProductVolume").replace(",", ""));
        String Description = request.getParameter("txtProductDescription");
        String filename = pID + ".png";

        String ImgURL = "\\RESOURCES\\images\\products\\" + filename;
        if (imagePart == null || imagePart.getSize() == 0) {
            ImgURL = oldProduct.getImgURL();
        }
        String updateData = pDAO.convertToStringData(pName, bName, pPrice, Gender, Smell, Quantity + "",
                ReleaseYear + "",
                Volume + "", ImgURL, Description);
        int kq = pDAO.updateProduct(pID, updateData);
        if (kq == 0) {
            System.out.println("Update Failed, The Product is not in the database");
            return;
        }

        // Update Img
        String Local_destination = getServletContext().getRealPath("/").replace("target\\SQLproject-1\\",
                "src\\main\\webapp\\RESOURCES\\images\\products\\");
        String Target_destination = getServletContext().getRealPath("/") + "RESOURCES\\images\\products";
        if (imagePart != null && imagePart.getSize() != 0) {
            pDAO.copyImg(imagePart, Local_destination, Target_destination, filename);
        } else {

        }

        System.out.println("Update Product with ID: " + pID + " successfully!");
    }

    private void restoreProduct(HttpServletRequest request, HttpServletResponse response) {
        // Admin/Restore/ID/1
        String path = request.getRequestURI();
        String data[] = path.split("/");
        ProductDAO pDAO = new ProductDAO();
        String productId = null;
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                productId = data[i + 1];
            }
        }
        if (productId == null) {
            System.out.println("Restore Failed, Lacking productID");
            return;
        }

        int kq = pDAO.restoreProduct(Integer.parseInt(productId));
        if (kq == 0) {
            System.out.println("Restore Failed, The Product is not in the database");
            return;
        }
        System.out.println("Restore Product with ID: " + productId + " successfully!");
    }

    private boolean handleUpdateProduct(HttpServletRequest request, HttpServletResponse response) {
        ProductDAO pDAO = new ProductDAO();
        String data[] = request.getRequestURI().split("/");
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                int ProductID = Integer.parseInt(data[i + 1]);
                Product pd = pDAO.getProduct(ProductID);
                if (pd != null) {
                    request.setAttribute("ProductUpdate", pd);
                    return true;
                }
            }
        }
        return false;
    }

    // ---------------------------- DELETE SECTION ----------------------------
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
        // Admin/Delete/ID/1
        String path = request.getRequestURI();
        String data[] = path.split("/");
        ProductDAO pDAO = new ProductDAO();
        String productId = null;
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                productId = data[i + 1];
            }
        }
        if (productId == null) {
            System.out.println("Delete Failed, Lacking productID");
            return;
        }

        int kq = pDAO.deleteProduct(Integer.parseInt(productId));
        if (kq == 0) {
            System.out.println("Delete Failed, The Product is not in the database");
            return;
        }
        System.out.println("Delete Product with ID: " + productId + " successfully!");
    }

}
// ---------------------------- TRASH SECTION ----------------------------

// private void ProductFilter(HttpServletRequest request, HttpServletResponse
// response) {
// String URI = request.getRequestURI();
// String data[] = URI.split("/");
// int page = 1;
// ProductDAO pDAO = new ProductDAO();
// ResultSet rs = null;
// for (int i = 0; i < data.length; i++) {
// if (data[i].equals("page")) {
// page = Integer.parseInt(data[i + 1]);
// }
// }
// rs = pDAO.getFilteredProductForAdmin(page);

// int NumberOfProduct = pDAO.GetNumberOfProduct(null, null, null, page);
// final int ROWS = 20;
// int NumberOfPage = NumberOfProduct / ROWS;
// NumberOfPage = (NumberOfProduct % ROWS == 0 ? NumberOfPage : NumberOfPage +
// 1);

// request.setAttribute("page", page);
// request.setAttribute("numberOfPage", NumberOfPage);

// List<Product> listProduct = new ArrayList<>();
// try {
// while (rs.next()) {
// int id = rs.getInt("ID");
// String name = rs.getString("Name");
// int brandID = rs.getInt("BrandID");
// int price = rs.getInt("Price");
// String gender = rs.getString("Gender");
// String smell = rs.getString("Smell");
// int quantity = rs.getInt("Quantity");
// int releaseYear = rs.getInt("ReleaseYear");
// int volume = rs.getInt("Volume");
// String imgURL = rs.getString("ImgURL");
// String description = rs.getString("Description");
// boolean active = rs.getBoolean("Active");

// // Create a new Product object and add it to the list
// Product product = new Product(id, name, brandID, price, gender, smell,
// quantity, releaseYear, volume,
// imgURL, description, active);
// listProduct.add(product);
// }
// } catch (SQLException e) {
// e.printStackTrace();
// }
// request.setAttribute("listProduct", listProduct);
// }
