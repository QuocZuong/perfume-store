package Interfaces.DAOs;

import Exceptions.EmailDuplicationException;
import Exceptions.UsernameDuplicationException;
import Models.Customer;

public interface ICustomerDAO {

    public final String CUSTOMER_CREDIT_POINT = "Customer_Credit_Point";

    public int addCustomer(Customer customer);

    public Customer getCustomer(int customerId);

    public boolean register(String email) throws UsernameDuplicationException, EmailDuplicationException;
}
