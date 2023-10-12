<%@page import="Lib.ExceptionUtils"%>
<%@page import="Lib.Converter"%>
<%@page import="DAOs.CustomerDAO"%>
<%@page import="Models.Customer"%>
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
<%! CustomerDAO cusDAO = new CustomerDAO(); %>
<%! OrderDAO od = new OrderDAO();%>
<%! List<Order> orders = null; %>
<%! Customer customer = null; %>
<%! Cookie currentUserCookie = null;%>
<%! String fullname, username, email, Tinh = "", QuanHuyen = "", PhuongXa = "";%>

<%! boolean isError; %>
<%! String exceptionMessage;%>
<%! boolean isUpdateAccountExecption;%>
<%! boolean isUpdateAddressExecption;%>

<%

    currentUserCookie = (Cookie) pageContext.getAttribute("userCookie", pageContext.SESSION_SCOPE);
    customer = cusDAO.getCustomer(currentUserCookie.getValue());
    fullname = customer.getName();
    username = customer.getUsername();
    email = customer.getEmail();
    orders = od.getOrderByCustomerId(customer.getCustomerId());

//    if (user.getAddress() != null && user.getAddress().split(" - ").length == 3) {
//        String Address[] = user.getAddress().split(" - ");
//        Tinh = Address[0];
//        QuanHuyen = Address[1];
//        PhuongXa = Address[2];
//    }

    // Handling exception
    String queryString = request.getQueryString();
    isError = ExceptionUtils.isWebsiteError(queryString);
    exceptionMessage = ExceptionUtils.getMessageFromExceptionQueryString(queryString);
    isUpdateAccountExecption
            = ExceptionUtils.isAccountNotFound(queryString)
            || ExceptionUtils.isAccountDeactivated(queryString)
            || ExceptionUtils.isEmailDuplication(queryString)
            || ExceptionUtils.isUsernameDuplication(queryString);

    isUpdateAddressExecption
            = ExceptionUtils.isPhoneNumberDuplication(queryString)
            || ExceptionUtils.isNotEnoughInformation(queryString);
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
                    <jsp:include page="/NAVBAR/ClientNavbar.jsp"></jsp:include>
                    </div>
                </div>


                <div class="main">
                    <div class="left">
                        <h1>Tài khoản của tôi</h1>
                        <div class="list">
                            <ul>
                                <li><a class="<%= isUpdateAccountExecption || isUpdateAddressExecption ? "" : "active"%>">Trang tài khoản</a></li>
                            <li><a class="">Đơn hàng</a></li>
                            <li><a class="<%= isUpdateAddressExecption ? "active" : ""%>">Địa chỉ</a></li>
                            <li><a class="<%= isUpdateAccountExecption ? "active" : ""%>">Tài khoản</a></li>
                            <li><a href="/Log/Logout">Đăng xuất</a></li>
                        </ul>
                    </div>
                </div>
                <div class="right">

                    <!--  ==================================Account PAGE ==================================== -->

                    <div class="account-page">
                        <p>Xin chào <b><strong><%=  (fullname != null && !fullname.isEmpty()) ? fullname : username%></strong></b> (không phải tài khoản
                            <b><strong><%=  (fullname != null && !fullname.isEmpty()) ? fullname : username%></strong></b>? Hãy <a href="/Log/Logout">thoát ra</a> và đăng nhập vào tài
                            khoản của bạn)</p>
                        <p>
                            Từ trang quản lý tài khoản bạn có thể xem
                            <a  class="account-link" data-page="order-page">đơn hàng mới</a>,
                            quản lý <a  class="account-link" data-page="address-page">địa chỉ giao hàng và thanh toán</a>,
                            và <a  class="account-link" data-page="info-page">sửa mật khẩu và thông tin tài khoản</a>.
                        </p>
                    </div>
                    <!--  ==================================Order PAGE ==================================== -->
                    <div class="order-page">
                        <c:choose>
                            <c:when test='<%=orders.size() == 0%>'>
                                <p>Bạn chưa có đơn hàng nào</p>
                            </c:when>

                            <c:otherwise>
                                <table class="table">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Ngày mua hàng</th>
                                            <th scope="col">Tổng tiền</th>
                                            <th scope="col"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test='<%=orders.size() != 0%>'>
                                            <c:forEach var="i" begin="0" end="<%=orders.size() - 1%>">
                                                <tr>
                                                    <th scope="row"><%=orders.get((int) pageContext.getAttribute("i")).getId()%></th>
                                                    <td><%=orders.get((int) pageContext.getAttribute("i")).getCreatedAt()%></td>
                                                    <td><%=Converter.covertIntergerToMoney(orders.get((int) pageContext.getAttribute("i")).getTotal())%></td>
                                                    <td><a href="/Customer/Order/Detail/ID/<%=orders.get((int) pageContext.getAttribute("i")).getId()%>" target="_blank">Xem chi tiết</a></td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                    </tbody>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!--  ==================================Adrdress PAGE ==================================== -->
                    <div class="address-page">
                        <p>Các địa chỉ bên dưới mặc định sẽ được sử dụng ở trang thanh toán sản phẩm.</p>


                        <!--Execption Handling-->
                        <c:if test='<%= isError%>'>
                            <h2 class="alert alert-danger text-center">
                                <%= exceptionMessage%>
                            </h2>
                        </c:if>
                        <!--Execption Handling-->
                        <div class="default">

                            <!-- Add new Form. Maybe Change later-->
                            <form action="/Customer/Update/Address" method="POST" id="form-address-update">
                                <h3>Địa chỉ giao hàng mặc định</h3>

                                <div class="d-flex flex-column gap-2 mb-2">
                                    <select id="city" class="form-select">
                                        <option value="" selected>Chọn tỉnh thành</option>           
                                    </select>

                                    <select id="district" class="form-select">
                                        <option value="" selected>Chọn quận huyện</option>
                                    </select>

                                    <select id="ward" class="form-select">
                                        <option value="" selected>Chọn phường xã</option>
                                    </select>
                                    <input type="text" name="txtAddress" id="txtAddress" readonly="true" value="" placeholder="Địa chỉ">
                                    <input style="width:100%" type="text" name="txtPhoneNumber" id="txtPhoneNumber" value="" placeholder="Số điện thoại">
                                </div>
                                <button type="submit" name="btnUpdateAdress" value="Submit"> <h4>Sửa</h4> </button>
                            </form>
                            <!--  Add new Form. Maybe Change later-->

                        </div>

                        <div class="address d-none">
                            <p><%= customer.getName()%></p>
                            get Phone and address data here
                        </div>

                    </div>
                    <!--  ==================================Info PAGE ==================================== -->
                    <div class="info-page">


                        <!--Execption Handling-->
                        <c:if test='<%= isError%>'>
                            <h2 class="alert alert-danger text-center">
                                <%= exceptionMessage%>
                            </h2>
                        </c:if>
                        <!--Execption Handling-->

                        <!--  Form Update Client account -->

                        <form action="/Customer/Update/Info" method="POST" id="formUpdateAccount">
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
                                <input type="password" id="pwdCurrent" name="pwdCurrent" ><br><br>
                                <label for="pwdNew">Mật khẩu mới (bỏ trống nếu không đổi)</label><br>
                                <input type="password" id="pwdNew" name="pwdNew" disabled="true"><br><br>
                                <label for="pwdConfirmNew">Xác nhận mật khẩu mới</label><br>
                                <input type="password" id="pwdConfirmNew" name="pwdConfirmNew" disabled="true"><br><br>
                            </fieldset>
                            <button type="submit" name="btnUpdateInfo" value="Submit">Lưu thay đổi</button>
                        </form>
                        <!--  Form Update Client account -->


                    </div>
                </div>
            </div>

            <!--  ================================== Other informaton  ==================================== -->
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

        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>

        <script src="/RESOURCES/user/public/js/main.js"></script>


        <!--VietName Province APU-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <script >
            const host = "https://provinces.open-api.vn/api/";
            let City = '<%= Tinh%>';
            let District = '<%= QuanHuyen%>';
            let Ward = '<%= PhuongXa%>';
            let DefaultCity = 'Chọn tỉnh thành';
            let DefaultDistrict = 'Chọn quận huyện';
            let DefaultWard = 'Chọn phường xã';
            var callAPI = (api) => {
                return axios.get(api)
                        .then((response) => {
                            renderData(response.data, "city");
                            if (City !== "")
                            {
                                $(`select option[value='` + City + `']`).prop("selected", true);
                                callApiDistrict(host + "p/" + $("#city").find(':selected').data('id') + "?depth=2");
                            }
                        });
            };
            callAPI('https://provinces.open-api.vn/api/?depth=1');
            var callApiDistrict = (api) => {
                return axios.get(api)
                        .then((response) => {
                            renderData(response.data.districts, "district", DefaultDistrict);
                            if (District !== "")
                            {
                                $(`select option[value='` + District + `']`).prop("selected", true);
                                callApiWard(host + "d/" + $("#district").find(':selected').data('id') + "?depth=2");
                            }
                        });
            };
            var callApiWard = (api) => {
                return axios.get(api)
                        .then((response) => {
                            renderData(response.data.wards, "ward", DefaultWard);
                            if (Ward !== "")
                            {
                                $(`select option[value='` + Ward + `']`).prop("selected", true);
                            }
                        });
            };
            var renderData = (array, select, msg = DefaultCity) => {
                let row = ' <option disable value="">' + msg + '</option>';
                array.forEach((e) => {
                    let code = e.code;
                    let name = e.name;
                    row += `<option data-id="` + code + `" value="` + name + `">` + name + `</option>`;
                });
                document.querySelector("#" + select).innerHTML = row;
            };

            function resetData(select, msg = DefaultCity) {
                let row = '<option disable value="">' + msg + '</option>';
                document.querySelector("#" + select).innerHTML = row;
            }


            $("#city").change(() => {
                resetData("district", DefaultDistrict);
                resetData("ward", DefaultWard);
                callApiDistrict(host + "p/" + $("#city").find(':selected').data('id') + "?depth=2");
                printResult();
            });
            $("#district").change(() => {
                resetData("ward", DefaultWard);
                callApiWard(host + "d/" + $("#district").find(':selected').data('id') + "?depth=2");
                printResult();
            });
            $("#ward").change(() => {
                printResult();
            });
            var printResult = () => {
                if ($("#district").find(':selected').data('id') != "" && $("#city").find(':selected').data('id') != "" &&
                        $("#ward").find(':selected').data('id') != "") {

                    let city = $("#city option:selected").text();
                    let district = $("#district option:selected").text();
                    let ward = $("#ward option:selected").text();
                    let sp = " - ";
                    let result = (city === DefaultCity ? "" : city);
                    result += (district === DefaultDistrict ? "" : sp + district);
                    result += (ward === DefaultWard ? "" : sp + ward);
                    if (city !== DefaultCity && district !== DefaultDistrict && ward !== DefaultWard) {
                        $("#result").text(result);
                        console.log("update value success");
                        $("input#txtAddress").val(result);
                    } else {
                        $("#result").text("");
                        console.log("update value null");
                        $("input#txtAddress").val("");
                    }
                }
            };
        </script>


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


            $(document).ready(function () {
                $.validator.addMethod("regex", function (value, element, regex) {
                    return regex.test(value);
                }, "Wrong input.");

                $("#form-address-update").validate({
                    rules: {
                        txtPhoneNumber: {
                            regex: /(^$|^0[1-9][\d]{8}$)/
                        }
                    },

                    messages: {
                        txtPhoneNumber: {
                            regex: "Số điện thoại không hợp lệ."
                        }
                    }
                });
            });

        </script>

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