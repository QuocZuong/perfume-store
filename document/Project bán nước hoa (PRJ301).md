<sup>Created: **19-06-2023** *22:55:09*</sup>


ERD
![](https://lh3.googleusercontent.com/nW0ksWTJjKRoQdp-CXCN2FixbfuxYRwPEXctqXNRqJJ4024T4mnveSdbJvLCq52FitFTLkoFPCVk3GQj5toaz42CQ52gJjidcUznA2HGBbdMxaSPnRGnI25x5o82xYsWcklGi-NhStV3LaUYNcsAK9s)



# DB Diagram

#

# SQL Entity

## Table Brand
#### Show all brand (useful while searching)
+ *Attribute*:
	+ **Brand_ID (int not null Indentity(1,1))** 
	+ Brand_Name (nvarchar(50))
	+ Brand_Logo (nvarchar(max))
	+ Brand_Img (nvarchar(max))
	+ Brand_Total_Product (int default 0)

## Table Stock
#### Weak entity
+ *Attribute*:
	+ *Product_ID (int not null)*
	+ Price (Int default 0)
	+ Quantity (int default 0)

## Table Product
#### Show all product (useful while searching)
+ *Attribute*:
	+ **Product_ID (int not null Indentity (1,1))**
	+ Product_Name (nvarchar(300))
	+ *Brand_ID (ID not null)*
	+ Product_Gender (nvarchar(50))
	+ Product_Smell (nvarchar(200))
	+ Product_Release_Year (SMALLINT default 2003) 
	+ Product_Volume (int default 100) (ml)
	+ Product_Img_URL (nvarchar(max))
	+ Product_Description (nvarchar(max))
	+ Product_Active BIT DEFAULT 1

## Table CartItem
#### Weak Entity
+ *Attribute*:
	+ *Customer_ID (int not null) (duplicate)*
	+ *Product_ID( int not null ) (duplicate)*
	+ Quantity (int default 0)
	+ Price (Int default 0)
	+ Sum (Int default 0)

## Table Voucher:
+ *Attribute*:
	+ **Voucher_ID (int not null Indetity(1,1))** 
	+ Voucher_Code (nvarchar(20))
	+ Voucher_Quantity (int not null)
	+ Voucher_Discount_Percent (int not null)
	+ Voucher_Discount_Max (int not null)
	+ Voucher_Created_At (BIGINT not null)
	+ Voucher_Expired_At (BIGINT)
	+ *Voucher_Created_By_Admin (int not null)*

## Table Voucher_Product
+ *Attribute*:
	+ *Voucher_ID (int not null) (duplicate)*
	+ *Product_ID (int not null) (duplicate)*


## Table OrderDetail
####  Weak Entity
+ *Attribute*:
	+ *Order_ID (int not null) (duplicate)*
	+ *Product_ID (int not null) (duplicate)*
	+ Quantity (int default 0)
	+ Price (Int default 0)
	+ Total (Int default 0)

## Table Order
+ *Attribute*:
	+ **Order_ID (int not null Indentity (1,1))**
	+ *Customer_ID ( int not null (duplicate) )*
	+ *Voucher_ID (int not null)*
	+ Order_Receiver_Name (nvarchar(200) not null)
	+ Order_Delivery_Address (nvarchar(300) not null)
	+ Order_Phone_Number (varchar(10) not null)
	+ Order_Note (nvarchar(500))
	+ Order_Total (int default 0 not null)
	+ Order_Deducted_Price (int default 0)
	+ Order_Status (varchar(20) not null)
	+ Order_Created_At (BIGINT not null)
	+ Order_Checkout_At (BIGINT)
	+ Order_Update_At (BIGINT)
	+ *Order_Update_By_Order_Manager (int not null)*


## Table Customer
#### Inherited table User
+ *Attribute*:
	+ **Customer_ID (int not null Indentity(1,1))** 
	+ *User_ID (int not null)*
	+ Customer_Credit_Point (int not null) 

## Table DeliveryAddress
#### Weak Entity
+ *Attribute*:
	+ *Customer_ID (int not null) (duplicate)*
	+ Receiver_Name (nvarchar(200) not null)
	+ Phone_Number (varchar(10) not null)
	+ Address (nvarchar(max) not null)
	+ Status (nvarchar(200) not null)
	+ Create_At (BIGINT not null)
	+ Modified_At (BIGINT)


## Table User
+ *Attribute*:
	+ **User_ID (int not null Indentity(1,1))** 
	+ User_Name (nvarchar(50))
	+ User_Username (varchar(50))
	+ User_Password (varchar(32))  %%After **MD5** digestion%%
	+ User_Email (varchar(100))
	+ User_Active (BIT) DEFAULT 1
	+ User_Type (nvarchar(20) not null)


## Table Order_Manager
#### Inherited table Employee
+ *Attribute*:
	+ **Order_Manager_ID (int not null Indentity(1,1))**
	+ *Employee_ID (int not null)*

## Table Employee
#### Inherited table User
+ *Attribute*:
	+ **Employee_ID (int not null Indentity(1,1))**
	+ *User_ID (int not null)*
	+ Employee_Citizen_ID (nvarchar(20) not null)
	+ Employee_DoB (BIGINT not null)
	+ Employee_Phone_Number (varchar(10) not null)
	+ Employee_Address (nvarchar(max))
	+ *Employee_Role (int not null)*
	+ Employee_Join_Date (BIGINT not null)
	+ Employee_Retire_Date (BIGINT)

## Table Inventory_Manager
#### Inherited table Employee
+ *Attribute*:
	+ **Inventory_Manager_ID (int not null Indentity(1,1))** 
	+ *Employee_ID (int not null)*

## Table Import_Stash_Item
+ *Attribute*:
	+ *Inventory_Manager_ID (int not null) (duplicate)* 
	+ *Product_ID (int not null) (duplicate)*
	+ Quantity (Int not null)
	+ Cost (Int not null)
	+ SumCost (Int not null)

## Table Import
#### Managed by Inventory_Manager
+ *Attribute*:
	+ **Import_ID (int not null Indentity(1,1))**
	+ Import_Total_Quantity (int not null)
	+ Import_Total_Cost (int not null)
	+ Supplier_Name (nvarchar(max))
	+ Import_At (BIGINT not null)
	+ Delivered_At (BIGINT)
	+ *Import_By_Inventory_Manager (int not null)*
	+ Modified_At (BIGINT)
	+ *Modified_By_Admin (int)*


## Table Import_Detail
####  Weak Entity
+ *Attribute*:
	+ *Import_ID (int not nulll) (duplicate)*
	+ *Product_ID (int not null) (duplicate)*
	+ Quantity (int not null)
	+ Cost (int not null)
	+ Status (varchar (20) not null)



## Table Admin
#### Inherited table Employee
+ *Attribute*:
	+ **Admin_ID (int not null Indentity(1,1))**
	+ *Employee_ID (int not null)*

## Table Product_Activity_Log
#### Weak Entity
+ *Attribute*:
	+ *Product_ID (int not null) (duplicate)*
	+ Action (nvarchar(10) not null)
	+ Description (nvarchar(max) default null)
	+ Updated_By_Admin (int not null)
	+ Updated_At (BIGINT)

## Table Employee_Role
+ *Attribute*:
	+ **Role_ID (int not null Indetity(1,1))**
	+ Role_Name (nvarchar(100) not null)


**Change**:
- Delivery Address thành thực thể mạnh để order sử dụng delivery_Address_Id
- The voucher table is connect to table Admin => Fix erd



## SQL Code
### New sql script
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


-- Create Table User
CREATE TABLE [User](
    [User_ID] INT NOT NULL IDENTITY(1,1),
    [User_Name] NVARCHAR(50),
    [User_Username] VARCHAR(50),
    [User_Password] VARCHAR(32), -- MD5 hash
    [User_Email] VARCHAR(100),
    [User_Active] BIT DEFAULT 1,
    [User_Type] NVARCHAR(20) NOT NULL,
    PRIMARY KEY ([User_ID])
);


-- Create Table Employee_Role
CREATE TABLE [Employee_Role](
    [Role_ID] INT NOT NULL IDENTITY(1,1),
    [Role_Name] NVARCHAR(100) NOT NULL,
    PRIMARY KEY ([Role_ID])
);


-- Create Table Employee
CREATE TABLE [Employee](
    [Employee_ID] INT NOT NULL IDENTITY(1,1),
    [User_ID] INT NOT NULL,
    [Employee_Citizen_ID] NVARCHAR(20) NOT NULL,
    [Employee_DoB] BIGINT NOT NULL,
    [Employee_Phone_Number] VARCHAR(10) NOT NULL,
    [Employee_Address] NVARCHAR(MAX),
    [Employee_Role] INT NOT NULL, -- Foreign key to Employee_Role
    [Employee_Join_Date] BIGINT NOT NULL,
    [Employee_Retire_Date] BIGINT,
    PRIMARY KEY ([Employee_ID]),
    FOREIGN KEY ([User_ID]) REFERENCES [User]([User_ID]),
    FOREIGN KEY ([Employee_Role]) REFERENCES [Employee_Role]([Role_ID])
);



-- Create Table Customer (Inherited from User)
CREATE TABLE [Customer](
    [Customer_ID] INT NOT NULL IDENTITY(1,1),
    [User_ID] INT NOT NULL,
    [Customer_Credit_Point] INT NOT NULL,
    PRIMARY KEY ([Customer_ID]),
    FOREIGN KEY ([User_ID]) REFERENCES [User]([User_ID])
);

-- Create Table Admin (Inherited from Employee)
CREATE TABLE [Admin](
    [Admin_ID] INT NOT NULL IDENTITY(1,1),
    [Employee_ID] INT NOT NULL,
    PRIMARY KEY ([Admin_ID]),
    FOREIGN KEY ([Employee_ID]) REFERENCES [Employee]([Employee_ID])
);

-- Create Table Inventory_Manager (Inherited from Employee)
CREATE TABLE [Inventory_Manager](
    [Inventory_Manager_ID] INT NOT NULL IDENTITY(1,1),
    [Employee_ID] INT NOT NULL,
    PRIMARY KEY ([Inventory_Manager_ID]),
    FOREIGN KEY ([Employee_ID]) REFERENCES [Employee]([Employee_ID])
);

-- Create Table Brand
CREATE TABLE [Brand](
    [Brand_ID] INT NOT NULL IDENTITY(1,1),
    [Brand_Name] NVARCHAR(50),
    [Brand_Logo] NVARCHAR(MAX),
    [Brand_Img] NVARCHAR(MAX),
    [Brand_Total_Product] INT DEFAULT 0,
    PRIMARY KEY ([Brand_ID])
);

-- Create Table Product
CREATE TABLE [Product](
    [Product_ID] INT NOT NULL IDENTITY(1,1),
    [Product_Name] NVARCHAR(300),
    [Brand_ID] INT NOT NULL, -- Foreign key to Brand
    [Product_Gender] NVARCHAR(50),
    [Product_Smell] NVARCHAR(200),
    [Product_Release_Year] SMALLINT DEFAULT 2003,
    [Product_Volume] INT DEFAULT 100, -- ml
    [Product_Img_URL] NVARCHAR(MAX),
    [Product_Description] NVARCHAR(MAX),
    [Product_Active] BIT DEFAULT 1,
    PRIMARY KEY ([Product_ID]),
    FOREIGN KEY ([Brand_ID]) REFERENCES [Brand]([Brand_ID])
);

-- Create Table Voucher
CREATE TABLE [Voucher](
    [Voucher_ID] INT NOT NULL IDENTITY(1,1),
    [Voucher_Code] NVARCHAR(20),
    [Voucher_Quantity] INT NOT NULL,
    [Voucher_Discount_Percent] INT NOT NULL,
    [Voucher_Discount_Max] INT NOT NULL,
    [Voucher_Created_At] BIGINT NOT NULL,
    [Voucher_Expired_At] BIGINT,
    [Voucher_Created_By_Admin] INT NOT NULL, -- Foreign key to Admin
    PRIMARY KEY ([Voucher_ID]),
    FOREIGN KEY ([Voucher_Created_By_Admin]) REFERENCES [Admin]([Admin_ID])
);

-- Create Table Voucher_Product
CREATE TABLE [Voucher_Product](
    [Voucher_ID] INT NOT NULL, -- Foreign key to Voucher
    [Product_ID] INT NOT NULL, -- Foreign key to Product
    PRIMARY KEY ([Voucher_ID], [Product_ID]),
    FOREIGN KEY ([Voucher_ID]) REFERENCES [Voucher]([Voucher_ID]),
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID])
);

-- Create Table CartItem (Weak Entity)
CREATE TABLE [CartItem](
    [Customer_ID] INT NOT NULL, -- Duplicate, Foreign key to Customer
    [Product_ID] INT NOT NULL, -- Duplicate, Foreign key to Product
    [Quantity] INT DEFAULT 0,
    [Price] INT DEFAULT 0,
    [Sum] INT DEFAULT 0,
    FOREIGN KEY ([Customer_ID]) REFERENCES [Customer]([Customer_ID]),
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID])
);

-- Create Table DeliveryAddress (Weak Entity)
CREATE TABLE [DeliveryAddress](
    [Customer_ID] INT NOT NULL, -- Duplicate, Foreign key to Customer
    [Receiver_Name] NVARCHAR(200) NOT NULL,
    [Phone_Number] VARCHAR(10) NOT NULL,
    [Address] NVARCHAR(MAX) NOT NULL,
    [Status] NVARCHAR(200) NOT NULL,
    [Create_At] BIGINT NOT NULL,
    [Modified_At] BIGINT,
    FOREIGN KEY ([Customer_ID]) REFERENCES [Customer]([Customer_ID])
);

-- Create Table Import
CREATE TABLE [Import](
    [Import_ID] INT NOT NULL IDENTITY(1,1),
    [Import_Total_Quantity] INT NOT NULL,
    [Import_Total_Cost] INT NOT NULL,
    [Supplier_Name] NVARCHAR(MAX),
    [Import_At] BIGINT NOT NULL,
    [Delivered_At] BIGINT,
    [Import_By_Inventory_Manager] INT NOT NULL, -- Foreign key to Inventory_Manager
    [Modified_At] BIGINT,
    [Modified_By_Admin] INT,
    PRIMARY KEY ([Import_ID]),
    FOREIGN KEY ([Import_By_Inventory_Manager]) REFERENCES [Inventory_Manager]([Inventory_Manager_ID]),
    FOREIGN KEY ([Modified_By_Admin]) REFERENCES [Admin]([Admin_ID])
);

-- Create Table Import_Detail (Weak Entity)
CREATE TABLE [Import_Detail](
    [Import_ID] INT NOT NULL, -- Duplicate, Foreign key to Import
    [Product_ID] INT NOT NULL, -- Foreign key to Product
    [Quantity] INT NOT NULL,
    [Cost] INT NOT NULL,
    [Status] VARCHAR(20) NOT NULL,
    PRIMARY KEY ([Import_ID]),
    FOREIGN KEY ([Import_ID]) REFERENCES [Import]([Import_ID]),
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID])
);

-- Create Table Inventory_Manager (Inherited from Employee)
CREATE TABLE [Order_Manager](
    Order_Manager_ID  INT NOT NULL IDENTITY(1,1),
    [Employee_ID] INT NOT NULL,
    PRIMARY KEY (Order_Manager_ID ),
    FOREIGN KEY ([Employee_ID]) REFERENCES [Employee]([Employee_ID])
);

-- Create Table Import_Stash_Item
CREATE TABLE [Import_Stash_Item](
    [Inventory_Manager_ID] INT NOT NULL, -- Duplicate, Foreign key to Inventory_Manager
    [Product_ID] INT NOT NULL, -- Duplicate, Foreign key to Product
    [Quantity] INT NOT NULL,
    [Cost] INT NOT NULL,
    [SumCost] INT NOT NULL,
    FOREIGN KEY ([Inventory_Manager_ID]) REFERENCES [Inventory_Manager]([Inventory_Manager_ID]),
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID])
);

-- Create Table Order
CREATE TABLE [Order](
    [Order_ID] INT NOT NULL IDENTITY(1,1),
    [Customer_ID] INT NOT NULL, -- Duplicate, Foreign key to Customer
    [Voucher_ID] INT NOT NULL, -- Foreign key to Voucher
    [Order_Receiver_Name] NVARCHAR(200) NOT NULL,
    [Order_Delivery_Address] NVARCHAR(300),
    [Order_Phone_Number] VARCHAR(10),
    [Order_Note] NVARCHAR(500),
    [Order_Total] INT DEFAULT 0,
    [Order_Deducted_Price] INT DEFAULT 0,
    [Order_Status] VARCHAR(20) NOT NULL,
    [Order_Created_At] BIGINT NOT NULL,
    [Order_Checkout_At] BIGINT,
    [Order_Update_At] BIGINT,
    [Order_Update_By_Order_Manager] INT NOT NULL, -- Foreign key to Order_Manager
    PRIMARY KEY ([Order_ID]),
    FOREIGN KEY ([Customer_ID]) REFERENCES [Customer]([Customer_ID]),
    FOREIGN KEY ([Voucher_ID]) REFERENCES [Voucher]([Voucher_ID]),
    FOREIGN KEY ([Order_Update_By_Order_Manager]) REFERENCES [Order_Manager]([Order_Manager_ID])
);

-- Create Table OrderDetail (Weak Entity)
CREATE TABLE [OrderDetail](
    [Order_ID] INT NOT NULL, -- Duplicate, Foreign key to Order
    [Product_ID] INT NOT NULL, -- Duplicate, Foreign key to Product
    [Quantity] INT DEFAULT 0,
    [Price] INT DEFAULT 0,
    [Total] INT DEFAULT 0,
    FOREIGN KEY ([Order_ID]) REFERENCES [Order]([Order_ID]),
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID])
);

-- Create Table Product_Activity_Log (Weak Entity)
CREATE TABLE [Product_Activity_Log](
    [Product_ID] INT NOT NULL, -- Duplicate, Foreign key to Product
    [Action] NVARCHAR(10) NOT NULL,
    [Description] NVARCHAR(MAX),
    [Updated_By_Admin] INT NOT NULL, -- Foreign key to Admin
    [Updated_At] BIGINT,
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID]),
    FOREIGN KEY ([Updated_By_Admin]) REFERENCES [Admin]([Admin_ID])
);




```

***
### Old sql script
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


# Script demo
## Role customer
## Trang chủ:
- Lướt xuống thương hiệu nổi tiếng
	- Chọn 1 thương hiệu qua tab mới
	- Trở về home page
- Lướt sản phẩm nổi bật
	- Kéo danh sách sản phẩm qua trái, qua phải
	- Bấm chuyển sang nước hoa nữ
	- Kéo danh sách sản phẩm qua trái, qua phải
	- Bấm chuyển sang nước hoa unisex
	- Kéo danh sách sản phẩm qua trái, qua phải
	- Chọn 1 sản phẩm qua tab mới
	- Trở về homepage
- Lướt xuống "Tại sao chọn xxiv store"
- Lướt xuống hình ảnh của store
- Lướt xuống "Đăng kí thành viên để nhận khuyến mãi"
- Lướt xuống footer
## Giới thiệu
- Lướt từ từ cho đến hết trang
## Thương hiệu
- Lướt từ từ cho đến hết trang
- Bấm chữ "I" trong danh sách 
- Bấm chữ "J" trong danh sách
- Chọn 1 "thương hiệu" qua tab mới
- Trở về trang "thương hiệu"
## Sản phẩm
- Lướt từ từ cho đến hết trang
- Vào filter thương hiệu search "KENZO" qua tab mới, rồi quay lại trang sản phẩm
- Vào filter thương hiệu search "Tom Ford" qua tab mới
	- Bấm qua trang 2
	- Bấm vào filter theo giới tính "Nam"
	- Bấm vào filter theo giới tính "Nữ"
	- Bấm vào filter theo giới tính "Unisex"
	- Bấm vào filter theo giá "1.500.000 - 3.000.000"
	- Bấm vào filter theo giá "3.000.000 - 5.000.000"
	- Bấm vào filter theo giá "1.500.000 - 3.000.000"
	- Chọn chai "Musk Oud" của "By Kilian" và đợi 5s cho file javascript load
		- Nhấp vào hình ảnh
		- Lướt từ từ đến cuối trang
		- Lướt sản phẩm được đề xuất
		- Bấm thêm vào giỏ hàng sẽ bị đá sang trang Login
			- Bấm login nhập "ducnltCE170499@fpt.edu.vn" và password "123456" thì ra login failed
			- Register với email "ducnltCE170499@fpt.edu.vn"
			- Vào email lấy password được generated ra vào user đổi lại tên đăng nhập và mật khẩu là "ducnlt"
			- Check email
			- Quay lại trang sản phẩm
			- Bấm vào kính lúp search lại chai "Musk Oud"
			- Bấm "Thêm vào giỏ hàng"
			- Bấm "Thêm vào giỏ hàng" lần hai với số lượng là 2
			- Lướt xuống chọn chai "Good Girl Gone Bad Extreme" trong sản phẩm liên quan
			- Bấm "Thêm vào giỏ hàng"
	- Click vào icon "Giỏ hàng"
		- Giảm số lượng Musk Oud xuống 1 rồi bấm cập nhật giỏ hàng

# Bảng phân công công việc
## Nguyễn Lê Tài Đức
- *FrontEnd*:
	-  Paging cho Product/List và Lọc thuộc tính cho Product (search..)
- *BackEnd*:
	- Làm HomeController
	- Làm ProductController
	- Làm ClientController
	- Làm AdminController
	- Paging cho Product/List và Lọc thuộc tính cho Product (search..)
	- Xử lý những ngoại lê cho trang web
- *Database*:
	- Viết thư viện để query database (Models, DAO)
	- Lấy dữ liệu database ( web scrapping)
- *Khác*:
	- Sử dụng Vietnam Province API
	- Cấu trúc thành model MVC
	- Sử dụng Jquery để validation

## Lê Bá Thịnh
- *FrontEnd*:
	-  Paging cho Product/List và Lọc thuộc tính cho Product (search..)
- *BackEnd*:
	- Làm HomeController
	- Làm ProductController
	- Làm ClientController
	- Làm AdminController
	- Paging cho Product/List và Lọc thuộc tính cho Product (search..)
- *Database*:
	- Viết thư viện để query database (Models, DAO)
	- Lấy dữ liệu database ( web scrapping )
	- Sử dụng outh2 API để upload ảnh lên Imgur lên database
	- Gửi mail đến user
- *Khác*:
	- Cấu trúc thành model MVC

## Lê Quốc Vương
- *FrontEnd*:
	- Làm animation cho web
	- Làm trang homePage
	- Làm trang introduction
	- Làm trang thương hiệu
	- Làm trang sản phẩm
	- Làm trang login
	- Làm trang chi tiết sản phẩm
	- Làm trang giỏ hàng
	- Làm trang cho Client tương tác
	- Style những trang jsp trong ADMIN_PAGE
- *BackEnd*:
	- Làm ClientController
	- Làm AdminController
	- Làm OrderDAO, OrderDetail
- *Database*:
	- Lấy dữ liệu database ( web scrapping)

## Nguyễn Phi Long
- *FrontEnd*:
	- Làm trang checkOut
- *BackEnd*:
	- Làm HomeController
	- Làm ProductController
	- Làm ClientController
	- Làm AdminController
- *Database*:
	- Viết sql để tạo database
	- Lấy dữ liệu database ( web scrapping)
- *Khác*:
	- Viết thư viện để query database (Models, DAO)
	- Cấu trúc thành model MVC
	- Làm filter authentication, authorization

#

| Họ Tên            | Tag            | Công Việc                                              | Mức độ hoàn thành công việc | Tỉ lệ đóng góp trên toàn bộ project |
| ----------------- | -------------- | ------------------------------------------------------ |:---------------------------:|:-----------------------------------:|
| Nguyễn Lê Tài Đức | **FrontEnd**   |                                                        |                             |               **25%**               |
|                   |                | Tham gia fix bug nhỏ (css, js, boostrap, ...)          |             25%             |                                     |
|                   |                | Tham gia sử dụng Jquery để validation các form         |            33.3%            |                                     |
|                   | *BackEnd*      |                                                        |                             |                                     |
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
|                   | ***Database*** |                                                        |                             |                                     |
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
|                   | *BackEnd*      |                                                        |                             |                                     |
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
|                   | ***Database*** |                                                        |                             |                                     |
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
|                   | *BackEnd*      |                                                        |                             |                                     |
|                   |                | Tham gia làm HomeController                            |             20%             |                                     |
|                   |                | Tham gia làm ProductController                         |                             |                                     |
|                   |                | Tham gia làm ClientController                          |                             |                                     |
|                   |                | Tham gia làm AdminController                           |                             |                                     |
|                   |                | Tham gia làm LogController                             |                             |                                     |
|                   |                | Làm Subscription                                       |            100%             |                                     |
|                   |                | Tham gia làm function trong  OrderDAO                  |                             |                                     |
|                   |                | Tham gia làm function trong  UserDAO                   |                             |                                     |
|                   | ***Database*** |                                                        |                             |                                     |
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
|                   | *BackEnd*      |                                                        |                             |                                     |
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
|                   | ***Database*** |                                                        |                             |                                     |
|                   |                | Tham gia thiết kế database                             |             50%             |                                     |
|                   |                | Lấy dữ liệu database (web scrapping)                   |             20%             |                                     |
|                   |                | Host database online cho project                       |            100%             |                                     |
|                   | Khác           |                                                        |                             |                                     |
|                   |                | Cấu trúc thành model MVC                               |             20%             |                                     |
|                   |                | Tham gia code filter userValidation                    |             80%             |                                     |



---
<sub>Author:</sub> #🐲 #🦆
<sub>Tags:</sub> #Project #Java  #JSP #SQL #Back-end #Front-end #Database
