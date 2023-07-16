

<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="Models.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! BrandDAO bDAO = new BrandDAO();  %>
<%! ProductDAO pDAO = new ProductDAO(); %>
<%! Product pd;%>
<% pd = (Product) request.getAttribute("ProductUpdate");%>

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
        <link rel="stylesheet" href="/RESOURCES/admin/product/public/style/update.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">

        <style>
            #preview-img{
                width:20%;
                height: 20%;
            }

        </style>

        <title>Cập nhật sản phẩm</title>
    </head>
    <body>

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 nav">
                    <ul>
                        <li><a href="/">trang chủ</a></li>
                        <li> <a href="/home/introduction">giới thiệu</a></li>
                        <li><a href="/home/brand">thương hiệu</a></li>
                        <!-- This link to shop servlet file. DO NOT MODIFY the link -->
                        <li><a href="/Product/List">sản phẩm</a></li>
                    </ul>
                    <a href="/" class="iconPage"><img src="/RESOURCES/images/icons/icon.webp" alt=""
                                                      height="64"></a>
                    <div class="account">
                        <button class="droppown-btn bg-transparent border-0" id="product-dropdown-btn"><img src="/RESOURCES/images/icons/shopping-bag-alone.png" alt="">
                        </button>
                        <ul class="shadow position-absolute align-items-start ps-1 pt-2">
                            <li class="py-3 text-dark"><a href="/Admin/Product/Add">Thêm sản phẩm</a></li>
                            <li class="pb-3 text-dark"><a href="/Admin/Product/List">Danh sách sản phẩm</a></li>
                        </ul>

                        <button class="droppown-btn bg-transparent border-0" id="user-dropdown-btn"><img src="/RESOURCES/images/icons/group.png" alt="">
                        </button>
                        <ul class="shadow position-absolute align-items-start ps-1 pt-2">
                            <li class="py-3 text-dark"><a href="/Admin/User/List">Danh sách người dùng</a></li>
                        </ul>

                        <a href="/Client/User"><img src="/RESOURCES/images/icons/user.png" alt=""></a>
                        <a href="/Client/Cart"><img src="/RESOURCES/images/icons/cart.png" alt=""></a>

                    </div>
                </div>
            
            <div class="container"></div>
            <div class="row">
                <h1>Update Product</h1>
                <form action="/Admin/Product/Update" method="POST" enctype="multipart/form-data">
                    <div class="id">
                        <label>Product ID <span class="text-danger">*</span></label>
                        <input type="number" name="txtProductID" readonly="true" value="<%= pd.getID()%>">
                    </div>
                    <div class="name">
                        <label>Product name <span class="text-danger">*</span></label>
                        <input type="text" name="txtProductName" value="<%= pd.getName()%>">
                    </div>
                    <div class="brand">
                        <label>Product brand <span class="text-danger">*</span></label>
                        <input type="text" name="txtBrandName" value="<%= bDAO.getBrandName(pd.getBrandID())%>" >
                    </div>
                    <div class="price">
                        <label>Product price <span class="text-danger"><span class="text-danger">*</span></span></label>
                        <input type="text" name="txtProductPrice"  value="<%= pDAO.IntegerToMoney(pd.getPrice()).replace(".", ",")%>">
                    </div>
                    <div class="gender">
                        <fieldset>
                            <legend>
                                Gender
                            </legend>
                            <input type="radio" name="rdoGender" value="Nam" id="nam"   <%= (pd.getGender().equals("Nam")) ? "checked" : ""%> > 
                            <label for="nam">Nam</label>
                            <input type="radio" name="rdoGender" value="Nữ" id="nu"  <%= (pd.getGender().equals("Nữ")) ? "checked" : ""%>>
                            <label for="nu">Nữ</label>
                            <input type="radio" name="rdoGender" value="Unisex" id="unisex" <%= (pd.getGender().equals("Unisex")) ? "checked" : ""%>>
                            <label for="unisex">Unisex</label>
                        </fieldset>
                        <div>

                        </div>
                    </div>
                    <div class="smell">
                        <label>Smell <span class="text-danger">*</span></label>
                        <input type="text" name="txtProductSmell" value="<%= pd.getSmell()%>">
                    </div>
                    <div class="quantity">
                        <label>Quantity <span class="text-danger">*</span></label>
                        <input type="number" name="txtProductQuantity" value="<%= pd.getQuantity()%>">
                    </div>
                    <div class="releaseyear">
                        <label>Release Year <span class="text-danger">*</span></label>
                        <input type="number" name="txtProductReleaseYear" value="<%= pd.getReleaseYear()%>">
                    </div>
                    <div class="volume">
                        <label>Volume: <span class="text-danger">*</span></label>
                        <input type="number" name="txtProductVolume" value="<%= pd.getVolume()%>">
                    </div>
                    <div class="image">
                        <label class="custom-file-upload">
                            <input type="file" name="fileProductImg" onchange="onFileSelected(event)" >
                            <img src="/RESOURCES/images/icons/cloud-computing.png" alt="alt"/> Upload Image <span class="text-danger">*</span>
                        </label>
                        <img id="preview-img" src="<%= pd.getImgURL()%>"  alt="alt"/>
                    </div>
                    <div class="description">
                        <label>Description <span class="text-danger">*</span></label>
                        <textarea name="txtProductDescription" cols="30" rows="4"><%= pd.getDescription()%></textarea>
                    </div>
                    <button type="submit" name="btnUpdateProduct" value="Submit" class="btnUpdateProduct">Update Product</button>
                </form>
            </div>
        </div>
</div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <script src="/RESOURCES/admin/product/public/js/update.js"></script>
        <!--Update Img real time-->
        <script>
                            function onFileSelected(event) {
                                var selectedFile = event.target.files[0];
                                var reader = new FileReader();

                                var imgtag = document.getElementById("preview-img");
                                imgtag.title = selectedFile.name;

                                reader.onload = function (event) {
                                    imgtag.src = event.target.result;
                                };
                                reader.readAsDataURL(selectedFile);
                            }
                            $().ready(function () {

                            });
        </script>


        <!--Jquery Validation-->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
        <script>
                            $(document).ready(function () {
                                $.validator.addMethod("requiredFile", function (value, element) {
                                    return element.files.length !== 0;
                                }, "Vui lòng chọn ảnh sản phẩm");

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
                                            range: [1900, (new Date().getFullYear())]
                                        },
                                        txtProductVolume: {
                                            required: true,
                                            digits: true
                                        },
                                        fileProductImg: {
                                            requiredFile: true,
                                            accept: "image/jpeg, image/pjpeg"
                                        },
                                        txtProductDescription: {
                                            required: true
                                        }
                                    },
                                    messages: {
                                        txtProductName: {
                                            required: "Vui lòng nhập tên sản phẩm",
                                            maxlength: "Tên sản phẩm không được vượt quá 300 ký tự"
                                        },
                                        txtBrandName: {
                                            required: "Vui lòng nhập tên thương hiệu",
                                            maxlength: "Tên thương hiệu không được vượt quá 50 ký tự"
                                        },
                                        txtProductPrice: {
                                            required: "Vui lòng nhập giá sản phẩm",
                                            number: "Giá sản phẩm phải là số"
                                        },
                                        rdoGender: {
                                            required: "Vui lòng chọn giới tính"
                                        },
                                        txtProductSmell: {
                                            required: "Vui lòng nhập hương thơm",
                                            maxlength: "Hương thơm không được vượt quá 200 ký tự"
                                        },
                                        txtProductQuantity: {
                                            required: "Vui lòng nhập số lượng",
                                            digits: "Số lượng phải là số nguyên"
                                        },
                                        txtProductReleaseYear: {
                                            required: "Vui lòng nhập năm phát hành",
                                            range: "Năm phát hành phải nằm trong khoảng 1900 đến năm hiện tại"
                                        },
                                        txtProductVolume: {
                                            required: "Vui lòng nhập dung tích",
                                            digits: "Dung tích phải là số nguyên"
                                        },
                                        fileProductImg: {
                                            requiredFile: "Vui lòng chọn ảnh sản phẩm",
                                            accept: "Vui lòng chọn file ảnh"
                                        },
                                        txtProductDescription: {
                                            required: "Vui lòng nhập thông tin mùi hương"
                                        }
                                    },

                                    errorPlacement: function (error, element) {
                                        error.addClass("text-danger d-block mt-2");

                                        if (element.attr('name') === 'rdoGender') {
                                            // error.insertAfter($("#gender-input"));
                                            error.insertAfter(element.parent());
                                            return;
                                        }

                                        error.insertAfter(element);
                                    }
                                });
                            });
        </script>
    </body>

</html>
