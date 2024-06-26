<%@page import="Lib.Generator"%>
<%@page import="DAOs.InventoryManagerDAO"%>
<%@page import="Models.ImportDetail"%>
<%@page import="Models.Import"%>
<%@page import="Models.Product"%>
<%@page import="Models.OrderDetail"%>
<%@page import="Lib.Converter"%>
<%@page import="DAOs.OrderDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="DAOs.EmployeeDAO"%>
<%@page import="DAOs.VoucherDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="Models.Order"%>
<%@page import="Models.User"%>
<%@page import="Models.Voucher"%>
<%@page import="DAOs.UserDAO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%! ProductDAO pDAO = new ProductDAO(); %>
<%! InventoryManagerDAO ivtrManaDAO = new InventoryManagerDAO(); %>

<%! String fullname, username, email;%>
<%
    Import importInfo = (Import) request.getAttribute("importInfo");
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
        <style>
            .Import-Info{
                display: block;
                margin: 0rem 1rem;
            }
            .Import-Info h4{
                display: flex;
                align-items: flex-start;
                margin: 0rem 1rem;
            }
            .Import-Info p{
                display: flex;
                align-items: flex-start;
                font-size: 3rem !important;
            }
            .right h1{
                font-size: 4rem;
                font-weight: bold;
            }
            .main{
                margin: 0px 0px 100px 0px;
            }
            a.disabled {
                pointer-events: none;
                cursor: default;
            }
        </style>
    </head>

    <body>


        <div class="container-fluid">

            <!--Navbar section-->
            <div class="row">
                <div class="col-md-12 nav">
                    <jsp:include page="/NAVBAR/AdminNavbar.jsp"></jsp:include>
                    </div>
                </div>
                <div class="main">
                    <div class="right">
                        <h1>Chi tiết hóa đơn nhập</h1>
                        <div class="order-info">
                            <div class="Import-Info">
                                <h4 class="">Import ID: <%= importInfo.getId()%></h4>
                            <h4 class="">Nhà cung cấp: <%= importInfo.getSupplierName()%></h4>
                            <h4 class="">Người nhập đơn: <%= ivtrManaDAO.getInventoryManager(importInfo.getImportByInventoryManager()).getName()%></h4>
                            <h4 class="">Ngày nhập: <%= importInfo.getImportAt(Generator.DatePattern.DateForwardSlashPattern)%></h4>
                            <h4 class="">Ngày vận chuyển đến: <%= importInfo.getDeliveredAt(Generator.DatePattern.DateForwardSlashPattern)%></h4>
                            <br>
                            <br>
                        </div>
                    </div>
                    <div class="order-page">
                        <table class="table">
                            <thead class="thead-dark">
                                <tr>
                                    <th>#</th>
                                    <th>Sản phẩm</th>
                                    <th>Hình ảnh</th>
                                    <th>Số lượng</th>
                                    <th>Đơn giá</th>
                                    <th>Tổng tiền</th>
                                    <th>Trạng thái</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:if test='<%=  importInfo.getImportDetail().size() > 0%>'>
                                    <c:forEach var="i" begin="0" end="<%= importInfo.getImportDetail().size() - 1%>">
                                        <%
                                            ImportDetail importDetail = importInfo.getImportDetail().get((int) pageContext.getAttribute("i"));
                                            Product product = pDAO.getProduct(importDetail.getProductId());
                                            String productName = product.getName();
                                            String quantity = importDetail.getQuantity() + "";
                                            String cost = Converter.covertIntergerToMoney(importDetail.getCost());
                                            String sumCost = Converter.covertIntergerToMoney(importDetail.getCost() * importDetail.getQuantity());
                                            String status = importDetail.getStatus();
                                        %>
                                        <tr>
                                            <td><%=(int) pageContext.getAttribute("i") + 1%></td>
                                            <td>
                                                <a src="/Product/Detail/ID/<%= product.getId()%>"><%= productName%></a>
                                            </td>
                                            <td><img src="<%= product.getImgURL()%>"></td>
                                            <td><%= quantity%></td>
                                            <td><%= cost%>₫</td>
                                            <td>
                                                Total cost: <%= sumCost%>₫
                                            </td>
                                            <td>
                                                <a href="/Admin/Import/Bring/ImportID/<%= importDetail.getImportId()%>/ProductID/<%= importDetail.getProductId()%>" class="text-decoration-none <%= status.equals("WAIT") ? "" : "disabled"%>">
                                                    <button class="btn btn-outline-dark d-flex justify-content-center align-items-center rounded-0 fn-btn">
                                                        <span class="add-import"><%= status%></span>
                                                    </button>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </tbody>
                        </table>
                    </div>

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

        <!--Jquery-->    
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <script src="/RESOURCES/admin/product/public/js/main.js"></script>
        <!--Jquery Validation-->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>

        <script src="/RESOURCES/admin/user/public/js/main.js"></script>

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

                $(".add-import").on({
                    mouseenter: function () {
                        this.innerHTML = "ADD";
                    },
                    mouseleave: function () {
                        this.innerHTML = "WAIT";
                    }

                });
            });
        </script>
    </body>

</html>