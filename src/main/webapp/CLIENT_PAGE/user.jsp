<%-- Document : newjsp Created on : Jul 5, 2023, 3:27:56 PM Author : Acer --%>

<%@page import="Models.User"%>
<%@page import="DAOs.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%! UserDAO usDAO = new UserDAO();%>
<%!String fullname, username, email;%>
<%
    Cookie currentUserCookie = (Cookie) pageContext.getAttribute("userCookie", pageContext.SESSION_SCOPE);
    User user = usDAO.getUser(currentUserCookie.getValue());
    fullname = user.getName();
    username = user.getUsername();
    email = user.getEmail();
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
              crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond&family=Josefin+Sans:wght@200&family=Josefin+Slab&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="/RESOURCES/user/public/style/style.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
              rel="stylesheet" type="text/css" />
        <title>Tài khoản</title>
    </head>

    <body>


        <div class="container-fluid">
            <h1><%= user.getName()%></h1>
            <h1><%= user.getUsername()%></h1>
            <h1><%= user.getEmail()%></h1>
            <h1><%= user.getPassword()%></h1>
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
                    <a href="/"><img src="/RESOURCES/images/icons/icon.webp" alt="" height="64"></a>
                    <div class="account">
                        <a><img src="/RESOURCES/images/icons/search.png" alt=""></a>
                        <a href="/Log/Login"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
                        <a href="/Client/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>
                    </div>
                </div>
            </div>


            <div class="main">
                <div class="left">
                    <h1>Tài khoản của tôi</h1>
                    <div class="list">
                        <ul>
                            <li><a>Trang tài khoản</a></li>
                            <li><a>Đơn hàng</a></li>
                            <li><a>Địa chỉ</a></li>
                            <li><a>Tài khoản</a></li>
                            <li><a href="/Log/Logout">Đăng xuất</a></li>
                        </ul>
                    </div>
                </div>
                <div class="right">
                    <div class="account-page">
                        <p>Xin chào <b><strong>quocvuongle.ct</strong></b> (không phải tài khoản
                            <b><strong>quocvuongle.ct</strong></b>? Hãy <a href="">thoát ra</a> và đăng nhập vào tài
                            khoản của bạn)</p>
                        <p>
                            Từ trang quản lý tài khoản bạn có thể xem <a href="">đơn hàng mới</a>, quản lý <a
                                href="">địa chỉ giao hàng và thanh toán</a>, và <a href="">sửa mật khẩu và thông tin
                                tài khoản</a>.
                        </p>
                    </div>
                    <div class="order-page">
                        <p>Bạn chưa có đơn hàng nào</p>
                        <table class="table">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Thông tin</th>
                                    <th scope="col">Sản phẩm</th>
                                    <th scope="col" class="number">Số lượng</th>
                                    <th scope="col">Đơn giá</th>
                                    <th scope="col">Địa chỉ</th>
                                    <th scope="col">Tổng tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row">3</th>
                                    <td>Baccarat Rouge 540 EDP</td>
                                    <td><img src="/RESOURCES/images/products/baccarat540-600x600.png"></td>
                                    <td>10000</td>
                                    <td>633.300.000</td>
                                    <td>Đầu đường Phạm Thị Ban, cầu;ldfjsal;dfkjals;dfj;lsdjflk;flkjdfasdffáo Dẫn
                                        91b Phường Thới An Đông, Quận Bình Thủy, Cần Thơ</td>
                                    <td>63.000.000.000</td>
                                </tr>
                                <tr>
                                    <th scope="row">3</th>
                                    <td>Baccarat Rouge 540 EDP</td>
                                    <td><img src="/RESOURCES/images/products/baccarat540-600x600.png"></td>
                                    <td>10000</td>
                                    <td>6.300.000</td>
                                    <td>Đầu đường Phạm Thị Ban, cầu Giáo Dẫn 91b Phường Thới An Đông, Quận Bình
                                        Thủy, Cần Thơ</td>
                                    <td>63.000.000.000</td>
                                </tr>
                                <tr>
                                    <th scope="row">3</th>
                                    <td>Baccarat Rouge 540 EDP</td>
                                    <td><img src="/RESOURCES/images/products/baccarat540-600x600.png"></td>
                                    <td>10000</td>
                                    <td>6.300.000</td>
                                    <td>Đầu đường Phạm Thị Ban, cầu Giáo Dẫn 91b Phường Thới An Đông, Quận Bình
                                        Thủy, Cần Thơ</td>
                                    <td>63.000.000.000</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="address-page">
                        <p>Các địa chỉ bên dưới mặc định sẽ được sử dụng ở trang thanh toán sản phẩm.</p>
                        <div class="default">
                            <h3>Địa chỉ giao hàng mặc định</h3><a href="">
                                <h4>Sửa</h4>
                            </a>
                        </div>
                        <div class="address">
                            <p>Quốc Vương</p>
                            <p>0326344241</p>
                            <p>Đầu đường Phạm Thị Ban, cầu Giáo Dẫn 91b Phường Thới An Đông, Quận Bình Thủy, Cần Thơ
                            </p>
                        </div>
                    </div>
                    <div class="info-page">
                        <form action="/Client/Update/Info" method="POST">
                            <div class="fullname">
                                <div>
                                    <label>
                                        Họ & Tên *
                                    </label>
                                    <input type="text" value="<%= (fullname == null ? "" : fullname)%>" name="txtFullname">
                                </div>
                            </div>
                            <div class="display-name">
                                <label>Tên hiển thị *</label>
                                <input type="text" value="<%= (username == null ? "" : username)%>" name="txtUserName">
                            </div>
                            <div class="email">
                                <label>Địa chỉ email *</label>
                                <input type="text" value="<%= (email == null ? "" : email)%>" name="txtEmail">
                            </div>
                            <fieldset>
                                <legend>Thay đổi mật khẩu</legend>
                                <label for="pwdCurrent">Mật khẩu hiện tại (bỏ trống nếu không đổi)</label><br>
                                <input type="password" id="pwdCurrent" name="pwdCurrent"><br><br>
                                <label for="pwdNew">Mật khẩu mới (bỏ trống nếu không đổi)</label><br>
                                <input type="password" id="pwdNew" name="pwdNew"><br><br>
                                <label for="pwdConfirmNew">Xác nhận mật khẩu mới</label><br>
                                <input type="password" id="pwdConfirmNew" name="pwdConfirmNew"><br><br>
                            </fieldset>
                            <button type="submit" name="btnUpdateInfo" value="Submit">Lưu thay đổi</button>
                        </form>

                    </div>
                </div>
            </div>


            <div class="row">
                <div class="col-md-12 register">
                    <h1>Đăng ký thành viên để nhận khuyến mại</h1>
                    <p>Theo dõi chúng tôi để nhận thêm nhiều ưu đãi</p>
                    <form action="">
                        <input type="text" placeholder="nhập email">
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
                    <p>&copy; xxiv 2023 | all right reserved</p>
                </div>
            </div>

        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
        <script src="/RESOURCES/user/public/js/main.js"></script>
    </body>

</html>