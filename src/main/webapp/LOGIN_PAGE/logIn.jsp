<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%! boolean isAccountDeactivated, isAccountNotFound, isExistEmail;%>
<%
    // Handling execption
    isAccountDeactivated = (request.getParameter("errAccD") == null ? false : Boolean.parseBoolean(request.getParameter("errAccD")));
    isAccountNotFound = (request.getParameter("errAccNF") == null ? false : Boolean.parseBoolean(request.getParameter("errAccNF")));
    isExistEmail = (request.getParameter("errEmail") == null ? false : Boolean.parseBoolean(request.getParameter("errEmail")));
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
        <link rel="stylesheet" href="/RESOURCES/logIn/public/style/style.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
              rel="stylesheet" type="text/css" />
        <title>Tài khoản của tôi</title>
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

                <!--Execption Handling-->
                <c:choose>
                    <c:when test='<%= isAccountNotFound%>'>
                        <h1 class="alert alert-danger">
                            Sai tên đăng nhập hoặc mật khẩu
                        </h1>
                    </c:when>
                    <c:when test='<%= isAccountDeactivated%>'>
                        <h1 class="alert alert-danger">
                            Tài khoản đã bị vô hiệu hóa.
                        </h1>
                    </c:when>

                    <c:when test='<%= isExistEmail%>'>
                        <h1 class="alert alert-danger">
                            Email đã tồn tại
                        </h1>
                    </c:when>
                </c:choose>
                <!--Execption Handling-->

                <div class="col-md-12 login-form">
                    <div class="type">
                        <button type="button" id="signIn" class="<%= isExistEmail ? "" : "active"%>">Đăng nhập</button>
                        <button type="button" id="signUp" class="<%= isExistEmail ? "active" : ""%>">Đăng ký</button>
                    </div>

                    <form action="LogController" method="post" class="sign-in" id="signInForm">
                        <label for="txtUsername">Tên tài khoản hoặc địa chỉ email *</label>
                        <br>
                        <input type="text" name="txtUsername" id="txtUsername">
                        <br>
                        <label for="password-input">Mật khẩu *</label>
                        <br>
                        <input type="password" name="txtPassword" id="password-input">
                        <br>
                        <input type="checkbox" name="txtRememberPassword" id="remember-password"
                               class="remember-password">
                        <label for="remember-password">Ghi nhớ mật khẩu</label>
                        <br>
                        <button type="submit" name="submitBtn" value="submitLogin" class="enter">Đăng nhập</button>
                    </form>

                    <form action="LogController" method="post" class="sign-up" id="signUpForm">
                        <label for="user-input">Địa chỉ email *</label>
                        <br>
                        <input type="text" name="txtEmail" id="user-input">
                        <p>Một mật khẩu sẽ được gửi đến địa chỉ email của bạn.</p>
                        <br>
                        <button type="submit" name="submitBtn" value="submitRegister" class="enter">Đăng ký</button>
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
                    <p>&copy; xxiv 2023 | all rigth reserved</p>
                </div>
            </div>

        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <!--Jquery Validation-->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>

        <script src="/RESOURCES/logIn/public/js/main.js"></script>

        <script>

            if (window.performance && window.performance.navigation.type === window.performance.navigation.TYPE_BACK_FORWARD) {
                window.location.replace(window.location.href);
            }
            $(document).ready(function () {
                if (jQuery)
    //          alert('jQuery is loaded');
                    $("input[name='user']").on("keydown", function (e) {
                        var regex = /^[a-zA-Z0-9]+$/;
                        var key = String.fromCharCode(e.keyCode);
                        if (!regex.test(key)) {
                            e.preventDefault();
                        }
                    });

                // $("input[name='txtEmail']").on("keydown", function (e) {
                //     var regex = /^[a-zA-Z0-9]+$/;
                //     var key = String.fromCharCode(e.keyCode);
                //     if (!regex.test(key)) {
                //         e.preventDefault();
                //     }
                // });
            });
        </script>




        <script>
            $(document).ready(function () {
                $.validator.addMethod("emailCustom", function (value, element, toggler) {
                    if (toggler) {
                        let regex = /^[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+$/;
                        return regex.test(value);
                    }
                    return true;
                }, "Vui lòng nhập đúng định dạng email");

                $("#signInForm").validate({
                    rules: {
                        txtUsername: {
                            required: true
                        },
                        txtPassword: {
                            required: true
                        }
                    },
                    messages: {
                        txtUsername: {
                            required: "Vui lòng nhập tên đăng nhập hoặc email",
                        },
                        txtPassword: {
                            required: "Vui lòng nhập mật khẩu",
                        }
                    },

                    errorPlacement: function (error, element) {
                        error.addClass("text-danger d-block");
                        error.insertAfter(element.next());
                    }
                });

                $("#signUpForm").validate({
                    rules: {
                        txtEmail: {
                            required: true,
                            email: true
                        }
                    },
                    messages: {
                        txtEmail: {
                            required: "Vui lòng nhập email",
                            email: "Vui lòng nhập đúng định dạng email"
                        }
                    },

                    errorPlacement: function (error, element) {
                        error.addClass("text-danger d-block");
                        error.insertBefore(element.next());
                    }
                });

                function changeValidationMethod(element) {
                    let regex = /.*@.*/;
                    let value = $("input#txtUsername").val();
                    const hasEmailRule = $("#txtUsername").rules().emailCustom;
                    console.log('has rule:' + hasEmailRule);
                    console.log(value);
                    console.log('testing regex:' + regex.test(value));
                    if (regex.test(value))
                    {
                        if (!hasEmailRule) {
                            $("#txtUsername").rules("add", {
                                emailCustom: true
                            });
                        }
                    } else {
                        if (hasEmailRule) {
                            $("#txtUsername").rules("remove", "emailCustom");
                        }
                    }
                }

                $("button[type='submit']").on("click", function () {
                    changeValidationMethod("#txtUsername");
                });

            });
        </script>

    </body>

</html>