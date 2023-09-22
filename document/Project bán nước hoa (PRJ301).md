<sup>Created: **19-06-2023** _22:55:09_</sup>

# Java Models

## class Product

-   _Variable_:
    -   id (int)
    -   tên (string)
    -   hãng (string)
    -   giá (double, float)
    -   gender (string)
    -   năm sx (string) 2003
    -   nhóm hương (string\[ ])
    -   mô tả (string)
    -   hình (string)
    -   dung tích (int) (ml)
-   _Function_:

## class Customer

-   _Variable_:
    -   sdt (string)
    -   username (string)
    -   password (string)
    -   họ và tên (string)
    -   email (string)
    -   address (string)
    -   cart (Cart)
-   _Function_:

## class Cart

-   _Variable_:
    -   Products (arrayList\<product>)
    -   Total (int)
-   _Function_:
    -   addProduct
    -   removeProduct

# Controllers

## Class HomeController

-   _Function_:
    -   Access to homepage ✅
    -   Access to introduction ✅
    -   Acesss to login/register
    -   Access to brand ✅

## Class ProductController

-   _Function_:
    -   List ✅
        -   Brand ✅
        -   Gender ✅
        -   Price ✅
        -   Paging ✅
    -   Detail ✅

## Class ClientController

-   Filter required ✅
-   _Function_:
    -   Able to Update Infomation ✅
        -   Update email, username, password and fullname ✅ chua validate
        -   Update PhoneNumber, Address ✅ chua validate
    -   Access to the cart ✅
        -   Add ✅
        -   Update ✅
        -   Delete ✅
    -   Able to watch order history ✅
    -   Able to Signout ✅
    -   Block unwanted URL with prefix "/Client"
    -   Pay ✅

## Class AdminController

-   Filter required
-   _Function_:
    -   Update Product:
        -   List ✅
        -   Add ✅
        -   Update ✅
        -   Delete ✅
        -   Search With Paging
        -   Use Imgur API to update API ✅
    -   Update Information:
        -   User's Info ✅
        -   User's Order
    -   Able to send mail to user

#

---

# DB Diagram

![[Pasted image 20230801141543.png]]

#

# SQL object

## Table Product

#### Show all product (useful while searching)

-   _Attribute_:
    -   ID (int (primary key) Indentity (1,1))
    -   Name (nvarchar(300))
    -   _BrandID (ID not null)_
    -   Price (Int default 0)
    -   Gender (nvarchar(50))
    -   Smell (nvarchar(200))
    -   Quantity (int default 0)
    -   ReleaseYear (SMALLINT default 2003)
    -   Volume (int default 100) (ml)
    -   ImgURL (nvarchar(max))
    -   Description (nvarchar(max))
    -   Active BIT DEFAULT 1

## Table Brand

#### Show all brand (useful while searching)

-   _Attribute_:
    -   ID (int primary key Indentity(1,1))
    -   Name (nvarchar(50))
    -   BrandLogo (nvarchar(max))
    -   BrandImg (nvarchar(max))
    -   TotalProduct (int default 0)

## Table User

-   _Attribute_:
    -   ID (int) (primary key) Indentity(1,1)
    -   Name (nvarchar(50))
    -   UserName (varchar(50))
    -   Password (varchar(32)) %%After **MD5** digestion%%
    -   PhoneNumber (varchar(10))
    -   Email (varchar(100))
    -   Address (nvarchar(500))
    -   Role (nvarchar(50))
    -   Active (BIT) DEFAULT 1

## Table Order

-   _Attribute_:
    -   _ID (int) (primary key Indentity (1,1))_
    -   _ClientID (ID (int) (duplicate) )_
    -   Date (Date) not null
    -   Address (nvarchar(500))
    -   PhoneNumber (varchar(10))
    -   Note (nvarchar(500))
    -   Sum (int default 0)

#### Weak Entity

## Table OrderDetail

-   _Attribute_:
    -   _orderID (int not null) (duplicate)_
    -   _productID( int not null ) (duplicate)_
    -   quantity (int default 0)
    -   Price (Int default 0)
    -   Sum (Int default 0)

## Table Cart

-   _Attribute_:
    -   _ClientID (int not null) (duplicate)_
    -   _productID( int not null ) (duplicate)_
    -   quantity (int default 0)
    -   Price (Int default 0)
    -   Sum (Int default 0)

%%## Table Role

-   _Attribute_:
    -   Role Name (varchar(50))
    -   Co the lam gi do (varchar(50)) (duplicate)%%

##

```sql

USE master;
GO

GO
ALTER DATABASE projectPRJ SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
GO

DROP DATABASE projectPRJ;
GO
USE master;

CREATE DATABASE projectPRJ;
GO

USE projectPRJ;
GO

CREATE TABLE Product (
    ID INT IDENTITY(1, 1) PRIMARY KEY,
    [Name] NVARCHAR(300),
    BrandID INT NOT NULL,
    Price INT DEFAULT 0,
    Gender NVARCHAR(50),
    Smell NVARCHAR(200),
    Quantity INT DEFAULT 0,
    ReleaseYear SMALLINT DEFAULT 2003,
    Volume INT DEFAULT 100,
    ImgURL NVARCHAR(MAX),
    [Description] NVARCHAR(MAX),
    Active BIT DEFAULT 1
);

CREATE TABLE Brand (
    ID INT IDENTITY(1, 1) PRIMARY KEY,
    [Name] NVARCHAR(50),
    BrandLogo NVARCHAR(MAX),
    BrandImg NVARCHAR(MAX),
    TotalProduct INT DEFAULT 0
);

CREATE TABLE [User] (
    ID INT IDENTITY(1, 1) PRIMARY KEY,
    [Name] NVARCHAR(50),
    UserName VARCHAR(50),
    [Password] VARCHAR(32),
    PhoneNumber VARCHAR(10),
    Email VARCHAR(100),
    [Address] NVARCHAR(500),
    [Role] NVARCHAR(50),
    Active BIT DEFAULT 1
);

CREATE TABLE [Order] (
    ID INT IDENTITY(1, 1) PRIMARY KEY,
    ClientID INT NOT NULL,
    [Date] DATE NOT NULL,
    Address NVARCHAR(500),
    PhoneNumber VARCHAR(10),
    Note NVARCHAR(500),
    Sum INT DEFAULT 0
);

CREATE TABLE OrderDetail (
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT DEFAULT 0,
    Price INT DEFAULT 0,
    [Sum] INT DEFAULT 0
);

CREATE TABLE Cart (
    ClientID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT DEFAULT 0,
    Price INT DEFAULT 0,
    [Sum] INT DEFAULT 0
);

ALTER TABLE Product
ADD FOREIGN KEY (BrandID) REFERENCES Brand(ID);

ALTER TABLE [Order]
ADD FOREIGN KEY (ClientID) REFERENCES [User](ID);

ALTER TABLE OrderDetail
ADD FOREIGN KEY (OrderID) REFERENCES [Order](ID);

ALTER TABLE OrderDetail
ADD FOREIGN KEY (ProductID) REFERENCES Product(ID);

ALTER TABLE Cart
ADD FOREIGN KEY (ClientID) REFERENCES [User](ID);

ALTER TABLE Cart
ADD FOREIGN KEY (ProductID) REFERENCES Product(ID);

```

#### Trigger, stored Procedure

```sql
--RETURN PRODUCT PRICE
GO
CREATE OR ALTER FUNCTION GetProductPrice(@ProductID INT)
RETURNS INT
AS
BEGIN
    DECLARE @OUT INT;
	SET @OUT = (SELECT Product.Price FROM Product WHERE Product.ID = @ProductID)
	RETURN @OUT
END;



--INSERT TO CART
GO
CREATE OR ALTER PROC INSERT_TO_CART @ClientID INT, @ProductID INT, @BuyQuantity INT
AS
BEGIN
	DECLARE @count INT = 0,
			@PriceProduct INT;
	SET @count = (SELECT COUNT(*) FROM Cart WHERE Cart.ClientID = @ClientID AND Cart.ProductID = @ProductID);
	SET	@PriceProduct = dbo.GetProductPrice(@ProductID);
	IF @count = 0
	BEGIN
		INSERT INTO Cart (ClientID, ProductID, Quantity) VALUES(@ClientID, @ProductID, @BuyQuantity);
	END
	IF @count != 0
	BEGIN
		UPDATE Cart
		SET Quantity = @BuyQuantity
		WHERE Cart.ClientID = @ClientID AND Cart.ProductID = @ProductID
	END
	UPDATE Cart
	SET Price = @PriceProduct, [Sum] = @BuyQuantity * @PriceProduct
	WHERE Cart.ClientID = @ClientID AND Cart.ProductID = @ProductID
END

--ADD ORDER DETAIL TRIGGER
GO
CREATE or alter TRIGGER AddOrderDetailTrigger ON [Order]
AFTER INSERT
AS
BEGIN
	declare @OrderID int,
			@ClientID int,
			@Sum int,
			@Date Date;
	select	@OrderID = inserted.ID,
			@ClientID = inserted.ClientID,
			@Sum = inserted.[Sum],
			@Date = inserted.[Date]
	from inserted
	--INSERT TO OrderDetail
	INSERT INTO OrderDetail(OrderID, ProductID, Quantity, Price, [Sum])
		SELECT
			[Order].ID as OrderID,
			Cart.ProductID as ProductID,
			Cart.Quantity as Quantity,
			Cart.Price as Price,
			Cart.[Sum] as [Sum]
		FROM Cart, [Order]
		WHERE Cart.ClientID = [Order].ClientID AND [Order].ID = @OrderID
	--Update Product Quantity in Product
	UPDATE Product SET Quantity = Product.Quantity - (SELECT Cart.Quantity FROM Cart WHERE Cart.ProductID = Product.ID AND Cart.ClientID = @ClientID)
	WHERE Product.ID IN (SELECT Cart.ProductID FROM Cart WHERE Cart.ClientID = @ClientID)
	--DELETE Cart
	DELETE FROM Cart WHERE Cart.ClientID = @ClientID
end
```

# Bảng phân công công việc

## Nguyễn Lê Tài Đức

-   _FrontEnd_:
    -   Paging cho Product/List và Lọc thuộc tính cho Product (search..)
-   _BackEnd_:
    -   Làm HomeController
    -   Làm ProductController
    -   Làm ClientController
    -   Làm AdminController
    -   Paging cho Product/List và Lọc thuộc tính cho Product (search..)
    -   Xử lý những ngoại lê cho trang web
-   _Database_:
    -   Viết thư viện để query database (Models, DAO)
    -   Lấy dữ liệu database ( web scrapping)
-   _Khác_:
    -   Sử dụng Vietnam Province API
    -   Cấu trúc thành model MVC
    -   Sử dụng Jquery để validation

## Lê Bá Thịnh

-   _FrontEnd_:
    -   Paging cho Product/List và Lọc thuộc tính cho Product (search..)
-   _BackEnd_:
    -   Làm HomeController
    -   Làm ProductController
    -   Làm ClientController
    -   Làm AdminController
    -   Paging cho Product/List và Lọc thuộc tính cho Product (search..)
-   _Database_:
    -   Viết thư viện để query database (Models, DAO)
    -   Lấy dữ liệu database ( web scrapping )
    -   Sử dụng outh2 API để upload ảnh lên Imgur lên database
    -   Gửi mail đến user
-   _Khác_:
    -   Cấu trúc thành model MVC

## Lê Quốc Vương

-   _FrontEnd_:
    -   Làm animation cho web
    -   Làm trang homePage
    -   Làm trang introduction
    -   Làm trang thương hiệu
    -   Làm trang sản phẩm
    -   Làm trang login
    -   Làm trang chi tiết sản phẩm
    -   Làm trang giỏ hàng
    -   Làm trang cho Client tương tác
    -   Style những trang jsp trong ADMIN_PAGE
-   _BackEnd_:
    -   Làm ClientController
    -   Làm AdminController
    -   Làm OrderDAO, OrderDetail
-   _Database_:
    -   Lấy dữ liệu database ( web scrapping)

## Nguyễn Phi Long

-   _FrontEnd_:
    -   Làm trang checkOut
-   _BackEnd_:
    -   Làm HomeController
    -   Làm ProductController
    -   Làm ClientController
    -   Làm AdminController
-   _Database_:
    -   Viết sql để tạo database
    -   Lấy dữ liệu database ( web scrapping)
-   _Khác_:
    -   Viết thư viện để query database (Models, DAO)
    -   Cấu trúc thành model MVC
    -   Làm filter authentication, authorization

#

| Họ Tên            | Tag            | Công Việc                                              | Mức độ hoàn thành công việc | Tỉ lệ đóng góp trên toàn bộ project |
| ----------------- | -------------- | ------------------------------------------------------ | :-------------------------: | :---------------------------------: |
| Nguyễn Lê Tài Đức | **FrontEnd**   |                                                        |                             |               **25%**               |
|                   |                | Tham gia fix bug nhỏ (css, js, boostrap, ...)          |             25%             |                                     |
|                   |                | Tham gia sử dụng Jquery để validation các form         |            33.3%            |                                     |
|                   | _BackEnd_      |                                                        |                             |                                     |
|                   |                | Tham gia code login, register cho LogController        |             10%             |                                     |
|                   |                | Tham gia làm HomeController                            |             40%             |                                     |
|                   |                | Tham gia làm ProductController                         |                             |                                     |
|                   |                | Tham gia làm ClientController                          |                             |                                     |
|                   |                | Tham gia làm AdminController                           |                             |                                     |
|                   |                | Làm một số function trong BrandDAO                     |            33.3%            |                                     |
|                   |                | Làm một số function trong CartDAO                      |             50%             |                                     |
|                   |                | Làm một số function trong OrderDAO                     |                             |                                     |
|                   |                | Làm một số function trong ProductDAO                   |                             |                                     |
|                   |                | Làm một số function trong UserDAO                      |                             |                                     |
|                   |                | Clean code để dễ quản lí function, class               |            100%             |                                     |
|                   |                | Paging cho Product/List (Java)                         |             50%             |                                     |
|                   |                | Search cho Product (Java)                              |             50%             |                                     |
|                   | **_Database_** |                                                        |                             |                                     |
|                   |                | Lấy dữ liệu database (web scrapping)                   |             20%             |                                     |
|                   |                | Tham gia thiết kế database                             |             50%             |                                     |
|                   | Khác           |                                                        |                             |                                     |
|                   |                | Sử dụng Vietnam Province API để chọn địa chỉ           |            100%             |                                     |
|                   |                | Cấu trúc thành model MVC                               |             35%             |                                     |
|                   |                | Exception Handling                                     |             90%             |                                     |
|                   |                | Gửi mail đến user                                      |            33.3%            |                                     |
|                   |                | Tham gia code filter userValidation                    |             20%             |                                     |
| Lê Bá Thịnh       | **FrontEnd**   |                                                        |                             |               **25%**               |
|                   |                | Tham gia sử dụng Jquery để validation các form         |            33.3%            |                                     |
|                   | _BackEnd_      |                                                        |                             |                                     |
|                   |                | Tham gia làm HomeController                            |             40%             |                                     |
|                   |                | Tham gia làm ProductController                         |                             |                                     |
|                   |                | Tham gia làm ClientController                          |                             |                                     |
|                   |                | Tham gia làm AdminController                           |                             |                                     |
|                   |                | Làm một số function trong BrandDAO                     |            33.3%            |                                     |
|                   |                | Làm một số function trong CartDAO                      |             50%             |                                     |
|                   |                | Làm một số function trong OrderDAO                     |                             |                                     |
|                   |                | Làm một số function trong ProductDAO                   |                             |                                     |
|                   |                | Làm một số function trong UserDAO                      |                             |                                     |
|                   |                | Paging cho Product/List (Java)                         |             50%             |                                     |
|                   |                | Paging cho Admin/Product/List (Java)                   |            100%             |                                     |
|                   |                | Paging cho Admin/User/List (Java)                      |            100%             |                                     |
|                   |                | Search cho Product/List (Java)                         |             50%             |                                     |
|                   |                | Search cho Admin/Product/List (Java)                   |            100%             |                                     |
|                   |                | Search cho User/Product/List (Java)                    |            100%             |                                     |
|                   | **_Database_** |                                                        |                             |                                     |
|                   |                | Viết các trigger cho database                          |            100%             |                                     |
|                   |                | Viết các procedure cho database                        |            100%             |                                     |
|                   |                | Lấy dữ liệu database (web scrapping)                   |             40%             |                                     |
|                   | Khác           |                                                        |                             |                                     |
|                   |                | Cấu trúc thành model MVC                               |             35%             |                                     |
|                   |                | Sử dụng outh2 API để upload ảnh lên Imgur lên database |            100%             |                                     |
|                   |                | Gửi mail đến user                                      |            33.3%            |                                     |
|                   |                | Tham gia fix bug                                       |                             |                                     |
|                   |                | Exception Handling                                     |             10%             |                                     |
| Lê Quốc Vương     | **FrontEnd**   |                                                        |                             |               **25%**               |
|                   |                | Code plugin zoom ảnh cho trang sản phẩm                |            100%             |                                     |
|                   |                | Tham gia làm animation cho web                         |            100%             |                                     |
|                   |                | Tham gia làm front end trang homePage                  |             92%             |                                     |
|                   |                | Tham gia làm front end trang introduction              |             92%             |                                     |
|                   |                | Tham gia làm front end trang thương hiệu               |             92%             |                                     |
|                   |                | Tham gia làm front end trang sản phẩm                  |             92%             |                                     |
|                   |                | Tham gia làm front end trang chi tiết sản phẩm         |             92%             |                                     |
|                   |                | Tham gia làm front end trang giỏ hàng                  |             92%             |                                     |
|                   |                | Tham gia làm front end trang cho Client tương tác      |             92%             |                                     |
|                   |                | Làm trang báo lỗi                                      |            100%             |                                     |
|                   |                | Tham gia style toàn bộ trang jsp trong ADMIN_PAGE      |            100%             |                                     |
|                   |                | Làm sản phẩm liên quan                                 |            100%             |                                     |
|                   |                | Tham gia navbar cho admin                              |             20%             |                                     |
|                   |                | Paging cho Product list (css)                          |             50%             |                                     |
|                   | _BackEnd_      |                                                        |                             |                                     |
|                   |                | Tham gia làm HomeController                            |             20%             |                                     |
|                   |                | Tham gia làm ProductController                         |                             |                                     |
|                   |                | Tham gia làm ClientController                          |                             |                                     |
|                   |                | Tham gia làm AdminController                           |                             |                                     |
|                   |                | Tham gia làm LogController                             |                             |                                     |
|                   |                | Làm Subscription                                       |            100%             |                                     |
|                   |                | Tham gia làm function trong OrderDAO                   |                             |                                     |
|                   |                | Tham gia làm function trong UserDAO                    |                             |                                     |
|                   | **_Database_** |                                                        |                             |                                     |
|                   |                | Lấy dữ liệu database (web scrapping)                   |             20%             |                                     |
|                   | Khác           |                                                        |                             |                                     |
|                   |                | Cấu trúc thành model MVC                               |             10%             |                                     |
| Nguyễn Phi Long   | **FrontEnd**   |                                                        |                             |               **25%**               |
|                   |                | Tham gia làm front end trang homePage                  |             8%              |                                     |
|                   |                | Tham gia làm front end trang introduction              |             8%              |                                     |
|                   |                | Tham gia làm front end trang thương hiệu               |             8%              |                                     |
|                   |                | Tham gia làm front end trang sản phẩm                  |             8%              |                                     |
|                   |                | Tham gia làm front end trang chi tiết sản phẩm         |             8%              |                                     |
|                   |                | Tham gia làm front end trang giỏ hàng                  |             8%              |                                     |
|                   |                | Tham gia làm front end trang cho Client tương tác      |             8%              |                                     |
|                   |                | Code trang checkout                                    |            100%             |                                     |
|                   |                | Tham gia navbar cho admin                              |             80%             |                                     |
|                   |                | Tham gia code jquery validate cho các form             |            33.3%            |                                     |
|                   |                | Paging cho Product list (css)                          |             50%             |                                     |
|                   |                | Tham gia fix bug nhỏ (css, js, boostrap, ...)          |                             |                                     |
|                   |                | Gửi mail đến user (Làm UI cho mail sender)             |            33.3%            |                                     |
|                   | _BackEnd_      |                                                        |                             |                                     |
|                   |                | Tham gia code login, register cho LogController        |             90%             |                                     |
|                   |                | Tham gia code AdminController                          |                             |                                     |
|                   |                | Tham gia code ClientController                         |                             |                                     |
|                   |                | Tham gia code ProductController                        |                             |                                     |
|                   |                | Tham gia code DAOs UserDao                             |                             |                                     |
|                   |                | Tham gia code DAOs ProductDao                          |                             |                                     |
|                   |                | Tham gia code DAOs BrandDao                            |            33.3%            |                                     |
|                   |                | Tham gia code Models User                              |            100%             |                                     |
|                   |                | Tham gia code Models Product                           |            100%             |                                     |
|                   |                | Tham gia code Models Order                             |            100%             |                                     |
|                   | **_Database_** |                                                        |                             |                                     |
|                   |                | Tham gia thiết kế database                             |             50%             |                                     |
|                   |                | Lấy dữ liệu database (web scrapping)                   |             20%             |                                     |
|                   |                | Host database online cho project                       |            100%             |                                     |
|                   | Khác           |                                                        |                             |                                     |
|                   |                | Cấu trúc thành model MVC                               |             20%             |                                     |
|                   |                | Tham gia code filter userValidation                    |             80%             |                                     |

---

<sub>Author:</sub> #🐲 #🦆
<sub>Tags:</sub> #Project #Java #JSP #SQL #Back-end #Front-end #Database
