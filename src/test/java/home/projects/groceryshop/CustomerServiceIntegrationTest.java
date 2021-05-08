package home.projects.groceryshop;

import home.projects.groceryshop.domain.User;
import home.projects.groceryshop.service.CustomerService;
import home.projects.groceryshop.steps.CustomerTestSteps;
import home.projects.groceryshop.steps.UserTestSteps;
import home.projects.groceryshop.transfer.customer.CustomerResponse;
import home.projects.groceryshop.transfer.customer.SaveCustomerRequest;
import home.projects.groceryshop.transfer.customer.UpdateCustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerTestSteps customerTestSteps;

    @Test
    void createCustomer_whenValidRequest_thenCustomerIsCreated() {
        customerTestSteps.createCustomer();
    }

    @Test
    void updateCustomer_whenValidRequest_thenCustomerIsUpdated() {

        CustomerResponse customer = customerTestSteps.createCustomer();

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setFirstName("Lulu");
        request.setLastName("Nebunu");
        request.setEmail("lulu@etc.com");
        request.setPhoneNumber("new nr 0777...");
        request.setAddress("strada strazilor");

        CustomerResponse updatedCustomer = customerService.updateCustomer(customer.getId(), request);

        assertThat(updatedCustomer, notNullValue());
        assertThat(updatedCustomer.getId(), is(customer.getId()));
        assertThat(updatedCustomer.getFirstName(), is(request.getFirstName()));
        assertThat(updatedCustomer.getLastName(), is(request.getLastName()));
        assertThat(updatedCustomer.getEmail(), is(request.getEmail()));
        assertThat(updatedCustomer.getPhoneNumber(), is(request.getPhoneNumber()));
        assertThat(updatedCustomer.getAddress(), is(request.getAddress()));
    }
}
