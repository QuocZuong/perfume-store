<%@page import="Models.Product"%>
<%@page import="Lib.Generator"%>
<%@page import="Models.Admin"%>
<%@page import="java.util.List"%>
<%@page import="DAOs.AdminDAO"%>
<%@page import="Lib.Generator.DatePattern"%>
<%@page import="Models.Voucher"%>
<%@page import="Lib.ExceptionUtils"%>
<%@page import="Models.Customer"%>
<%@page import="Models.Employee"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.BrandDAO"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%! String Tinh, QuanHuyen, PhuongXa;%>
<%!Voucher voucher;%>
<%! ProductDAO pDAO = new ProductDAO(); %>
<%! List<Integer> approvedProductId;%>

<%
    approvedProductId = request.getAttribute("ApprovedProductId") == null ? null : (List<Integer>) request.getAttribute("ApprovedProductId");
    voucher = (Voucher) request.getAttribute("VoucherUpdate");
    // Handling execption
    String queryString = request.getQueryString();
    boolean isErr = ExceptionUtils.isWebsiteError(queryString);
    String exeptionMessage = ExceptionUtils.getMessageFromExceptionQueryString(queryString);
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
      crossorigin="anonymous"
      />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond&family=Josefin+Sans:wght@200&family=Josefin+Slab&display=swap"
      rel="stylesheet"
      />
    <link
      href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css"
      rel="stylesheet"
      type="text/css"
      />

    <!--Custom Style-->
    <link rel="stylesheet" href="/RESOURCES/admin/user/public/style/update.css" />
    <link rel="stylesheet" href="/RESOURCES/admin/voucher/public/style/list.css" />

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

    <title>Cập nhật khách hàng</title>
  </head>
  <body>
    <div class="container-fluid">
      <!--Navbar section-->
      <div class="row">
        <div class="col-md-12 nav">
          <jsp:include page="/NAVBAR/AdminNavbar.jsp"></jsp:include>
          </div>
        </div>

        <div class="row">
          <!--Execption Handling-->
        <c:if test="<%=isErr%>">
          <h1 class="alert alert-danger text-center"><%=exeptionMessage%></h1>
        </c:if>
        <!--Execption Handling-->
      </div>
      <div class="container mt-5">
        <div class="row">
          <div class="col-sm-6">

            <h1>Update Voucher</h1>
            <form action="/Admin/Voucher/Update" method="POST" id="updateCustomer">
              <div class="id">
                <label>Voucher ID *</label>
                <input type="number" name="txtId" readonly="true" value="<%= voucher.getId()%>" />
              </div>
              <div class="code">
                <label>Code *</label>
                <input type="text" name="txtCode" value="<%= voucher.getCode()%>" />
              </div>
              <div class="quantity">
                <label>Quantity *</label>
                <input type="number" name="txtQuantity" value="<%= voucher.getQuantity()%>" />
              </div>
              <div class="discount-percent">
                <label>Discount percent *</label>
                <input type="number" name="txtDiscountPercent" value="<%= voucher.getDiscountPercent()%>" />
              </div>
              <div class="discount-max">
                <label>Discount max *</label>
                <input type="number" name="txtDiscountMax" value="<%= voucher.getDiscountMax()%>" />
              </div>
              <div class="created-at">
                <label>Created at *</label>
                <input type="date" name="txtCreateAt" value="<%=  Generator.getDateTime(voucher.getCreatedAt(), DatePattern.DateSqlPattern)%>" />
              </div>
              <div class="expired-at">
                <label>Expired at *</label>
                <input type="date" name="txtExpiredAt" value="<%= Generator.getDateTime(voucher.getExpiredAt(), DatePattern.DateSqlPattern)%>" />
              </div>
              <button type="submit" name="btnUpdateVoucher" value="Submit" class="btnUpdateCustomer mb-3">
                Update Voucher
              </button>
              <br />
            </form>
          </div>
          <div class="col-sm-4 ms-5">
            <div class="list">

              <%
                                List<Product> productList = pDAO.getAll();
              %>
              <c:choose>
                <c:when test='<%=productList.size() == 0%>'>
                  <div>Không có sản phẩm nào để chọn</div>
                </c:when>

                <c:otherwise>
                  <div class="scroll-list">
                    <input placeholder="Tìm kiếm nhanh" type="text" name="" id="searchBox">
                    
                    <!-- Product List -->
                    <div class="list-product">
                      <c:forEach var='i' begin='0' end="<%=productList.size() - 1%>">
                        <%
                                                  int index = (int) pageContext.getAttribute("i");
                                                  Product product = productList.get(index);

                                                  boolean isCheck = true;
                        %>
                        <div>
                          <div class="item fw-bold">
                            <div class="row">
                              <div class="col-sm-12 row curso">
                                <input type="checkbox" id="<%= "Product_Item_" + product.getId()%>" value="<%= product.getName()%>" class="d-none">
                                <label class="col-6 d-flex align-items-center" for="<%= "Product_Item_" + product.getId()%>"> <%= product.getName()%></label>
                                <img class="col-2 me-5" src=<%= product.getImgURL()%> alt="<%= product.getName()%>">
                              </div>
                            </div>
                          </div>
                        </div>   
                      </c:forEach>
                    </div>
                  </div>

                </c:otherwise>

              </c:choose>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
      crossorigin="anonymous"
    ></script>
    <script src="/RESOURCES/admin/product/public/js/list.js"></script>

    <!--Jquery Validation-->
    <script>
      $(document).ready(function () {
        //                $.validator.addMethod("regex", function (value, regex) {
        //                    return regex.test(value);
        //                }, "Wrong input.");

        $("#updateCustomer").validate({
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
            txtEmail: {
              required: true,
              email: true,
              maxlength: 100
            },
            txtCitizenId: {
              required: true,
              maxlength: 20
            },
            txtDOB: {
              required: true
            },
            txtPhoneNumber: {
              required: true,
              digits: true,
              maxlength: 10,
              minlength: 10
            },
            txtAddress: {
              maxlength: 500
            },
            txtJoinDate: {
              required: true
            },
          },
          messages: {
            txtName: {
              required: "Tên không được để trống.",
              maxlength: "Tên không được vượt quá 50 ký tự."
            },
            txtUsername: {
              required: "Tên đăng nhập không được để trống.",
              maxlength: "Tên đăng nhập không được vượt quá 50 ký tự."
            },
            txtPassword: {
              required: "Mật khẩu không được để trống.",
              minlength: "Mật khẩu phải có ít nhất 6 ký tự."
            },
            txtEmail: {
              required: "Email không được để trống.",
              email: "Email không hợp lệ.",
              maxlength: "Email không được vượt quá 100 ký tự."
            },
            txtCitizenId: {
              required: "Số CMND không được để trống.",
              maxlength: "Số CMND không được vượt quá 20 ký tự."
            },
            txtDOB: {
              required: "Ngày tháng năm sinh không được để trống"
            },
            txtPhoneNumber: {
              required: "Số điện thoại không được để trống",
              digits: "Số điện thoại không hợp lệ",
              maxlength: "Số điện thoại phải là 10 chữ số",
              minlength: "Số điện thoại phải là 10 chữ số"
            },
            txtAddress: {
              maxlength: "Địa chỉ không được vượt quá 500 ký tự."
            },
            txtJoinDate: {
              required: "Ngày tham gia không được để trống."
            },
          },
        });
      });
    </script>

    <script>
      const typeSelect = document.querySelector('.typeSelect');

      const role = document.querySelector('.role');
      const citizenId = document.querySelector('.citizenId');
      const dateOfBirth = document.querySelector('.dateOfBirth');
      const phone = document.querySelector('.phone');
      const address = document.querySelector('.address');
      const joinDate = document.querySelector('.joinDate');
      const retireDate = document.querySelector('.retireDate');

      typeSelect.addEventListener('change', function () {
        if (typeSelect.value === "Employee") {
          citizenId.classList.remove('hidden');
          role.classList.remove('hidden');
          dateOfBirth.classList.remove('hidden');
          phone.classList.remove('hidden');
          address.classList.remove('hidden');
          joinDate.classList.remove('hidden');
          retireDate.classList.remove('hidden');

        } else {
          citizenId.classList.add('hidden');
          role.classList.add('hidden');
          dateOfBirth.classList.add('hidden');
          phone.classList.add('hidden');
          address.classList.add('hidden');
          joinDate.classList.add('hidden');
          retireDate.classList.add('hidden');
        }
      });

      if (typeSelect.value === "Employee") {
        citizenId.classList.remove('hidden');
        role.classList.remove('hidden');
        dateOfBirth.classList.remove('hidden');
        phone.classList.remove('hidden');
        address.classList.remove('hidden');
        joinDate.classList.remove('hidden');
        retireDate.classList.remove('hidden');
      } else {
        citizenId.classList.add('hidden');
        role.classList.add('hidden');
        dateOfBirth.classList.add('hidden');
        phone.classList.add('hidden');
        address.classList.add('hidden');
        joinDate.classList.add('hidden');
        retireDate.classList.add('hidden');
      }
    </script>
    <script>
      $(document).ready(function () {
        // Highlight items when enabled
        const items = $('.item');

        items.click(function (e) {
          const item = $(this);

          const checkbox = item.find('input[type="checkbox"]');
          checkbox.prop('checked', !checkbox.prop('checked'));
          const isChecked = checkbox.prop('checked');

          console.log(`Is the input checked? ${isChecked}`);

          if (isChecked === true) {
            item.addClass('enabled');
          } else {
            item.removeClass('enabled')
          }
        });

      });
    </script>
  </body>
</html>
