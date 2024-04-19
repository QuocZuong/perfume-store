package Controllers;

import DAOs.ImportDAO;
import DAOs.ImportStashItemDAO;
import DAOs.InventoryManagerDAO;
import DAOs.ProductDAO;
import DAOs.UserDAO;
import Exceptions.AccountDeactivatedException;
import Exceptions.EmailDuplicationException;
import Exceptions.InvalidInputException;
import Exceptions.ProductNotFoundException;
import Exceptions.UsernameDuplicationException;
import Exceptions.WrongPasswordException;
import Interfaces.DAOs.IImportDetailDAO;
import Interfaces.DAOs.IUserDAO.loginType;
import Lib.Converter;
import Lib.EmailSender;
import Lib.ExceptionUtils;
import Lib.Generator;
import Models.Import;
import Models.ImportDetail;
import Models.ImportStashItem;
import Models.InventoryManager;
import Models.Product;
import Models.User;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class InventoryManagerController extends HttpServlet {

    public static final String IVTR_MANAGER_USER_URI = "/InventoryManager";
    public static final String IVTR_MANAGER_UPDATE_INFO_URI = "/InventoryManager/Update/Info";

    public static final String IVTR_MANAGER_PRODUCT_LIST_URI = "/InventoryManager/Product/List";
    public static final String IVTR_MANAGER_PRODUCT_IMPORT_URI = "/InventoryManager/ProductImport";

    public static final String IVTR_MANAGER_IMPORT_LIST_URI = "/InventoryManager/Import/List";
    public static final String IVTR_MANAGER_IMPORT_DETAIL_URI = "/InventoryManager/Import/Detail";

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

        if (path.startsWith(IVTR_MANAGER_IMPORT_LIST_URI)
                || path.startsWith(IVTR_MANAGER_IMPORT_LIST_URI + "/page")) {
            System.out.println("Going Import List");
            int result = -1;
            if (!path.endsWith("?errAccNF=true") && !path.endsWith("?errImpNF")) {
                result = searchImport(request);
            }
            if (result == State.Success.value) {
                request.getRequestDispatcher("/INVENTORY_MANAGER_PAGE/Import/list.jsp").forward(request, response);
            } else if (result == State.Fail.value) {
                response.sendRedirect(
                        IVTR_MANAGER_IMPORT_LIST_URI + ExceptionUtils.generateExceptionQueryString(request));
            } else if (result == -1) {
                response.sendRedirect(IVTR_MANAGER_IMPORT_LIST_URI);
            }

            return;
        }
        //
        if (path.startsWith(IVTR_MANAGER_IMPORT_DETAIL_URI)) {
            System.out.println("Going Order Detail");
            int result = getImportDetail(request);
            if (result == State.Success.value) {
                request.getRequestDispatcher("/INVENTORY_MANAGER_PAGE/Import/importDetail.jsp").forward(request,
                        response);
            } else if (result == State.Fail.value) {
                request.getRequestDispatcher(IVTR_MANAGER_IMPORT_DETAIL_URI + ExceptionUtils.generateExceptionQueryString(request));
            }
            return;
        }

        //
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
        
        if (path.startsWith(IVTR_MANAGER_UPDATE_INFO_URI)) {
          int result = updateAdminInfomation(request, response);

          if (result == State.Success.value) {
              response.sendRedirect(IVTR_MANAGER_USER_URI);
          } else {
              response.sendRedirect(IVTR_MANAGER_USER_URI + ExceptionUtils.generateExceptionQueryString(request));
          }
      }

    }

    private int getImportDetail(HttpServletRequest request) {
        ImportDAO ipDAO = new ImportDAO();
        InventoryManagerDAO ivtrManaDAO = new InventoryManagerDAO();

        String data[] = request.getRequestURI().split("/");

        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        InventoryManager ivtrManager = ivtrManaDAO.getInventoryManager(username);

        if (ivtrManager == null) {
            System.out.println("Unknow Username to view import detail");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }

        try {
            Import ip = null;
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals("ID")) {
                    int importId = Integer.parseInt(data[i + 1]);
                    ip = ipDAO.getImport(importId);
                }
            }
            if (ip == null) {
                System.out.println("not found import detail");
                request.setAttribute("exceptionType", "ImportNotFoundException");
                return State.Fail.value;
            }
            if (ip.getImportDetail() == null || ip.getImportDetail().isEmpty()) {
                System.out.println("import has no import detail");
                request.setAttribute("exceptionType", "ImportNotFoundException");
                return State.Fail.value;
            }
            if (ip.getImportByInventoryManager() != ivtrManager.getInventoryManagerId()) {
                System.out.println("has no authorization to view import detail");
                request.setAttribute("exceptionType", "NotEnoughInformationException");
            }
            request.setAttribute("importInfo", ip);
            return State.Success.value;
        } catch (NumberFormatException e) {
            request.setAttribute("exceptionType", "ImportNotFoundException");
            return State.Fail.value;
        }
    }

    private int searchImport(HttpServletRequest request) {
        InventoryManagerDAO ivtrManaDAO = new InventoryManagerDAO();
        ImportDAO ipDAO = new ImportDAO();
        Cookie currentUserCookie = (Cookie) request.getSession().getAttribute("userCookie");
        String username = currentUserCookie.getValue();
        InventoryManager ivtrManager = ivtrManaDAO.getInventoryManager(username);

        if (ivtrManager == null) {
            System.out.println("Unknow Username to import product");
            request.setAttribute("exceptionType", "AccountNotFoundException");
            return State.Fail.value;
        }
        String URI = request.getRequestURI();
        String data[] = URI.split("/");
        int page = 1;
        int rows = 20;
        String search = request.getParameter("txtSearch");
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("page")) {
                page = Integer.parseInt(data[i + 1]);
            }
        }
        List<Import> importList = ipDAO.searchImport(search);
        importList = ipDAO.fillterImport(importList, ivtrManager.getInventoryManagerId());
        if (importList.isEmpty()) {
            System.out.println("Empty import list");
            request.setAttribute("exceptionType", "ImportNotFoundException");
            return State.Fail.value;
        }
        int numberOfPage = (importList.size() / rows) + (importList.size() % rows == 0 ? 0 : 1);
        importList = Generator.pagingList(importList, page, rows);
        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", numberOfPage);
        request.setAttribute("importList", importList);
        request.setAttribute("search", search);
        return State.Success.value;
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
        List<Product> productList;
        try {
            productList = pDAO.searchProduct(search);
        } catch (ProductNotFoundException ex) {
            request.setAttribute("exceptionType", "ProductNotFoundException");
            return State.Fail.value;
        }

        int numberOfPage = (productList.size() / rows) + (productList.size() % rows == 0 ? 0 : 1);
        productList = Generator.pagingList(productList, page, rows);

        request.setAttribute("page", page);
        request.setAttribute("numberOfPage", numberOfPage);
        request.setAttribute("productList", productList);
        request.setAttribute("search", search);

        return State.Success.value;
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
            ipD.setStatus(IImportDetailDAO.Status.WAIT.toString());
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
            } catch (Exception e) {
               System.out.println(e); 
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
