package com.example.logprocessorservice.event;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "kafka-logs-from-user-and-order-service")
public class KafkaLogsEvent {
    @Id
    private String eventid;
    private String serviceName;
    private String level;
    private String event;
    private String action;
    @Field(type = FieldType.Object)
    private Object payload;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime localDateTime;
}
