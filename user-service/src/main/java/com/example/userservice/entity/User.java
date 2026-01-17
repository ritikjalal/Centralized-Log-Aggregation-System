package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String userStatus;


    @PrePersist
    public void generatedValues(){
        this.userStatus="Created";
    }


}
