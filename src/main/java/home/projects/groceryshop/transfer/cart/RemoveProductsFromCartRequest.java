package home.projects.groceryshop.transfer.cart;

public class RemoveProductsFromCartRequest {

    private long customerId;
    private long productId;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "RemoveProductsFromCartRequest{" +
                "customerId=" + customerId +
                ", productId=" + productId +
                '}';
    }
}
