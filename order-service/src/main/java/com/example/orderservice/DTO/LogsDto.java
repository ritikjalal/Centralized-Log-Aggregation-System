package com.example.orderservice.DTO;

import com.example.orderservice.entity.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Component
public class LogsDto {
    private String eventid;
    private String event;
    private Order payload;
    private LocalDateTime localDateTime;
}
