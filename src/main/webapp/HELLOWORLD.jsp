<%-- 
    Document   : index
    Created on : Jun 4, 2023, 3:37:08 PM
    Author     : Acer
--%>

<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DB.DataManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Product Page</title>
    </head>
    <body>
        <%! String value = "";%>
        <%
            ResultSet rs;
            Connection conn = DB.DataManager.getConnection();
            rs = conn.prepareStatement(DataManager.select("Brand", "", "Code")).executeQuery();
        %>
        <h1>Insert Product</h1>
        <form action="ImportProduct" method="POST" enctype="multipart/form-data">

            <label>Product Code:</label> <input type="text" name="pCode"><br>
            <label>Product Name:</label> <input type="text" name="pName"><br>
            <label for="bCode">Brand Code:</label> 
            <select name="bCode">
                <option value="value" hidden="true" value="" selected></option>
                <%
                    while (rs.next()) {
                        value = rs.getString("Code");
                %>
                <option value="<%= value%>"><%= value%></option>
                <%
                    }
                %>
            </select>
            <br>
            <label>Price:</label> <input type="text" name="pPrice"><br>
            <label>Gender:</label> 
            <select name="pGender">
                <option value="" hidden="true" selected></option>
                <option value="Nam">Nam</option>
                <option value="Nữ">Nữ</option>
                <option value="Unisex">Unisex</option>
            </select>
            <br>
            <label>Smell:</label> <input type="text" name="pSmell"><br>
            <label>Quantity:</label> <input type="number" name="pQuantity"><br>
            <label>Release Year:</label> <input type="number" name="pReleaseYear"><br>
            <label>Volume</label> <input type="number" name="pVolume"><br>
            <label>Description</label> <textarea name="pDescription" rows="20" cols="30"></textarea> <br>
            <label>Image</label> <input type="file" name="pImgFile"> <br>

            <input type="submit" name="Submit">
        </form>
        <a href="allProducts.jsp">allProduct</a>
    </body>
</html>
