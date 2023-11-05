use projectPRJ

--getOrderForChartByOrderIdByPrice()
SELECT [Product].Product_Name, [Stock].Price, [Stock].Product_ID, [Stock].Quantity FROM [Order]
                INNER JOIN [OrderDetail] ON [Order].Order_ID=[OrderDetail].Order_ID
                INNER JOIN [Product] ON [OrderDetail].Product_ID [Product].Product_ID
                INNER JOIN [Stock] ON [Product].Product_ID = [Stock].Product_ID


