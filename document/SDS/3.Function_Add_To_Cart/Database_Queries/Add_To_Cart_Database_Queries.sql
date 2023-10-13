USE projectPrj;

--getCustomer(customerID)
SELECT 
u.[User_ID],
u.[User_Name],
u.[User_Username],
u.User_Password,
u.User_Email,
u.User_Active,
u.User_Type,
cus.Customer_ID,
cus.Customer_Credit_Point
FROM Customer cus, [User] u
WHERE cus.[User_ID] = u.[User_ID]
AND cus.[Customer_ID] = '<customerID>'

--getCartQuantity()
SELECT Quantity FROM CartItem
WHERE Customer_ID = '<customerID>'
AND Product_ID = '<productID>'

--addToCart()
USE projectPrj;

BEGIN TRANSACTION

GO
CREATE OR ALTER PROC INSERT_CART_ITEM @Customer_ID INT, @Product_ID INT, @Buy_Quantity INT, @Price INT, @Sum INT
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
		INSERT INTO CartItem(Customer_ID, Product_ID, Quantity, Price, [Sum]) VALUES(@Customer_ID, @Product_ID, @Buy_Quantity, @Price, @Sum);
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

ROLLBACK