package home.projects.groceryshop;

import home.projects.groceryshop.service.CustomerService;
import home.projects.groceryshop.steps.CustomerTestSteps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerTestSteps customerTestSteps;

    @Test
    void createCustomer_whenValidRequest_thenCustomerIsCreated() {
        customerTestSteps.createCustomer();
    }
}
