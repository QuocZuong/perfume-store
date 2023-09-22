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