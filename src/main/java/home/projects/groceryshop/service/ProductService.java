package home.projects.groceryshop.service;

import home.projects.groceryshop.domain.Product;
import home.projects.groceryshop.exception.ResourceNotFoundException;
import home.projects.groceryshop.persistance.ProductRepository;
import home.projects.groceryshop.transfer.product.GetProductsRequest;
import home.projects.groceryshop.transfer.product.ProductResponse;
import home.projects.groceryshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(SaveProductRequest request) {
        LOGGER.info("Creating product {}", request);

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

        Product savedProduct = productRepository.save(product);
        return mapProductResponse(savedProduct);
    }

    public ProductResponse getProduct(long id) {
        LOGGER.info("Retrieving product {}", id);

//        Optional<Product> productOptional = productRepository.findById(id);
//
//        if (productOptional.isPresent()) {
//            return productOptional.get();
//        } else {
//            throw new ResourceNotFoundException("Product " + id + " not found.");
//        }

        Product product = findProduct(id);

        return mapProductResponse(product);
    }

    public Product findProduct(long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product " + id + " not found."));
    }

    private ProductResponse mapProductResponse(Product product) {
        ProductResponse productDto = new ProductResponse();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }

    public Page<ProductResponse> getProducts(GetProductsRequest request, Pageable pageable) {
        LOGGER.info("Retrieving all products.");

        Page<Product> productsPage;

        if (request != null) {
            if (request.getPartialName() != null && request.getMinQuantity() != null) {
                productsPage = productRepository.findByNameContainingAndQuantityGreaterThanEqual
                        (request.getPartialName(), request.getMinQuantity(), pageable);
            } else if (request.getPartialName() != null) {
                productsPage = productRepository.findByNameContaining(request.getPartialName(), pageable);
            } else {
                productsPage = productRepository.findAll(pageable);
            }
        } else {
            productsPage = productRepository.findAll(pageable);
        }

        List<ProductResponse> productDtos = new ArrayList<>();

        for (Product product : productsPage.getContent()) {
            ProductResponse productDto = mapProductResponse(product);

            productDtos.add(productDto);
        }

        return new PageImpl<>(productDtos, pageable, productsPage.getTotalElements());
    }

    public ProductResponse updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product {} {}", id, request);
        Product product = findProduct(id);

        BeanUtils.copyProperties(request, product);
        Product savedProduct = productRepository.save(product);
        return mapProductResponse(savedProduct);
    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product with id {}", id);
        productRepository.deleteById(id);
    }
}
