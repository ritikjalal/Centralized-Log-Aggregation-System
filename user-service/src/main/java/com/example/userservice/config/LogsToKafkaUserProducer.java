package com.example.userservice.config;

import com.example.userservice.event.KafkaLogsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogsToKafkaUserProducer {

    @Autowired
    private KafkaTemplate<String, KafkaLogsEvent> kafkaTemplate;

    public LogsToKafkaUserProducer(
            KafkaTemplate<String, KafkaLogsEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageToKafka(String topic, KafkaLogsEvent kafkaLogsEvent){
        kafkaTemplate.send(topic, kafkaLogsEvent);
    }

}
