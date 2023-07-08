<%-- Document : newjsp Created on : Jul 5, 2023, 3:27:56 PM Author : Acer --%>

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
    OrderDAO od = new OrderDAO();
    List<Order> orders = od.getOrderByClientId(user.getID());

    if (user.getAddress() != null && user.getAddress().split(" - ").length == 3) {
        String Address[] = user.getAddress().split(" - ");
        Tinh = Address[0];
        QuanHuyen = Address[1];
        PhuongXa = Address[2];
    }
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
            <h1><%= user.getID()%></h1>
            <h1><%= user.getName()%></h1>
            <h1><%= user.getUsername()%></h1>
            <h1><%= user.getEmail()%></h1>
            <h1><%= user.getPassword()%></h1>
            <h1>|<%= Tinh%>|</h1>
            <h1>|<%= QuanHuyen%>|</h1>
            <h1>|<%= PhuongXa%>|</h1>

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
                        <c:choose>
                            <c:when test="<%=orders.size() == 0%>">
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
                                        <tr>
                                            <c:if test="<%=orders.size() != 0%>">
                                                <c:forEach var="i" begin="0" end="<%=orders.size() - 1%>">
                                                <tr>
                                                    <th scope="row"><%=orders.get((int) pageContext.getAttribute("i")).getID()%></th>
                                                    <td><%=orders.get((int) pageContext.getAttribute("i")).getDate()%></td>
                                                    <td><%=pDAO.IntegerToMoney(orders.get((int) pageContext.getAttribute("i")).getSum())%></td>
                                                    <td><a href="/Client/Order/Detail/ID/<%=orders.get((int) pageContext.getAttribute("i")).getID()%>" target="_blank">Xem chi tiết</a></td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                        </tr>
                                    </tbody>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="address-page">
                        <p>Các địa chỉ bên dưới mặc định sẽ được sử dụng ở trang thanh toán sản phẩm.</p>
                        <div class="default">

                            <!-- Add new Form. Maybe Change later-->
                            <form action="/Client/Update/Address" method="POST">
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
                                    <input style="width:100%" type="text" name="txtPhoneNumber" id="txtPhoneNumber" value="<%= user.getPhoneNumber()%>"> 
                                </div>
                                <input  type="hidden" name="txtAddress" id="txtAddress" value="" >
                                <button type="submit" name="btnUpdateAdress" value="Submit"> <h4>Sửa</h4> </button>
                            </form>
                            <!--  Add new Form. Maybe Change later-->

                        </div>

                        <div class="address d-none">
                            <p><%= user.getName()%></p>
                            <p><%= user.getPhoneNumber()%></p>
                            <p><%= user.getAddress()%></p>
                        </div>

                    </div>
                    <div class="info-page">


                        <!--  Form Update Client account -->
                        <form action="/Client/Update/Info" method="POST" id="formUpdateAccount">
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
                        <!--  Form Update Client account -->


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


        <!--VietName Province APU-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <script >
            const host = "https://provinces.open-api.vn/api/";
            let City = '<%= Tinh%>';
            let District = '<%= QuanHuyen%>';
            let Ward = '<%= PhuongXa%>';
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
                            renderData(response.data.districts, "district", "Chọn quận huyện");
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
                            renderData(response.data.wards, "ward", "Chọn phường xã");
                            if (Ward !== "")
                            {
                                $(`select option[value='` + Ward + `']`).prop("selected", true);
                            }
                        });
            };
            var renderData = (array, select, msg = "Chọn") => {
                let row = ' <option disable value="">' + msg + '</option>';
                array.forEach((e) => {
                    let code = e.code;
                    let name = e.name;
                    row += `<option data-id="` + code + `" value="` + name + `">` + name + `</option>`;
                });
                document.querySelector("#" + select).innerHTML = row;
            };

            function resetData(select, msg = "Chọn") {
                let row = '<option disable value="">' + msg + '</option>';
                document.querySelector("#" + select).innerHTML = row;
            }


            $("#city").change(() => {
                resetData("district", "Chọn quận huyện");
                resetData("ward", "Chọn phường xã");
                callApiDistrict(host + "p/" + $("#city").find(':selected').data('id') + "?depth=2");
                printResult();
            });
            $("#district").change(() => {
                resetData("ward", "Chọn phường xã");
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
                    let result = (city === Default ? "" : city);
                    result += (district === Default ? "" : sp + district);
                    result += (ward === Default ? "" : sp + ward);
                    $("#result").text(result);
                    $("#txtAddress").val(result);
                }
            };
        </script>

        <!--Jquery Validation-->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
        <script>
            $().ready(function () {
                $.validator.addMethod("regex", function (value, element, regex) {
                    return !regex.test(value);
                }, "No special character please.");

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
                        },
                        pwdNew: {
                            minlength: 6
                        },
                        pwdConfirmNew: {
                            equalto: "#pwdNew"
                        }

                    }
                });
            });
        </script>
    </body>

</html>