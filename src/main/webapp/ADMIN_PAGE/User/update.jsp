

<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! BrandDAO bDAO = new BrandDAO();  %>
<%! ProductDAO pDAO = new ProductDAO(); %>
<%! User us;%>

<% us = (User) request.getAttribute("UserUpdate");%>

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
        <link rel="stylesheet" href="/RESOURCES/admin/user/public/style/update.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">

        <style>
            #preview-img{
                width:20%;
                height: 20%;
            }

        </style>

        <title>Cập nhật người dùng</title>
    </head>
    <body>

        <div class="container">
            <h1>Update User</h1>
            <form action="/Admin/User/Update" method="POST">
                <div class="id">
                    <label>User ID *</label>
                    <input type="number" name="txtUserID" readonly="true" value="<%= us.getID()%>">
                </div>
                <div class="name">
                    <label>Name *</label>
                    <input type="text" name="txtName" value="<%= us.getName()%>">
                </div>
                <div class="username">
                    <label>Username *</label>
                    <input type="text" name="txtUsername" value="<%= us.getUsername()%>">
                </div>
                <div class="password">
                    <label>Password *</label>
                    <input type="password" name="txtPassword" value="<%= us.getPassword()%>">
                </div>
                <div class="phone">
                    <label>Phone Number *</label>
                    <input type="text" name="txtPhoneNumber" value="<%= us.getPhoneNumber()%>">
                </div>
                <div class="email">
                    <label>Email *</label>
                    <input type="text" name="txtEmail" value="<%= us.getEmail()%>">
                </div>
                <div class="address">
                    <label>Address *</label>
                    <input type="text" name="txtAddress" value="<%= us.getAddress()%>">
                </div>
                <div class="role">
                    <label>Role *</label>
                    <select name="txtRole">
                        <option value="Client" <%= (us.getRole().equals("Client")) ? "selected" : ""%>>Client</option>
                        <option value="Admin" <%= (us.getRole().equals("Admin")) ? "selected" : ""%>>Admin</option>
                    </select>
                </div>
                <button type="submit" name="btnUpdateUser" value="Submit" class="btnUpdateUser">Update User</button>
            </form>

        </div>



        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <script src="/RESOURCES/admin/public/js/main.js"></script>



        <!--Jquery Validation-->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
        <script>
            $(document).ready(function () {
                $("form").validate({
                    rules: {
                        txtName: {
                            required: true,
                            maxlength: 50
                        },
                        txtUsername: {
                            required: true,
                            maxlength: 50
                        },
                        txtPassword: {
                            required: true,
                            minlength: 6
                        },
                        txtPhoneNumber: {
                            required: true,
                            digits: true,
                            maxlength: 10
                        },
                        txtEmail: {
                            required: true,
                            email: true,
                            maxlength: 100
                        },
                        txtAddress: {
                            required: true,
                            maxlength: 500
                        },
                        txtRole: {
                            required: true,
                            maxlength: 50
                        }
                    },
                    messages: {
                        txtPassword: {
                            minlength: "Password must be at least 6 characters long"
                        },
                        txtPhoneNumber: {
                            digits: "Phone number must contain only digits",
                            maxlength: "Phone number must not exceed 10 digits"
                        },
                        txtEmail: {
                            email: "Please enter a valid email address",
                            maxlength: "Email must not exceed 100 characters"
                        },
                        txtAddress: {
                            maxlength: "Address must not exceed 500 characters"
                        }
                    }
                });
            });
        </script>

    </body>
</html>
