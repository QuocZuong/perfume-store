<%-- 
    Document   : ClientNavbar
    Created on : Sep 5, 2023, 8:36:18 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Perfume Store</title>
    </head>
    <body>
        
        <ul>
            <li><a href="/">Trang chủ</a></li>
            <li> <a href="/home/introduction">Giới thiệu</a></li>
            <li><a href="/home/brand">Thương hiệu</a></li>
            <!-- This link to shop servlet file. DO NOT MODIFY the link -->
            <li><a href="/Product/List">Sản phẩm</a></li>
        </ul>
        <a href="/" class="iconPage"><img src="/RESOURCES/images/icons/icon.webp" alt=""
                                          height="64"></a>
        <div class="account">
            <a href="/Customer/User"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
            <a href="/Customer/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>
        </div>
        
    </body>
</html>
