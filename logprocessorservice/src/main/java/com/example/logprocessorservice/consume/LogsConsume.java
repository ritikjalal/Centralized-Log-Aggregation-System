package com.example.logprocessorservice.consume;

import com.example.logprocessorservice.event.KafkaLogsEvent;
import com.example.logprocessorservice.service.ElasticService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogsConsume {


    private final ElasticService elasticService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = { "order-service-topic", "user-service-topic" },
            groupId = "log-processor-group"
    )
    public void consume(Map<String,Object> event) {

        System.out.println("event recieved "+ event);

        KafkaLogsEvent kafkaLogsEvent=objectMapper.convertValue(event, KafkaLogsEvent.class);

        System.out.println("Kafka events consume "+kafkaLogsEvent);

        elasticService.saveLog(kafkaLogsEvent);

        System.out.println("Saved logs from " +kafkaLogsEvent.getServiceName()+" to elasticsearch");

    }
}
