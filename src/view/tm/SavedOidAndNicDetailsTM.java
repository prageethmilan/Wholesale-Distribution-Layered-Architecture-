package view.tm;

public class SavedOidAndNicDetailsTM {
    private String orderId;
    private String NIC;

    public SavedOidAndNicDetailsTM() {
    }

    public SavedOidAndNicDetailsTM(String orderId, String NIC) {
        this.orderId = orderId;
        this.NIC = NIC;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setorderId(String oId) {
        this.orderId = orderId;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }
}
