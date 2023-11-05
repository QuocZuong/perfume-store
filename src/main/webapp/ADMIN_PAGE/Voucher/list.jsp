
<%@page import="Lib.ExceptionUtils"%>
<%@page import="Lib.Generator.DatePattern"%>
<%@page import="Lib.Converter"%>
<%@page import="Lib.Generator"%>

<%@page import="DAOs.AdminDAO"%>

<%@page import="Models.Admin"%>
<%@page import="Models.Voucher"%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! AdminDAO adDAO = new AdminDAO();%>
<%! List<Voucher> voucherList = null; %>
<%! int currentPage, numberOfPage;%>


<%
    voucherList = (List<Voucher>) request.getAttribute("voucherList");
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
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">

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
                </div>

                <div class="col-md-10 offset-1 d-flex justify-content-center align-items-center flex-column">
                    <table class="table" id="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Code</th>
                                <th>Số lượng</th>
                                <th>% giảm giá</th>
                                <th>giảm giá max</th>
                                <th>Ngày tạo</th>
                                <th>Ngày hết hạn</th>
                                <th>Admin cập nhật</th>
                                <th><a href="/Admin/Voucher/Add" class="btn btn-outline-dark rounded-0">Add voucher</a></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test='<%= (voucherList.size() != 0)%>'>
                                <c:forEach var="i" begin="0" end="<%= voucherList.size() - 1%>">
                                    <%
                                        Voucher v = voucherList.get((int) pageContext.getAttribute("i"));
                                        Admin admin = adDAO.getAdmin(v.getCreatedByAdmin());
                                        boolean isActive = true;
                                        if (v.getExpiredAt() < Generator.getCurrentTimeFromEpochMilli()) {
                                            isActive = false;
                                        }

                                    %>
                                    <tr class="rowTable  ">
                                        <td class="<%=isActive ? " " : "faded"%>" ><%= v.getId()%></td>
                                        <td class="<%=isActive ? " " : "faded"%>" ><%= v.getCode()%></td>
                                        <td class="<%=isActive ? " " : "faded"%>" ><%= v.getQuantity()%></td>
                                        <td class="<%=isActive ? " " : "faded"%>" ><%= v.getDiscountPercent()%></td>
                                        <td class="<%=isActive ? " " : "faded"%>" ><%= v.getDiscountMax()%></td>


                                        <td class="<%=isActive ? " " : "faded"%>" ><%= v.getCreatedAt(DatePattern.DateForwardSlashPattern)%></td>
                                        <td class="<%=isActive ? " " : "faded"%>" ><%= v.getExpiredAt(DatePattern.DateForwardSlashPattern)%></td>
                                        <td class="<%=isActive ? " " : "faded"%>" ><%= admin.getName()%></td>

                                        <td>
                                            <a href="/Admin/Voucher/Update/ID/<%= v.getId()%>" class="btn btn-outline-primary rounded-0">Update</a>
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
                <li class="page-item"><a class="page-link" href="/Admin/Order/List/page/1<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angles-left" style="color: #000000;"></i></a></li>
                <li class="page-item<%= currentPage == 1 ? " disabled" : ""%>"><a class="page-link" href="/Admin/Order/List/page/<%=currentPage - 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angle-left" style="color: #000000;"></i></a></li>
                        <c:forEach var="i" begin="${page-2<0?0:page-2}" end="${page+2 +1}">
                            <c:choose>
                                <c:when test="${i==page}">
                            <li class="page-item active"><a href="/Admin/Order/List/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
                            </c:when>
                            <c:when test="${i>0 && i<=numberOfPage}"> 
                            <li class="page-item"><a href="/Admin/Order/List/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
                            </c:when>
                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                <li class="page-item<%= currentPage == numberOfPage ? " disabled" : ""%>"><a class="page-link" href="/Admin/Order/List/page/<%=currentPage + 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angle-right" style="color: #000000;"></i></a></li>
                <li class="page-item"><a class="page-link" href="/Admin/Order/List/page/${numberOfPage}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angles-right" style="color: #000000;"></i></a></li>
            </ul>
        </nav>
        <script>
            function changeLink() {
                let SearchURL = document.getElementById("inputSearch").value;
                SearchURL = encodeURIComponent(SearchURL);
                document.getElementById("Search").href = "/Admin/Order/List/page/1?txtSearch=" + SearchURL;
            }
        </script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
        <script src="/RESOURCES/admin/product/public/js/list.js"></script>
    </body>
</html>
