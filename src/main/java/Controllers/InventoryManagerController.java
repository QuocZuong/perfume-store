/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.EmployeeDAO;
import DAOs.ImportStashItemDAO;
import DAOs.InventoryManagerDAO;
import DAOs.ProductDAO;
import Lib.ExceptionUtils;
import Lib.Generator;
import Models.Employee;
import Models.ImportStashItem;
import Models.InventoryManager;
import Models.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
public class InventoryManagerController extends HttpServlet {

    public static final String IVTR_MANAGER_USER_URI = "/InventoryManager";
    public static final String IVTR_MANAGER_UPDATE_INFO_URI = "/InventoryManager/Update/Info";

    public static final String IVTR_MANAGER_PRODUCT_LIST_URI = "/InventoryManager/Product/List";
    public static final String IVTR_MANAGER_PRODUCT_IMPORT_URI = "/InventoryManager/ProductImport";
    public static final String IVTR_MANAGER_IMPORT_CART_URI = "/InventoryManager/ImportCart";
    public static final String IVTR_MANAGER_IMPORT_CART_DELETE_URI = "/InventoryManager/ImportCart/Delete";
    public static final String IVTR_MANAGER_IMPORT_CART_UPDATE_URI = "/InventoryManager/ImportCart/Update";

    public enum State {
        Success(1),
        Fail(0);

        private int value;

        State(int value) {
            this.value = value;
        }
    }

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
        System.out.println("Request Path URI " + path);

        if (path.startsWith(IVTR_MANAGER_PRODUCT_LIST_URI)
                || path.startsWith(IVTR_MANAGER_PRODUCT_LIST_URI + "/page")) {
            System.out.println("vao controller thanh cong");
            int result = searchProduct(request, response);
            if (result == State.Success.value) {
                request.getRequestDispatcher("/INVENTORY_MANAGER_PAGE/Product/list.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(IVTR_MANAGER_PRODUCT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }
        //
        if (path.startsWith(IVTR_MANAGER_PRODUCT_IMPORT_URI)) {
            System.out.println("Going inport product to inport cart");
            int result = importProduct(request, response);
            if (result == State.Success.value) {
                response.sendRedirect(IVTR_MANAGER_PRODUCT_LIST_URI);
            } else if (result == State.Fail.value) {
                response.sendRedirect(IVTR_MANAGER_PRODUCT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }
        //
        if (path.startsWith(IVTR_MANAGER_IMPORT_CART_DELETE_URI)) {
            System.out.println("Going delete");
            deleteImportProduct(request, response);
            response.sendRedirect(IVTR_MANAGER_IMPORT_CART_URI);
            return;
        }
        //
        if (path.startsWith(IVTR_MANAGER_IMPORT_CART_UPDATE_URI)) {
            System.out.println("Going update");
            updateImportProduct(request, response);
            response.sendRedirect(IVTR_MANAGER_IMPORT_CART_URI);
            return;
        }

        if (path.startsWith(IVTR_MANAGER_IMPORT_CART_URI)) {
            System.out.println("Going cart");
            int result = getImportCart(request);
            if (result == State.Success.value) {
                request.getRequestDispatcher("/INVENTORY_MANAGER_PAGE/cart.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(IVTR_MANAGER_IMPORT_CART_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        if (path.startsWith(IVTR_MANAGER_USER_URI)) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            System.out.println("Going user");
            request.getRequestDispatcher("/INVENTORY_MANAGER_PAGE/user.jsp").forward(request, response);
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
        List<Product> productList = pDAO.searchProduct(search);
        if (productList.isEmpty()) {
            request.setAttribute("exceptionType", "ProductNotFoundException");
            return InventoryManagerController.State.Fail.value;
        }
        int numberOfPage = (productList.size() / rows) + (productList.size() % rows == 0 ? 0 : 1);
        productList = Generator.pagingList(productList, page, rows);

        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", numberOfPage);
        request.setAttribute("productList", productList);
        request.setAttribute("search", search);

        return InventoryManagerController.State.Success.value;
    }

    private int importProduct(HttpServletRequest request, HttpServletResponse response) {
        int result = 0;
        InventoryManagerDAO ivtrManaDAO = new InventoryManagerDAO();
        ImportStashItemDAO ipsiDAO = new ImportStashItemDAO();

        String path = request.getRequestURI();
        String data[] = path.split("/");
        ProductDAO pDAO = new ProductDAO();
        String pId = null;
        String quan = null;
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ProductID")) {
                pId = data[i + 1];
            }
            if (data[i].equals("Quantity")) {
                quan = data[i + 1];
            }
        }

        if (pId == null || quan == null) {
            System.out.println("Import Failed, Lacking ProductID or Quantity");
            request.setAttribute("exceptionType", ExceptionUtils.ExceptionType.ProductNotFoundException.toString());
            return State.Fail.value;
        }

        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        InventoryManager ivtrManager = ivtrManaDAO.getInventoryManager(username);

        if (ivtrManager == null) {
            System.out.println("Unknow Username to import product");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }

        try {
            int ivtrMagaId = ivtrManager.getInventoryManagerId();
            System.out.println("inventory id:" + ivtrMagaId);
            int ProductID = Integer.parseInt(pId);
            int Quantity = Integer.parseInt(quan);
            int sumCost;
            ImportStashItem ipsi = ipsiDAO.getImportStashItem(ProductID, ProductID);
            if (ipsi == null) {
                ipsi = new ImportStashItem();
                ipsi.setInventoryManagerId(ivtrMagaId);
                ipsi.setProductId(ProductID);
                ipsi.setQuantity(Quantity);
                ipsi.setCost(0);
                sumCost = ipsi.getCost() * Quantity;
                ipsi.setSumCost(sumCost);
                result = ipsiDAO.addImportStashItem(ipsi);
            } else {
                ipsi.setQuantity(ipsi.getQuantity() + Quantity);
                ipsi.setSumCost(ipsi.getSumCost() + Quantity * ipsi.getCost());
                result = ipsiDAO.updateImportStashItem(ipsi);
            }

            if (result == 0) {
                request.setAttribute("exceptionType", "OperationAddFailedException");
                return State.Fail.value;
            }

        } catch (Exception e) {
            request.setAttribute("exceptionType", "OperationAddFailedException");
            return State.Fail.value;
        }
        return State.Success.value;
    }

    private void deleteImportProduct(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void updateImportProduct(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private int getImportCart(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
