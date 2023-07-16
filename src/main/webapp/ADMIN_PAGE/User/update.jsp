

<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%! BrandDAO bDAO = new BrandDAO();  %>
<%! ProductDAO pDAO = new ProductDAO(); %>

<%! User us;%>
<%! String Tinh, QuanHuyen, PhuongXa;%>
<% us = (User) request.getAttribute("UserUpdate");
    Tinh = "";
    QuanHuyen = "";
    PhuongXa ="";
    if (us.getAddress() != null && !us.getAddress().equals("") && us.getAddress().split(" - ").length == 3) {
        String Address[] = us.getAddress().split(" - ");
        Tinh = Address[0];
        QuanHuyen = Address[1];
        PhuongXa = Address[2];
    }
%>


<%! boolean isExistedPhone, isExistEmail, isExistedUsername;%>
<%    // Handling execption
    String err = "err";
    isExistedPhone = (request.getParameter(err + "Phone") == null ? false : Boolean.parseBoolean(request.getParameter(err + "Phone")));
    isExistEmail = (request.getParameter(err + "Email") == null ? false : Boolean.parseBoolean(request.getParameter(err + "Email")));
    isExistedUsername = (request.getParameter(err + "Username") == null ? false : Boolean.parseBoolean(request.getParameter(err + "Username")));
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
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
              rel="stylesheet" type="text/css" />

        <!--Custom Style-->
        <link rel="stylesheet" href="/RESOURCES/admin/user/public/style/update.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">

        <style>
            #preview-img{
                width:20%;
                height: 20%;
            }

        </style>

        <title>Cập nhật người dùng</title>
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
          </ul>
          <a href="/" class="iconPage"><img src="/RESOURCES/images/icons/icon.webp" alt=""
                                            height="64"></a>
          <div class="account">
            <button class="droppown-btn bg-transparent border-0" id="product-dropdown-btn"><img src="/RESOURCES/images/icons/shopping-bag-alone.png" alt="">
            </button>
            <ul class="shadow position-absolute align-items-start ps-1 pt-2">
              <li class="py-3 text-dark"><a href="/Admin/Product/Add">Thêm sản phẩm</a></li>
              <li class="pb-3 text-dark"><a href="/Admin/Product/List">Danh sách sản phẩm</a></li>
            </ul>
            
            <button class="droppown-btn bg-transparent border-0" id="user-dropdown-btn"><img src="/RESOURCES/images/icons/group.png" alt="">
            </button>
            <ul class="shadow position-absolute align-items-start ps-1 pt-2">
              <li class="py-3 text-dark"><a href="/Admin/User/List">Danh sách người dùng</a></li>
            </ul>
            
            <a href="/Client/User"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
            <a href="/Client/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>

          </div>
        </div>
      </div>
            <div class="row">
            <!--Execption Handling-->
        <c:choose>
            <c:when test='<%= isExistedPhone%>'>
                <h1 class="alert alert-danger">
                    Số điện thọai  đã tồn tại
                </h1>
            </c:when>
            <c:when test='<%= isExistedUsername%>'>
                <h1 class="alert alert-danger">
                    Tên đăng nhập đã tồn tại
                </h1>
            </c:when>

            <c:when test='<%= isExistEmail%>'>
                <h1 class="alert alert-danger">
                    Email đã tồn tại
                </h1>
            </c:when>
        </c:choose>
        <!--Execption Handling-->
            </div>
            <div class="row">
            <h1>Update User</h1>
            <form action="/Admin/User/Update" method="POST">
                <div class="id">
                    <label>User ID *</label>
                    <input type="number" name="txtUserID" readonly="true" value="<%= us.getID()%>">
                </div>
                <div class="name">
                    <label>Name *</label>
                    <input type="text" name="txtName" value="<%= us.getName() == null ? "" : us.getName()%>">
                </div>
                <div class="username">
                    <label>Username *</label>
                    <input type="text" name="txtUsername" value="<%= us.getUsername()%>">
                </div>
                <div class="password">
                    <label>Password *</label>
                    <input type="password" name="txtPassword" value="<%= us.getPassword()%>">
                </div>
                <div class="phone">
                    <label>Phone Number *</label>
                    <input type="text" name="txtPhoneNumber" value="<%= us.getPhoneNumber() == null ? "" : us.getPhoneNumber()%>">
                </div>
                <div class="email">
                    <label>Email *</label>
                    <input type="text" name="txtEmail" value="<%= us.getEmail()%>">
                </div>
                <div class="address">
                    <label>Address *</label>
                    <select id="city" class="form-select">
                        <option value="" selected>Chọn tỉnh thành</option>           
                    </select>

                    <select id="district" class="form-select">
                        <option value="" selected>Chọn quận huyện</option>
                    </select>

                    <select id="ward" class="form-select">
                        <option value="" selected>Chọn phường xã</option>
                    </select>
                    <input type="text" name="txtAddress" id="txtAddress" value="<%= us.getAddress() == null ? "" : us.getAddress()%>" readonly="true">
                </div>
                <div class="role">
                    <label>Role *</label>
                    <select name="txtRole" class="roleSelect">
                        <option value="Client" <%= (us.getRole().equals("Client")) ? "selected" : ""%>>Client</option>
                        <option value="Admin" <%= (us.getRole().equals("Admin")) ? "selected" : ""%>>Admin</option>
                    </select>
                </div>
                <button type="submit" name="btnUpdateUser" value="Submit" class="btnUpdateUser">Update User</button>
            </form>
            </div>
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <script src="/RESOURCES/admin/public/js/main.js"></script>


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
                $("form").validate({
                    rules: {
                        txtName: {
                            maxlength: 50
                        },
                        txtUsername: {
                            required: true,
                            maxlength: 50
                        },
                        txtPassword: {
                            required: true,
                            minlength: 6
                        },
                        txtPhoneNumber: {
                            number: true,
                            maxlength: 10,
                            minlength: 10
                        },
                        txtEmail: {
                            required: true,
                            email: true,
                            maxlength: 100
                        },
                        txtAddress: {
                            maxlength: 500
                        },
                        txtRole: {
                            required: true,
                            maxlength: 50
                        }
                    },
                    messages: {
                        txtPassword: {
                            minlength: "Password must be at least 6 characters long"
                        },
                        txtPhoneNumber: {
                            digits: "Phone number must contain only digits",
                            maxlength: "Phone number must not exceed 10 digits"
                        },
                        txtEmail: {
                            email: "Please enter a valid email address",
                            maxlength: "Email must not exceed 100 characters"
                        },
                        txtAddress: {
                            maxlength: "Address must not exceed 500 characters"
                        }
                    }
                });
            });
        </script>

    </body>
</html>


