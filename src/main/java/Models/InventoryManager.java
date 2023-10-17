package Models;

public class InventoryManager extends Employee{
    private int inventoryManagerId;
    private int employeeId;

    public InventoryManager() {
    }

    public InventoryManager(Employee emp) {
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

    public int getInventoryManagerId() {
        return inventoryManagerId;
    }

    public void setInventoryManagerId(int inventoryManagerId) {
        this.inventoryManagerId = inventoryManagerId;
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
