package Interfaces.DAOs;

import Exceptions.EmailDuplicationException;
import Exceptions.OperationAddFailedException;
import Exceptions.UsernameDuplicationException;
import Models.Customer;

public interface ICustomerDAO {

    public final String CUSTOMER_CREDIT_POINT = "Customer_Credit_Point";
    public final String CUSTOMER_ID = "Customer_ID";

    public int addCustomer(Customer customer) throws OperationAddFailedException;

    public Customer getCustomer(int customerId);

    public Customer register(String email) throws UsernameDuplicationException, EmailDuplicationException;
}
