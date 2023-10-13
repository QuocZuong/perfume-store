package Models;

public class InventoryManager extends Employee{
    private int inventoryManagerId;
    private int employeeId;

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
