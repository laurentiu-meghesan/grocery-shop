package home.projects.groceryshop.web;

import home.projects.groceryshop.domain.Product;
import home.projects.groceryshop.service.ProductService;
import home.projects.groceryshop.transfer.product.GetProductsRequest;
import home.projects.groceryshop.transfer.product.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody SaveProductRequest request) {
        Product product = productService.createProduct(request);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        Product product = productService.getProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(GetProductsRequest request, Pageable pageable) {
        Page<Product> products = productService.getProducts(request, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @Valid @RequestBody SaveProductRequest request) {
        Product product = productService.updateProduct(id, request);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
