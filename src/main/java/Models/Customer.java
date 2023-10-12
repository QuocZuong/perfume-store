package Models;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private int customerId;
    private int customerCreditPoint;
    private List<DeliveryAddress> deliveryAddress;

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
        deliveryAddress = new ArrayList();
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

    public List<DeliveryAddress> getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(List<DeliveryAddress> deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
}
