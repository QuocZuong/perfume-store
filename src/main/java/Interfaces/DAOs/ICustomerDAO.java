/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.DAOs;

import Models.Customer;

/**
 *
 * @author Acer
 */
public interface ICustomerDAO {

    public int addCustomer(Customer customer);

    public Customer getCustomer(int customerId);

    public int restoreCustomer(Customer customer);

    public int disableCustomer(Customer customer);

}
