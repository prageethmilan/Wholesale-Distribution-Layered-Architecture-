package view.tm;

public class OrderTM {
    private String orderId;
    private String orderDate;
    private String customerNic;
    private double orderDiscount;
    private double orderGrossTotal;
    private double orderNetTotal;

    public OrderTM() {
    }

    public OrderTM(String orderId, String orderDate, String customerNic, double orderDiscount, double orderGrossTotal, double orderNetTotal) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerNic = customerNic;
        this.orderDiscount = orderDiscount;
        this.orderGrossTotal = orderGrossTotal;
        this.orderNetTotal = orderNetTotal;
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

    public String getCustomerNic() {
        return customerNic;
    }

    public void setCustomerNic(String customerNic) {
        this.customerNic = customerNic;
    }

    public double getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(double orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public double getOrderGrossTotal() {
        return orderGrossTotal;
    }

    public void setOrderGrossTotal(double orderGrossTotal) {
        this.orderGrossTotal = orderGrossTotal;
    }

    public double getOrderNetTotal() {
        return orderNetTotal;
    }

    public void setOrderNetTotal(double orderNetTotal) {
        this.orderNetTotal = orderNetTotal;
    }
}
