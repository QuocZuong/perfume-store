package Controllers;

import DAOs.CartItemDAO;
import DAOs.CustomerDAO;
import DAOs.DeliveryAddressDAO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import DAOs.OrderDAO;
import DAOs.ProductDAO;
import DAOs.UserDAO;
import DAOs.VoucherDAO;
import Exceptions.AccountDeactivatedException;
import Exceptions.EmailDuplicationException;
import Exceptions.InvalidInputException;
import Exceptions.InvalidVoucherException;
import Exceptions.NotEnoughInformationException;
import Exceptions.NotEnoughVoucherQuantityException;
import Exceptions.OperationAddFailedException;
import Exceptions.PhoneNumberDuplicationException;
import Exceptions.UsernameDuplicationException;
import Exceptions.VoucherNotFoundException;
import Exceptions.WrongPasswordException;
import Interfaces.DAOs.IUserDAO;
import Lib.Converter;
import Lib.DatabaseUtils;
import Lib.EmailSender;
import Lib.ExceptionUtils;
import Lib.Generator;
import Models.CartItem;
import Models.Customer;
import Models.DeliveryAddress;
import Models.Order;
import Models.Product;
import Models.User;
import Models.Voucher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import Interfaces.DAOs.IOrderDAO;
import Models.OrderDetail;

public class CustomerController extends HttpServlet {

    // ---------------------------- URI DECLARATION SECTION
    // ----------------------------
    public static final String CUSTOMER_CART_URI = "/Customer/Cart";
    public static final String CUSTOMER_CART_CHECKOUT_URI = "/Customer/Cart/Checkout";
    public static final String CUSTOMER_CART_DELETE_URI = "/Customer/Cart/Delete";
    public static final String CUSTOMER_CART_UPDATE_URI = "/Customer/Cart/Update";
    public static final String CUSTOMER_ADD_TO_CART_URI = "/Customer/addToCart";
    public static final String CUSTOMER_USER_URI = "/Customer/User";
    public static final String CUSTOMER_UPDATE_INFO_URI = "/Customer/Update/Info";

    public static final String CUSTOMER_UPDATE_ADDRESS_URI = "/Customer/Update/Address";
    public static final String CUSTOMER_ADD_ADDRESS_URI = "/Customer/Update/Address";
    public static final String CUSTOMER_DELETE_ADDRESS_URI = "/Customer/User/Delete/Address/ID";

    public static final String CUSTOMER_CHECKOUT_URI = "/Customer/Checkout";
    public static final String CUSTOMER_ORDER_DETAIL_URI = "/Customer/Order/Detail/ID";

    public static final String BTN_ADD_DELIVERY_ADDRESS = "btnAddAddress";
    public static final String BTN_DELETE_DELIVERY_ADDRESS = "btnDeleteAddress";
    public static final String BTN_UPDATE_DELIVERY_ADDRESS = "btnUpdateAddress";

    public static final String BTN_ADD_TO_CART = "btnAddToCart";
    public static final String BTN_CHECKOUT_CART = "btnCheckoutCart";
    public static final String SUBMIT_VALUE = "Submit";

    public static final String PRODUCT_DETAIL_URI = "/Product/Detail/ID/";

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
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("Request Path URI " + path);

        //
        if (path.startsWith(CUSTOMER_CART_DELETE_URI)) {
            System.out.println("Going delete");
            deleteCartProduct(request, response);
            response.sendRedirect(CUSTOMER_CART_URI);
            return;
        }
        //
        if (path.startsWith(CUSTOMER_CART_UPDATE_URI)) {
            System.out.println("Going update");
            updateCartProduct(request, response);
            response.sendRedirect(CUSTOMER_CART_URI);
            return;
        }

        if (path.startsWith(CUSTOMER_CART_URI)) {
            System.out.println("Going cart");
            int result = getCartProduct(request);
            if (result == State.Success.value) {
                request.getRequestDispatcher("/CUSTOMER_PAGE/cart.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                System.out.println("Invalid voucher to add. Going to /Customer/Cart");
                response.sendRedirect(CUSTOMER_CART_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(CUSTOMER_USER_URI)) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            System.out.println("Going user");
            request.getRequestDispatcher("/CUSTOMER_PAGE/user.jsp").forward(request, response);
            return;
        }
        if (path.startsWith(CUSTOMER_DELETE_ADDRESS_URI)) {
            System.out.println("Going delete address");
            deleteCustomerDeliveryAddress(request);
            response.sendRedirect(CUSTOMER_USER_URI);
            return;
        }

        //
        if (path.startsWith(CUSTOMER_ORDER_DETAIL_URI)) {
            System.out.println("Going Order Detail");
            int result = getCustomerOrderDetail(request);
            if (result == State.Success.value) {
                request.getRequestDispatcher("/CUSTOMER_PAGE/order_detail.jsp").forward(request,
                        response);
            } else {
                request.getRequestDispatcher(CUSTOMER_USER_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        System.out.println("Going home");
        response.sendRedirect("/");
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
        if (path.startsWith(CUSTOMER_ADD_TO_CART_URI)) {
            if (request.getParameter(BTN_ADD_TO_CART) != null
                    && request.getParameter(BTN_ADD_TO_CART).equals(SUBMIT_VALUE)) {
                int result = addToCart(request, response);
                int pID = Integer.parseInt(request.getParameter("ProductID"));
                if (result == State.Success.value) {

                } else if (result == State.Fail.value) {
                    response.sendRedirect(
                            PRODUCT_DETAIL_URI + pID + ExceptionUtils.generateExceptionQueryString(request));
                }
                response.sendRedirect("/Product/Detail/ID/" + pID);
                return;
            }
        }

        if (path.startsWith(CUSTOMER_CART_CHECKOUT_URI)) {
            if (request.getParameter(BTN_CHECKOUT_CART) != null
                    && request.getParameter(BTN_CHECKOUT_CART).equals(SUBMIT_VALUE)) {
                System.out.println("going checkout cart");
                int result = checkOutCart(request, response);
                if (result == State.Success.value) {
                    System.out.println("Chek cart valid. Going to /Customer/Checkout");
                    request.getRequestDispatcher("/CUSTOMER_PAGE/checkout.jsp").forward(request, response);
                } else {
                    System.out.println("Chek cart invalid. Going to /Customer/Cart");
                    response.sendRedirect(CUSTOMER_CART_URI + ExceptionUtils.generateExceptionQueryString(request));
                }
            }
        }

        if (path.startsWith(CUSTOMER_UPDATE_INFO_URI)) {
            if (request.getParameter("btnUpdateInfo") != null
                    && request.getParameter("btnUpdateInfo").equals("Submit")) {
                System.out.println("Going update info");
                if (updateClientInfomation(request, response)) {
                    response.sendRedirect(CUSTOMER_USER_URI);
                } else {
                    response.sendRedirect(CUSTOMER_USER_URI + checkException(request));
                }
                return;
            }
        }

        if (path.startsWith(CUSTOMER_UPDATE_ADDRESS_URI)) {
            if (request.getParameter("btnUpdateAddress") != null
                    && request.getParameter("btnUpdateAddress").equals("Submit")) {
                System.out.println("Going update address");
                if (updateCustomerDeliveryAddress(request, response) > 0) {
                    response.sendRedirect(CUSTOMER_USER_URI);
                } else {
                    response.sendRedirect(CUSTOMER_USER_URI + ExceptionUtils.generateExceptionQueryString(request));
                }
                return;
            }

            if (request.getParameter("btnAddAddress") != null
                    && request.getParameter("btnAddAddress").equals("Submit")) {
                System.out.println("Going add address");
                if (addCustomerDeliveryAddress(request) > 0) {
                    response.sendRedirect(CUSTOMER_USER_URI);
                } else {
                    response.sendRedirect(CUSTOMER_USER_URI + ExceptionUtils.generateExceptionQueryString(request));
                }
                return;
            }

            if (request.getParameter("btnDeleteAddress") != null
                    && request.getParameter("btnDeleteAddress").equals("Submit")) {
                System.out.println("Going delete delivery address");
                int result = deleteCustomerDeliveryAddress(request);

                if (result == State.Success.value) {
                    response.sendRedirect(CUSTOMER_USER_URI);
                } else if (result == State.Fail.value) {
                    response.sendRedirect(CUSTOMER_USER_URI + ExceptionUtils.generateExceptionQueryString(request));
                }

                return;
            }
        }
        if (path.startsWith(CUSTOMER_CHECKOUT_URI)) {
            if (request.getParameter("btnSubmitCheckOut") != null
                    && request.getParameter("btnSubmitCheckOut").equals("Submit")) {
                System.out.println("Going Checkout");
                int result = customerCheckout(request);
                if (result == State.Success.value) {
                    response.sendRedirect(CUSTOMER_ORDER_DETAIL_URI + "=" + request.getParameter("OrderID")
                            + request.getParameter("CheckOutSuccess"));
                } else {
                    response.sendRedirect(CUSTOMER_USER_URI + ExceptionUtils.generateExceptionQueryString(request));
                }
            }
        }

    }

    /* CRUD */
    // ---------------------------- CREATE SECTION ----------------------------
    private int addToCart(HttpServletRequest request, HttpServletResponse response) {
        int result;
        CustomerDAO cusDAO = new CustomerDAO();

        Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        String username = userCookie.getValue();

        int pID = Integer.parseInt(request.getParameter("ProductID"));
        int pQuan = Integer.parseInt(request.getParameter("ProductQuantity"));
        int pPrice = Integer.parseInt(request.getParameter("ProductPrice"));
        int pSum = pQuan * pPrice;

        // Assume having client Username and get Client ID from username
        Customer cus = cusDAO.getCustomer(username);
        if (cus == null) {
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }

        int CustomerID = cus.getCustomerId();
        System.out.println("CustomerID" + CustomerID);

        CartItemDAO ciDAO = new CartItemDAO();
        CartItem ci = new CartItem();
        ci.setCustomerId(CustomerID);
        ci.setProductId(pID);
        ci.setQuantity(pQuan);
        ci.setPrice(pPrice);
        ci.setSum(pSum);
        result = ciDAO.addToCart(ci);
        if (result == 0) {
            request.setAttribute("exceptionType", "OperationAddFailedException");
            return State.Fail.value;
        }
        return State.Success.value;

    }

    /**
     * Add a new delivery address to the database
     *
     * @param request The request object
     * @return 1 if the operation is successful, 0 otherwise
     */
    private int addCustomerDeliveryAddress(HttpServletRequest request) {
        final Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        final String username = userCookie.getValue();
        final String address = request.getParameter("txtAddress");
        final String phoneNumber = request.getParameter("txtPhoneNumber");
        final String status = request.getParameter("txtStatus") == null ? "non" : "Default";
        final String receiverName = request.getParameter("txtReceiverName");

        if (username == null || username.equals("")) {
            request.setAttribute("exceptionType",
                    ExceptionUtils.ExceptionType.NotEnoughInformationException.toString());
            return State.Fail.value;
        }
        if (address == null || address.equals("")) {
            request.setAttribute("exceptionType",
                    ExceptionUtils.ExceptionType.NotEnoughInformationException.toString());
            return State.Fail.value;
        }
        if (phoneNumber == null || phoneNumber.equals("")) {
            request.setAttribute("exceptionType",
                    ExceptionUtils.ExceptionType.NotEnoughInformationException.toString());
            return State.Fail.value;
        }
        if (receiverName == null || receiverName.equals("")) {
            request.setAttribute("exceptionType",
                    ExceptionUtils.ExceptionType.NotEnoughInformationException.toString());
            return State.Fail.value;
        }

        final DeliveryAddressDAO daDao = new DeliveryAddressDAO();
        final CustomerDAO cDao = new CustomerDAO();

        final DeliveryAddress da = new DeliveryAddress();
        final int customerID = cDao.getCustomer(username).getCustomerId();

        da.setCustomerId(customerID);
        da.setAddress(address);
        da.setPhoneNumber(phoneNumber);
        da.setStatus(status);
        da.setReceiverName(receiverName);
        da.setCreateAt(Generator.generateDateTime());
        da.setModifiedAt(da.getCreateAt());

        // If the address is set to default, set all other addresses to non-default
        if (da.getStatus().equals("Default")) {
            final List<DeliveryAddress> temp = daDao.getAll(customerID);

            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getStatus().equals("Default")) {
                    temp.get(i).setStatus("non");
                    daDao.updateDeliveryAddress(temp.get(i));
                }
            }
        }

        final int result = daDao.addDeliveryAddress(da) > 0 ? State.Success.value : State.Fail.value;

        return result;
    }

    // ---------------------------- READ SECTION ----------------------------
    private int getCartProduct(HttpServletRequest request) {
        CustomerDAO cusDAO = new CustomerDAO();
        CartItemDAO ciDAO = new CartItemDAO();
        Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        String username = userCookie.getValue();
        int CustomerID = cusDAO.getCustomer(username).getCustomerId();
        System.out.println("cus id:" + CustomerID);
        List<CartItem> listCartItem = ciDAO.getAllCartItemOfCustomer(CustomerID);
        // Handling out of stock
        List<Product> listOutOfStock = ciDAO.getAllOutOfStockProductFromCart(CustomerID);
        request.setAttribute("listOutOfStock", listOutOfStock);
        if (!listOutOfStock.isEmpty()) {
            for (int i = 0; i < listOutOfStock.size(); i++) {
                ciDAO.deleteCartItem(CustomerID, listOutOfStock.get(i).getId());
                System.out.println(
                        "delete product " + listOutOfStock.get(i).getName() + " from cart user ID:" + CustomerID);
                if (!listCartItem.isEmpty()) {
                    for (int j = 0; j < listCartItem.size(); j++) {
                        if (listCartItem.get(j).getProductId() == listOutOfStock.get(i).getId()) {
                            listCartItem.remove(j);
                        }
                    }
                }
            }
        }
        request.setAttribute("listCartItem", listCartItem);
        request.setAttribute("customerID", CustomerID);

        String voucherCode = request.getParameter("VoucherCode");
        if (voucherCode != null && !voucherCode.equals("")) {
            VoucherDAO vDAO = new VoucherDAO();
            Voucher v = vDAO.getVoucher(voucherCode);
            try {
                if (vDAO.checkValidVoucher(v, CustomerID)) {
                    ProductDAO pDAO = new ProductDAO();
                    System.out.println("cus id add voucher:" + CustomerID);
                    List<CartItem> cartItemList = ciDAO.getAllCartItemOfCustomer(CustomerID);
                    List<Product> approvedProduct = new ArrayList<>();
                    int sumDeductPrice = 0;
                    Product p;
                    for (int i = 0; i < cartItemList.size(); i++) {
                        if (v.getApprovedProductId().contains(cartItemList.get(i).getProductId())) {
                            p = pDAO.getProduct(cartItemList.get(i).getProductId());
                            approvedProduct.add(p);
                            sumDeductPrice += p.getStock().getPrice() * v.getDiscountPercent() / 100;
                        }
                    }

                    request.setAttribute("sumDeductPrice", sumDeductPrice);
                    request.setAttribute("approvedProduct", approvedProduct);
                    request.setAttribute("voucher", v);
                }
            } catch (VoucherNotFoundException ex) {
                request.setAttribute("exceptionType", "VoucherNotFoundException");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                return State.Fail.value;

            } catch (InvalidVoucherException ex) {
                request.setAttribute("exceptionType", "InvalidVoucherException");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                return State.Fail.value;

            } catch (NotEnoughVoucherQuantityException ex) {
                request.setAttribute("exceptionType", "NotEnoughVoucherQuantityException");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                return State.Fail.value;

            }
        }
        return State.Success.value;
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
    // ---------------------------- UPDATE SECTION ----------------------------

    private boolean updateClientInfomation(HttpServletRequest request, HttpServletResponse response) {
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

        CustomerDAO cusDAO = new CustomerDAO();
        UserDAO usDAO = new UserDAO();
        User user = usDAO.getUser(currentUserCookie.getValue());

        boolean isChangedEmail = true;
        boolean isChangedPassword = true;
        boolean isChangedUsername = true;
        // Username, email, phone number is unique
        try {
            if (!email.equals(user.getEmail())) {
                if (usDAO.getUserByEmail(email) != null) {
                    throw new EmailDuplicationException();
                }
            } else {
                isChangedEmail = false;
            }

            if (!username.equals(user.getUsername())) {
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
                usDAO.login(user.getUsername(), currentPassword, IUserDAO.loginType.Username);
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
            newPassword = user.getPassword();
        }

        Customer updateCustomer = new Customer();
        updateCustomer.setId(user.getId());
        updateCustomer.setName(fullname);
        updateCustomer.setUsername(username);
        updateCustomer.setPassword(newPassword);
        updateCustomer.setEmail(email);

        cusDAO.updateUser(updateCustomer);

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
                es.sendToEmail(es.CHANGE_PASSWORD_NOTFICATION, es.changePasswordNotifcation());
            }
            if (isChangedEmail) {
                System.out.println("Detect email change");
                System.out.println("sending mail changing email");
                es.setEmailTo(user.getEmail());
                es.sendToEmail(es.CHANGE_EMAIL_NOTFICATION, es.changeEmailNotification(email));
            }
            if (isChangedUsername) {
                System.out.println("Detect username change");
                System.out.println("sending mail changing username");
                es.setEmailTo(email);
                es.sendToEmail(es.CHANGE_USERNAME_NOTFICATION, es.changeUsernameNotification(username));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("update account with ID " + user.getId() + " successfully");
        return true;
    }

    /**
     * Update a delivery address
     *
     * @param request  The request object
     * @param response The response object
     * @return 1 if the operation is successful, 0 otherwise
     */
    private int updateCustomerDeliveryAddress(HttpServletRequest request, HttpServletResponse response) {
        final Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        final CustomerDAO cDao = new CustomerDAO();
        final DeliveryAddressDAO daDao = new DeliveryAddressDAO();

        final String username = userCookie.getValue();
        final Customer c = cDao.getCustomer(username);
        final int customerID = c.getCustomerId();

        final String addressId = request.getParameter("txtAddressId");

        int id = -1;
        try {
            id = Integer.parseInt(addressId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String address = request.getParameter("txtAddress");
        final String phoneNumber = request.getParameter("txtPhoneNumber");
        final String status = request.getParameter("txtStatus") == null ? "non" : "Default";
        final String receiverName = request.getParameter("txtReceiverName");

        if (addressId == null || addressId.equals("")) {
            request.setAttribute("exceptionType",
                    ExceptionUtils.ExceptionType.NotEnoughInformationException.toString());
            return State.Fail.value;
        }
        if (address == null || address.equals("")) {
            request.setAttribute("exceptionType",
                    ExceptionUtils.ExceptionType.NotEnoughInformationException.toString());
            return State.Fail.value;
        }
        if (phoneNumber == null || phoneNumber.equals("")) {
            request.setAttribute("exceptionType",
                    ExceptionUtils.ExceptionType.NotEnoughInformationException.toString());
            return State.Fail.value;
        }
        if (receiverName == null || receiverName.equals("")) {
            request.setAttribute("exceptionType",
                    ExceptionUtils.ExceptionType.NotEnoughInformationException.toString());
            return State.Fail.value;
        }

        final String[] exceptionalAddresses = new String[] {
                "Tỉnh Bà Rịa - Vũng Tàu"
        };

        boolean isExceptionalAddress = false;

        for (int i = 0; i < exceptionalAddresses.length; i++) {
            if (address.contains(exceptionalAddresses[i])) {
                isExceptionalAddress = true;
                break;
            }
        }

        try {
            if (isExceptionalAddress && address.split(" - ").length < 4) {
                throw new NotEnoughInformationException();
            } else if (address.split(" - ").length < 3) {
                throw new NotEnoughInformationException();
            }
        } catch (NotEnoughInformationException e) { // TODO: Show the error to the customer's screen
            request.setAttribute("exceptionType",
                    ExceptionUtils.ExceptionType.NotEnoughInformationException.toString());
            return State.Fail.value;
        } catch (Exception e) {
            request.setAttribute("exceptionType", "Exception");
            return State.Fail.value;
        }

        final DeliveryAddress da = new DeliveryAddress();
        final DeliveryAddress existingDa = daDao.getDeliveryAdress(id);

        try {
            da.setId(id);
            da.setCustomerId(c.getCustomerId());
            da.setReceiverName(existingDa.getReceiverName());
            da.setPhoneNumber(phoneNumber);
            da.setAddress(address);
            da.setStatus(status);
            da.setReceiverName(receiverName);
            da.setModifiedAt(Generator.generateDateTime());

            if (existingDa != null) { // Doesn't update the create at, since it already existed
                da.setCreateAt(existingDa.getCreateAt());
            } else { // Also update the new create at
                da.setCreateAt(Generator.generateDateTime());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // If the address is non-deafult, and all other addresses are non-default,
        // return error
        System.out.println("Status: " + da.getStatus());
        if (!da.getStatus().equals("Default")) {
            System.out.println("Checking if all addresses are non-default");
            final List<DeliveryAddress> temp = daDao.getAll(customerID);
            boolean isAllNonDefault = true;

            for (int i = 0; i < temp.size(); i++) {
                DeliveryAddress tempDa = temp.get(i);

                if (tempDa.getId() != da.getId() && tempDa.getStatus().equals("Default")) {
                    isAllNonDefault = false;
                    break;
                }
            }

            if (isAllNonDefault) {
                request.setAttribute("exceptionType",
                        ExceptionUtils.ExceptionType.DefaultDeliveryAddressWillBeNotFoundException.toString());
                System.out.println("All addresses are non-default. Cannot add new non-default address");
                return State.Fail.value;
            }
        }

        int result = State.Fail.value;
        if (daDao.updateDeliveryAddress(da)) {
            result = State.Success.value;
        }

        if (result == State.Fail.value) { // TODO: Show the error to the customer's screen
            System.out.println("Update address failed");
            return State.Fail.value;
        }

        // If the address is set to default, set all other addresses to non-default
        if (da.getStatus().equals("Default")) {
            List<DeliveryAddress> temp = daDao.getAll(c.getCustomerId());

            for (int i = 0; i < temp.size(); i++) {
                DeliveryAddress tempDa = temp.get(i);

                if (tempDa.getId() != da.getId() && tempDa.getStatus().equals("Default")) {
                    temp.get(i).setStatus("non");
                    daDao.updateDeliveryAddress(temp.get(i));
                }
            }
        }

        System.out.println("Update address successfully");
        return State.Success.value;
    }

    private int updateCartProduct(HttpServletRequest request, HttpServletResponse response) {
        // /Client/Cart/Update?ClientID=1&ProductID0=80&ProductQuan0=5&ProductID1=34&ProductQuan1=9&ListSize=2
        int result;
        CartItemDAO ciDAO = new CartItemDAO();
        int listSize = Integer.parseInt(request.getParameter("ListSize"));
        for (int i = 0; i < listSize; i++) {
            int CustomerID = Integer.parseInt(request.getParameter("CustomerID"));
            int ProductID = Integer.parseInt(request.getParameter("ProductID" + i));
            int ProductQuan = Integer.parseInt(request.getParameter("ProductQuan" + i));
            int ProductPrice = Integer.parseInt(request.getParameter("ProductPrice" + i));
            CartItem ci = new CartItem();
            ci.setCustomerId(CustomerID);
            ci.setProductId(ProductID);
            ci.setQuantity(ProductQuan);
            ci.setPrice(ProductPrice);
            ci.setSum(ProductQuan * ProductPrice);
            result = ciDAO.updateCartItem(ci);
            if (result == 0) {
                return State.Fail.value;
            }
        }
        return State.Success.value;
    }

    // // ---------------------------- DELETE SECTION ----------------------------
    private void deleteCartProduct(HttpServletRequest request, HttpServletResponse response) {
        /// /Client/Cart/Delete/ProductID/<%= p.getID()%>/ClientID/<%= ClientID %>
        CartItemDAO ciDAO = new CartItemDAO();

        int CustomerID = -1;
        int ProductID = -1;
        String data[] = request.getRequestURI().split("/");

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("CustomerID")) {
                CustomerID = Integer.parseInt(data[i + 1]);
            } else if (data[i].equals("ProductID")) {
                ProductID = Integer.parseInt(data[i + 1]);
            }
        }
        ciDAO.deleteCartItem(CustomerID, ProductID);
    }

    private int checkOutCart(HttpServletRequest request, HttpServletResponse response) {
        CustomerDAO cusDAO = new CustomerDAO();
        CartItemDAO ciDAO = new CartItemDAO();
        ProductDAO pDAO = new ProductDAO();

        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        Customer cus = cusDAO.getCustomer(username);

        if (cus == null) {
            System.out.println("Unknow Username to checkout");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }
        int CustomerID = cus.getCustomerId();
        System.out.println("cus id checkout cart :" + cus.getCustomerId());
        List<CartItem> cartItemList = ciDAO.getAllCartItemOfCustomer(CustomerID);

        if (cartItemList.isEmpty()) {
            System.out.println("The cart is empty");
            request.setAttribute("exceptionType", "OperationAddFailedException");
            return State.Fail.value;
        }

        // check xem cac san pham trong kho co du de checkout khong
        List<Product> outOfStockProductToCheckOut = ciDAO.getAllOutOfStockProductFromCart(CustomerID);
        if (!outOfStockProductToCheckOut.isEmpty()) {
            for (int i = 0; i < outOfStockProductToCheckOut.size(); i++) {
                System.out
                        .println("Kho khong du so luong san pham co ID:" + outOfStockProductToCheckOut.get(i).getId());
            }
            request.setAttribute("exceptionType", "NotEnoughProductQuantityException");
            return State.Fail.value;
        }

        String voucherCode = request.getParameter("VoucherTXT");
        // Co su dung voucher
        if (voucherCode != null && !voucherCode.equals("")) {
            VoucherDAO vDAO = new VoucherDAO();
            Voucher v = vDAO.getVoucher(voucherCode);
            try {
                // Kiem tra tinh hop le va quang exception
                if (vDAO.checkValidVoucher(v, cus.getCustomerId())) {
                    List<Product> approveVoucherProduct = new ArrayList();
                    for (int i = 0; i < cartItemList.size(); i++) {
                        if (v.getApprovedProductId().contains(cartItemList.get(i).getProductId())) {
                            approveVoucherProduct.add(pDAO.getProduct(cartItemList.get(i).getProductId()));
                        }
                    }
                    if (approveVoucherProduct.isEmpty()) {
                        System.out.println("Khong co san pham nao ap dung duong voucher nay");
                        request.setAttribute("exceptionType", "OperationAddFailedException");
                        return State.Fail.value;
                    }
                    request.setAttribute("voucher", v);
                }
            } catch (VoucherNotFoundException ex) {
                request.setAttribute("exceptionType", "VoucherNotFoundException");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                return State.Fail.value;

            } catch (InvalidVoucherException ex) {
                request.setAttribute("exceptionType", "InvalidVoucherException");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                return State.Fail.value;

            } catch (NotEnoughVoucherQuantityException ex) {
                request.setAttribute("exceptionType", "NotEnoughVoucherException");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                return State.Fail.value;

            }
        }

        request.setAttribute("Customer", cus);
        return State.Success.value;
    }

    //
    private int customerCheckout(HttpServletRequest request) {
        CartItemDAO ciDAO = new CartItemDAO();
        ProductDAO pDAO = new ProductDAO();
        OrderDAO oDAO = new OrderDAO();
        CustomerDAO cusDAO = new CustomerDAO();

        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        Customer cus = cusDAO.getCustomer(username);

        if (cus == null) {
            System.out.println("unknow customer to checkout");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }

        int CustomerID = cus.getCustomerId();
        List<CartItem> cartItemList = ciDAO.getAllCartItemOfCustomer(CustomerID);

        if (cartItemList.isEmpty()) {
            System.out.println("The cart is empty to checkout");
            request.setAttribute("exceptionType", "OperationAddFailedException");
            return State.Fail.value;
        }

        List<Product> outOfStockToCheckoutProduct = ciDAO.getAllOutOfStockProductFromCart(CustomerID);
        if (!outOfStockToCheckoutProduct.isEmpty()) {
            for (int i = 0; i < outOfStockToCheckoutProduct.size(); i++) {
                System.out.println("Khong du so luong cua san pham:" + outOfStockToCheckoutProduct.get(i).getName());
            }
            request.setAttribute("exceptionType", "NotEnoughProductQuantityException");
            return State.Fail.value;
        }

        String newAddress = request.getParameter("txtNewAddress");
        String newPhone = request.getParameter("txtNewPhone");
        String Address = request.getParameter("txtAddress");
        String Phone = request.getParameter("txtPhone");

        if (newAddress != null && !newAddress.equals("")) {
            Address = newAddress;
        }
        if (newPhone != null && !newPhone.equals("")) {
            Phone = newPhone;
        }
        String receiverName = request.getParameter("txtReceiverName");
        if (receiverName == null || receiverName.equals("")) {
            System.out.println("Receiver name is null");
            request.setAttribute("exceptionType", "OperationAddFailedException");
            return State.Fail.value;
        }
        String Note = request.getParameter("txtNote");
        if (Note == null) {
            Note = "";
        }

        String voucherCode = request.getParameter("txtVoucher");
        // Co su dung voucher
        Voucher v = null;
        int voucherID = 0;
        if (voucherCode != null && !voucherCode.equals("")) {
            VoucherDAO vDAO = new VoucherDAO();
            v = vDAO.getVoucher(voucherCode);
            try {
                // Kiem tra tinh hop le va quang exception
                if (vDAO.checkValidVoucher(v, cus.getCustomerId())) {
                    List<Product> approveVoucherProduct = new ArrayList();
                    for (int i = 0; i < cartItemList.size(); i++) {
                        if (v.getApprovedProductId().contains(cartItemList.get(i).getProductId())) {
                            approveVoucherProduct.add(pDAO.getProduct(cartItemList.get(i).getProductId()));
                        }
                    }
                    if (approveVoucherProduct.isEmpty()) {
                        System.out.println("Khong co san pham nao ap dung duong voucher nay");
                        request.setAttribute("exceptionType", "OperationAddFailedException");
                        return State.Fail.value;
                    }
                }
                voucherID = v.getId();
            } catch (VoucherNotFoundException ex) {
                request.setAttribute("exceptionType", "VoucherNotFoundException");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                return State.Fail.value;

            } catch (InvalidVoucherException ex) {
                request.setAttribute("exceptionType", "InvalidVoucherException");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                return State.Fail.value;

            } catch (NotEnoughVoucherQuantityException ex) {
                request.setAttribute("exceptionType", "NotEnoughVoucherException");
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                return State.Fail.value;
            }
        }

        int Total = ciDAO.getCartTotal(cartItemList);
        int sumDeductPrice = 0;

        if (v != null) {
            System.out.println("cus id add voucher:" + CustomerID);
            Product p;
            for (int i = 0; i < cartItemList.size(); i++) {
                if (v.getApprovedProductId().contains(cartItemList.get(i).getProductId())) {
                    p = pDAO.getProduct(cartItemList.get(i).getProductId());
                    sumDeductPrice += p.getStock().getPrice() * v.getDiscountPercent() / 100;
                }
            }
            sumDeductPrice = (sumDeductPrice < v.getDiscountMax() ? sumDeductPrice : v.getDiscountMax());
            System.out.println("sumdeducttprice:" + sumDeductPrice);
        }

        String now = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        Date nowDate = Date.valueOf(now);

        boolean result = checkout(CustomerID, voucherID, receiverName, Address, Phone, Note, Total, sumDeductPrice,
                nowDate, cartItemList);

        if (result == false) {
            System.out.println("Khong thanh toan duoc");
            return State.Fail.value;
        } else {
            System.out.println("Thanh toan thanh cong");
            request.setAttribute("OrderID", oDAO.getOrderByOrderId(DatabaseUtils.getLastIndentityOf("[Order]")));
            request.setAttribute("CheckOutSuccess", "?CheckOutSuccess=true");
            ciDAO.deleteAllCartItemOfCustomer(CustomerID);
            return State.Success.value;
        }

    }

    public boolean checkout(int customerId, int voucherId, String orderReceiverName, String orderDeliveryAddress,
            String orderPhoneNumber, String orderNote, int orderTotal, int orderDeductPrice, Date orderCreateAt,
            List<CartItem> itemsCheckout) {
        Order od = new Order();
        od.setCustomerId(customerId);
        od.setReceiverName(orderReceiverName);
        od.setDeliveryAddress(orderDeliveryAddress);
        od.setPhoneNumber(orderPhoneNumber);
        od.setTotal(orderTotal);
        od.setStatus(IOrderDAO.status.PENDING.toString());
        od.setCreatedAt(orderCreateAt);
        // nullable
        od.setNote(orderNote);
        od.setVoucherId(voucherId);
        if (voucherId != 0) {
            od.setVoucherId(voucherId);
            od.setDeductedPrice(orderDeductPrice);
        }
        if (!orderNote.equals("")) {
            od.setNote(orderNote);
        }

        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (int i = 0; i < itemsCheckout.size(); i++) {
            OrderDetail item = new OrderDetail();
            item.setProductId(itemsCheckout.get(i).getProductId());
            item.setPrice(itemsCheckout.get(i).getPrice());
            item.setQuantity(itemsCheckout.get(i).getQuantity());
            item.setTotal(itemsCheckout.get(i).getSum());
            orderDetailList.add(item);
        }
        od.setOrderDetailList(orderDetailList);
        OrderDAO odDAO = new OrderDAO();
        boolean result = odDAO.addOrder(od);
        return result;
    }

    /**
     * Delete a delivery address
     *
     * @param request The request object
     * @return 1 if the operation is successful, 0 otherwise
     */
    private int deleteCustomerDeliveryAddress(HttpServletRequest request) {
        final String addressId = request.getParameter("txtAddressId");

        if (addressId == null || addressId.equals("")) {
            request.setAttribute("exceptionType", "NotEnoughInformationException");
            return State.Fail.value;
        }

        int id = -1;
        try {
            id = Integer.parseInt(addressId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        final String username = currentUserCookie.getValue();

        final CustomerDAO cDao = new CustomerDAO();
        final DeliveryAddressDAO daDao = new DeliveryAddressDAO();
        final DeliveryAddress da = daDao.getDeliveryAdress(id);

        System.out.println("Delete address with ID " + id);
        boolean result = daDao.deleteDeliveryAddress(id);

        if (!result) { // TODO: Show the error to the customer's screen
            System.out.println("Delete address failed");
            return State.Fail.value;
        }

        // If the default address is deleted, set another address to default
        if (da.getStatus().equals("Default")) {
            Customer c = cDao.getCustomer(username);
            List<DeliveryAddress> temp = daDao.getAll(c.getCustomerId());

            if (!temp.isEmpty()) {
                temp.get(0).setStatus("Default");
                daDao.updateDeliveryAddress(temp.get(0));
            }
        }

        System.out.println("Delete address successfully");
        return State.Success.value;
    }

    // ------------------------- EXEPTION HANDLING SECTION -------------------------
    private String checkException(HttpServletRequest request) {
        if (request.getAttribute("exceptionType") == null) {
            return "";
        }
        return null;

    }
}
