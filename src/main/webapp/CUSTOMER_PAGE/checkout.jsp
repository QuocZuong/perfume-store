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
        <style>
            .inputHide{
                cursor:not-allowed;
                pointer-events: none ;
            }

            label.error{
                color: red;
                margin-top: 10px;
            }
        </style>
        <title>Thanh toán</title>
    </head>

    <body>
        <div class="container-fluid">
            
            <!--Navbar section-->
            <div class="row">
                <div class="col-md-12 nav">
                    <jsp:include page="/NAVBAR/ClientNavbar.jsp"></jsp:include>
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
                                        <div class="d-flex flex-column col-4">
                                            <label><input type="radio" name="rdoCustomAddress" id="rdoCustomAddress" value="no" class="form-check-input" checked=""> Sử
                                                dụng địa chỉ mặc định</label>
                                            <label><input type="radio" name="rdoCustomAddress" id="rdoCustomAddress" value="yes" class="form-check-input"> Địa chỉ giao
                                                hàng khác</label>
                                        </div>

                                        <div class="d-flex flex-column ms-5">
                                            <label for="txtNote" class="d-block w-100">Ghi chú đơn hàng (tuỳ chọn)</label>
                                            <textarea name="txtNote" cols="57" rows="2" class="border-1"
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
                                        <input class="inputHide" type="text" name="txtPhone" id="txtPhone" class="" value="<%= (phone == null ? "" : phone)%>" placeholder="Số điện thoại"  readonly="true">
                                        <input class="inputHide" type="text" name="txtAddress" id="txtAddress" value="<%= (address == null ? "" : address)%>" placeholder="Địa chỉ" readonly="true">
                                    </div>

                                    <h2 class="text-start mt-2 mb-0 mt-5 hidden" id="header-address">giao đến địa chỉ</h2>

                                    <div class="d-flex flex-column flex-wrap w-100 mt-2">

                                        <div class="w-100 d-flex">
                                            <select id="city" class="hidden form-select m-2">
                                                <option value="" selected>Chọn tỉnh thành</option>           
                                            </select>
                                            <select id="district" class="hidden form-select m-2">
                                                <option value="" selected>Chọn quận huyện</option>
                                            </select>
                                            <select id="ward" class="hidden form-select m-2">
                                                <option value="" selected>Chọn phường xã</option>
                                            </select>
                                        </div>

                                        <input type="text"  name="txtNewAddress" class="hidden" id="txtNewAddress" value="<%= (address == null ? "" : address)%>"  readonly="true">
                                        <input type="text" name="txtNewPhone" class="hidden" id="txtNewPhone" value="<%= (phone == null ? "" : phone)%>" placeholder="Số điện thoại tuỳ chọn">

                                        <div class="d-flex justify-content-start">
                                            <button type="submit" name="btnSubmitCheckOut" value="Submit" class="btn rounded-0 mt-3 w-100" >ĐẶT HÀNG</button>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="row mt-5 bottom">
                <p>Cảm ơn bạn đã đặt hàng tại XXIV STORE. Để hoàn tất đặt hàng bạn vui lòng chuyển khoản
                    trước 100% giá trị đón hàng.<br/> Thông tin chuyển khoản sẽ hiện lên khi bạn hoàn tất việc đặt hàng.</p>
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
                        });
            };
            callAPI('https://provinces.open-api.vn/api/?depth=1');
            var callApiDistrict = (api) => {
                return axios.get(api)
                        .then((response) => {
                            renderData(response.data.districts, "district", DefaultDistrict);
                        });
            };
            var callApiWard = (api) => {
                return axios.get(api)
                        .then((response) => {
                            renderData(response.data.wards, "ward", DefaultWard);
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
                        $("input#txtNewAddress").val(result);
                    } else {
                        $("#result").text("");
                        console.log("update value null");
                        $("input#txtNewAddress").val("");
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
                }, "Wrong input.");

                $("form").validate({
                    rules: {
                        txtPhone: {
                            required: true,
                            regex: /(^$|^0[1-9][\d]{8}$)/
                        },
                        txtAddress: {
                            required: true
                        }
                    },
                    messages: {
                        txtPhone: {
                            required: "Thiếu số điện thoại mặc định. Vui lòng cập nhật hoặc chọn giao bằng số điện thoại khác",
                            regex: "Số điện thoại không hợp lệ"
                        },
                        txtAddress: {
                            required: "Thiếu địa chỉ mặc định vui lòng cập nhật hoặc chọn giao bằng địa chỉ khác"
                        }
                    },

                });
            });

            let tempNewAddress;
            let tempNewPhone;

            function requirechange(element) {
                if ($("input#rdoCustomAddress:checked").val() == "no")
                {
                    //Remove all rules
                    $("input#txtPhone").rules("remove");
                    $("input#txtAddress").rules("remove");
                    $("input#txtNewPhone").rules("remove");
                    $("input#txtNewAddress").rules("remove");


                    //Hide the error label
                    $("label#txtNewPhone-error").hide();
                    $("label#txtNewAddress-error").hide();
                    $("label#txtPhone-error").show();
                    $("label#txtAddress-error").show();

                    tempNewAddress = $("input#txtNewPhone").val();
                    tempNewPhone = $("input#txtNewAddress").val();

                    $("input#txtNewPhone").val("");
                    $("input#txtNewAddress").val("");

                    console.log("apply no rule");
                    $("input#txtPhone").rules("add", {
                        required: true,
                        messages: {
                            required: "Thiếu số điện thoại mặc định vui lòng cập nhật hoặc chọn giao bằng số điện thoại khác"
                        }
                    });
                    $("input#txtAddress").rules("add", {
                        required: true,
                        messages: {
                            required: "Thiếu địa chỉ mặc định vui lòng cập nhật hoặc chọn giao bằng địa chỉ khác"
                        }
                    });


                } else if ($("input#rdoCustomAddress:checked").val() == "yes") {

                    $("input#txtNewPhone").val(tempNewAddress);
                    $("input#txtNewAddress").val(tempNewPhone);


                    //Remove all rules
                    $("input#txtPhone").rules("remove");
                    $("input#txtAddress").rules("remove");
                    $("input#txtNewPhone").rules("remove");
                    $("input#txtNewAddress").rules("remove");

                    $("label#txtNewPhone-error").show();
                    $("label#txtNewAddress-error").show();
                    $("label#txtPhone-error").hide();
                    $("label#txtAddress-error").hide();

                    console.log("apply yes rule");
                    //add rule
                    $("input#txtNewPhone").rules("add", {
                        required: true,
                        maxlength: 10,
                        minlength: 10,
                        number: true,
                        messages: {
                            required: "Vui lòng nhập số điện thoại",
                            number: "Số điện thoại không hợp lệ",
                            maxlength: "Số điện thoại phải là 10 chữ số",
                            minlength: "Số điện thoại phải là 10 chữ số"
                        }
                    });
                    $("input#txtNewAddress").rules("add", {
                        required: true,
                        messages: {
                            required: "Vui lòng nhập địa chỉ"
                        }
                    });
                }
            }
            ;

            $("input#rdoCustomAddress").on("input", function () {
                console.log($("input#rdoCustomAddress:checked").val());
                requirechange("input#rdoCustomAddress:checked");
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