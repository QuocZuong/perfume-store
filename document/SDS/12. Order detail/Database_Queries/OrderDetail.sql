use projectPRJ
--getOrderByOrderID(odID)
SELECT * FROM Order WHERE ORDER_ID = <odID> 
--getOrderDetailByOrderID(odID)
SELECT * FROM OrderDetail WHERE Order_ID = <odID>
--getOrderManagerByOrderManagerID(odmID)
SELECT * FROM OrderManager WHERE OrderManager_ID = <odmID>
--getProductByProductID
SELECT * FROM Product WHERE Product_ID = <pID>
--getVoucherByVoucherID(vID)
SELECT * FROM Voucher WHERE Voucher_ID = <vID>
--getHavingVoucher(odID)
SELECT VoucherID FROM Voucher_Order WHERE Order_ID = <odID>
