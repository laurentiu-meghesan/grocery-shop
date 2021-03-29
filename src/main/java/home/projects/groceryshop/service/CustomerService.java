package home.projects.groceryshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.projects.groceryshop.domain.Customer;
import home.projects.groceryshop.exception.ResourceNotFoundException;
import home.projects.groceryshop.persistance.CustomerRepository;
import home.projects.groceryshop.transfer.customer.SaveCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    public Customer createCustomer(SaveCustomerRequest request) {
        LOGGER.info("Creating Customer {}", request);

        Customer customer = objectMapper.convertValue(request, Customer.class);

        return customerRepository.save(customer);
    }

    public Customer getCustomer(long id) {
        LOGGER.info("Retrieving customer {}", id);

        return customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer " + id + " not found."));
    }

    public Customer updateCustomer(long id, SaveCustomerRequest request) {
        LOGGER.info("Updating customer {}: {}", id, request);

        Customer customer = getCustomer(id);

        BeanUtils.copyProperties(request, customer);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(long id) {
        LOGGER.info("Deleting customer {}.", id);
        customerRepository.deleteById(id);
    }
}