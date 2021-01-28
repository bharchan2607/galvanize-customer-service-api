package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerService customerService;

    Customer customer1;
    Customer customer2;
    Customer customer3;
    List<Customer> customers;

    ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        customerService.setCustomers(new ArrayList<>());
        customer1 = new Customer("Mark", "Davis", "someNumber", "someAddress");
        customer2 = new Customer("Kevin", "Adams", "someNumber", "someAddress");
        customer3 = new Customer("John", "Doe", "someNumber", "someAddress");
        customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
    }


    @Test
    public void getAllCustomers() throws Exception {
        customerService.setCustomers(customers);
        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void addCustomer() throws Exception {
        Customer newCustomer = new Customer("Suzy", "Reynolds", "noNumber", "tacoDiner");

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(newCustomer);

        mockMvc.perform(post("/customers")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        assertEquals(newCustomer, customerService.customers.get(0));
    }


    @Test
    public void getCustomerById() throws Exception {
        customer2.setId("mockId");
        customerService.setCustomers(customers);
        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(customer2);

        mockMvc.perform(get("/customers/mockId"))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void updateCustomer() throws Exception {
        customer2.setId("mockId");
        customerService.setCustomers(customers);
        Customer updatedCustomer = new Customer("Kevin", "Adams", "someNumber", "newAddress");
        updatedCustomer.setId("mockId");

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(updatedCustomer);

        mockMvc.perform(put("/customers/mockId")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonString));

        assertEquals(updatedCustomer, customerService.customers.get(2));
    }

    @Test
    public void removeCustomer() throws Exception {
        customer2.setId("mockId");
        customerService.setCustomers(customers);

        mockMvc.perform(delete("/customers/mockId"))
                .andExpect(status().isNoContent());

        assertEquals(2, customerService.getAllCustomers().size());
    }
}