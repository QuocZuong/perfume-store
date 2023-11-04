use projectPRJ


--getAdmin(username)
SELECT * FROM [Admin] WHERE Employee_Username =  username
--updateProduct(pd, ad)
UPDATE Product
                SET Product_Name = productName
                Brand_ID = brandId
                Product_Gender = productGender
                Product_Smell = productSmell
                Product_Release_Year = productReleaseYear
                Product_Volume = productVolume
                Product_Img_URL = productImgUrl
                Product_Description = productDescription
                Product_Active = productActive
                WHERE Product_ID = productId
