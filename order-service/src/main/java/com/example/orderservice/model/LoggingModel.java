package com.example.orderservice.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
@Getter
@Setter
@AllArgsConstructor
public class LoggingModel {

    private String loggingLevel;
    private String message;
    private String eventId;
    private LocalDate timestamp;

}
