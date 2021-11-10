package dto;

import java.util.ArrayList;

public class OrderDTO {
    private String orderId;
    private String orderDate;
    private String orderTime;
    private String custNIC;
    private double discount;
    private double cost;
    private ArrayList<ItemDetailsDTO> items;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", custNIC='" + custNIC + '\'' +
                ", discount=" + discount +
                ", cost=" + cost +
                ", items=" + items +
                '}';
    }

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String orderDate, String orderTime, String custNIC, double discount, double cost, ArrayList<ItemDetailsDTO> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.custNIC = custNIC;
        this.discount = discount;
        this.cost = cost;
        this.items = items;
    }

    public OrderDTO(String orderId, String custNIC, ArrayList<ItemDetailsDTO> items) {
        this.orderId = orderId;
        this.custNIC = custNIC;
        this.items = items;
    }

    public OrderDTO(String orderId, String orderDate, String custNIC, double cost, ArrayList<ItemDetailsDTO> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.custNIC = custNIC;
        this.cost = cost;
        this.items = items;
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

    public String getCustNIC() {
        return custNIC;
    }

    public void setCustNIC(String custNIC) {
        this.custNIC = custNIC;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ArrayList<ItemDetailsDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDetailsDTO> items) {
        this.items = items;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}
