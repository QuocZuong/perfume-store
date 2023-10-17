USE master;
GO
GO ALTER DATABASE projectPRJ
SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
GO DROP DATABASE projectPRJ;
GO USE master;
CREATE DATABASE projectPRJ;
GO USE projectPRJ;
GO -- Create Table User
    CREATE TABLE [User](
        [User_ID] INT NOT NULL IDENTITY(1, 1),
        [User_Name] NVARCHAR(50),
        [User_Username] VARCHAR(50),
        [User_Password] VARCHAR(32),
        -- MD5 hash
        [User_Email] VARCHAR(100),
        [User_Active] BIT DEFAULT 1,
        [User_Type] NVARCHAR(20) NOT NULL,
        PRIMARY KEY ([User_ID])
    );
-- Create Table Employee_Role
CREATE TABLE [Employee_Role](
    [Role_ID] INT NOT NULL IDENTITY(1, 1),
    [Role_Name] NVARCHAR(100) NOT NULL,
    PRIMARY KEY ([Role_ID])
);
-- Create Table Employee
CREATE TABLE [Employee](
    [Employee_ID] INT NOT NULL IDENTITY(1, 1),
    [User_ID] INT NOT NULL,
    [Employee_Citizen_ID] NVARCHAR(20) NOT NULL,
    [Employee_DoB] DATETIME NOT NULL,
    [Employee_Phone_Number] VARCHAR(10) NOT NULL,
    [Employee_Address] NVARCHAR(MAX),
    [Employee_Role] INT NOT NULL,
    -- Foreign key to Employee_Role
    [Employee_Join_Date] DATETIME NOT NULL,
    [Employee_Retire_Date] DATETIME,
    PRIMARY KEY ([Employee_ID]),
    FOREIGN KEY ([User_ID]) REFERENCES [User]([User_ID]),
    FOREIGN KEY ([Employee_Role]) REFERENCES [Employee_Role]([Role_ID])
);
-- Create Table Customer (Inherited from User)
CREATE TABLE [Customer](
    [Customer_ID] INT NOT NULL IDENTITY(1, 1),
    [User_ID] INT NOT NULL,
    [Customer_Credit_Point] INT NOT NULL,
    PRIMARY KEY ([Customer_ID]),
    FOREIGN KEY ([User_ID]) REFERENCES [User]([User_ID])
);
-- Create Table Admin (Inherited from Employee)
CREATE TABLE [Admin](
    [Admin_ID] INT NOT NULL IDENTITY(1, 1),
    [Employee_ID] INT NOT NULL,
    PRIMARY KEY ([Admin_ID]),
    FOREIGN KEY ([Employee_ID]) REFERENCES [Employee]([Employee_ID])
);
-- Create Table Inventory_Manager (Inherited from Employee)
CREATE TABLE [Inventory_Manager](
    [Inventory_Manager_ID] INT NOT NULL IDENTITY(1, 1),
    [Employee_ID] INT NOT NULL,
    PRIMARY KEY ([Inventory_Manager_ID]),
    FOREIGN KEY ([Employee_ID]) REFERENCES [Employee]([Employee_ID])
);
-- Create Table Brand
CREATE TABLE [Brand](
    [Brand_ID] INT NOT NULL IDENTITY(1, 1),
    [Brand_Name] NVARCHAR(50),
    [Brand_Logo] NVARCHAR(MAX),
    [Brand_Img] NVARCHAR(MAX),
    [Brand_Total_Product] INT DEFAULT 0,
    PRIMARY KEY ([Brand_ID])
);
-- Create Table Product
CREATE TABLE [Product](
    [Product_ID] INT NOT NULL IDENTITY(1, 1),
    [Product_Name] NVARCHAR(300),
    [Brand_ID] INT NOT NULL,
    -- Foreign key to Brand
    [Product_Gender] NVARCHAR(50),
    [Product_Smell] NVARCHAR(200),
    [Product_Release_Year] SMALLINT DEFAULT 2003,
    [Product_Volume] INT DEFAULT 100,
    -- ml
    [Product_Img_URL] NVARCHAR(MAX),
    [Product_Description] NVARCHAR(MAX),
    [Product_Active] BIT DEFAULT 1,
    PRIMARY KEY ([Product_ID]),
    FOREIGN KEY ([Brand_ID]) REFERENCES [Brand]([Brand_ID])
);
CREATE TABLE [Stock](
    Product_ID INT NOT NULL,
    Price INT DEFAULT 0,
    Quantity INT DEFAULT 0,
    FOREIGN KEY (Product_ID) REFERENCES Product([Product_ID])
);
-- Create Table Voucher
CREATE TABLE [Voucher](
    [Voucher_ID] INT NOT NULL IDENTITY(1, 1),
    [Voucher_Code] NVARCHAR(20),
    [Voucher_Quantity] INT NOT NULL,
    [Voucher_Discount_Percent] INT NOT NULL,
    [Voucher_Discount_Max] INT NOT NULL,
    [Voucher_Created_At] DATETIME NOT NULL,
    [Voucher_Expired_At] DATETIME,
    [Voucher_Created_By_Admin] INT NOT NULL,
    -- Foreign key to Admin
    PRIMARY KEY ([Voucher_ID]),
    FOREIGN KEY ([Voucher_Created_By_Admin]) REFERENCES [Admin]([Admin_ID])
);
-- Create Table Voucher_Product
CREATE TABLE [Voucher_Product](
    [Voucher_ID] INT NOT NULL,
    -- Foreign key to Voucher
    [Product_ID] INT NOT NULL,
    -- Foreign key to Product
    FOREIGN KEY ([Voucher_ID]) REFERENCES [Voucher]([Voucher_ID]),
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID])
);
-- Create Table CartItem (Weak Entity)
CREATE TABLE [CartItem](
    [Customer_ID] INT NOT NULL,
    -- Duplicate, Foreign key to Customer
    [Product_ID] INT NOT NULL,
    -- Duplicate, Foreign key to Product
    [Quantity] INT DEFAULT 0,
    [Price] INT DEFAULT 0,
    [Sum] INT DEFAULT 0,
    FOREIGN KEY ([Customer_ID]) REFERENCES [Customer]([Customer_ID]),
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID])
);
-- Create Table DeliveryAddress (Weak Entity)
CREATE TABLE [DeliveryAddress](
    [DeliveryAddress_ID] INT NOT NULL IDENTITY(1, 1),
    -- Primary key
    [Customer_ID] INT NOT NULL,
    -- Duplicate, Foreign key to Customer
    [Receiver_Name] NVARCHAR(200) NOT NULL,
    [Phone_Number] VARCHAR(10) NOT NULL,
    [Address] NVARCHAR(MAX) NOT NULL,
    [Status] NVARCHAR(200) NOT NULL,
    [Create_At] DATETIME NOT NULL,
    [Modified_At] DATETIME,
    PRIMARY KEY (DeliveryAddress_ID),
    FOREIGN KEY ([Customer_ID]) REFERENCES [Customer]([Customer_ID])
);
-- Create Table Import
CREATE TABLE [Import](
    [Import_ID] INT NOT NULL IDENTITY(1, 1),
    [Import_Total_Quantity] INT NOT NULL,
    [Import_Total_Cost] INT NOT NULL,
    [Supplier_Name] NVARCHAR(MAX),
    [Import_At] DATETIME NOT NULL,
    [Delivered_At] DATETIME,
    [Import_By_Inventory_Manager] INT NOT NULL,
    -- Foreign key to Inventory_Manager
    [Modified_At] DATETIME,
    [Modified_By_Admin] INT,
    PRIMARY KEY ([Import_ID]),
    FOREIGN KEY ([Import_By_Inventory_Manager]) REFERENCES [Inventory_Manager]([Inventory_Manager_ID]),
    FOREIGN KEY ([Modified_By_Admin]) REFERENCES [Admin]([Admin_ID])
);
-- Create Table Import_Detail (Weak Entity)
CREATE TABLE [Import_Detail](
    [Import_ID] INT NOT NULL,
    -- Duplicate, Foreign key to Import
    [Product_ID] INT NOT NULL,
    -- Foreign key to Product
    [Quantity] INT NOT NULL,
    [Cost] INT NOT NULL,
    [Status] VARCHAR(20) NOT NULL,
    PRIMARY KEY ([Import_ID]),
    FOREIGN KEY ([Import_ID]) REFERENCES [Import]([Import_ID]),
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID])
);
-- Create Table Inventory_Manager (Inherited from Employee)
CREATE TABLE [Order_Manager](
    Order_Manager_ID INT NOT NULL IDENTITY(1, 1),
    [Employee_ID] INT NOT NULL,
    PRIMARY KEY (Order_Manager_ID),
    FOREIGN KEY ([Employee_ID]) REFERENCES [Employee]([Employee_ID])
);
-- Create Table Import_Stash_Item
CREATE TABLE [Import_Stash_Item](
    [Inventory_Manager_ID] INT NOT NULL,
    -- Duplicate, Foreign key to Inventory_Manager
    [Product_ID] INT NOT NULL,
    -- Duplicate, Foreign key to Product
    [Quantity] INT NOT NULL,
    [Cost] INT NOT NULL,
    [SumCost] INT NOT NULL,
    FOREIGN KEY ([Inventory_Manager_ID]) REFERENCES [Inventory_Manager]([Inventory_Manager_ID]),
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID])
);
-- Create Table Order
CREATE TABLE [Order](
    [Order_ID] INT NOT NULL IDENTITY(1, 1),
    [Customer_ID] INT NOT NULL,
    -- Duplicate, Foreign key to Customer
    [Voucher_ID] INT NOT NULL,
    -- Foreign key to Voucher
    [Order_Receiver_Name] NVARCHAR(200) NOT NULL,
    [Order_Delivery_Address] NVARCHAR(300),
    [Order_Phone_Number] VARCHAR(10),
    [Order_Note] NVARCHAR(500),
    [Order_Total] INT DEFAULT 0,
    [Order_Deducted_Price] INT DEFAULT 0,
    [Order_Status] VARCHAR(20) NOT NULL,
    [Order_Created_At] DATETIME NOT NULL,
    [Order_Checkout_At] DATETIME,
    [Order_Update_At] DATETIME,
    [Order_Update_By_Order_Manager] INT NOT NULL,
    -- Foreign key to Order_Manager
    PRIMARY KEY ([Order_ID]),
    FOREIGN KEY ([Customer_ID]) REFERENCES [Customer]([Customer_ID]),
    FOREIGN KEY ([Voucher_ID]) REFERENCES [Voucher]([Voucher_ID]),
    FOREIGN KEY ([Order_Update_By_Order_Manager]) REFERENCES [Order_Manager]([Order_Manager_ID])
);
-- Create Table OrderDetail (Weak Entity)
CREATE TABLE [OrderDetail](
    [Order_ID] INT NOT NULL,
    -- Duplicate, Foreign key to Order
    [Product_ID] INT NOT NULL,
    -- Duplicate, Foreign key to Product
    [Quantity] INT DEFAULT 0,
    [Price] INT DEFAULT 0,
    [Total] INT DEFAULT 0,
    FOREIGN KEY ([Order_ID]) REFERENCES [Order]([Order_ID]),
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID])
);
-- Create Table Product_Activity_Log (Weak Entity)
CREATE TABLE [Product_Activity_Log](
    [Product_ID] INT NOT NULL,
    -- Duplicate, Foreign key to Product
    [Action] NVARCHAR(10) NOT NULL,
    [Description] NVARCHAR(MAX),
    [Updated_By_Admin] INT NOT NULL,
    -- Foreign key to Admin
    [Updated_At] DATETIME,
    FOREIGN KEY ([Product_ID]) REFERENCES [Product]([Product_ID]),
    FOREIGN KEY ([Updated_By_Admin]) REFERENCES [Admin]([Admin_ID])
);