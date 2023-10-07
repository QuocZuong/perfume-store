use projectPRJ

--getBrandID(brandname)
SELECT BrandID FROM Brand WHERE BrandName == <brandname>
 --addBrand(brandname)
INSERT INTO Brand (BrandName) VALUES(<brandname>)
 --addProduct(brandname)
INSERT INTO Brand (BrandName) VALUES(<brandname>)
--updateProduct(pd)
UPDATE Product
SET Product_Name = '<name>',
Brand_ID = '<brandId>',
Product_Gender = '<gender>',
Product_Smell = '<smell>',
Product_Release_Year = '<release year>',
Product_Volume = '<volume>',
Product_Img_URL = '<img URL>',
Product_Description = '<description>',
Product_Active = '<Active>'
WHERE Product_ID = '<Id>'

