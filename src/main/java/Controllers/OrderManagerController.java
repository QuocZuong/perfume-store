package Controllers;

import DAOs.OrderDAO;
import DAOs.OrderManagerDAO;
import DAOs.ProductDAO;
import DAOs.VoucherDAO;
import Exceptions.OperationEditFailedException;
import Lib.ExceptionUtils;
import Lib.Generator;
import Models.Order;
import Models.OrderDetail;
import Models.OrderManager;
import Models.Product;
import Models.Voucher;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class OrderManagerController extends HttpServlet {

    private int searchProduct(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    enum Operation {
        ACCEPT,
        REJECT
    };

    enum State {
        Success(1),
        Fail(0);

        public int value;

        State(int value) {
            this.value = value;
        }
    }

    public static final String ORDER_MANAGER_USER_URI = "/OrderManager";

    public static final String ORDER_MANAGER_ORDER_LIST_URI = "/OrderManager/Order/List";
    public static final String ORDER_MANAGER_ORDER_DETAIL_URI = "/OrderManager/Order/Detail";

    public static final String ORDER_MANAGER_UPDATE_INFO_URI = "/OrderManager/Update/Info";

    public final String ORDER_MANAGER_ORDER_LIST = "/OrderManager/List";
    public final String ORDER_MANAGER_ACCEPT_ORDER_URI = "/OrderManager/ID/" + Operation.ACCEPT.toString();
    public final String ORDER_MANAGER_REJECT_ORDER_URI = "/OrderManager/ID/" + Operation.REJECT.toString();

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

        // ---------------------------- ORDER SECTION ----------------------------
        if (path.startsWith(ORDER_MANAGER_ORDER_LIST_URI)
                || path.startsWith(ORDER_MANAGER_ORDER_LIST_URI + "/page")) {
            System.out.println("Going Order List");
            int result = searchOrder(request);

            if (result == State.Success.value) {
                request.getRequestDispatcher("/ORDER_MANAGER/Order/list.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(
                        ORDER_MANAGER_ORDER_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }

            return;
        }

        if (path.startsWith(ORDER_MANAGER_ORDER_DETAIL_URI)) {
            System.out.println("Going Order Detail");

            int result = getCustomerOrderDetail(request);

            if (result == State.Success.value) {
                request.getRequestDispatcher("/ORDER_MANAGER/Order/orderDetail.jsp").forward(request,
                        response);
            } else {
                request.getRequestDispatcher(
                        "/ORDER_MANAGER/Order/orderDetail.jsp" + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(ORDER_MANAGER_ACCEPT_ORDER_URI)
                || path.startsWith(ORDER_MANAGER_REJECT_ORDER_URI)) {
            int result = updateOrderStatus(request, response);

            if (result == State.Success.value) {
                response.sendRedirect(ORDER_MANAGER_ORDER_LIST);
            } else {
                response.sendRedirect(ORDER_MANAGER_ORDER_LIST + ExceptionUtils.generateExceptionQueryString(request));
            }

            return;
        }

        // ---------------------------- DEFAULT SECTION ----------------------------
        if (path.startsWith(ORDER_MANAGER_USER_URI)) { // Put this at the last
            request.getRequestDispatcher("/ORDER_MANAGER/admin.jsp").forward(request, response);
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
        String path = request.getRequestURI();

        if (path.startsWith(ORDER_MANAGER_ACCEPT_ORDER_URI)
                || path.startsWith(ORDER_MANAGER_REJECT_ORDER_URI)) {
            int result = updateOrderStatus(request, response);
            if (result == State.Success.value) {
                response.sendRedirect(ORDER_MANAGER_ORDER_LIST);
            } else {
                response.sendRedirect(ORDER_MANAGER_ORDER_LIST + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }
    }

    // The link will look like this. /OrderManager/ID/1/Accept
    public int updateOrderStatus(HttpServletRequest request, HttpServletResponse response) {
        String URI = request.getRequestURI();
        String parameters[] = URI.split("/");
        int orderId = 0;
        Operation op = null;
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].equals("ID")) {
                orderId = Integer.parseInt(parameters[i + 1]);
                i++;
            } else if (parameters[i].equals(Operation.ACCEPT.toString())) {
                op = Operation.ACCEPT;
            } else if (parameters[i].equals(Operation.REJECT.toString())) {
                op = Operation.REJECT;
            }
        }
        if (op == null) {
            System.out.println("Invalid operation, aborting");
            return State.Fail.value;
        }
        if (orderId <= 0) {
            System.out.println("Invalid order id, aborting operation " + op.toString());
            return State.Fail.value;
        }
        Cookie cookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = cookie.getValue();
        OrderManagerDAO omDAO = new OrderManagerDAO();
        OrderManager orderManager = omDAO.getOrderManager(username);
        if (orderManager == null) {
            System.out.println("Invalid username, aborting update order status");
            return State.Fail.value;
        }

        try {
            OrderDAO orDAO = new OrderDAO();
            Order order = orDAO.getOrderByOrderId(orderId);
            if (op == Operation.ACCEPT) {
                orDAO.acceptOrder(order, orderManager.getOrderManagerId());
                return State.Success.value;
            }

            if (op == Operation.REJECT) {
                orDAO.rejectOrder(order, orderManager.getOrderManagerId());
                return State.Success.value;
            }
        } catch (OperationEditFailedException ex) {
            request.setAttribute("exceptionType", "OperationEditFailedException");
            return State.Fail.value;
        }
        return State.Fail.value;
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
