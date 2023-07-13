package Controllers;

import Models.User;
import DAOs.UserDAO;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import Models.Order;
import Models.Product;
import DAOs.OrderDAO;
import DAOs.ProductDAO;
import Exceptions.AccountDeactivatedException;
import Exceptions.EmailDuplicationException;
import Exceptions.PhoneNumberDuplicationException;
import Exceptions.UsernameDuplicationException;
import Exceptions.WrongPasswordException;
import Lib.EmailSender;
import java.sql.ResultSet;
import java.io.InputStream;
import java.util.ArrayList;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Cookie;
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
import java.io.UnsupportedEncodingException;
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
    public static final String ADMIN_CLIENT_DETAIL_URI = "/Admin/User/Detail";
    public static final String ADMIN_CLIENT_ORDER_URI = "/Admin/User/OrderDetail";


    public static final String ADMIN_UPDATE_INFO_URI = "/Admin/Update/Info";

    public static final String IMGUR_API_ENDPOINT = "https://api.imgur.com/3/image";
    public static final String IMGUR_CLIENT_ID = "87da474f87f4754";

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
        
        if(path.startsWith(ADMIN_CLIENT_DETAIL_URI)){
            clientDetail(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/User/detail.jsp").forward(request, response);
            return;
        }
        if(path.startsWith(ADMIN_CLIENT_ORDER_URI)){
            OrderDetail(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/User/orderDetail.jsp").forward(request, response);
            return;
        }

        // ---------------------------- DEFAULT SECTION ----------------------------
        if (path.startsWith(ADMIN_USER_URI)) { // Put this at the last
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            request.getRequestDispatcher("/ADMIN_PAGE/admin.jsp").forward(request, response);
            return;
        }
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

        if (path.startsWith(ADMIN_UPDATE_INFO_URI)) {
            if (request.getParameter("btnUpdateInfo") != null
                    && request.getParameter("btnUpdateInfo").equals("Submit")) {
                System.out.println("Going update info");
                if (updateAdminInfomation(request, response)) {
                    response.sendRedirect(ADMIN_USER_URI);
                } else {
                    response.sendRedirect(ADMIN_USER_URI + checkException(request));
                }
                return;
            }
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
                // String phoneNumber = rs.getString("PhoneNumber") == null ? "" : rs.getString("PhoneNumber");
                // String phoneNumber = null;
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

    private void clientDetail(HttpServletRequest request, HttpServletResponse response) {
        OrderDAO oDAO = new OrderDAO();
        UserDAO uDAO = new UserDAO();
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int ClientID = -1;
        for(int i = 0; i < data.length; i++){
            if (data[i].equals("ID")) {
                ClientID = Integer.parseInt(data[i + 1]);
            }
        }
        List<Order> orderList = oDAO.getOrderByClientId(ClientID);
        User client = uDAO.getUser(ClientID);
        request.setAttribute("client", client);
        request.setAttribute("orderList", orderList);
    }
    
    private void OrderDetail(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDAO = new UserDAO();
        OrderDAO oDAO = new OrderDAO();
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int OrderID = -1;
        for(int i = 0; i < data.length; i++){
            if (data[i].equals("ID")) {
                OrderID = Integer.parseInt(data[i + 1]);
            }
        }
        List<String[]> orderDetail = oDAO.getOrderDetailByOrderID(OrderID);
        Order OrderInfor = oDAO.getOrderByOrderId(OrderID);
        User client = uDAO.getUser(OrderInfor.getClientID());
        request.setAttribute("client", client);
        request.setAttribute("OrderInfor", OrderInfor);
        request.setAttribute("OrderDetail", orderDetail);
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

    private boolean updateUser(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDAO = new UserDAO();

        int uID = Integer.parseInt(request.getParameter("txtUserID"));
        String uName = request.getParameter("txtName");
        String uUserName = request.getParameter("txtUsername");

        String uPassword = request.getParameter("txtPassword");

        // For sending email
        boolean isChangedEmail = false;
        boolean isChangedPassword = false;
        boolean isChangedUsername = false;

        // Only hash the new password if the password is different from the user's old
        // md5 password.
        System.out.println("Compare password: " + uPassword + " vs " + uDAO.getUser(uID).getPassword());
        if (!uPassword.equals(uDAO.getUser(uID).getPassword())) {
            isChangedPassword = true;
            System.out.println("New password: " + uPassword);
            uPassword = uDAO.getMD5hash(uPassword);
            System.out.println("New hashed password: " + uPassword);
        }

        String uPhoneNumber = request.getParameter("txtPhoneNumber");
        String uEmail = request.getParameter("txtEmail");

        if (!uEmail.equals(uDAO.getUser(uID).getEmail())) {
            isChangedEmail = true;
        }
        if(!uUserName.equals(uDAO.getUser(uID).getUsername())){
            isChangedUsername = true;
        }

        String uAddress = request.getParameter("txtAddress");
        String uRole = request.getParameter("txtRole");

        boolean isExistUsername = uDAO.isExistUsernameExceptItself(uUserName, uID);
        boolean isExistPhone = uDAO.isExistPhoneExceptItself(uPhoneNumber, uID);
        boolean isExistEmail = uDAO.isExistEmailExceptItself(uEmail, uID);

        try {
            if (isExistUsername || isExistPhone || isExistEmail) {
                if (isExistUsername) {
                    throw new UsernameDuplicationException();
                }
                if (isExistPhone) {
                    throw new PhoneNumberDuplicationException();
                }
                if (isExistEmail) {
                    throw new EmailDuplicationException();
                }
            }
        } catch (UsernameDuplicationException ex) {
            request.setAttribute("exceptionType", "UsernameDuplicationException");
            return false;
        } catch (PhoneNumberDuplicationException ex) {
            request.setAttribute("exceptionType", "PhoneNumberDuplicationException");
            return false;
        } catch (EmailDuplicationException ex) {
            request.setAttribute("exceptionType", "EmailDuplicationException");
            return false;
        }

        // Sending mail
        try {
            EmailSender es = new EmailSender();
            if (isChangedPassword) {
                System.out.println("Detect password change");
                System.out.println("sending mail changing password");
                es.setEmailTo(uEmail);
                es.sendToEmail(es.CHANGE_PASSWORD_NOTFICATION,
                es.changePasswordNotifcation());
            }
            if (isChangedEmail) {
                System.out.println("Detect email change");
                System.out.println("sending mail changing email");
                es.setEmailTo(uDAO.getUser(uID).getEmail());
                es.sendToEmail(es.CHANGE_EMAIL_NOTFICATION,
                es.changeEmailNotification(uEmail));
            }
            if(isChangedUsername){
                System.out.println("Detect username change");
                System.out.println("sending mail changing username");
                es.setEmailTo(uEmail);
                es.sendToEmail(es.CHANGE_USERNAME_NOTFICATION, es.changeUsernameNotification(uUserName));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        User updateUser = new User(uID, uName, uUserName, uPassword, uEmail, uPhoneNumber, uAddress, uRole);
        uDAO.updateUser(updateUser);
        System.out.println("update account with ID " + updateUser.getID() + " successfully");
        return true;
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

    private boolean updateAdminInfomation(HttpServletRequest request, HttpServletResponse response) {
        /*
         * txtFullname
         * txtUserName
         * txtEmail
         * pwdCurrent
         * pwdNew
         * pwdConfirmNew
         * btnUpdateInfo (value = Submit)
         */

        String fullname = request.getParameter("txtFullname");

        String username = request.getParameter("txtUserName");
        String email = request.getParameter("txtEmail");
        String currentPassword = request.getParameter("pwdCurrent");
        String newPassword = "";
        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");

        UserDAO usDAO = new UserDAO();
        User user = usDAO.getUser(currentUserCookie.getValue());

        boolean isChangedEmail = true;
        boolean isChangedPassword = true;
        boolean isChangedUsername = true;
        // Username, email, phone number is unique
        try {
            if (!email.equals(user.getEmail())) {
                if (usDAO.isExistEmail(email)) {
                    throw new EmailDuplicationException();
                }
            } else {
                isChangedEmail = false;
            }

            if (!username.equals(user.getUsername())) {
                if (usDAO.isExistUsername(username)) {
                    throw new UsernameDuplicationException();
                }
            } else{
                isChangedUsername = false;
            }

            if (currentPassword == null || currentPassword.equals("")) {
                currentPassword = user.getPassword();
                isChangedPassword = false;
                // check if currentPassword is true
            } else if (!usDAO.login(user.getUsername(), currentPassword)) {
                throw new WrongPasswordException();
            }

            // Email and username duplication must come first
        } catch (WrongPasswordException e) {
            request.setAttribute("exceptionType", "WrongPasswordException");
            return false;
        } catch (AccountDeactivatedException e) {
            request.setAttribute("exceptionType", "AccountDeactivatedException");
            return false;
        } catch (AccountNotFoundException e) {
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return false;
        } catch (EmailDuplicationException e) {
            request.setAttribute("exceptionType", "EmailDuplicationException");
            return false;
        } catch (UsernameDuplicationException e) {
            request.setAttribute("exceptionType", "UsernameDuplicationException");
            return false;
        }
        System.out.println("change password is " + isChangedPassword);
        System.out.println("New password is before if: " + request.getParameter("pwdNew"));
        if (isChangedPassword && request.getParameter("pwdNew") != null && !request.getParameter("pwdNew").equals("")) {
            newPassword = request.getParameter("pwdNew");
            System.out.println("New password is before MD5: " + newPassword);
            newPassword = usDAO.getMD5hash(newPassword);
        } else {
            newPassword = user.getPassword();
        }

        User updateUser = new User(
                user.getID(),
                fullname,
                username,
                newPassword,
                email,
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRole());

        usDAO.updateUser(updateUser);

        // Update cookie
        Cookie c = ((Cookie) request.getSession().getAttribute("userCookie"));
        c.setValue(username);
        c.setPath("/");
        response.addCookie(c);

        // Sending mail
        try {
            EmailSender es = new EmailSender();
            if (isChangedPassword) {
                System.out.println("Detect password change");
                System.out.println("sending mail changing password");
                es.setEmailTo(email);
                es.sendToEmail(es.CHANGE_PASSWORD_NOTFICATION,
                        es.changePasswordNotifcation());
            }
            if (isChangedEmail) {
                System.out.println("Detect email change");
                System.out.println("sending mail changing email");
                es.setEmailTo(user.getEmail());
                es.sendToEmail(es.CHANGE_EMAIL_NOTFICATION,
                        es.changeEmailNotification(email));
            }
            if(isChangedUsername){
                System.out.println("Detect username change");
                System.out.println("sending mail changing username");
                es.setEmailTo(email);
                es.sendToEmail(es.CHANGE_USERNAME_NOTFICATION, es.changeUsernameNotification(username));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("update account with ID " + user.getID() + " successfully");
        return true;
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

        try ( InputStream inputStream = part.getInputStream();  OutputStream outputStream = new FileOutputStream(tempFile)) {
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
