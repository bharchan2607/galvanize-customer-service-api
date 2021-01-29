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
}
