<%-- 
    Document   : detail
    Created on : Jul 3, 2023, 3:41:56 PM
    Author     : Acer
--%>

<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="Models.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%! ProductDAO pDAO = new ProductDAO(); %>
<%! BrandDAO bDao = new BrandDAO();%>
<%

    Product pd = (Product) request.getAttribute("product");

    int id = pd.getID();
    String name = pd.getName();
    String brandName = bDao.getBrandName(pd.getBrandID());
    int price = pd.getPrice();
    String gender = pd.getGender();
    String smell = pd.getSmell();
    int quantity = pd.getQuantity();
    int releaseYear = pd.getReleaseYear();
    int volume = pd.getVolume();
    String imgURL = pd.getImgURL();
    String description = pd.getDescription();

%>

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
        <link rel="stylesheet" href="/RESOURCES/product/public/style/style.css" type="text/css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
        <!--<link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css" rel="stylesheet" type="text/css" />-->
        <title>Nước hoa</title>
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
                        <li><a href="">blog</a></li>
                    </ul>
                    <a href="/"><img src="/RESOURCES/images/icons/icon.webp" alt=""
                                     height="64"></a>
                    <div class="account">
                        <a><img src="/RESOURCES/images/icons/search.png" alt=""></a>
                        <a href="/Log/Login"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
                        <a href="/Client/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>
                    </div>
                </div>
            </div>



            <div class="row">
                <div class="col-md-12 main">
                    <div class="info">
                        <div class="left">
                            <img src="<%= imgURL%>" alt="" class="product-img" id="productImg">
                        </div>
                        <div class="right">
                            <p id="brandName"><%= brandName%></p>
                            <h1 id="productName"><%= name%></h1>
                            <span id="productPrice"><%= pDAO.IntegerToMoney(price)%><span>đ</span></span>
                            <div>
                                <img src="/RESOURCES/images/icons/sex.png" alt="">
                                <span id="gender"><%= gender%></span>
                            </div>
                            <div>
                                <p id="volume">Dung tích</p>
                                <span><%= volume%>ml</span>
                            </div>
                            <form action="/Client/addToCart" method="POST">
                                <input type="number" name="ProductQuantity" id="" value="1" >
                                <input type="hidden" name="ProductID" value="<%= id%>">
                                <button name="btnAddToCart" class="btnAddToCart" value="Submit" type="submit">THÊM VÀO GIỎ HÀNG</button>
                            </form>
                        </div>
                    </div>
                    <div class="bottom">

                        <div class="content">
                            <div class="description">
                                <hr>
                                <h1 id="descriptionTitle">mô tả</h1>
                                <label for="">tone hương</label>
                                <p id="tone"><%= smell%></p>
                                <br>
                                <p id="descriptionDetail">
                                    <%=description%>
                                </p>
                                <hr>
                            </div>
                        </div>
                    </div>
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
                    <p>&copy; xxiv 2023 | all rigth reserved</p>
                </div>
            </div>

        </div>


        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
        <!-- This script handle the quantity isn't out of bound -->
        <script>
            let inputQuan = document.querySelector("input[name=ProductQuantity]");
            inputQuan.addEventListener("input", function () {
                if (inputQuan.value > <%=quantity%>)
                {
                    inputQuan.value = <%=quantity%>;
                }

                if (inputQuan.value < 1 && inputQuan.value !== "")
                {
                    inputQuan.value = 1;
                }
            });
            inputQuan.addEventListener("blur", function () {
                if (inputQuan.value === "")
                {
                    inputQuan.value = 1;
                }
            });
        </script>
        <script src="/RESOURCES/product/public/js/main.js"></script>
    </body>
</html>