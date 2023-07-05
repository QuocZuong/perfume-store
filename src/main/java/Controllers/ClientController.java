package Controllers;

import java.io.IOException;
import java.util.List;

import DAOs.CartDAO;
import DAOs.UserDAO;
import Models.Cart;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Acer
 */
public class ClientController extends HttpServlet {

    public static final String CLIENT_CART_URI = "/Client/Cart";
    public static final String CLIENT_CART_DELETE_URI = "/Client/Cart/Delete";
    public static final String CLIENT_CART_UPDATE_URI = "/Client/Cart/Update";
    public static final String CLIENT_ADD_TO_CART_URI = "/Client/addToCart";
    public static final String CLIENT_USER_URI = "/Client/User";
    public static final String CLIENT_UPDATE_INFO_URI = "/Client/Update/Info";
    public static final String CLIENT_UPDATE_ADDRESS_URI = "/Client/Update/Address";
    public static final String CLIENT_CHECKOUT_URI = "/Client/Checkout";
    public static final String BTN_ADD_TO_CART = "btnAddToCart";
    public static final String SUBMIT_VALUE = "Submit";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
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

        if (path.startsWith(CLIENT_CHECKOUT_URI)) {
            System.out.println("Going Checkout");
            ClientCheckout(request, response);
            return;
        }

        System.out.println("Going home");
        response.sendRedirect("/");
    }

    private void ClientCheckout(HttpServletRequest request, HttpServletResponse response) {
        UserDAO usDAO = new UserDAO();
        CartDAO cDAO = new CartDAO();
        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        int ClientID = usDAO.getUser(username).getID();
        
        // int kq = usDAO.checkout(ClientID, );

    }

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

    }

    private void GetCartProduct(HttpServletRequest request, HttpServletResponse response) {
        UserDAO uDao = new UserDAO();
        CartDAO cDAO = new CartDAO();

        Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        String username = userCookie.getValue();
        int ClientID = uDao.getUser(username).getID();

        List<Cart> listCart = cDAO.getAllClientProduct(ClientID);
        request.setAttribute("listCart", listCart);
    }

    /// /Client/Cart/Delete/ProductID/<%= p.getID()%>/ClientID/<%= ClientID %>
    private void deleteCartProduct(HttpServletRequest request, HttpServletResponse response) {
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

    // /Client/Cart/Update?ClientID=1&ProductID0=80&ProductQuan0=5&ProductID1=34&ProductQuan1=9&ListSize=2
    private void updateCartProduct(HttpServletRequest request, HttpServletResponse response) {
        CartDAO cDAO = new CartDAO();
        int listSize = Integer.parseInt(request.getParameter("ListSize"));
        for (int i = 0; i < listSize; i++) {
            int ClientID = Integer.parseInt(request.getParameter("ClientID"));
            int ProductID = Integer.parseInt(request.getParameter("ProductID" + i));
            int ProductQuan = Integer.parseInt(request.getParameter("ProductQuan" + i));
            cDAO.changeProductQuantity(ClientID, ProductID, ProductQuan);
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
            System.out.println("Going update address");
            updateClientAddress(request, response);
            request.getRequestDispatcher("/CLIENT_PAGE/user.jsp").forward(request, response);
            return;
        }
    }

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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
