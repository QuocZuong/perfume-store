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
<%! ResultSet rs = null;%>
<%! ArrayList<User> listUser = null; %>
<%! int currentPage, numberOfPage;%>
<%! boolean isAdmin;%>

<%
    rs = uDAO.getAllForAdmin();
    listUser = (ArrayList<User>) request.getAttribute("listUser");
    currentPage = (int) request.getAttribute("page");
    numberOfPage = (int) request.getAttribute("numberOfPage");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
                    <input id="inputSearch" type="text" name="txtSearch" value="<%= (request.getParameter("txtSearch") != null ? request.getParameter("txtSearch") : "")%>">
                    <a class="page-link" href="" id="Search" onclick="changeLink();">Search</a>
                    <table class="table" id="table">
                        <thead>
                            <tr>
                                <td>User ID</td>
                                <td>Name</td>
                                <td>Username</td>
                                <td>Phone Number</td>
                                <td>Email</td>
                                <td>Address</td>
                                <td>Role</td>
                                <td></td>
                                <td></td>
                            </tr>
                        </thead>

                        <tbody>
                            <c:if test="<%= (listUser.size() != 0)%>">
                                <c:forEach var="i" begin="0" end="<%= listUser.size() - 1%>">
                                    <%
                                        User us = listUser.get((int) pageContext.getAttribute("i"));
                                    %>
                                    <tr class="rowTable  ">
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getID()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getName()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getUsername()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getPhoneNumber()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getEmail()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getAddress()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>"><%= us.getRole()%></td>
                                        <td class="<%= us.isActive() ? " " : "faded"%>">
                                            <a href="/Admin/User/Update/ID/<%= us.getID()%>" class="<%= us.isActive() ? "" : "disabled"%> btn btn-outline-primary rounded-0">Update</a>
                                        </td>
                                        <td class="buttonStatus <%= us.isActive() ? "" : "unfaded"%>">
                                            <a href="/Admin/User/<%= us.isActive() ? "Delete" : "Restore"%>/ID/<%=  us.getID()%>" class="btn btn-outline-<%= us.isActive() ? "danger" : "success"%> rounded-0"> <%= us.isActive() ? "Delete" : "Restore"%></a>
                                        </td>
                                    </tr>

                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <h1>num page: ${numberOfPage}</h1>
        <h1>page:  <%= currentPage%> </h1>
        <nav aria-label="...">
            <ul class="pagination">
                <li class="page-item"><a class="page-link" href="/Admin/User/List/page/1<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>">Trang dau</a></li>
                <li class="page-item<%= currentPage == 1 ? " disabled" : ""%>"><a class="page-link" href="/Admin/User/List/page/<%=currentPage - 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>">Previous</a></li>
                    <c:forEach var="i" begin="${page-2<0?0:page-2}" end="${page+2 +1}">
                        <c:choose>
                            <c:when test="${i==page}">
                            <li class="page-item active"><a href="/Admin/User/List/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
                            </c:when>
                            <c:when test="${i>0 && i<=numberOfPage}"> 
                            <li class="page-item"><a href="/Admin/User/List/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
                            </c:when>
                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                <li class="page-item<%= currentPage == numberOfPage ? " disabled" : ""%>"><a class="page-link" href="/Admin/User/List/page/<%=currentPage + 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>">Next</a></li>
                <li class="page-item"><a class="page-link" href="/Admin/User/List/page/${numberOfPage}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>">Trang cuoi</a></li>
            </ul>
        </nav>
        <script>
            function changeLink() {
                let SearchURL = document.getElementById("inputSearch").value;
                document.getElementById("Search").href = "/Admin/User/List/page/1?txtSearch=" + SearchURL;
            }
        </script>
        <script src="/RESOURCES/admin/public/js/list.js"></script>
    </body>
</html>
