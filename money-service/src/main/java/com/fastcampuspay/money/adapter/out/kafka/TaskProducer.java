package com.fastcampuspay.money.adapter.out.kafka;

import com.fastcampuspay.common.RechargingMoneyTask;
import com.fastcampuspay.money.application.port.out.SendRechargingMoneyTaskPort;
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
public class TaskProducer implements SendRechargingMoneyTaskPort {

    private final KafkaProducer<String, String> producer;
    private final String topic;

    public TaskProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}") String topic) {

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers); // kafka cluster 의 broker, kafka:29092
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<String, String>(props);
        this.topic = topic;
    }

    @Override
    public void sendRechargingMoneyTaskPort(RechargingMoneyTask task) {
        log.info("3. sendRechargingMoneyTaskPort. task id: " + task.getTaskId());
        this.sendMessage(task.getTaskId(), task);
    }

    public void sendMessage(String key, RechargingMoneyTask value) {
        final ObjectMapper mapper = new ObjectMapper();
        String jsonStringToProduce;
        // json string
        try {
            jsonStringToProduce = mapper.writeValueAsString(value);
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
