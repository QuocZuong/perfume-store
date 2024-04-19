<%@page import="java.util.Calendar"%>
<%@page import="Lib.ExceptionUtils"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="Models.Order"%>
<%@page import="java.util.List"%>
<%@page import="DAOs.OrderDAO"%>
<%@page import="Models.User"%>
<%@page import="DAOs.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%! ProductDAO pDAO = new ProductDAO();  %>
<%! UserDAO usDAO = new UserDAO();%>
<%!String fullname, username, email, Tinh = "", QuanHuyen = "", PhuongXa = "";%>

<%! boolean isUpdateAccountExeception;%>

<%
    Cookie currentUserCookie = (Cookie) pageContext.getAttribute("userCookie", pageContext.SESSION_SCOPE);
    User user = usDAO.getUser(currentUserCookie.getValue());
    fullname = user.getName();
    username = user.getUsername();
    email = user.getEmail();

    // Handling exception
    String queryString = request.getQueryString();
    boolean isError = ExceptionUtils.isWebsiteError(queryString);
    String exceptionMessage = ExceptionUtils.getMessageFromExceptionQueryString(queryString);


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
        <link rel="stylesheet" href="/RESOURCES/admin/user/public/style/style.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
              rel="stylesheet" type="text/css" />
        <title>Tài khoản</title>
        <style>
            .alert{
                z-index:-99 !important;
            }
        </style>
    </head>

    <body>


        <div class="container-fluid">
            <!--Navbar section-->
            <div class="row">
                <div class="col-md-12 nav">
                    <jsp:include page="/NAVBAR/OrderManagerNavbar.jsp"></jsp:include>
                    </div>
                </div>

                <div class="main">
                    <div class="left">
                        <h1>Tài khoản của tôi</h1>
                        <div class="list">
                            <ul>
                                <li><a class="<%= isError ? "" : "active"%>">Trang tài khoản</a></li>
                            <li><a>Quản lí</a></li>
                            <li><a class="<%= isError ? "active" : ""%>">Tài khoản</a></li>
                            <li><a href="/Log/Logout">Đăng xuất</a></li>
                        </ul>
                    </div>
                </div>
                <div class="right">
                    <div class="account-page">
                        <p>Xin chào <b><strong><%=  (user.getName() != null && !user.getName().isEmpty()) ? user.getName() : user.getUsername()%></strong></b> (không phải tài khoản <b><strong><%=  (user.getName() != null && !user.getName().isEmpty()) ? user.getName() : user.getUsername()%></strong></b>? Hãy <a href="/Log/Logout">thoát ra</a> và đăng nhập vào tài khoản của bạn)</p>
                        <p>
                            Từ trang quản lý tài khoản bạn có thể  <button class="account-link text-primary border-0 bg-transparent" id="btn-management" data-page="manager-page">quản lý dữ liệu hệ thống</button><br> và <button class="account-link text-primary border-0 bg-transparent"  data-page="info-page">sửa mật khẩu và thông tin
                                tài khoản</button>.
                        </p>
                    </div>
                    <div class="manager-page">
                        <div class="d-flex flex-wrap h-100 gap-5">
                            <div class="w-25 h-25">
                                <a href="/OrderManager/Order/List" class="text-decoration-none">
                                    <button class="btn btn-outline-dark d-flex w-100 h-100 justify-content-center align-items-center rounded-0 fn-btn">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
                                        <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"/>
                                        </svg>
                                        <span class="h2">&nbsp;&nbsp;Quản lí<br>&nbsp;&nbsp;đơn hàng</span>
                                    </button>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="info-page">

                        <!--Execption Handling-->
                        <c:if test='<%= isError%>'>
                            <h1 class="alert alert-danger text-center">
                                <%= exceptionMessage%>
                            </h1>
                        </c:if>
                        <!--Execption Handling-->

                        <form action="/OrderManager/Update/Info" method="POST" id="formUpdateAccount">
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
                                <input type="password" id="pwdNew" name="pwdNew" disabled><br><br>
                                <label for="pwdConfirmNew">Xác nhận mật khẩu mới</label><br>
                                <input type="password" id="pwdConfirmNew" name="pwdConfirmNew" disabled><br><br>
                            </fieldset>
                            <button type="submit" name="btnUpdateInfo" value="Submit">Lưu thay đổi</button>
                        </form>

                    </div>
                </div>
            </div>

        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <!-- For changing tag -->
        <script src="/RESOURCES/admin/user/public/js/main.js"></script>


        <!--Jquery Validation-->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
        <script>
            $(document).ready(function () {
                $.validator.addMethod("regex", function (value, element, regex) {
                    return regex.test(value);
                }, "Mật khẩu phải có ít nhất 6 kí tự.");

                $("#formUpdateAccount").validate({
                    rules: {
                        txtFullname: {
                            maxlength: 50
                        },
                        txtUserName: {
                            required: true,
                            maxlength: 50
                        },
                        txtEmail: {
                            required: true,
                            email: true
                        }
                    },
                    messages: {
                        txtFullname: {
                            maxlength: "Họ và tên không được vượt quá 50 kí tự."
                        },
                        txtUserName: {
                            required: "Tên hiển thị không được để trống.",
                            maxlength: "Tên hiển thị không được vượt quá 50 kí tự."
                        },
                        txtEmail: {
                            required: "Email không được để trống.",
                            email: "Email không hợp lệ."
                        }
                    },

                });
            });

            function requirechange(element) {
                console.log("Value: " + $("input#pwdCurrent").val() + " |" + ($("input#pwdCurrent").val() !== ""));
                if ($("input#pwdCurrent").val() !== "")
                {
                    $("input#pwdNew").prop("disabled", false);
                    $("input#pwdConfirmNew").prop("disabled", false);

                    $("input#pwdCurrent").rules("add", {
                        required: true,
                        regex: /^.{6,}$/,
                        messages: {
                            required: "Vui lòng nhập mật khẩu hiện tại",
                            regex: "Mật khẩu phải có ít nhất 6 kí tự."
                        }
                    });
                    $("input#pwdNew").rules("add", {
                        required: true,
                        regex: /^.{6,}$/,
                        messages: {
                            required: "Vui lòng nhập mật khẩu mới",
                            regex: "Mật khẩu phải có ít nhất 6 kí tự."
                        }
                    });
                    $("input#pwdConfirmNew").rules("add", {
                        required: true,
                        regex: /^.{6,}$/,
                        equalTo: "input#pwdNew",
                        messages: {
                            required: "Vui lòng nhập lại mật khẩu mới",
                            regex: "Mật khẩu phải có ít nhất 6 kí tự.",
                            equalTo: "Mật khẩu mới không khớp. Vui lòng nhập lại."
                        }
                    });
                } else {
                    $("input#pwdNew").prop("disabled", true);
                    $("input#pwdConfirmNew").prop("disabled", true);

                    $("input#pwdNew").val("");
                    $("input#pwdConfirmNew").val("");

                    //Remove all rules
                    $("input#pwdCurrent").rules("remove");
                    $("input#pwdNew").rules("remove");
                    $("input#pwdConfirmNew").rules("remove");
                }
            }

            $("input#pwdCurrent").on("input", function () {
                requirechange("input#pwdCurrent");
            });

            $("input#pwdNew").on("input", function () {
                requirechange("input#pwdNew");
            });

            $("input#pwdConfirmNew").on("input", function () {
                requirechange("input#pwdConfirmNew");
            });
        </script>
    </body>

</html>