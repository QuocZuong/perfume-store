package Models;

public class OrderManager extends Employee {

    private int orderManagerId;
    private int employeeId;

    public OrderManager() {
    }

    public OrderManager(Employee emp) {
        super.setUserId(emp.getUserId());
        super.setUsername(emp.getUsername());
        super.setPassword(emp.getPassword());
        super.setEmail(emp.getEmail());
        super.setActive(emp.isActive());
        super.setType(emp.getType());

        super.setEmployeeId(employeeId);
        super.setUserId(emp.getUserId());
        super.setCitizenId(emp.getCitizenId());
        super.setDateOfBirth(emp.getDateOfBirth());
        super.setPhoneNumber(emp.getPhoneNumber());
        super.setAddress(emp.getAddress());
        super.setRole(emp.getRole());
        super.setJoinDate(emp.getJoinDate());
        super.setRetireDate(emp.getRetireDate());
    }

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
