package home.projects.groceryshop.web;

import home.projects.groceryshop.service.CartService;
import home.projects.groceryshop.transfer.cart.AddProductsToCartRequest;
import home.projects.groceryshop.transfer.cart.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping
    public ResponseEntity<Void> addProductsToCart(@Valid @RequestBody AddProductsToCartRequest request) {
        cartService.addProductsToCart(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable(name = "id") long customerId) {
        CartResponse cart = cartService.getCart(customerId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
