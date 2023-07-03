package Models;

public class User {

    private int ID;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String role;

    public User(
            int ID,
            String name,
            String username,
            String password,
            String email,
            String phoneNumber,
            String address,
            String role) {
        this.ID = ID;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getRole() {
        return role;
    }
}
