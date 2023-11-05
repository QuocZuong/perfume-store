use projectPRJ

--getOrderManager(OrderManager orderManager)
SELECT * FROM [Order_Manager] WHERE Employee_ID = '<id>'

--getOrderByOrderId(int orderId)
SELECT * FROM [Order] where Order_ID = '<orderId>'

--getVoucher(int voucherId)
SELECT * FROM [Voucher] WHERE Voucher_ID = '<voucherId>'

--getStock(int productId)
SELECT * FROM Stock WHERE [Product_ID] = '<ProductId>'

--updateVoucher(Voucher voucher)
UPDATE [Voucher]
SET [Voucher_Code] = '<Code>',   
[Voucher_Quantity] = '<quantity>', 
[Voucher_Discount_Percent] = '<discountPercent>',  
[Voucher_Discount_Max] = '<discountMax>',
[Voucher_Created_At] = '<CreateAt>',  
[Voucher_Expired_At] = '<ExpiredAt>',
[Voucher_Created_By_Admin] = '<adminId>' 
WHERE [Voucher_ID] = '<voucherId>'

--updateStock(Stock stock)
UPDATE Stock 
SET Price='<Price>',
Quantity= '<Quantity>'
WHERE Product_ID='<ProductId>'

--updateOrder(Order order)
UPDATE [Order]
SET [Customer_ID] = '<CustomerId>', 
[Voucher_ID] = '<voucherId>', 
[Order_Receiver_Name] = '<receiverName>', 
[Order_Delivery_Address] = '<deliveryAddress>', 
[Order_Phone_Number] = '<phoneNumber>', 
[Order_Note] = '<Note>', 
[Order_Total] = '<Total>', 
[Order_Deducted_Price] = '<deductedPrice>', 
[Order_Status] = '<Status>', 
[Order_Created_At] = '<CreatedAt>', 
[Order_Checkout_At] = '<CheckoutAt>', 
[Order_Update_At] = '<UpdateAt>', 
[Order_Update_By_Order_Manager] = '<orderManagerId>' 
WHERE [Order_ID] = '<orderId>'
