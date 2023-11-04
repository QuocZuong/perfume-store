use projectPRJ

--getCustomer(String username)
SELECT * FROM Customer cus
JOIN [User] ON cus.[User_ID] = [User].[User_ID] 
WHERE [User].[User_Username] = '<username>'
-- updateDeliveryAddress(DeliveryAddress deliveryAddress)
UPDATE [DeliveryAddress]
SET [Customer_ID] = '<id>',
    [Receiver_Name] = '<name>',
    [Phone_Number] = '<phone>',
    [Address] = '<address>',
    [Status] = '<status>',
    [Create_At] = '<time>',
    [Modified_At] = '<time>'
WHERE [DeliveryAddress_ID] = 'daID'
-- deleteDeliveryAddress(int addressId)
DELETE FROM [DeliveryAddress]
WHERE [DeliveryAddress_ID] = '<id>'