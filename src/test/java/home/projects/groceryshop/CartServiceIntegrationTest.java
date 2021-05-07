package home.projects.groceryshop;

import home.projects.groceryshop.service.CartService;
import home.projects.groceryshop.steps.CustomerTestSteps;
import home.projects.groceryshop.steps.ProductTestSteps;
import home.projects.groceryshop.transfer.cart.AddProductsToCartRequest;
import home.projects.groceryshop.transfer.cart.CartResponse;
import home.projects.groceryshop.transfer.cart.ProductInCartResponse;
import home.projects.groceryshop.transfer.cart.RemoveProductsFromCartRequest;
import home.projects.groceryshop.transfer.customer.CustomerResponse;
import home.projects.groceryshop.transfer.product.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
class CartServiceIntegrationTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerTestSteps customerTestSteps;

    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    void addProductsToCartWhenNewCart_thenCartIsCreated(){
        CustomerResponse customer = customerTestSteps.createCustomer();

        ProductResponse product = productTestSteps.createProduct();

        AddProductsToCartRequest cartRequest = new AddProductsToCartRequest();
        cartRequest.setCustomerId(customer.getId());
        cartRequest.setProductIds(Collections.singletonList(product.getId()));

        cartService.addProductsToCart(cartRequest);

        CartResponse cart = cartService.getCart(customer.getId());

        assertThat(cart, notNullValue());
        assertThat(cart.getId(), is(customer.getId()));

        assertThat(cart.getProducts(), notNullValue());
        assertThat(cart.getProducts(), hasSize(1));

        Iterator<ProductInCartResponse> productIterator = cart.getProducts().iterator();

        assertThat(productIterator.hasNext(), is(true));

        ProductInCartResponse nextProduct = productIterator.next();

        assertThat(nextProduct, notNullValue());
        assertThat(nextProduct.getId(), is(product.getId()));
        assertThat(nextProduct.getName(), is(product.getName()));
        assertThat(nextProduct.getPrice(), is(product.getPrice()));
    }

    @Test
    void removeProductsFromCart() {
        CustomerResponse customer = customerTestSteps.createCustomer();
//        customer.setId(11L);

        ProductResponse product = productTestSteps.createProduct();
//        product.setId(32L);

        RemoveProductsFromCartRequest cartRequest = new RemoveProductsFromCartRequest();
        cartRequest.setCustomerId(customer.getId());
        cartRequest.setProductId(product.getId());

//        cartService.removeProductFromCart(cartRequest);

    }
}
