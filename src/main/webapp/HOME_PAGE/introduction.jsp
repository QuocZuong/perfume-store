<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.Cookie"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%
    Cookie currentUserCookie = (Cookie) pageContext.getAttribute("userCookie", pageContext.SESSION_SCOPE);
    boolean isAdmin = false;

    if (currentUserCookie != null) {
        isAdmin = currentUserCookie.getName().equals("Admin");
    }
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
        <link rel="stylesheet" href="/RESOURCES/introduction/public/style/style.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css" rel="stylesheet" type="text/css" />
        <style>
            .left-align {
                text-align: left;
                margin-left: 20px !important;
            }
        </style>
        <title>Giới thiệu</title>
    </head>
    <body>
        <div class="container-fluid">

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

            <div class="row main">

                <div class="col-md-12 content">
                    <h1>XXIV team</h1>
                    <div class="row group">
                        <img src="/RESOURCES/images/icons/brainstorm.png" alt="">
                        <div class="text">
                            <h4>Group 2</h4>
                            <p>Tại xxiv chúng mình luôn đặt chất lượng và lòng tin với khách hàng lên hàng đầu. cũng vì một phần có gia đình và bạn bè đang ở pháp, chính tay lựa chọn từ store nên xxiv tự tin 100% hàng chính hãng. bọn mình sẽ không cam kết bán giá rẻ nhất và cạnh tranh với các bên khác mà chỉ cam kết sẽ bán giá tốt nhất chúng mình có thể. những sản phẩm chúng mình tư vấn và giới thiệu đều là các sản phẩm đã trực tiếp sử dụng và trải nghiệm để đưa ra lời khuyên thực tế giúp các khách hàng hài lòng.</p>
                        </div>
                    </div>
                    <div class="row story">
                        <div class="text">
                            <h4>Câu chuyện về xxiv store</h4>
                            <p>Tại xxiv chúng mình luôn đặt chất lượng và lòng tin với khách hàng lên hàng đầu. cũng vì một phần có gia đình và bạn bè đang ở pháp, chính tay lựa chọn từ store nên xxiv tự tin 100% hàng chính hãng. bọn mình sẽ không cam kết bán giá rẻ nhất và cạnh tranh với các bên khác mà chỉ cam kết sẽ bán giá tốt nhất chúng mình có thể. những sản phẩm chúng mình tư vấn và giới thiệu đều là các sản phẩm đã trực tiếp sử dụng và trải nghiệm để đưa ra lời khuyên thực tế giúp các khách hàng hài lòng.</p>
                        </div>
                        <img src="/RESOURCES/images/icons/introduce.webp" alt="">

                    </div>
                    <div class="row story2">
                        <img src="/RESOURCES/images/icons/introduce2.png" alt="">
                        <div class="text">
                            <p>Tại xxiv chúng mình luôn đặt chất lượng và lòng tin với khách hàng lên hàng đầu. cũng vì một phần có gia đình và bạn bè đang ở pháp, chính tay lựa chọn từ store nên xxiv tự tin 100% hàng chính hãng. bọn mình sẽ không cam kết bán giá rẻ nhất và cạnh tranh với các bên khác mà chỉ cam kết sẽ bán giá tốt nhất chúng mình có thể. những sản phẩm chúng mình tư vấn và giới thiệu đều là các sản phẩm đã trực tiếp sử dụng và trải nghiệm để đưa ra lời khuyên thực tế giúp các khách hàng hài lòng.</p>
                        </div>
                    </div>
                    <div class="row thanks">
                        <div class="text">
                            <h1>Cảm ơn các bạn rất nhiều vì đã tin tưởng và lựa chọn xxiv.</h1>
                            <p>Các bạn có thể đến với xxiv, tâm sự với chúng mình, cùng chia sẻ cảm nhận của các bạn về các
                                loại nước hoa bạn thích. với các bạn đang đắn đo hay sử dụng lần đầu cũng đừng ngại nhé,
                                mình sẽ cố gắng trả lời các bạn nhiều nhất, review sản phẩm tốt nhất để các bạn chọn được
                                hương thơm ưng ý nhất.
                                xxiv love you!</p>
                        </div>
                    </div>
                </div>
            </div>


<!--            <div class="tableTeam">

                <h1 class="mb-5">Bảng phân công công việc</h1>
                <table class="table table-hover" style="font-size: 20px;">

                    <thead class="thead-dark">
                        <tr class="bg-primary">
                            <th scope="col" class="table-dark">Họ Tên</th>
                            <th scope="col" class="table-dark">Tag</th>
                            <th scope="col" class="table-dark w-45 left-align ms-2">Công Việc</th>
                            <th scope="col" class="table-dark">Mức độ hoàn thành công việc</th>
                            <th scope="col" class="table-dark">Tỉ lệ đóng góp trên toàn bộ project</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr class="table-active">
                            <td >Nguyễn Lê Tài Đức</td>
                            <td></td>
                            <td class="left-align"></td>
                            <td></td>
                            <td>25%</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Front End</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia fix bug nhỏ (css, js, boostrap, ...)</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia sử dụng Jquery để validation các form</td>
                            <td>33.3%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Back End</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code login, register cho LogController</td>
                            <td>10%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm HomeController</td>
                            <td>40%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm ProductController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm ClientController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm AdminController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Làm một số function trong BrandDAO</td>
                            <td>33.3%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Làm một số function trong CartDAO</td>
                            <td>50%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Làm một số function trong OrderDAO</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Làm một số function trong ProductDAO</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Làm một số function trong UserDAO</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Clean code để dễ quản lí function, class</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Paging cho Product/List (Java)</td>
                            <td>50%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Search cho Product (Java)</td>
                            <td>50%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Database</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Lấy dữ liệu database (web scrapping)</td>
                            <td>20%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia thiết kế database</td>
                            <td>50%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Khác</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Sử dụng Vietnam Province API để chọn địa chỉ</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Cấu trúc thành model MVC</td>
                            <td>35%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Exception Handling</td>
                            <td>90%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Gửi mail đến user</td>
                            <td>33.3%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code filter userValidation</td>
                            <td>20%</td>
                            <td></td>
                        </tr>
                        <tr class="table-active">
                            <td>Lê Bá Thịnh</td>
                            <td></td>
                            <td class="left-align"></td>
                            <td></td>
                            <td>25%</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Front End</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia sử dụng Jquery để validation các form</td>
                            <td>33.3%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Back End</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm HomeController</td>
                            <td>40%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm ProductController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm ClientController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm AdminController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm LogController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Làm Subscription</td>
                            <td>50%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm function trong OrderDAO</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm function trong UserDAO</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Database</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Lấy dữ liệu database (web scrapping)</td>
                            <td>40%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Khác</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Cấu trúc thành model MVC</td>
                            <td>35%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Sử dụng outh2 API để upload ảnh lên Imgur lên database</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Gửi mail đến user</td>
                            <td>33.3%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia fix bug</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Exception Handling</td>
                            <td>10%</td>
                            <td></td>
                        </tr>
                        <tr class="table-active">
                            <td>Lê Quốc Vương</td>
                            <td></td>
                            <td class="left-align"></td>
                            <td></td>
                            <td>25%</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Front End</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Code plugin zoom ảnh cho trang sản phẩm</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm animation cho web</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang homePage</td>
                            <td>92%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang introduction</td>
                            <td>92%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang thương hiệu</td>
                            <td>92%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang sản phẩm</td>
                            <td>92%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang chi tiết sản phẩm</td>
                            <td>92%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang giỏ hàng</td>
                            <td>92%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang cho Client tương tác</td>
                            <td>92%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Làm trang báo lỗi</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia style toàn bộ trang jsp trong ADMIN_PAGE</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Làm sản phẩm liên quan</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia navbar cho admin</td>
                            <td>20%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Paging cho Product list (css)</td>
                            <td>50%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Back End</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm HomeController</td>
                            <td>20%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm ProductController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm ClientController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm AdminController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm LogController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Làm Subscription</td>
                            <td>50%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm function trong OrderDAO</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm function trong UserDAO</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Database</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Lấy dữ liệu database (web scrapping)</td>
                            <td>20%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Khác</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Cấu trúc thành model MVC</td>
                            <td>10%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Sử dụng outh2 API để upload ảnh lên Imgur lên database</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Gửi mail đến user</td>
                            <td>33.3%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia fix bug</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Exception Handling</td>
                            <td>10%</td>
                            <td></td>
                        </tr>
                        <tr class="table-active">
                            <td>Nguyễn Phi Long</td>
                            <td></td>
                            <td class="left-align"></td>
                            <td></td>
                            <td>25%</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Front End</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang homePage</td>
                            <td>8%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang introduction</td>
                            <td>8%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang thương hiệu</td>
                            <td>8%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang sản phẩm</td>
                            <td>8%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang chi tiết sản phẩm</td>
                            <td>8%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang giỏ hàng</td>
                            <td>8%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia làm front end trang cho Client tương tác</td>
                            <td>8%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Code trang checkout</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia navbar cho admin</td>
                            <td>80%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code jQuery validate cho các form</td>
                            <td>33.3%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Back End</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code login, register cho LogController</td>
                            <td>90%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code AdminController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code ClientController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code ProductController</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code DAOs UserDao</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code DAOs ProductDao</td>
                            <td>25%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code DAOs BrandDao</td>
                            <td>33.3%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code Models User</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code Models Product</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code Models Order</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>Database</td>
                            <td class="left-align"></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia thiết kế database</td>
                            <td>50%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Lấy dữ liệu database (web scrapping)</td>
                            <td>20%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Host database online cho project</td>
                            <td>100%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td class="left-align">Khác</td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Cấu trúc thành model MVC</td>
                            <td>20%</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="left-align">Tham gia code filter userValidation</td>
                            <td>80%</td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>-->

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
        <script src="/RESOURCES/introduction/public/js/main.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <script src="/RESOURCES/admin/product/public/js/main.js"></script>
        <script src="/RESOURCES/introduction/public/js/main.js"></script>
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
