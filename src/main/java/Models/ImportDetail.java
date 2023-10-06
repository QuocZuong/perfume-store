/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ImportDetail {
    private int importId;
    private int productId;
    private int quantity;
    private int cost;       

    public ImportDetail(int importId, int productId, int quantity, int cost) {
        this.importId = importId;
        this.productId = productId;
        this.quantity = quantity;
        this.cost = cost;
    }

    public ImportDetail() {
        
    }

    public int getImportId() {
        return importId;
    }

    public void setImportId(int importId) {
        this.importId = importId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
}
