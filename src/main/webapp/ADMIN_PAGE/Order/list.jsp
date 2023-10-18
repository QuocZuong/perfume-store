
<%@page import="DAOs.CustomerDAO"%>
<%@page import="DAOs.OrderDAO"%>
<%@page import="Lib.ExceptionUtils"%>
<%@page import="Lib.Converter"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Models.Order"%>
<%@page import="Models.Customer"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAOs.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! OrderDAO oAO = new OrderDAO();%>
<%! CustomerDAO cDAO = new CustomerDAO();%>
<%! ResultSet rs = null;%>
<%! List<Order> orderList = null; %>
<%! int currentPage, numberOfPage;%>
<%! boolean isAdmin, isOrderManager;%>

<%
    orderList = (List<Order>) request.getAttribute("orderList");
    currentPage = (int) request.getAttribute("page");
    numberOfPage = (int) request.getAttribute("numberOfPage");

    String queryString = request.getQueryString();
    boolean isError = ExceptionUtils.isWebsiteError(queryString);
    String exceptionMessage = ExceptionUtils.getMessageFromExceptionQueryString(queryString);
%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Danh sách đơn hàng</title>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css"/>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
          crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
          rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://kit.fontawesome.com/49a22e2b99.js" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="/RESOURCES/admin/order/public/style/list.css">
    <style>
      img{
        width:50px;
        height: 50px;
      }
    </style>


  </head>
  <body>
    <div class="container-fluid">

      <!--Navbar section-->
      <div class="row">
        <div class="col-md-12 nav">
          <jsp:include page="/NAVBAR/AdminNavbar.jsp"></jsp:include>
          </div>
        </div>

        <div class="row">
          <div class="col-md-10 offset-1 d-flex justify-content-center align-items-center flex-column">
            <!--Execption Handling-->
          <c:if test='<%= isError%>'>
            <h1 class="alert alert-danger text-center"> <%= exceptionMessage%></h1>
          </c:if>
          <!--Execption Handling-->

          <div class="search-box-first">
            <a class="page-link" href="" id="Search" onclick="changeLink();"><img src="/RESOURCES/images/icons/search.png" alt=""></a>
            <input id="inputSearch" type="text" name="txtSearch" placeholder="Tìm kiếm" value="<%= (request.getParameter("txtSearch") != null ? request.getParameter("txtSearch") : "")%>" autofocus onkeydown="handleKeyDown(event)">
          </div>

          <table class="table" id="table">
            <thead>
              <tr>
                <td>ID</td>
                <td>Tên khách hàng</td>
                <td>Địa chỉ</td>
                <td>Số điện thoại</td>
                <td>Ghi chú</td>
                <td>Tổng tiền</td>
                <td>Giảm giá</td>
                <td>Trạng thái</td>
                <td>Ngày tạo</td>
                <td>Ngày thanh toán</td>
                <td>Ngày cập nhật</td>
                <td>Người cập nhật</td>
                <td>Chi tiết</td>
                <td></td>
                <td></td>
              </tr>
            </thead>
            <tbody>
              <c:if test='<%= (orderList.size() != 0)%>'>
                <c:forEach var="i" begin="0" end="<%= orderList.size() - 1%>">
                  <%
                                        Order o = orderList.get((int) pageContext.getAttribute("i"));
                                        Customer c = cDAO.getCustomer(o.getCustomerId());

                                        boolean isActive = !o.getStatus().equals("Rejected");
                  %>
                  <tr class="rowTable <%= isActive ? " " : "faded"%>">
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getId()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= c.getName()%></td>
                    <td class="<%= isActive ? " " : "faded"%>" class="description">
                      <div class="content"><<%= o.getDeliveryAddress()%></div>
                      <button class="expand-btn">Xem thêm</button>
                    </td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getPhoneNumber()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getNote()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getTotal()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getDeductedPrice()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getStatus()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getCreatedAt()%></td>                                        
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getCheckoutAt()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getUpdateAt()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getUpdateByOrderManager()%></td>
                    
                    <td class="<%= isActive ? " " : "faded"%>">
                      <a href="/Admin/Order/Detail/ID/<%= o.getId()%>" class="<%= isActive ? "" : "disabled"%> btn btn-outline-primary rounded-0">Info</a>
                    </td>
                    <td class="buttonStatus <%= isActive ? "" : "unfaded"%>">
                      <a href="/Admin/Order/<%= isActive ? "Delete" : "Restore"%>/ID/<%=  o.getId()%>" class="btn btn-outline-<%= isActive ? "danger" : "success"%> rounded-0"> <%= isActive ? "Delete" : "Restore"%></a>
                    </td>
                    <td class="buttonStatus <%= isActive ? "" : "unfaded"%>">
                      <a href="/Admin/Order/<%= isActive ? "Delete" : "Restore"%>/ID/<%=  o.getId()%>" class="btn btn-outline-<%= isActive ? "danger" : "success"%> rounded-0"> <%= isActive ? "Delete" : "Restore"%></a>
                    </td>
                  </tr>

                </c:forEach>
              </c:if>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <h1 class="d-none">num page: ${numberOfPage}</h1>
    <h1 class="d-none">page:  <%= currentPage%> </h1>

    <nav aria-label="...">
      <ul class="pagination">
        <li class="page-item"><a class="page-link" href="/Admin/Product/List/page/1<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angles-left" style="color: #000000;"></i></a></li>
        <li class="page-item<%= currentPage == 1 ? " disabled" : ""%>"><a class="page-link" href="/Admin/Product/List/page/<%=currentPage - 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angle-left" style="color: #000000;"></i></a></li>
            <c:forEach var="i" begin="${page-2<0?0:page-2}" end="${page+2 +1}">
              <c:choose>
                <c:when test="${i==page}">
              <li class="page-item active"><a href="/Admin/Product/List/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
              </c:when>
              <c:when test="${i>0 && i<=numberOfPage}"> 
              <li class="page-item"><a href="/Admin/Product/List/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
              </c:when>
              <c:otherwise>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        <li class="page-item<%= currentPage == numberOfPage ? " disabled" : ""%>"><a class="page-link" href="/Admin/Product/List/page/<%=currentPage + 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angle-right" style="color: #000000;"></i></a></li>
        <li class="page-item"><a class="page-link" href="/Admin/Product/List/page/${numberOfPage}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angles-right" style="color: #000000;"></i></a></li>
      </ul>
    </nav>
    <script>
      function changeLink() {
        let SearchURL = document.getElementById("inputSearch").value;
        SearchURL = encodeURIComponent(SearchURL);
        document.getElementById("Search").href = "/Admin/Product/List/page/1?txtSearch=" + SearchURL;
      }
    </script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
    crossorigin="anonymous"></script>
    <script src="/RESOURCES/admin/product/public/js/list.js"></script>
  </body>
</html>