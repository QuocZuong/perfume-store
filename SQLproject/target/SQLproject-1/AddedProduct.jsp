<%-- 
    Document   : AddedProduct
    Created on : Jun 4, 2023, 4:43:17 PM
    Author     : Acer
--%>

<%@page import="DB.DataManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#dataBase').DataTable();
            });
        </script>
        <link href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css" rel="stylesheet"/>
    </head>
    <body>
        <%! String value;%>
        <table border="1px" id="dataBase">
            <thead>
                <tr>
                    <th>Code</th> 
                    <th>Name</th>
                    <th>Brand Code</th>
                    <th>Price</th>
                    <th>Gender</th>
                    <th>Smell</th>           
                    <th>Quantity</th>
                    <th>Release Year</th>
                    <th>Volume</th>
                    <th>Description</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ResultSet rs = (ResultSet) request.getAttribute("result");
                    String column[] = DataManager.TableMap.get("Product").split(",");
                    while (rs.next()) {
                %>
                <tr>
                    <%
                        for (int i = 0; i < column.length; i++) {
                            value = rs.getObject(column[i]) + "";
                    %>
                    <td><%= value%></td>
                    <%
                        }
                    %>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
