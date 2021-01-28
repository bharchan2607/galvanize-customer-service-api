package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    List<Customer> customers = new ArrayList<>();

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public void addCustomer(Customer newCustomer) {
        customers.add(newCustomer);
    }

    public Customer getCustomerById(String customerId) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst()
                .get();
    }

    public Customer updateCustomer(Customer updatedCustomer) {
       boolean isCustomerRemoved =  false;
        for(Customer customer : customers) {
            if(customer.getId().equals(updatedCustomer.getId())) {
                customers.remove(customer);
                isCustomerRemoved = true;
            }
        }
        if(isCustomerRemoved) {
            customers.add(updatedCustomer);
        }
        return updatedCustomer;
    }


    public void removeCustomer(String customerId) {
        for(Customer customer: customers) {
            if(customer.getId().equals(customerId)) {
                customers.remove(customer);
            }
        }

    }
}
