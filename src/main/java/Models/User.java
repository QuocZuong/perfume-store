package Models;

public class User implements Cloneable {

    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private boolean active;
    private String type;

    public User() {
    }

    /**
     * For passing value to other subclass
     *
     * @param user
     */
    public User(User user) {
        id = user.getId();
        name = user.getName();
        username = user.getUsername();
        password = user.getPassword();
        email = user.getEmail();
        active = user.isActive();
        type = user.getType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
