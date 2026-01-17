package com.example.logprocessorservice.service;

import com.example.logprocessorservice.event.KafkaLogsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElasticService {

    private final ElasticsearchOperations elasticsearchOperations;

    public void saveLog(KafkaLogsEvent event) {
        elasticsearchOperations.save(event);
    }
}
