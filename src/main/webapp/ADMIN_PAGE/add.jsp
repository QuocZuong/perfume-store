

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>AddProduct</title>
    </head>
    <body>
        <h1>Add Product</h1>
        <form action="/Admin/Add" method="POST" enctype="multipart/form-data">
            Product Name: <input type="text" name="txtProductName"> <br>
            Product Brand: <input type="text" name="txtBrandName"> <br>
            Product Price: <input type="text" name="txtProductPrice"> <br>
            Gender: <input type="radio" name="rdoGender" value="Nam">Nam<input type="radio" name="rdoGender" value="Nữ">Nữ<input type="radio" name="rdoGender" value="Unisex">Unisex<br>
            Smell: <input type="text" name="txtProductSmell"> <br>
            Quantity: <input type="number" name="txtProductQuantity"> <br>
            ReleaseYear: <input type="number" name="txtProductReleaseYear"> <br>
            Volume: <input type="number" name="txtProductVolume"> <br>
            Product Image: <input type="file" name="fileProductImg"> <br>
            Description: <br> <textarea name="txtProductDescription" cols="30" rows="10"></textarea> <br>
            <button type="submit" name="btnAddProduct" value="Submit">Add Product</button>

        </form>
    </body>
</html>
