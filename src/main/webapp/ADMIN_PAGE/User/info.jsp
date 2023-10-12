

<%@page import="Models.Employee"%>
<%@page import="DAOs.EmployeeDAO"%>
<%@page import="DAOs.CustomerDAO"%>
<%@page import="Models.Customer"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! User us;%>

<%
    us = (User) request.getAttribute("UserInfo");
    Customer customer = null;
    Employee employee= null;
    if (us.getType().equals("Customer")) {
        CustomerDAO customerDAO = new CustomerDAO();
        customer = customerDAO.getCustomerByUserId(us.getId());
    } else if (us.getType().equals("Employee")) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employee = employeeDAO.getEmployeeByUserId(us.getId());
    }
    
    System.out.println("customer info " + customer);
    System.out.println("employee info " + employee);
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
        <link rel="stylesheet" href="/RESOURCES/admin/user/public/style/info.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">

        <style>
            #preview-img{
                width:20%;
                height: 20%;
            }
            .alert{
                z-index:-99 !important;
            }

        </style>

        <title>Chi tiết người dùng</title>
    </head>
    <body>
        <div class="container-fluid">
            <!--Navbar section-->
            <div class="row">
                <div class="col-md-12 nav">
                    <jsp:include page="/NAVBAR/AdminNavbar.jsp"></jsp:include>
                    </div>
                </div>


                <div class="container mt-5">
                    <div class="row">
                        <h1>User Information</h1>
                        <div class="wrapper">
                            <div class="id">
                                <label>User ID: </label>
                                <p><%= us.getId()%></p>
                            </div>
                            <div class="name">
                                <label>Name: </label>
                                <p><%= us.getName()%></p>
                            </div>
                            <div class="username">
                                <label>Username: </label>
                                <p><%= us.getUsername()%></p>
                            </div>
                            <div class="email">
                                <label>Email: </label>
                                <p><%= us.getEmail()%></p>
                            </div>
                            <div class="type">
                                <label>Type: </label>
                                <p><%= us.getType()%></p>
                            </div>
                            
                                 <!--Information base on type-->
                                 <c:choose>
                                 <c:when test='<%= (us.getType().equals("Employee"))%>'>
                                     <div class="employeeId">
                                        <label>Employee Id: </label>
                                        <p><%= employee.getEmployeeId()%></p>
                                    </div>
                                    <div class="employeeCitizenId">
                                        <label>Employee Citizen ID: </label>
                                        <p><%= employee.getCitizenId()%></p>
                                    </div>
                                    <div class="employeeDoB">
                                        <label>Employee DoB: </label>
                                        <p><%= employee.getDayOfBirth()%></p>
                                    </div>
                                    <div class="employeePhoneNumber">
                                        <label>Employee Phone Number: </label>
                                        <p><%= employee.getPhoneNumber()%></p>
                                    </div>
                                    <div class="employeeAddress">
                                        <label>Employee Address: </label>
                                        <p><%= employee.getAddress()%></p>
                                    </div>
                                    <div class="employeeRole">
                                        <label>Employee Role: </label>
                                        <p><%= employee.getRole().getName()%></p>
                                    </div>
                                    <div class="employeeJoinDate">
                                        <label>Employee Join Date: </label>
                                        <p><%= employee.getJoinDate()%></p>
                                    </div>
                                    <div class="employeeRetireDate">
                                        <label>Employee Retire Date: </label>
                                        <p><%= employee.getRetireDate() == null ? "Not Retired":employee.getRetireDate()%></p>
                                    </div>
                                 </c:when>
                                 
                                 <c:when test='<%= (us.getType().equals("Customer"))%>'>
                                     <div class="customerId">
                                        <label>Customer Id: </label>
                                        <p><%= customer.getCustomerId()%></p>
                                    </div>
                                    <div class="customerCreditPoint">
                                        <label>Customer Credit Point: </label>
                                        <p><%= customer.getCustomerCreditPoint()%></p>
                                    </div>
                                 </c:when>
                                 </c:choose>
                    </div>
                </div>
            </div> 
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

        <script src="/RESOURCES/admin/user/public/js/update.js"></script>

        <!--VietName Province APU-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>

       
        <!--Jquery Validation-->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>

    </body>
</html>