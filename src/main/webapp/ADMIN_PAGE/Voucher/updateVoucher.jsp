<%@page import="Models.Product"%>
<%@page import="Lib.Generator"%>
<%@page import="Models.Admin"%>
<%@page import="java.util.List"%>
<%@page import="DAOs.AdminDAO"%>
<%@page import="Lib.Generator.DatePattern"%>
<%@page import="Models.Voucher"%>
<%@page import="Lib.ExceptionUtils"%>
<%@page import="Models.Customer"%>
<%@page import="Models.Employee"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! String Tinh, QuanHuyen, PhuongXa;%>
<%!Voucher voucher;%>
<%! ProductDAO pDAO = new ProductDAO(); %>
<%! List<Integer> approvedProductId;%>

<%
    approvedProductId = request.getAttribute("ApprovedProductId") == null ? null : (List<Integer>) request.getAttribute("ApprovedProductId");
    voucher = (Voucher) request.getAttribute("VoucherUpdate");
    // Handling execption
    String queryString = request.getQueryString();
    boolean isErr = ExceptionUtils.isWebsiteError(queryString);
    String exeptionMessage = ExceptionUtils.getMessageFromExceptionQueryString(queryString);
    String productListString = "";
    for (int i = 0; i < approvedProductId.size(); i++) {
        Product product = pDAO.getProduct(approvedProductId.get(i));
        if (product.getName() != null && i != approvedProductId.size() - 1) {
            productListString += product.getName() + ", ";
        } else {
            productListString += product.getName();
        }
    }


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
        <link rel="stylesheet" href="/RESOURCES/admin/voucher/public/style/list.css" />

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

        <title>Cập nhật Voucher</title>
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
                    <div class="col-sm-6">

                        <h1>Update Voucher</h1>
                        <form action="/Admin/Voucher/Update" method="POST" id="updateVoucher">
                            <div class="id">
                                <label>Voucher ID *</label>
                                <input type="number" name="txtId" readonly="true" value="<%= voucher.getId()%>" />
                            </div>
                            <div class="code">
                                <label>Code *</label>
                                <input type="text" name="txtCode" value="<%= voucher.getCode()%>" />
                            </div>
                            <div class="quantity">
                                <label>Quantity *</label>
                                <input type="number" name="txtQuantity" value="<%= voucher.getQuantity()%>" />
                            </div>
                            <div class="discount-percent">
                                <label>Discount percent *</label>
                                <input type="number" name="txtDiscountPercent" value="<%= voucher.getDiscountPercent()%>" />
                            </div>
                            <div class="discount-max">
                                <label>Product to apply *</label>
                                <input id="productList" type="text" name="txtApprovedProduct" readonly="true" />
                            </div>
                            <div class="discount-max">
                                <label>Discount max *</label>
                                <input type="number" name="txtDiscountMax" value="<%= voucher.getDiscountMax()%>" />
                            </div>
                            <div class="created-at">
                                <label>Created at *</label>
                                <input type="date" name="txtCreateAt" value="<%=  Generator.getDateTime(voucher.getCreatedAt(), DatePattern.DateSqlPattern)%>" />
                            </div>
                            <div class="expired-at">
                                <label>Expired at *</label>
                                <input type="date" name="txtExpiredAt" value="<%= Generator.getDateTime(voucher.getExpiredAt(), DatePattern.DateSqlPattern)%>" />
                            </div>
                            <button type="submit" name="btnUpdateVoucher" value="Submit" class="btnUpdateCustomer mb-3">
                                Update Voucher
                            </button>
                            <br />
                        </form>
                    </div>
                    <div class="col-sm-4 ms-5">
                        <div class="list">
                            <%
                                List<Product> productList = pDAO.getAll();
                            %>
                            <c:choose>
                                <c:when test='<%=productList.size() == 0%>'>
                                    <div>Không có sản phẩm nào để chọn</div>
                                </c:when>

                                <c:otherwise>
                                    <input placeholder="Tìm kiếm nhanh" type="text" class="searchBox w-100">
                                    <div class="loading-section">
                                        <svg class="spinner" viewBox="0 0 10 10">
                                        <circle class="path" cx="50%" cy="50%" r="4"></circle>
                                        </svg>
                                    </div>
                                    <div class="scroll-list">
                                        <!-- Product List -->
                                        <c:forEach var='i' begin='0' end="<%=productList.size() - 1%>">
                                            <%
                                                int index = (int) pageContext.getAttribute("i");
                                                Product product = productList.get(index);

                                                boolean isCheck = true;
                                            %>
                                            <div class="item fw-bold <%=productListString.contains(product.getName()) ? "enabled" : ""%>" data-product-name="<%= product.getName()%>" >
                                                <div class="row">
                                                    <div class="col-sm-12 row">
                                                        <div data-id="<%=product.getId()%>" class="col-6 d-flex align-items-center item-name"> <%= product.getName()%></div>
                                                        <a href="/Product/Detail/ID/<%= product.getId()%>" class="col-2 offset-4" target="_blank"><img src=<%= product.getImgURL()%> alt="<%= product.getName()%>"></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>

                                </c:otherwise>

                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"
        ></script>
        <script src="/RESOURCES/admin/product/public/js/list.js"></script>
        <script src="/RESOURCES/admin/voucher/public/js/filterList.js"></script>
        <script src="/RESOURCES/admin/voucher/public/js/list.js"></script>

        <!--Jquery Validation-->
        <script>
            $(document).ready(function () {

                //Add custom validation
                $.validator.addMethod("greaterThan",
                        function (value, element, params) {
                            console.log(new Date(value));
                            console.log(new Date($(params).val()));

                            if (!/Invalid|NaN/.test(new Date(value))) {
                                return new Date(value) > new Date($(params).val());
                            }

                            return isNaN(value) && isNaN($(params).val())
                                    || (Number(value) > Number($(params).val()));
                        }, 'Must be greater than {0}.');
                $("#updateVoucher").validate({
                    rules: {
                        txtId: {
                            required: true
                        },
                        txtCode: {
                            required: true,
                            maxlength: 20
                        },
                        txtQuantity: {
                            required: true,
                            digits: true
                        },
                        txtDiscountPercent: {
                            required: true,
                            digits: true,
                            range: [0, 100]
                        },
                        txtApprovedProduct: {
                            required: true
                        },
                        txtDiscountMax: {
                            required: true,
                            digits: true
                        },
                        txtCreateAt: {
                            required: true
                        },
                        txtExpiredAt: {
                            required: true,
                            greaterThan: "input[name=txtCreateAt]"
                        }
                    },
                    messages: {
                        txtId: {
                            required: "Voucher Id không được để trống"
                        },
                        txtCode: {
                            required: "Voucher Code không được để trống",
                            maxlength: "Voucher Code không được vượt quá 20 kí tự"
                        },
                        txtQuantity: {
                            required: "Số lượng không được để trống",
                            digits: "Số lượng phải là số tự nhiên"
                        },
                        txtDiscountPercent: {
                            required: "Phần trăm giảm giá không được để trống",
                            digits: "Phần trăm giảm giá phải là số tự nhiên",
                            range: "Phần trăm giảm giá phải nằm trong khoảng từ 0-100"
                        },
                        txtApprovedProduct: {
                            required: "Sản phẩm được voucher áp dụng không được để trống"
                        },
                        txtDiscountMax: {
                            required: "Giảm giá tối đa không được để trống",
                            digits: "Giảm giá tối đa phải là số nguyên không âm"
                        },
                        txtCreateAt: {
                            required: "Ngày tạo voucher không được để trống"
                        },
                        txtExpiredAt: {
                            required: "Ngày voucher hết hạn không được để trống",
                            greaterThan: "Ngày voucher hết hạn phải lớn hơn ngày voucher tạo"
                        }
                    }
                });
            });
        </script>
    </body>
</html>
