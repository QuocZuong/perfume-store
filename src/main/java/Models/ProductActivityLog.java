package Models;

import java.sql.Date;

public class ProductActivityLog {
    private int Id;
    private String action;
    private String description;
    private int updatedByAdmin;
    private Date updatedAt;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUpdatedByAdmin() {
        return updatedByAdmin;
    }

    public void setUpdatedByAdmin(int updatedByAdmin) {
        this.updatedByAdmin = updatedByAdmin;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
