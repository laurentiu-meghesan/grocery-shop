package home.projects.groceryshop.transfer.cart;

import java.util.Set;

public class CartResponse {

    private long id;
    private Set<ProductInCartResponse> products;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<ProductInCartResponse> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductInCartResponse> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "CartResponse{" +
                "id=" + id +
                ", products=" + products +
                '}';
    }
}
