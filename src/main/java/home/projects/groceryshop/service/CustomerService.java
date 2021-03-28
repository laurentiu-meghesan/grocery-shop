package home.projects.groceryshop.service;

import home.projects.groceryshop.persistance.CustomerRepository;
import home.projects.groceryshop.transfer.customer.SaveCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public SaveCustomerRequest createCustomer() {

    }
}
