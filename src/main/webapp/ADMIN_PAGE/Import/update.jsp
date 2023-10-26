<%@page import="Lib.Generator.DatePattern"%>
<%@page import="Lib.Generator"%>
<%@page import="Models.ImportDetail"%>
<%@page import="Models.Import"%>
<%@page import="DAOs.InventoryManagerDAO"%>
<%@page import="Models.InventoryManager"%>
<%@page import="DAOs.ImportStashItemDAO"%>
<%@page import="Models.ImportStashItem"%>
<%@page import="Models.Voucher"%>
<%@page import="Lib.ExceptionUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="DAOs.CartItemDAO"%>
<%@page import="DAOs.CustomerDAO"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="Models.Product"%>
<%@page import="java.util.List"%>
<%@page import="Models.CartItem"%>
<%@page import="Lib.Converter"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%! ProductDAO pDAO = new ProductDAO();%>
<%! InventoryManagerDAO ivtrManaDAO = new InventoryManagerDAO();%>


<%
    String queryString = request.getQueryString();
    boolean isError = ExceptionUtils.isWebsiteError(queryString);
    String exceptionMessage = ExceptionUtils.getMessageFromExceptionQueryString(queryString);

    Import importInfo = (Import) request.getAttribute("importInfo");
    List<ImportDetail> impDetailList = importInfo.getImportDetail();

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
        <link rel="stylesheet" href="/RESOURCES/admin/import/public/style/style.css" />
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp" />
        <link
            href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
            rel="stylesheet"
            type="text/css" 
            />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>

        <title>Giỏ hàng</title>
    </head>
    <body>
        <div class="container-fluid">

            <!--Navbar section-->
            <div class="row">
                <div class="col-md-12 nav">
                    <jsp:include page="/NAVBAR/InventoryManagerNavbar.jsp"></jsp:include>
                    </div>
                </div>

                <div class="row">
                    <div class="main">

                        <form  class="box" id="importUpdate" action="/Admin/Import/ConfirmUpdate" method="POST">
                            <div class="left w-100">
                                <!--  Showing list of product -->
                                <h1>Sản phẩm đã chọn</h1>
                                <table class="w-100">
                                <c:choose>
                                    <c:when test='<%= impDetailList.size() > 0%>'>
                                        <%
                                            int ListSize = 0;
                                        %>
                                        <c:forEach var="i" begin="0" end="<%= impDetailList.size() - 1%>">
                                            <%
                                                Product p = pDAO.getProduct(impDetailList.get((int) pageContext.getAttribute("i")).getProductId());
                                                int CartCost = impDetailList.get((int) pageContext.getAttribute("i")).getCost();
                                                int CartQuan = impDetailList.get((int) pageContext.getAttribute("i")).getQuantity();
                                                int sumCost = CartCost * CartQuan;
                                            %>
                                            <c:choose>
                                                <c:when test='<%= impDetailList.get((int) pageContext.getAttribute("i")).getStatus().equals("WAIT")%>'>
                                                    <tr>
                                                        <td>
                                                            <a href="/Product/Detail/ID/<%= p.getId()%>">
                                                                <img src="<%= p.getImgURL()%>"alt=""/>
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <a href="/Product/Detail/ID/<%= p.getId()%>">
                                                                <%= p.getName()%> - <%= p.getVolume()%>ml
                                                            </a>
                                                            <span><%= Converter.covertIntergerToMoney(CartCost)%> <span>₫</span></span>
                                                            <span>Total: <%= Converter.covertIntergerToMoney(sumCost)%> <span>₫</span></span>
                                                        </td>
                                                        <td>
                                                            <h4>Quantity</h4>
                                                            <span>
                                                                <input type="number" min="1" name="<%= "ProductQuan" + ListSize%>" value="<%= CartQuan%>" /> 
                                                            </span>
                                                            <h4>Cost</h4>
                                                            <span>
                                                                <input type="number" min="0" name="<%= "ProductCost" + ListSize%>" value="<%= CartCost%>" /> 
                                                            </span>
                                                            <a href="/Admin/Import/DeleteDetail/ImportID/<%= importInfo.getId()%>/ProductID/<%= p.getId()%>">
                                                                <img src="/RESOURCES/images/icons/close.png" alt="alt"/>
                                                            </a>
                                                            <input type="hidden" name="<%= "ProductID" + pageContext.getAttribute("i")%>" value="<%= p.getId()%>" />
                                                        </td>
                                                    </tr>
                                                    <%
                                                        ListSize++;
                                                    %>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td>
                                                            <a href="/Product/Detail/ID/<%= p.getId()%>">
                                                                <img src="<%= p.getImgURL()%>"alt=""/>
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <a href="/Product/Detail/ID/<%= p.getId()%>">
                                                                <%= p.getName()%> - <%= p.getVolume()%>ml
                                                            </a>
                                                            <span><%= Converter.covertIntergerToMoney(CartCost)%> <span>₫</span></span>
                                                            <span>Total: <%= Converter.covertIntergerToMoney(sumCost)%> <span>₫</span></span>
                                                        </td>
                                                        <td>
                                                            <h4>Quantity</h4>
                                                            <span>
                                                                <input type="number" min="1" disabled="true" value="<%= CartQuan%>" /> 
                                                            </span>
                                                            <h4>Cost</h4>
                                                            <span>
                                                                <input type="number" min="0" disabled="true" value="<%= CartCost%>" /> 
                                                            </span>
                                                            <span>
                                                                <h3>USED</h3>
                                                            </span>
                                                        </td>
                                                    </tr>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="w-100">
                                            <td colspan="3" class="w-100">
                                                <div id="no-product-header">
                                                    <h3>Chưa có sản phẩm nào trong giỏ hàng.</h3>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </table>
                            <input type="hidden" name="ListSize" value="<%= ListSize%>" />
                            <c:choose>
                                <c:when test='<%= impDetailList.size() > 0%>'>

                                </c:when>
                                <c:otherwise>
                                    <a href="/Admin/Impot/Store" class="backToStore">
                                        <div id="come-back-link">
                                            Quay trở lại cửa hàng
                                        </div>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <c:choose>
                            <c:when test='<%= impDetailList.size() > 0%>'>
                                <div class="right">
                                    <div class="checkout">
                                        <h2>Phiếu hoá đơn</h2>
                                        <div class="flex-column align-items-start">
                                            <h5>Supplier name</h5>
                                            <input class="w-100" type="text" name="txtSupplier" value="<%= importInfo.getSupplierName()%>">
                                        </div>
                                        <hr/>
                                        <div class="flex-column align-items-start">
                                            <h5>Import At</h5>
                                            <input class="w-100" type="date" name="txtImportAt" value="<%= importInfo.getImportAt(DatePattern.DateSqlPattern)%>">
                                        </div>
                                        <div class="flex-column align-items-start">
                                            <h5>Delivered_At</h5>
                                            <input class="w-100" type="date" name="txtDeliveredAt" value="<%= importInfo.getDeliveredAt(DatePattern.DateSqlPattern)%>">
                                        </div>
                                        <hr/>
                                        <div class="flex-column align-items-start">
                                            <h5>Import Total Quantity</h5>
                                            <input class="w-100" type="number" readonly="true"  value="<%= importInfo.getTotalQuantity()%>">
                                        </div>
                                        <hr/>
                                        <div class="flex-column align-items-start">
                                            <h5>Import Total Cost</h5>
                                            <input class="w-100" type="number" readonly="true" value="<%= Converter.covertIntergerToMoney(importInfo.getTotalCost())%>">
                                        </div>
                                        <hr/>
                                        <div class="flex-column align-items-start">
                                            <h5>Import By Inventory Manager</h5>
                                            <input class="w-100" type="text" readonly="true"value="<%= ivtrManaDAO.getInventoryManager(importInfo.getImportByInventoryManager()).getUsername()%>">
                                        </div>
                                        <hr/>
                                        <div>
                                            <button id="btn-checkout" type="submit" class="btn-checkout" name="btnCheckoutImportCart" value="Submit">Cập nhật hóa đơn</button>
                                        </div>
                                    </div>
                                    <c:if test='<%=isError%>'>
                                        <div class="error-exception">
                                            <p style="color: red; margin-top: 1rem; text-align: left"><%= exceptionMessage%> </p>
                                        </div>
                                    </c:if>
                                    <div class="drop-down">
                                        <ul>
                                            <li>
                                                <button type="button" class="btnDropDown">
                                                    trợ giúp ? <span>+</span>
                                                </button>
                                                <div class="open">
                                                    <p>
                                                        Nếu trong quá trình đặt hàng có bất kỳ thắc mắc nào, quý
                                                        khách vui lòng liên hệ theo Hotline:
                                                    </p>
                                                    <ul>
                                                        <li>+ Chi nhánh Hà Nội: 090 721 9889</li>
                                                        <li>+ Chi nhánh Sài Gòn: 093 194 8668</li>
                                                    </ul>
                                                </div>
                                                <hr />
                                            </li>
                                            <li>
                                                <button type="button" class="btnDropDown">
                                                    thông tin ship ? <span>+</span>
                                                </button>
                                                <div class="open">
                                                    <p>
                                                        XXIV Store miễn phí ship với tất cả các đơn hàng toàn
                                                        quốc.
                                                    </p>
                                                </div>
                                                <hr />
                                            </li>
                                            <li>
                                                <button type="button" class="btnDropDown">
                                                    đổi trả và hoàn tiền <span>+</span>
                                                </button>
                                                <div class="open">
                                                    <p>Chính sách đổi trả sản phẩm:</p>
                                                    <ul>
                                                        <li>
                                                            XXIV Store hỗ trợ đổi trả sản phẩm trong vòng 3 ngày
                                                            kể từ khi nhận hàng.
                                                        </li>
                                                        <li>
                                                            Với việc đổi sản phẩm, chỉ áp dụng với các sản phẩm
                                                            còn nguyên seal, tem. Chỉ áp dụng đổi sang các sản
                                                            phẩm có giá trị bằng hoặc cao hơn.
                                                        </li>
                                                        <li>
                                                            Với việc hoàn tiền, chỉ áp dụng trong trường hợp sản
                                                            phẩm có lỗi từ nhà sản xuất hoặc bể, vỡ trong quá
                                                            trình vận chuyển.
                                                        </li>
                                                    </ul>
                                                </div>
                                                <hr />
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
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
                    <a href=""
                       ><img src="/RESOURCES/images/icons/instagram.png" alt=""
                          /></a>
                    <a href=""
                       ><img src="/RESOURCES/images/icons/facebook.png" alt=""
                          /></a>
                    <a href=""
                       ><img src="/RESOURCES/images/icons/youtube.png" alt=""
                          /></a>
                    <a href=""
                       ><img src="/RESOURCES/images/icons/location-pin.png" alt=""
                          /></a>
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

                $("#importUpdate").validate({
                    rules: {
                        txtSupplier: {
                            required: true
                        },
                        txtImportAt: {
                            required: true
                        },
                        txtDeliveredAt: {
                            required: true
                        }
                    },
                    messages: {
                        txtSupplier: {
                            required: "Vui lòng nhập tên nhà cung cấp"
                        },
                        txtImportAt: {
                            required: "Vui lòng nhập ngày import"
                        },
                        txtDeliveredAt: {
                            required: "Vui lòng nhập ngày vận chuyển đến"
                        }
                    }
                });
            });


        </script>

        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"
        ></script>
        <script src="/RESOURCES/admin/import/public/js/main.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>



    </body>
</html>

