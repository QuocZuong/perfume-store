import DAOs.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileOutputStream;

import DB.DataManager;

/**
 *
 * @author Acer
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class ImportProduct extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ImportProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImportProduct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
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
        processRequest(request, response);
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

        Enumeration<String> names = request.getParameterNames();
        StringBuilder data = new StringBuilder();
        String n;
        String value;
        String sp = DataManager.Separator;
        while (names.hasMoreElements()) {
            n = names.nextElement();
            if (n.equals("pImgFile") || n.equals("Submit")) {
                continue;
            }
            value = request.getParameter(n);
            if (n.equals("pPrice")) {
                value = DataManager.MoneyToInteger(value);
            }
            data.append(value);
            data.append(sp);
        }
        if ((data.charAt(data.length() - 1) + "").equals(sp)) {
            data.delete(data.length() - 1, data.length());
        }
        ResultSet rs = null;
        try {
            ProductDAO pd = new ProductDAO();


            // pd.addProduct(data.toString());
            rs = pd.getAll();
        } catch (Exception ex) {
            Logger.getLogger(ImportProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("result", rs);

        // Save image
        String destination = "C:\\Users\\Acer\\OneDrive\\Desktop\\#SU23\\PRJ301\\SQLproject\\SQLproject\\src\\main\\webapp\\img\\";
        Part imgPart = request.getPart("pImgFile");
        InputStream inS = imgPart.getInputStream();
        OutputStream outS = new FileOutputStream(destination + request.getParameter("pCode") + ".png");

        byte[] buffer = new byte[1024];
        int bytes;
        while ((bytes = inS.read(buffer)) != -1) {
            outS.write(buffer, 0, bytes);
        }
        inS.close();
        outS.close();

        request.getRequestDispatcher("AddedProduct.jsp").forward(request, response);
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
