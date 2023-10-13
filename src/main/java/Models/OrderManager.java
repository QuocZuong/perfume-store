package Models;

public class OrderManager extends Employee{

    private int orderManagerId;
    private int employeeId;
    
    public int getOrderManagerId() {
        return orderManagerId;
    }

    public void setOrderManagerId(int orderManagerId) {
        this.orderManagerId = orderManagerId;
    }

    @Override
    public int getEmployeeId() {
        return employeeId;
    }

    @Override
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
