USE projectPRJ;
GO

--isUsernameExist(username)
SELECT [User].[User_ID] from [User]
Where [User].User_Username = '<username>';

--isEmailExist(email)
SELECT [User].[User_ID] from [User]
Where [User].User_Email = '<email>';

--isCitizenIDExist(citizenID)
SELECT Employee.[User_ID]  from Employee
Where Employee.Employee_Citizen_ID = '<citizenID>';

--isPhoneNumberExist(phoneNumber)
SELECT Employee.[User_ID]  from Employee
Where Employee.Employee_Phone_Number = '<phoneNumber>';

GO
--addEmployee(role, Employee)

BEGIN TRANSACTION
GO

CREATE PROCEDURE Insert_Employee 
	@User_Name nvarchar(50),
	@User_Username varchar(50),
	@User_Password varchar(32),
	@User_Email varchar(100),
	@Employee_Citizen_ID nvarchar(20),
	@Employee_Dob Datetime,
	@Employee_Phone_Number varchar(10),
	@Employee_Address nvarchar(max),
	@Employee_Join_Date Datetime,
	@Employee_Retire_Date Datetime,
	@Role nvarchar(100)
AS
BEGIN
	-- Insert into table User first
	INSERT INTO [User](
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
		 Select TOP 1 [User].[User_ID] from [User]
		 ORDER BY [User].[User_ID] DESC
	);

	-- Get the roleId from the role name
	DECLARE @RoleID int = (
		SELECT Role_ID FROM Employee_Role
		WHERE Role_Name = @Role
	);


	-- Insert into table Employee
	INSERT INTO [Employee](
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
		 Select TOP 1 [Employee].[Employee_ID] from [Employee]
		 ORDER BY [Employee].[Employee_ID] DESC
	);

	-- Insert into each table
	IF @Role = 'Admin'
	BEGIN
		Insert Into [Admin](Employee_ID) VALUES(@empID);
	END
	ELSE IF @Role = 'Order Manager'
	BEGIN
		Insert Into [Order_Manager](Employee_ID) VALUES(@empID);
	END
	ELSE IF @Role = 'Inventory Manager'
	BEGIN
		Insert Into [Inventory_Manager](Employee_ID) VALUES(@empID);
	END


END
GO
-- Example: insert role
INSERT INTO Employee_Role(Role_Name)
VALUES('Admin'),
	('Order Manager'),
	('Inventory Manager')

EXEC Insert_Employee 
	'<name>', 
	'<username>', 
	'<password>', 
	'<email>', 
	'<empCitzenID>', 
	'2003-9-23', -- DoB
	'<phoneNumber>', 
	'<address>', 
	'2023-9-30', -- Joined Date
	null, -- Retire Date
	'Order Manager' -- Role name
GO
SELECT * FROM [User]
SELECT * FROM Employee
SELECT * FROM Employee_Role
SELECT * FROM [Admin]
SELECT * FROM [Order_Manager]
SELECT * FROM [Inventory_Manager]
GO
ROLLBACK