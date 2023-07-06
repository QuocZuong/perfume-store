/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import DAOs.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class AdminController extends HttpServlet {

    // ----------------------- URI DECLARATION SECTION ----------------------------
    public static String ADMIN_PRODUCT_LIST_URI = "/Admin/List";
    public static String ADMIN_PRODUCT_ADD_URI = "/Admin/Add";
    public static String ADMIN_UPDATE_URI = "/Admin/Update";
    public static String ADMIN_DELETE_URI = "/Admin/Delete";

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
        if (path.startsWith(ADMIN_PRODUCT_LIST_URI)) {
            request.getRequestDispatcher("/ADMIN_PAGE/list.jsp").forward(request, response);
            return;
        }
        if (path.startsWith(ADMIN_DELETE_URI)) {
            deleteProduct(request, response);
            request.getRequestDispatcher("/ADMIN_PAGE/list.jsp").forward(request, response);
            return;
        }
        if (path.startsWith(ADMIN_PRODUCT_ADD_URI)) {
            request.getRequestDispatcher("/ADMIN_PAGE/add.jsp").forward(request, response);
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

        String path = request.getRequestURI();

        if (path.startsWith(ADMIN_PRODUCT_ADD_URI)) {
            if (request.getParameter("btnAddProduct") != null
                    && request.getParameter("btnAddProduct").equals("Submit")) {
                addProduct(request, response);
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
            }
            return;
        }
        if (path.startsWith(ADMIN_UPDATE_URI)) {
            if (request.getParameter("btnName") != null && request.getParameter("btnName").equals("Submit")) {
                updateProduct(request, response);
                response.sendRedirect(ADMIN_PRODUCT_LIST_URI);
            }
            return;
        }

    }

    /* CRUD */
    // ---------------------------- CREATE SECTION ----------------------------
    private void addProduct(HttpServletRequest request, HttpServletResponse response) {

        ProductDAO pDAO = new ProductDAO();
        String pName = request.getParameter("txtProductName");
        String bName = request.getParameter("txtBrandName");
        String pPrice = request.getParameter("txtProductPrice");
        String Gender = request.getParameter("rdoGender");
        String Smell = request.getParameter("txtProductSmell");
        int Quantity = Integer.parseInt(request.getParameter("txtProductQuantity"));
        int ReleaseYear = Integer.parseInt(request.getParameter("txtProductReleaseYear"));
        int Volume = Integer.parseInt(request.getParameter("txtProductVolume"));
        String Description = request.getParameter("txtProductDescription");

        String Local_destination = getServletContext().getRealPath("/").replace("target\\SQLproject-1\\",
                "src\\main\\webapp\\RESOURCES\\images\\products\\");
        String Target_destination = getServletContext().getRealPath("/") + "RESOURCES\\images\\products";

        File IMG = new File(Target_destination);
        if (!IMG.exists()) {
            IMG.mkdir();
        }

        System.out.println(pDAO.getMaxProductID() + 1);

        String filename = (pDAO.getMaxProductID() + 1) + ".png";
        String ImgURL = "\\RESOURCES\\images\\products\\" + filename;
        try {
            Part imagePart = request.getPart("fileProductImg");
            InputStream imageInputStream = imagePart.getInputStream();
            OutputStream outputStream = new FileOutputStream(Local_destination + filename);
            OutputStream outputStreamTarget = new FileOutputStream(Target_destination + filename);
            byte[] buffer = new byte[1024];
            int bytes;
            while ((bytes = imageInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytes);
                outputStreamTarget.write(buffer, 0, bytes);
            }
            imageInputStream.close();
            outputStream.close();
            outputStreamTarget.close();
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        String addData = pDAO.convertToStringData(pName, bName, pPrice, Gender, Smell, Quantity + "", ReleaseYear + "",
                Volume + "", ImgURL, Description);
        int kq = pDAO.addProduct(addData);

        if (kq == 0) {
            System.out.println("Add failed, Some attribute may be duplicate");
            return;
        }
        System.out.println("Add successfully");

        // Local
        // :C:\Users\Acer\OneDrive\Desktop\#SU23\PRJ301\SQLproject\perfume-store\src\main\webapp\RESOURCES\images\products
        // Target:
        // C:\Users\Acer\OneDrive\Desktop\#SU23\PRJ301\SQLproject\perfume-store\target\SQLproject-1\RESOURCES\images\products
    }

    // ---------------------------- READ SECTION ----------------------------

    // ---------------------------- UPDATE SECTION ----------------------------
    private void updateProduct(HttpServletRequest request, HttpServletResponse response) {

    }

    // ---------------------------- DELETE SECTION ----------------------------
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
        // Admin/Delete/ID/1
        String path = request.getRequestURI();
        String data[] = path.split("/");
        ProductDAO pDAO = new ProductDAO();
        String productId = null;
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("ID")) {
                productId = data[i + 1];
            }
        }
        if (productId == null) {
            System.out.println("Delete Failed, Lacking productID");
            return;
        }

        int kq = pDAO.deleteProduct(Integer.parseInt(productId));
        if (kq == 0) {
            System.out.println("Delete Failed, The Product is not in the database");
            return;
        }
        System.out.println("Delete Product with ID: " + productId + " successfully!");
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
