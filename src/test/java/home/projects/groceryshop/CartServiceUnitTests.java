package home.projects.groceryshop;

import home.projects.groceryshop.domain.Cart;
import home.projects.groceryshop.domain.Customer;
import home.projects.groceryshop.domain.Product;
import home.projects.groceryshop.persistance.CartRepository;
import home.projects.groceryshop.service.CartService;
import home.projects.groceryshop.service.CustomerService;
import home.projects.groceryshop.service.ProductService;
import home.projects.groceryshop.transfer.cart.AddProductsToCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceUnitTests {

    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductService productService;

    @Before
    public void setup() {
        cartService = new CartService(cartRepository, customerService, productService);
    }

    @Test
    public void addProductsToCart_whenNewCartThenNoErrorIsThrown() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.empty());

        Customer customer = new Customer();
        customer.setId(2);
        customer.setFirstName("FirstName");
        customer.setLastName("LastName");

        when(customerService.findCustomer(anyLong())).thenReturn(customer);

        Product product = new Product();
        product.setId(2);
        product.setName("TestProduct");
        product.setDescription("desc");
        product.setPrice(30);
        product.setQuantity(100);

        when(productService.findProduct(anyLong())).thenReturn(product);

        when(cartRepository.save(any(Cart.class))).thenReturn(null);

        AddProductsToCartRequest request = new AddProductsToCartRequest();
        request.setProductIds(Collections.singletonList(product.getId()));
        request.setCustomerId(customer.getId());

        cartService.addProductsToCart(request);

        verify(cartRepository).findById(anyLong());
        verify(customerService).findCustomer(anyLong());
        verify(productService).findProduct(anyLong());
        verify(cartRepository).save(any(Cart.class));
    }
}
