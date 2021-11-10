package view.tm;

public class SavedOrderDetailsTM {
    private String itemCode;
    private String description;
    private String packSize;
    private int qtyForSell;
    private double unitPrice;
    private double discount;
    private double total;

    public SavedOrderDetailsTM() {
    }

    public SavedOrderDetailsTM(String itemCode, String description, String packSize, int qtyForSell, double unitPrice, double discount, double total) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.qtyForSell = qtyForSell;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.total = total;
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
