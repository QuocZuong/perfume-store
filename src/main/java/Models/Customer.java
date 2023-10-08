package Models;

public class Customer extends User {

    private int customerId;
    private int customerCreditPoint;

    public Customer() {
    }

    /**
     * Passing value from superclass to subclass
     *
     * @param user
     */
    public Customer(User user) {
        super(user);
        customerCreditPoint = 0;
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

}
