package Interfaces.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import Models.OrderDetail;

public interface IOrderDetailDAO {

  String TABLE_NAME = "OrderDetail";
  String ORDER_ID = "Order_ID";
  String PRODUCT_ID = "Product_ID";
  String QUANTITY = "Quantity";
  String PRICE = "Price";
  String TOTAL = "Total";

  /**
   * Create an {@link OrderDetail} object from a {@link ResultSet}.
   * @param rs The {@code ResultSet} to create the {@code OrderDetail} object from.
   * @return An {@code OrderDetail} object.
   * @throws NullPointerException if {@code rs} is {@code null}.
   */
  public OrderDetail orderDetailFactory(ResultSet rs) throws NullPointerException;

  /**
   * Fill a {@link PreparedStatement} with an {@link OrderDetail} object.
   * @param ps The {@code PreparedStatement} to fill.
   * @param orderDetail The {@code OrderDetail} object to get data from.
   * @return The {@code PreparedStatement} filled.
   * @throws NullPointerException if {@code ps} or {@code orderDetail} is {@code null}.
   */
  public PreparedStatement fillOrderDetail(PreparedStatement ps, OrderDetail orderDetail) throws NullPointerException;

  /**
   * Add an {@link OrderDetail} object to the database.
   * @param orderDetail The {@code OrderDetail} object to add.
   * @return {@code true} if the {@code OrderDetail} object is added successfully, {@code false} otherwise.
   * @throws NullPointerException if {@code orderDetail} is {@code null}.
   */
  public boolean addOrderDetail(OrderDetail orderDetail) throws NullPointerException;

  /**
   * Add a {@link List} of {@link OrderDetail} objects to the database.
   * @param list The {@code List} of {@code OrderDetail} objects to add.
   * @return {@code true} if the {@code List} of {@code OrderDetail} objects is added successfully, {@code false} otherwise.
   * @throws NullPointerException if {@code list} is {@code null}.
   */
  public boolean addOrderDetail(List<OrderDetail> list) throws NullPointerException;

  /**
   * Get all {@link OrderDetail} objects in database.
   * @param orderId The Id of the {@code Order} that the {@code OrderDetail} objects belong to.
   * @return A {@code List} containing all {@code OrderDetail} objects in database.
   */
  public List<OrderDetail> getOrderDetail(int orderId);

  /**
   * Update an {@link OrderDetail} in the database.
   * @param orderDetail The {@code OrderDetail} to be updated.
   * @return {@code true} if the {@code OrderDetail} is updated successfully, {@code false} otherwise.
   * @throws NullPointerException if {@code orderDetail} is {@code null}.
   */
  public boolean updateOrderDetail(OrderDetail orderDetail) throws NullPointerException;

  /**
   * Delete an {@link OrderDetail} from the database.
   * @param orderId The Id of the {@code Order} that the {@code OrderDetail} belongs to.
   * @return {@code true} if the {@code OrderDetail} is deleted successfully, {@code false} otherwise.
   */
  public boolean deleteOrderDetail(int orderId);
}
