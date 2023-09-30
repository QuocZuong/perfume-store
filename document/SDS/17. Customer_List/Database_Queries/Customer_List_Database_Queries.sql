USE projectPRJ

SELECT u.[User_ID],
u.[User_Name],
u.User_Username,
u.User_Email,
cus.Customer_Credit_Point
FROM Customer cus, [User] u
WHERE cus.[User_ID] = u.[User_ID];