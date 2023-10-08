package Interfaces.DAOs;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.Order;
import Models.Voucher;
import Models.Customer;

public interface IOrderDAO {

  /** The name of the table in the database. */
  String TABLE_NAME = "Order";
  /** The column name of the order's Id. */
  String ORDER_Id = "Order_Id";
  /** The column name of the order's {@link Customer} Id. */
  String CUSTOMER_Id = "Customer_Id";
  /** The column name of the order's {@link Voucher} Id. */
  String VOUCHER_Id = "Voucher_Id";
  /** The column name of the order's receiver name. */
  String ORDER_RECEIVER_NAME = "Order_Receiver_Name";
  /** The column name of the order's delivery address. */
  String ORDER_DELIVERY_ADDRESS = "Order_Delivery_Address";
  /** The column name of the order's receiver phone number. */
  String ORDER_PHONE_NUMBER = "Order_Phone_Number";
  /** The column name of the order's note. */
  String ORDER_NOTE = "Order_Note";
  /** The column name of the order's total. */
  String ORDER_TOTAL = "Order_Total";
  /** The column name of the order's deducted price. */
  String ORDER_DEDUCTED_PRICE = "Order_Deducted_Price";
  /** The column name of the order's status. */
  String ORDER_STATUS = "Order_Status";
  /** The column name of the order's created at. */
  String ORDER_CREATED_AT = "Order_Created_At";
  /** The column name of the order's checkout at. */
  String ORDER_CHECKOUT_AT = "Order_Checkout_At";
  /** The column name of the order's update at. */
  String ORDER_UPDATE_AT = "Order_Update_At";
  /** The column name of the order's checkout by order manager. */
  String ORDER_UPDATE_BY_ORDER_MANAGER = "Order_Update_By_Order_Manager";

  /**
   * Create an {@link Order} object from a {@link ResultSet}.
   * 
   * @param rs The {@code ResultSet} to create the {@code Order} object from.
   * @return An {@code Order} object.
   */
  public Order orderFactory(ResultSet rs);

  /**
   * Fill a {@link PreparedStatement} with an {@link Order}'s information.
   * 
   * @param ps    The {@code PreparedStatement} to be filled.
   * @param order The {@code Order} to get information from.
   * @return The {@code PreparedStatement} object that has all the {@code Order}'s
   *         information.
   * @throws NullPointerException if {@code ps} or {@code order} is {@code null}.
   * @throws SQLException         if a database access error occurs.
   */
  public PreparedStatement fillPreparedStatement(PreparedStatement ps, Order order)
      throws NullPointerException, SQLException;

  /**
   * Add a new {@link Order} to the database.
   * 
   * @param order The {@code Order} to be added.
   * @return {@code true} if the {@code Order} is added successfully,
   *         {@code false} otherwise.
   * @throws NullPointerException if {@code order} is {@code null}.
   */
  public boolean addOrder(Order order) throws NullPointerException;

  /**
   * Get all {@link Order}s in database.
   * 
   * @return A {@code List} containing all {@code Order} in database.
   */
  public List<Order> getAll();

  /**
   * Get an {@link Order} from the database.
   * 
   * @param Id The Id of the {@code Order} to be retrieved.
   * @return A {@code Order} object containing the order's information.
   */
  public Order getOrderByOrderId(int orderId);

  /**
   * Get an {@link Order} from the database.
   * 
   * @param customerId The Id of the {@code Customer} that owns the {@code Order}.
   * @return A {@code List} of {@code Order} objects that are belonged to the
   *         {@code CustomerId}.
   */
  public List<Order> getOrderByCustomerId(int customerId);

  /**
   * Update an {@link Order} in the database.
   * 
   * @param order The {@code Order} to be updated.
   * @return {@code true} if the {@code Order} is updated successfully,
   *         {@code false} otherwise.
   * @throws NullPointerException if {@code order} is {@code null}.
   */
  public boolean updateOrder(Order order) throws NullPointerException;

  /**
   * Delete an {@link Order} from the database.
   * 
   * @param Id The Id of the {@code Order} to be deleted.
   * @return {@code true} if the {@code Order} is deleted
   *         successfully,{@code false} otherwise.
   */
  public int deleteOrder(int Id);
}
