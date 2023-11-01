package Models;

public class Admin extends Employee {
    
    private int adminId;
    private int employeeId;
    
    public Admin() {
        setActive(true);
        setType("Employee");
    }
    
    public Admin(Employee emp) {
        //User
        setId(emp.getId());
        setName(emp.getName());
        setUsername(emp.getUsername());
        setPassword(emp.getPassword());
        setEmail(emp.getEmail());
        setActive(emp.isActive());
        setType(emp.getType());

        //Employee
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
    
    public int getAdminId() {
        return adminId;
    }
    
    public void setAdminId(int adminId) {
        this.adminId = adminId;
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
