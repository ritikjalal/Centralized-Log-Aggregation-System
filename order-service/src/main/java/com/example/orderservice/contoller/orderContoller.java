package com.example.orderservice.contoller;

import java.util.*;
import com.example.orderservice.DTO.LogsDto;
import com.example.orderservice.DTO.LogsDto1;
import com.example.orderservice.DTO.OrderDto;
import com.example.orderservice.config.LogToKafkaProdcuer;
import com.example.orderservice.entity.Order;
import com.example.orderservice.event.KafkaLogsEvent;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.util.Utility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class orderContoller {


    private final OrderService orderService;
    private final Utility utility;

    private final LogToKafkaProdcuer logToKafkaProdcuer;


    @Value("${kafka.topic.order-topic}")
    private String KAFKA_ORDER_TOPIC;


    @PostMapping("/create")
    public ResponseEntity<LogsDto> createTheOrder(@Valid @RequestBody OrderDto orderDto){
        System.out.println("Received "+orderDto);

        LogsDto logsDto=new LogsDto();

        //setting the logsDto
        String eventId= utility.createEventId();

        logsDto.setEventid(eventId);
        logsDto.setEvent("Order placed");

        //service call
        Order savedOrder=orderService.createOrder(orderDto);

        logsDto.setPayload(savedOrder);
        logsDto.setLocalDateTime(LocalDateTime.now());



        //kafka send
        KafkaLogsEvent kafkaLogsEvent = KafkaLogsEvent.builder()
                        .serviceName(KAFKA_ORDER_TOPIC)
                .eventid(logsDto.getEventid())
                .level("INFO")
                .event("Order placed")
                .payload(logsDto.getPayload())
                .action("Order Creation post api call")
                .localDateTime(LocalDateTime.now())
                .build();

        logToKafkaProdcuer.sendMessageToKafka(KAFKA_ORDER_TOPIC, kafkaLogsEvent);

        System.out.println("Order creation message sent to kafka");

        eventId=null;


        return ResponseEntity.ok(logsDto);
    }


    @PostMapping("/bulk/create")
    public ResponseEntity<List<LogsDto>> BulkOrder(@Valid @RequestBody List<OrderDto> orderDto){
        System.out.println("Received "+orderDto);

        //service call
        List<Order> savedOrder=orderService.createBulkOrder(orderDto);


        List<LogsDto> logsDtos=savedOrder
                .stream()
                .map(order->{
                    String eventId= UUID.randomUUID().toString();
                    LogsDto logsDto=new LogsDto();
                    logsDto.setEventid(eventId);
                    logsDto.setEvent("Order placed");
                    logsDto.setPayload(order);
                    logsDto.setLocalDateTime(LocalDateTime.now());
                    eventId=null;
                    return  logsDto;
                })
                .toList();

        System.out.println("List of logsDTos "+ logsDtos);

        for(LogsDto logsDto:logsDtos){

            KafkaLogsEvent kafkaLogsEvent = KafkaLogsEvent.builder()
                    .serviceName(KAFKA_ORDER_TOPIC)
                    .eventid(logsDto.getEventid())
                    .level("INFO")
                    .event("Order placed")
                    .payload(logsDto.getPayload())
                    .action("Order Creation post api call")
                    .localDateTime(logsDto.getLocalDateTime())
                    .build();
            logToKafkaProdcuer.sendMessageToKafka(KAFKA_ORDER_TOPIC, kafkaLogsEvent);
            System.out.println("Order creation message sent to kafka");
        }


        return ResponseEntity.ok(logsDtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<LogsDto> getOrdersById(@PathVariable("id") Long id){

        LogsDto logsDto=new LogsDto();

        String eventId= utility.createEventId();
        logsDto.setEventid(eventId);
        logsDto.setEvent("user Details");
        logsDto.setLocalDateTime(LocalDateTime.now());

        Order order=orderService.getByorderId(id);

        logsDto.setPayload(order);


        //kafka
        KafkaLogsEvent kafkaLogsEvent = KafkaLogsEvent.builder()
                .serviceName(KAFKA_ORDER_TOPIC)
                .eventid(logsDto.getEventid())
                .level("INFO")
                .event("Order placed")
                .payload(logsDto.getPayload())
                .action("fetch order by id get call")
                .localDateTime(LocalDateTime.now())
                .build();

        logToKafkaProdcuer.sendMessageToKafka(KAFKA_ORDER_TOPIC, kafkaLogsEvent);

        System.out.println("Get order by id message sent to kafka");

        return ResponseEntity.ok(logsDto);
    }


    @GetMapping("/")
    public ResponseEntity<LogsDto1> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        LogsDto1 logsDto1=new LogsDto1();
        String eventId= utility.createEventId();
        logsDto1.setEventid(eventId);
        logsDto1.setEvent("Records of users according");
        logsDto1.setLocalDateTime(LocalDateTime.now());
        List<Order> orderList=orderService.getOrderList(page,size);
        logsDto1.setPayload(orderList);


        return ResponseEntity.ok(logsDto1);
    }




}
