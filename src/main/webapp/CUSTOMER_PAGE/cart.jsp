<%@page import="Models.Voucher"%>
<%@page import="Lib.ExceptionUtils"%>
<%@page import="java.util.List"%>
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
<%! List<CartItem> listCartItem = null;%>
<%! ProductDAO pDAO = new ProductDAO();%>
<%! BrandDAO bDAO = new BrandDAO(); %>
<%! CartItemDAO ciDAO = new CartItemDAO(); %>
<%! List<Product> listOutOfStock; %>
<%! int Total;%>
<%
    String queryString = request.getQueryString();
    boolean isError = ExceptionUtils.isWebsiteError(queryString);
    String exceptionMessage = ExceptionUtils.getMessageFromExceptionQueryString(queryString);
    int CustomerID = (int) request.getAttribute("customerID");
    listCartItem = (List<CartItem>) request.getAttribute("listCartItem");
    listOutOfStock = (List<Product>) request.getAttribute("listOutOfStock");
    Total = ciDAO.getCartTotal(listCartItem);
    Voucher v = (Voucher) request.getAttribute("voucher");
    List<Product> approvedProduct = (List<Product>) request.getAttribute("approvedProduct");
    Integer sumDeductPrice = (Integer) request.getAttribute("sumDeductPrice");

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
        <link rel="stylesheet" href="/RESOURCES/cart/public/style/style.css" />
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp" />
        <link
            href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
            rel="stylesheet"
            type="text/css" 
            />
        <title>Giỏ hàng</title>
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

                            <div class="left w-100">
                                <form action="/Customer/Cart/Update" method="GET">
                                    <input type="hidden" name="CustomerID" value="<%= CustomerID%>" />

                                <!--  Handling Product out of stock still in Client Cart -->
                                <c:if test='<%= listOutOfStock.size() != 0%>'>
                                    <%
                                        int lastBrandID = -1, curBrandID;
                                    %>
                                    <c:forEach var="i" begin="0" end="<%= listOutOfStock.size() - 1%>">
                                        <%
                                            Product opd = listOutOfStock.get((int) pageContext.getAttribute("i"));
                                            curBrandID = opd.getBrandId();
                                        %>  

                                        <c:if test='<%= lastBrandID != curBrandID%>'>
                                            <% lastBrandID = curBrandID;%>

                                            <c:if test='<%= (int) pageContext.getAttribute("i") != 0%>'>
                                                <hr>
                                            </c:if>
                                            <p>Please choose other product from the same brand by visitting <a href="/Product/List/BrandID/<%= lastBrandID%>" target="_blank"><%= bDAO.getBrand(lastBrandID).getName()%></a></p>
                                            </c:if>
                                        <div class="OFS">
                                            <p>Bạn không thể thêm "<span><%= opd.getName()%></span>" vào giỏ hàng vì sản phẩm này không đủ số lượng hoặc đã hết hàng</p>
                                        </div>

                                    </c:forEach>
                                </c:if>

                                <!--  Showing list of product -->
                                <h1>Sản phẩm đã chọn</h1>
                                <table class="w-100">

                                    <c:choose>
                                        <c:when test='<%= listCartItem.size() > 0%>'>
                                            <c:forEach var="i" begin="0" end="<%= listCartItem.size() - 1%>">
                                                <%
                                                    Product p = pDAO.getProduct(listCartItem.get((int) pageContext.getAttribute("i")).getProductId());
                                                    int sum = listCartItem.get((int) pageContext.getAttribute("i")).getSum();
                                                    int CartQuan = listCartItem.get((int) pageContext.getAttribute("i")).getQuantity();
                                                    int CartPrice = listCartItem.get((int) pageContext.getAttribute("i")).getPrice();
                                                %>
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
                                                        <c:choose>
                                                            <c:when test="<%= (v != null && approvedProduct != null && ProductDAO.isContain(p, approvedProduct))%>">
                                                                <span><%= Converter.covertIntergerToMoney(p.getStock().getPrice())%> <span>₫</span></span>
                                                                <span style="text-decoration: line-through;color: rgba(0,0,0,0.5);">Total:<%= Converter.covertIntergerToMoney(sum)%> <span>₫</span></span>
                                                                <span>Total: <%= Converter.covertIntergerToMoney(sumDeductPrice < v.getDiscountMax() ? (sum - (p.getStock().getPrice() * v.getDiscountPercent() / 100)) : (sum - (p.getStock().getPrice() * v.getDiscountPercent() / 100) + ((sumDeductPrice - v.getDiscountMax()) / approvedProduct.size())))%> <span>₫</span></span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span><%= Converter.covertIntergerToMoney(p.getStock().getPrice())%> <span>₫</span></span>
                                                                <span>Total: <%= Converter.covertIntergerToMoney(sum)%> <span>₫</span></span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <input type="hidden" name="<%= "ProductID" + pageContext.getAttribute("i")%>" value="<%= p.getId()%>" />
                                                        <span class="ProductMaxQuantity d-none" > <%= p.getStock().getQuantity()%> </span>
                                                        <input type="number" name="<%= "ProductQuan" + pageContext.getAttribute("i")%>" value="<%= CartQuan%>" /> 
                                                        <input type="hidden" name="<%= "ProductPrice" + pageContext.getAttribute("i")%>" value="<%= CartPrice%>" /> 
                                                        <a href="/Customer/Cart/Delete/ProductID/<%= p.getId()%>/CustomerID/<%= CustomerID%>">
                                                            <img src="/RESOURCES/images/icons/close.png" alt="" />
                                                        </a>
                                                    </td>
                                                </tr>
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
                                <input type="hidden" name="ListSize" value="<%= listCartItem.size()%>" />
                                <c:choose>
                                    <c:when test='<%= listCartItem.size() > 0%>'>
                                        <div>
                                            <button type="submit" class="btn-disabled"><span>Cập nhật giỏ hàng</span></button>
                                        </div>
                                    </c:when>

                                    <c:otherwise>
                                        <a href="/Product/List" class="backToStore">
                                            <div id="come-back-link">
                                                Quay trở lại cửa hàng
                                            </div>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </div>

                        <c:choose>
                            <c:when test='<%= listCartItem.size() > 0%>'>
                                <div class="right">
                                    <div class="checkout">
                                        <form action="/Customer/Cart/Checkout" method="POST">
                                            <h2>Phiếu thanh toán</h2>
                                            <div>
                                                <h4>Tạm tính</h4>
                                                <span><%= Converter.covertIntergerToMoney(Total)%><span>₫</span></span>
                                            </div>
                                            <hr />
                                            <c:choose>   
                                                <c:when test="<%= (sumDeductPrice != null)%>">
                                                    <div>
                                                        <h4>Tối đa</h4>
                                                        <span><%= Converter.covertIntergerToMoney(v.getDiscountMax())%><span>₫</span></span>
                                                    </div>
                                                    <hr>
                                                    <div>
                                                        <h4>Tổng</h4>
                                                        <span style="text-decoration: line-through; color: rgba(0,0,0,0.5);"><%= Converter.covertIntergerToMoney(Total)%><span>₫</span></span>
                                                    </div>
                                                    <div style="justify-content: flex-end">
                                                        <span><%= Converter.covertIntergerToMoney(Total - (sumDeductPrice < v.getDiscountMax() ? sumDeductPrice : v.getDiscountMax()))%><span>₫</span></span>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div>
                                                        <h4>Tổng</h4>
                                                        <span><%= Converter.covertIntergerToMoney(Total)%><span>₫</span></span>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                            <hr />
                                            <div>
                                                <h4>Voucher</h4>
                                                <input type="text" name="VoucherTXT" id="VoucherTXT" value="<%= v == null ? "" : v.getCode()%>">
                                                <a id="addVoucher"  href="">+</a>
                                            </div>
                                            <hr />
                                            <div>
                                                <button id="btn-checkout" type="submit" class="btn-checkout" name="btnCheckoutCart" value="Submit">TIẾN HÀNH THANH TOÁN</button>
                                            </div>
                                        </form>
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
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"
        ></script>
        <script src="/RESOURCES/cart/public/js/main.js"></script>
        <script src="/RESOURCES/home/public/js/main.js"></script>
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