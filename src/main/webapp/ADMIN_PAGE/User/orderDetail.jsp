<%@page import="Models.Order"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Models.Product"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="Models.User"%>
<%@page import="DAOs.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! ProductDAO pDAO = new ProductDAO(); %>
<%! List<String[]> order;%>
<%!Order OrderInfor;%>
<%! User client = null;%>
<%
    OrderInfor = (Order) request.getAttribute("OrderInfor");
    order = (List<String[]>) (request.getAttribute("OrderDetail"));
    client = (User) request.getAttribute("client");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= client.getUsername() %>'s Order Detail</title>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css"/>
        <link rel="stylesheet" href="/RESOURCES/admin/user/public/style/list.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
              crossorigin="anonymous">

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
                    <div>
                        <h1 class="display-6">Thông tin khách hàng: <%= client.getUsername()%></h1>
                    </div>
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Thông tin</th>
                                <th scope="col">Sản phẩm</th>
                                <th scope="col" class="number">Số lượng</th>
                                <th scope="col">Đơn giá</th>
                                <th scope="col">Tổng tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:if test="<%=  order.size() > 0%>">
                            <c:forEach var="i" begin="0" end="<%= order.size() - 1%>">
                                <tr>
                                    <th scope="row"><%=(int) pageContext.getAttribute("i") + 1%></th>
                                    <td><%= order.get((int) pageContext.getAttribute("i"))[0]%></td>
                                    <td><img src="<%=order.get((int) pageContext.getAttribute("i"))[1]%>"></td>
                                    <td><%=order.get((int) pageContext.getAttribute("i"))[2]%></td>
                                    <td><%=  pDAO.IntegerToMoney(Integer.parseInt(order.get((int) pageContext.getAttribute("i"))[3]))%></td>
                                    <td><%=  pDAO.IntegerToMoney(Integer.parseInt(order.get((int) pageContext.getAttribute("i"))[5]))%></td>
                                </tr>
                            </c:forEach>
                            <tr class="bottom-table">
                                <td colspan="6">
                                    <p><img src="/RESOURCES/images/icons/smartphone.png" alt="alt"/><span class="info"><%=OrderInfor.getPhoneNumber()%></span></p>
                                    <p><img src="/RESOURCES/images/icons/location-pin.png" alt="alt"/><span class="info"><%=OrderInfor.getAddress()%></span></p>
                                    <p><img src="/RESOURCES/images/icons/email.png" alt="alt"/><span class="info"><%=OrderInfor.getNote()%></span></p>
                                </td>
                            </tr>
                        </c:if>

                        </tbody>
                    </table>

                </div>
            </div>
        </div>
    </body>
</html>
