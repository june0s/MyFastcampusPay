package com.fastcampuspay.common;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class LoggingProducer {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public LoggingProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                           @Value("${logging.topic}") String topic) {
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
    public void sendMessage(String key, String message) {
        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("Message sent successfully. offset: " + metadata.offset());
            } else {
                System.out.println("Failed to send message: " + exception.getMessage());
            }
        });
    }
}
