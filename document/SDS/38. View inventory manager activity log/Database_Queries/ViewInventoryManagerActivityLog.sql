use projectPRJ

-- searchProductActivityLog(search)
SELECT * FROM Import
JOIN [Inventory_Manager] ON [Import].Import_By_Inventory_Manager = [Inventory_Manager].[Inventory_Manager_ID]
JOIN Employee ON [Inventory_Manager].Employee_ID = [Employee].Employee_ID
JOIN [User] ON [Employee].[User_ID] = [User].[User_ID]
WHERE [User].User_Email LIKE '<search>' OR [User].[User_Username] LIKE '<search>' OR [Import].[Supplier_Name] LIKE '<search>'
