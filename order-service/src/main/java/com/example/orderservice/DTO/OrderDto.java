package com.example.orderservice.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderDto {

    @NotBlank(message = " name is required")
    private String name;

    @NotBlank(message = "orderName is required")
    private String orderName;

    @NotBlank(message = "address is required")
    private String address;
}
