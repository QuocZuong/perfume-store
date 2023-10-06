/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Import {

    private int Id;
    private int totalQuantity;
    private int totalCost;
    private String supplierName;
    private Date importAt;
    private Date deliveredAt;
    private int importByInventoryManager;

    private ArrayList<ImportDetail> importDetail;

    public Import(int Id, int totalQuantity, int totalCost, String supplierName, Date importAt, Date deliveredAt, int importByInventoryManager, ArrayList<ImportDetail> importDetail) {
        this.Id = Id;
        this.totalQuantity = totalQuantity;
        this.totalCost = totalCost;
        this.supplierName = supplierName;
        this.importAt = importAt;
        this.deliveredAt = deliveredAt;
        this.importByInventoryManager = importByInventoryManager;
        this.importDetail = importDetail;
    }

    public Import() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getImportAt() {
        return importAt;
    }

    public void setImportAt(Date importAt) {
        this.importAt = importAt;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Date deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public int getImportByInventoryManager() {
        return importByInventoryManager;
    }

    public void setImportByInventoryManager(int importByInventoryManager) {
        this.importByInventoryManager = importByInventoryManager;
    }

    public ArrayList<ImportDetail> getImportDetail() {
        return importDetail;
    }

    public void setImportDetail(ArrayList<ImportDetail> importDetail) {
        this.importDetail = importDetail;
    }

}
