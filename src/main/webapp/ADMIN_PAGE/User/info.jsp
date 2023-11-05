    

<%@page import="Lib.Generator.DatePattern"%>
<%@page import="Lib.Generator"%>
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
    Employee employee = null;
    if (us.getType().equals("Customer")) {
        CustomerDAO customerDAO = new CustomerDAO();
        customer = customerDAO.getCustomerByUserId(us.getId());
    } else if (us.getType().equals("Employee")) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employee = employeeDAO.getEmployeeByUserId(us.getId());
    }

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

        <link rel="stylesheet" href="/RESOURCES/admin/user/public/style/update.css" />
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp" />
        <script src="/RESOURCES/plugin/jquery-3.7.1.min.js"></script>
        <script src="/RESOURCES/plugin/jquery-validation-1.19.5/dist/jquery.validate.min.js"></script>
        <style>
            #preview-img {
                width: 20%;
                height: 20%;
            }
            .alert {
                z-index: -99 !important;
            }

            span.arrow {
                margin-left: 6px;
                height: 17px;
            }
            label.error {
                color: red;
                margin-top: 5px;
                height: 17px;
                width: fit-content;
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
                        <form action="/Admin/User/Update/Customer" method="POST" id="updateCustomer">
                            <div class="id">
                                <label>User ID *</label>
                                <input type="number" name="txtUserID" readonly="true" value="<%= us.getId()%>" />
                        </div>

                        <div class="name">
                            <label>Name *</label>
                            <input type="text" name="txtName" readonly="true" value="<%= us.getName() == null ? "" : us.getName()%>">
                        </div>
                        <div class="username">
                            <label>Username *</label>
                            <input type="text" name="txtUsername" readonly="true" value="<%= us.getUsername()%>" />
                        </div>
                        <div class="email">
                            <label>Email *</label>
                            <input type="text" name="txtEmail" readonly="true" value="<%= us.getEmail()%>" />
                        </div>
                        <div class="type">
                            <label>Type: </label>
                            <input type="text" name="txtType" readonly="true" value="<%= us.getType()%>" />
                        </div>

                        <!--Information base on type-->
                        <c:choose>
                            <c:when test='<%= (us.getType().equals("Employee"))%>'>
                                <div class="employeeId">
                                    <label>Employee ID: </label>
                                    <input type="text" name="txtEmployeeId" readonly="true" value="<%=employee.getEmployeeId()%>" />
                                </div>
                                <div class="employeeCitizenId">
                                    <label>Employee Citizen ID: </label>
                                    <input type="text" name="txtEmployeeCitizenId" readonly="true" value="<%=employee.getCitizenId()%>" />
                                </div>
                                <div class="employeeDoB">
                                    <label>Employee DoB: </label>
                                    <input type="text" name="txtEmployeeDoB" readonly="true" value="<%=Generator.getDateTime(employee.getDateOfBirth(), DatePattern.DateForwardSlashPattern)%>" />
                                </div>
                                <div class="employeePhoneNumber">
                                    <label>Employee Phone Number: </label>
                                    <input type="text" name="txtEmployeePhoneNumber" readonly="true" value="<%=employee.getPhoneNumber()%>" />
                                </div>
                                <div class="employeeAddress">
                                    <label>Employee Address:</label>
                                    <input type="text" name="txtEmployeeAddress" readonly="true" value="<%=employee.getAddress()%>" />
                                </div>
                                <div class="employeeRole">
                                    <label>Employee Role: </label>
                                    <input type="text" name="txtEmployeeRole" readonly="true" value="<%=employee.getRole().getName()%>" />
                                </div>
                                <div class="employeeJoinDate">
                                    <label>Employee Join Date: </label>
                                    <input type="text" name="txtEmployeeJoinDate" readonly="true" value="<%=Generator.getDateTime(employee.getJoinDate(), DatePattern.DateForwardSlashPattern)%>" />
                                </div>
                                <div class="employeeRetireDate">
                                    <label>Employee Retire Date: </label>
                                    <input type="text" name="txtEmployeeRetireDate" readonly="true" value="<%=employee.getRetireDate() == null ? "Not retired" : Generator.getDateTime(employee.getRetireDate(), DatePattern.DateForwardSlashPattern)%>" />
                                </div>  
                            </c:when>

                            <c:when test='<%= (us.getType().equals("Customer"))%>'>
                                <div class="customerId">
                                    <label>Customer Id: </label>
                                    <input type="text" name="txtCustomerId" readonly="true" value="<%= customer.getCustomerId()%>" />
                                </div>
                                <div class="customerCreditPoint">
                                    <label>Customer Credit Point: </label>
                                    <input type="text" name="txtCustomerCreditPoint" readonly="true" value="<%= customer.getCustomerCreditPoint()%>" />
                                </div>
                            </c:when>
                        </c:choose>
                        <form/>
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