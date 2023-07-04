package Controllers;

import java.io.IOException;
import java.util.List;

import DAOs.CartDAO;
import DAOs.UserDAO;
import Models.Cart;
import Models.User;
import jakarta.servlet.ServletException;
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
        System.out.println("Going home");
        response.sendRedirect("/");
    }

    private void GetCartProduct(HttpServletRequest request, HttpServletResponse response) {
        // Assume having id
        int ClientID = 1;
        CartDAO cDAO = new CartDAO();
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
        System.out.println("listSize is " + listSize);
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
    }

    private void PostAddToCart(HttpServletRequest request, HttpServletResponse response) {
        int pID = Integer.parseInt(request.getParameter("ProductID"));
        int pQuan = Integer.parseInt(request.getParameter("ProductQuantity"));

        // Assume having client Username and get Client ID from username
        int ClientID = 1;

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
