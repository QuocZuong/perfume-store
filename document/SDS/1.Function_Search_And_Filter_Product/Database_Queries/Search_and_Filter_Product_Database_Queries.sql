use projectPRJ

--getNumberOfProduct(brandID, gender, price, search)
SELECT COUNT(p.Product_ID) AS CountRow 
FROM Product p, Brand b, Stock stk
WHERE p.Brand_ID LIKE '<brandID>'
AND p.Product_Gender LIKE '<productGender>'
AND stk.Price BETWEEN '<lowPrice>' AND '<highPrice>'
AND (p.[Product_Name] LIKE '<search>' OR b.[Brand_Name] LIKE '<search>')
AND p.Brand_ID = b.Brand_ID
AND stk.Product_ID = p.Product_ID

--getBrandName(brandID)
SELECT * FROM Brand
WHERE Brand.Brand_ID = '<brandId>'


-- getFilteredProduct(BrandID, gender, price, page, search)
SELECT 
p.Product_ID,
p.Product_Name,
p.Brand_ID,
stk.Price,
p.Product_Gender,
stk.Quantity,
p.Product_Release_Year,
p.Product_Volume,
p.Product_Smell,
p.Product_Img_URL,
p.Product_Description,
p.Product_Active
FROM Product p, Brand b, Stock stk
WHERE p.Brand_ID = b.Brand_ID
AND stk.Product_ID = p.Product_ID


