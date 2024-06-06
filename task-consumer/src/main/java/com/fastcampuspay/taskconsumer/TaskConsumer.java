package com.fastcampuspay.taskconsumer;

import com.fastcampuspay.common.RechargingMoneyTask;
import com.fastcampuspay.common.SubTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
@Slf4j
public class TaskConsumer {
    private final KafkaConsumer<String, String> consumer;
    private final TaskResultProducer taskResultProducer;

    public TaskConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}") String topic, TaskResultProducer taskResultProducer) {

        this.taskResultProducer = taskResultProducer;
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
                    final ObjectMapper mapper = new ObjectMapper();
                    for (final ConsumerRecord<String, String> record : records) {
                        log.info("4. [Task consumer] Recv message: " + record.value());

                        // task run
                        try {
                            // record 는 RechargingMoneyTask (jsonString) 형태 일 것이다.
                            RechargingMoneyTask task = mapper.readValue(record.value(), RechargingMoneyTask.class);
                            // task result
                            for (SubTask subTask: task.getSubTaskList()) {
                                // 정석대로 하려면,
                                // sub task 가 membership, banking 인지 구분해서
                                // external port 와 adapter 를 통해서 subTask 수행하고, 결과(상태) 받아서 처리해야 함.

                                // all sub task is ok 라고 가정.
                                subTask.setStatus("success"); // hard cording
                            }

                            // task 수행 결과를 result topic 으로 보낸다.
                            taskResultProducer.sendTaskResult(task.getTaskId(), task);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }
}
