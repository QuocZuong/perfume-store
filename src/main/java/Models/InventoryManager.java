package Models;

public class InventoryManager extends Employee {

    private int inventoryManagerId;
    private int employeeId;

    public InventoryManager() {
        setActive(true);
        setType("Employee");
    }

    public InventoryManager(Employee emp) {
        // User
        setUserId(emp.getUserId());
        setName(emp.getName());
        setUsername(emp.getUsername());
        setPassword(emp.getPassword());
        setEmail(emp.getEmail());
        setActive(emp.isActive());
        setType(emp.getType());

        // Employee
        setEmployeeId(emp.getEmployeeId());
        setUserId(emp.getUserId());
        setCitizenId(emp.getCitizenId());
        setDateOfBirth(emp.getDateOfBirth());
        setPhoneNumber(emp.getPhoneNumber());
        setAddress(emp.getAddress());
        setRole(emp.getRole());
        setJoinDate(emp.getJoinDate());
        setRetireDate(emp.getRetireDate());
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
