<div class="col-md-12 d-flex justify-content-center align-items-center flex-column">
          <table class="table" id="table">
            <thead>
              <tr>
                <td>ID</td>
                <td>Tên khách hàng</td>
                <td>Địa chỉ</td>
                <td>Số điện thoại</td>
                <td>Ghi chú</td>
                <td>Tổng tiền</td>
                <td>Giảm giá</td>
                <td>Trạng thái</td>
                <td>Ngày tạo</td>
                <td>Ngày thanh toán</td>
                <td>Ngày cập nhật</td>
                <td>Người cập nhật</td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
            </thead>
            <tbody>
              <c:if test='<%= (orderList.size() != 0)%>'>
                <c:forEach var="i" begin="0" end="<%= orderList.size() - 1%>">
                  <%
                                        OrderManagerDAO omDAO = new OrderManagerDAO();
                                        Order o = orderList.get((int) pageContext.getAttribute("i"));
                                        Customer c = cDAO.getCustomer(o.getCustomerId());

                                        String orderManagerName = "";
                                        if (o.getUpdateByOrderManager() > 0) {
                                            OrderManager om = omDAO.getOrderManager(o.getUpdateByOrderManager());
                                            orderManagerName = om.getName();
                                        }

                                        String checkoutAt = o.getCheckoutAt() == null ? "" : o.getCheckoutAt().toString();
                                        String updateAt = o.getUpdateAt() == null ? "" : o.getUpdateAt().toString();

                                        boolean isActive = !o.getStatus().equals("Rejected");
                                        String status = o.getStatus();
                                        boolean isRejected = status.equals("REJECTED");
                                        boolean isAccepted = status.equals("ACCEPTED");
                  %>
                  <tr class="rowTable <%= isActive ? " " : "faded"%>">
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getId()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= c.getName()%></td>
                    <td class="<%= isActive ? " " : "faded"%>" class="description">
                      <div class="content"><<%= o.getDeliveryAddress()%></div>
                      <button class="expand-btn">Xem thêm</button>
                    </td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getPhoneNumber()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getNote()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getTotal()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getDeductedPrice()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getStatus()%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= o.getCreatedAt()%></td>                                        
                    <td class="<%= isActive ? " " : "faded"%>"><%= checkoutAt%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= updateAt%></td>
                    <td class="<%= isActive ? " " : "faded"%>"><%= orderManagerName%></td>

                    <td class="<%= isActive ? " " : "faded"%>">
                      <a href="/Admin/User/OrderDetail/ID/<%= o.getId()%>" class="<%= isActive ? "" : "disabled"%> btn btn-outline-primary rounded-0">🤔</a>
                    </td>
                    <td class="buttonStatus <%= isAccepted ? "faded" : ""%>">
                      <a href="/Admin/Order/ID/<%= o.getId()%>/Accept" class="btn btn-outline-success rounded-0">✅</a>
                    </td>
                    <td class="buttonStatus <%= isRejected ? "faded" : ""%>">
                      <a href="/Admin/Order/ID/<%= o.getId()%>/Reject" class="btn btn-outline-danger rounded-0">❌</a>
                    </td>
                  </tr>

                </c:forEach>
              </c:if>
            </tbody>
          </table>
        </div>