
<%@page import="DAOs.BrandDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAOs.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! ProductDAO pDAO = new ProductDAO();%>
<%! BrandDAO bDAO = new BrandDAO(); %>
<%! ResultSet rs = null;%>
<%
    rs = pDAO.getAllForAdmin();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#table').DataTable();
            });
        </script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css"/>


        <style>
            img{
                width:50px;
                height: 50px;
            }
        </style>

    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-10 offset-1">
                    <table class="table" id="table">
                        <thead>
                            <tr>
                                <td>Product ID</td>
                                <td>Product Name</td>
                                <td>Brand Name</td>
                                <td>Price</td>
                                <td>Gender</td>
                                <td>Smell</td>
                                <td>Quantity</td>
                                <td>ReleaseYear</td> 
                                <td>Volume</td>    
                                <td>ImgURL</td>    
                                <td>Description</td>    
                                <td>Active</td>   
                                <td>Update</td> 
                                <td>Delete</td> 
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                while (rs.next()) {
                            %>
                            <tr>
                                <td><%= rs.getInt("ID")%></td>
                                <td><%= rs.getNString("Name")%></td>
                                <td><%= bDAO.getBrandName(rs.getInt("BrandID"))%></td>
                                <td><%= rs.getInt("Price")%></td>
                                <td><%= rs.getNString("Gender")%></td>
                                <td><%= rs.getNString("Smell")%></td>
                                <td><%= rs.getInt("Quantity")%></td>
                                <td><%= rs.getInt("ReleaseYear")%></td>
                                <td><%= rs.getInt("Volume")%></td>
                                <td><img src="<%= rs.getNString("ImgURL")%>" alt="<%= rs.getNString("Name")%>"/></td>
                                <td><%= rs.getNString("Description")%></td>
                                <td><%= rs.getInt("Active")%></td>
                                <td>Update</td>
                                <td>Delete</td>
                            </tr>

                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
