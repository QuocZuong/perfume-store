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
SELECT Quantity FROM Cart
WHERE Customer_ID = '<customerID>'
AND Product_ID = '<productID>'

--addToCart()

BEGIN TRANSACTION

GO
CREATE OR ALTER PROC INSERT_TO_CART @ClientID INT, @ProductID INT, @BuyQuantity INT
AS
BEGIN
	DECLARE @count INT = 0,
			@PriceProduct INT;
	-- Check if the product is existed in customer's cart
	SET @count = (
		SELECT COUNT(*) 
		FROM Cart 
		WHERE 
		Cart.Customer_ID = @ClientID 
		AND Cart.Product_ID = @ProductID
	);
	-- Get the price of the specific product
	SET	@PriceProduct = (
		SELECT stk.Price 
		FROM Product p, Stock stk 
		WHERE p.Product_ID = stk.Product_ID 
		AND p.Product_ID = @ProductID
	);
	-- If product isn't exist, insert like normal
	IF @count = 0 
	BEGIN
		INSERT INTO Cart (Customer_ID, Product_ID, Quantity) VALUES(@ClientID, @ProductID, @BuyQuantity);
	END
	-- If product is exist, update the quantity
	IF @count != 0
	BEGIN
		UPDATE Cart 
		SET Quantity = @BuyQuantity
		WHERE Cart.Customer_ID = @ClientID AND Cart.Product_ID = @ProductID
	END
END

ROLLBACK