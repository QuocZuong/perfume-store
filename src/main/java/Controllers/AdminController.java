package Controllers;

import DAOs.AdminDAO;
import DAOs.BrandDAO;
import DAOs.CustomerDAO;
import DAOs.EmployeeDAO;
import DAOs.ImportDAO;
import DAOs.ImportDetailDAO;
import DAOs.OrderDAO;
import DAOs.ProductActivityLogDAO;
import Models.User;
import Models.Voucher;

import java.util.List;

import Models.Product;
import DAOs.ProductDAO;
import DAOs.StockDAO;
import DAOs.UserDAO;
import DAOs.VoucherDAO;
import Exceptions.AccountDeactivatedException;
import Exceptions.AccountNotFoundException;
import Exceptions.CitizenIDDuplicationException;
import Exceptions.EmailDuplicationException;
import Exceptions.InvalidInputException;
import Exceptions.PhoneNumberDuplicationException;
import Exceptions.ProductNotFoundException;
import Exceptions.UsernameDuplicationException;
import Exceptions.WrongPasswordException;
import Interfaces.DAOs.IUserDAO;
import Lib.Converter;
import Lib.EmailSender;
import Lib.ExceptionUtils;
import Lib.Generator;
import Lib.ImageUploader;
import Models.Admin;
import Models.Stock;
import Models.Customer;
import Models.Employee;
import Models.Import;
import Models.ImportDetail;
import Models.Order;
import Models.OrderDetail;
import Models.ProductActivityLog;
import Models.Role;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class AdminController extends HttpServlet {

    // ----------------------- URI DECLARATION SECTION ----------------------------
    public static final String ADMIN_USER_URI = "/Admin";
    // Product
    public static final String ADMIN_PRODUCT_LIST_URI = "/Admin/Product/List";
    public static final String ADMIN_PRODUCT_ADD_URI = "/Admin/Product/Add";
    public static final String ADMIN_PRODUCT_UPDATE_URI = "/Admin/Product/Update";
    public static final String ADMIN_PRODUCT_DELETE_URI = "/Admin/Product/Delete";
    public static final String ADMIN_PRODUCT_RESTORE_URI = "/Admin/Product/Restore";
    // Order
    public static final String ADMIN_ORDER_LIST_URI = "/Admin/Order/List";
    public static final String ADMIN_ORDER_REQUEST_URI = "/Admin/Order/Request";
    public static final String ADMIN_ORDER_DETAIL_URI = "/Admin/Order/Detail";
    public static final String ADMIN_ORDER_DELETE_URI = "/Admin/Order/Delete";
    public static final String ADMIN_ORDER_RESTORE_URI = "/Admin/Order/Restore";

    public static final String ADMIN_IMPORT_LIST_URI = "/Admin/Import/List";
    public static final String ADMIN_IMPORT_DETAIL_URI = "/Admin/Import/Detail";
    public static final String ADMIN_IMPORT_UPDATE_URI = "/Admin/Import/Update";
    public static final String ADMIN_IMPORT_CONFIRM_UPDATE_URI = "/Admin/Import/ConfirmUpdate";

    public static final String ADMIN_IMPORT_DELETE_DETAIL_URI = "/Admin/Import/DeleteDetail";
    public static final String ADMIN_IMPORT_STORE = "/Admin/Import/Store";
    public static final String ADMIN_IMPORT_BRING_TO_STOCK = "/Admin/Import/Bring";

    // Voucher
    public static final String ADMIN_VOUCHER_ADD_URI = "/Admin/Voucher/Add";
    public static final String ADMIN_VOUCHER_LIST_URI = "/Admin/Voucher/List";
    public static final String ADMIN_VOUCHER_UPDATE_URI = "/Admin/Voucher/Update";
    public static final String ADMIN_VOUCHER_DELETE_URI = "/Admin/Voucher/Delete";

    // User
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
    public static final String ADMIN_ADMIN_ACTIVITY_LOG_URI = "/Admin/EmployeeActivityLog/Admin";
    public static final String ADMIN_ORDER_MANAGER_ACTIVITY_LOG_URI = "/Admin/EmployeeActivityLog/OrderManager";
    public static final String ADMIN_INVENTORY_MANAGER_ACTIVITY_LOG_URI = "/Admin/EmployeeActivityLog/InventoryManager";

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
        // ---------------------------- VOUCHER SECTION ----------------------------

        if (path.startsWith(ADMIN_VOUCHER_LIST_URI)
                || path.startsWith(ADMIN_VOUCHER_LIST_URI + "/page")) {
            int result = searchVoucher(request);

            if (result == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/Voucher/list.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(ADMIN_VOUCHER_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }
        // Page chua co
        if (path.startsWith(ADMIN_VOUCHER_ADD_URI)) {
            int result = getUpdateVoucher(request);

            if (result == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/Voucher/addVoucher.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(ADMIN_VOUCHER_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }
        // Page nhu cc
        if (path.startsWith(ADMIN_VOUCHER_UPDATE_URI)) {
            int result = getUpdateVoucher(request);

            if (result == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/Voucher/updateVoucher.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(ADMIN_VOUCHER_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(ADMIN_VOUCHER_DELETE_URI)) {
            int result = deleteVoucher(request);

            if (result == State.Success.value) {
                response.sendRedirect(ADMIN_VOUCHER_LIST_URI);
            } else if (result == State.Fail.value) {
                response.sendRedirect(ADMIN_VOUCHER_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
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

        if (path.startsWith(ADMIN_ADMIN_ACTIVITY_LOG_URI)) {
            adminActivityLog(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/User/adminActivityLog.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(ADMIN_ORDER_MANAGER_ACTIVITY_LOG_URI)) {
            orderManagerActivityLog(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/User/orderManagerActivityLog.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(ADMIN_INVENTORY_MANAGER_ACTIVITY_LOG_URI)) {
            adminActivityLog(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/User/inventoryManagerActivityLog.jsp").forward(request, response);
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

        // ---------------------------- IMPORT SECTION ----------------------------
        if (path.startsWith(ADMIN_IMPORT_STORE)
                || path.startsWith(ADMIN_IMPORT_STORE + "/page")) {
            int result = -1;
            if (!path.endsWith("?errAccNF=true") && !path.endsWith("?errImpNF")) {
                result = searchImportDetail(request);
            }
            if (result == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/User/bringImportToStock.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(ADMIN_IMPORT_STORE + ExceptionUtils.generateExceptionQueryString(request));
            } else if (result == -1) {
                response.sendRedirect(ADMIN_IMPORT_STORE);
            }

            return;
        }

        if (path.startsWith(ADMIN_IMPORT_BRING_TO_STOCK)) {
            int result = bringToStock(request);
            if (result == State.Success.value) {
                response.sendRedirect(ADMIN_IMPORT_STORE);
            } else if (result == State.Fail.value) {
                response.sendRedirect(ADMIN_IMPORT_STORE + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(ADMIN_IMPORT_LIST_URI)
                || path.startsWith(ADMIN_IMPORT_LIST_URI + "/page")) {
            System.out.println("Going Import List");
            int result = -1;
            if (!path.endsWith("?errAccNF=true") && !path.endsWith("?errImpNF")) {
                result = searchImport(request);
            }
            if (result == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/Import/list.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(
                        ADMIN_IMPORT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            } else if (result == -1) {
                response.sendRedirect(ADMIN_IMPORT_LIST_URI);
            }
            return;
        }

        if (path.startsWith(ADMIN_IMPORT_DETAIL_URI)) {
            System.out.println("Going Order Detail");
            int result = getImportDetail(request);
            if (result == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/Import/importDetail.jsp").forward(request,
                        response);
            } else if (result == State.Fail.value) {
                request.getRequestDispatcher(ADMIN_IMPORT_DETAIL_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(ADMIN_IMPORT_DELETE_DETAIL_URI)) {
            System.out.println("Going delete");
            int result = deleteImportDetail(request);
            if (result == State.Success.value) {
                response.sendRedirect(ADMIN_IMPORT_UPDATE_URI + "/ID/" + (String) request.getAttribute("ImportID"));
            } else if (result == State.Fail.value) {
                response.sendRedirect(ADMIN_IMPORT_UPDATE_URI + "/ID/" + ((String) request.getAttribute("ImportID") != null ? (String) request.getAttribute("ImportID") : "-1") + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(ADMIN_IMPORT_UPDATE_URI)) {
            System.out.println("Going import update");
            int result = getUpdateImport(request);
            if (result == State.Success.value) {
                request.getRequestDispatcher("/ADMIN_PAGE/Import/update.jsp").forward(request,
                        response);
            } else if (result == State.Fail.value) {
                request.getRequestDispatcher(ADMIN_IMPORT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }
        // ---------------------------- IMPORT SECTION ----------------------------
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
                    int pID = Integer.parseInt(request.getParameter("txtProductID"));
                    response.sendRedirect(
                            ADMIN_PRODUCT_UPDATE_URI + "/ID/" + pID + ExceptionUtils.generateExceptionQueryString(request));
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

        if (path.startsWith(ADMIN_UPDATE_INFO_URI)) {
            if (request.getParameter("btnUpdateInfo") != null
                    && request.getParameter("btnUpdateInfo").equals("Submit")) {
                System.out.println("Going update info");
                if (updateAdminInfomation(request, response)) {
                    response.sendRedirect(ADMIN_USER_URI);
                } else {
                    response.sendRedirect(ADMIN_USER_URI + ExceptionUtils.generateExceptionQueryString(request));
                }
                return;
            }
        }
        if (path.startsWith(ADMIN_VOUCHER_UPDATE_URI)) {
            if (request.getParameter("btnUpdateInfo") != null
                    && request.getParameter("btnUpdateInfo").equals("Submit")) {
                System.out.println("Going update info");
                if (updateAdminInfomation(request, response)) {
                    response.sendRedirect(ADMIN_USER_URI);
                } else {
                    response.sendRedirect(ADMIN_USER_URI + ExceptionUtils.generateExceptionQueryString(request));
                }
                return;
            }
        }

        if (path.startsWith(ADMIN_IMPORT_CONFIRM_UPDATE_URI)) {
            if (request.getParameter("btnCheckoutImportCart") != null
                    && request.getParameter("btnCheckoutImportCart").equals("Submit")) {
                System.out.println("Going update info");
                int result = updateImportInformation(request);
                if (result == State.Success.value) {
                    response.sendRedirect(ADMIN_USER_URI);
                } else if (result == State.Fail.value) {
                    response.sendRedirect(ADMIN_USER_URI + ExceptionUtils.generateExceptionQueryString(request));
                }
                return;
            }
        }
    }

    /* CRUD */
    // ---------------------------- CREATE SECTION ----------------------------
    private int addProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDAO = new BrandDAO();
        String pName = request.getParameter("txtProductName");
        String bName = request.getParameter("txtBrandName");
        int pPrice = Integer.parseInt(request.getParameter("txtProductPrice").replace(",", ""));
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
        product.setStock(stock);

        Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        String username = userCookie.getValue();
        AdminDAO adDAO = new AdminDAO();
        Admin admin = adDAO.getAdmin(username);

        // Upload data to db
        int kq;
        try {
            kq = pDAO.addProduct(product, admin);
            if (kq == 0) {
                System.out.println("Add failed, Some attribute may be duplicate");
                request.setAttribute("exceptionType", "OperationAddFailedException");
                return State.Fail.value;
            }
        } catch (InvalidInputException ex) {
            request.setAttribute("exceptionType", "InvalidInputException");
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
        employeeToAdd.setDateOfBirth(Converter.convertStringToEpochMilli(eDateOfBirth));
        employeeToAdd.setPhoneNumber(ePhoneNumber);
        employeeToAdd.setAddress(eAddress);
        employeeToAdd.setJoinDate(Converter.convertStringToEpochMilli(eJoinDate));
        employeeToAdd.setRetireDate(null);
        employeeToAdd.setRole(eRole);

        int result = 0;

        result = eDAO.addEmployee(employeeToAdd);

        if (result == 0) {
            System.out.println("Failed to update the user with ID " + uName + " to database");
            return false;
        }

        return true;
    }

    // ---------------------------- READ SECTION ----------------------------
    private int getImportDetail(HttpServletRequest request) {
        ImportDAO ipDAO = new ImportDAO();
        AdminDAO adDAO = new AdminDAO();

        String data[] = request.getRequestURI().split("/");

        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        Admin ad = adDAO.getAdmin(username);

        if (ad == null) {
            System.out.println("Unknow Username to view import detail");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }

        try {
            Import ip = null;
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals("ID")) {
                    int importId = Integer.parseInt(data[i + 1]);
                    ip = ipDAO.getImport(importId);
                }
            }
            if (ip == null) {
                System.out.println("not found import detail");
                request.setAttribute("exceptionType", "ImportNotFoundException");
                return State.Fail.value;
            }
            if (ip.getImportDetail() == null || ip.getImportDetail().isEmpty()) {
                System.out.println("import has no import detail");
                request.setAttribute("exceptionType", "ImportNotFoundException");
                return State.Fail.value;
            }
            request.setAttribute("importInfo", ip);
            return State.Success.value;
        } catch (NumberFormatException e) {
            request.setAttribute("exceptionType", "ImportNotFoundException");
            return State.Fail.value;
        }
    }

    private int getUpdateImport(HttpServletRequest request) {
        ImportDAO ipDAO = new ImportDAO();
        AdminDAO adDAO = new AdminDAO();

        String data[] = request.getRequestURI().split("/");

        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        Admin ad = adDAO.getAdmin(username);

        if (ad == null) {
            System.out.println("Unknow Username to update import");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }

        try {
            Import ip = null;
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals("ID")) {
                    int importId = Integer.parseInt(data[i + 1]);
                    ip = ipDAO.getImport(importId);
                }
            }
            if (ip == null) {
                System.out.println("not found import detail");
                request.setAttribute("exceptionType", "ImportNotFoundException");
                return State.Fail.value;
            }
            if (ip.getImportDetail() == null || ip.getImportDetail().isEmpty()) {
                System.out.println("import has no import detail");
                request.setAttribute("exceptionType", "ImportNotFoundException");
                return State.Fail.value;
            }
            request.setAttribute("importInfo", ip);
            return State.Success.value;
        } catch (NumberFormatException e) {
            request.setAttribute("exceptionType", "ImportNotFoundException");
            return State.Fail.value;
        }
    }

    private int searchImport(HttpServletRequest request) {
        AdminDAO adDAO = new AdminDAO();
        ImportDAO ipDAO = new ImportDAO();
        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        Admin ad = adDAO.getAdmin(username);

        if (ad == null) {
            System.out.println("Unknow Username to view import");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        int rows = 20;
        String search = request.getParameter("txtSearch");
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }
        List<Import> importList = ipDAO.searchImport(search);
        if (importList.isEmpty()) {
            System.out.println("Empty import list");
            request.setAttribute("exceptionType", "ImportNotFoundException");
            return State.Fail.value;
        }
        int numberOfPage = (importList.size() / rows) + (importList.size() % rows == 0 ? 0 : 1);
        importList = Generator.pagingList(importList, page, rows);
        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", numberOfPage);
        request.setAttribute("importList", importList);
        request.setAttribute("search", search);
        return State.Success.value;
    }

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
        List<Product> productList = new ArrayList<>();
        try {
            productList = pDAO.searchProduct(search);
        } catch (ProductNotFoundException ex) {
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

    private int searchVoucher(HttpServletRequest request) {
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        int rows = 20;
        String search = request.getParameter("txtSearch");
        VoucherDAO vDAO = new VoucherDAO();

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }
        List<Voucher> voucherList = vDAO.getAllVoucher();

        if (voucherList.isEmpty()) {
            System.out.println("Empty voucher list");
            request.setAttribute("exceptionType", "VoucherNotFoundException");
            return State.Fail.value;
        }

        int numberOfPage = (voucherList.size() / rows) + (voucherList.size() % rows == 0 ? 0 : 1);
        voucherList = Generator.pagingList(voucherList, page, rows);

        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", numberOfPage);
        request.setAttribute("voucherList", voucherList);
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

    private void adminActivityLog(HttpServletRequest request, HttpServletResponse response) {
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        int rows = 20;
        String Search = request.getParameter("txtSearch");
        ProductActivityLogDAO productActivityLogDAO = new ProductActivityLogDAO();

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }

        if (Search == null || Search.equals("")) {
            Search = "%";
        }

        List<ProductActivityLog> adminActivityLogsFromSearch = productActivityLogDAO.searchProductActivityLog(Search);
        List<ProductActivityLog> listAdminActivityLogs = Generator.pagingList(adminActivityLogsFromSearch, page, rows);

        final int ROWS = 20;
        int NumberOfPage = adminActivityLogsFromSearch.size() / ROWS;
        NumberOfPage = (adminActivityLogsFromSearch.size() % ROWS == 0 ? NumberOfPage : NumberOfPage + 1);

        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", NumberOfPage);
        request.setAttribute("listActivityLogs", listAdminActivityLogs);
        request.setAttribute("Search", Search);
    }

    private void orderManagerActivityLog(HttpServletRequest request, HttpServletResponse response) {
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        int rows = 20;
        String Search = request.getParameter("txtSearch");
        OrderDAO orderDAO = new OrderDAO();

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }

        if (Search == null || Search.equals("")) {
            Search = "%";
        }

        List<Order> adminActivityLogsFromSearch = orderDAO.searchOrderActivityLog(Search);
        List<Order> listOrderActivityLogs = Generator.pagingList(adminActivityLogsFromSearch, page, rows);

        final int ROWS = 20;
        int NumberOfPage = adminActivityLogsFromSearch.size() / ROWS;
        NumberOfPage = (adminActivityLogsFromSearch.size() % ROWS == 0 ? NumberOfPage : NumberOfPage + 1);
        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", NumberOfPage);
        request.setAttribute("listActivityLogs", listOrderActivityLogs);
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
        } catch (ProductNotFoundException ex) {
            request.setAttribute("exceptionType", "ProductNotFoundException");
            return State.Fail.value;
        }
    }

    // ---------------------------- UPDATE SECTION ----------------------------
    private int updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Get the text parameter in order to update
        ProductDAO pDAO = new ProductDAO();
        BrandDAO bDAO = new BrandDAO();
        int pID = Integer.parseInt(request.getParameter("txtProductID"));
        Product oldProduct;
        try {
            oldProduct = pDAO.getProduct(pID);
        } catch (ProductNotFoundException ex) {
            request.setAttribute("exceptionType", "ProductNotFoundException");
            return State.Fail.value;
        }
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
        int kq;
        try {
            kq = pDAO.updateProduct(product, admin);
            if (kq == 0) {
                System.out.println("Update Failed, The Product is not in the database");
                request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.OperationEditFailedException.toString());
                return State.Fail.value;
            }
        } catch (InvalidInputException ex) {
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.InvalidInputException.toString());
            return State.Fail.value;
        }

        System.out.println("Update Product with ID: " + pID + " successfully!");
        return State.Success.value;
    }

    private boolean updateCustomer(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDAO = new UserDAO();
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
        String oldEmail = userForUpdate.getEmail();

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
                es.sendEmailByThread(es.CHANGE_PASSWORD_NOTFICATION,
                        es.changePasswordNotifcation());
            }
            if (isChangedEmail) {
                System.out.println("Detect email change");

                es.setEmailTo(oldEmail);
                es.sendEmailByThread(es.CHANGE_EMAIL_NOTFICATION,
                        es.changeEmailNotification(uEmail));
            }
            if (isChangedUsername) {
                System.out.println("Detect username change");
                System.out.println("sending mail changing username");
                es.setEmailTo(uEmail);
                es.sendEmailByThread(es.CHANGE_USERNAME_NOTFICATION, es.changeUsernameNotification(uUsername));
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
        String oldEmail = employeeForUpdate.getEmail();

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
        employeeForUpdate.setDateOfBirth(Converter.convertStringToEpochMilli(eDateOfBirth));
        employeeForUpdate.setAddress(eAddress);
        employeeForUpdate.setJoinDate(Converter.convertStringToEpochMilli(eJoinDate));
        employeeForUpdate.setRetireDate(Converter.convertStringToEpochMilli(eRetireDate));

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
                es.sendEmailByThread(es.CHANGE_PASSWORD_NOTFICATION,
                        es.changePasswordNotifcation());
            }
            if (isChangedEmail) {
                System.out.println("Detect email change");
                System.out.println("sending mail changing email");
                es.setEmailTo(oldEmail);
                es.sendEmailByThread(es.CHANGE_EMAIL_NOTFICATION,
                        es.changeEmailNotification(uEmail));
            }
            if (isChangedUsername) {
                System.out.println("Detect username change");
                System.out.println("sending mail changing username");
                es.setEmailTo(uEmail);
                es.sendEmailByThread(es.CHANGE_USERNAME_NOTFICATION, es.changeUsernameNotification(uUsername));
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
        } catch (InvalidInputException ex) {
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.InvalidInputException.toString());
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
        } catch (ProductNotFoundException ex) {
            request.setAttribute("exceptionType", "ProductNotFoundException");
            return State.Fail.value;
        }

        request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.ProductNotFoundException.toString());
        return State.Fail.value;
    }

    private int getUpdateVoucher(HttpServletRequest request) {
        VoucherDAO vDAO = new VoucherDAO();
        try {
            String data[] = request.getRequestURI().split("/");
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals("ID")) {
                    int vId = Integer.parseInt(data[i + 1]);
                    Voucher v = vDAO.getVoucher(vId);
                    if (v != null) {
                        List<Integer> approvedProductId = vDAO.getAllApprovedProductIdByVoucherId(vId);
                        request.setAttribute("VoucherUpdate", v);
                        request.setAttribute("ApprovedProductId", approvedProductId);
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

    private int updateImportInformation(HttpServletRequest request) {
        return 0;
    }

    private boolean updateAdminInfomation(HttpServletRequest request,
            HttpServletResponse response) {
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

        AdminDAO adDAO = new AdminDAO();
        UserDAO usDAO = new UserDAO();
        Admin admin = adDAO.getAdmin(currentUserCookie.getValue());
        String oldEmail = admin.getEmail();

        boolean isChangedEmail = true;
        boolean isChangedPassword = true;
        boolean isChangedUsername = true;
        // Username, email, phone number is unique

        try {
            if (!email.equals(admin.getEmail())) {
                if (usDAO.getUserByEmail(email) != null) {
                    throw new EmailDuplicationException();
                }
            } else {
                isChangedEmail = false;
            }

            if (!username.equals(admin.getUsername())) {
                if (usDAO.getUser(username) != null) {
                    throw new UsernameDuplicationException();
                }

            } else {
                isChangedUsername = false;
            }

            if (currentPassword == null || currentPassword.equals("")) {
                isChangedPassword = false;
                // check if currentPassword is true
            } else {
                usDAO.login(admin.getUsername(), currentPassword, IUserDAO.loginType.Username);
            }
            // Email and username duplication must come first
        } catch (WrongPasswordException e) {
            request.setAttribute("exceptionType", "WrongPasswordException");
            return false;
        } catch (AccountDeactivatedException e) {
            request.setAttribute("exceptionType", "AccountDeactivatedException");
            return false;
        } catch (EmailDuplicationException e) {
            request.setAttribute("exceptionType", "EmailDuplicationException");
            return false;
        } catch (UsernameDuplicationException e) {
            request.setAttribute("exceptionType", "UsernameDuplicationException");
            return false;
        } catch (InvalidInputException ex) {
            request.setAttribute("exceptionType", "InvalidInputException");
            return false;
        }
        if (isChangedPassword
                && request.getParameter("pwdNew") != null
                && !request.getParameter("pwdNew").equals("")) {
            newPassword = request.getParameter("pwdNew");
            newPassword = Converter.convertToMD5Hash(newPassword);
        } else {
            newPassword = admin.getPassword();
        }
        admin.setName(fullname);
        admin.setUsername(username);
        admin.setPassword(newPassword);
        admin.setEmail(email);

        int result = adDAO.updateUser(admin);
        if (result != 1) {
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.OperationEditFailedException.toString());
            return false;
        }
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
                es.sendEmailByThread(es.CHANGE_PASSWORD_NOTFICATION, es.changePasswordNotifcation());
            }
            if (isChangedEmail) {
                System.out.println("Detect email change");
                System.out.println("sending mail changing email");
                es.setEmailTo(oldEmail);
                es.sendEmailByThread(es.CHANGE_EMAIL_NOTFICATION, es.changeEmailNotification(email));
            }
            if (isChangedUsername) {
                System.out.println("Detect username change");
                System.out.println("sending mail changing username");
                es.setEmailTo(email);
                es.sendEmailByThread(es.CHANGE_USERNAME_NOTFICATION, es.changeUsernameNotification(username));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("update account with ID " + admin.getId() + " successfully");
        return true;
    }

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
        } catch (InvalidInputException ex) {
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.InvalidInputException.toString());
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

    private int deleteImportDetail(HttpServletRequest request) {
        ImportDAO ipDAO = new ImportDAO();
        AdminDAO adDAO = new AdminDAO();
        ImportDetailDAO ipDetailDAO = new ImportDetailDAO();
        ProductDAO pDAO = new ProductDAO();
        String data[] = request.getRequestURI().split("/");

        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        Admin ad = adDAO.getAdmin(username);

        if (ad == null) {
            System.out.println("Unknow Username to delete import detail");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }

        try {
            ImportDetail ipD = null;
            int importId = -1;
            int productId = -1;
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals("ImportID")) {
                    importId = Integer.parseInt(data[i + 1]);
                }
                if (data[i].equals("ProductID")) {
                    productId = Integer.parseInt(data[i + 1]);
                }
            }
            request.setAttribute("ImportID", importId + "");
            ipD = ipDetailDAO.getImportDetail(importId, productId);
            if (ipD == null) {
                System.out.println("not found import detail");
                request.setAttribute("exceptionType", "ImportNotFoundException");
                return State.Fail.value;
            }
            if (ipD.getStatus().equals(ImportDetailDAO.Status.USED.toString())) {
                System.out.println("import detail is used so cannot delete");
                request.setAttribute("exceptionType", "OperationDeleteFailedException");
                return State.Fail.value;
            }
            int result = ipDetailDAO.deleteImportDetail(ipD);
            if (result == 0) {
                System.out.println("delete import detail fail");
                request.setAttribute("exceptionType", "OperationDeleteFailedException");
                return State.Fail.value;
            }
            Import ip = ipDAO.getImport(importId);
            ip.setTotalCost(ip.getTotalCost() - ipD.getCost() * ipD.getQuantity());
            ip.setTotalQuantity(ip.getTotalQuantity() - ipD.getQuantity());
            result = ipDAO.updateImport(ip, ad.getAdminId());
            if (result == 0) {
                System.out.println("update import fail so cannot delete");
                ipDetailDAO.addImportDetail(ipD);
                request.setAttribute("exceptionType", "OperationDeleteFailedException");
                return State.Fail.value;
            }
            return State.Success.value;
        } catch (NumberFormatException e) {
            request.setAttribute("exceptionType", "OperationDeleteFailedException");
            return State.Fail.value;
        }
    }

    private int deleteVoucher(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    private int searchImportDetail(HttpServletRequest request) {
        AdminDAO adDAO = new AdminDAO();
        ImportDetailDAO ipDetailDAO = new ImportDetailDAO();
        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        Admin ad = adDAO.getAdmin(username);

        if (ad == null) {
            System.out.println("Unknow Username to view import detail");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        int rows = 20;
        String search = request.getParameter("txtSearch");
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }
        List<ImportDetail> importDetailList = ipDetailDAO.searchImportDetail(search);
        // importDetailList = ipDetailDAO.fillterImportDetail();
        if (importDetailList.isEmpty()) {
            System.out.println("Empty import detail list");
            request.setAttribute("exceptionType", "ImportNotFoundException");
            return State.Fail.value;
        }
        int numberOfPage = (importDetailList.size() / rows) + (importDetailList.size() % rows == 0 ? 0 : 1);
        importDetailList = Generator.pagingList(importDetailList, page, rows);
        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", numberOfPage);
        request.setAttribute("importDetailList", importDetailList);
        request.setAttribute("search", search);
        return State.Success.value;
    }

    private int bringToStock(HttpServletRequest request) {
        AdminDAO adDAO = new AdminDAO();
        ImportDetailDAO ipDetailDAO = new ImportDetailDAO();
        ProductDAO pDAO = new ProductDAO();
        String data[] = request.getRequestURI().split("/");

        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        Admin ad = adDAO.getAdmin(username);

        if (ad == null) {
            System.out.println("Unknow Username to add to stock");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }

        try {
            ImportDetail ipD = null;
            int importId = -1;
            int productId = -1;
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals("ImportID")) {
                    importId = Integer.parseInt(data[i + 1]);
                }
                if (data[i].equals("ProductID")) {
                    productId = Integer.parseInt(data[i + 1]);
                }
            }
            ipD = ipDetailDAO.getImportDetail(importId, productId);
            if (ipD == null) {
                System.out.println("not found import detail");
                request.setAttribute("exceptionType", "ImportNotFoundException");
                return State.Fail.value;
            }
            Product pd = pDAO.getProduct(productId);
            Stock st = pd.getStock();
            if (pd == null || st == null) {
                System.out.println("not found product to update stock");
                request.setAttribute("exceptionType", "ProductNotFoundException");
                return State.Fail.value;
            }
            st.setQuantity(st.getQuantity() + ipD.getQuantity());
            int result = pDAO.updateProduct(pd, ad);
            if (result == 0) {
                System.out.println("update stock failt");
                request.setAttribute("exceptionType", "OperationEditFailedException");
                return State.Fail.value;
            }
            ipD.setStatus(ImportDetailDAO.Status.USED.toString());
            result = ipDetailDAO.updateImportDetail(ipD);
            if (result == 0) {
                st.setQuantity(st.getQuantity() - ipD.getQuantity());
                pDAO.updateProduct(pd, ad);
                System.out.println("set status for import detail fail");
                request.setAttribute("exceptionType", "OperationEditFailedException");
                return State.Fail.value;
            }
            return State.Success.value;
        } catch (NumberFormatException e) {
            request.setAttribute("exceptionType", "OperationEditFailedException");
            return State.Fail.value;
        } catch (InvalidInputException ex) {
            request.setAttribute("exceptionType", "InvalidInputException");
            return State.Fail.value;

        } catch (ProductNotFoundException ex) {
            request.setAttribute("exceptionType", "ProductNotFoundException");
            return State.Fail.value;

        }
    }

}
