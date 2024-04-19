USE master
IF EXISTS(SELECT * FROM sys.databases where name='projectPRJ') 
	DROP DATABASE [projectPRJ]
	
CREATE DATABASE [projectPRJ]
USE [projectPRJ]
GO
/****** Object:  Table [dbo].[Admin]    Script Date: 12/5/2023 1:24:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Admin](
	[Admin_ID] [int] IDENTITY(1,1) NOT NULL,
	[Employee_ID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Admin_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Brand]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Brand](
	[Brand_ID] [int] IDENTITY(1,1) NOT NULL,
	[Brand_Name] [nvarchar](50) NULL,
	[Brand_Logo] [nvarchar](max) NULL,
	[Brand_Img] [nvarchar](max) NULL,
	[Brand_Total_Product] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[Brand_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CartItem]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CartItem](
	[Customer_ID] [int] NOT NULL,
	[Product_ID] [int] NOT NULL,
	[Quantity] [int] NULL,
	[Price] [int] NULL,
	[Sum] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customer]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[Customer_ID] [int] IDENTITY(1,1) NOT NULL,
	[User_ID] [int] NOT NULL,
	[Customer_Credit_Point] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Customer_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DeliveryAddress]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DeliveryAddress](
	[DeliveryAddress_ID] [int] IDENTITY(1,1) NOT NULL,
	[Customer_ID] [int] NOT NULL,
	[Receiver_Name] [nvarchar](200) NOT NULL,
	[Phone_Number] [varchar](10) NOT NULL,
	[Address] [nvarchar](max) NOT NULL,
	[Status] [nvarchar](200) NOT NULL,
	[Create_At] [bigint] NOT NULL,
	[Modified_At] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[DeliveryAddress_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[Employee_ID] [int] IDENTITY(1,1) NOT NULL,
	[User_ID] [int] NOT NULL,
	[Employee_Citizen_ID] [nvarchar](20) NOT NULL,
	[Employee_DoB] [bigint] NOT NULL,
	[Employee_Phone_Number] [varchar](10) NOT NULL,
	[Employee_Address] [nvarchar](max) NULL,
	[Employee_Role] [int] NOT NULL,
	[Employee_Join_Date] [bigint] NOT NULL,
	[Employee_Retire_Date] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[Employee_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee_Role]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee_Role](
	[Role_ID] [int] IDENTITY(1,1) NOT NULL,
	[Role_Name] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Role_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Import]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Import](
	[Import_ID] [int] IDENTITY(1,1) NOT NULL,
	[Import_Total_Quantity] [int] NOT NULL,
	[Import_Total_Cost] [int] NOT NULL,
	[Supplier_Name] [nvarchar](max) NULL,
	[Import_At] [bigint] NOT NULL,
	[Delivered_At] [bigint] NULL,
	[Import_By_Inventory_Manager] [int] NOT NULL,
	[Modified_At] [bigint] NULL,
	[Modified_By_Admin] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[Import_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Import_Detail]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Import_Detail](
	[Import_ID] [int] NOT NULL,
	[Product_ID] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[Cost] [int] NOT NULL,
	[Status] [varchar](20) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Import_Stash_Item]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Import_Stash_Item](
	[Inventory_Manager_ID] [int] NOT NULL,
	[Product_ID] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[Cost] [int] NOT NULL,
	[SumCost] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Inventory_Manager]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Inventory_Manager](
	[Inventory_Manager_ID] [int] IDENTITY(1,1) NOT NULL,
	[Employee_ID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Inventory_Manager_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
	[Order_ID] [int] IDENTITY(1,1) NOT NULL,
	[Customer_ID] [int] NOT NULL,
	[Voucher_ID] [int] NULL,
	[Order_Receiver_Name] [nvarchar](200) NOT NULL,
	[Order_Delivery_Address] [nvarchar](300) NOT NULL,
	[Order_Phone_Number] [varchar](10) NOT NULL,
	[Order_Note] [nvarchar](500) NULL,
	[Order_Total] [int] NOT NULL,
	[Order_Deducted_Price] [int] NULL,
	[Order_Status] [varchar](20) NOT NULL,
	[Order_Created_At] [bigint] NOT NULL,
	[Order_Checkout_At] [bigint] NULL,
	[Order_Update_At] [bigint] NULL,
	[Order_Update_By_Order_Manager] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[Order_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order_Manager]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order_Manager](
	[Order_Manager_ID] [int] IDENTITY(1,1) NOT NULL,
	[Employee_ID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Order_Manager_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetail](
	[Order_ID] [int] NOT NULL,
	[Product_ID] [int] NOT NULL,
	[Quantity] [int] NULL,
	[Price] [int] NULL,
	[Total] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[Product_ID] [int] IDENTITY(1,1) NOT NULL,
	[Product_Name] [nvarchar](300) NULL,
	[Brand_ID] [int] NOT NULL,
	[Product_Gender] [nvarchar](50) NULL,
	[Product_Smell] [nvarchar](200) NULL,
	[Product_Release_Year] [smallint] NULL,
	[Product_Volume] [int] NULL,
	[Product_Img_URL] [nvarchar](max) NULL,
	[Product_Description] [nvarchar](max) NULL,
	[Product_Active] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[Product_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product_Activity_Log]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product_Activity_Log](
	[Product_ID] [int] NOT NULL,
	[Action] [nvarchar](10) NOT NULL,
	[Description] [nvarchar](max) NULL,
	[Updated_By_Admin] [int] NOT NULL,
	[Updated_At] [bigint] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Stock]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Stock](
	[Product_ID] [int] NOT NULL,
	[Price] [int] NULL,
	[Quantity] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[User_ID] [int] IDENTITY(1,1) NOT NULL,
	[User_Name] [nvarchar](50) NULL,
	[User_Username] [varchar](50) NULL,
	[User_Password] [varchar](32) NULL,
	[User_Email] [varchar](100) NULL,
	[User_Active] [bit] NULL,
	[User_Type] [nvarchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[User_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Voucher]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Voucher](
	[Voucher_ID] [int] IDENTITY(1,1) NOT NULL,
	[Voucher_Code] [nvarchar](20) NULL,
	[Voucher_Quantity] [int] NOT NULL,
	[Voucher_Discount_Percent] [int] NOT NULL,
	[Voucher_Discount_Max] [int] NOT NULL,
	[Voucher_Created_At] [bigint] NOT NULL,
	[Voucher_Expired_At] [bigint] NULL,
	[Voucher_Created_By_Admin] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Voucher_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Voucher_Product]    Script Date: 11/5/2023 1:24:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Voucher_Product](
	[Voucher_ID] [int] NOT NULL,
	[Product_ID] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Brand] ADD  DEFAULT ((0)) FOR [Brand_Total_Product]
GO
ALTER TABLE [dbo].[CartItem] ADD  DEFAULT ((0)) FOR [Quantity]
GO
ALTER TABLE [dbo].[CartItem] ADD  DEFAULT ((0)) FOR [Price]
GO
ALTER TABLE [dbo].[CartItem] ADD  DEFAULT ((0)) FOR [Sum]
GO
ALTER TABLE [dbo].[Order] ADD  DEFAULT ((0)) FOR [Order_Total]
GO
ALTER TABLE [dbo].[Order] ADD  DEFAULT ((0)) FOR [Order_Deducted_Price]
GO
ALTER TABLE [dbo].[OrderDetail] ADD  DEFAULT ((0)) FOR [Quantity]
GO
ALTER TABLE [dbo].[OrderDetail] ADD  DEFAULT ((0)) FOR [Price]
GO
ALTER TABLE [dbo].[OrderDetail] ADD  DEFAULT ((0)) FOR [Total]
GO
ALTER TABLE [dbo].[Product] ADD  DEFAULT ((2003)) FOR [Product_Release_Year]
GO
ALTER TABLE [dbo].[Product] ADD  DEFAULT ((100)) FOR [Product_Volume]
GO
ALTER TABLE [dbo].[Product] ADD  DEFAULT ((1)) FOR [Product_Active]
GO
ALTER TABLE [dbo].[Stock] ADD  DEFAULT ((0)) FOR [Price]
GO
ALTER TABLE [dbo].[Stock] ADD  DEFAULT ((0)) FOR [Quantity]
GO
ALTER TABLE [dbo].[User] ADD  DEFAULT ((1)) FOR [User_Active]
GO
ALTER TABLE [dbo].[Admin]  WITH CHECK ADD FOREIGN KEY([Employee_ID])
REFERENCES [dbo].[Employee] ([Employee_ID])
GO
ALTER TABLE [dbo].[CartItem]  WITH CHECK ADD FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Customer] ([Customer_ID])
GO
ALTER TABLE [dbo].[CartItem]  WITH CHECK ADD FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
GO
ALTER TABLE [dbo].[Customer]  WITH CHECK ADD FOREIGN KEY([User_ID])
REFERENCES [dbo].[User] ([User_ID])
GO
ALTER TABLE [dbo].[DeliveryAddress]  WITH CHECK ADD FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Customer] ([Customer_ID])
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD FOREIGN KEY([Employee_Role])
REFERENCES [dbo].[Employee_Role] ([Role_ID])
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD FOREIGN KEY([User_ID])
REFERENCES [dbo].[User] ([User_ID])
GO
ALTER TABLE [dbo].[Import]  WITH CHECK ADD FOREIGN KEY([Import_By_Inventory_Manager])
REFERENCES [dbo].[Inventory_Manager] ([Inventory_Manager_ID])
GO
ALTER TABLE [dbo].[Import]  WITH CHECK ADD FOREIGN KEY([Modified_By_Admin])
REFERENCES [dbo].[Admin] ([Admin_ID])
GO
ALTER TABLE [dbo].[Import_Detail]  WITH CHECK ADD FOREIGN KEY([Import_ID])
REFERENCES [dbo].[Import] ([Import_ID])
GO
ALTER TABLE [dbo].[Import_Detail]  WITH CHECK ADD FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
GO
ALTER TABLE [dbo].[Import_Stash_Item]  WITH CHECK ADD FOREIGN KEY([Inventory_Manager_ID])
REFERENCES [dbo].[Inventory_Manager] ([Inventory_Manager_ID])
GO
ALTER TABLE [dbo].[Import_Stash_Item]  WITH CHECK ADD FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
GO
ALTER TABLE [dbo].[Inventory_Manager]  WITH CHECK ADD FOREIGN KEY([Employee_ID])
REFERENCES [dbo].[Employee] ([Employee_ID])
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Customer] ([Customer_ID])
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD FOREIGN KEY([Order_Update_By_Order_Manager])
REFERENCES [dbo].[Order_Manager] ([Order_Manager_ID])
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD FOREIGN KEY([Voucher_ID])
REFERENCES [dbo].[Voucher] ([Voucher_ID])
GO
ALTER TABLE [dbo].[Order_Manager]  WITH CHECK ADD FOREIGN KEY([Employee_ID])
REFERENCES [dbo].[Employee] ([Employee_ID])
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD FOREIGN KEY([Order_ID])
REFERENCES [dbo].[Order] ([Order_ID])
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD FOREIGN KEY([Brand_ID])
REFERENCES [dbo].[Brand] ([Brand_ID])
GO
ALTER TABLE [dbo].[Product_Activity_Log]  WITH CHECK ADD FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
GO
ALTER TABLE [dbo].[Product_Activity_Log]  WITH CHECK ADD FOREIGN KEY([Updated_By_Admin])
REFERENCES [dbo].[Admin] ([Admin_ID])
GO
ALTER TABLE [dbo].[Stock]  WITH CHECK ADD FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
GO
ALTER TABLE [dbo].[Voucher]  WITH CHECK ADD FOREIGN KEY([Voucher_Created_By_Admin])
REFERENCES [dbo].[Admin] ([Admin_ID])
GO
ALTER TABLE [dbo].[Voucher_Product]  WITH CHECK ADD FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
GO
ALTER TABLE [dbo].[Voucher_Product]  WITH CHECK ADD FOREIGN KEY([Voucher_ID])
REFERENCES [dbo].[Voucher] ([Voucher_ID])
GO

use [projectPRJ]

GO
--addEmployee(role, Employee)

CREATE OR ALTER PROCEDURE Insert_Employee
    @User_Name nvarchar(50),
    @User_Username varchar(50),
    @User_Password varchar(32),
    @User_Email varchar(100),
    @Employee_Citizen_ID nvarchar(20),
    @Employee_Dob BIGINT,
    @Employee_Phone_Number varchar(10),
    @Employee_Address nvarchar(max),
    @Employee_Join_Date BIGINT,
    @Employee_Retire_Date BIGINT,
    @Role nvarchar(100)
AS
BEGIN
    -- Insert into table User first
    INSERT INTO [User]
        (
        [User_Name],
        User_Username,
        User_Password,
        User_Email,
        User_Type
        )
    VALUES(
            @User_Name,
            @User_Username,
            @User_Password,
            @User_Email,
            'Employee'
	)

    -- Get the userID which has just been generated
    DECLARE @UserID int = (
		 Select TOP 1
        [User].[User_ID]
    from [User]
    ORDER BY [User].[User_ID] DESC
	);

    -- Get the roleId from the role name
    DECLARE @RoleID int = (
		SELECT Role_ID
    FROM Employee_Role
    WHERE Role_Name = @Role
	);

    IF @RoleID IS NULL
    BEGIN
        INSERT INTO Employee_Role
            (Role_Name)
        VALUES(@Role);

        SET @RoleID = (
		    
            SELECT Role_ID
        FROM Employee_Role
        WHERE Role_Name = @Role
	  );
    END


    -- Insert into table Employee
    INSERT INTO [Employee]
        (
        [User_ID],
        Employee_Citizen_ID,
        Employee_DoB,
        Employee_Phone_Number,
        Employee_Address,
        Employee_Role,
        Employee_Join_Date,
        Employee_Retire_Date
        )
    VALUES(
            @UserID,
            @Employee_Citizen_ID,
            @Employee_Dob,
            @Employee_Phone_Number,
            @Employee_Address,
            @RoleID,
            @Employee_Join_Date,
            @Employee_Retire_Date
	)

    -- Get the userID which has just been generated
    DECLARE @empID int = (
		 Select TOP 1
        [Employee].[Employee_ID]
    from [Employee]
    ORDER BY [Employee].[Employee_ID] DESC
	);

    -- Insert into each table
    IF @Role = 'Admin'
	BEGIN
        Insert Into [Admin]
            (Employee_ID)
        VALUES(@empID);
    END
	ELSE IF @Role = 'Order Manager'
	BEGIN
        Insert Into [Order_Manager]
            (Employee_ID)
        VALUES(@empID);
    END
	ELSE IF @Role = 'Inventory Manager'
	BEGIN
        Insert Into [Inventory_Manager]
            (Employee_ID)
        VALUES(@empID);
    END


END

GO
--addCustomer()

CREATE OR ALTER PROCEDURE Insert_Customer
    @User_Name nvarchar(50),
    @User_Username varchar(50),
    @User_Password varchar(32),
    @User_Email varchar(100),
    @Customer_Credit_Point int
AS
BEGIN
    -- Insert into table User first
    INSERT INTO [User]
        (
        [User_Name],
        User_Username,
        User_Password,
        User_Email,
        User_Type
        )
    VALUES(
            @User_Name,
            @User_Username,
            @User_Password,
            @User_Email,
            'Customer'
	 )

    -- Get the userID which has just been generated
    DECLARE @UserID int = (
		 Select TOP 1
        [User].[User_ID]
    from [User]
    ORDER BY [User].[User_ID] DESC
	 );

    -- Insert into table Customer
    INSERT INTO Customer
        ([User_ID], Customer_Credit_Point)
    VALUES(@UserID, @Customer_Credit_Point)

END
 GO


GO
CREATE OR ALTER PROC INSERT_CART_ITEM
    @Customer_ID INT,
    @Product_ID INT,
    @Buy_Quantity INT,
    @Price INT,
    @Sum INT
AS
BEGIN
    DECLARE @count INT = 0
    -- Check if the product is existed in customer's cart
    SET @count = (
		SELECT COUNT(*)
    FROM CartItem
    WHERE 
		CartItem.Customer_ID = @Customer_ID AND
        CartItem.Product_ID = @Product_ID
	);
    -- If product isn't exist, insert like normal
    IF @count = 0 
	BEGIN
        INSERT INTO CartItem
            (Customer_ID, Product_ID, Quantity, Price, [Sum])
        VALUES(@Customer_ID, @Product_ID, @Buy_Quantity, @Price, @Sum);
    END
    -- If product is exist, update the quantity
    IF @count != 0
	BEGIN
        UPDATE CartItem 
		SET Quantity = @Buy_Quantity,
			Price = @Price,
			[Sum] = @Sum
		WHERE CartItem.Customer_ID = @Customer_ID AND CartItem.Product_ID = @Product_ID
    END
END