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

<%!List<Order> orders = null;%>
<%!User client;%>
<%!ProductDAO pDAO = new ProductDAO();%>

<%
    orders = (List<Order>) request.getAttribute("orderList");
    client = (User) request.getAttribute("client");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <title><%= client.getUsername() %>'s Detail</title>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
        <!--        <script>
                    $(document).ready(function () {
                        $('#table').DataTable();
                    });
                </script>-->
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
        <link rel="stylesheet" href="/RESOURCES/admin/user/public/style/detail.css">

        <style>
            img{
                width:50px;
                height: 50px;
            }
        </style>
        <title>Chi tiết người dùng</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
        <div class="col-md-12 nav">
          <ul>
            <li><a href="/">trang chủ</a></li>
            <li> <a href="/home/introduction">giới thiệu</a></li>
            <li><a href="/home/brand">thương hiệu</a></li>
            <!-- This link to shop servlet file. DO NOT MODIFY the link -->
            <li><a href="/Product/List">sản phẩm</a></li>
          </ul>
          <a href="/" class="iconPage"><img src="/RESOURCES/images/icons/icon.webp" alt=""
                                            height="64"></a>
          <div class="account">
            <button class="droppown-btn bg-transparent border-0" id="product-dropdown-btn"><img src="/RESOURCES/images/icons/shopping-bag-alone.png" alt="">
            </button>
            <ul class="shadow position-absolute align-items-start ps-1 pt-2">
              <li class="py-3 text-dark"><a href="/Admin/Product/Add">Thêm sản phẩm</a></li>
              <li class="pb-3 text-dark"><a href="/Admin/Product/List">Danh sách sản phẩm</a></li>
            </ul>
            
            <button class="droppown-btn bg-transparent border-0" id="user-dropdown-btn"><img src="/RESOURCES/images/icons/group.png" alt="">
            </button>
            <ul class="shadow position-absolute align-items-start ps-1 pt-2">
              <li class="py-3 text-dark"><a href="/Admin/User/List">Danh sách người dùng</a></li>
            </ul>
            
            <a href="/Client/User"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
            <a href="/Client/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>

          </div>
        </div>
      </div>
            <div class="row">
                <div class="col-md-10 offset-1">
                    <div>
                        <h1 class="display-6">Thông tin khách hàng: <%= client.getUsername()%></h1>
                    </div>

                    <c:choose>
                        <c:when test="<%=orders.size() == 0%>">
                            <p>Khách hàng chưa có đơn hàng nào</p>
                        </c:when>
                        <c:otherwise>
                            <table class="table">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Ngày mua hàng</th>
                                        <th scope="col">Tổng tiền</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>

                                        <c:if test="<%=orders.size() != 0%>">
                                            <c:forEach var="i" begin="0" end="<%=orders.size() - 1%>">
                                            <tr>
                                                <th scope="row"><%=orders.get((int) pageContext.getAttribute("i")).getID()%></th>
                                                <td><%=orders.get((int) pageContext.getAttribute("i")).getDate()%></td>
                                                <td><%=pDAO.IntegerToMoney(orders.get((int) pageContext.getAttribute("i")).getSum())%></td>
                                                <td><a href="/Admin/User/OrderDetail/ID/<%=orders.get((int) pageContext.getAttribute("i")).getID()%>" target="_blank">Xem chi tiết</a></td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>

                                    </tr>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>

        <script src="/RESOURCES/admin/user/public/js/detail.js">
    </body>
</html>
