package Controllers;

import DAOs.CartItemDAO;
import DAOs.CustomerDAO;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import DAOs.OrderDAO;
import DAOs.ProductDAO;
import DAOs.UserDAO;
import Exceptions.AccountDeactivatedException;
import Exceptions.EmailDuplicationException;
import Exceptions.NotEnoughInformationException;
import Exceptions.PhoneNumberDuplicationException;
import Exceptions.UsernameDuplicationException;
import Exceptions.WrongPasswordException;
import Lib.EmailSender;
import Models.CartItem;
import Models.Customer;
import Models.Order;
import Models.Product;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static final String CUSTOMER_CHECKOUT_URI = "/Customer/Checkout";
    public static final String CUSTOMER_ORDER_DETAIL_URI = "/Customer/Order/Detail/ID";

    public static final String BTN_ADD_TO_CART = "btnAddToCart";
    public static final String SUBMIT_VALUE = "Submit";

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
//
//        if (path.startsWith(CLIENT_CART_CHECKOUT_URI)) {
//            System.out.println("Going checkout");
//            boolean kq = handleCheckout(request, response);
//            if (kq) {
//                request.getRequestDispatcher("/CLIENT_PAGE/checkout.jsp").forward(request, response);
//            } else {
//                response.sendRedirect(CLIENT_CART_URI);
//            }
//            return;
//        }
//
        if (path.startsWith(CUSTOMER_CART_URI)) {
            System.out.println("Going cart");
            getCartProduct(request);
            request.getRequestDispatcher("/CUSTOMER_PAGE/cart.jsp").forward(request, response);
            return;
        }
//
//        if (path.startsWith(CLIENT_USER_URI)) {
//            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//            response.setHeader("Pragma", "no-cache");
//            response.setHeader("Expires", "0");
//            System.out.println("Going user");
//            request.getRequestDispatcher("/CLIENT_PAGE/user.jsp").forward(request, response);
//            return;
//        }
//
//        if (path.startsWith(CLIENT_ORDER_DETAIL_URI)) {
//            System.out.println("Going Order Detail");
//            ClientOrderDetail(request, response);
//            request.getRequestDispatcher("/CLIENT_PAGE/order_detail.jsp").forward(request, response);
//            return;
//        }
//
//        System.out.println("Going home");
//        response.sendRedirect("/");
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
        if (path.startsWith(CUSTOMER_ADD_TO_CART_URI)) {
            if (request.getParameter(BTN_ADD_TO_CART) != null
                    && request.getParameter(BTN_ADD_TO_CART).equals(SUBMIT_VALUE)) {
                int result = addToCart(request, response);
                if (result == State.Success.value) {

                } else if (result == State.Fail.value) {

                }
                int pID = Integer.parseInt(request.getParameter("ProductID"));
                response.sendRedirect("/Product/Detail/ID/" + pID);
                return;
            }
        }
//        if (path.startsWith(CLIENT_UPDATE_INFO_URI)) {
//            if (request.getParameter("btnUpdateInfo") != null
//                    && request.getParameter("btnUpdateInfo").equals("Submit")) {
//                System.out.println("Going update info");
//                if (updateClientInfomation(request, response)) {
//                    response.sendRedirect(CLIENT_USER_URI);
//                } else {
//                    response.sendRedirect(CLIENT_USER_URI + checkException(request));
//                }
//                return;
//            }
//        }
//        if (path.startsWith(CLIENT_UPDATE_ADDRESS_URI)) {
//            if (request.getParameter("btnUpdateAdress") != null
//                    && request.getParameter("btnUpdateAdress").equals("Submit")) {
//                System.out.println("Going update address");
//                if (updateClientAddress(request, response)) {
//                    response.sendRedirect(CLIENT_USER_URI);
//                } else {
//                    response.sendRedirect(CLIENT_USER_URI + checkException(request));
//                }
//                return;
//            }
//        }
//        if (path.startsWith(CLIENT_CHECKOUT_URI)) {
//            if (request.getParameter("btnSubmitCheckOut") != null
//                    && request.getParameter("btnSubmitCheckOut").equals("Submit")) {
//                System.out.println("Going Checkout");
//                if (ClientCheckout(request, response)) {
//                    int OrderID = (int) request.getAttribute("OrderID");
//                    String CheckOutSuccess = (String) request.getAttribute("CheckOutSuccess");
//                    response.sendRedirect(CLIENT_ORDER_DETAIL_URI + "/" + OrderID + CheckOutSuccess);
//                    return;
//                }
//                response.sendRedirect(CLIENT_CART_URI);
//                return;
//            }
//        }

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
            return 0;
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
            return 0;
        }
        return 1;
    }
//
//    private boolean ClientCheckout(HttpServletRequest request, HttpServletResponse response) {
//        UserDAO usDAO = new UserDAO();
//        CartDAO cDAO = new CartDAO();
//        ProductDAO pDAO = new ProductDAO();
//        OrderDAO oDAO = new OrderDAO();
//
//        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
//        String username = currentUserCookie.getValue();
//        int ClientID = usDAO.getUser(username).getID();
//        ArrayList<int[]> CartProductQuan = cDAO.getAllCartProductQuantity(ClientID);
//
//        if (CartProductQuan.size() == 0) {
//            System.out.println("The cart is empty");
//            return false;
//        }
//
//        for (int i = 0; i < CartProductQuan.size(); i++) {
//            int ProductID = CartProductQuan.get(i)[0];
//            int Quantity = CartProductQuan.get(i)[1];
//            int StoreQuan = pDAO.getProduct(ProductID).getQuantity();
//            if (StoreQuan < Quantity) {
//                System.out.println("Kho khong du so luong san pham co ID:" + ProductID);
//                return false;
//            }
//        }
//        String newAddress = request.getParameter("txtNewAddress");
//        String newPhone = request.getParameter("txtNewPhone");
//        String Address = request.getParameter("txtAddress");
//        String Phone = request.getParameter("txtPhone");
//        if (newAddress != null && !newAddress.equals("")) {
//            Address = newAddress;
//        }
//        if (newPhone != null && !newPhone.equals("")) {
//            Phone = newPhone;
//        }
//
//        String Note = request.getParameter("txtNote");
//        int Total = cDAO.getCartTotal(ClientID);
//        String now = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
//        Date nowDate = Date.valueOf(now);
//        int kq = usDAO.checkout(ClientID, nowDate, Address, Phone, Note, Total);
//        if (kq == 0) {
//            System.out.println("Khong thanh toan duoc");
//            return false;
//        } else {
//            System.out.println("Thanh toan thanh cong");
//            request.setAttribute("OrderID", oDAO.getMaxOrderID());
//            request.setAttribute("CheckOutSuccess", "?CheckOutSuccess=true");
//            return true;
//        }
//
//    }
// ---------------------------- READ SECTION ----------------------------

    private void getCartProduct(HttpServletRequest request) {
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

    }
//    
//    private void ClientOrderDetail(HttpServletRequest request, HttpServletResponse response) {
//        String path = request.getRequestURI();
//        String[] data = path.split("/");
//        int order_id = Integer.parseInt(data[data.length - 1]);
//        OrderDAO oDAO = new OrderDAO();
//        List<String[]> orderDetail = oDAO.getOrderDetailByOrderID(order_id);
//        Order OrderInfor = oDAO.getOrderByOrderId(order_id);
//        System.out.println(OrderInfor);
//        request.setAttribute("OrderInfor", OrderInfor);
//        request.setAttribute("OrderDetail", orderDetail);
//    }
//
//    // ---------------------------- UPDATE SECTION ----------------------------
//    private boolean updateClientInfomation(HttpServletRequest request, HttpServletResponse response) {
//        /*
//         * txtFullname
//         * txtUserName
//         * txtEmail
//         * pwdCurrent
//         * pwdNew
//         * pwdConfirmNew
//         * btnUpdateInfo (value = Submit)
//         */
//        
//        String fullname = request.getParameter("txtFullname");
//        
//        String username = request.getParameter("txtUserName");
//        String email = request.getParameter("txtEmail");
//        String currentPassword = request.getParameter("pwdCurrent");
//        String newPassword = "";
//        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
//        
//        UserDAO usDAO = new UserDAO();
//        User user = usDAO.getUser(currentUserCookie.getValue());
//        
//        boolean isChangedEmail = true;
//        boolean isChangedPassword = true;
//        boolean isChangedUsername = true;
//        // Username, email, phone number is unique
//        try {
//            if (!email.equals(user.getEmail())) {
//                if (usDAO.isExistEmail(email)) {
//                    throw new EmailDuplicationException();
//                }
//            } else {
//                isChangedEmail = false;
//            }
//            
//            if (!username.equals(user.getUsername())) {
//                if (usDAO.isExistUsername(username)) {
//                    throw new UsernameDuplicationException();
//                }
//                
//            } else {
//                isChangedUsername = false;
//            }
//            
//            if (currentPassword == null || currentPassword.equals("")) {
//                currentPassword = user.getPassword();
//                isChangedPassword = false;
//                // check if currentPassword is true
//            } else if (!usDAO.login(user.getUsername(), currentPassword)) {
//                throw new WrongPasswordException();
//            }
//
//            // Email and username duplication must come first
//        } catch (WrongPasswordException e) {
//            request.setAttribute("exceptionType", "WrongPasswordException");
//            return false;
//        } catch (AccountDeactivatedException e) {
//            request.setAttribute("exceptionType", "AccountDeactivatedException");
//            return false;
//        } catch (AccountNotFoundException e) {
//            request.setAttribute("exceptionType", "AccountNotFoundException");
//            return false;
//        } catch (EmailDuplicationException e) {
//            request.setAttribute("exceptionType", "EmailDuplicationException");
//            return false;
//        } catch (UsernameDuplicationException e) {
//            request.setAttribute("exceptionType", "UsernameDuplicationException");
//            return false;
//        }
//        if (isChangedPassword && request.getParameter("pwdNew") != null && !request.getParameter("pwdNew").equals("")) {
//            newPassword = request.getParameter("pwdNew");
//            newPassword = usDAO.getMD5hash(newPassword);
//        } else {
//            newPassword = user.getPassword();
//        }
//        
//        User updateUser = new User(
//                user.getID(),
//                fullname,
//                username,
//                newPassword,
//                email,
//                user.getPhoneNumber(),
//                user.getAddress(),
//                user.getRole());
//        
//        usDAO.updateUser(updateUser);
//
//        // Update cookie
//        Cookie c = ((Cookie) request.getSession().getAttribute("userCookie"));
//        c.setValue(username);
//        c.setPath("/");
//        response.addCookie(c);
//
//        // Sending mail
//        try {
//            EmailSender es = new EmailSender();
//            if (isChangedPassword) {
//                System.out.println("Detect password change");
//                System.out.println("sending mail changing password");
//                es.setEmailTo(email);
//                es.sendToEmail(es.CHANGE_PASSWORD_NOTFICATION, es.changePasswordNotifcation());
//            }
//            if (isChangedEmail) {
//                System.out.println("Detect email change");
//                System.out.println("sending mail changing email");
//                es.setEmailTo(user.getEmail());
//                es.sendToEmail(es.CHANGE_EMAIL_NOTFICATION, es.changeEmailNotification(email));
//            }
//            if (isChangedUsername) {
//                System.out.println("Detect username change");
//                System.out.println("sending mail changing username");
//                es.setEmailTo(email);
//                es.sendToEmail(es.CHANGE_USERNAME_NOTFICATION, es.changeUsernameNotification(username));
//            }
//            
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        
//        System.out.println("update account with ID " + user.getID() + " successfully");
//        return true;
//    }
//    
//    private boolean updateClientAddress(HttpServletRequest request, HttpServletResponse response) {
//        String phoneNumber = request.getParameter("txtPhoneNumber");
//        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
//        UserDAO usDAO = new UserDAO();
//        String username = currentUserCookie.getValue();
//        User UpdateClient = usDAO.getUser(username);
//        
//        String newAddress = request.getParameter("txtAddress");
//        try {
//            if (newAddress.split(" - ").length != 3) {
//                throw new NotEnoughInformationException();
//            }
//            
//            System.out.println("User name :" + username);
//            System.out.println("User's Phone :" + UpdateClient.getPhoneNumber());
//            System.out.println("User's Address :" + newAddress);
//            // Phone number is unique
//            if (!phoneNumber.equals(UpdateClient.getPhoneNumber())) {
//                if (usDAO.isExistPhone(phoneNumber)) {
//                    throw new PhoneNumberDuplicationException();
//                }
//            }
//        } catch (PhoneNumberDuplicationException e) {
//            request.setAttribute("exceptionType", "PhoneNumberDuplicationException");
//            return false;
//        } catch (NotEnoughInformationException e) {
//            request.setAttribute("exceptionType", "NotEnoughInformationException");
//            return false;
//        }
//        
//        UpdateClient.setAddress(newAddress);
//        UpdateClient.setPhoneNumber(phoneNumber);
//        
//        int kq = usDAO.updateUser(UpdateClient);
//        if (kq == 0) {
//            System.out.println("Update address and phone fail");
//            return false;
//        }
//        System.out.println("Update address and phone success");
//        return true;
//        
//    }
//    

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

//    // ---------------------------- DELETE SECTION ----------------------------
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

//        private boolean handleCheckout(HttpServletRequest request, HttpServletResponse response) {
//        UserDAO usDAO = new UserDAO();
//        CartDAO cDAO = new CartDAO();
//        ProductDAO pDAO = new ProductDAO();
//        
//        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
//        String username = currentUserCookie.getValue();
//        int ClientID = usDAO.getUser(username).getID();
//        ArrayList<int[]> CartProductQuan = cDAO.getAllCartProductQuantity(ClientID);
//        
//        if (CartProductQuan.size() == 0) {
//            System.out.println("The cart is empty");
//            return false;
//        }
//        for (int i = 0; i < CartProductQuan.size(); i++) {
//            int ProductID = CartProductQuan.get(i)[0];
//            int Quantity = CartProductQuan.get(i)[1];
//            int StoreQuan = pDAO.getProduct(ProductID).getQuantity();
//            if (StoreQuan < Quantity) {
//                System.out.println("Kho khong du so luong san pham co ID:" + ProductID);
//                return false;
//            }
//        }
//        return true;
//    }
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
                break;
            case "NotEnoughInformationException":
                exception += "NEInfo";
            default:
                break;
        }
        exception += "=true";
        return exception;
    }

}
