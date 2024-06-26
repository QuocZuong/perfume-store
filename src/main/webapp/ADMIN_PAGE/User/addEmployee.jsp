<%@page import="Lib.ExceptionUtils"%>
<%@page import="Models.Employee"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! String Tinh, QuanHuyen, PhuongXa;%>

<%    // Handling execption
    String queryString = request.getQueryString();
    boolean isErr = ExceptionUtils.isWebsiteError(queryString);
    String exeptionMessage = ExceptionUtils.getMessageFromExceptionQueryString(queryString);
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
            crossorigin="anonymous"
            />
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond&family=Josefin+Sans:wght@200&family=Josefin+Slab&display=swap"
            rel="stylesheet"
            />
        <link
            href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
            rel="stylesheet"
            type="text/css"
            />

        <!--Custom Style-->
        <link rel="stylesheet" href="/RESOURCES/admin/user/public/style/update.css" />
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp" />
        <script src="/RESOURCES/plugin/jquery-3.7.1.min.js"></script>
        <script src="/RESOURCES/plugin/jquery-validation-1.19.5/dist/jquery.validate.min.js"></script>
        <style>
            #preview-img {
                width: 20%;
                height: 20%;
            }
            .alert {
                z-index: -99 !important;
            }

            span.arrow {
                margin-left: 6px;
                height: 17px;
            }
            label.error {
                color: red;
                margin-top: 5px;
                height: 17px;
                width: fit-content;
            }
        </style>

        <title>Thêm nhân viên</title>
    </head>
    <body>
        <div class="container-fluid">
            <!--Navbar section-->
            <div class="row">
                <div class="col-md-12 nav">
                    <jsp:include page="/NAVBAR/AdminNavbar.jsp"></jsp:include>
                    </div>
                </div>

                <div class="row">
                    <!--Execption Handling-->
                <c:if test="<%=isErr%>">
                    <h1 class="alert alert-danger text-center"><%=exeptionMessage%></h1>
                </c:if>
                <!--Execption Handling-->
            </div>
            <div class="container mt-5">
                <div class="row">
                    <h1>Add Employee</h1>
                    <form action="/Admin/User/Add/Employee" method="POST" id="addEmployee">
                        <div class="name">
                            <label>Name *</label>
                            <input type="text" name="txtName">
                        </div>
                        <div class="username">
                            <label>Username *</label>
                            <input type="text" name="txtUsername" />
                        </div>
                        <div class="password">
                            <label>Password *</label>
                            <input type="password" name="txtPassword" />
                        </div>
                        <div class="email">
                            <label>Email *</label>
                            <input type="text" name="txtEmail"/>
                        </div>
                        <div class="role">
                            <label>Role *</label>
                            <select name="txtRole" class="roleSelect py-2">
                                <option value="1">Admin</option>
                                <option value="2" selected>Order Manager</option>
                                <option value="3">Inventory Manager</option>
                            </select>
                        </div>
                        <div class="citizenId">
                            <label>Citizen id *</label>
                            <input type="text" name="txtCitizenId" />
                        </div>
                        <div class="dateOfBirth">
                            <label>Date of birth *</label>
                            <input type="date" name="txtDOB"/>
                        </div>
                        <div class="phone">
                            <label>Phone Number *</label>
                            <input type="text" name="txtPhoneNumber">
                        </div>
                        <div class="address">
                            <label>Address *</label>
                            <select id="city" class="form-select">
                                <option value="" selected>Chọn tỉnh thành</option>
                            </select>
                            <br />
                            <select id="district" class="form-select">
                                <option value="" selected>Chọn quận huyện</option>
                            </select>
                            <br />
                            <select id="ward" class="form-select">
                                <option value="" selected>Chọn phường xã</option>
                            </select>
                            <br />
                            <input
                                type="text"
                                name="txtAddress"
                                id="txtAddress"
                                readonly="true"
                                />
                        </div>
                        <div class="joinDate">
                            <label>Join date *</label>
                            <input type="date" name="txtJoinDate"/>
                        </div>
                        <button type="submit" name="btnAddEmployee" value="Submit" class="btnAddEmployee mb-3">
                            Add Employee
                        </button>
                        <br />
                    </form>
                </div>
            </div>
        </div>

        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"
        ></script>

        <script src="/RESOURCES/admin/user/public/js/update.js"></script>

        <!--VietName Province APU-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
        <script src="/RESOURCES/user/public/js/addressAPI.js" data-province="<%= Tinh %>" data-district="<%= QuanHuyen %>" data-ward="<%= PhuongXa %>"></script>

        <!--Jquery Validation-->

        <script>
            $(document).ready(function () {
                //                $.validator.addMethod("regex", function (value, regex) {
                //                    return regex.test(value);
                //                }, "Wrong input.");
                $.validator.addMethod("atLeastOneLetter", function (value, element) {
                    // Use a regular expression to check if the value contains at least one alphabet letter
                    return /[A-Za-z]/.test(value);
                }, "Phải có ký tự chữ cái.");

                $("#addEmployee").validate({
                    rules: {
                        txtName: {
                            required: true,
                            maxlength: 50,
                            atLeastOneLetter: true
                        },
                        txtUsername: {
                            required: true,
                            maxlength: 50,
                            atLeastOneLetter: true
                        },
                        txtPassword: {
                            required: true,
                            minlength: 6
                        },
                        txtEmail: {
                            required: true,
                            email: true,
                            maxlength: 100
                        },
                        txtCitizenId: {
                            required: true,
                            digits: true,
                            minlength: 9,
                            maxlength: 20
                        },
                        txtDOB: {
                            required: true
                        },
                        txtPhoneNumber: {
                            required: true,
                            digits: true,
                            maxlength: 10,
                            minlength: 10
                        },
                        txtAddress: {
                            required:true,
                            maxlength: 500
                        },
                        txtJoinDate: {
                            required: true
                        }
                    },
                    messages: {
                        txtName: {
                            required: "Tên không được để trống.",
                            maxlength: "Tên không được vượt quá 50 ký tự.",
                            atLeastOneLetter: "Tên không hợp lệ."

                        },
                        txtUsername: {
                            required: "Tên đăng nhập không được để trống.",
                            maxlength: "Tên đăng nhập không được vượt quá 50 ký tự.",
                            atLeastOneLetter: "Username không hợp lệ."

                        },
                        txtPassword: {
                            required: "Mật khẩu không được để trống.",
                            minlength: "Mật khẩu phải có ít nhất 6 ký tự.",
                        },
                        txtEmail: {
                            required: "Email không được để trống.",
                            email: "Email không hợp lệ.",
                            maxlength: "Email không được vượt quá 100 ký tự.",
                        },
                        txtCitizenId: {
                            required: "Số CMND không được để trống.",
                            digits: "Số CMND không hợp lệ",
                            minlength: "Số CMND phải có ít nhất 9 ký tự.",
                            maxlength: "Số CMND không được vượt quá 20 ký tự."
                        },
                        txtDOB: {
                            required: "Ngày tháng năm sinh không được để trống",
                        },
                        txtPhoneNumber: {
                            required: "Số điện thoại không được để trống",
                            digits: "Số điện thoại không hợp lệ",
                            maxlength: "Số điện thoại phải là 10 chữ số",
                            minlength: "Số điện thoại phải là 10 chữ số",
                        },
                        txtAddress: {
                            required: "Địa chỉ không được để trống",
                            maxlength: "Địa chỉ không được vượt quá 500 ký tự.",
                        },
                        txtJoinDate: {
                            required: "Ngày tham gia không được để trống.",
                        },
                    },
                });
            });
        </script>
    </body>
</html>
