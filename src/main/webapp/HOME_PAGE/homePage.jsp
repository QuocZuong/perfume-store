<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.Cookie"%>

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
                        <a href="/Client/User"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
                        <a href="/Client/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>
                    </div>
                </div>
            </div>

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
                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Nuoc-hoa-Creed-Aventus-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Creed</span>
                                    <hr>
                                    <span class="product-name">Aventus</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/13-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Lelabo</span>
                                    <hr>
                                    <span class="product-name">Another 13</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/6bb559193c12a192157b071aa6c2f153-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Tom Ford</span>
                                    <hr>
                                    <span class="product-name">Lost Cherry</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="./logIn/index.html">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/baccarat540-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Maison Francis Kurkdijian</span>
                                    <hr>
                                    <span class="product-name">Baccarat Rouge 540</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/Marie-Jeanne-Vetiver-Santal-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Marie Jeanne</span>
                                    <hr>
                                    <span class="product-name">Vetiver Santal</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/Nuoc-hoa-Dior-Sauvage-EDT-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Dior</span>
                                    <hr>
                                    <span class="product-name">Sauvage</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                        </div>
                        <div class="item unisex">
                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Nuoc-hoa-Creed-Aventus-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Creed</span>
                                    <hr>
                                    <span class="product-name">Aventus</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/13-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Lelabo</span>
                                    <hr>
                                    <span class="product-name">Another 13</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/6bb559193c12a192157b071aa6c2f153-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Tom Ford</span>
                                    <hr>
                                    <span class="product-name">Lost Cherry</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>

                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/baccarat540-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Maison Francis Kurkdijian</span>
                                    <hr>
                                    <span class="product-name">Baccarat Rouge 540</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Marie-Jeanne-Vetiver-Santal-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Marie Jeanne</span>
                                    <hr>
                                    <span class="product-name">Vetiver Santal</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Nuoc-hoa-Dior-Sauvage-EDT-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Dior</span>
                                    <hr>
                                    <span class="product-name">Sauvage</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">

                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
                                </div>
                            </a>
                            <a href="./logIn/index.html">
                                <div class="card">
                                    <img src="/RESOURCES/images/products/Kilian-Angels-Share-600x600.png" alt="" class="product-img">
                                    <span class="product-brand">Kilian</span>
                                    <hr>
                                    <span class="product-name">Angels Share</span>
                                    <span class="product-price">6.000.000 <span>đ</span></span>
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
                    <form action="">
                        <input type="text" name="" id="" placeholder="nhập email" required="true">
                        <button type="button">ĐĂNG KÝ</button>
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
    </body>
</html>