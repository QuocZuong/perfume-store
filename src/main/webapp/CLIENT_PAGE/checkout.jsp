<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@page import="Models.User"%>
<%@page import="DAOs.UserDAO"%>

<%! UserDAO usDAO = new UserDAO();%>
<%! String Tinh = "", QuanHuyen = "", PhuongXa = "";%>
<%
    Cookie currentUserCookie = (Cookie) pageContext.getAttribute("userCookie", pageContext.SESSION_SCOPE);
    User user = usDAO.getUser(currentUserCookie.getValue());
    String fullname = user.getName();
    String username = user.getUsername();
    String email = user.getEmail();
    String phone = user.getPhoneNumber();
    String address = user.getAddress();

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
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous" />
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond&family=Josefin+Sans:wght@200&family=Josefin+Slab&display=swap"
            rel="stylesheet" />
        <link rel="stylesheet" href="/RESOURCES/checkout/public/style/style.css" />
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp" />
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css" rel="stylesheet"
              type="text/css" />
        <title>Checkout</title>
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
                    <a href="/"><img src="/RESOURCES/images/icons/icon.webp" alt="" height="64"></a>
                    <div class="account">
                        <a><img src="/RESOURCES/images/icons/search.png" alt=""></a>
                        <a href="/Log/Login"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
                        <a href="/Client/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="main">
                    <div class="box">

                        <form action="/Client/Checkout" method="POST">

                            <div class="left">
                                <h1 class="mb-4">Checkout</h1>
                                <hr>
                                <div class="d-flex flex-row">
                                    <div class="d-flex flex-column col-5">
                                        <label><input type="radio" name="rdoCustomAddress" value="no" class="form-check-input" checked=""> Sử
                                            dụng địa chỉ mặc định</label>
                                        <label><input type="radio" name="rdoCustomAddress" value="yes" class="form-check-input"> Địa chỉ giao
                                            hàng khác</label>
                                    </div>

                                    <div class="d-flex flex-column ms-5">
                                        <label for="txtNote" class="d-block w-100">Ghi chú đơn hàng (tuỳ chọn)</label>
                                        <textarea name="txtNote" cols="60" rows="2" class="border-1"
                                                  placeholder="Ghi chú về đơn hàng, ví dụ: thời gian hay chỉ dẫn địa điểm giao hàng chi tiết hơn."></textarea>
                                    </div>
                                </div>

                            </div>

                            <div class="right">
                                <div class="checkout">

                                    <h2 class="text-start">Thông tin thanh toán</h2>

                                    <div class="d-flex flex-column flex-wrap w-100">
                                        <input type="text" name="txtUsername" value="<%= username%>" placeholder="Tên người dùng">
                                        <input type="text" name="txtEmail" value="<%= email%>" placeholder="Email">
                                        <input type="text" name="txtPhone" id="phone" class="" value="<%= phone%>" placeholder="Số điện thoại">
                                        <input type="text" name="txtAddress" value="<%= address%>" placeholder="Địa chỉ">
                                    </div>

                                    <h2 class="text-start mt-2 mb-0 mt-5 hidden" id="header-address">giao đến địa chỉ</h2>

                                    <div class="d-flex flex-column flex-wrap w-100 mt-2">

                                        <div class="w-100 d-flex">
                                            <select id="city" class="hidden form-select">
                                                <option value="" selected>Chọn tỉnh thành</option>           
                                            </select>
                                            <select id="district" class="hidden form-select">
                                                <option value="" selected>Chọn quận huyện</option>
                                            </select>
                                            <select id="ward" class="hidden form-select">
                                                <option value="" selected>Chọn phường xã</option>
                                            </select>
                                        </div>

                                        <input type="text"  name="txtNewAddress" class="hidden" id="txtAddress" value="<%= address%>">
                                        <input type="text" name="txtNewPhone" class="hidden" value="<%= phone%>" placeholder="Số điện thoại tuỳ chọn">

                                        <div class="d-flex justify-content-star">
                                            <button type="submit" name="btnSubmitCheckOut" value="Submit"
                                                    class="btn rounded-0 mt-3 w-100">ĐẶT HÀNG</button>
                                        </div>

                                    </div>
                                </div>

                        </form>
                    </div>

                </div>
            </div>
        </div>

        <div class="row mt-5">
            <p>Cảm ơn bạn đã đặt hàng tại XXIV STORE. Để hoàn tất đặt hàng bạn vui lòng chuyển khoản
                trước 100% giá trị đón hàng.<br/> Thông tin chuyển khoản sẽ hiện lên khi bạn hoàn tất việc đặt hàng.</p>
        </div>

        <div class="row">
            <div class="col-md-12 register">
                <h1>Đăng ký thành viên để nhận khuyến mại</h1>
                <p>Theo dõi chúng tôi để nhận thêm nhiều ưu đãi</p>
                <form action="">
                    <input type="text" name="" id="" placeholder="nhập email" />
                    <button>ĐĂNG KÝ</button>
                </form>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 social">
                <a href=""><img src="/RESOURCES/images/icons/instagram.png" alt="" /></a>
                <a href=""><img src="/RESOURCES/images/icons/facebook.png" alt="" /></a>
                <a href=""><img src="/RESOURCES/images/icons/youtube.png" alt="" /></a>
                <a href=""><img src="/RESOURCES/images/icons/location-pin.png" alt="" /></a>
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
    <script src="/RESOURCES/checkout/public/js/main.js"></script>

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
                $("#result").text(result);
                console.log("update value success");
                $("input#txtAddress").val(result);
            }
        };
    </script>
</body>

</html>