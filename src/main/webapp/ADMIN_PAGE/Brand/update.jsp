

<%@page import="Models.Brand"%>
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
<%! Brand br;%>
<% br = (Brand) request.getAttribute("BrandUpdate");%>
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
        <link rel="stylesheet" href="/RESOURCES/admin/brand/public/style/update.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">

        <style>
            #preview-img{
                width:20%;
                height: 20%;
            }

        </style>

        <title>Cập nhật thương hiệu</title>
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

                    <h1>Update Brand</h1>
                    <form action="/Admin/Brand/Update" method="POST" enctype="multipart/form-data">
                        <div class="id">
                            <label class="required">Brand ID</label>
                            <input type="number" name="txtBrandID" readonly="true" value="<%= br.getId()%>">
                        </div>
                        <div class="name">
                            <label class="required">Brand name</label>
                            <input type="text" name="txtBrandName" value="<%= br.getName()%>">
                        </div>
                        <button type="submit" name="btnUpdateBrand" value="Submit" class="btnUpdateBrand">Update Brand</button>
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

        <script>
            $(document).ready(function () {

                $("form").validate({
                    rules: {
                        txtBrandName: {
                            required: true,
                            maxlength: 50
                        }
                    },
                    messages: {
                        txtBrandName: {
                            required: "Vui lòng nhập tên thương hiệu",
                            maxlength: "Tên thương hiệu không được vượt quá 50 ký tự"
                        }
                    },

                    errorPlacement: function (error, element) {
                        error.addClass("text-danger d-block mt-2");
                        error.insertAfter(element);
                    }
                });
            });
        </script>
    </body>
</html>
