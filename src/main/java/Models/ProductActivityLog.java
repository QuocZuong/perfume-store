package Models;

import Lib.Generator;

public class ProductActivityLog {

    private int productId;
    private String action;
    private String description;
    private int updatedByAdmin;
    private long updatedAt;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getUpdatedAt(Generator.DatePattern pattern) {
        return Generator.getDateTime(updatedAt, pattern);
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
