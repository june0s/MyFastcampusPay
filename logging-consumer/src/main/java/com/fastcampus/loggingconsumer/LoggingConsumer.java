package com.fastcampus.loggingconsumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class LoggingConsumer {

    private final KafkaConsumer<String, String> consumer;
    public LoggingConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                           @Value("${logging.topic}") String topic) {

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers); // kafka cluster 의 broker, kafka:29092
        // consumer group
        props.put("group.id", "my-group");
        // producer 와 합이 맞아야 한다.
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    for (ConsumerRecord<String, String> record : records) {
                        // consume 한 로그 출력하기.
                        System.out.println("Recv message: " + record.value());
                    }
                }
//            } catch (Exception e) {
//                System.out.println("LoggingConsumer failed. exception: " + e.getMessage());
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }
}
