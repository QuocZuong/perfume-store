<%@page import="DAOs.ProductDAO"%>
<%@page import="Models.Product"%>
<%@page import="java.util.List"%>
<%@page import="Models.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>


<%! List<Cart> listCart = null;%>
<%! ProductDAO pDAO = new ProductDAO();%>
<%! int Total;%>
<%
    int ClientID = 1;
    Total = 0;
    listCart = (List<Cart>) request.getAttribute("listCart");
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
                        <!-- This link to shop servlet file. DO NOT MODIFY the link -->
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

                        <div class="left">
                            <form action="/Client/Cart/Update" method="GET">
                                <input type="hidden" name="ClientID" value="<%= ClientID%>" />
                                <h1>Sản phẩm đã chọn</h1>
                                <table>

                                    <c:forEach var="i" begin="0" end="<%= listCart.size() - 1%>">
                                        <%
                                            Product p = pDAO.getProduct(listCart.get((int) pageContext.getAttribute("i")).getProductID());
                                            int sum = listCart.get((int) pageContext.getAttribute("i")).getSum();
                                            int CartQuan = listCart.get((int) pageContext.getAttribute("i")).getQuantity();
                                            Total += sum;
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
                                </table>
                                <input type="hidden" name="ListSize" value="<%= listCart.size()%>" />
                                <button type="submit">Cập nhật giỏ hàng</button>

                            </form>
                        </div>

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
                                    <button>TIẾN HÀNH THANH TOÁN</button>
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
                    </div>
                </div>
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

        <script>
            let ProductQuantInputs = document.querySelectorAll("input[name^=ProductQuan]");
            let ProductIDInputs = document.querySelectorAll("input[name^=ProductID]");
            let ProductMaxQuantityInputs = document.querySelectorAll("span.ProductMaxQuantity");

            for (let i = 0; i < ProductQuantInputs.length; i++)
            {
                ProductQuantInputs[i].addEventListener("input", function () {
                    console.log(ProductMaxQuantityInputs[i].innerText);
                    if (ProductQuantInputs[i].value > parseInt(ProductMaxQuantityInputs[i].innerText))
                    {
                        ProductQuantInputs[i].value =  parseInt(ProductMaxQuantityInputs[i].innerText);
                    }
                    if (ProductQuantInputs[i].value < 1 && ProductQuantInputs[i].value !== "")
                    {
                        ProductQuantInputs[i].value = 1;
                    }
                });
                ProductQuantInputs[i].addEventListener("blur", function () {
                    if (ProductQuantInputs[i].value === "")
                    {
                        ProductQuantInputs[i].value = 1;
                    }
                });
            }

        </script>

        <script src="/RESOURCES/cart/public/js/main.js"></script>
    </body>
</html>
