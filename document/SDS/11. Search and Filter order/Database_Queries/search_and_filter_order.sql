
use projectPRJ
--getNumberOfOrder(status, date, search)
SELECT COUNT(od.Order_ID) 
FROM Order od, OrderManager odM, Employee emp, [User] us
WHERE 
od.Order_Name LIKE <search>
OR od.Order_Date = <date>
OR od.Status = <status>
OR us.Name LIKE <search>
OR od.Order_ID = <search>
OR odM.OrderManager_ID = <search>
AND odM.OrderManager_ID = emp.Employee_ID
AND emp.Employee_ID = [User].User_ID

--getSearchAndFilterOrder(status, date, search, page)
SELECT 
od.Order_ID, 
od.Customer_ID,
od.Receiver_Name,
od.Order_Delivery_Address,
od.Order_Phone_Number,
od.Order_Note,
od.Order_Status,
od.Order_Total,
od.Order_Created_At,
od.Order_Checkout_At,
od.Order_Update_At,
od.Order_Update_By_Order_Manager

FROM Order od, OrderManager odM, Employee emp, [User] us
WHERE 
od.Order_Name LIKE <search>
OR od.Order_Date = <date>
OR od.Status = <status>
OR us.Name LIKE <search>
OR od.Order_ID = <search>
OR odM.OrderManager_ID = <search>
AND odM.OrderManager_ID = emp.Employee_ID
AND emp.Employee_ID = [User].User_ID
ORDER BY p.ID
OFFSET <20> ROWS
FETCH NEXT <20*(page-1)> ROWS ONLY

