package com.example.orderservice.event;


import com.example.orderservice.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class KafkaLogsEvent {
    private String eventid;
    private String serviceName;
    private String level;
    private String event;
    private String action;
    private Order payload;
    private LocalDateTime localDateTime;
}
