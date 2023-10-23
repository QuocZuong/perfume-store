package Models;

import java.sql.Date;
import java.util.List;

import Lib.Generator;

public class Import {

    private int Id;
    private int totalQuantity;
    private int totalCost;
    private String supplierName;
    private long importAt;
    private long deliveredAt;
    private int importByInventoryManager;

    private List<ImportDetail> importDetail;

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

    public String getImportAt(Generator.DatePattern pattern) {
        return Generator.getDateTime(importAt, pattern);
    }

    public long getImportAt() {
        return importAt;
    }

    public void setImportAt(long importAt) {
        this.importAt = importAt;
    }

    public String getDeliveredAt(Generator.DatePattern pattern) {
        return Generator.getDateTime(deliveredAt, pattern);
    }

    public long getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(long deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public int getImportByInventoryManager() {
        return importByInventoryManager;
    }

    public void setImportByInventoryManager(int importByInventoryManager) {
        this.importByInventoryManager = importByInventoryManager;
    }

    public List<ImportDetail> getImportDetail() {
        return importDetail;
    }

    public void setImportDetail(List<ImportDetail> importDetail) {
        this.importDetail = importDetail;
    }

}
