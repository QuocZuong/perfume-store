package Models;

public class OrderManager extends Employee {

    private int orderManagerId;
    private int employeeId;

    public OrderManager() {
    }

    public OrderManager(Employee emp) {
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
