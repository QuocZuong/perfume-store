package Interfaces.DAOs;

import Models.Customer;

public interface ICustomerDAO {

    public int addCustomer(Customer customer);

    public Customer getCustomer(int customerId);

    public int restoreCustomer(Customer customer);

    public int disableCustomer(Customer customer);

}
