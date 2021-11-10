package entity;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class Order {
    private String orderId;
    private String orderDate;
    private String orderTime;
    private String custNIC;
    private double discount;
    private double cost;

    public Order() {
    }

    public Order(String orderId, String orderDate, String orderTime, String custNIC, double discount, double cost) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.custNIC = custNIC;
        this.discount = discount;
        this.cost = cost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustNIC() {
        return custNIC;
    }

    public void setCustNIC(String custNIC) {
        this.custNIC = custNIC;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
