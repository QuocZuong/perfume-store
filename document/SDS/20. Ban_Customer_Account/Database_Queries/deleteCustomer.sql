use projectPRJ

-- updateUser(updateUser)

UPDATE [User] SET [User].[User_Name] = '<name>', [User].[User_Username] = '<username>', [User].[User_Password] = '<password>', [User].[User_Email] = '<email>', [User].[User_Active] = 'false', [User].[User_Type] = '<user_type>' WHERE [User].User_Id = 'user_id'
