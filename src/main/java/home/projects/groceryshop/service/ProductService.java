package home.projects.groceryshop.service;

import home.projects.groceryshop.domain.Product;
import home.projects.groceryshop.exception.ResourceNotFoundException;
import home.projects.groceryshop.persistance.ProductRepository;
import home.projects.groceryshop.transfer.product.GetProductsRequest;
import home.projects.groceryshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request) {
        LOGGER.info("Creating product {}", request);

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

        return productRepository.save(product);
    }

    public Product getProduct(long id) {
        LOGGER.info("Retrieving product {}", id);

//      //optional usage
//        Optional<Product> productOptional = productRepository.findById(id);
//
//        if (productOptional.isPresent()) {
//            return productOptional.get();
//        } else {
//            throw new ResourceNotFoundException("Product " + id + " not found.");
//        }

        return productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product " + id + "not found."));
    }

    public Page<Product> getProducts(GetProductsRequest request, Pageable pageable) {
        LOGGER.info("Retrieving all products.");

        if (request != null) {
            if (request.getPartialName() != null && request.getMinQuantity() != null) {
                return productRepository.findByNameContainingAndQuantityGreaterThanEqual
                        (request.getPartialName(), request.getMinQuantity(), pageable);
            } else if (request.getPartialName() != null) {
                return productRepository.findByNameContaining(request.getPartialName(), pageable);
            }
        }
        return productRepository.findAll(pageable);
    }

    public Product updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product {} {}", id, request);
        Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);
        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product with id {}", id);
        productRepository.deleteById(id);
    }

}
