<%-- Document : newjsp Created on : Jul 5, 2023, 3:27:56 PM Author : Acer --%>

<%@page import="DAOs.OrderDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="Models.Order"%>
<%@page import="Models.User"%>
<%@page import="DAOs.UserDAO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%! UserDAO usDAO = new UserDAO();%>
<%!String fullname, username, email;%>
<%! List<String[]> order;%>
<%!Order OrderInfor;%>
<%! String CheckOutSuccess; %>

<%
    Cookie currentUserCookie = (Cookie) pageContext.getAttribute("userCookie", pageContext.SESSION_SCOPE);
    User user = usDAO.getUser(currentUserCookie.getValue());
    fullname = user.getName();
    username = user.getUsername();
    email = user.getEmail();
    ProductDAO pDAO = new ProductDAO();
    OrderInfor = (Order) request.getAttribute("OrderInfor");
    order = (List<String[]>) (request.getAttribute("OrderDetail"));
    CheckOutSuccess = (String)request.getParameter("CheckOutSuccess");
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
        <link rel="stylesheet" href="/RESOURCES/user/public/style/order_detail_style.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
              rel="stylesheet" type="text/css" />
        <title>Chi tiết đơn hàng</title>
    </head>

    <body>


        <div class="container-fluid">
            <h1><%= user.getName()%></h1>
            <h1><%= user.getUsername()%></h1>
            <h1><%= user.getEmail()%></h1>
            <h1><%= user.getPassword()%></h1>
            <h1> Order size la <%= order.size()%> </h1>
            <div class="row">
                <div class="col-md-12 nav">
                    <ul>
                        <li><a href="/">trang chủ</a></li>
                        <li> <a href="/home/introduction">giới thiệu</a></li>
                        <li><a href="/home/brand">thương hiệu</a></li>
                        <!-- This link to shop servlet file. DO NOT MODIFY the link -->
                        <li><a href="/Product/List">sản phẩm</a></li>
                    </ul>
                    <a href="/"><img src="/RESOURCES/images/icons/icon.webp" alt="" height="64"></a>
                    <div class="account">
                        <a href="/Log/Login"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
                        <a href="/Client/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>
                    </div>
                </div>
            </div>


            <div class="main">
                <div class="right">
                    <div class="order-page">
                        <c:if test="<%= CheckOutSuccess != null %>">
                            <div class="CheckOutSuccess">
                                <h1 class="display-4 mb-10">THANH TOÁN THÀNH CÔNG</h1>
                            </div>
                        </c:if>
                        <table class="table">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Thông tin</th>
                                    <th scope="col">Sản phẩm</th>
                                    <th scope="col" class="number">Số lượng</th>
                                    <th scope="col">Đơn giá</th>
                                    <th scope="col">Tổng tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:if test="<%=  order.size() > 0%>">
                                    <c:forEach var="i" begin="0" end="<%= order.size() - 1%>">
                                        <tr>
                                            <th scope="row"><%=(int) pageContext.getAttribute("i") + 1%></th>
                                            <td><%= order.get((int) pageContext.getAttribute("i"))[0]%></td>
                                            <td><img src="<%=order.get((int) pageContext.getAttribute("i"))[1]%>"></td>
                                            <td><%=order.get((int) pageContext.getAttribute("i"))[2]%></td>
                                            <td><%=  pDAO.IntegerToMoney(Integer.parseInt(order.get((int) pageContext.getAttribute("i"))[3]))%></td>
                                            <td><%=  pDAO.IntegerToMoney(Integer.parseInt(order.get((int) pageContext.getAttribute("i"))[5]))%></td>
                                        </tr>
                                    </c:forEach>
                                    <tr class="bottom-table">
                                        <td colspan="6">
                                            <p><img src="/RESOURCES/images/icons/smartphone.png" alt="alt"/><span class="info"><%=OrderInfor.getPhoneNumber()%></span></p>
                                            <p><img src="/RESOURCES/images/icons/location-pin.png" alt="alt"/><span class="info"><%=OrderInfor.getAddress()%></span></p>
                                            <c:if test="<%= (OrderInfor.getNote() != null && !OrderInfor.getNote().equals(""))%>">
                                                <p><img src="/RESOURCES/images/icons/email.png" alt="alt"/><span class="info"><%=OrderInfor.getNote()%></span></p>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                        <!-- -->
                         <c:if test="<%= CheckOutSuccess != null %>"> 
                            <a href="/Product/List" class="btn btn-outline-dark rounded-0">QUAY LẠI TRANG SẢN PHẨM</a>
                        </c:if>
                    </div>

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
                    <p>&copy; xxiv 2023 | all right reserved</p>
                </div>
            </div>

        </div>

            <script src="/RESOURCES/user/public/js/order_detail.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"></script>

            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

            <script src="/RESOURCES/admin/product/public/js/main.js"></script>
            <!--Jquery Validation-->
            <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
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
                                email:true
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