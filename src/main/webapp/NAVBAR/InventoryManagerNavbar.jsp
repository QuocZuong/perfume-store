<%-- Document : AdminNavbar.jsp Created on : Sep 5, 2023, 8:41:33 PM Author : Acer --%> <%@page contentType="text/html"
                                                                                                pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>JSP Page</title>
    </head>
    <body>
        <ul>
            <li><a href="/">Trang chủ</a></li>
            <li><a href="/home/introduction">Giới thiệu</a></li>
            <li><a href="/home/brand">Thương hiệu</a></li>
            <!-- This link to shop servlet file. DO NOT MODIFY the link -->
            <li><a href="/Product/List">Sản phẩm</a></li>
        </ul>
        <a href="/" class="iconPage"><img src="/RESOURCES/images/icons/icon.webp" alt="" height="64" /></a>

        <div class="account">
            <button class="droppown-btn bg-transparent border-0" id="product-dropdown-btn">
                <img src="/RESOURCES/images/icons/shopping-bag-alone.png" alt="" />
            </button>
            <ul class="shadow position-absolute align-items-start ps-1 pt-2">
                <li class="py-3 text-dark"><a href="/InventoryManager/Product/List">Nhập hàng</a></li>
                <li class="py-3 text-dark"><a href="/InventoryManager/ImportCart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a></li>
            </ul>
            <a href="/InventoryManager/User"><img src="/RESOURCES/images/icons/user.png" alt="" /></a>
        </div>
    </body>
</html>
