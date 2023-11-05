

<%@page import="Lib.ExceptionUtils"%>
<%@page import="Lib.Converter"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="Models.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%! BrandDAO bDAO = new BrandDAO();  %>
<%! ProductDAO pDAO = new ProductDAO(); %>
<%! Product pd;%>
<% pd = (Product) request.getAttribute("ProductUpdate");%>
<%
    String queryString = request.getQueryString();
    boolean isError = ExceptionUtils.isWebsiteError(queryString);
    String exceptionMessage = ExceptionUtils.getMessageFromExceptionQueryString(queryString);

%>
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


            <!--Navbar section-->
            <div class="row">
                <div class="col-md-12 nav">
                    <jsp:include page="/NAVBAR/AdminNavbar.jsp"></jsp:include>
                    </div>
                </div>

                <div class="container">
                    <div class="row">

                        <!--Execption Handling-->
                    <c:if test='<%= isError%>'>
                        <h1 class="alert alert-danger text-center"> <%= exceptionMessage%></h1>
                    </c:if>
                    <!--Execption Handling-->

                    <h1>Update Product</h1>
                    <form action="/Admin/Product/Update" method="POST" enctype="multipart/form-data">
                        <div class="id">
                            <label class="required">Product ID</label>
                            <input type="number" name="txtProductID" readonly="true" value="<%= pd.getId()%>">
                        </div>
                        <div class="name">
                            <label class="required">Product name</label>
                            <input type="text" name="txtProductName" value="<%= pd.getName()%>">
                        </div>
                        <div class="brand">
                            <label class="required">Product brand</label>
                            <input type="text" name="txtBrandName" value="<%= bDAO.getBrand(pd.getBrandId()).getName()%>" >
                        </div>
                        <div class="price">
                            <label class="required">Product price</label>
                            <input type="text" name="txtProductPrice"  value="<%= Converter.convertMoneyToInteger(String.valueOf(pd.getStock().getPrice())).replace(".", ",")%>">
                        </div>
                        <div class="gender"  >
                            <fieldset>
                                <legend >
                                    Gender <span>*</span>
                                </legend>
                                <input type="radio" name="rdoGender" value="Nam" id="nam"   <%= (pd.getGender().equals("Nam")) ? "checked" : ""%> > 
                                <label for="nam">Nam</label>
                                <input type="radio" name="rdoGender" value="Nữ" id="nu"  <%= (pd.getGender().equals("Nữ")) ? "checked" : ""%>>
                                <label for="nu">Nữ</label>
                                <input type="radio" name="rdoGender" value="Unisex" id="unisex" <%= (pd.getGender().equals("Unisex")) ? "checked" : ""%>>
                                <label for="unisex">Unisex</label>
                            </fieldset>
                        </div>
                        <div class="smell">
                            <label class="required">Smell</label>
                            <input type="text" name="txtProductSmell" value="<%= pd.getSmell()%>">
                        </div>
                        <div class="quantity">
                            <label class="required">Quantity</label>
                            <input type="number" name="txtProductQuantity" value="<%= pd.getStock().getQuantity()%>">
                        </div>
                        <div class="releaseyear">
                            <label class="required">Release Year</label>
                            <input type="number" name="txtProductReleaseYear" value="<%= pd.getReleaseYear()%>">
                        </div>
                        <div class="volume">
                            <label class="required">Volume</label>
                            <input type="number" name="txtProductVolume" value="<%= pd.getVolume()%>">
                        </div>
                        <div class="image">
                            <label class="custom-file-upload">
                                <img src="/RESOURCES/images/icons/cloud-computing.png" alt="alt"/>
                                <label for="imgInput" class="required" id="filename">Upload Image</label>
                                <input type="file" name="fileProductImg" onchange="onFileSelected(event)" id="imgInput" >
                            </label>
                            <img id="preview-img" src="<%= pd.getImgURL()%>"  alt="alt"/>
                        </div>
                        <div class="description">
                            <label class="required">Description</label>
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

        <!--Custome script-->
        <script src="/RESOURCES/admin/product/public/js/update.js"></script>



        <!--Jquery Validation-->
        <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
        <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>

        <!--Update Img real time-->
        <script>
                                    let currentImgURL = '<%= pd.getImgURL()%>';
                                    function onFileSelected(event) {
                                        var selectedFile = event.target.files[0];
                                        console.log(selectedFile);
                                        if (selectedFile === undefined)
                                        {
                                            return;
                                        }
                                        var reader = new FileReader();

                                        var imgtag = document.getElementById("preview-img");
                                        imgtag.title = selectedFile.name;

                                        reader.onload = function (event) {
                                            imgtag.src = event.target.result;
                                        };
                                        reader.readAsDataURL(selectedFile);
                                    }


                                    $("input[type=file][name=fileProductImg]").on("change", function () {
                                        let fname = $('input[type=file][name=fileProductImg]').val().replace(/C:\\fakepath\\/i, '')
                                        if (fname !== "") {
                                            $("#filename").text(fname);
                                        } else {
                                            let imgtag = document.getElementById("preview-img");
                                            //Reset default image if cancel is clicked
                                            imgtag.src = currentImgURL;
                                            $("#filename").text("Upload Image");
                                        }
                                    });


        </script>

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
                            digits: true
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
                            range: [1900, (new Date().getFullYear())],
                            digits: true
                        },
                        txtProductVolume: {
                            required: true,
                            digits: true
                        },
                        fileProductImg: {
                            accept: "image/*"
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
                            digits: "Giá sản phẩm phải là số nguyên"
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
                            range: "Năm phát hành phải nằm trong khoảng 1900 đến năm hiện tại",
                            digits: "Năm phát hành phải là số nguyên"
                        },
                        txtProductVolume: {
                            required: "Vui lòng nhập dung tích",
                            digits: "Dung tích phải là số nguyên"
                        },
                        fileProductImg: {
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
                        if (element.attr('name') === 'fileProductImg') {
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
