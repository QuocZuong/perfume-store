use projectPRJ


--addImport(import)
INSERT INTO [Import] (Import_Total_Quantity, Import_Total_Cost, Supplier_Name, Import_At, Delivered_At, Import_By_Inventory_Manager) VALUES (totalQuantity, totalCost, supplierName, importAt, deliveredAt, importBy)
--getInventoryManager(username)
SELECT * FROM [Inventory_Manager] WHERE Employee_Username =  username



