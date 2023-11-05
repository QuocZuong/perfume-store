use projectPRJ

-- searchProductActivityLog(search)
SELECT [Product_Activity_Log].Product_ID, [Product_Activity_Log].[Action], [Product_Activity_Log].[Description],[Product_Activity_Log].Updated_By_Admin, [Product_Activity_Log].Updated_At, [Product].Product_Name FROM Product_Activity_Log
JOIN [Admin] ON Product_Activity_Log.Updated_By_Admin = [Admin].[Admin_ID]
JOIN [Employee] ON [Admin].[Employee_ID] = [Employee].[Employee_ID]
JOIN [User] ON [Employee].[User_ID] = [User].[User_ID]
JOIN [Product] ON [Product_Activity_Log].Product_ID = [Product].Product_ID
WHERE [User].[User_Name] LIKE '<search>' OR [User].[User_Username] LIKE '<search>' OR [User].[User_Email] LIKE '<search>' OR [Product].Product_Name LIKE '<search>' ORDER BY [Product_Activity_Log].Updated_At DESC