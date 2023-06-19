<%-- 
    Document   : index.jsp
    Created on : Jun 7, 2023, 1:34:15 PM
    Author     : Acer
--%>

<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
            crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond&family=Josefin+Sans:wght@200&family=Josefin+Slab&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/shop/public/style/style.css"type="text/css">
        <link rel="icon" href="${pageContext.request.contextPath}/shop/public/images/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css" rel="stylesheet" type="text/css" />

        <title><%= request.getAttribute("shopName")%></title>
        <%! ProductDAO pdao = new ProductDAO();%>       
        <%! BrandDAO bdao = new BrandDAO();%>       
        <%! ResultSet rs = null;%>


    </head>
    <body>
        <div class="container-fluid">

            <div class="row">
                <div class="col-md-12 nav">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/index.html">trang chủ</a></li>
                        <li><a href="${pageContext.request.contextPath}/introduction/index.jsp">giới thiệu</a></li>
                        <li><a href="${pageContext.request.contextPath}/brand/index.jsp">thương hiệu</a></li>
                        <!-- This link to shop servlet file. DO NOT MODIFY the link -->
                        <li><a href="${pageContext.request.contextPath}/shop">sản phẩm</a></li>
                        <li><a href="">blog</a></li>
                    </ul>
                    <a href="./home/index.html"><img src="./resources/images/icons/icon.webp" alt=""
                                                     height="64"></a>
                    <div class="account">
                        <a><img src="./resources/images/icons/search.png" alt=""></a>
                        <a href="./logIn/index.jsp"><img src="./resources/images/icons/user.png" alt=""></a>
                        <a><img src="./resources/images/icons/cart.png" alt=""></a>
                    </div>
                </div>
            </div>

            <div class="row no-gutters center">
                <div class="content">
                    <h1 class="header-for-shop"><%= request.getAttribute("shopName")%></h1>
                    <div class="col-md-12 main">
                        <div class="search-bar">
                            <div class="brand">
                                <h4>THƯƠNG HIỆU</h4>
                                <input placeholder="Tìm kiếm nhanh" type="text" name="" id="searchBox">
                                <div class="list-brand">
                                    <ul>
                                        <%
                                            rs = (ResultSet) request.getAttribute("BDResultSet");
                                            while (rs != null && rs.next()) {
                                        %>
                                        <li> 
                                            <a href="shop?filter_brand=<%=rs.getInt("ID")%>">
                                                <%=rs.getString(2)%></a> 
                                        </li>

                                        <%
                                            }
                                            rs = null;
                                        %>
                                    </ul>
                                </div>
                            </div>
                            <div class="gender">
                                <h4>GIỚI TÍNH</h4>
                                <input type="checkbox" name="Gender" id="Nam">
                                <label for="male" value="Male">Nam</label>

                                <input type="checkbox" name="Gender" id="Nữ">
                                <label for="female" value="Female">Nữ</label>

                                <input type="checkbox" name="Gender" id="Unisex">
                                <label for="unisex" value="Unisex">Unisex</label>
                            </div>
                            <div class="price">
                                <h4>THEO GIÁ</h4>
                                <input type="radio" name="priceRange" id="low" value="low">
                                <label for="low">1.500.000 - 3.000.000</label>
                                <br>
                                <input type="radio" name="priceRange" id="medium" value="medium">
                                <label for="medium">3.000.000 - 5.000.000</label>
                                <br>
                                <input type="radio" name="priceRange" id="high" value="high">
                                <label for="high"> >5.000.000</label>
                            </div>

                        </div> 
                        <div class="products" id="products">
                            <div class="top" id="top">
                                <select name="sorting" id="sorting" class="sorting" autofocus onchange="callSorting(this.value)" >
                                    <option value="all" ><button type="button">Tất cả</button></option>
                                    <option value="lowToHigh"><button type="button" >Thứ tự theo giá: thấp đến cao</button></option>
                                    <option value="highToLow"><button type="button">Thứ tự theo giá: cao đến thấp</button></option>
                                </select>
                            </div>

                            <%
                                rs = (ResultSet) request.getAttribute("PDResultSet");
                                while (rs != null && rs.next()) {
                            %>
                            <div class="product">
                                <img src="<%= rs.getString("ImgURL")%>" alt="" class="product-img">
                                <span class="product-brand"><%= bdao.getBrandName(rs.getInt("BrandID"))%></span>
                                <span class="product-name"><%= rs.getString("Name")%></span>
                                <span class="product-price"><%=  pdao.getPrice(rs.getInt("ID"))%> <span>đ</span></span>
                            </div>
                            <%
                                }
                                rs = null;
                            %>


                        </div> 

                    </div>
                    <ul class="pagination">
                        <li class="page-item"><a class="page-link" href="#"></a></li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item"><a class="page-link" href="#"></a></li>
                    </ul>
                </div>

            </div>

            <div class="row">
                <div class="col-md-12 register">
                    <h1>Đăng ký thành viên để nhận khuyến mại</h1>
                    <p>Theo dõi chúng tôi để nhận thêm nhiều ưu đãi</p>
                    <form action="">
                        <input type="text" name="" id="" placeholder="nhập email">
                        <button>ĐĂNG KÝ</button>
                    </form>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 social">
                    <a href=""><img src=".//resources/images/icons/instagram.png" alt=""></a>
                    <a href=""><img src=".//resources/images/icons/facebook.png" alt=""></a>
                    <a href=""><img src=".//resources/images/icons/youtube.png" alt=""></a>
                    <a href=""><img src=".//resources/images/icons/location-pin.png" alt=""></a>
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
                    <p>&copy; xxiv 2023 | all rigth reserved</p>
                </div>
            </div>

        </div>


        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
        <script src="./public/js/main.js"></script>
        <script>

                                    let gender = document.querySelectorAll("input[name=Gender]");
                                    for (let i = 0; i < gender.length; i++)
                                    {
                                        gender[i].addEventListener("change", function () {
                                            console.log(this.id);
                                            window.location.replace("shop?filter-gender=" + this.id);
                                        });
                                    }
        </script>
    </body>
</html>
