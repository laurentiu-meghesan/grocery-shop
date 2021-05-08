package home.projects.groceryshop.web;

import home.projects.groceryshop.domain.Customer;
import home.projects.groceryshop.service.CustomerService;
import home.projects.groceryshop.transfer.customer.CustomerResponse;
import home.projects.groceryshop.transfer.customer.SaveCustomerRequest;
import home.projects.groceryshop.transfer.customer.UpdateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/customers")
@RestController
@CrossOrigin
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody SaveCustomerRequest request) {
        CustomerResponse customer = customerService.createCustomer(request);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable long id) {
        CustomerResponse customer = customerService.getUser(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable long id, @Valid @RequestBody UpdateCustomerRequest request) {
        CustomerResponse customer = customerService.updateCustomer(id, request);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
