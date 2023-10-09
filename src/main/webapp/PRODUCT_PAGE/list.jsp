<%@page import="Models.Brand"%>
<%@page import="Models.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="DAOs.ProductDAO" %>
<%@page import="DAOs.BrandDAO" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page import="jakarta.servlet.http.HttpServletResponse"%>
<%@page import="jakarta.servlet.http.Cookie"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! ProductDAO pdao = new ProductDAO();%>
<%! BrandDAO bdao = new BrandDAO();%>

<%! boolean isProductNotFound;%>
<%! int currentPage, numberOfPage;%>
<%
    List<Product> productList = (List<Product>) request.getAttribute("productList");
    List<Brand> brandList = (List<Brand>) request.getAttribute("brandList");
    currentPage = (int) (request.getAttribute("currentPage") == null ? 0 : request.getAttribute("currentPage"));
    numberOfPage = (int) (request.getAttribute("numberOfPages") == null ? 0 : request.getAttribute("numberOfPages"));
    // Handling exception
    String err = "err";
    isProductNotFound = (request.getParameter(err + "PNF") == null ? false : Boolean.parseBoolean(request.getParameter(err + "PNF")));
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
              crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond&family=Josefin+Sans:wght@200&family=Josefin+Slab&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="/RESOURCES/shop/public/style/style.css" type="text/css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
              rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <script src="https://kit.fontawesome.com/49a22e2b99.js" crossorigin="anonymous"></script>
        <title>
            <%= request.getAttribute("shopName")%>
        </title>


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
                    <a href="/"><img src="/RESOURCES/images/icons/icon.webp" alt=""
                                     height="64"></a>

                    <!-- This is search function -->                                     
                    <div class="account">
                        <a class="searchIcon"><img src="/RESOURCES/images/icons/search.png" alt=""></a>
                        <a href="/Client/User"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
                        <a href="/Client/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>
                    </div>
                    <div class="search-box">
                        <div class="search-box-first">
                            <a href="" id="SearchProduct" onclick="changeLink()" >
                                <img src="/RESOURCES/images/icons/search.png" alt="">
                            </a>
                            <input id="inputSearch" type="text" name="txtSearch" placeholder="Tìm kiếm" value="<%= (request.getParameter("txtSearch") != null ? request.getParameter("txtSearch") : "")%>" autofocus onkeydown="handleKeyDown(event)">
                        </div>
                        <button class="close-search-box" type="button">
                            <img src="/RESOURCES/images/icons/close.png" alt="">
                        </button>
                    </div>

                </div>
            </div>

            <div class="row no-gutters center">
                <div class="content">
                    <h1 class="header-for-shop">
                        <%= request.getAttribute("shopName")%>
                    </h1>
                    <div class="col-md-12 main">
                        <div class="search-bar">
                            <div class="brand">
                                <h4>THƯƠNG HIỆU</h4>
                                <input placeholder="Tìm kiếm nhanh" type="text" name="" id="searchBox">
                                <div class="list-brand">
                                    <!-- Brand List -->
                                    <ul id="box-brand">
                                        <c:if test='<%= brandList != null%>'>
                                            <%for (Brand brand : brandList) {%>
                                            <li>
                                                <a  href="/Product/List/BrandID/<%=brand.getId()%>" class="brandNameForSearch">
                                                    <%= brand.getName()%>
                                                </a>
                                            </li>
                                            <% }%>
                                        </c:if>
                                    </ul>

                                </div>
                            </div>

                            <!--Filter Form-->

                            <form action="" id="searchForm" method="GET">
                                <div class="gender">
                                    <h4>GIỚI TÍNH</h4>
                                    <input type="checkbox" name="Gender" id="Nam" value="Nam">
                                    <label for="Nam" value="Male">Nam</label>

                                    <input type="checkbox" name="Gender" id="Nữ" value="Nữ">
                                    <label for="Nữ" value="Female">Nữ</label>

                                    <input type="checkbox" name="Gender" id="Unisex" value="Unisex">
                                    <label for="Unisex" value="Unisex">Unisex</label>
                                </div>
                                <div class="price">
                                    <h4>THEO GIÁ</h4>
                                    <input type="checkbox" name="priceRange" id="low" value="1500000-3000000">
                                    <label for="low">1.500.000 - 3.000.000</label>
                                    <br>
                                    <input type="checkbox" name="priceRange" id="medium" value="3000000-5000000">
                                    <label for="medium">3.000.000 - 5.000.000</label>
                                    <br>
                                    <input type="checkbox" name="priceRange" id="high" value="5000000-100000000">
                                    <label for="high"> >5.000.000</label>
                                </div>
                                <c:if test='<%= request.getParameter("txtSearch") != null%>'>
                                    <input type="hidden" name="txtSearch"  value="<%= request.getParameter("txtSearch")%>" >
                                </c:if>
                            </form>
                            <!--Filter Form-->

                        </div>

                        <c:choose>
                            <c:when  test="<%= isProductNotFound%>">
                                <div> 
                                    <h1 class="display-6">Không tìm thấy sản phẩm nào khớp với lựa chọn của bạn.</h1>
                                </div>
                            </c:when>
                            <c:otherwise>

                                <div class="products" id="products">
                                    <div class="top" id="top">
                                        <select name="sorting" id="sorting" class="sorting" autofocus
                                                onchange="callSorting(this.value)">
                                            <option value="all"><button type="button">Tất cả</button></option>
                                            <option value="lowToHigh"><button type="button">Thứ tự theo giá:
                                                thấp đến cao</button></option>
                                            <option value="highToLow"><button type="button">Thứ tự theo giá: cao
                                                đến thấp</button></option>
                                        </select>
                                    </div>
                                    <c:if  test='<%= productList != null%>'>
                                        <% for (Product product : productList) {%>

                                        <a href="/Product/Detail/ID/<%=  product.getId()%>">
                                            <div class="product">
                                                <img src="<%= product.getImgURL()%>" alt="" class="product-img">
                                                <span class="product-brand">
                                                    <%= bdao.getBrand(product.getBrandId()).getName()%>
                                                </span>
                                                <span class="product-name">
                                                    <%= product.getName()%> 
                                                </span>
                                                <span class="product-price">
                                                    <%= product.getStock().getPrice()%> <span>đ</span>
                                                </span>
                                            </div>
                                        </a>
                                        <%}%>
                                    </div>
                                </c:if>
                            </c:otherwise>
                        </c:choose>       


                    </div>
                    <!--                    <ul class="pagination">
                                            <li class="page-item"><a class="page-link" href="#"></a></li>
                                            <li class="page-item"><a class="page-link" href="#"></a></li>
                                            <li class="page-item"><a class="page-link" href="#">2</a></li>
                                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                                            <li class="page-item"><a class="page-link" href="#"></a></li>
                                        </ul>-->
                    <form>
                        <nav aria-label="...">
                            <ul class="pagination">
                                <li class="page-item<%= currentPage == 1 ? " disabled" : ""%>">
                                    <a class="page-link" href="${currentURL}/page/1<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link">
                                        <i class="fa-solid fa-angles-left" style="color: #000000;"></i>
                                    </a>
                                </li>
                                <li class="page-item<%= currentPage == 1 ? " disabled" : ""%>">
                                    <a class="page-link" href="${currentURL}/page/<%=currentPage - 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link">
                                        <i class="fa-solid fa-angle-left" style="color: #000000;"></i>
                                    </a>
                                </li>

                                <c:forEach var="i" begin="${page-2<0?0:page-2}" end="${page+2 +1}">
                                    <c:choose>
                                        <c:when test='${i==page}'>
                                            <li class="page-item active"><a href="${currentURL}/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
                                            </c:when>
                                            <c:when test='${i>0 && i<=numberOfPage}'> 
                                            <li class="page-item"><a href="${currentURL}/page/${i}<%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link"> ${i}</a></li>
                                            </c:when>
                                            <c:otherwise>

                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>

                                <li class="page-item<%= currentPage == numberOfPage ? " disabled" : ""%>">
                                    <a class="page-link" href="${currentURL}/page/<%=currentPage + 1%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link">
                                        <i class="fa-solid fa-angle-right" style="color: #000000;"></i>
                                    </a>
                                </li>
                                <li class="page-item<%= currentPage == numberOfPage ? " disabled" : ""%>">
                                    <a class="page-link" href="${currentURL}/page/<%=numberOfPage%><%= (request.getQueryString() == null ? "" : "?" + request.getQueryString())%>" class="page-link">
                                        <i class="fa-solid fa-angles-right" style="color: #000000;"></i>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </form>
                </div>

            </div>

            <div class="row">
                <div class="col-md-12 register">
                    <h1>Đăng ký thành viên để nhận khuyến mại</h1>
                    <p>Theo dõi chúng tôi để nhận thêm nhiều ưu đãi</p>
                    <form action="/home/subscribe" method="POST">
                        <input type="text" name="txtEmailSubscribe" id="" placeholder="nhập email" required="true">
                        <button type="submit" name="submitEmailBtn" value="Submit" class="enter">ĐĂNG KÝ</button>
                    </form>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 social">
                    <a href=""><img src="/RESOURCES/images/icons/instagram.png" alt=""></a>
                    <a href=""><img src="/RESOURCES/images/icons/facebook.png" alt=""></a>
                    <a href=""><img src="/RESOURCES/images/icons/youtube.png" alt=""></a>
                    <a href=""><img src="/RESOURCES/images/icons/location-pin.png" alt=""></a>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 footer">
                    <div>
                        <h2>xxiv store</h2>
                        <ul>
                            <li><a href="">ưu đãi thành viên</a></li>
                            <li><a href="">tài khoản</a></li>
                            <li><a href="">tuyển dụng</a></li>
                        </ul>
                    </div>
                    <div>
                        <h2>chính sách bán hàng</h2>
                        <ul>
                            <li><a href="">phương thức vận chuyển</a></li>
                            <li><a href="">câu hỏi thường gặp</a></li>
                            <li><a href="">điều khoản và điện kiện sử dụng</a></li>
                            <li><a href="">điều khoản và điều kiện bán hàng</a></li>
                            <li><a href="">thông báo pháp lý</a></li>
                        </ul>
                    </div>
                    <div>
                        <h2>thông tin chung</h2>
                        <ul>
                            <li><a href="">giới thiệu</a></li>
                            <li><a href="">blog</a></li>
                            <li><a href="">liên hệ</a></li>
                            <li><a href="">sản phẩm</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 copyright">
                    <p>&copy; xxiv 2023 | all right reserved</p>
                </div>
            </div>
        </div>

        <div class="spinner-container">
            <div class="spinner-border" role="status"></div>
        </div>
        <!-- This is funtion search -->
        <script>

            let input = document.getElementById("inputSearch");
            input.addEventListener("input", () => {
                console.log(input.value);
            })

            function changeLink() {
                let SearchURL = document.getElementById("inputSearch").value;
                SearchURL = encodeURIComponent(SearchURL);
                console.log(SearchURL);
            <%
                String searchTxT = "";
                if (request.getQueryString() != null) {
                    if (request.getQueryString().startsWith("txtSearch") || request.getQueryString().startsWith("errPNF")) {
                        searchTxT = "txtSearch=";
                    } else {
                        searchTxT = "&txtSearch=";
                    }
                }
            %>
                let url = "${currentURL}/page/1";

            <%
                String urljava = "";
                if (request.getQueryString() == null) {
                    urljava = "?txtSearch=";
                    System.out.println(urljava);
                } else {
                    urljava = request.getQueryString();
                    urljava = urljava.replace("&errPNF=true", "");
                    urljava = urljava.replace("errPNF=true", "");
                    urljava = urljava.replace("&txtSearch=" + request.getParameter("txtSearch"), "");
                    urljava = urljava.replace("txtSearch=" + request.getParameter("txtSearch"), "");
                    urljava += searchTxT;
                    urljava = "?" + urljava;
                    System.out.println(urljava);
                }
            %>
                url = url + "<%= urljava%>" + SearchURL;
                document.getElementById("SearchProduct").href = url;
            }
        </script>

        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
        <script src="/RESOURCES/shop/public/js/main.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <script src="/RESOURCES/admin/product/public/js/main.js"></script>
        <!--Jquery Validation-->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
        <script>
            $(document).ready(function () {
                $.validator.addMethod("emailCustom", function (value, element, toggler) {
                    if (toggler) {
                        let regex = /^[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+$/;
                        let result = regex.test(value);
                        return result;
                    }
                    return true;
                }, "Vui lòng nhập đúng định dạng email");

                $("form[action='/home/subscribe']").validate({
                    rules: {
                        txtEmailSubscribe: {
                            required: true,
                            email: true
                        }
                    },
                    messages: {
                        txtEmailSubscribe: {
                            required: "Vui lòng nhập email",
                            email: "Vui lòng nhập đúng định dạng email"
                        }
                    },

                    errorPlacement: function (error, element) {
                        error.addClass("text-danger d-block mt-3");
                        error.insertAfter(element.next());
                    }

                });
            });
        </script>

    </body>

</html>