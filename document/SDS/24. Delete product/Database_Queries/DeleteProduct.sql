use projectPRJ

--deleteProduct(productId)
UPDATE Product SET Active = 0 WHERE ID = '<productId>'
--deleteAllDeletedProduct()
Delete from Cart Where ProductID in (Select ID from Product Where Active = 0)
