package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    CustomerService service;

    Customer customer1;
    Customer customer2;
    Customer customer3;
    List<Customer> customers;

    @BeforeEach
    public void setUp() {
        service = new CustomerService();
        customer1 = new Customer("Mark", "Davis", "someNumber", "someAddress");
        customer2 = new Customer("Kevin", "Adams", "someNumber", "someAddress");
        customer3 = new Customer("John", "Doe", "someNumber", "someAddress");
        customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
    }


    @Test
    public void getAllCustomers_returnsListOfCustomers() {
        service.customers = customers;
        List<Customer> result = service.getAllCustomers();
        assertEquals(customers, result);
    }

    @Test
    public void addCustomer_addsCustomerToList() {
        Customer newCustomer = new Customer("Suzy", "Reynolds", "noNumber", "tacoDiner");
        service.addCustomer(newCustomer);
        assertEquals(newCustomer, service.customers.get(0));
    }

    @Test
    public void getCustomerById_returnsCustomerFromList() {
        service.customers = customers;
        String customerId = customers.get(1).getId();
        Customer result = service.getCustomerById(customerId);
        assertEquals(customers.get(1), result);
    }

    @Test
    public void updateCustomer_returnsUpdatedCustomerFromList() {
        service.customers = customers;
        String customerId = customers.get(1).getId();
        Customer updatedCustomer = new Customer("Kevin", "Adams", "someNumber", "newAddress");
        updatedCustomer.setId(customerId);
        Customer result = service.updateCustomer(updatedCustomer);
        assertEquals(updatedCustomer, result);
        assertEquals(updatedCustomer, service.customers.get(2));
    }

    @Test
    public void removeCustomer_removesCustomerFromList() {
        service.customers = customers;
        String customerId = customers.get(1).getId();
        service.removeCustomer(customerId);
        assertEquals(2, service.customers.size());
    }
}