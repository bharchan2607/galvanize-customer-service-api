package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/challenge/customers")
    public CustomerResponse getAllCustomersResponse() {
        return customerService.getAllCustomersResponse();
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestBody Customer newCustomer) {
        customerService.addCustomer(newCustomer);
    }

    @PostMapping("/challenge/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse addCustomerResponse(@RequestBody Customer newCustomer) {
        return customerService.addCustomerResponse(newCustomer);
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomerById(@PathVariable String customerId) {
        return customerService.getCustomerById(customerId);
    }

    @GetMapping("/challenge/customers/{customerId}")
    public CustomerResponse getCustomerByIdResponse(@PathVariable String customerId) {
        return customerService.getCustomerByIdResponse(customerId);
    }

    @PutMapping("/customers/{customerId}")
    public Customer updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @PutMapping("/challenge/customers/{customerId}")
    public CustomerResponse updateCustomerResponse(@PathVariable String customerId, @RequestBody Customer customer) {
        return customerService.updateCustomerResponse(customer);
    }

    @DeleteMapping("/customers/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCustomer(@PathVariable String customerId) {
        customerService.removeCustomer(customerId);
    }

    @DeleteMapping("/challenge/customers/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomerResponse removeCustomerResponse(@PathVariable String customerId) {
        return customerService.removeCustomerResponse(customerId);
    }
}
