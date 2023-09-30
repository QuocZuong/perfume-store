USE projectPRJ;

--getNumberOfEmployee(role, search)
SELECT COUNT(emp.Employee_ID) 
FROM Employee emp, [User] u, Employee_Role empR
WHERE u.[User_Name] LIKE '<Search>'
AND emp.Employee_Citizen_ID LIKE '<Search>'
AND emp.Employee_Phone_Number LIKE '<Search>'
AND empR.Role_Name LIKE '<Role>'
AND emp.Employee_Role = empR.Role_ID
AND emp.[User_ID] = u.[User_ID];

-- searchEmployee(role, search)
SELECT 
u.[User_Name],
u.User_Active,
emp.Employee_ID,
emp.Employee_Citizen_ID,
emp.Employee_Phone_Number,
emp.Employee_DoB,
empR.Role_Name
FROM Employee emp, [User] u, Employee_Role empR
WHERE u.[User_Name] LIKE '<Search>'
AND emp.Employee_Citizen_ID LIKE '<Search>'
AND emp.Employee_Phone_Number LIKE '<Search>'
AND empR.Role_Name LIKE '<Role>'
AND emp.Employee_Role = empR.Role_ID
AND emp.[User_ID] = u.[User_ID];
