use projectPRJ

-- isExistEmail(email)
SELECT * FROM [User] WHERE Email = '<email>'

-- isExistUsername(username)
SELECT * FROM [User] WHERE UserName = '<username>'

-- login(username, password)
SELECT * FROM [User] WHERE UserName = '<username>' AND [Password] = '<password>'