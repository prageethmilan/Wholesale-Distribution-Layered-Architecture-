package view.tm;

public class ItemTM {
    private String itemCode;
    private String description;
    private String packSize;
    private int quantityOnHand;
    private double discount;
    private double unitPrice;

    public ItemTM() {
    }

    public ItemTM(String itemCode, String description, String packSize, int quantityOnHand, double discount, double unitPrice) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.quantityOnHand = quantityOnHand;
        this.discount = discount;
        this.unitPrice = unitPrice;
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

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
