package Controllers;

import DAOs.AdminDAO;
import DAOs.BrandDAO;
import DAOs.CustomerDAO;
import DAOs.EmployeeDAO;
import DAOs.OrderDAO;
import Models.User;
import Models.Voucher;

import java.util.List;

import Models.Product;
import DAOs.ProductDAO;
import DAOs.UserDAO;
import DAOs.VoucherDAO;
import Exceptions.CitizenIDDuplicationException;
import Exceptions.EmailDuplicationException;
import Exceptions.PhoneNumberDuplicationException;
import Exceptions.ProductNotFoundException;
import Exceptions.UsernameDuplicationException;
import Lib.Converter;
import Lib.EmailSender;
import Lib.ExceptionUtils;
import Lib.Generator;
import Lib.ImageUploader;
import Models.Admin;
import Models.Stock;
import Models.Customer;
import Models.Employee;
import Models.Order;
import Models.OrderDetail;
import Models.Role;
import java.io.InputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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

    public static final String ADMIN_ORDER_LIST_URI = "/Admin/Order/List";
    public static final String ADMIN_ORDER_REQUEST_URI = "/Admin/Order/Request";
    public static final String ADMIN_ORDER_DETAIL_URI = "/Admin/Order/Detail";
    public static final String ADMIN_ORDER_DELETE_URI = "/Admin/Order/Delete";
    public static final String ADMIN_ORDER_RESTORE_URI = "/Admin/Order/Restore";

    public static final String ADMIN_USER_INFO = "/Admin/User/Info";
    public static final String ADMIN_USER_LIST_URI = "/Admin/User/List";
    public static final String ADMIN_USER_ADD_URI = "/Admin/User/Add";
    public static final String ADMIN_USER_UPDATE_CUSTOMER_URI = "/Admin/User/Update/Customer";
    public static final String ADMIN_USER_ADD_EMPLOYEE_URI = "/Admin/User/Add/Employee";
    public static final String ADMIN_USER_UPDATE_EMPLOYEE_URI = "/Admin/User/Update/Employee";
    public static final String ADMIN_USER_DELETE_URI = "/Admin/User/Delete";
    public static final String ADMIN_USER_RESTORE_URI = "/Admin/User/Restore";
    public static final String ADMIN_CLIENT_DETAIL_URI = "/Admin/User/Detail";
    public static final String ADMIN_CLIENT_ORDER_URI = "/Admin/User/OrderDetail";

    // Chart URL
    public static final String ADMIN_CHART_BEST_SELLING_PRODUCT_BY_GENDER = "/Admin/Chart/BestSellingProductByGender";
    public static final String ADMIN_CHART_BEST_SELLING_PRODUCT_BY_PRICE = "/Admin/Chart/BestSellingProductByPrice";
    public static final String ADMIN_CHART_TOTAL_ORDER = "/Admin/Chart/TotalOrder";

    public static final String ADMIN_UPDATE_INFO_URI = "/Admin/Update/Info";

    public static final String IMGUR_API_ENDPOINT = "https://api.imgur.com/3/image";
    public static final String IMGUR_CLIENT_ID = "87da474f87f4754";

    public enum State {
        Success(1),
        Fail(0);

        private int value;

        State(int value) {
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
        String path = request.getRequestURI();

        // ---------------------------- PRODUCT SECTION ----------------------------
        if (path.startsWith(ADMIN_PRODUCT_LIST_URI)
                || path.startsWith(ADMIN_PRODUCT_LIST_URI + "/page")) {
            int result = searchProduct(request, response);
            if (result == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/Product/list.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(ADMIN_PRODUCT_DELETE_URI)) {
            int result = deleteProduct(request, response);
            if (result == State.Success.value) {
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
            } else {
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(ADMIN_PRODUCT_ADD_URI)) {
            request.getRequestDispatcher("/ADMIN_PAGE/Product/add.jsp").forward(request, response);
            return;
        }
        // Load data to update page
        if (path.startsWith(ADMIN_PRODUCT_UPDATE_URI)) {
            if (getUpdateProduct(request, response) == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/Product/update.jsp").forward(request,
                        response);
            } else {
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(ADMIN_PRODUCT_RESTORE_URI)) {
            int result = restoreProduct(request, response);
            if (result == State.Success.value) {
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
            } else {
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        // ---------------------------- ORDER SECTION ----------------------------
        if (path.startsWith(ADMIN_ORDER_LIST_URI)
                || path.startsWith(ADMIN_ORDER_LIST_URI + "/page")) {
            int result = searchOrder(request);

            if (result == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/Order/list.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(ADMIN_ORDER_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }

            return;
        }

        // ---------------------------- USER SECTION ----------------------------
        if (path.startsWith(ADMIN_USER_INFO)) {
            userInfo(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/User/info.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(ADMIN_USER_LIST_URI) || path.startsWith(ADMIN_USER_LIST_URI + "/page")) {
            searchUser(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/User/list.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(ADMIN_USER_ADD_EMPLOYEE_URI)) {
            request.getRequestDispatcher("/ADMIN_PAGE/User/addEmployee.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(ADMIN_USER_UPDATE_CUSTOMER_URI)) {
            if (handleUpdateCustomer(request, response)) {
                request.getRequestDispatcher("/ADMIN_PAGE/User/updateCustomer.jsp").forward(request, response);
            } else {
                response.sendRedirect(ADMIN_USER_LIST_URI);
            }
            return;
        }

        if (path.startsWith(ADMIN_USER_UPDATE_EMPLOYEE_URI)) {
            if (handleUpdateEmployee(request, response)) {
                request.getRequestDispatcher("/ADMIN_PAGE/User/updateEmployee.jsp").forward(request, response);
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
        //
        // if (path.startsWith(ADMIN_CLIENT_DETAIL_URI)) {
        // clientDetail(request, response);
        // request.getRequestDispatcher("/ADMIN_PAGE/User/detail.jsp").forward(request,
        // response);
        // return;
        // }
        if (path.startsWith(ADMIN_CLIENT_ORDER_URI)) {
            System.out.println("Going Order Detail");

            int result = getCustomerOrderDetail(request);

            if (result == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/Order/orderDetail.jsp").forward(request,
                        response);
            } else {
                request.getRequestDispatcher(
                        "/ADMIN_PAGE/Order/orderDetail.jsp" + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }
        // ---------------------------- CHART SECTION ----------------------------
        if (path.startsWith(ADMIN_CHART_BEST_SELLING_PRODUCT_BY_GENDER)) {
            bestSellingProductByGender(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/Chart/bestProductSellingByGender.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(ADMIN_CHART_BEST_SELLING_PRODUCT_BY_PRICE)) {
            bestSellingProductByPrice(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/Chart/bestProductSellingByPrice.jsp").forward(request, response);
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
                int result = addProduct(request, response);

                if (result == State.Success.value) {
                    response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
                } else if (result == State.Fail.value) {
                    response.sendRedirect(
                            ADMIN_PRODUCT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
                }
            }
            return;
        }

        if (path.startsWith(ADMIN_PRODUCT_UPDATE_URI)) {
            if (request.getParameter("btnUpdateProduct") != null
                    && request.getParameter("btnUpdateProduct").equals("Submit")) {
                int result = updateProduct(request, response);
                if (result == State.Success.value) {
                    response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
                } else {
                    response.sendRedirect(
                            ADMIN_PRODUCT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
                }
            }
            return;
        }

        if (path.startsWith(ADMIN_USER_UPDATE_CUSTOMER_URI)) {
            if (request.getParameter("btnUpdateCustomer") != null
                    && request.getParameter("btnUpdateCustomer").equals("Submit")) {
                if (updateCustomer(request, response)) {
                    response.sendRedirect(ADMIN_USER_LIST_URI);
                } else {
                    System.out.println(ADMIN_USER_UPDATE_CUSTOMER_URI + "/ID/" + request.getAttribute("errUserID")
                            + ExceptionUtils.generateExceptionQueryString(request));
                    response.sendRedirect(ADMIN_USER_UPDATE_CUSTOMER_URI + "/ID/" + request.getAttribute("errUserID")
                            + ExceptionUtils.generateExceptionQueryString(request));
                }
            }
            return;
        }

        if (path.startsWith(ADMIN_USER_ADD_EMPLOYEE_URI)) {
            if (request.getParameter("btnAddEmployee") != null
                    && request.getParameter("btnAddEmployee").equals("Submit")) {
                if (addEmployee(request, response)) {
                    response.sendRedirect(ADMIN_USER_LIST_URI);
                } else {
                    System.out.println(ADMIN_USER_ADD_EMPLOYEE_URI + request.getAttribute("errUserID")
                            + ExceptionUtils.generateExceptionQueryString(request));
                    response.sendRedirect(ADMIN_USER_ADD_EMPLOYEE_URI + request.getAttribute("errUserID")
                            + ExceptionUtils.generateExceptionQueryString(request));
                }
            }
            return;
        }

        if (path.startsWith(ADMIN_USER_UPDATE_EMPLOYEE_URI)) {
            if (request.getParameter("btnUpdateEmployee") != null
                    && request.getParameter("btnUpdateEmployee").equals("Submit")) {
                if (updateEmployee(request, response)) {
                    ExceptionUtils.generateExceptionQueryString(request);
                    response.sendRedirect(ADMIN_USER_LIST_URI);
                } else {
                    System.out.println(ADMIN_USER_UPDATE_EMPLOYEE_URI + "/ID/" + request.getAttribute("errUserID")
                            + ExceptionUtils.generateExceptionQueryString(request));
                    response.sendRedirect(ADMIN_USER_UPDATE_EMPLOYEE_URI + "/ID/" + request.getAttribute("errUserID")
                            + ExceptionUtils.generateExceptionQueryString(request));
                }
            }
            return;
        }

        //
        // if (path.startsWith(ADMIN_UPDATE_INFO_URI)) {
        // if (request.getParameter("btnUpdateInfo") != null
        // && request.getParameter("btnUpdateInfo").equals("Submit")) {
        // System.out.println("Going update info");
        // if (updateAdminInfomation(request, response)) {
        // response.sendRedirect(ADMIN_USER_URI);
        // } else {
        // response.sendRedirect(ADMIN_USER_URI + checkException(request));
        // }
        // return;
        // }
        // }
    }

    /* CRUD */
    // ---------------------------- CREATE SECTION ----------------------------
    private int addProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDAO = new BrandDAO();
        String pName = request.getParameter("txtProductName");
        String bName = request.getParameter("txtBrandName");
        int pPrice = Integer.parseInt(
                Converter.covertIntergerToMoney(
                        Integer.parseInt(request.getParameter("txtProductPrice").replace(",", ""))));
        String gender = request.getParameter("rdoGender");
        String smell = request.getParameter("txtProductSmell");
        int quantity = Integer.parseInt(request.getParameter("txtProductQuantity").replace(",", ""));
        int releaseYear = Integer.parseInt(request.getParameter("txtProductReleaseYear"));
        int volume = Integer.parseInt(request.getParameter("txtProductVolume").replace(",", ""));
        String description = request.getParameter("txtProductDescription");
        Part imgPart = request.getPart("fileProductImg");

        // Upload to Imgur database
        String imgURL = ImageUploader.uploadImageToCloud(imgPart);

        Product product = new Product();
        product.setName(pName);
        product.setBrandId(bDAO.getBrand(bName).getId());
        product.setGender(gender);
        product.setSmell(smell);
        product.setReleaseYear(releaseYear);
        product.setVolume(volume);
        product.setImgURL(imgURL);
        product.setDescription(description);

        Stock stock = new Stock();
        stock.setPrice(pPrice);
        stock.setQuantity(quantity);

        Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        String username = userCookie.getValue();
        AdminDAO adDAO = new AdminDAO();
        Admin admin = adDAO.getAdmin(username);

        // Upload data to db
        int kq = pDAO.addProduct(product, admin);
        if (kq == 0) {
            System.out.println("Add failed, Some attribute may be duplicate");
            request.setAttribute("exceptionType", "OperationAddFailedException");
            return State.Fail.value;
        }
        System.out.println("Add successfully");
        return State.Success.value;
    }

    private boolean addEmployee(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDAO = new UserDAO();
        EmployeeDAO eDAO = new EmployeeDAO();

        // [User] Update Section
        String uName = request.getParameter("txtName");
        String uUsername = request.getParameter("txtUsername");
        String uPassword = request.getParameter("txtPassword");
        String uEmail = request.getParameter("txtEmail");

        // [Employee] Update Section
        String eCitizenId = request.getParameter("txtCitizenId");
        String eDateOfBirth = request.getParameter("txtDOB");
        String ePhoneNumber = request.getParameter("txtPhoneNumber");
        String eAddress = request.getParameter("txtAddress");
        String eJoinDate = request.getParameter("txtJoinDate");
        Role eRole = eDAO.getRole(Integer.parseInt(request.getParameter("txtRole")));
        System.out.println("Vao add employee");
        Employee employeeToAdd = new Employee();

        // For checking duplicate
        boolean isDuplicatedUsername = false;
        boolean isDuplicatedEmail = false;
        boolean isDuplicatedCitizenId = false;
        boolean isDuplicatedPhoneNumber = false;

        if (eDAO.isExistUsername(uUsername)) {
            isDuplicatedUsername = true;
        }
        if (uDAO.isExistEmail(uEmail)) {
            isDuplicatedEmail = true;
        }
        if (eDAO.isExistCitizen(eCitizenId)) {
            isDuplicatedCitizenId = true;
        }
        if (eDAO.isExistPhoneNumber(ePhoneNumber)) {
            isDuplicatedPhoneNumber = true;
        }

        try {
            if (isDuplicatedUsername || isDuplicatedEmail || isDuplicatedCitizenId || isDuplicatedPhoneNumber) {
                if (isDuplicatedUsername) {
                    throw new UsernameDuplicationException();
                }
                if (isDuplicatedEmail) {
                    throw new EmailDuplicationException();
                }
                if (isDuplicatedCitizenId) {
                    throw new CitizenIDDuplicationException();
                }
                if (isDuplicatedPhoneNumber) {
                    throw new PhoneNumberDuplicationException();
                }
            }
        } catch (UsernameDuplicationException ex) {
            System.out.println("username dup");
            request.setAttribute("exceptionType", "UsernameDuplicationException");
            return false;
        } catch (PhoneNumberDuplicationException ex) {
            System.out.println("phone dup");
            request.setAttribute("exceptionType", "PhoneNumberDuplicationException");
            return false;
        } catch (EmailDuplicationException ex) {
            System.out.println("Email dup");
            request.setAttribute("exceptionType", "EmailDuplicationException");
            return false;
        } catch (CitizenIDDuplicationException ex) {
            System.out.println("CitizenId dup");
            request.setAttribute("exceptionType", "CitizenIDDuplicationException");
            return false;
        }

        // Start to add
        employeeToAdd.setName(uName);
        employeeToAdd.setUsername(uUsername);
        employeeToAdd.setPassword(uPassword);
        employeeToAdd.setEmail(uEmail);
        employeeToAdd.setType("Employee");
        employeeToAdd.setCitizenId(eCitizenId);
        employeeToAdd.setDateOfBirth(Converter.convertStringToDate(eDateOfBirth));
        employeeToAdd.setPhoneNumber(ePhoneNumber);
        employeeToAdd.setAddress(eAddress);
        employeeToAdd.setJoinDate(Converter.convertStringToDate(eJoinDate));
        employeeToAdd.setRetireDate(null);
        employeeToAdd.setRole(eRole);

        System.out.println("employee join date: " + employeeToAdd.getJoinDate());
        System.out.println("employee DOB date: " + employeeToAdd.getDateOfBirth());
        System.out.println("Da build");
        int result = 0;

        result = eDAO.addEmployee(employeeToAdd);

        if (result == 0) {
            System.out.println("Failed to update the user with ID " + uName + " to database");
            return false;
        }

        return true;
    }

    // ---------------------------- READ SECTION ----------------------------
    private int searchProduct(HttpServletRequest request, HttpServletResponse response) {
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        int rows = 20;
        String search = request.getParameter("txtSearch");
        ProductDAO pDAO = new ProductDAO();

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }
        if (search == null || search.equals("")) {
            search = "%";
        } else {
            search = "%" + search + "%";
        }
        List<Product> productList = pDAO.searchProduct(search);
        if (productList.isEmpty()) {
            request.setAttribute("exceptionType", "ProductNotFoundException");
            return State.Fail.value;
        }
        int numberOfPage = (productList.size() / rows) + (productList.size() % rows == 0 ? 0 : 1);
        productList = Generator.pagingList(productList, page, rows);

        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", numberOfPage);
        request.setAttribute("productList", productList);
        request.setAttribute("search", search);

        return State.Success.value;
    }

    private int searchOrder(HttpServletRequest request) {
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        int rows = 20;
        String search = request.getParameter("txtSearch");
        OrderDAO oDAO = new OrderDAO();

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }

        List<Order> orderList = oDAO.searchOrder(search);

        if (orderList.isEmpty()) {
            System.out.println("Empty order list");
            request.setAttribute("exceptionType", "OrderNotFoundException");
            return State.Fail.value;
        }

        int numberOfPage = (orderList.size() / rows) + (orderList.size() % rows == 0 ? 0 : 1);
        orderList = Generator.pagingList(orderList, page, rows);

        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", numberOfPage);
        request.setAttribute("orderList", orderList);
        request.setAttribute("search", search);

        return State.Success.value;
    }

    private void searchUser(HttpServletRequest request, HttpServletResponse response) {
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        int rows = 20;
        String Search = request.getParameter("txtSearch");
        UserDAO uDAO = new UserDAO();

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }

        if (Search == null || Search.equals("")) {
            Search = "%";
        }

        List<User> usersFromSearch = uDAO.searchUser(Search);

        List<User> listUser = Generator.pagingList(usersFromSearch, page, rows);

        final int ROWS = 20;
        int NumberOfPage = usersFromSearch.size() / ROWS;
        NumberOfPage = (usersFromSearch.size() % ROWS == 0 ? NumberOfPage : NumberOfPage + 1);

        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", NumberOfPage);
        request.setAttribute("listUser", listUser);
        request.setAttribute("Search", Search);
    }

    private void userInfo(HttpServletRequest request, HttpServletResponse response) {
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int userId = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                userId = Integer.parseInt(data[i + 1]);
            }
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(userId);
        System.out.println("userInfo " + user.getName());
        request.setAttribute("UserInfo", user);

    }

    private int getCustomerOrderDetail(HttpServletRequest request) {
        String path = request.getRequestURI();
        String[] data = path.split("/");
        try {
            OrderDAO oDAO = new OrderDAO();
            VoucherDAO vDAO = new VoucherDAO();
            ProductDAO pDAO = new ProductDAO();

            int orderId = Integer.parseInt(data[data.length - 1]);
            Order order = oDAO.getOrderByOrderId(orderId);
            Voucher v = vDAO.getVoucher(order.getVoucherId());

            List<OrderDetail> orderDetailList = order.getOrderDetailList();
            List<Product> approvedProductsList = new ArrayList<>();

            // Get the list of all product that is approviate for voucher discount.
            Product p;
            if (v != null) {
                System.out.println("Order detail list size:" + orderDetailList.size());
                System.out.println("Approved product list size:" + v.getApprovedProductId().size());
                for (int i = 0; i < orderDetailList.size(); i++) {
                    if (v.getApprovedProductId().contains(orderDetailList.get(i).getProductId())) {
                        p = pDAO.getProduct(orderDetailList.get(i).getProductId());

                        approvedProductsList.add(p);
                    }
                }
            }

            request.setAttribute("approvedProductsList", approvedProductsList);

            System.out.println("Get order detail list");
            System.out.println(order.getOrderDetailList());

            request.setAttribute("OrderInfor", order);
            return State.Success.value;
        } catch (NumberFormatException e) {
            return State.Fail.value;
        }
    }

    //
    // private void clientDetail(HttpServletRequest request, HttpServletResponse
    // response) {
    // OrderDAO oDAO = new OrderDAO();
    // UserDAO uDAO = new UserDAO();
    // String URI = request.getRequestURI();
    // String data[] = URI.split("/");
    // int ClientID = -1;
    // for (int i = 0; i < data.length; i++) {
    // if (data[i].equals("ID")) {
    // ClientID = Integer.parseInt(data[i + 1]);
    // }
    // }
    // List<Order> orderList = oDAO.getOrderByClientId(ClientID);
    // User client = uDAO.getUser(ClientID);
    // request.setAttribute("client", client);
    // request.setAttribute("orderList", orderList);
    // }
    //
    // private void OrderDetail(HttpServletRequest request, HttpServletResponse
    // response) {
    // UserDAO uDAO = new UserDAO();
    // OrderDAO oDAO = new OrderDAO();
    // String URI = request.getRequestURI();
    // String data[] = URI.split("/");
    // int OrderID = -1;
    // for (int i = 0; i < data.length; i++) {
    // if (data[i].equals("ID")) {
    // OrderID = Integer.parseInt(data[i + 1]);
    // }
    // }
    // List<String[]> orderDetail = oDAO.getOrderDetailByOrderID(OrderID);
    // Order OrderInfor = oDAO.getOrderByOrderId(OrderID);
    // User client = uDAO.getUser(OrderInfor.getClientID());
    // request.setAttribute("client", client);
    // request.setAttribute("OrderInfor", OrderInfor);
    // request.setAttribute("OrderDetail", orderDetail);
    // }
    //
    // ---------------------------- UPDATE SECTION ----------------------------
    private int updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Get the text parameter in order to update
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDAO = new BrandDAO();
        int pID = Integer.parseInt(request.getParameter("txtProductID"));
        Product oldProduct = pDAO.getProduct(pID);
        String pName = request.getParameter("txtProductName");
        String bName = request.getParameter("txtBrandName");
        String gender = request.getParameter("rdoGender");
        String smell = request.getParameter("txtProductSmell");
        int releaseYear = Integer.parseInt(request.getParameter("txtProductReleaseYear"));
        int volume = Integer.parseInt(request.getParameter("txtProductVolume").replace(",", ""));
        String description = request.getParameter("txtProductDescription");
        int stkPrice = Integer.parseInt(request.getParameter("txtProductPrice").replace(",", ""));
        int stkQuantity = Integer.parseInt(request.getParameter("txtProductQuantity").replace(",", ""));

        // Get the part of the image
        Part imagePart = null;
        imagePart = request.getPart("fileProductImg");
        System.out.println("Part of the Product : " + imagePart.getName() + " ||| "
                + imagePart.getSize());

        String imgURL;
        // Check if user update image
        if (imagePart == null || imagePart.getSize() == 0) {
            imgURL = oldProduct.getImgURL();
        } else {
            // Upload to Imgur Database and save new URL
            imgURL = ImageUploader.uploadImageToCloud(imagePart);
        }

        Product product = new Product();
        product.setId(pID);
        product.setName(pName);
        product.setBrandId(bDAO.getBrand(bName).getId());
        product.setGender(gender);
        product.setSmell(smell);
        product.setReleaseYear(releaseYear);
        product.setVolume(volume);
        product.setImgURL(imgURL);
        product.setDescription(description);
        product.setActive(true);

        Stock stock = new Stock();
        stock.setProductID(pID);
        stock.setPrice(stkPrice);
        stock.setQuantity(stkQuantity);
        product.setStock(stock);

        Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        String username = userCookie.getValue();
        AdminDAO adDAO = new AdminDAO();
        Admin admin = adDAO.getAdmin(username);
        int kq = pDAO.updateProduct(product, admin);
        if (kq == 0) {
            System.out.println("Update Failed, The Product is not in the database");
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.OperationEditFailedException.toString());
            return State.Fail.value;
        }
        System.out.println("Update Product with ID: " + pID + " successfully!");
        return State.Success.value;
    }

    private boolean updateCustomer(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDAO = new UserDAO();
        EmployeeDAO eDAO = new EmployeeDAO();
        int result = 0;

        // [User] Update Section
        int uID = Integer.parseInt(request.getParameter("txtUserID"));
        String uName = request.getParameter("txtName");
        String uUsername = request.getParameter("txtUsername");
        String uPassword = request.getParameter("txtPassword");
        String uEmail = request.getParameter("txtEmail");
        // For sending email
        boolean isChangedUsername = false;
        boolean isChangedEmail = false;
        boolean isChangedPassword = false;

        User userForUpdate = uDAO.getUser(uID);

        if (!uUsername.equals(userForUpdate.getUsername())) {
            isChangedUsername = true;
            userForUpdate.setUsername(uUsername);
        }
        if (!uEmail.equals(userForUpdate.getEmail())) {
            isChangedEmail = true;
            userForUpdate.setEmail(uEmail);
        }

        // Checking update it self
        // Only hash the new password if the password is different from the user's old
        // md5 password.
        if (!uPassword.equals(userForUpdate.getPassword())) {
            isChangedPassword = true;
            uPassword = Converter.convertToMD5Hash(uPassword);
            userForUpdate.setPassword(uPassword);
        }

        // For checking duplicate
        boolean isDuplicatedUsername = false;
        boolean isDuplicatedEmail = false;

        if (uDAO.isExistUsername(uUsername)) {
            isDuplicatedUsername = true;
        }

        if (uDAO.isExistEmail(uEmail)) {
            isDuplicatedEmail = true;
        }

        try {
            if (isDuplicatedUsername || isDuplicatedEmail) {
                request.setAttribute("errUserID", uID);
                if (isDuplicatedUsername && isChangedUsername) {
                    throw new UsernameDuplicationException();
                }
                if (isDuplicatedEmail && isChangedEmail) {
                    throw new EmailDuplicationException();
                }
            }
        } catch (UsernameDuplicationException ex) {
            System.out.println("username dup");
            request.setAttribute("exceptionType", "UsernameDuplicationException");
            return false;
        } catch (EmailDuplicationException ex) {
            System.out.println("Email dup");
            request.setAttribute("exceptionType", "EmailDuplicationException");
            return false;
        }

        // Start to update
        userForUpdate.setName(uName);
        userForUpdate.setActive(true);
        userForUpdate.setType("Customer");
        result = uDAO.updateUser(userForUpdate);

        if (result < 1) {
            System.out.println("Failed to update the user with ID " + uID + " to database");
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
            if (isChangedUsername) {
                System.out.println("Detect username change");
                System.out.println("sending mail changing username");
                es.setEmailTo(uEmail);
                es.sendToEmail(es.CHANGE_USERNAME_NOTFICATION, es.changeUsernameNotification(uUsername));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("update account with ID " + userForUpdate.getId() + " successfully");

        return true;
    }

    private boolean updateEmployee(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDAO = new UserDAO();
        EmployeeDAO eDAO = new EmployeeDAO();

        // [User] Update Section
        int uID = Integer.parseInt(request.getParameter("txtUserID"));
        String uName = request.getParameter("txtName");
        String uUsername = request.getParameter("txtUsername");
        String uPassword = request.getParameter("txtPassword");
        String uEmail = request.getParameter("txtEmail");

        // [Employee] Update Section
        String eCitizenId = request.getParameter("txtCitizenId");
        String eDateOfBirth = request.getParameter("txtDOB");
        String ePhoneNumber = request.getParameter("txtPhoneNumber");
        String eAddress = request.getParameter("txtAddress");
        String eJoinDate = request.getParameter("txtJoinDate");
        String eRetireDate = request.getParameter("txtRetireDate");

        Employee employeeForUpdate = eDAO.getEmployeeByUserId(uID);

        // For sending email
        boolean isChangedUsername = false;
        boolean isChangedEmail = false;
        boolean isChangedPassword = false;
        boolean isChangedCitizenId = false;
        boolean isChangedPhoneNumber = false;

        User userForUpdate = uDAO.getUser(uID);
        Employee employeeForChecking = eDAO.getEmployeeByUserId(uID);

        if (!uUsername.equals(employeeForChecking.getUsername())) {
            isChangedUsername = true;
            userForUpdate.setUsername(uUsername);
        }
        if (!uEmail.equals(employeeForChecking.getEmail())) {
            isChangedEmail = true;
            userForUpdate.setEmail(uEmail);
        }

        // Checking update it self
        // Only hash the new password if the password is different from the user's old
        // md5 password.
        if (!uPassword.equals(employeeForChecking.getPassword())) {
            isChangedPassword = true;
            uPassword = Converter.convertToMD5Hash(uPassword);
            userForUpdate.setPassword(uPassword);
        }
        if (!eCitizenId.equals(employeeForChecking.getCitizenId())) {
            isChangedCitizenId = true;
            employeeForUpdate.setCitizenId(eCitizenId);

        }
        if (!ePhoneNumber.equals(employeeForChecking.getPhoneNumber())) {
            isChangedPhoneNumber = true;
            employeeForUpdate.setPhoneNumber(ePhoneNumber);
        }

        // For checking duplicate
        boolean isDuplicatedUsername = false;
        boolean isDuplicatedEmail = false;
        boolean isDuplicatedCitizenId = false;
        boolean isDuplicatedPhoneNumber = false;

        if (eDAO.isExistUsername(uUsername)) {
            isDuplicatedUsername = true;
        }
        if (uDAO.isExistEmail(uEmail)) {
            isDuplicatedEmail = true;
        }
        if (eDAO.isExistCitizen(eCitizenId)) {
            isDuplicatedCitizenId = true;
        }
        if (eDAO.isExistPhoneNumber(ePhoneNumber)) {
            isDuplicatedPhoneNumber = true;
        }

        try {
            if (isDuplicatedUsername || isDuplicatedEmail || isDuplicatedCitizenId || isDuplicatedPhoneNumber) {
                request.setAttribute("errUserID", uID);
                if (isDuplicatedUsername && isChangedUsername) {
                    throw new UsernameDuplicationException();
                }
                if (isDuplicatedEmail && isChangedEmail) {
                    throw new EmailDuplicationException();
                }
                if (isDuplicatedCitizenId && isChangedCitizenId) {
                    throw new CitizenIDDuplicationException();
                }
                if (isDuplicatedPhoneNumber && isChangedPhoneNumber) {
                    throw new PhoneNumberDuplicationException();
                }
            }
        } catch (UsernameDuplicationException ex) {
            System.out.println("username dup");
            request.setAttribute("exceptionType", "UsernameDuplicationException");
            return false;
        } catch (PhoneNumberDuplicationException ex) {
            System.out.println("phone dup");
            request.setAttribute("exceptionType", "PhoneNumberDuplicationException");
            return false;
        } catch (EmailDuplicationException ex) {
            System.out.println("Email dup");
            request.setAttribute("exceptionType", "EmailDuplicationException");
            return false;
        } catch (CitizenIDDuplicationException ex) {
            System.out.println("CitizenId dup");
            request.setAttribute("exceptionType", "CitizenIDDuplicationException");
            return false;
        }

        // Start to update
        userForUpdate.setName(uName);
        employeeForUpdate.setDateOfBirth(Converter.convertStringToDate(eDateOfBirth));
        employeeForUpdate.setAddress(eAddress);
        employeeForUpdate.setJoinDate(Converter.convertStringToDate(eJoinDate));
        employeeForUpdate.setRetireDate(Converter.convertStringToDate(eRetireDate));

        int result;

        result = uDAO.updateUser(userForUpdate);
        result += eDAO.updateEmployee(employeeForUpdate);

        if (result < 2) {
            System.out.println("Failed to update the user with ID " + uID + " to database");
            return false;
        } else if (!"".equals(eRetireDate) && eRetireDate != null) {
            uDAO.disableUser(userForUpdate);
            eDAO.disableEmployee(employeeForUpdate);
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
            if (isChangedUsername) {
                System.out.println("Detect username change");
                System.out.println("sending mail changing username");
                es.setEmailTo(uEmail);
                es.sendToEmail(es.CHANGE_USERNAME_NOTFICATION, es.changeUsernameNotification(uUsername));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("update account with ID " + employeeForUpdate.getId() + " successfully");

        return true;
    }

    private int restoreProduct(HttpServletRequest request, HttpServletResponse response) {
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
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.ProductNotFoundException.toString());
            return State.Fail.value;
        }
        try {
            Product product = pDAO.getProduct(Integer.parseInt(productId));

            Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
            String username = userCookie.getValue();
            AdminDAO adDAO = new AdminDAO();
            Admin admin = adDAO.getAdmin(username);

            System.out.println("Admin id: " + admin.getAdminId() + "| admin name: " + admin.getName());
            int kq = pDAO.restoreProduct(product, admin);
            if (kq == 0) {
                System.out.println("Restore Failed, The Product is not in the database");
                return State.Fail.value;
            }
        } catch (NumberFormatException e) {
            // To get default exception
            request.setAttribute("exceptionType", "");
            return State.Fail.value;
        } catch (ProductNotFoundException ex) {
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.ProductNotFoundException.toString());
            return State.Fail.value;
        }
        System.out.println("Restore Product with ID: " + productId + "successfully!");
        return State.Success.value;
    }

    private void restoreUser(HttpServletRequest request, HttpServletResponse response) {
        // Admin/User/Restore/ID/1
        String path = request.getRequestURI();
        String currentUsername = "";
        String data[] = path.split("/");

        UserDAO uDAO = new UserDAO();
        Integer userId = null;

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                userId = Integer.parseInt(data[i + 1]);
                currentUsername = data[i + 2];
            }
        }

        if (userId == null) {
            System.out.println("Restore Failed, Lacking userID");
            return;
        }

        User user = uDAO.getUser(userId);
        if (user.getUsername().equals(currentUsername)) {
            System.out.println("Can't restore itself");
            return;
        }

        int kq = uDAO.restoreUser(user);

        if (user.getType().equals("Employee")) {
            EmployeeDAO eDAO = new EmployeeDAO();
            Employee employee = eDAO.getEmployeeByUserId(userId);
            eDAO.restoreEmployee(employee);
        }

        if (kq == 0) {
            System.out.println("Restore Failed, The User is not in the database");
            return;
        }
        System.out.println("Restore User with ID: " + userId + " successfully!");
    }

    private int getUpdateProduct(HttpServletRequest request, HttpServletResponse response) {
        ProductDAO pDAO = new ProductDAO();
        try {
            String data[] = request.getRequestURI().split("/");
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals("ID")) {
                    int ProductID = Integer.parseInt(data[i + 1]);
                    Product pd = pDAO.getProduct(ProductID);
                    if (pd != null) {
                        request.setAttribute("ProductUpdate", pd);
                        return State.Success.value;
                    }
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("exceptionType", "");
            return State.Fail.value;
        }

        request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.ProductNotFoundException.toString());
        return State.Fail.value;
    }

    private boolean handleUpdateCustomer(HttpServletRequest request, HttpServletResponse response) {
        String data[] = request.getRequestURI().split("/");
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                int UserId = Integer.parseInt(data[i + 1]);
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerByUserId(UserId);
                request.setAttribute("CustomerUpdate", customer);
                return true;
            }
        }

        return false;
    }

    private boolean handleUpdateEmployee(HttpServletRequest request, HttpServletResponse response) {
        String data[] = request.getRequestURI().split("/");
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                int UserId = Integer.parseInt(data[i + 1]);
                EmployeeDAO employeeDAO = new EmployeeDAO();
                Employee employee = employeeDAO.getEmployeeByUserId(UserId);
                if (employee.getRole().getName().equals("Admin")) {
                    System.out.println("Can't update Admin");
                    return false;
                }
                request.setAttribute("EmployeeUpdate", employee);
                return true;
            }
        }

        return false;
    }

    //
    // private boolean updateAdminInfomation(HttpServletRequest request,
    // HttpServletResponse response) {
    // /*
    // * txtFullname
    // * txtUserName
    // * txtEmail
    // * pwdCurrent
    // * pwdNew
    // * pwdConfirmNew
    // * btnUpdateInfo (value = Submit)
    // */
    //
    // String fullname = request.getParameter("txtFullname");
    //
    // String username = request.getParameter("txtUserName");
    // String email = request.getParameter("txtEmail");
    // String currentPassword = request.getParameter("pwdCurrent");
    // String newPassword = "";
    // Cookie currentUserCookie = (Cookie)
    // request.getSession().getAttribute("userCookie");
    //
    // UserDAO usDAO = new UserDAO();
    // User user = usDAO.getUser(currentUserCookie.getValue());
    //
    // boolean isChangedEmail = true;
    // boolean isChangedPassword = true;
    // boolean isChangedUsername = true;
    // // Username, email, phone number is unique
    // try {
    // if (!email.equals(user.getEmail())) {
    // if (usDAO.isExistEmail(email)) {
    // throw new EmailDuplicationException();
    // }
    // } else {
    // isChangedEmail = false;
    // }
    //
    // if (!username.equals(user.getUsername())) {
    // if (usDAO.isExistUsername(username)) {
    // throw new UsernameDuplicationException();
    // }
    // } else {
    // isChangedUsername = false;
    // }
    //
    // if (currentPassword == null || currentPassword.equals("")) {
    // currentPassword = user.getPassword();
    // isChangedPassword = false;
    // // check if currentPassword is true
    // } else if (!usDAO.login(user.getUsername(), currentPassword)) {
    // throw new WrongPasswordException();
    // }
    //
    // // Email and username duplication must come first
    // } catch (WrongPasswordException e) {
    // request.setAttribute("exceptionType", "WrongPasswordException");
    // return false;
    // } catch (AccountDeactivatedException e) {
    // request.setAttribute("exceptionType", "AccountDeactivatedException");
    // return false;
    // } catch (AccountNotFoundException e) {
    // request.setAttribute("exceptionType", "AccountNotFoundException");
    // return false;
    // } catch (EmailDuplicationException e) {
    // request.setAttribute("exceptionType", "EmailDuplicationException");
    // return false;
    // } catch (UsernameDuplicationException e) {
    // request.setAttribute("exceptionType", "UsernameDuplicationException");
    // return false;
    // }
    // System.out.println("change password is " + isChangedPassword);
    // System.out.println("New password is before if: " +
    // request.getParameter("pwdNew"));
    // if (isChangedPassword && request.getParameter("pwdNew") != null &&
    // !request.getParameter("pwdNew").equals("")) {
    // newPassword = request.getParameter("pwdNew");
    // System.out.println("New password is before MD5: " + newPassword);
    // newPassword = usDAO.getMD5hash(newPassword);
    // } else {
    // newPassword = user.getPassword();
    // }
    //
    // User updateUser = new User(
    // user.getID(),
    // fullname,
    // username,
    // newPassword,
    // email,
    // user.getPhoneNumber(),
    // user.getAddress(),
    // user.getRole());
    //
    // usDAO.updateUser(updateUser);
    //
    // // Update cookie
    // Cookie c = ((Cookie) request.getSession().getAttribute("userCookie"));
    // c.setValue(username);
    // c.setPath("/");
    // response.addCookie(c);
    //
    // // Sending mail
    // try {
    // EmailSender es = new EmailSender();
    // if (isChangedPassword) {
    // System.out.println("Detect password change");
    // System.out.println("sending mail changing password");
    // es.setEmailTo(email);
    // es.sendToEmail(es.CHANGE_PASSWORD_NOTFICATION,
    // es.changePasswordNotifcation());
    // }
    // if (isChangedEmail) {
    // System.out.println("Detect email change");
    // System.out.println("sending mail changing email");
    // es.setEmailTo(user.getEmail());
    // es.sendToEmail(es.CHANGE_EMAIL_NOTFICATION,
    // es.changeEmailNotification(email));
    // }
    // if (isChangedUsername) {
    // System.out.println("Detect username change");
    // System.out.println("sending mail changing username");
    // es.setEmailTo(email);
    // es.sendToEmail(es.CHANGE_USERNAME_NOTFICATION,
    // es.changeUsernameNotification(username));
    // }
    // } catch (UnsupportedEncodingException e) {
    // e.printStackTrace();
    // }
    //
    // System.out.println("update account with ID " + user.getID() + "
    // successfully");
    // return true;
    // }
    //
    // // ---------------------------- DELETE SECTION ----------------------------
    private int deleteProduct(HttpServletRequest request, HttpServletResponse response) {
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
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.ProductNotFoundException.toString());
            return State.Fail.value;
        }

        try {
            Product product = pDAO.getProduct(Integer.parseInt(productId));

            Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
            String username = userCookie.getValue();
            AdminDAO adDAO = new AdminDAO();
            Admin admin = adDAO.getAdmin(username);

            System.out.println("Admin id: " + admin.getAdminId() + "| admin name: " + admin.getName());

            int kq = pDAO.disableProduct(product, admin);
            if (kq == 0) {
                System.out.println("Delete Failed, The Product is not in the database");
                request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.ProductNotFoundException.toString());
                return State.Fail.value;
            }
        } catch (NumberFormatException e) {
            // default exception
            request.setAttribute("exceptionType", "");
            return State.Fail.value;
        } catch (ProductNotFoundException ex) {
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.ProductNotFoundException.toString());
            return State.Fail.value;
        }

        System.out.println("Delete Product with ID: " + productId + "successfully!");
        return State.Success.value;
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        // Admin/User/Delete/ID/1/currentUsername
        String path = request.getRequestURI();
        String data[] = path.split("/");

        UserDAO uDAO = new UserDAO();
        Integer userId = null;
        String currentUsername = "";

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                userId = Integer.parseInt(data[i + 1]);
                currentUsername = data[i + 2];
            }
        }
        if (userId == null) {
            System.out.println("Deactivate Failed, Lacking userID");
            return;
        }

        User user = uDAO.getUser(userId);
        if (user.getUsername().equals(currentUsername)) {
            System.err.println("Can't delete itself");
            return;
        }

        int kq = uDAO.disableUser(user);

        if (user.getType().equals("Employee")) {
            EmployeeDAO eDAO = new EmployeeDAO();
            Employee employee = eDAO.getEmployeeByUserId(userId);
            System.out.println("Employee id in Admin controller: " + employee.getEmployeeId());
            eDAO.disableEmployee(employee);
        }

        if (kq == 0) {
            System.out.println("Deactivate Failed, The User is not in the database");
            return;
        }
        System.out.println("Deactivated User with ID: " + userId + " successfully!");
    }

    private void bestSellingProductByGender(HttpServletRequest request, HttpServletResponse response) {
        OrderDAO oDAO = new OrderDAO();
        List<Product> listProduct = oDAO.getOrderForChartByOrderIdByGender();
        request.setAttribute("listProduct", listProduct);
    }

    private void bestSellingProductByPrice(HttpServletRequest request, HttpServletResponse response) {
        OrderDAO oDAO = new OrderDAO();
        List<Product> listProduct = oDAO.getOrderForChartByOrderIdByPrice();
        request.setAttribute("listProduct", listProduct);
    }

}
