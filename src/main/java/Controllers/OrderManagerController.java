package Controllers;

import DAOs.OrderDAO;
import DAOs.OrderManagerDAO;
import DAOs.ProductDAO;
import DAOs.StockDAO;
import DAOs.UserDAO;
import DAOs.VoucherDAO;
import Exceptions.AccountDeactivatedException;
import Exceptions.EmailDuplicationException;
import Exceptions.InvalidInputException;
import Exceptions.NotEnoughProductQuantityException;
import Exceptions.NotEnoughVoucherQuantityException;
import Exceptions.OperationAddFailedException;
import Exceptions.OperationEditFailedException;
import Exceptions.ProductNotFoundException;
import Exceptions.UsernameDuplicationException;
import Exceptions.VoucherCodeDuplication;
import Exceptions.WrongPasswordException;
import Interfaces.DAOs.IUserDAO.loginType;
import Lib.ExceptionUtils;
import Lib.Generator;
import Lib.Converter;
import Lib.EmailSender;
import Models.Order;
import Models.OrderDetail;
import Models.OrderManager;
import Models.Product;
import Models.Stock;
import Models.User;
import Models.Voucher;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class OrderManagerController extends HttpServlet {

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

    enum SearchType {
        PENDING,
        HISTORY
    };

    public static final String ORDER_MANAGER_USER_URI = "/OrderManager";

    public static final String ORDER_MANAGER_ORDER_LIST_URI = "/OrderManager/Order/List";
    public static final String ORDER_MANAGER_ORDER_LIST_PENDING_URI = "/OrderManager/Order/List/Pending";
    public static final String ORDER_MANAGER_ORDER_LIST_HISTORY_WORK_URI = "/OrderManager/Order/List/HistoryWork";

    public static final String ORDER_MANAGER_ORDER_DETAIL_URI = "/OrderManager/Order/Detail/ID";

    public static final String ORDER_MANAGER_UPDATE_INFO_URI = "/OrderManager/Update/Info";

    public final String ORDER_MANAGER_ORDER_LIST_ACCEPT_ORDER_URI = "/OrderManager/OrderList/"
            + Operation.ACCEPT.toString() + "/Order/ID/";
    public final String ORDER_MANAGER_ORDER_LIST_REJECT_ORDER_URI = "/OrderManager/OrderList/"
            + Operation.REJECT.toString() + "/Order/ID/";

    public final String ORDER_MANAGER_ORDER_LIST_PENDING_ACCEPT_ORDER_URI = "/OrderManager/OrderList/Pending/"
            + Operation.ACCEPT.toString() + "/Order/ID/";
    public final String ORDER_MANAGER_ORDER_LIST_PENDING_REJECT_ORDER_URI = "/OrderManager/OrderList/Pending/"
            + Operation.REJECT.toString() + "/Order/ID/";

    public final String ORDER_MANAGER_ORDER_LIST_HISTORY_WORK_ACCEPT_ORDER_URI = "/OrderManager/OrderList/HistoryWork/"
            + Operation.ACCEPT.toString() + "/Order/ID/";
    public final String ORDER_MANAGER_ORDER_LIST_HISTORY_WORK_REJECT_ORDER_URI = "/OrderManager/OrderList/HistoryWork/"
            + Operation.REJECT.toString() + "/Order/ID/";

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

        // ---------------------------- ORDER SECTION ----------------------------
        if (path.startsWith(ORDER_MANAGER_ORDER_LIST_PENDING_URI)
                || path.startsWith(ORDER_MANAGER_ORDER_LIST_PENDING_URI + "/page")) {
            System.out.println("Going Order List: Pending");
            int result = searchOrder(request, SearchType.PENDING);

            if (result == State.Success.value) {
                request.getRequestDispatcher("/ORDER_MANAGER/Order/pendingList.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(
                        ORDER_MANAGER_ORDER_LIST_PENDING_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(ORDER_MANAGER_ORDER_LIST_HISTORY_WORK_URI)
                || path.startsWith(ORDER_MANAGER_ORDER_LIST_HISTORY_WORK_URI + "/page")) {
            System.out.println("Going Order List: History Work");
            int result = searchOrder(request, SearchType.HISTORY);

            if (result == State.Success.value) {
                request.getRequestDispatcher("/ORDER_MANAGER/Order/workingHistoryList.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(
                        ORDER_MANAGER_ORDER_LIST_HISTORY_WORK_URI
                        + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(ORDER_MANAGER_ORDER_LIST_URI)
                || path.startsWith(ORDER_MANAGER_ORDER_LIST_URI + "/page")) {
            System.out.println("Going Order List");
            int result = searchOrder(request, null);

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

        // Accept/Rejecting order then redirect to order list
        if (path.startsWith(ORDER_MANAGER_ORDER_LIST_ACCEPT_ORDER_URI)
                || path.startsWith(ORDER_MANAGER_ORDER_LIST_REJECT_ORDER_URI)) {
            System.out.println("Update order status");

            int result = updateOrderStatus(request, response);

            if (result == State.Success.value) {
                response.sendRedirect(ORDER_MANAGER_ORDER_LIST_URI);
            } else {
                response.sendRedirect(
                        ORDER_MANAGER_ORDER_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        // Accept/Rejecting order then redirect to order list: pending
        if (path.startsWith(ORDER_MANAGER_ORDER_LIST_PENDING_ACCEPT_ORDER_URI)
                || path.startsWith(ORDER_MANAGER_ORDER_LIST_PENDING_REJECT_ORDER_URI)) {
            System.out.println("Update order status");

            int result = updateOrderStatus(request, response);

            if (result == State.Success.value) {
                response.sendRedirect(ORDER_MANAGER_ORDER_LIST_PENDING_URI);
            } else {
                response.sendRedirect(
                        ORDER_MANAGER_ORDER_LIST_PENDING_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        // Accept/Rejecting order then redirect to order list: history work
        if (path.startsWith(ORDER_MANAGER_ORDER_LIST_HISTORY_WORK_ACCEPT_ORDER_URI)
                || path.startsWith(ORDER_MANAGER_ORDER_LIST_HISTORY_WORK_REJECT_ORDER_URI)) {
            System.out.println("Update order status");

            int result = updateOrderStatus(request, response);

            if (result == State.Success.value) {
                response.sendRedirect(ORDER_MANAGER_ORDER_LIST_HISTORY_WORK_URI);
            } else {
                response.sendRedirect(
                        ORDER_MANAGER_ORDER_LIST_HISTORY_WORK_URI
                        + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        // ---------------------------- DEFAULT SECTION ----------------------------
        if (path.startsWith(ORDER_MANAGER_USER_URI)) { // Put this at the last
            System.out.println("Going default");
            request.getRequestDispatcher("/ORDER_MANAGER/admin.jsp").forward(request, response);
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

        if (path.startsWith(ORDER_MANAGER_UPDATE_INFO_URI)) {
            int result = updateAdminInfomation(request, response);

            if (result == State.Success.value) {
                response.sendRedirect(ORDER_MANAGER_USER_URI);
            } else {
                response.sendRedirect(ORDER_MANAGER_USER_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
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
            System.out.println("\n" + orderManager.getOrderManagerId() + " | " + orderManager.getName() + "\n");
            if (op == Operation.ACCEPT) {
                orDAO.acceptOrder(order, orderManager.getOrderManagerId());
                return State.Success.value;
            }

            if (op == Operation.REJECT) {
                // Check quantity for voucher
                VoucherDAO voucherDAO = new VoucherDAO();
                Voucher voucher = voucherDAO.getVoucher(order.getVoucherId());
                if (voucher != null) {
                    // Proceed to add voucher quantity
                    voucher.setQuantity(voucher.getQuantity() + 1);
                    voucherDAO.updateVoucher(voucher);
                }

                List<OrderDetail> orderDetailList = order.getOrderDetailList();
                StockDAO stkDAO = new StockDAO();
                // Check if the quantity stock is less than the order detail
                for (OrderDetail orderDetail : orderDetailList) {
                    Stock stk = stkDAO.getStock(orderDetail.getProductId());
                    // Proceed to add the product
                    stk.setQuantity(stk.getQuantity() + orderDetail.getQuantity());
                    int result = stkDAO.updateStock(stk);
                    if (result < 1) {
                        System.out.println("Update stock fail!");
                        throw new OperationEditFailedException();
                    }
                }
                orDAO.rejectOrder(order, orderManager.getOrderManagerId());
                return State.Success.value;
            }

        } catch (OperationEditFailedException ex) {
            request.setAttribute("exceptionType", "OperationEditFailedException");
            return State.Fail.value;
        } catch (OperationAddFailedException ex) {
            request.setAttribute("exceptionType", "OperationAddFailedException");
            return State.Fail.value;
        } catch (VoucherCodeDuplication ex) {
            request.setAttribute("exceptionType", "VoucherCodeDuplication");
            return State.Fail.value;
        }
        return State.Fail.value;
    }

    private int searchOrder(HttpServletRequest request, SearchType type) {
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

        // Filter out the list
        if (type != null) {
            if (type == SearchType.PENDING) {
                orderList = orderList
                        .stream()
                        .filter(order -> order.getStatus().equals("PENDING"))
                        .collect(Collectors.toList());
            } else if (type == SearchType.HISTORY) {
                OrderManagerDAO omDAO = new OrderManagerDAO();
                Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
                OrderManager currentManager = omDAO.getOrderManager(currentUserCookie.getValue());
                orderList = orderList
                        .stream()
                        .filter(order -> (order.getUpdateByOrderManager() != 0
                        && order.getUpdateByOrderManager() == currentManager.getOrderManagerId()))
                        .collect(Collectors.toList());
            }
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
            request.setAttribute("exceptionType", "NumberFormatException");
            return State.Fail.value;
        } catch (ProductNotFoundException ex) {
            request.setAttribute("exceptionType", "ProductNotFoundException");
            return State.Fail.value;
        }
    }

    private int updateAdminInfomation(HttpServletRequest request,
            HttpServletResponse response) {

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
            } else {
                isChangedUsername = false;
            }

            if (currentPassword == null || currentPassword.equals("")) {
                currentPassword = user.getPassword();
                isChangedPassword = false;
                // check if currentPassword is true
            } else if (usDAO.login(user.getUsername(), currentPassword, loginType.Username) == null) {
                throw new WrongPasswordException();
            }

        } catch (AccountDeactivatedException e) {
            request.setAttribute("exceptionType", "AccountDeactivatedException");
            return State.Fail.value;
        } catch (InvalidInputException iie) {
            request.setAttribute("exceptionType", "InvalidInputException");
            return State.Fail.value;
        } catch (UsernameDuplicationException e) {
            request.setAttribute("exceptionType", "UsernameDuplicationException");
            return State.Fail.value;
        } catch (EmailDuplicationException e) {
            request.setAttribute("exceptionType", "EmailDuplicationException");
            return State.Fail.value;
        } catch (WrongPasswordException e) {
            request.setAttribute("exceptionType", "WrongPasswordException");
            return State.Fail.value;
        }

        if (isChangedPassword && request.getParameter("pwdNew") != null
                && !request.getParameter("pwdNew").equals("")) {
            newPassword = request.getParameter("pwdNew");
            newPassword = Converter.convertToMD5Hash(newPassword);
        } else {
            newPassword = user.getPassword();
        }

        User updateUser = new User(user);

        // Applying new information
        updateUser.setName(fullname);
        updateUser.setUsername(username);
        updateUser.setPassword(newPassword);
        updateUser.setEmail(email);

        int result = usDAO.updateUser(updateUser);

        if (result != 1) {
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.OperationEditFailedException.toString());
            return State.Fail.value;
        }
        // Update cookie
        Cookie c = ((Cookie) request.getSession().getAttribute("userCookie"));
        c.setValue(username);
        c.setPath("/");
        response.addCookie(c);

        // Sending mail
        boolean sendMailToggler = true;

        if (sendMailToggler) {
            try {
                EmailSender es = new EmailSender();

                if (isChangedPassword) {
                    System.out.println("Detect password change");
                    System.out.println("sending mail changing password");
                    es.setEmailTo(email);
                    es.sendEmailByThread(es.CHANGE_PASSWORD_NOTFICATION,
                            es.changePasswordNotifcation(updateUser));
                }

                if (isChangedEmail) {
                    System.out.println("Detect email change");
                    System.out.println("sending mail changing email");
                    es.setEmailTo(user.getEmail());
                    es.sendEmailByThread(es.CHANGE_EMAIL_NOTFICATION,
                            es.changeEmailNotification(email));
                }

                if (isChangedUsername) {
                    System.out.println("Detect username change");
                    System.out.println("sending mail changing username");
                    es.setEmailTo(email);
                    es.sendEmailByThread(es.CHANGE_USERNAME_NOTFICATION,
                            es.changeUsernameNotification(username));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (result < 1) {
            System.out.println("update account with ID " + user.getId() + " failed");
            return State.Fail.value;
        }

        System.out.println("update account with ID " + user.getId() + " successfully");
        return State.Success.value;
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
