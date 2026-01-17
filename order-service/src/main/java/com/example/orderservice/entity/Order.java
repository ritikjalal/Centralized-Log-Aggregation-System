package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "order_seq")
    @SequenceGenerator(
            name = "order_seq",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    private Long id;

    private String orderNumber;

    private String name;

    private String address;

    private String orderName;

    private Long totalAmount;

    private String orderStatus;


    @PrePersist
    public void generatedValues(){
        this.orderNumber="Order-"+ this.id;
        this.orderStatus="Created";
        this.totalAmount=100L;
    }


}
