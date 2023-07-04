<%-- 
    Document   : index
    Created on : Jun 19, 2023, 10:58:07 PM
    Author     : quoczuong
--%>

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
        <link rel="stylesheet" href="../RESOURCES/brand/public/style/style.css">
        <link rel="icon" href="../RESOURCES/images/icons/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css" rel="stylesheet" type="text/css" />
        <title>Thương hiệu</title>
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
                        <li><a href="shop">sản phẩm</a></li>
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
            <div class="row">
                <div class="col-md-12 login-form">
                    <div class="type">
                        <button type="button" id="signIn">Đăng nhập</button>
                        <button type="button" id="signUp">Đăng ký</button>
                    </div>

                    <form action="" class="sign-in" id="signInForm">
                        <label for="user">Tên tài khoản hoặc địa chỉ email *</label>
                        <br>
                        <input type="text" name="user" id="user-input">
                        <br>
                        <label for="password">Mật khẩu *</label>
                        <br>
                        <input type="password" name="password" id="password-input">
                        <br>
                        <input type="checkbox" name="remember-password" id="remember-password" class="remember-password">
                        <label for="remember-password">Ghi nhớ mật khẩu</label>
                        <br>
                        <button class="enter">Đăng nhập</button>
                    </form>

                    <form action="" class="sign-up" id="signUpForm">
                        <label for="email">Địa chỉ email *</label>
                        <br>
                        <input type="text" name="email" id="user-input">
                        <p>Một mật khẩu sẽ được gửi đến địa chỉ email của bạn.</p>
                        <br>
                        <button class="enter">Đăng ký</button>
                    </form>
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
                    <a href=""><img src="../RESOURCES/images/icons/instagram.png" alt=""></a>
                    <a href=""><img src="../RESOURCES/images/icons/facebook.png" alt=""></a>
                    <a href=""><img src="../RESOURCES/images/icons/youtube.png" alt=""></a>
                    <a href=""><img src="../RESOURCES/images/icons/location-pin.png" alt=""></a>
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


        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
        <script src="../RESOURCES/cart/public/js/main.js"></script>
    </body>
</html>