package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    Customer customer1;
    Customer customer2;
    Customer customer3;

    ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        customer1 = new Customer("Mark", "Davis", "someNumber", "someAddress");
        customer2 = new Customer("Kevin", "Adams", "someNumber", "someAddress");
        customer3 = new Customer("John", "Doe", "someNumber", "someAddress");
    }


    @Test
    public void getAllCustomers() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String customer1Json = mapper.writeValueAsString(customer1);
        String customer2Json = mapper.writeValueAsString(customer2);
        List<Customer> customerList =  new ArrayList();
        customerList.add(customer1);
        customerList.add(customer2);
        String customerListJson = mapper.writeValueAsString(customerList);

//        ADDING CUSTOMERS FOR TEST
        mockMvc.perform(post("/customers")
                .content(customer1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/customers")
                .content(customer2Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

//        CHECKING THAT CUSTOMER LIST IS RETURNED
        mockMvc.perform(get("/customers"))
                .andExpect(content().string(customerListJson))
                .andExpect(status().isOk());
    }

    @Test
    public void addCustomer() throws Exception {
        Customer newCustomer = new Customer("Suzy", "Reynolds", "noNumber", "tacoDiner");

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(newCustomer);

//        ADDING CUSTOMER
        mockMvc.perform(post("/customers")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

//        VERIFYING NEW CUSTOMER ADDED
        mockMvc.perform(get("/customers/" + newCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonString));
    }


    @Test
    public void getCustomerById() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String customer1Json = mapper.writeValueAsString(customer1);
        String customer2Json = mapper.writeValueAsString(customer2);

//        ADDING CUSTOMERS FOR TEST
        mockMvc.perform(post("/customers")
                .content(customer1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/customers")
                .content(customer2Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

//        VERIFYING ABILITY TO GET CUSTOMER BY ID
        mockMvc.perform(get("/customers/" + customer2.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(customer2Json));
    }

    @Test
    public void updateCustomer() throws Exception {
        Customer updatedCustomer = new Customer("Kevin", "Adams", "someNumber", "newAddress");
        updatedCustomer.setId(customer2.getId());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(updatedCustomer);

//        UPDATING CUSTOMER
        mockMvc.perform(put("/customers/mockId")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonString));

//        VERIFYING CUSTOMER UPDATED
        mockMvc.perform(get("/customers/" + customer2.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonString));
    }

    @Test
    public void removeCustomer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String customer1Json = mapper.writeValueAsString(customer1);
        String customer2Json = mapper.writeValueAsString(customer2);

//        ADDING CUSTOMERS FOR TEST
        mockMvc.perform(post("/customers")
                .content(customer1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/customers")
                .content(customer2Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

//        DELETING CUSTOMER
        mockMvc.perform(delete("/customers/" + customer2.getId()))
                .andExpect(status().isNoContent());

//        VERIFYING CUSTOMER WAS DELETED
        MvcResult result = mockMvc.perform(get("/customers"))
                .andExpect(status().isOk()).andReturn();

        List<Customer> resultList = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<ArrayList<Customer>>() {});

        assertEquals(1, resultList.size());
    }
}