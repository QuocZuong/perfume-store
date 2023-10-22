/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.OrderDAO;
import DAOs.OrderManagerDAO;
import Exceptions.OperationEditFailedException;
import Lib.ExceptionUtils;
import Models.Order;
import Models.OrderManager;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Acer
 */
public class OrderManagerController extends HttpServlet {

    private int searchProduct(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    enum Operation {
        ACCEPT,
        REJECT
    };

    public enum State {
        Success(1),
        Fail(0);

        private int value;

        State(int value) {
            this.value = value;
        }
    }

    public final String ORDER_MANAGER_ORDER_LIST = "/OrderManager/List";
    public final String ORDER_MANAGER_ACCEPT_ORDER_URI = "/OrderManager/ID/1/" + Operation.ACCEPT.toString();
    public final String ORDER_MANAGER_REJECT_ORDER_URI = "/OrderManager/ID/1/" + Operation.REJECT.toString();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

        if (path.startsWith(ORDER_MANAGER_ORDER_LIST)
                || path.startsWith(ORDER_MANAGER_ORDER_LIST + "/page")) {
            System.out.println("vao controller thanh cong");
            int result = searchProduct(request, response);
            if (result == State.Success.value) {
                request.getRequestDispatcher("/ORDER_MANAGER_ORDER_LIST/Order/list.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(ORDER_MANAGER_ORDER_LIST + ExceptionUtils.generateExceptionQueryString(request));
            }
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

    //The link will look like this. /OrderManager/ID/1/Accept
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
