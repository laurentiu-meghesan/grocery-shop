package home.projects.groceryshop.transfer.cart;

import java.util.List;

public class AddProductsToCartRequest {

    private long customerId;
    private List<Long> productIds;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    @Override
    public String toString() {
        return "AddProductsToCartRequest{" +
                "customerId=" + customerId +
                ", productIds=" + productIds +
                '}';
    }
}
