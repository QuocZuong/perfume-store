package Controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import DAOs.OrderDAO;
import DAOs.CartDAO;
import DAOs.ProductDAO;
import DAOs.UserDAO;
import Models.Cart;
import Models.Order;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClientController extends HttpServlet {

    // ---------------------------- URI DECLARATION SECTION ----------------------------
    public static final String CLIENT_CART_URI = "/Client/Cart";
    public static final String CLIENT_CART_CHECKOUT_URI = "/Client/Cart/Checkout";
    public static final String CLIENT_CART_DELETE_URI = "/Client/Cart/Delete";
    public static final String CLIENT_CART_UPDATE_URI = "/Client/Cart/Update";
    public static final String CLIENT_ADD_TO_CART_URI = "/Client/addToCart";
    public static final String CLIENT_USER_URI = "/Client/User";
    public static final String CLIENT_UPDATE_INFO_URI = "/Client/Update/Info";
    public static final String CLIENT_UPDATE_ADDRESS_URI = "/Client/Update/Address";
    public static final String CLIENT_CHECKOUT_URI = "/Client/Checkout";
    public static final String CLIENT_ORDER_DETAIL_URI = "/Client/Order/Detail/ID";
    public static final String BTN_ADD_TO_CART = "btnAddToCart";
    public static final String SUBMIT_VALUE = "Submit";

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

        if (path.startsWith(CLIENT_CART_DELETE_URI)) {
            System.out.println("Going delete");
            deleteCartProduct(request, response);
            response.sendRedirect(CLIENT_CART_URI);
            return;
        }

        if (path.startsWith(CLIENT_CART_UPDATE_URI)) {
            System.out.println("Going update");
            updateCartProduct(request, response);
            response.sendRedirect(CLIENT_CART_URI);
            return;
        }

        if (path.startsWith(CLIENT_CART_CHECKOUT_URI)) {
            System.out.println("Going checkout");
            request.getRequestDispatcher("/CLIENT_PAGE/checkout.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(CLIENT_CART_URI)) {
            System.out.println("Going cart");
            GetCartProduct(request, response);
            request.getRequestDispatcher("/CLIENT_PAGE/cart.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(CLIENT_USER_URI)) {
            System.out.println("Going user");
            request.getRequestDispatcher("/CLIENT_PAGE/user.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(CLIENT_ORDER_DETAIL_URI)) {
            System.out.println("Going Order Detail");
            ClientOrderDetail(request, response);
            request.getRequestDispatcher("/CLIENT_PAGE/order_detail.jsp").forward(request, response);
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
        if (path.startsWith(CLIENT_ADD_TO_CART_URI)) {
            if (request.getParameter(BTN_ADD_TO_CART) != null
                    && request.getParameter(BTN_ADD_TO_CART).equals(SUBMIT_VALUE)) {
                PostAddToCart(request, response);
                int pID = Integer.parseInt(request.getParameter("ProductID"));
                response.sendRedirect("/Product/Detail/ID/" + pID);
                return;
            }
        }

        if (path.startsWith(CLIENT_UPDATE_INFO_URI)) {
            if (request.getParameter("btnUpdateInfo") != null
                    && request.getParameter("btnUpdateInfo").equals("Submit")) {
                System.out.println("Going update info");
                updateClientInfomation(request, response);
                response.sendRedirect(CLIENT_USER_URI);
                return;
            }
        }
        if (path.startsWith(CLIENT_UPDATE_ADDRESS_URI)) {
            if (request.getParameter("btnUpdateAdress") != null
                    && request.getParameter("btnUpdateAdress").equals("Submit")) {
                System.out.println("Going update address");
                updateClientAddress(request, response);
                response.sendRedirect(CLIENT_USER_URI);
                return;
            }
        }

        if (path.startsWith(CLIENT_CHECKOUT_URI)) {
            if (request.getParameter("btnSubmitCheckOut") != null
                    && request.getParameter("btnSubmitCheckOut").equals("Submit")) {
                System.out.println("Going Checkout");
                ClientCheckout(request, response);
                response.sendRedirect(CLIENT_CART_URI);
                return;
            }
        }
    }

    /* CRUD */
    // ---------------------------- CREATE SECTION ----------------------------
    private void PostAddToCart(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDao = new UserDAO();

        Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        String username = userCookie.getValue();

        int pID = Integer.parseInt(request.getParameter("ProductID"));
        int pQuan = Integer.parseInt(request.getParameter("ProductQuantity"));

        // Assume having client Username and get Client ID from username
        int ClientID = uDao.getUser(username).getID();

        CartDAO cDAO = new CartDAO();
        cDAO.addToCart(ClientID, pID, pQuan);
    }

    private boolean ClientCheckout(HttpServletRequest request, HttpServletResponse response) {
        UserDAO usDAO = new UserDAO();
        CartDAO cDAO = new CartDAO();
        ProductDAO pDAO = new ProductDAO();

        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        int ClientID = usDAO.getUser(username).getID();
        ArrayList<int[]> CartProductQuan = cDAO.getAllCartProductQuantity(ClientID);

        for (int i = 0; i < CartProductQuan.size(); i++) {
            int ProductID = CartProductQuan.get(i)[0];
            int Quantity = CartProductQuan.get(i)[1];
            int StoreQuan = pDAO.getProduct(ProductID).getQuantity();
            if (StoreQuan < Quantity) {
                System.out.println("Kho khong du so luong san pham co ID:" + ProductID);
                return false;
            }
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
        String Note = request.getParameter("txtNote");
        int Total = cDAO.getCartTotal(ClientID);
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        Date nowDate = Date.valueOf(now);
        int kq = usDAO.checkout(ClientID, nowDate, Address, Phone, Note, Total);
        if (kq == 0) {
            System.out.println("Khong thanh toan duoc");
            return false;
        } else {
            System.out.println("Thanh toan thanh cong");
            return true;
        }

    }

    // ---------------------------- READ SECTION ----------------------------
    private void GetCartProduct(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDao = new UserDAO();
        CartDAO cDAO = new CartDAO();

        Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        String username = userCookie.getValue();
        int ClientID = uDao.getUser(username).getID();

        List<Cart> listCart = cDAO.getAllClientProduct(ClientID);
        request.setAttribute("listCart", listCart);
    }

    private void ClientOrderDetail(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();
        String[] data = path.split("/");
        int order_id = Integer.parseInt(data[data.length - 1]);
        OrderDAO oDAO = new OrderDAO();
        List<String[]> orderDetail = oDAO.getOrderDetailByOrderID(order_id);
        Order OrderInfor = oDAO.getOrderByOrderId(order_id);
        System.out.println(OrderInfor);
        request.setAttribute("OrderInfor", OrderInfor);
        request.setAttribute("OrderDetail", orderDetail);
    }

    // ---------------------------- UPDATE SECTION ----------------------------
    private void updateClientInfomation(HttpServletRequest request, HttpServletResponse response) {
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

        // Username, email, phone number is unique
        if (currentPassword != null) {
            currentPassword = user.getPassword();
        } else if (!usDAO.login(user.getUsername(), currentPassword)) {
            System.out.println("Login failed. Update Failed");
            return;
        }

        if (!email.equals(user.getEmail())) {
            if (usDAO.isExistEmail(email)) {
                System.out.println("Email is existed. Update Failed");
                return;
            }
        }
        if (!username.equals(user.getUsername())) {
            if (usDAO.isExistUsername(username)) {
                System.out.println("Username is existed. Update Failed");
                return;
            }
        }

        if (request.getParameter("pwdNew") != null && !request.getParameter("pwdNew").equals("")) {
            newPassword = usDAO.getMD5hash(request.getParameter("pwdNew"));
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

    }

    private void updateClientAddress(HttpServletRequest request, HttpServletResponse response) {
        String phoneNumber = request.getParameter("txtPhoneNumber");
        String newAddress = request.getParameter("txtAddress");
        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        UserDAO usDAO = new UserDAO();
        String username = currentUserCookie.getValue();
        User UpdateClient = usDAO.getUser(username);
        System.out.println("User name :" + username);
        System.out.println("User name :" + UpdateClient.getPhoneNumber());
        if (!phoneNumber.equals(UpdateClient.getPhoneNumber())) {
            if (usDAO.isExistPhone(phoneNumber)) {
                System.out.println("Phone Number is existed. Update Failed");
                return;
            }
        }

        UpdateClient.setAddress(newAddress);
        UpdateClient.setPhoneNumber(phoneNumber);

        int kq = usDAO.updateUser(UpdateClient);
        if (kq == 0) {
            System.out.println("Update address and phone fail");
        } else {
            System.out.println("Update address and phone success");
        }

    }

    private void updateCartProduct(HttpServletRequest request, HttpServletResponse response) {
        // /Client/Cart/Update?ClientID=1&ProductID0=80&ProductQuan0=5&ProductID1=34&ProductQuan1=9&ListSize=2
        CartDAO cDAO = new CartDAO();
        int listSize = Integer.parseInt(request.getParameter("ListSize"));
        for (int i = 0; i < listSize; i++) {
            int ClientID = Integer.parseInt(request.getParameter("ClientID"));
            int ProductID = Integer.parseInt(request.getParameter("ProductID" + i));
            int ProductQuan = Integer.parseInt(request.getParameter("ProductQuan" + i));
            cDAO.changeProductQuantity(ClientID, ProductID, ProductQuan);
        }
    }

    // ---------------------------- DELETE SECTION ----------------------------
    private void deleteCartProduct(HttpServletRequest request, HttpServletResponse response) {
        /// /Client/Cart/Delete/ProductID/<%= p.getID()%>/ClientID/<%= ClientID %>
        CartDAO cDAO = new CartDAO();

        int ClientID = -1;
        int ProductID = -1;
        String data[] = request.getRequestURI().split("/");

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ClientID")) {
                ClientID = Integer.parseInt(data[i + 1]);
            } else if (data[i].equals("ProductID")) {
                ProductID = Integer.parseInt(data[i + 1]);
            }
        }
        cDAO.deleteCart(ClientID, ProductID);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
