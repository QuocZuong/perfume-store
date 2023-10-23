package Controllers;

import DAOs.ImportDAO;
import DAOs.ImportStashItemDAO;
import DAOs.InventoryManagerDAO;
import DAOs.ProductDAO;
import Interfaces.DAOs.IImportDetailDAO;
import Lib.Converter;
import Lib.ExceptionUtils;
import Lib.Generator;
import Models.Import;
import Models.ImportDetail;
import Models.ImportStashItem;
import Models.InventoryManager;
import Models.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
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
    public static final String IVTR_MANAGER_IMPORT_CART_CHECKOUT_URI = "/InventoryManager/ImportCart/Checkout";

    public static final String BTN_CHECKOUT_IMPORT_CART = "btnCheckoutImportCart";
    public static final String SUBMIT_VALUE = "Submit";

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

        String path = request.getRequestURI();
        System.out.println("Request Path URI " + path);
        if (path.startsWith(IVTR_MANAGER_IMPORT_CART_CHECKOUT_URI)) {
            if (request.getParameter(BTN_CHECKOUT_IMPORT_CART) != null
                    && request.getParameter(BTN_CHECKOUT_IMPORT_CART).equals(SUBMIT_VALUE)) {
                System.out.println("going checkout cart");
                int result = checkoutImportCart(request);
                if (result == State.Success.value) {
                    System.out.println("checkout sussess. Going to /InventoryManager/ImportCart");
                    response.sendRedirect(IVTR_MANAGER_IMPORT_CART_URI);
                } else {
                    System.out.println("checkout import cart invalid. Going to /InventoryManager/ImportCart");
                    response.sendRedirect(IVTR_MANAGER_IMPORT_CART_URI + ExceptionUtils.generateExceptionQueryString(request));
                }
            }
        }

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
        /// /Client/Cart/Delete/ProductID/<%= p.getID()%>/ClientID/<%= ClientID %>
        ImportStashItemDAO ipsiDAO = new ImportStashItemDAO();

        int InventoryManagerID = -1;
        int ProductID = -1;
        String data[] = request.getRequestURI().split("/");

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("InventoryManagerID")) {
                InventoryManagerID = Integer.parseInt(data[i + 1]);
            } else if (data[i].equals("ProductID")) {
                ProductID = Integer.parseInt(data[i + 1]);
            }
        }
        ipsiDAO.deleteImportStashItem(InventoryManagerID, ProductID);
    }

    private int updateImportProduct(HttpServletRequest request, HttpServletResponse response) {
        // /Client/Cart/Update?ClientID=1&ProductID0=80&ProductQuan0=5&ProductID1=34&ProductQuan1=9&ListSize=2
        int result;
        ImportStashItemDAO ipsiDAO = new ImportStashItemDAO();
        int listSize = Integer.parseInt(request.getParameter("ListSize"));
        for (int i = 0; i < listSize; i++) {
            int InventoryManagerID = Integer.parseInt(request.getParameter("InventoryManagerID"));
            int ProductID = Integer.parseInt(request.getParameter("ProductID" + i));
            int ProductQuan = Integer.parseInt(request.getParameter("ProductQuan" + i));
            int ProductCost = Integer.parseInt(request.getParameter("ProductCost" + i));
            ImportStashItem ipsi = new ImportStashItem();
            ipsi.setInventoryManagerId(InventoryManagerID);
            ipsi.setProductId(ProductID);
            ipsi.setQuantity(ProductQuan);
            ipsi.setCost(ProductCost);
            ipsi.setSumCost(ProductQuan * ProductCost);
            result = ipsiDAO.updateImportStashItem(ipsi);
            if (result == 0) {
                return State.Fail.value;
            }
        }
        return State.Success.value;
    }

    private int getImportCart(HttpServletRequest request) {
        InventoryManagerDAO ivtrManaDAO = new InventoryManagerDAO();
        ImportStashItemDAO ipsiDAO = new ImportStashItemDAO();

        Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
        String username = userCookie.getValue();
        InventoryManager ivtrMana = ivtrManaDAO.getInventoryManager(username);

        if (ivtrMana == null) {
            System.out.println("Unknow Username to view inport cart");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }
        int ivtrManaId = ivtrMana.getInventoryManagerId();

        List<ImportStashItem> listImportItem = ipsiDAO.getAllImportStashItemOfManager(ivtrManaId);
        // Handling out of stock

        request.setAttribute("ivtrManaId", ivtrManaId);
        request.setAttribute("listImportItem", listImportItem);
        request.setAttribute("ivtrManaUsername", username);

        return State.Success.value;
    }

    private int checkoutImportCart(HttpServletRequest request) {
        InventoryManagerDAO ivtrManaDAO = new InventoryManagerDAO();
        ImportStashItemDAO ipsiDAO = new ImportStashItemDAO();
        ImportDAO ipDAO = new ImportDAO();

        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        InventoryManager ivtrMana = ivtrManaDAO.getInventoryManager(username);

        if (ivtrMana == null) {
            System.out.println("Unknow Username to checkout");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }
        int InventoryManagerID = ivtrMana.getInventoryManagerId();
        System.out.println("InventoryManagerID checkout import cart :" + InventoryManagerID);
        List<ImportStashItem> ipsiList = ipsiDAO.getAllImportStashItemOfManager(InventoryManagerID);

        if (ipsiList.isEmpty()) {
            System.out.println("The import cart is empty");
            request.setAttribute("exceptionType", "OperationAddFailedException");
            return State.Fail.value;
        }

        String supplierName = request.getParameter("txtSupplier");
        Long importAt = Converter.convertStringToEpochMilli(request.getParameter("txtImportAt"));
        Long deliveredAt = Converter.convertStringToEpochMilli(request.getParameter("txtDeliveredAt"));

        if (supplierName == null || importAt == null || deliveredAt == null) {
            System.out.println("lack of information");
            request.setAttribute("exceptionType", "OperationAddFailedException");
            return State.Fail.value;
        }

        Import ip = new Import();
        ip.setImportByInventoryManager(InventoryManagerID);
        ip.setImportAt(importAt);
        ip.setDeliveredAt(deliveredAt);
        ip.setSupplierName(supplierName);

        int totalCost = 0;
        int totalQuan = 0;

        List<ImportDetail> ipdList = new ArrayList<>();
        for (int i = 0; i < ipsiList.size(); i++) {
            totalCost += ipsiList.get(i).getSumCost();
            totalQuan += ipsiList.get(i).getQuantity();
            ImportDetail ipD = new ImportDetail();
            ipD.setProductId(ipsiList.get(i).getProductId());
            ipD.setCost(ipsiList.get(i).getCost());
            ipD.setQuantity(ipsiList.get(i).getQuantity());
            ipD.setStatus(IImportDetailDAO.Status.Wait.toString());
            ipdList.add(ipD);
        }

        ip.setTotalCost(totalCost);
        ip.setTotalQuantity(totalQuan);
        ip.setImportDetail(ipdList);

        int result = ipDAO.addImport(ip);
        if (result == 0) {
            System.out.println("add import fail");
            request.setAttribute("exceptionType", "OperationAddFailedException");
            return State.Fail.value;
        }
        System.out.println("import success so delete all import stash item of manager id :" + InventoryManagerID);
        ipsiDAO.deleteAllImportStashItemOfManager(InventoryManagerID);

        // check xem cac san pham trong kho co du de checkout khong
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
