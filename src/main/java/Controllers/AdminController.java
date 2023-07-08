
package Controllers;

import java.io.InputStream;
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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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

    public static final String IMGUR_API_ENDPOINT = "https://api.imgur.com/3/image";
    public static final String IMGUR_CLIENT_ID = "87da474f87f4754";

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

        // ---------------------------- PRODUCT SECTION ----------------------------
        if (path.startsWith(ADMIN_PRODUCT_LIST_URI) || path.startsWith(ADMIN_PRODUCT_LIST_URI + "/page")) {
            searchProduct(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/Product/list.jsp").forward(request, response);
            return;
        }
        if (path.startsWith(ADMIN_PRODUCT_DELETE_URI)) {
            deleteProduct(request, response);
            response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
            return;
        }
        if (path.startsWith(ADMIN_PRODUCT_ADD_URI)) {
            request.getRequestDispatcher("/ADMIN_PAGE/Product/add.jsp").forward(request, response);
            return;
        }
        if (path.startsWith(ADMIN_PRODUCT_UPDATE_URI)) {
            if (handleUpdateProduct(request, response)) {
                request.getRequestDispatcher("/ADMIN_PAGE/Product/update.jsp").forward(request, response);
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

        // ---------------------------- USER SECTION ----------------------------
        if (path.startsWith(ADMIN_USER_LIST_URI) || path.startsWith(ADMIN_USER_LIST_URI + "/page")) {
            searchUser(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/User/list.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(ADMIN_USER_UPDATE_URI)) {
            if (handleUpdateUser(request, response)) {
                request.getRequestDispatcher("/ADMIN_PAGE/User/update.jsp").forward(request, response);
            } else {
                response.sendRedirect(ADMIN_USER_LIST_URI);
            }
            return;
        }

        if (path.startsWith(ADMIN_USER_DELETE_URI)) {
            deleteUser(request, response);
            response.sendRedirect(ADMIN_USER_LIST_URI);
            return;
        }

        if (path.startsWith(ADMIN_USER_RESTORE_URI)) {
            restoreUser(request, response);
            response.sendRedirect(ADMIN_USER_LIST_URI);
            return;
        }

        // ---------------------------- DEFAULT SECTION ----------------------------
        if (path.startsWith(ADMIN_USER_URI)) { // Put this at the last
            request.getRequestDispatcher("/ADMIN_PAGE/admin.jsp").forward(request, response);
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

        if (path.startsWith(ADMIN_USER_UPDATE_URI)) {
            if (request.getParameter("btnUpdateUser") != null
                    && request.getParameter("btnUpdateUser").equals("Submit")) {
                updateUser(request, response);
                response.sendRedirect(ADMIN_USER_LIST_URI);
            }
            return;
        }

    }

    /* CRUD */
    // ---------------------------- CREATE SECTION ----------------------------
    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
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
        Part imgPart = request.getPart("fileProductImg");

        // Upload to Imgur database
        String ImgURL = uploadImageToClound(imgPart);

        String addData = pDAO.convertToStringData(pName, bName, pPrice, Gender, Smell, Quantity + "", ReleaseYear + "",
                Volume + "", ImgURL, Description);
        // Upload data to db
        int kq = pDAO.addProduct(addData);
        if (kq == 0) {
            System.out.println("Add failed, Some attribute may be duplicate");
            return;
        }
        System.out.println("Add successfully");

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
        request.setAttribute("listProduct", listProduct);
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
        request.setAttribute("listUser", listUser);
        request.setAttribute("Search", Search);
    }

    // ---------------------------- UPDATE SECTION ----------------------------
    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Get the text parameter in order to update
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

        // Get the part of the image
        Part imagePart = null;
        imagePart = request.getPart("fileProductImg");
        System.out.println("Part of the Product : " + imagePart.getName() + " ||| " + imagePart.getSize());

        String ImgURL = "";
        // Check if user update image
        if (imagePart == null || imagePart.getSize() == 0) {
            ImgURL = oldProduct.getImgURL();
        } else {
            // Upload to Imgur Database and save new URL
            ImgURL = uploadImageToClound(imagePart);
        }

        String updateData = pDAO.convertToStringData(pName, bName, pPrice, Gender, Smell, Quantity + "",
                ReleaseYear + "",
                Volume + "", ImgURL, Description);
        int kq = pDAO.updateProduct(pID, updateData);
        if (kq == 0) {
            System.out.println("Update Failed, The Product is not in the database");
            return;
        }
        System.out.println("Update Product with ID: " + pID + " successfully!");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDAO = new UserDAO();

        int uID = Integer.parseInt(request.getParameter("txtUserID"));
        String uName = request.getParameter("txtName");
        String uUserName = request.getParameter("txtUsername");
        String uPassword = request.getParameter("password");
        uPassword = uDAO.getMD5hash(uPassword);
        String uPhoneNumber = request.getParameter("txtPhoneNumber");
        String uEmail = request.getParameter("txtEmail");
        String uAddress = request.getParameter("txtAddress");
        String uRole = request.getParameter("txtRole");

        boolean isExistUsername = uDAO.isExistUsernameEceptItself(uUserName, uID);
        boolean isExistPhone = uDAO.isExistPhoneEceptItself(uPhoneNumber, uID);
        boolean isExistEmail = uDAO.isExistEmailEceptItself(uEmail, uID);
        if (isExistUsername || isExistPhone || isExistEmail) {
            System.out.println("Update fail because Username or Phone or Email exist");
            return;
        }
        User updateUser = new User(uID, uName, uUserName, uPassword, uEmail, uPhoneNumber, uAddress, uRole);
        uDAO.updateUser(updateUser);
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

    private void restoreUser(HttpServletRequest request, HttpServletResponse response) {
        // Admin/User/Restore/ID/1
        String path = request.getRequestURI();
        String data[] = path.split("/");

        UserDAO uDAO = new UserDAO();
        Integer userId = null;

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                userId = Integer.parseInt(data[i + 1]);
            }
        }

        if (userId == null) {
            System.out.println("Restore Failed, Lacking userID");
            return;
        }

        int kq = uDAO.restoreUser(userId);
        if (kq == 0) {
            System.out.println("Restore Failed, The User is not in the database");
            return;
        }
        System.out.println("Restore User with ID: " + userId + " successfully!");
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

    private boolean handleUpdateUser(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDAO = new UserDAO();
        String data[] = request.getRequestURI().split("/");
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                int UserID = Integer.parseInt(data[i + 1]);
                User us = uDAO.getUser(UserID);
                if (us != null) {
                    request.setAttribute("UserUpdate", us);
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

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        // Admin/User/Delete/ID/1
        String path = request.getRequestURI();
        String data[] = path.split("/");

        UserDAO uDAO = new UserDAO();
        Integer userId = null;

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                userId = Integer.parseInt(data[i + 1]);
            }
        }

        if (userId == null) {
            System.out.println("Deactivate Failed, Lacking userID");
            return;
        }

        int kq = uDAO.deactivateUser(userId);
        if (kq == 0) {
            System.out.println("Deactivate Failed, The User is not in the database");
            return;
        }
        System.out.println("Deactivated User with ID: " + userId + " successfully!");
    }

    // ---------------------------- CLOUD SECTION ----------------------------

    private String uploadImageToClound(Part imagePart) throws IOException {
        String imgURL = "fail";
        File imageFile = convertPartToFile(imagePart);
        // File imageFile = new File("D:\\Images\\Anime Image\\Untitled.png");

        // File imageFile = new File("D:\\Images\\Anime Image\\Untitled.png");
        // Create URL object
        URL url = new URL(IMGUR_API_ENDPOINT);
        HttpURLConnection URLconn = (HttpURLConnection) url.openConnection();

        // Set the request method to POST
        URLconn.setRequestMethod("POST");

        // Set the Authorization header
        URLconn.setRequestProperty("Authorization", "Client-ID " + IMGUR_CLIENT_ID);

        // Enable input and output streams
        URLconn.setDoInput(true);
        URLconn.setDoOutput(true);

        // Create a boundary for the multipart/form-data
        String boundary = "---------------------------" + System.currentTimeMillis();

        // Set the content type as multipart/form-data with the boundary
        URLconn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        // Create the request body
        DataOutputStream rq = new DataOutputStream(URLconn.getOutputStream());
        rq.writeBytes("--" + boundary + "\r\n");
        rq.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"" + imageFile.getName() + "\"\r\n");
        rq.writeBytes("Content-Type: application/octet-stream\r\n\r\n");

        // Read the image file and write it to the rq body
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            rq.write(buffer, 0, bytesRead);
        }
        rq.writeBytes("\r\n");
        rq.writeBytes("--" + boundary + "--\r\n");
        rq.flush();
        rq.close();

        // Get the response
        int responseCode = URLconn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            InputStreamReader inputStreamReader = new InputStreamReader(URLconn.getInputStream(),
                    StandardCharsets.UTF_8);
            StringBuilder rp = new StringBuilder();
            int c;
            while ((c = inputStreamReader.read()) != -1) {
                rp.append((char) c);
            }
            inputStreamReader.close();

            // Extract the image URL from the response
            String imageUrl = extractImageUrl(rp.toString());
            imgURL = imageUrl;

            // Display the image URL
            System.out.println("Image uploaded successfully. Image URL: " + imageUrl);

        } else {
            System.out.println("Error occurred while uploading the image. Response Code: " + responseCode);
        }
        URLconn.disconnect();
        fileInputStream.close();
        return imgURL;
    }

    public static File convertPartToFile(Part part) throws IOException {
        // String fileName = part.getSubmittedFileName();
        File tempFile = File.createTempFile("temp", null);
        tempFile.deleteOnExit();

        try (InputStream inputStream = part.getInputStream();
                OutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }

    private String extractImageUrl(String response) {
        // Parse the JSON response to extract the image URL
        int startIndex = response.indexOf("\"link\":\"") + 8;
        int endIndex = response.indexOf("\"", startIndex);
        return response.substring(startIndex, endIndex);
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
