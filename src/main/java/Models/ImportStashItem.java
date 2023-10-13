/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class ImportStashItem {

    private int inventoryManagerId;
    private int productId;
    private int quantity;
    private int cost;
    private int sumCost;

    public ImportStashItem() {
    }

    public int getInventoryManagerId() {
        return inventoryManagerId;
    }

    public void setInventoryManagerId(int inventoryManagerId) {
        this.inventoryManagerId = inventoryManagerId;
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

    public int getSumCost() {
        return sumCost;
    }

    public void setSumCost(int sumCost) {
        this.sumCost = sumCost;
    }

}
