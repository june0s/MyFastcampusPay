package com.fastcampuspay.common;

import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Configuration
public class CountDownLatchManager {
    private final Map<String, CountDownLatch> countDownLatchMap;
    private final Map<String, String> stringMap;

    public CountDownLatchManager() {
        this.countDownLatchMap = new HashMap<>();
        this.stringMap = new HashMap<>();

        // 멀티 스레딩을 위한 코드.
        // sample code
//        final CountDownLatch latch = new CountDownLatch(1);
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        // <- 다른 곳에서 latch.countDown(); 호출되기 전까지 wait 한다!
    }

    public void addCountDownLatch(String key) {
        this.countDownLatchMap.put(key, new CountDownLatch(1));
    }
    public void setDataForKey(String key, String data) {
        this.stringMap.put(key, data);
    }
    public String getDataForKey(String key) {
        return this.stringMap.get(key);
    }
    public CountDownLatch getCountDownLatch(String key) {
        return this.countDownLatchMap.get(key);
    }
}
