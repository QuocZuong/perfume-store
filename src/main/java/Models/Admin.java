package Models;

public class Admin extends Employee {
    
    private int adminId;
    private int employeeId;
    
    public Admin() {
    }
    public Admin(Employee emp) {
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
