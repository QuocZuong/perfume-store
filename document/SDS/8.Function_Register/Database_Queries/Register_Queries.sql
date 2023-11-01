use projectPRJ;

--getUserByEmail()
SELECT * FROM [User] u
WHERE u.User_Email = '<email>';

--isUsernameExist()
SELECT * FROM [User] u
WHERE u.User_Username = '<username>';

--addCustomer()
 BEGIN TRANSACTION
 GO
 
 CREATE PROCEDURE Insert_Customer
	 @User_Name nvarchar(50),
	 @User_Username varchar(50),
	 @User_Password varchar(32),
	 @User_Email varchar(100),
	 @Customer_Credit_Point int
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
		 'Customer'
	 )
 
	 -- Get the userID which has just been generated
	 DECLARE @UserID int = (
		 Select TOP 1 [User].[User_ID] from [User]
		 ORDER BY [User].[User_ID] DESC
	 );
 
	 -- Insert into table Customer
	 INSERT INTO Customer([User_ID], Customer_Credit_Point)
	 VALUES(@UserID,@Customer_Credit_Point)
 
 END
 GO
 -- Example: insert role
 
 EXEC Insert_Customer
	 '<name>',
	 '<username>',
	 '<password>',
	 '<email>',
	 200
 GO
 SELECT * FROM [User]
 SELECT *  FROM [Customer]
 GO
 ROLLBACK
