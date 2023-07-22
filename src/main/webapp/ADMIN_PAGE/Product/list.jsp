
<%@page import="java.util.ArrayList"%>
<%@page import="Models.Product"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAOs.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! ProductDAO pDAO = new ProductDAO();%>
<%! BrandDAO bDAO = new BrandDAO(); %>
<%! ResultSet rs = null;%>
<%! ArrayList<Product> listProduct = null; %>
<%! int currentPage, numberOfPage;%>
<%! boolean isAdmin;%>


<%
    rs = pDAO.getAllForAdmin();
    listProduct = (ArrayList<Product>) request.getAttribute("listProduct");
    currentPage = (int) request.getAttribute("page");
    numberOfPage = (int) request.getAttribute("numberOfPage");


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
            <div class="row">
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
                                <td></td> 
                                <td></td> 
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test='<%= (listProduct.size() != 0)%>'>
                                <c:forEach var="i" begin="0" end="<%= listProduct.size() - 1%>">
                                    <%
                                        Product pd = listProduct.get((int) pageContext.getAttribute("i"));
                                    %>
                                    <tr class="rowTable  ">
                                        <td class="<%= pd.isActive() ? " " : "faded"%>" ><%= pd.getID()%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"><%= pd.getName()%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"><%= bDAO.getBrandName(pd.getBrandID())%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"> <%= pDAO.IntegerToMoney(pd.getPrice())%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"><%= pd.getGender()%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>" > <%= pd.getSmell()%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"><%= pd.getQuantity()%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"><%= pd.getReleaseYear()%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"><%= pd.getVolume()%></td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>"><img src="<%= pd.getImgURL()%>" alt="<%= pd.getName()%>"/></td>
                                        <td class="description <%= pd.isActive() ? " " : "faded"%>">
                                            <div class="content"><%= pd.getDescription()%></div>
                                            <button class="expand-btn">Xem thêm</button>
                                        </td>
                                        <td class="<%= pd.isActive() ? " " : "faded"%>">
                                            <a href="/Admin/Product/Update/ID/<%= pd.getID()%>" class="<%= pd.isActive() ? "" : "disabled"%> btn btn-outline-primary rounded-0">Update</a>
                                        </td>
                                        <td class="buttonStatus  <%= pd.isActive() ? "" : "unfaded"%>">
                                            <a href="/Admin/Product/<%= pd.isActive() ? "Delete" : "Restore"%>/ID/<%=  pd.getID()%>" class="btn btn-outline-<%= pd.isActive() ? "danger" : "success"%> rounded-0"> <%= pd.isActive() ? "Delete" : "Restore"%></a>
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
