package dto;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class OrderDetailsDTO {
    private String oid;
    private String itemCode;
    private int qty;
    private double discount;
    private double unitPrice;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String oid, String itemCode, int qty, double discount, double unitPrice) {
        this.oid = oid;
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailsDTO{" +
                "oid='" + oid + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
