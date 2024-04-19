package Models;

import Lib.Generator;

public class Employee extends User {

    private int employeeId;
    private int UserId;
    private String citizenId;
    private Long dateOfBirth;
    private String phoneNumber;
    private String address;
    private Role role;
    private Long joinDate;
    private Long retireDate;

    public Employee() {
        setActive(true);
        setType("Employee");
    }

    public Employee(User user) {
        super.setId(user.getId());
        super.setName(user.getName());
        super.setUsername(user.getUsername());
        super.setPassword(user.getPassword());
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

    public String getDateOfBirth(Generator.DatePattern pattern) {
        return Generator.getDateTime(dateOfBirth, pattern);
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
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

    public String getJoinDate(Generator.DatePattern pattern) {
        return Generator.getDateTime(joinDate, pattern);
    }

    public Long getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Long joinDate) {
        this.joinDate = joinDate;
    }

    public String getRetireDate(Generator.DatePattern pattern) {
        return Generator.getDateTime(retireDate, pattern);
    }

    public Long getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Long retireDate) {
        this.retireDate = retireDate;
    }
}
