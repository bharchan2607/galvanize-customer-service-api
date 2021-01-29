package com.example.demo;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
public class Customer {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = UUID.randomUUID().toString();
    }

    public Customer(String firstName, String lastName, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.id = UUID.randomUUID().toString();
    }

}

