package Interfaces.DAOs;

import Models.Customer;

public interface ICustomerDAO {

    public final String CUSTOMER_CREDIT_POINT = "Customer_Credit_Point";

    public boolean addCustomer(Customer customer);

    public Customer getCustomer(int customerId);

}
