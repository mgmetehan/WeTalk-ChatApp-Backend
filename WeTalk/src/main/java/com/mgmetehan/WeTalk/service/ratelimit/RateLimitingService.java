package com.mgmetehan.WeTalk.service.ratelimit;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RateLimitingService {

    private final Map<String, Bucket> rateLimitingMap = new ConcurrentHashMap<>();

    @Scheduled(cron = "0 0/15 * * * *")
    public void clearRateLimitingMap() {
        rateLimitingMap.clear();
    }

    public void increment(String key, Long lastRequestTime) {
        Bucket bucket = rateLimitingMap.get(key);
        if (bucket == null) {
            bucket = new Bucket(lastRequestTime, 1);
            rateLimitingMap.put(key, bucket);
        } else {
            bucket.increment();
            rateLimitingMap.put(key, bucket);
        }
    }

    public void refreshIncrement(String key, Long currentTime) {
        Bucket bucket = rateLimitingMap.get(key);
        bucket.increment();
        bucket.lastRequestTime = currentTime;
        rateLimitingMap.put(key, bucket);
    }

    public boolean isAllowed(String key, int limit, int time) {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minute = rightNow.get(Calendar.MINUTE);
        int second = rightNow.get(Calendar.SECOND);
        int milisecond = rightNow.get(Calendar.MILLISECOND);
        long currentTime = (hour * 60 * 60 * 1000) + (minute * 60 * 1000) + (second * 1000) + milisecond;

        Long lastRequestTime = rateLimitingMap.get(key) == null ? null : rateLimitingMap.get(key).getLastRequestTime();
        int requestCount = rateLimitingMap.get(key) == null ? 0 : rateLimitingMap.get(key).getRequestCount();

        if (lastRequestTime == null) {
            increment(key, currentTime);
            return true;
        }

        if (requestCount < limit && currentTime - lastRequestTime <= time) {
            increment(key, lastRequestTime);
            return true;
        } else if (currentTime - lastRequestTime > time) {
            return refreshSlot(key, currentTime);
        } else if (requestCount >= limit && currentTime - lastRequestTime <= time) {
            return false;
        }
        increment(key, lastRequestTime);
        return true;
    }

    private boolean refreshSlot(String key, long currentTime) {
        decrement(key);
        refreshIncrement(key, currentTime);
        return true;
    }

    private void decrement(String key) {
        Bucket bucket = rateLimitingMap.get(key);
        bucket.decrement();
        rateLimitingMap.put(key, bucket);
    }

    private static class Bucket {

        Long lastRequestTime;
        int requestCount;

        public Bucket(Long lastRequestTime, int requestCount) {
            this.lastRequestTime = lastRequestTime;
            this.requestCount = requestCount;
        }

        public Long getLastRequestTime() {
            return lastRequestTime;
        }

        public int getRequestCount() {
            return requestCount;
        }

        public void increment() {
            requestCount++;
        }

        public void decrement() {
            requestCount = 0;
            lastRequestTime = null;
        }
    }
}