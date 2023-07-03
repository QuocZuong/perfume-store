<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <link rel="stylesheet" href="./public/style/style.css">
        <link rel="icon" href="../resources/images/icons/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css" rel="stylesheet" type="text/css" />
        <title>Thương hiệu</title>
    </head>
    <body>
        <div class="container-fluid">

            <div class="row">
                <div class="col-md-12 nav">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/index.html">trang chủ</a></li>
                        <li><a href="${pageContext.request.contextPath}/introduction/index.jsp">giới thiệu</a></li>
                        <li><a href="${pageContext.request.contextPath}/brand/index.jsp">thương hiệu</a></li>
                        <!-- This link to shop servlet file. DO NOT MODIFY the link -->
                        <li><a href="${pageContext.request.contextPath}/shop">sản phẩm</a></li>
                        <li><a href="">blog</a></li>
                    </ul>
                    <a href="../home/index.html"><img src="../resources/images/icons/icon.webp" alt=""
                            height="64"></a>
                    <div class="account">
                        <a><img src="../resources/images/icons/search.png" alt=""></a>
                        <a href="../logIn/index.jsp"><img src="../resources/images/icons/user.png" alt=""></a>
                        <a><img src="../resources/images/icons/cart.png" alt=""></a>
                    </div>
                </div>
            </div>

           
            <div class="main">
                <h1>thương hiệu</h1>
                <div id="searchBox" class="search-box">
                    <a href="" class="character">ALL BRANDS</a>
                    <a href="" class="character">A</a>
                    <a href="" class="character">B</a>
                    <a href="" class="character">C</a>
                    <a href="" class="character">D</a>
                    <a href="" class="character">E</a>
                    <a href="" class="character">F</a>
                    <a href="" class="character">G</a>
                    <a href="" class="character">H</a>
                    <a href="" class="character">I</a>
                    <a href="" class="character">J</a>
                    <a href="" class="character">K</a>
                    <a href="" class="character">L</a>
                    <a href="" class="character">M</a>
                    <a href="" class="character">N</a>
                    <a href="" class="character">O</a>
                    <a href="" class="character">P</a>
                    <a href="" class="character">Q</a>
                    <a href="" class="character">R</a>
                    <a href="" class="character">S</a>
                    <a href="" class="character">T</a>
                    <a href="" class="character">U</a>
                    <a href="" class="character">V</a>
                    <a href="" class="character">W</a>
                    <a href="" class="character">X</a>
                    <a href="" class="character">Y</a>
                    <a href="" class="character">Z</a>
                </div>

                <div class="box-brand" id="boxBrand">
                    <div class="brands">
                        <h3>A</h3>
                        <a href="" class="brand">Maison Francis Kurkdjian</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>B</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">L'Occitane en Provence</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>C</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>D</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>E</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>F</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>G</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>H</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>I</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>J</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>K</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>L</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>M</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>N</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>O</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>P</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>R</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>S</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>T</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>V</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>X</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>Y</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                    <div class="brands">
                        <h3>Z</h3>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                        <a href="" class="brand">Acqua di Parma</a>
                    </div>
                </div>
                  
            </div>


            <div class="row">
                <div class="col-md-12 register">
                    <h1>Đăng ký thành viên để nhận khuyến mại</h1>
                    <p>Theo dõi chúng tôi để nhận thêm nhiều ưu đãi</p>
                    <form action="">
                        <input type="text" name="" id="" placeholder="nhập email">
                        <button>ĐĂNG KÝ</button>
                    </form>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 social">
                    <a href=""><img src="..//resources/images/icons/instagram.png" alt=""></a>
                    <a href=""><img src="..//resources/images/icons/facebook.png" alt=""></a>
                    <a href=""><img src="..//resources/images/icons/youtube.png" alt=""></a>
                    <a href=""><img src="..//resources/images/icons/location-pin.png" alt=""></a>
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
                    <p>&copy; xxiv 2023 | all rigth reserved</p>
                </div>
            </div>

        </div>


        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"></script>
        <script src="./public/js/main.js"></script>
    </body>
</html>