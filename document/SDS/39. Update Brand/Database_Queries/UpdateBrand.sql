use projectPRJ

-- updateBrand(Brand brand)
UPDATE Brand
 SET Brand_Name  = '<brandName>', 
 Brand_Logo = '<brandLogo>', 
 Brand_Img = '<brandImg>' 
 WHERE Brand_ID  = '<brandId>';