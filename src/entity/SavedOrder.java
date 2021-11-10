package entity;

import dto.ItemDetailsDTO;

import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class SavedOrder {
    private String oId;
    private String NIC;
    private String itemCode;
    private String description;
    private String packSize;
    private int qtyForSell;
    private double unitPrice;
    private double discount;
    private double total;

    public SavedOrder() {
    }

    public SavedOrder(String oId, String NIC, String itemCode, String description, String packSize, int qtyForSell, double unitPrice, double discount, double total) {
        this.oId = oId;
        this.NIC = NIC;
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.qtyForSell = qtyForSell;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.total = total;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public int getQtyForSell() {
        return qtyForSell;
    }

    public void setQtyForSell(int qtyForSell) {
        this.qtyForSell = qtyForSell;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
