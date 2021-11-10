package entity;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class OrderId {
    private String orderId;
    private String nic;

    public OrderId() {
    }

    public OrderId(String orderId, String nic) {
        this.orderId = orderId;
        this.nic = nic;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
