<%@page import="Lib.ExceptionUtils"%>
<%@page import="Lib.Converter"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Models.Product"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAOs.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! BrandDAO bDAO = new BrandDAO(); %>
<%! List<Product> productList = null; %>
<%! int currentPage, numberOfPage;%>



<%
    productList = (List<Product>) request.getAttribute("productList");
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
        <title>Danh sách sản phẩm</title>
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
        <link rel="stylesheet" href="/RESOURCES/admin/product/public/style/list.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
              crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
              rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <script src="https://kit.fontawesome.com/49a22e2b99.js" crossorigin="anonymous"></script>
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
                    <jsp:include page="/NAVBAR/InventoryManagerNavbar.jsp"></jsp:include>
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
                                <td>Product ID</td>
                                <td>Product Name</td>
                                <td>Brand Name</td>
                                <td>ImgURL</td>    
                                <td>Quantity</td>
                                <td>

                                </td> 
                                <td></td> 
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test='<%= (productList.size() != 0)%>'>
                                <c:forEach var="i" begin="0" end="<%= productList.size() - 1%>">
                                    <%
                                        Product pd = productList.get((int) pageContext.getAttribute("i"));
                                    %>
                                    <tr class="rowTable  ">
                                        <td class="<%= pd.isActive() ? " " : "faded"%>" ><%= pd.getId()%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"><%= pd.getName()%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"><%= bDAO.getBrand(pd.getBrandId()).getName()%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"><img src="<%= pd.getImgURL()%>" alt="<%= pd.getName()%>"/></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>">
                                            <input type="number" min="1" name="<%= "txtQuantity" + pd.getId()%>" id="<%="txtQuantity" + pd.getId()%>">
                                        </td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>">
                                            <a id="ImportClick<%= pd.getId()%>" href="/InventoryManager/ProductImport/ProductID/<%= pd.getId()%>" class="<%= pd.isActive() ? "" : "disabled"%> btn btn-outline-primary rounded-0 import-click">Add to import cart</a>
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
                <li class="page-item"><a class="page-link" href="/InventoryManager/Product/List/page/1<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angles-left" style="color: #000000;"></i></a></li>
                <li class="page-item<%= currentPage == 1 ? " disabled" : ""%>"><a class="page-link" href="/InventoryManager/Product/List/page/<%=currentPage - 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angle-left" style="color: #000000;"></i></a></li>
                        <c:forEach var="i" begin="${page-2<0?0:page-2}" end="${page+2 +1}">
                            <c:choose>
                                <c:when test="${i==page}">
                            <li class="page-item active"><a href="/InventoryManager/Product/List/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
                            </c:when>
                            <c:when test="${i>0 && i<=numberOfPage}"> 
                            <li class="page-item"><a href="/InventoryManager/Product/List/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
                            </c:when>
                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                <li class="page-item<%= currentPage == numberOfPage ? " disabled" : ""%>"><a class="page-link" href="/Admin/Product/List/page/<%=currentPage + 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angle-right" style="color: #000000;"></i></a></li>
                <li class="page-item"><a class="page-link" href="/InventoryManager/Product/List/page/${numberOfPage}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>"><i class="fa-solid fa-angles-right" style="color: #000000;"></i></a></li>
            </ul>
        </nav>
        <script src="/RESOURCES/inventoryManager/Product/public/js/list.js"></script>
        <script>
                            function changeLink() {
                                let SearchURL = document.getElementById("inputSearch").value;
                                SearchURL = encodeURIComponent(SearchURL);
                                document.getElementById("Search").href = "/InventoryManager/Product/List/page/1?txtSearch=" + SearchURL;
                            }

                            $(".import-click").on("click", function () {
                                let selectedId = this.id;
                                selectedId = selectedId.replace("ImportClick", "");
                                selectedId = "txtQuantity" + selectedId;
                                let quantity = document.getElementById(selectedId).value;
                                if (quantity === null || quantity === "") {
                                    quantity = 1;
                                }
                                this.href = this.href + "/Quantity/" + quantity + "/";
                            });
        </script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
    </body>
</html>
