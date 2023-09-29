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
CREATE PROCEDURE AddCustomer 
	@Name nvarchar(50),
	@Username varchar(50),
	@Password varchar(32), 
	@Email varchar(100),
	@Credit_Point int
AS
	-- Insert User first
	INSERT INTO [User](
		[User_Name],
		User_Username,
		User_Password,
		User_Email,
		User_Type
		)
	VALUES( @Name, @Username, @Password, @Email, 'Customer')

	--Then get the user id who has just been inserted
	DECLARE @MAXID AS INT = (
		SELECT TOP 1 [User_ID] 
		FROM [User]
		ORDER BY [User_ID] DESC
	)

	--Finally insert to the Customer table
	INSERT INTO [Customer](
		[User_ID],
		Customer_Credit_Point
	)
	VALUES(@MAXID, @Credit_Point)
GO
-- Example how to use the stored procedure
	--EXEC AddCustomer 'Nguyen Le Tai Duc', 'duc', 'ducduc', 'nguyenletaiduc@gmail.com', 0
	--EXEC AddCustomer 'Le Tai Duc', 'duc2', 'ducduc2' , 'letaiduc@gmail.com', 300
	--EXEC AddCustomer 'Tai Duc', 'duc3', 'ducduc3' , 'taiduc@gmail.com', 0

	--SELECT * FROM [User]
	--SELECT * FROM [Customer]


ROLLBACK
