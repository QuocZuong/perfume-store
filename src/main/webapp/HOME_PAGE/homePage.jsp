<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.Cookie"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%
    Cookie currentUserCookie = (Cookie) pageContext.getAttribute("userCookie", pageContext.SESSION_SCOPE);
    boolean isAdmin = false;

    if (currentUserCookie != null) {
        isAdmin = currentUserCookie.getName().equals("Admin");
    }
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
        <link rel="stylesheet" href="/RESOURCES/home/public/style/style.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css" rel="stylesheet" type="text/css" />
        <title>Perfume Store</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row no-gutters">
                <div class="col-md-12 header">
                    <video id="bg-video" autoplay loop muted>
                        <source src="/RESOURCES/videos/header.mp4" type="video/mp4">
                    </video>
                    <h1>xxiv store</h1>
                    <h2>You deserve to be happy</h2>
                    <button>FOLLOW ME</button>
                    <p>chào mừng các bạn đến với XXIII store - một tiệm nước hoa
                        nho nhỏ lấy cảm hứng từ những con người có cảm xúc đặc
                        biệt với hương thơm mê hoặc.</p>
                </div>
            </div>

            <c:choose>
                <c:when test='<%= isAdmin%>'>

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
                </c:when>
                <c:otherwise>
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
                                <a href="/Client/User"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
                                <a href="/Client/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="row">
                <div class="col-md-12 brand">
                    <h1>thương hiệu nổi tiếng</h2>

                        <div class="logo">
                            <a href="/Product/List/BrandID/22"><img
                                    src="/RESOURCES/images/brands/159133030_1044233219399119_4321418372070751780_n.png"
                                    alt=""></a>
                            <a href="/Product/List/BrandID/38"><img
                                    src="/RESOURCES/images/brands/Hang-nuoc-hoa-Imaginary-Authors.png"
                                    alt=""></a>
                            <a href="/Product/List/BrandID/51"><img
                                    src="/RESOURCES/images/brands/Hang-nuoc-hoa-Maison-Matine.png"
                                    alt=""></a>
                            <a href="/Product/List/BrandID/48"><img
                                    src="/RESOURCES/images//brands/LOrchestre-Parfums.png"
                                    alt=""></a>
                            <a href="/Product/List/BrandID/61"><img src="/RESOURCES/images/brands/Nasomatto.png"
                                                                    alt=""></a>
                            <a href="/Product/List/BrandID/16"><img
                                    src="/RESOURCES/images/brands/nuoc-hoa-by-kilian.png"
                                    alt="" class="kilian"></a>
                            <a href="/Product/List/BrandID/27"><img src="/RESOURCES/images/brands/nuoc-hoa-dior.png"
                                                                    alt=""></a>
                            <a href="/Product/List/BrandID/46"><img
                                    src="/RESOURCES/images/brands/nuoc-hoa-le-labo.png"
                                    alt="" class="lelabo"></a>
                            <a href="/Product/List/BrandID/74"><img
                                    src="/RESOURCES/images/brands/nuoc-hoa-tomford.png"
                                    alt="" class="tomford"></a>
                            <a href="/Product/List/BrandID/79"><img src="/RESOURCES/images/brands/nuoc-hoa-ysl.png"
                                                                    alt=""></a>
                            <a href="/Product/List/BrandID/66"><img src="/RESOURCES/images/brands/Orto-Parisi.png"
                                                                    alt=""></a>
                            <a href="/Product/List/BrandID/80"><img src="/RESOURCES/images/brands/Zoologist.png"
                                                                    alt=""></a>
                            <hr>  
                        </div>

                </div>
            </div>

            <div class="row">
                <div class="product-container">

                    <div class="col-md-12 product">
                        <h1>sản phẩm nổi bật</h1>
                        <div class="classify">
                            <button class="btnMan">nước hoa nam</button>
                            <button class="btnWoman">nước hoa nữ</button>
                            <button class="btnUnisex">unisex</button>
                        </div>

                        <div class="item man active">
                            <a href="/Product/Detail/ID/88">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Nuoc-hoa-Creed-Aventus-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Creed</span>
                                    <hr>
                                    <span class="product-name">Aventus</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="/Product/Detail/ID/39">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">By Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels` Share</span>
                                    <span class="product-price">4.800.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="/Product/Detail/ID/128">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/09/eau-de-minthe-eau-de-parfum-75ml-e87-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Diptyque</span>
                                    <hr>
                                    <span class="product-name">Eau de minthé</span>
                                    <span class="product-price">3.700.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="/Product/Detail/ID/246">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/baccarat540-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Maison Francis Kurkdijian</span>
                                    <hr>
                                    <span class="product-name">Baccarat Rouge 540</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/88">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Nuoc-hoa-Creed-Aventus-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Creed</span>
                                    <hr>
                                    <span class="product-name">Aventus</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/107">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/06/bbda9ea512ea8b2187cf7ca60da01936-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Dior</span>
                                    <hr>
                                    <span class="product-name">Sauvage EDP</span>
                                    <span class="product-price">3.400.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/28">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/Marie-Jeanne-Vetiver-Santal-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Burberry</span>
                                    <hr>
                                    <span class="product-name">Mr. Burberry EDP</span>
                                    <span class="product-price">2.600.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/39">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">By Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels` Share</span>
                                    <span class="product-price">4.800.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/3">

                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/07/CIPRESSO-DI-TOSCANA-EDT-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Acqua di Parma</span>
                                    <hr>
                                    <span class="product-name">Cipresso di Toscana</span>
                                    <span class="product-price">3.100.000 <span>đ</span></span>
                                </div>
                            </a>
                        </div>

                        <div class="item woman">
                            <a href="/Product/Detail/ID/398">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/07/afa315528ab34622c4184e035cc662f0-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Tom Ford</span>
                                    <hr>
                                    <span class="product-name">Santal Blush</span>
                                    <span class="product-price">5.800.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/89">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/06/aventus-for-her-eau-de-parfum-75ml-8cd-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Creed</span>
                                    <hr>
                                    <span class="product-name">Aventus For Her</span>
                                    <span class="product-price">7.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/375">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/07/1df87c53922b7304030d168cef3632ed-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Tom Ford</span>
                                    <hr>
                                    <span class="product-name">Jasmin Rouge EDP</span>
                                    <span class="product-price">5.500.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="/Product/Detail/ID/89">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/06/aventus-for-her-eau-de-parfum-75ml-8cd-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Creed</span>
                                    <hr>
                                    <span class="product-name">Aventus For Her</span>
                                    <span class="product-price">7.000.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="/Product/Detail/ID/419">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2021/12/Dama1-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Xerjoff</span>
                                    <hr>
                                    <span class="product-name">Dama Bianca</span>
                                    <span class="product-price">6.900.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="/Product/Detail/ID/398">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/07/afa315528ab34622c4184e035cc662f0-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Tom Ford</span>
                                    <hr>
                                    <span class="product-name">Santal Blush</span>
                                    <span class="product-price">5.800.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/39">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/120">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/07/558153_1_4-e1594898257731-600x530.png" alt="" class="product-img">
                                    <span class="product-brand">Dior</span>
                                    <hr>
                                    <span class="product-name">Miss Dior Blooming Bouquet EDT</span>
                                    <span class="product-price">3.600.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/127">

                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/08/do-son-eau-de-toilette-100ml-99a-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Diptyque</span>
                                    <hr>
                                    <span class="product-name">Do Son</span>
                                    <span class="product-price">3.500.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/10">

                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2023/01/Amouge-Meander-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Amouage</span>
                                    <hr>
                                    <span class="product-name">Meander</span>
                                    <span class="product-price">7.900.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/419">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2021/12/Dama1-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Xerjoff</span>
                                    <hr>
                                    <span class="product-name">Dama Bianca</span>
                                    <span class="product-price">6.900.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/171">

                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/08/hermes-twilly-d-hermes-hermes-twilly-d-hermes-eau-de-parfum-50-ml-3346133200014-copy-600x629.png" alt="" class="product-img">
                                    <span class="product-brand">Hermès</span>
                                    <hr>
                                    <span class="product-name">Twilly d`Hermès</span>
                                    <span class="product-price">2.950.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/191">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2022/08/Imaginary-Authors-Fox-in-the-flowerbed-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Imaginary Authors</span>
                                    <hr>
                                    <span class="product-name">Fox in the Flowerbed</span>
                                    <span class="product-price">3.200.000 <span>đ</span></span>
                                </div>
                            </a>
                        </div>
                        <div class="item unisex">
                            <a href="/Product/Detail/ID/392">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/08/Mandarino-di-amalfi.png" alt="" class="product-img">
                                    <span class="product-brand">Tom Ford</span>
                                    <hr>
                                    <span class="product-name">Mandarino Di Amalfi</span>
                                    <span class="product-price">4.100.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/394">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/08/tom-ford-lavender-extreme-eau-de-parfum-50ml.png" alt="" class="product-img">
                                    <span class="product-brand">Tom Ford</span>
                                    <hr>
                                    <span class="product-name">Lavender Extreme</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/256">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/06/fire-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Maison Margiela</span>
                                    <hr>
                                    <span class="product-name">Replica By The Fireplace</span>
                                    <span class="product-price">3.500.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/246">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/baccarat540-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Maison Francis Kurkdijian</span>
                                    <hr>
                                    <span class="product-name">Baccarat Rouge 540</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/256">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/06/fire-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Maison Margiela</span>
                                    <hr>
                                    <span class="product-name">Replica By The Fireplace</span>
                                    <span class="product-price">3.500.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="/Product/Detail/ID/325">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/6bb559193c12a192157b071aa6c2f153-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Nasomatto</span>
                                    <hr>
                                    <span class="product-name">Nasomatto Blamage</span>
                                    <span class="product-price">3.850.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="/Product/Detail/ID/329">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2021/07/XXIV-Store-Nishane-Hacivat-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Nishane</span>
                                    <hr>
                                    <span class="product-name">Nishane Hacivat</span>
                                    <span class="product-price">5.500.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/339">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2022/05/Orto-Parisi-Viride.png" alt="" class="product-img">
                                    <span class="product-brand">Orto Parisi</span>
                                    <hr>
                                    <span class="product-name">Orto Parisi Viride</span>
                                    <span class="product-price">5.300.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/392">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/08/Mandarino-di-amalfi.png" alt="" class="product-img">
                                    <span class="product-brand">Tom Ford</span>
                                    <hr>
                                    <span class="product-name">Mandarino Di Amalfi</span>
                                    <span class="product-price">4.100.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/394">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2020/08/tom-ford-lavender-extreme-eau-de-parfum-50ml.png" alt="" class="product-img">
                                    <span class="product-brand">Tom Ford</span>
                                    <hr>
                                    <span class="product-name">Lavender Extreme</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="/Product/Detail/ID/426">
                                <div class="card">
                                    <img src="https://xxivstore.com/wp-content/uploads/2022/08/Zoologist-Seahorse-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Zoologist</span>
                                    <hr>
                                    <span class="product-name">Seahorse</span>
                                    <span class="product-price">5.200.000 <span>đ</span></span>
                                </div>
                            </a>
                        </div>

                        <div class="dots">
                            <span class="dot active"></span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 reason">
                    <hr>
                    <h1>Tại sao chọn xxiv store</h1>
                    <div class="listReason">
                        <div class="advantage">
                            <img src="/RESOURCES/images/icons/grommet-icons_shield-security.svg" alt="">
                            <h4>sản phẩm chính hãng
                            </h4>
                            <p>sản phẩm nước hoa được mua trực tiếp tại các store ở nước ngoài hoặc làm việc trực tiếp với các hãng, cam kết authentic 100%
                            </p>
                        </div>
                        <div class="advantage">
                            <img src="/RESOURCES/images/icons/free-ship.svg" alt="">
                            <h4>free ship toàn quốc
                            </h4>
                            <p>xxiv áp dụng freeship cho tất cả các khách hàng trên toàn quốc. chúng tôi chưa áp dụng hình thức giao hàng quốc tế tại thời điểm này</p>
                        </div>
                        <div class="advantage">
                            <img src="/RESOURCES/images/icons/gift.svg" alt="">
                            <h4>thành viên thân thiết
                            </h4>
                            <p>thành viên vàng sẽ được giảm 5% / đơn hàng. với thành viên bạc khách được giảm 3% / đơn hàng.</p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 contact">
                    <hr>
                    <h1>xxiv store</h1>
                    <div>
                        <img src="/RESOURCES/images/icons/location-pin.png">
                        <p>Số 25 Ngõ Thái Hà, Đống Đa, Hà Nội | 525/44 Tô Hiến Thành, P14, Q10, TP. Hồ Chí Minh</p>
                    </div>
                    <div>
                        <img src="/RESOURCES/images/icons/smartphone.png">
                        <p>090 721 9889| 093 194 8668</p>
                        <img src="/RESOURCES/images/icons/email.png" class="mail-icon">
                        <p>xxiv.fragrance@gmail.com</p>
                    </div>
                    <div>
                        <p>Giờ mở cửa: Các ngày trong tuần từ 9:00 - 21:00</p>
                    </div>
                </div>
            </div>

            <div class="row no-gutters">
                <div class="theme ">
                    <img src="/RESOURCES/images/icons/perfumeStore.jpg">
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
            <script
                src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"></script>
            <script src="/RESOURCES/home/public/js/main.js"></script>
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