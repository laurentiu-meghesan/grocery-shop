package home.projects.groceryshop.steps;

import home.projects.groceryshop.domain.User;
import home.projects.groceryshop.service.CustomerService;
import home.projects.groceryshop.transfer.customer.CustomerResponse;
import home.projects.groceryshop.transfer.customer.SaveCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;

@Component
public class CustomerTestSteps {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserTestSteps userTestSteps;

    public CustomerResponse createCustomer() {
        User user = userTestSteps.createUser();

        SaveCustomerRequest request = new SaveCustomerRequest();
        request.setUserId(user.getId());
        request.setFirstName("FirstName");
        request.setLastName("LastName");
        request.setPhoneNumber("0753951753");
        request.setAddress("Downing Street, No. 75");
        request.setEmail("user@email.com");

        CustomerResponse customer = customerService.createCustomer(request);

        assertThat(customer, notNullValue());
        assertThat(customer.getId(), greaterThan(0L));
        assertThat(customer.getFirstName(), is(request.getFirstName()));
        assertThat(customer.getLastName(), is(request.getLastName()));
        assertThat(customer.getPhoneNumber(), is(request.getPhoneNumber()));
        assertThat(customer.getAddress(), is(request.getAddress()));
        assertThat(customer.getEmail(), is(request.getEmail()));

        return customer;
    }
}
