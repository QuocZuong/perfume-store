<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
              crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond&family=Josefin+Sans:wght@200&family=Josefin+Slab&display=swap"
            rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
              rel="stylesheet" type="text/css" />

        <!--Custom Style-->
        <link rel="stylesheet" href="/RESOURCES/admin/product/public/style/add.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">

        <style>
            label.error{
                color:red;
            }
        </style>
        <title>Thêm sản phẩm</title>
    </head>
    <body>
        <div class="container">
            <h1>Add Product</h1>
            <form action="/Admin/Product/Add" method="POST" enctype="multipart/form-data">
                <div class="name">
                    <label>Product name *</label>
                    <input type="text" name="txtProductName">
                </div>
                <div class="brand">
                    <label>Product brand *</label>
                    <input type="text" name="txtBrandName">
                </div>
                <div class="price">
                    <label>Product price *</label>
                    <input type="text" name="txtProductPrice">
                </div>
                <div class="gender">
                    <fieldset>
                        <legend>
                            Gender
                        </legend>
                        <input type="radio" name="rdoGender" value="Nam" id="nam">
                        <label for="nam">Nam</label>
                        <input type="radio" name="rdoGender" value="Nữ" id="nu">
                        <label for="nu">Nữ</label>
                        <input type="radio" name="rdoGender" value="Unisex" id="unisex">
                        <label for="unisex">Unisex</label>
                    </fieldset>
                    <div>

                    </div>
                </div>
                <div class="smell">
                    <label>Smell *</label>
                    <input type="text" name="txtProductSmell">
                </div>
                <div class="quantity">
                    <label>Quantity *</label>
                    <input type="number" name="txtProductQuantity">
                </div>
                <div class="releaseyear">
                    <label>Release Year *</label>
                    <input type="number" name="txtProductReleaseYear">
                </div>
                <div class="volume">
                    <label>Volume: *</label>
                    <input type="number" name="txtProductVolume">
                </div>
                <div class="image">
                    <label class="custom-file-upload">
                        <input type="file"/>
                        <img src="/RESOURCES/images/icons/cloud-computing.png" alt="alt"/> Upload Image *
                        <input type="file" name="fileProductImg">
                    </label>
                </div>
                <div class="description">
                    <label>Smell *</label>
                    <textarea name="txtProductDescription" cols="30" rows="10"></textarea>
                </div>
                <button type="submit" name="btnAddProduct" value="Submit" class="btnAddProduct">Add Product</button>
            </form>
        </div>



        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <script src="/RESOURCES/admin/product/public/js/main.js"></script>
        <!--Jquery Validation-->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
        <script>
            $().ready(function () {
                $("form").validate({
                    rules: {
                        txtProductName: {
                            required: true,
                            maxlength: 300
                        },
                        txtBrandName: {
                            required: true,
                            maxlength: 50
                        },
                        txtProductPrice: {
                            required: true,
                            number: true
                        },
                        rdoGender: {
                            required: true
                        },
                        txtProductSmell: {
                            required: true,
                            maxlength: 200
                        },
                        txtProductQuantity: {
                            required: true,
                            digits: true
                        },
                        txtProductReleaseYear: {
                            required: true,
                            range: [1900, 2100]
                        },
                        txtProductVolume: {
                            required: true,
                            digits: true
                        },
                        fileProductImg: {
                            required: true
                        }
                    }
                });
            });
        </script>
    </body>

</html>
