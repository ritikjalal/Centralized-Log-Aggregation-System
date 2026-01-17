package com.example.userservice.controller;

import java.util.*;
import com.example.userservice.DTO.LogsDto;
import com.example.userservice.DTO.LogsDto1;
import com.example.userservice.DTO.UserDto;
import com.example.userservice.config.LogsToKafkaUserProducer;
import com.example.userservice.entity.User;
import com.example.userservice.event.KafkaLogsEvent;
import com.example.userservice.service.UserService;
import com.example.userservice.util.Utility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {


    private final UserService userService;
    private final Utility utility;

    private final LogsToKafkaUserProducer logsToKafkaUserProducer;



    @Value("${kafka.topic.user-topic}")
    private String KAFKA_USER_TOPIC;


    @PostMapping("/create")
    public ResponseEntity<LogsDto> createTheUser(@Valid @RequestBody UserDto userDto){
        System.out.println("Received "+userDto);

        LogsDto logsDto=new LogsDto();

        //setting the logsDto
        String eventId= utility.createEventId();
        logsDto.setEventid(eventId);
        logsDto.setEvent("User created");

        //service call
        User savedUser=userService.createUser(userDto);

        logsDto.setPayload(savedUser);
        logsDto.setLocalDateTime(LocalDateTime.now());


        //kafka
        KafkaLogsEvent kafkaLogsEvent = KafkaLogsEvent.builder()
                .serviceName(KAFKA_USER_TOPIC)
                .eventid(logsDto.getEventid())
                .level("INFO")
                .event("user created")
                .payload(logsDto.getPayload())
                .action("User Creation api post call")
                .localDateTime(logsDto.getLocalDateTime())
                .build();

        logsToKafkaUserProducer.sendMessageToKafka(KAFKA_USER_TOPIC, kafkaLogsEvent);
        System.out.println("User Created message send to kafka user topic by " + eventId);

        eventId=null;


        return ResponseEntity.ok(logsDto);
    }


    @PostMapping("/bulk/create")
    public ResponseEntity<List<LogsDto>> BulkUser(@Valid @RequestBody List<UserDto> userDto){
        System.out.println("Received "+userDto);

        //service call
        List<User> savedUser=userService.createBulkUser(userDto);



        List<LogsDto> logsDtos=savedUser
                .stream()
                .map(user->{
                    LogsDto logsDto=new LogsDto();
                    String eventId= utility.createEventId();
                    logsDto.setEventid(eventId);
                    logsDto.setEvent("User created");
                    logsDto.setPayload(user);
                    logsDto.setLocalDateTime(LocalDateTime.now());
                    eventId=null;
                    return  logsDto;
                })
                .toList();

        for(LogsDto logsDto:logsDtos){

            KafkaLogsEvent kafkaLogsEvent = KafkaLogsEvent.builder()
                    .serviceName(KAFKA_USER_TOPIC)
                    .eventid(logsDto.getEventid())
                    .level("INFO")
                    .event("user created")
                    .payload(logsDto.getPayload())
                    .action("User Creation api post call")
                    .localDateTime(logsDto.getLocalDateTime())
                    .build();

            logsToKafkaUserProducer.sendMessageToKafka(KAFKA_USER_TOPIC, kafkaLogsEvent);

            System.out.println("User Created message send to kafka user topic");
        }


        return ResponseEntity.ok(logsDtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<LogsDto> getUserById(@PathVariable("id") Long id){

        LogsDto logsDto=new LogsDto();

        String eventId= utility.createEventId();
        logsDto.setEventid(eventId);
        logsDto.setEvent("User Details");
        logsDto.setLocalDateTime(LocalDateTime.now());

        User user=userService.getByUserId(id);

        logsDto.setPayload(user);


        //kafka
        KafkaLogsEvent kafkaLogsEvent = KafkaLogsEvent.builder()
                .serviceName(KAFKA_USER_TOPIC)
                .eventid(logsDto.getEventid())
                .level("INFO")
                .event("User Detail by Id")
                .payload(logsDto.getPayload())
                .action("fetch user by id  get call")
                .localDateTime(logsDto.getLocalDateTime())
                .build();

        logsToKafkaUserProducer.sendMessageToKafka(KAFKA_USER_TOPIC, kafkaLogsEvent);
        System.out.println("Get user by id send to kafka user topic");



        return ResponseEntity.ok(logsDto);
    }


    @GetMapping("/")
    public ResponseEntity<LogsDto1> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        LogsDto1 logsDto1=new LogsDto1();
        String eventId= utility.createEventId();
        logsDto1.setEventid(eventId);
        logsDto1.setEvent("Records of users according");
        logsDto1.setLocalDateTime(LocalDateTime.now());
        List<User> userList=userService.getUserList(page,size);
        logsDto1.setPayload(userList);

        return ResponseEntity.ok(logsDto1);
    }




}
