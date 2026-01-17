package com.example.userservice.DTO;

import com.example.userservice.entity.User;
import lombok.Data;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Component
public class LogsDto1 {
    private String eventid;
    private String event;
    private List<User> payload;
    private LocalDateTime localDateTime;
}
