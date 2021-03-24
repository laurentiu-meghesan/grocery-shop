package home.projects.groceryshop;

import home.projects.groceryshop.domain.Product;
import home.projects.groceryshop.service.ProductService;
import home.projects.groceryshop.transfer.SaveProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Test
    void createProduct_whenValidRequest_thenProductIsCreated() {
        SaveProductRequest request = new SaveProductRequest();
        request.setName("Coca-Cola");
        request.setQuantity(10);
        request.setPrice(1.2);

        Product product = productService.createProduct(request);

        assertThat(product, notNullValue());
        assertThat(product.getId(), greaterThan(0L));
        assertThat(product.getName(), is(request.getName()));
        assertThat(product.getQuantity(), is(request.getQuantity()));
        assertThat(product.getPrice(), is(request.getPrice()));
    }

    @Test
    void createProduct_whenMissingName_thanExceptionIsThrown() {
        SaveProductRequest request = new SaveProductRequest();
        request.setQuantity(10);
        request.setPrice(1.2);

        try {
            productService.createProduct(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
            assertThat("Unexpected exception type", e instanceof TransactionSystemException);
        }

    }
}
