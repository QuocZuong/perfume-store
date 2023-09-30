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



-- Create the User table
CREATE TABLE [User] (
    User_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    User_Name NVARCHAR(50),
    User_Username VARCHAR(50),
    User_Password VARCHAR(32),
    User_Email VARCHAR(100),
    User_Active BIT DEFAULT 1,
    User_Type NVARCHAR(20) NOT NULL
);

-- Create the Employee_Role table
CREATE TABLE Employee_Role (
    Role_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Role_Name NVARCHAR(100) NOT NULL
);

-- Create the Employee table
CREATE TABLE Employee (
    Employee_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    User_ID INT NOT NULL,
    Employee_Citizen_ID NVARCHAR(20) NOT NULL,
    Employee_DoB DATETIME NOT NULL,
    Employee_Phone_Number VARCHAR(10) NOT NULL,
    Employee_Address NVARCHAR(max),
    Employee_Role INT NOT NULL,
    Employee_Join_Date DATETIME NOT NULL,
    Employee_Retire_Date DATETIME
);

-- Create the Admin table (Inherited from Employee)
CREATE TABLE [Admin] (
    Admin_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Employee_ID INT NOT NULL
);

-- Create the Order_Manager table (Inherited from Employee)
CREATE TABLE Order_Manager (
    Order_Manager_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Employee_ID INT NOT NULL
);

-- Create the Inventory_Manager table (Inherited from Employee)
CREATE TABLE Inventory_Manager (
    Inventory_Manager_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Employee_ID INT NOT NULL
);

-- Create the Brand table
CREATE TABLE Brand (
    Brand_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Brand_Name NVARCHAR(50),
    Brand_Logo NVARCHAR(max),
    Brand_Img NVARCHAR(max),
    Brand_Total_Product INT DEFAULT 0
);

-- Create the Product table
CREATE TABLE Product (
    Product_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Product_Name NVARCHAR(300),
    Brand_ID INT NOT NULL,
    Product_Gender NVARCHAR(50),
    Product_Smell NVARCHAR(200),
    Product_Release_Year SMALLINT DEFAULT 2003,
    Product_Volume INT DEFAULT 100,
    Product_Img_URL NVARCHAR(max),
    Product_Description NVARCHAR(max),
    Product_Active BIT DEFAULT 1
);

-- Create the Stock table
CREATE TABLE Stock (
    Product_ID INT NOT NULL,
    Price INT DEFAULT 0,
    Quantity INT DEFAULT 0
);

-- Create the Customer table (Inherited from User)
CREATE TABLE Customer (
    Customer_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    User_ID INT NOT NULL,
    Customer_Credit_Point INT NOT NULL
);

-- Create the Delivery Address table (Weak Entity)
CREATE TABLE Delivery_Address (
    Delivery_Address_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Customer_ID INT NOT NULL,
	Receiver_Name NVARCHAR(200) NOT NULL,
    Phone_Number VARCHAR(10) NOT NULL,
    Address NVARCHAR(max) NOT NULL,
    Status NVARCHAR(200) NOT NULL,
    Create_At DATETIME NOT NULL,
    Modified_At DATETIME
);

-- Create the Cart table (Weak Entity)
CREATE TABLE Cart (
    Customer_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Quantity INT DEFAULT 0,
    Price INT DEFAULT 0
);

-- Create the Voucher table
CREATE TABLE Voucher (
    Voucher_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Voucher_Code NVARCHAR(20),
    Voucher_Quantity INT NOT NULL,
    Voucher_Discount_Percent INT NOT NULL,
    Voucher_Disount_Max INT NOT NULL,
    Voucher_Created_At DATETIME NOT NULL,
    Voucher_Expired_At DATETIME,
    Voucher_Created_By_Admin INT NOT NULL
);

-- Create the Voucher_Product table
CREATE TABLE Voucher_Product (
    Voucher_ID INT NOT NULL,
    Product_ID INT NOT NULL
);

-- Create the Voucher_Customer table
CREATE TABLE Voucher_Customer (
    Voucher_ID INT NOT NULL,
    Customer_ID INT NOT NULL,
    Deducted_Price INT NOT NULL
);

-- Create the Order table
CREATE TABLE [Order] (
    Order_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Customer_ID INT NOT NULL,
    Order_Delivery_Address_ID INT NOT NULL,
	Order_Receiver_Name NVARCHAR(200) NOT NULL,
    Order_Phone_Number VARCHAR(10),
    Order_Note NVARCHAR(500),
    Order_Total INT DEFAULT 0,
	Order_Status VARCHAR(20) NOT NULL,
    Order_Created_At DATE NOT NULL,
    Order_Checkout_At DATETIME NOT NULL,
    Order_Update_At DATETIME,
    Order_Update_By_Order_Manager INT NOT NULL
);

-- Create the OrderDetail table (Weak Entity)
CREATE TABLE OrderDetail (
    Order_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Voucher_ID INT,
    Quantity INT DEFAULT 0,
    Price INT DEFAULT 0,
    Total INT DEFAULT 0
);

-- Create the Import table (Managed by Inventory_Manager)
CREATE TABLE Import (
    Import_ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Import_Total_Quantity INT NOT NULL,
    Import_Total_Cost INT NOT NULL,
    Supplier_Name NVARCHAR(max),
    Import_At DATETIME NOT NULL,
    Delivered_At DATETIME,
    Import_By_Inventory_Manager INT NOT NULL
);

-- Create the Import_Detail table (Weak Entity)
CREATE TABLE Import_Detail (
    Import_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Quantity INT NOT NULL,
    Cost INT NOT NULL,
    Modified_At DATETIME,
    Modified_By_Inventory_Manager INT
);

-- Create the Product_Activity_Log table (Weak Entity)
CREATE TABLE Product_Activity_Log (
    Product_ID INT NOT NULL,
    Action NVARCHAR(10) NOT NULL,
    Description NVARCHAR(max),
    Updated_By_Admin INT NOT NULL,
    Updated_At DATETIME
);

-- Define foreign key relationships

-- Employee to User
ALTER TABLE Employee
ADD CONSTRAINT FK_Employee_User
FOREIGN KEY (User_ID)
REFERENCES [User](User_ID);

-- Employee to Employee_Role
ALTER TABLE Employee
ADD CONSTRAINT FK_Employee_EmployeeRole
FOREIGN KEY (Employee_Role)
REFERENCES Employee_Role(Role_ID);

-- Admin to Employee
ALTER TABLE Admin
ADD CONSTRAINT FK_Admin_Employee
FOREIGN KEY (Employee_ID)
REFERENCES Employee(Employee_ID);

-- Order_Manager to Employee
ALTER TABLE Order_Manager
ADD CONSTRAINT FK_OrderManager_Employee
FOREIGN KEY (Employee_ID)
REFERENCES Employee(Employee_ID);

-- Inventory_Manager to Employee
ALTER TABLE Inventory_Manager
ADD CONSTRAINT FK_InventoryManager_Employee
FOREIGN KEY (Employee_ID)
REFERENCES Employee(Employee_ID);

-- Product to Brand
ALTER TABLE Product
ADD CONSTRAINT FK_Product_Brand
FOREIGN KEY (Brand_ID)
REFERENCES Brand(Brand_ID);

-- Stock to Product
ALTER TABLE Stock
ADD CONSTRAINT FK_Stock_Product
FOREIGN KEY (Product_ID)
REFERENCES Product(Product_ID);

-- Customer to User
ALTER TABLE Customer
ADD CONSTRAINT FK_Customer_User
FOREIGN KEY (User_ID)
REFERENCES [User](User_ID);

-- Delivery_Address to Customer
ALTER TABLE Delivery_Address
ADD CONSTRAINT FK_DeliveryAddress_Customer
FOREIGN KEY (Customer_ID)
REFERENCES Customer(Customer_ID);

-- Cart to Customer and Product
ALTER TABLE Cart
ADD CONSTRAINT FK_Cart_Customer
FOREIGN KEY (Customer_ID)
REFERENCES Customer(Customer_ID);

ALTER TABLE Cart
ADD CONSTRAINT FK_Cart_Product
FOREIGN KEY (Product_ID)
REFERENCES Product(Product_ID);

-- Voucher to Admin
ALTER TABLE Voucher
ADD CONSTRAINT FK_Voucher_Admin
FOREIGN KEY (Voucher_Created_By_Admin)
REFERENCES Admin(Admin_ID);

-- Voucher_Product to Voucher and Product
ALTER TABLE Voucher_Product
ADD CONSTRAINT FK_VoucherProduct_Voucher
FOREIGN KEY (Voucher_ID)
REFERENCES Voucher(Voucher_ID);

ALTER TABLE Voucher_Product
ADD CONSTRAINT FK_VoucherProduct_Product
FOREIGN KEY (Product_ID)
REFERENCES Product(Product_ID);

-- Voucher_Customer to Voucher and Customer
ALTER TABLE Voucher_Customer
ADD CONSTRAINT FK_VoucherCustomer_Voucher
FOREIGN KEY (Voucher_ID)
REFERENCES Voucher(Voucher_ID);

ALTER TABLE Voucher_Customer
ADD CONSTRAINT FK_VoucherCustomer_Customer
FOREIGN KEY (Customer_ID)
REFERENCES Customer(Customer_ID);

-- Order to Customer and Order_Manager and Delivery Address
ALTER TABLE [Order]
ADD CONSTRAINT FK_Order_Customer
FOREIGN KEY (Customer_ID)
REFERENCES Customer(Customer_ID);

ALTER TABLE [Order]
ADD CONSTRAINT FK_Order_OrderManager
FOREIGN KEY (Order_Update_By_Order_Manager)
REFERENCES Order_Manager(Order_Manager_ID);

ALTER TABLE [Order]
ADD CONSTRAINT FK_Order_Delivery_Address
FOREIGN KEY (Order_Delivery_Address_ID)
REFERENCES Delivery_Address(Delivery_Address_ID)


-- OrderDetail to Order, Product, and Voucher
ALTER TABLE OrderDetail
ADD CONSTRAINT FK_OrderDetail_Order
FOREIGN KEY (Order_ID)
REFERENCES [Order](Order_ID);

ALTER TABLE OrderDetail
ADD CONSTRAINT FK_OrderDetail_Product
FOREIGN KEY (Product_ID)
REFERENCES Product(Product_ID);

ALTER TABLE OrderDetail
ADD CONSTRAINT FK_OrderDetail_Voucher
FOREIGN KEY (Voucher_ID)
REFERENCES Voucher(Voucher_ID);

-- Import to Inventory_Manager
ALTER TABLE Import
ADD CONSTRAINT FK_Import_InventoryManager
FOREIGN KEY (Import_By_Inventory_Manager)
REFERENCES Inventory_Manager(Inventory_Manager_ID);

-- Import_Detail to Import, Product, and Inventory_Manager
ALTER TABLE Import_Detail
ADD CONSTRAINT FK_ImportDetail_Import
FOREIGN KEY (Import_ID)
REFERENCES Import(Import_ID);

ALTER TABLE Import_Detail
ADD CONSTRAINT FK_ImportDetail_Product
FOREIGN KEY (Product_ID)
REFERENCES Product(Product_ID);

ALTER TABLE Import_Detail
ADD CONSTRAINT FK_ImportDetail_InventoryManager
FOREIGN KEY (Modified_By_Inventory_Manager)
REFERENCES Inventory_Manager(Inventory_Manager_ID);


-- Product_Activity_Log to Product and Admin
ALTER TABLE Product_Activity_Log
ADD CONSTRAINT FK_ProductActivityLog_Product
FOREIGN KEY (Product_ID)
REFERENCES Product(Product_ID);

ALTER TABLE Product_Activity_Log
ADD CONSTRAINT FK_ProductActivityLog_Admin
FOREIGN KEY (Updated_By_Admin)
REFERENCES Admin(Admin_ID);



