package com.fastcampuspay.taskconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Slf4j
public class TaskResultProducer {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public TaskResultProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                              @Value("${task.result.topic}") String topic) {
        // Producer 초기화
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers); // kafka cluster 의 broker, kafka:29092

        // key, value 인 데이터를 produce 할거야.
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    // kafka cluster [key, value] 형태로 produce
    public void sendTaskResult(String key, Object task) {
        log.info("5. [task result producer] Send message: " + task.toString());
        final ObjectMapper mapper = new ObjectMapper();
        String jsonStringToProduce;
        try {
            jsonStringToProduce = mapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, jsonStringToProduce);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                log.debug("Message sent successfully. offset: " + metadata.offset());
            } else {
                log.debug("Failed to send message: " + exception.getMessage());
            }
        });
    }
}
