<%@page import="DAOs.BrandDAO"%>
<%@page import="DAOs.CartDAO"%>
<%@page import="DAOs.UserDAO"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="Models.Product"%>
<%@page import="java.util.List"%>
<%@page import="Models.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>


<%! List<Cart> listCart = null;%>
<%! ProductDAO pDAO = new ProductDAO();%>
<%! BrandDAO bDAO = new BrandDAO(); %>
<%! UserDAO uDao = new UserDAO();%>
<%! CartDAO cDAO = new CartDAO(); %>
<%! List<Product> listOutOfStock; %>
<%! int Total;%>
<%
    Cookie userCookie = ((Cookie) request.getSession().getAttribute("userCookie"));
    String username = userCookie.getValue();
    int ClientID = uDao.getUser(username).getID();
    Total = cDAO.getCartTotal(ClientID);
    listCart = (List<Cart>) request.getAttribute("listCart");
    listOutOfStock = (List<Product>) request.getAttribute("listOutOfStock");

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
        <title>Sản phẩm đã chọn</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 nav">
                    <ul>
                        <li><a href="/">trang chủ</a></li>
                        <li> <a href="/home/introduction">giới thiệu</a></li>
                        <li><a href="/home/brand">thương hiệu</a></li>
                        <li><a href="/Product/List">sản phẩm</a></li>
                        <li><a href="">blog</a></li>
                    </ul>
                    <a href="/"><img src="/RESOURCES/images/icons/icon.webp" alt=""
                                     height="64"></a>
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

                        <div class="left w-100">
                            <form action="/Client/Cart/Update" method="GET">
                                <input type="hidden" name="ClientID" value="<%= ClientID%>" />

                                <!--  Handling Product out of stock still in Client Cart -->
                                <c:if test='<%= listOutOfStock.size() != 0%>'>
                                    <%
                                        int lastBrandID = -1, curBrandID;
                                    %>
                                    <c:forEach var="i" begin="0" end="<%= listOutOfStock.size() - 1%>">
                                        <%
                                            Product opd = listOutOfStock.get((int) pageContext.getAttribute("i"));
                                            curBrandID = opd.getBrandID();
                                        %>  

                                        <c:if test='<%= lastBrandID != curBrandID%>'>
                                            <% lastBrandID = curBrandID;%>

                                            <c:if test='<%= (int) pageContext.getAttribute("i") != 0%>'>
                                                <hr>
                                            </c:if>
                                            <p>Please choose other product from the same brand by visitting <a href="/Product/List/BrandID/<%= lastBrandID%>" target="_blank"><%= bDAO.getBrandName(lastBrandID)%></a></p>
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
                                        <c:when test='<%= listCart.size() > 0%>'>
                                            <c:forEach var="i" begin="0" end="<%= listCart.size() - 1%>">
                                                <%
                                                    Product p = pDAO.getProduct(listCart.get((int) pageContext.getAttribute("i")).getProductID());
                                                    int sum = listCart.get((int) pageContext.getAttribute("i")).getSum();
                                                    int CartQuan = listCart.get((int) pageContext.getAttribute("i")).getQuantity();
                                                %>
                                                <tr>
                                                    <td>
                                                        <a href="/Product/Detail/ID/<%= p.getID()%>">
                                                            <img src="<%= p.getImgURL()%>"alt=""/>
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <a href="/Product/Detail/ID/<%= p.getID()%>">
                                                            <%= p.getName()%> - <%= p.getVolume()%>ml
                                                        </a>
                                                        <span><%= pDAO.IntegerToMoney(p.getPrice())%> <span>₫</span></span>
                                                        <span>Total: <%= pDAO.IntegerToMoney(sum)%> <span>₫</span></span>
                                                    </td>
                                                    <td>

                                                        <input type="hidden" name="<%= "ProductID" + pageContext.getAttribute("i")%>" value="<%= p.getID()%>" />
                                                        <span class="ProductMaxQuantity" > <%= p.getQuantity()%> </span>     <input type="number" name="<%= "ProductQuan" + pageContext.getAttribute("i")%>" value="<%= CartQuan%>" /> 

                                                        <a href="/Client/Cart/Delete/ProductID/<%= p.getID()%>/ClientID/<%= ClientID%>">
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
                                <input type="hidden" name="ListSize" value="<%= listCart.size()%>" />
                                <c:choose>
                                    <c:when test="<%= listCart.size() > 0%>">
                                        <div>
                                            <button type="submit" class="btn-disabled"><span>Cập nhật giỏ hàng</span></button>
                                        </div>
                                    </c:when>

                                    <c:otherwise>
                                        <a href="/Product/List" class="backToStore">
                                            <div id="come-back-link">
                                                <span>Quay trở lại cửa hàng</span>
                                            </div>
                                        </a>
                                    </c:otherwise>
                                </c:choose>

                            </form>
                        </div>

                        <c:choose>
                            <c:when test="<%= listCart.size() > 0%>">
                                <div class="right">
                                    <div class="checkout">
                                        <h2>Phiếu thanh toán</h2>
                                        <div>
                                            <h4>Tạm tính</h4>
                                            <span><%= pDAO.IntegerToMoney(Total)%><span>₫</span></span>
                                        </div>
                                        <hr />
                                        <div>
                                            <h4>Tổng</h4>
                                            <span><%= pDAO.IntegerToMoney(Total)%><span>₫</span></span>
                                        </div>
                                        <div>
                                            <a href="/Client/Cart/Checkout" class="text-decoration-none"><button>TIẾN HÀNH THANH TOÁN</button></a>
                                        </div>
                                    </div>
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
    </body>
</html>
