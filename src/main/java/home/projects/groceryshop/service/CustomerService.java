package home.projects.groceryshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.projects.groceryshop.domain.Customer;
import home.projects.groceryshop.domain.User;
import home.projects.groceryshop.exception.ResourceNotFoundException;
import home.projects.groceryshop.persistance.CustomerRepository;
import home.projects.groceryshop.transfer.customer.CustomerResponse;
import home.projects.groceryshop.transfer.customer.SaveCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ObjectMapper objectMapper, UserService userService) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Transactional
    public CustomerResponse createCustomer(SaveCustomerRequest request) {
        LOGGER.info("Creating Customer {}", request);

        User user = userService.findUser(request.getUserId());
        Customer customer = objectMapper.convertValue(request, Customer.class);
        customer.setUser(user);

        Customer savedCustomer = customerRepository.save(customer);
        return mapCustomerResponse(savedCustomer);
    }

    private CustomerResponse mapCustomerResponse(Customer customer) {
        CustomerResponse customerDto = new CustomerResponse();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setEmail(customer.getEmail());
        customerDto.setAddress(customer.getAddress());

        return customerDto;
    }

    public CustomerResponse getUser(long id) {
        LOGGER.info("Retrieving customer {}", id);

        Customer customer = findCustomer(id);

        return mapCustomerResponse(customer);
    }

    public Customer findCustomer(long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer " + id + " not found."));
    }

    public CustomerResponse updateCustomer(long id, SaveCustomerRequest request) {
        LOGGER.info("Updating customer {}: {}", id, request);

        Customer customer = findCustomer(id);

        BeanUtils.copyProperties(request, customer);
        Customer savedCustomer = customerRepository.save(customer);

        return mapCustomerResponse(savedCustomer);
    }

    public void deleteCustomer(long id) {
        LOGGER.info("Deleting customer {}.", id);
        customerRepository.deleteById(id);
    }
}