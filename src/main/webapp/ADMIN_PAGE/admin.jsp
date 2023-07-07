<%@page import="DAOs.ProductDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.sql.ResultSet"%>
<%@page import="Models.Order"%>
<%@page import="java.util.List"%>
<%@page import="DAOs.OrderDAO"%>
<%@page import="Models.User"%>
<%@page import="DAOs.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%! ProductDAO pDAO = new ProductDAO();  %>
<%! UserDAO usDAO = new UserDAO();%>
<%!String fullname, username, email, Tinh = "", QuanHuyen = "", PhuongXa = "";%>
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
    <link rel="stylesheet" href="/RESOURCES/admin/public/style/style.css">
    <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
    <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
          rel="stylesheet" type="text/css" />
    <title>Tài khoản</title>
  </head>

  <body>


    <div class="container-fluid">
      <h1><%= user.getID()%></h1>
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
              <li><a>Quản lí</a></li>
              <li><a>Tài khoản</a></li>
              <li><a href="/Log/Logout">Đăng xuất</a></li>
            </ul>
          </div>
        </div>
        <div class="right">
          <div class="account-page">
            <p>Xin chào <b><strong><%= fullname%></strong></b> (không phải tài khoản
              <b><strong><%= fullname%></strong></b>? Hãy <a href="">thoát ra</a> và đăng nhập vào tài
              khoản của bạn)</p>
            <p>
              Từ trang quản lý tài khoản bạn có thể  <a href="">quản lý dữ liệu hệ thống</a> và <a href="">sửa mật khẩu và thông tin
                tài khoản</a>.
            </p>
          </div>
          <div class="manager-page">
            <div class="d-flex flex-wrap h-100 gap-5">
              <div class="w-25 h-25">
                <a href="/Admin/Add" class="text-decoration-none">
                  <button class="btn btn-outline-dark d-flex w-100 h-100 justify-content-center align-items-center rounded-0">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bag-plus-fill" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M10.5 3.5a2.5 2.5 0 0 0-5 0V4h5v-.5zm1 0V4H15v10a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4h3.5v-.5a3.5 3.5 0 1 1 7 0zM8.5 8a.5.5 0 0 0-1 0v1.5H6a.5.5 0 0 0 0 1h1.5V12a.5.5 0 0 0 1 0v-1.5H10a.5.5 0 0 0 0-1H8.5V8z"/>
                    </svg>
                    <span class="h2">&nbsp;&nbsp;Thêm sản phẩm</span>
                  </button>
                </a>
              </div>

              <div class="w-25 h-25">
                <a href="/Admin/List" class="text-decoration-none">
                  <button class="btn btn-outline-dark d-flex w-100 h-100 justify-content-center align-items-center rounded-0">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bag-plus-fill" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M10.5 3.5a2.5 2.5 0 0 0-5 0V4h5v-.5zm1 0V4H15v10a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4h3.5v-.5a3.5 3.5 0 1 1 7 0zM8.5 8a.5.5 0 0 0-1 0v1.5H6a.5.5 0 0 0 0 1h1.5V12a.5.5 0 0 0 1 0v-1.5H10a.5.5 0 0 0 0-1H8.5V8z"/>
                    </svg>
                    <span class="h2">&nbsp;&nbsp;Danh sách<br>&nbsp;&nbsp;sản phẩm</span>
                  </button>
                </a>
              </div>

              <div class="w-25 h-25">
                <a href="#" class="text-decoration-none">
                  <button class="btn btn-outline-dark d-flex w-100 h-100 justify-content-center align-items-center rounded-0">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
                    <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"/>
                    </svg>
                    <span class="h2">&nbsp;&nbsp;Quản lí<br>&nbsp;&nbsp;khách hàng</span>
                  </button>
                </a>
              </div>

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


      <div class="row invisible">
        <div class="col-md-12 register">
          <h1>Đăng ký thành viên để nhận khuyến mại</h1>
          <p>Theo dõi chúng tôi để nhận thêm nhiều ưu đãi</p>
          <form action="">
            <input type="text" placeholder="nhập email">
            <button>ĐĂNG KÝ</button>
          </form>
        </div>
      </div>

      <div class="row invisible">
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

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

    <script src="/RESOURCES/admin/public/js/main.js"></script>
  </body>

</html>