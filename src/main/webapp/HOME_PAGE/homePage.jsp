<%@page import="Lib.Converter"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.Cookie"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@page import="Models.Product"%>
<%@page import="Models.Brand"%>
<%@page import="DAOs.ProductDAO" %>
<%@page import="DAOs.BrandDAO" %>
<%@page import="java.util.List"%>
<%@page import="java.util.stream.Collectors"%>

<%! ProductDAO pdao = new ProductDAO();%>

<%
    Cookie currentUserCookie = (Cookie) pageContext.getAttribute("userCookie", pageContext.SESSION_SCOPE);
    boolean isAdmin = false;

    if (currentUserCookie != null) {
        isAdmin = currentUserCookie.getName().equals("Admin");
    }

    List<Product> maleProductList = pdao.getBottom9MaleProduct();
    List<Product> femaleProductList = pdao.getBottom9FemaleProduct();
    List<Product> unisexProductList = pdao.getBottom9UnisexProduct();

    int index;
    Product p;
    BrandDAO bDAO = new BrandDAO();
    Brand b;

    int id;
    String name;
    String brand;
    String imgUrl;
    String price;
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
      crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
      href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond&family=Josefin+Sans:wght@200&family=Josefin+Slab&display=swap"
      rel="stylesheet">
    <link rel="stylesheet" href="/RESOURCES/home/public/style/style.css">
    <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
    <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css" rel="stylesheet" type="text/css" />
    <title>Perfume Store</title>
  </head>
  <body>
    <div class="container-fluid">
      <div class="row no-gutters">
        <div class="col-md-12 header">
          <video id="bg-video" autoplay loop muted>
            <source src="/RESOURCES/videos/header.mp4" type="video/mp4">
          </video>
          <h1>xxiv Store</h1>
          <h2>You deserve to be happy</h2>
          <button>FOLLOW ME</button>
          <p>Chào mừng các bạn đến với XXIII store - một tiệm nước hoa
            nho nhỏ lấy cảm hứng từ những con người có cảm xúc đặc
            biệt với hương thơm mê hoặc.</p>
        </div>
      </div>

      <!--Navbar section-->
      <div class="row">
        <div class="col-md-12 nav">
          <c:choose>
            <c:when test='<%= isAdmin%>'>
              <jsp:include page="/NAVBAR/AdminNavbar.jsp"></jsp:include>
            </c:when>
            <c:otherwise>
              <jsp:include page="/NAVBAR/ClientNavbar.jsp"></jsp:include>
            </c:otherwise>
          </c:choose>
        </div>
      </div>



      <div class="row">
        <div class="col-md-12 brand">
          <h1>Thương hiệu nổi tiếng</h2>

            <div class="logo">
              <a href="/Product/List/BrandID/22"><img
                  src="/RESOURCES/images/brands/159133030_1044233219399119_4321418372070751780_n.png"
                  alt=""></a>
              <a href="/Product/List/BrandID/38"><img
                  src="/RESOURCES/images/brands/Hang-nuoc-hoa-Imaginary-Authors.png"
                  alt=""></a>
              <a href="/Product/List/BrandID/51"><img
                  src="/RESOURCES/images/brands/Hang-nuoc-hoa-Maison-Matine.png"
                  alt=""></a>
              <a href="/Product/List/BrandID/48"><img
                  src="/RESOURCES/images//brands/LOrchestre-Parfums.png"
                  alt=""></a>
              <a href="/Product/List/BrandID/61"><img src="/RESOURCES/images/brands/Nasomatto.png"
                                                      alt=""></a>
              <a href="/Product/List/BrandID/16"><img
                  src="/RESOURCES/images/brands/nuoc-hoa-by-kilian.png"
                  alt="" class="kilian"></a>
              <a href="/Product/List/BrandID/27"><img src="/RESOURCES/images/brands/nuoc-hoa-dior.png"
                                                      alt=""></a>
              <a href="/Product/List/BrandID/46"><img
                  src="/RESOURCES/images/brands/nuoc-hoa-le-labo.png"
                  alt="" class="lelabo"></a>
              <a href="/Product/List/BrandID/74"><img
                  src="/RESOURCES/images/brands/nuoc-hoa-tomford.png"
                  alt="" class="tomford"></a>
              <a href="/Product/List/BrandID/79"><img src="/RESOURCES/images/brands/nuoc-hoa-ysl.png"
                                                      alt=""></a>
              <a href="/Product/List/BrandID/66"><img src="/RESOURCES/images/brands/Orto-Parisi.png"
                                                      alt=""></a>
              <a href="/Product/List/BrandID/80"><img src="/RESOURCES/images/brands/Zoologist.png"
                                                      alt=""></a>
              <hr>  
            </div>

        </div>
      </div>

      <div class="row">
        <div class="product-container">

          <div class="col-md-12 product">
            <h1>Sản phẩm nổi bật</h1>
            <div class="classify">
              <button class="btnMan">Nước hoa nam</button>
              <button class="btnWoman">Nước hoa nữ</button>
              <button class="btnUnisex">Unisex</button>
            </div>

            <div class="item man active">

              <c:if test='<%= maleProductList.size() != 0%>'>
                <c:forEach var="i" begin='0' end="<%= maleProductList.size() > 9 ? 8 : maleProductList.size() - 1%>">
                  <%
                                        index = (int) pageContext.getAttribute("i");
                                        p = maleProductList.get(index);
                                        b = bDAO.getBrand(p.getBrandId());

                                        id = p.getId();
                                        name = p.getName().equals(null) ? "" : p.getName();
                                        brand = b.getName().equals(null) ? "" : b.getName();
                                        imgUrl = p.getImgURL().equals(null) ? "" : p.getImgURL();
                                        price = Converter.covertIntergerToMoney(p.getStock().getPrice());
                  %>
                  <a href="/Product/Detail/ID/<%= id%>">
                    <div class="card">
                      <img src="<%= imgUrl%>" alt="<%= name%>" class="product-img">
                      <span class="product-brand"><%= brand%></span>
                      <hr>
                      <span class="product-name"><%= name%></span>
                      <span class="product-price"><%= price%> <span>đ</span></span>
                    </div>
                  </a>
                </c:forEach>
              </c:if>
            </div>

            <div class="item woman">
              <c:if test='<%= femaleProductList.size() != 0%>'>
                <c:forEach var="i" begin='0' end="<%= femaleProductList.size() > 9 ? 8 : femaleProductList.size() - 1%>">
                  <%
                                        index = (int) pageContext.getAttribute("i");
                                        p = femaleProductList.get(index);
                                        b = bDAO.getBrand(p.getBrandId());

                                        id = p.getId();
                                        name = p.getName().equals(null) ? "" : p.getName();
                                        brand = b.getName().equals(null) ? "" : b.getName();
                                        imgUrl = p.getImgURL().equals(null) ? "" : p.getImgURL();
                                        price = Converter.covertIntergerToMoney(p.getStock().getPrice());
                  %>
                  <a href="/Product/Detail/ID/<%= id%>">
                    <div class="card">
                      <img src="<%= imgUrl%>" alt="<%= name%>" class="product-img">
                      <span class="product-brand"><%= brand%></span>
                      <hr>
                      <span class="product-name"><%= name%></span>
                      <span class="product-price"><%= price%> <span>đ</span></span>
                    </div>
                  </a>
                </c:forEach>
              </c:if>
            </div>

            <div class="item unisex">
              <c:if test='<%= unisexProductList.size() != 0%>'>
                <c:forEach var="i" begin='0' end="<%= unisexProductList.size() > 9 ? 8 : unisexProductList.size() - 1%>">
                  <%
                                        index = (int) pageContext.getAttribute("i");
                                        p = unisexProductList.get(index);
                                        b = bDAO.getBrand(p.getBrandId());

                                        id = p.getId();
                                        name = p.getName().equals(null) ? "" : p.getName();
                                        brand = b.getName().equals(null) ? "" : b.getName();
                                        imgUrl = p.getImgURL().equals(null) ? "" : p.getImgURL();
                                        price = Converter.covertIntergerToMoney(p.getStock().getPrice());
                  %>
                  <a href="/Product/Detail/ID/<%= id%>">
                    <div class="card">
                      <img src="<%= imgUrl%>" alt="<%= name%>" class="product-img">
                      <span class="product-brand"><%= brand%></span>
                      <hr>
                      <span class="product-name"><%= name%></span>
                      <span class="product-price"><%= price%> <span>đ</span></span>
                    </div>
                  </a>
                </c:forEach>
              </c:if>
            </div>

            <div class="dots">
              <span class="dot active"></span>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12 reason">
          <hr>
          <h1>Tại sao chọn xxiv store</h1>
          <div class="listReason">
            <div class="advantage">
              <img src="/RESOURCES/images/icons/grommet-icons_shield-security.svg" alt="">
              <h4>Sản phẩm chính hãng
              </h4>
              <p>Sản phẩm nước hoa được mua trực tiếp tại các store ở nước ngoài hoặc làm việc trực tiếp với các hãng, cam kết authentic 100%
              </p>
            </div>
            <div class="advantage">
              <img src="/RESOURCES/images/icons/free-ship.svg" alt="">
              <h4>Free ship toàn quốc
              </h4>
              <p>XXIV áp dụng freeship cho tất cả các khách hàng trên toàn quốc. Chúng tôi chưa áp dụng hình thức giao hàng quốc tế tại thời điểm này</p>
            </div>
            <div class="advantage">
              <img src="/RESOURCES/images/icons/gift.svg" alt="">
              <h4>Thành viên thân thiết
              </h4>
              <p>Thành viên vàng sẽ được giảm 5% / đơn hàng. Với thành viên bạc khách được giảm 3% / đơn hàng.</p>
            </div>
          </div>
        </div>
      </div>



      <div class="row no-gutters">
        <div class="theme ">
          <img src="/RESOURCES/images/icons/perfumeStore.jpg">
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
        <div class="col-md-12 contact">
          <hr>
          <h1>xxiv store</h1>
          <div>
            <img src="/RESOURCES/images/icons/location-pin.png">
            <p>Số 25 Ngõ Thái Hà, Đống Đa, Hà Nội | 525/44 Tô Hiến Thành, P14, Q10, TP. Hồ Chí Minh</p>
          </div>
          <div>
            <img src="/RESOURCES/images/icons/smartphone.png">
            <p>090 721 9889| 093 194 8668</p>
            <img src="/RESOURCES/images/icons/email.png" class="mail-icon">
            <p>xxiv.fragrance@gmail.com</p>
          </div>
          <div>
            <p>Giờ mở cửa: Các ngày trong tuần từ 9:00 - 21:00</p>
          </div>
        </div>
      </div>



      <div class="row">
        <div class="col-md-12 footer">
          <div>
            <h2>XXIV store</h2>
            <ul>
              <li><a href="">Ưu đãi thành viên</a></li>
              <li><a href="">Tài khoản</a></li>
              <li><a href="">Tuyển dụng</a></li>
            </ul>
          </div>
          <div>
            <h2>Chính sách bán hàng</h2>
            <ul>
              <li><a href="">Phương thức vận chuyển</a></li>
              <li><a href="">Câu hỏi thường gặp</a></li>
              <li><a href="">Điều khoản và điện kiện sử dụng</a></li>
              <li><a href="">Điều khoản và điều kiện bán hàng</a></li>
              <li><a href="">Thông báo pháp lý</a></li>
            </ul>
          </div>
          <div>
            <h2>Thông tin chung</h2>
            <ul>
              <li><a href="">Giới thiệu</a></li>
              <li><a href="">Blog</a></li>
              <li><a href="">Liên hệ</a></li>
              <li><a href="">Sản phẩm</a></li>
            </ul>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12 copyright">
          <p>&copy; xxiv 2023 | all right reserved</p>
        </div>
      </div>

      <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
      crossorigin="anonymous"></script>
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
