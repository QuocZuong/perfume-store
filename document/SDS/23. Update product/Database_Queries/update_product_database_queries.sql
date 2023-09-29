use projectPRJ

--getBrandID(brandname)
SELECT BrandID FROM Brand WHERE BrandName == <brandname>
 --addBrand(brandname)
INSERT INTO Brand (BrandName) VALUES(<brandname>)
 --addProduct(brandname)
INSERT INTO Brand (BrandName) VALUES(<brandname>)
--updateProduct(pd)
UPDATE Product SET
Name = <pd.Name>, 
BrandID = pd.<pd.BrandID>, 
Gender = <pd.Gender>, 
Smell = <pd.Smell>, 
ReleaseYear = <pd.ReleaseYear>, 
Volume = <pd.Volume>, 
ImgURL = <pd.ImgURL>, 
Description = <pd.Description>
WHERE ID = <pd.ID>

