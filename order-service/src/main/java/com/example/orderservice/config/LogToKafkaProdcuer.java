package com.example.orderservice.config;

import com.example.orderservice.event.KafkaLogsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogToKafkaProdcuer {



    @Autowired
    private KafkaTemplate<String, KafkaLogsEvent> kafkaTemplate;

    public LogToKafkaProdcuer(
            KafkaTemplate<String, KafkaLogsEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageToKafka(String topic, KafkaLogsEvent kafkaLogsEvent){
        kafkaTemplate.send(topic, kafkaLogsEvent);
    }


}
