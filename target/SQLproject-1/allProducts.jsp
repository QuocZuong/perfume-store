<%-- 
    Document   : allProducts
    Created on : Jun 6, 2023, 4:05:07 PM
    Author     : Acer
--%>

<%@page import="DAOs.BrandDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAOs.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show all products</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Josefin+Sans:wght@300&display=swap" rel="stylesheet">
        <style>
            body{
                font-family: "Josefin Sans";
            }

            .container{
                display: grid;
                grid-template-columns: auto auto auto auto auto;

                padding: 10px;
                border-radius: 10px;
                row-gap: 30px;
                column-gap:30px;
            }
            .item{
                display: flex;
                flex-direction: column;
                align-items: center;

                border:1px solid grey;
                box-shadow: 3px 3px 10px 1px rgba(0,0,0,0.35);
                padding:30px;

            }
            .item img{
                width:50%;
            }
            .item-name{

            }


        </style>
    </head>
    <body>
        <div class="container">
            <%
                ProductDAO pDao = new ProductDAO();
                BrandDAO bDao = new BrandDAO();
                ResultSet rs = pDao.getAll();
                while (rs.next()) {
            %>
            <div class="item">
                <img src="img/<%= rs.getString("Code")%>.png" alt="alt"/>
                <br>
                <div class="item-brand"> <%= bDao.getBrandName(rs.getString("BrandCode"))%> </div>
                <br>
                <div class="item-name"> <%= rs.getString("Name")%> </div>
                <br>
                <div class="item-name"> <%= pDao.getPrice(rs.getString("Code"))%>  </div>
            </div>
            <%                }

            %>
        </div>
    </body>
</html>
