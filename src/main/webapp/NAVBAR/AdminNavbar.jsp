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
            <li><a href="/">trang chủ</a></li>
            <li><a href="/home/introduction">giới thiệu</a></li>
            <li><a href="/home/brand">thương hiệu</a></li>
            <!-- This link to shop servlet file. DO NOT MODIFY the link -->
            <li><a href="/Product/List">sản phẩm</a></li>
        </ul>
        <a href="/" class="iconPage"><img src="/RESOURCES/images/icons/icon.webp" alt="" height="64" /></a>

        <div class="account">
            <button class="droppown-btn bg-transparent border-0" id="product-dropdown-btn">
                <img src="/RESOURCES/images/icons/shopping-bag-alone.png" alt="" />
            </button>
            <ul class="shadow position-absolute align-items-start ps-1 pt-2">
                <li class="py-3 text-dark"><a href="/Admin/Product/Add">Thêm sản phẩm</a></li>
                <li class="pb-3 text-dark"><a href="/Admin/Product/List">Danh sách sản phẩm</a></li>
            </ul>

            <button class="droppown-btn bg-transparent border-0" id="user-dropdown-btn">
                <img src="/RESOURCES/images/icons/group.png" alt="" />
            </button>
            <ul class="shadow position-absolute align-items-start ps-1 pt-5 pb-5">
                <li class="py-2 text-dark"><a href="/Admin/User/List">Danh sách người dùng</a></li>
                <li class="py-2 text-dark"><a href="/Admin/EmployeeActivityLog/Admin">Hoạt động admin</a></li>
                <li class="py-2 text-dark"><a href="/Admin/EmployeeActivityLog/OrderManager">Hoạt động order manager</a></li>
                <li class="py-2 text-dark"><a href="/Admin/EmployeeActivityLog/InventoryManager">Hoạt động inventory manager</a></li>
            </ul>

            <a href="/Admin/User"><img src="/RESOURCES/images/icons/user.png" alt="" /></a>
        </div>
    </body>
</html>
