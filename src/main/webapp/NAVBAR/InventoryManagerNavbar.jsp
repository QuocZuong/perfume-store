<%-- Document : AdminNavbar.jsp Created on : Sep 5, 2023, 8:41:33 PM Author : Acer --%> <%@page contentType="text/html"
                                                                                                pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>JSP Page</title>
        <style>

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
                height: 108px !important;
            }
            .nav .account #user-dropdown-btn + ul.drop-down-show {
                margin-top: 30px;
                height: 180px !important;
            }




        </style>
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
                <li class="py-3 text-dark"><a href="/InventoryManager/Product/List">Nhập hàng</a></li>
                <li class="py-3 text-dark"><a href="/InventoryManager/ImportCart">Import Cart</a></li>
            </ul>
            <a href="/InventoryManager/User"><img src="/RESOURCES/images/icons/user.png" alt="" /></a>
        </div>
    </body>
</html>
