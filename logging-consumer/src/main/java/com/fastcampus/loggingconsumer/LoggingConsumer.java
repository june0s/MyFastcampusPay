package com.fastcampus.loggingconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class LoggingConsumer {
    private final KafkaConsumer<String, String> consumer;

    public LoggingConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                           @Value("${logging.topic}") String topic) {

        final Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers); // kafka cluster 의 broker, kafka:29092
        props.put("group.id", "my-group"); // consumer group
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singletonList(topic));

        final Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    final ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    for (final ConsumerRecord<String, String> record : records) {
                        System.out.println("Recv message: " + record.value());
                    }
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }
}
