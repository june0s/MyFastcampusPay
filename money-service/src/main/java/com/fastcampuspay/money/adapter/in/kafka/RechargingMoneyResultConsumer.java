package com.fastcampuspay.money.adapter.in.kafka;

import com.fastcampuspay.common.CountDownLatchManager;
import com.fastcampuspay.common.LoggingProducer;
import com.fastcampuspay.common.RechargingMoneyTask;
import com.fastcampuspay.common.SubTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
@Slf4j
public class RechargingMoneyResultConsumer {
    private final KafkaConsumer<String, String> consumer;
    private final LoggingProducer loggingProducer;
    @NotNull
    private final CountDownLatchManager countDownLatchManager;

    public RechargingMoneyResultConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                                         @Value("${task.result.topic}") String topic, LoggingProducer loggingProducer, CountDownLatchManager countDownLatchManager) {

        this.loggingProducer = loggingProducer;
        this.countDownLatchManager = countDownLatchManager;
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
//                    if (records.count() > 0) {
//                        log.debug("# of records: " + records.count());
//                    }
                    for (final ConsumerRecord<String, String> record : records) {
                        log.info("6. [Task result consumer] Recv message: " + record.key() + " / " + record.value());
                        // record 는 RechargingMoneyTask, all subtask 는 ok 라고 온다.

                        // task run
                        RechargingMoneyTask task;
                        try {
                            task = mapper.readValue(record.value(), RechargingMoneyTask.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        // task result
                        boolean taskResult = true;
                        for (SubTask subTask: task.getSubTaskList()) {
                            if (subTask.getStatus().equals("fail")) {
                                taskResult = false;
                                break;
                            }
                        }
                        // 모두 성공했다면,
                        if (taskResult) {
                            loggingProducer.sendMessage(task.getTaskId(), "task success");
                            countDownLatchManager.setDataForKey(task.getTaskId(), "success");
                        } else {
                            loggingProducer.sendMessage(task.getTaskId(), "task failed");
                            countDownLatchManager.setDataForKey(task.getTaskId(), "failed");
                        }

//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }

                        log.debug("+ count down: " + task.getTaskId());
                        countDownLatchManager.getCountDownLatch(task.getTaskId()).countDown();
                    }
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }
}
