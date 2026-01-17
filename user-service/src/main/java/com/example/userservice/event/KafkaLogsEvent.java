package com.example.userservice.event;

import com.example.userservice.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class KafkaLogsEvent {
    private String eventid;
    private String serviceName;
    private String level;
    private String action;
    private String event;
    private User payload;
    private LocalDateTime localDateTime;
}
