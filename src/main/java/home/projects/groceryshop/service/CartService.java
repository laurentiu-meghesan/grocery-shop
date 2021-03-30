package home.projects.groceryshop.service;

import home.projects.groceryshop.domain.Cart;
import home.projects.groceryshop.domain.Customer;
import home.projects.groceryshop.domain.Product;
import home.projects.groceryshop.persistance.CartRepository;
import home.projects.groceryshop.transfer.cart.AddProductsToCartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    public void addProductsToCart(AddProductsToCartRequest request) {
        LOGGER.info("Adding products to cart: {}", request);

        Cart cart = cartRepository.findById(request.getCustomerId()).orElse(new Cart());

        if (cart.getCustomer() == null) {
            Customer customer = customerService.getCustomer(request.getCustomerId());

            cart.setCustomer(customer);
        }

        for (Long id : request.getProductIds()) {
            Product product = productService.getProduct(id);
            cart.addProductToCart(product);
        }
        cartRepository.save(cart);
    }
}
