package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    CustomerRepository repository;

    @InjectMocks
    CustomerService service;

    Customer customer1;
    Customer customer2;
    Customer customer3;
    List<Customer> customers;

    @BeforeEach
    public void setUp() {
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
        when(repository.findAll()).thenReturn(customers);
        List<Customer> result = service.getAllCustomers();
        assertEquals(customers, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void addCustomer_addsCustomerToList() {
        Customer newCustomer = new Customer("Suzy", "Reynolds", "noNumber", "tacoDiner");
        when(repository.save(newCustomer)).thenReturn(null);
        service.addCustomer(newCustomer);
        verify(repository, times(1)).save(newCustomer);
    }

    @Test
    public void getCustomerById_returnsCustomerFromList() {
        when(repository.findById("mockId")).thenReturn(java.util.Optional.ofNullable(customer2));
        Customer result = service.getCustomerById("mockId");
        verify(repository, times(1)).findById("mockId");
    }

    @Test
    public void updateCustomer_returnsUpdatedCustomerFromList() {
        Customer updatedCustomer = new Customer("Kevin", "Adams", "someNumber", "newAddress");
        when(repository.save(updatedCustomer)).thenReturn(updatedCustomer);
        Customer result = service.updateCustomer(updatedCustomer);
        assertEquals(updatedCustomer, result);
        verify(repository, times(1)).save(updatedCustomer);
    }

    @Test
    public void removeCustomer_removesCustomerFromList() {
        service.removeCustomer("mockId");
        verify(repository, times(1)).deleteById("mockId");
    }
}