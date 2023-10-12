package Models;

import java.util.ArrayList;

public class Customer extends User {

    private int customerId;
    private int customerCreditPoint;
    private ArrayList<DeliveryAddress> customerDeliveryAddress;

    public Customer() {
    }

    /**
     * Passing value from superclass to subclass
     *
     * @param user
     */
    public Customer(User user) {
        super(user);
        customerId = 0;
        customerCreditPoint = 0;
        customerDeliveryAddress = new ArrayList();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerCreditPoint() {
        return customerCreditPoint;
    }

    public void setCustomerCreditPoint(int customerCreditPoint) {
        this.customerCreditPoint = customerCreditPoint;
    }

    public ArrayList<DeliveryAddress> getCustomerDeliveryAddress() {
        return customerDeliveryAddress;
    }

    public void setCustomerDeliveryAddress(ArrayList<DeliveryAddress> customerDeliveryAddress) {
        this.customerDeliveryAddress = customerDeliveryAddress;
    }

}
