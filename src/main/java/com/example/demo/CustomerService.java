package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public void addCustomer(Customer newCustomer) {
        repository.save(newCustomer);
    }

    public Customer getCustomerById(String customerId) {
        return repository.findById(customerId).get();
    }

    public Customer updateCustomer(Customer updatedCustomer) {
       return repository.save(updatedCustomer);
    }

    public void removeCustomer(String customerId) {
        repository.deleteById(customerId);
    }

    public CustomerResponse getAllCustomersResponse() {
        return CustomerResponse.builder()
                .statusCode(200)
                .status("OK")
                .data(getAllCustomers())
                .build();
    }

    public CustomerResponse getCustomerByIdResponse(String customerId) {
        return CustomerResponse.builder()
                .statusCode(200)
                .status("OK")
                .data(getCustomerById(customerId))
                .build();
    }

    public CustomerResponse updateCustomerResponse(Customer updatedCustomer) {
        return CustomerResponse.builder()
                .statusCode(200)
                .status("OK")
                .data(updateCustomer(updatedCustomer))
                .build();
    }

    public CustomerResponse addCustomerResponse(Customer newCustomer) {
        addCustomer(newCustomer);
        return CustomerResponse.builder()
                .statusCode(201)
                .status("Created")
                .data(null)
                .build();
    }

    public CustomerResponse removeCustomerResponse(String customerId) {
        removeCustomer(customerId);
        return CustomerResponse.builder()
                .statusCode(204)
                .status("No Content")
                .data(null)
                .build();
    }
}
