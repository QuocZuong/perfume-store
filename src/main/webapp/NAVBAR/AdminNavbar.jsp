
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <style>
            .nav .account #product-dropdown-btn + ul{
                visibility: hidden;
            }

            .nav .account #user-dropdown-btn + ul{
                visibility: hidden;
            }

            .drop-down-show{
                visibility: visible !important;
            }

            .drop-down-show {
                opacity: 0.95 !important;
            }

            .nav .account #product-dropdown-btn + ul.drop-down-show {
                height: 220px !important;
            }
            .nav .account #user-dropdown-btn + ul.drop-down-show {
                height: 220px !important;
            }

            /*new style*/
            .nav {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px;
                width: 100%;
                border-bottom: 1px solid black;
            }

            .nav ul {
                float: left;
                display: flex;
                justify-content: center;
                align-items: center;
                list-style: none;
                margin: 0;
                padding-left: 2rem;
            }

            .nav li a {
                font-size: 1.2rem;
                margin: 0 10px;
                cursor: pointer;
                border-bottom: 1px solid transparent;
                transition: border-bottom 0.3s ease-in-out;
                color: black;
                list-style-type: none;
                text-decoration: none;
            }

            .nav li a:first-child {
                border-bottom: 1px solid transparent;
            }

            .nav li a:hover {
                border-bottom: 1px solid black;
                transition: border-bottom 0.3s ease-in-out;
            }

            .nav .iconPage {
                margin-left: -235px;
            }

            .nav .account {
                display: flex;
                align-items: center;
                padding-right: 2rem;
            }

            .nav .account img {
                width: 23px;
                height: 23px;
            }

            .nav .account button img {
                opacity: 0.7;
            }

            .nav .account a {
                margin: 0 10px;
                background-color: transparent;
                border: none;
                outline: none;
                cursor: pointer;
            }

            .nav .account #product-dropdown-btn,
            .nav .account #user-dropdown-btn {
                margin-right: 10px;
            }

            .nav .account #product-dropdown-btn + ul,
            .nav .account #user-dropdown-btn + ul {
                display: flex;
                flex-flow: column nowrap;
                background-color: white;
                margin-top: 1.6rem;
                top: 30px;
                opacity: 0;
                height: 0px;
                overflow: hidden;
                transition: all 0.3s ease;
            }

            .nav .account #product-dropdown-btn + ul {
                right: 60px;
            }
            .nav .account #user-dropdown-btn + ul {
                right: 8px;
            }

            .nav .account button img {
                width: 23px;
                height: 23px;
                opacity: 0.7;
            }

            .drop-down-show {
                opacity: 0.95 !important;
            }

            .nav .account #product-dropdown-btn + ul.drop-down-show {
                height: 180px !important;
            }
            .nav .account #user-dropdown-btn + ul.drop-down-show {
                margin-top: 30px;
                height: 180px !important;
            }
        </style>
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
                <li class="py-3 text-dark"><a href="/Admin/Product/Add">Thêm sản phẩm</a></li>
                <li class="pb-3 text-dark"><a href="/Admin/Product/List">Danh sách sản phẩm</a></li>
                <li class="pb-3 text-dark"><a href="/Admin/Import/Store">Thêm số lượng sản phẩm</a></li>
            </ul>

            <button class="droppown-btn bg-transparent border-0" id="user-dropdown-btn">
                <img src="/RESOURCES/images/icons/group.png" alt="" />
            </button>
            <ul class="shadow position-absolute align-items-start ps-1 pt-2 pb-2" >
                <li class="py-2 text-dark"><a href="/Admin/User/List">Danh sách người dùng</a></li>
                <li class="py-2 text-dark"><a href="/Admin/EmployeeActivityLog/Admin">Hoạt động admin</a></li>
                <li class="py-2 text-dark"><a href="/Admin/EmployeeActivityLog/OrderManager">Hoạt động order manager</a></li>
                <li class="py-2 text-dark"><a href="/Admin/EmployeeActivityLog/InventoryManager">Hoạt động inventory manager</a></li>
            </ul>

            <a href="/Admin/User"><img src="/RESOURCES/images/icons/user.png" alt="" /></a>
        </div>
        
    </body>
</html>
