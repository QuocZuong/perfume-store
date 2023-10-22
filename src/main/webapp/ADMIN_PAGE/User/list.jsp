<%@page import="Models.Employee"%>
<%@page import="DAOs.EmployeeDAO"%>
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

<%! UserDAO uDAO = new UserDAO();%>
<%! BrandDAO bDAO = new BrandDAO(); %>
<%! List<User> listUser = null; %>
<%! int currentPage, numberOfPage;%>


<%
    Cookie currentUserCookie = (Cookie) pageContext.getAttribute("userCookie", pageContext.SESSION_SCOPE);
    String currentUsername = currentUserCookie.getValue();

    listUser = (List<User>) request.getAttribute("listUser");
    currentPage = (int) request.getAttribute("page");
    numberOfPage = (int) request.getAttribute("numberOfPage");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách người dùng</title>
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
        <link rel="stylesheet" href="/RESOURCES/admin/user/public/style/info.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
              crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
              rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <script src="https://kit.fontawesome.com/49a22e2b99.js" crossorigin="anonymous"></script>
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
        
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

                        <div class="search-box-first">
                            <a class="page-link" href="" id="Search" onclick="changeLink();"><img src="/RESOURCES/images/icons/search.png" alt=""></a>
                            <input id="inputSearch" type="text" name="txtSearch" placeholder="Tìm kiếm" value="<%= (request.getParameter("txtSearch") != null ? request.getParameter("txtSearch") : "")%>" autofocus onkeydown="handleKeyDown(event)">
                    </div>
                    <table class="table" id="table">
                        <thead>
                            <tr>
                                <td>User ID</td>
                                <td>Name</td>
                                <td>Username</td>
                                <td>Email</td>
                                <td>Type</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>
                                    <a href="/Admin/User/Add/Employee" class="btn btn-outline-dark rounded-0">Add employee</a>
                                </td>
                            </tr>
                        </thead>

                        <tbody>
                            <c:if test='<%= (listUser.size() != 0)%>'>
                                <c:forEach var="i" begin="0" end="<%= listUser.size() - 1%>">
                                    <%
                                        boolean isDisableUpdate = false;
                                        User us = listUser.get((int) pageContext.getAttribute("i"));

                                        if (us.getType().equals("Employee")) {
                                            EmployeeDAO employeeDAO = new EmployeeDAO();
                                            Employee employee = employeeDAO.getEmployeeByUserId(us.getId());
                                            if (employee.getRole().getName().equals("Admin")) {
                                                isDisableUpdate = true;
                                            }
                                        }

                                        if (!us.isActive()) {
                                            isDisableUpdate = true;
                                        }
                                    %>
                                    <tr class="rowTable">
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getId()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getName() == null ? "" : us.getName()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getUsername() == null ? "" : us.getUsername()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getEmail()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getType()%></td>
                                        <td>
                                            <a href="/Admin/User/Info/ID/<%= us.getId()%>" class="btn btn-outline-success rounded-0">Detail</a>
                                        </td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>">
                                            <a href="/Admin/User/Update/<%=us.getType().equals("Customer") ? "Customer" : "Employee"%>/ID/<%= us.getId()%>" class="<%= isDisableUpdate ? "disabled" : ""%> <%= us.isActive() ? "" : "disabled"%> btn btn-outline-primary rounded-0">Update</a>
                                        </td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>">
                                            <a href="/Admin/User/Detail/ID/<%= us.getId()%>" class="<%= us.isActive() && !us.getType().equals("Admin") ? "" : "disabled"%> btn btn-outline-info rounded-0">Order</a>
                                        </td>
                                        <td class="buttonStatus <%= us.isActive() ? "" : "unfaded"%>">
                                            <a href="/Admin/User/<%= us.isActive() ? "Delete" : "Restore"%>/ID/<%= us.getId()%>/<%= currentUsername%>/" class="<%=us.getUsername().equals(currentUsername) ? "disabled" : ""%> btn btn-outline-<%= us.isActive() ? "danger" : "success"%> rounded-0"> <%= us.isActive() ? "Delete" : "Restore"%></a>
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
                <li class="page-item"><a class="page-link" href="/Admin/User/List/page/1<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angles-left" style="color: #000000;"></i></a></li>
                <li class="page-item<%= currentPage == 1 ? " disabled" : ""%>"><a class="page-link" href="/Admin/User/List/page/<%=currentPage - 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angle-left" style="color: #000000;"></i></a></li>
                        <c:forEach var="i" begin="${page-2<0?0:page-2}" end="${page+2 +1}">
                            <c:choose>
                                <c:when test='${i==page}'>
                            <li class="page-item active"><a href="/Admin/User/List/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
                            </c:when>
                            <c:when test='${i>0 && i<=numberOfPage}'> 
                            <li class="page-item"><a href="/Admin/User/List/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
                            </c:when>
                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                <li class="page-item<%= currentPage == numberOfPage ? " disabled" : ""%>"><a class="page-link" href="/Admin/User/List/page/<%=currentPage + 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angle-right" style="color: #000000;"></i></a></li>
                <li class="page-item"><a class="page-link" href="/Admin/User/List/page/${numberOfPage}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angles-right" style="color: #000000;"></i></a></li>
            </ul>   
        </nav>
    </div>
    <script>
        function changeLink() {
            let SearchURL = document.getElementById("inputSearch").value;
            SearchURL = encodeURIComponent(SearchURL);
            document.getElementById("Search").href = "/Admin/User/List/page/1?txtSearch=" + SearchURL;
        }
    </script>
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
    crossorigin="anonymous"></script>
    <script src="/RESOURCES/admin/user/public/js/list.js"></script>
</body>
</html>
