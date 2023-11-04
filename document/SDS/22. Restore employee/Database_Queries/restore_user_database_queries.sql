use projectPRJ

--updateUser(User updateUser)
UPDATE [User] 
SET User_Name = '<name>',
User_UserName = '<username>',
User_Password = '<password>',
User_Email = '<email>',
User_Active = '<active>',
User_Type = '<userType>'
WHERE User_Id = '<userId>'


