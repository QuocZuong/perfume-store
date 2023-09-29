use projectPRJ

--getBrandID(brandname)
SELECT BrandID FROM Brand WHERE BrandName == <brandname>
 --addBrand(brandname)
INSERT INTO Brand (BrandName) VALUES(‘<brandname>’)
--addProudct(pd)
INSERT INTO Product([Name], [BrandID], [Gender], [Smell], [ReleaseYear], [Volume], [ImgURL], [Description]) 
VALUES (<pd.Name>, <pd.BrandID>, <pd.Gender>, <pd.Smell>, <pd.ReleaseYear>, <pd.Volume>, <pd.ImgURL>, <pd.Description>)


