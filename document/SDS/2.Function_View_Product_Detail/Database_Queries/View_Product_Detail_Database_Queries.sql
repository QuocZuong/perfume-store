USE projectPRJ;
GO
--getProduct(id)
SELECT 
p.Product_ID,
p.Product_Name,
p.Brand_ID,
p.Product_Gender,
p.Product_Smell,
p.Product_Release_Year,
p.Product_Volume,
p.Product_Img_URL,
p.Product_Description,
p.Product_Active,
stk.Quantity,
stk.Price
FROM Product p, Stock stk
WHERE p.Product_ID = stk.Product_ID




