use projectPRJ


--addOrder(order)
INSERT INTO [Order] (Customer_ID, 
                    Voucher_ID,
                    Order_Receiver_Name, 
                    Order_Delivery_Address,
                    Order_Phone_Number, 
                    Order_Note,
                    Order_Total,
                    Order_Deducted_Price,
                    Order_Status,
                    Order_Created_At,
                    Order_Checkout_At,
                    Order_Update_At,
                    Order_Update_By_Order_Manager)
                    VALUES(customerId,
                    voucehrId,
                    orderReceiverName,
                    orderDeliveryAddress,
                    orderPhoenNumber,
                    orderNote,
                    orderTotal,
                    orderDeductedPrice,
                    orderStatus,
                    orderCreatedAt,
                    orderCheckoutAt,
                    orderUpdateAt,
                    orderUpdateByOrderManager)
--getCustomer(username)
SELECT * FROM Customer cus
        JOIN [User] ON cus.[User_ID] = [User].[User_ID]
        WHERE [User].[User_ID] = username



