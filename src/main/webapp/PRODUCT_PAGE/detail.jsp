<%@page import="Lib.ExceptionUtils"%>
<%@page import="Lib.Converter"%>
<%@page import="Models.Brand"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="Models.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%! ProductDAO pdao = new ProductDAO();%>
<%! BrandDAO bdao = new BrandDAO();%>
<%! ResultSet rs = null;%>
<%! ProductDAO pDAO = new ProductDAO(); %>
<%! BrandDAO bDao = new BrandDAO();%>
<%
    String queryString = request.getQueryString();
    boolean isError = ExceptionUtils.isWebsiteError(queryString);
    String exceptionMessage = ExceptionUtils.getMessageFromExceptionQueryString(queryString);
    Product pd = (Product) request.getAttribute("product");

    int id = pd.getId();
    String name = pd.getName();
    Brand brand = bDao.getBrand(pd.getBrandId());
    int price = pd.getStock().getPrice();
    String gender = pd.getGender();
    String smell = pd.getSmell();
    int quantity = pd.getStock().getQuantity();
    int releaseYear = pd.getReleaseYear();
    int volume = pd.getVolume();
    String imgURL = pd.getImgURL();
    String description = pd.getDescription();
    List<Product> productSuggestList = pDAO.filterProductByBrand(brand);
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
        <!-- Add zoom picture plugin -->
        <link rel="stylesheet" href="/RESOURCES/VenoBox-2.0.4/dist/venobox.min.css" />
        <link rel="stylesheet" href="/RESOURCES/VenoBox-2.0.4/dist/venobox.min.css" type="text/css" media="screen" />

        <title><%= name%></title>

        <!-- add disable pointer for btn -->
        <style>
            button:disabled{
                cursor: not-allowed;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">

            <!--Navbar section-->
            <div class="row">
                <div class="col-md-12 nav">
                    <jsp:include page="/NAVBAR/ClientNavbar.jsp"></jsp:include>
                    </div>
                </div>



                <div class="row">
                    <div class="col-md-12 main">
                        <div class="info">
                            <div class="left">
                                <a class="venobox" href="<%= imgURL%>"><img class=" product-img"  id="productImg" src="<%= imgURL%>" alt="perfume"/></a>

                        </div>
                        <div class="right">
                            <p id="brandName"><%= brand.getName()%></p>
                            <h1 id="productName"><%= name%></h1>
                            <span id="productPrice"><%= Converter.covertIntergerToMoney(price)%><span>đ</span></span>
                            <div>
                                <img src="/RESOURCES/images/icons/sex.png" alt="">
                                <span id="gender"><%= gender%></span>
                            </div>
                            <div>
                                <p id="volume">Dung tích</p>
                                <span><%= volume%>ml</span>
                            </div>
                            <form action="/Customer/addToCart" method="POST">
                                <input type="number" name="ProductQuantity" id="" value="1" >
                                <input type="hidden" name="ProductID" value="<%= id%>">
                                <input type="hidden" name="ProductPrice" value = <%= price%>>
                                <button name="btnAddToCart" class="btnAddToCart" value="Submit" type="submit" <%= (quantity == 0 ? "disabled" : "")%>><%= (quantity == 0 ? "HẾT HÀNG" : "THÊM VÀO GIỎ HÀNG")%></button>
                            </form>
                            <c:if test='<%=isError%>'>
                                <div>
                                    <p style="color: red"><%= exceptionMessage%> </p>
                                </div>
                            </c:if>
                                
                        </div>
                    </div>
                    <div class="bottom">

                        <div class="content">
                            <div class="description">
                                <hr>
                                <h1 id="descriptionTitle">Mô tả</h1>
                                <label for="">Tone hương</label>
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

                <div class="suggest-product">
                    <h1>Sản phẩm liên quan</h1>
                    <div class="suggest-product-list">
                        <%
                            for (Product product : productSuggestList) {
                                String productBrandName = brand.getName();
                                String productSuggestPrice = Converter.covertIntergerToMoney(product.getStock().getPrice());
                        %>
                        <a class="product-wrapper" href="/Product/Detail/ID/<%=product.getId()%>">
                              <div class="product">
                                <img src="<%=product.getImgURL()%>" alt="" class="product-img">
                                <span class="product-brand"><%=productBrandName%></span>
                                <hr>
                                <span class="product-name"><%=product.getName()%></span>
                                <span class="product-price"><%=productSuggestPrice%> <span>đ</span></span>
                            </div>
                        </a>
                        <%
                            }
                        %>
                    </div>
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
        <!-- Add zoom picture plugin -->
        <link rel="stylesheet" href="/RESOURCES/VenoBox-2.0.4/dist/venobox.min.js" />
        <script type="text/javascript" src="/RESOURCES/VenoBox-2.0.4/dist/venobox.min.js"></script>
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