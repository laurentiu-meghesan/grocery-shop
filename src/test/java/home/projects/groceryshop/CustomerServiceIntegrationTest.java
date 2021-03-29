package home.projects.groceryshop;

import home.projects.groceryshop.domain.Customer;
import home.projects.groceryshop.service.CustomerService;
import home.projects.groceryshop.transfer.customer.SaveCustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;

@SpringBootTest
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void createCustomer() {
        SaveCustomerRequest request = new SaveCustomerRequest();
        request.setFirstName("FirstName");
        request.setLastName("LastName");

        Customer customer = customerService.createCustomer(request);

        assertThat(customer, notNullValue());
        assertThat(customer.getId(), greaterThan(0L));
        assertThat(customer.getFirstName(), is(request.getFirstName()));
        assertThat(customer.getLastName(), is(request.getLastName()));
    }
}
