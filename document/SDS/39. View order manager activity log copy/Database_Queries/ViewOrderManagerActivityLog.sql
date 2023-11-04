use projectPRJ

-- searchOrderActivityLog(search)
SELECT * FROM [Order]
JOIN [Order_Manager] ON [Order].[Order_Update_By_Order_Manager] = [Order_Manager].[Order_Manager_ID]
JOIN [Employee] ON [Order_Manager].[Employee_ID] = [Employee].[Employee_ID]
JOIN [User] ON [Employee].[User_ID] = [User].[User_ID]
WHERE [User].[User_Name] LIKE '<search>' OR [User].[User_Username] LIKE '<search>' OR [User].[User_Email] LIKE '<search>' OR [Order].Order_Phone_Number LIKE '<search>' OR [Order].Order_Receiver_Name LIKE '<search>' ORDER BY [Order].Order_Update_At DESC