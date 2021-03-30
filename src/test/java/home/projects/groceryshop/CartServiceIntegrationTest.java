package home.projects.groceryshop;

import home.projects.groceryshop.domain.Customer;
import home.projects.groceryshop.domain.Product;
import home.projects.groceryshop.service.CartService;
import home.projects.groceryshop.steps.CustomerTestSteps;
import home.projects.groceryshop.steps.ProductTestSteps;
import home.projects.groceryshop.transfer.cart.AddProductsToCartRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class CartServiceIntegrationTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerTestSteps customerTestSteps;

    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    void addProductsToCartWhenNewCart_thenCartIsCreated(){
        Customer customer = customerTestSteps.createCustomer();

        Product product = productTestSteps.createProduct();

        AddProductsToCartRequest cartRequest = new AddProductsToCartRequest();
        cartRequest.setCustomerId(customer.getId());
        cartRequest.setProductIds(Collections.singletonList(product.getId()));

        cartService.addProductsToCart(cartRequest);
    }
}
