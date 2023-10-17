package Models;

import java.sql.Date;

public class Employee extends User {

    private int employeeId;
    private int UserId;
    private String citizenId;
    private Date dateOfBirth;
    private String phoneNumber;
    private String address;
    private Role role;
    private Date joinDate;
    private Date retireDate;

    public Employee() {

    }

    public Employee(User user) {
        super.setId(user.getId());
        super.setName(user.getName());
        super.setUsername(user.getUsername());
        super.setPassword(user.getUsername());
        super.setEmail(user.getEmail());
        super.setActive(user.isActive());
        super.setType(user.getType());
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Date retireDate) {
        this.retireDate = retireDate;
    }

}
