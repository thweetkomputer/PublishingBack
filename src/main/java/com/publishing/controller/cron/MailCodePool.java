package com.publishing.controller.cron;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MailCodePool {
    private static final HashMap<String, Properties> mailPool = new HashMap<>(); // email -> properties
    private static final int NUM_THREADS = 50;
    private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(NUM_THREADS);

    public static void putMailCode(String email, int code) {
        Properties properties = new Properties();
        properties.put("email", email);
        properties.put("code", code);
//        properties.put("time", new Date());
        mailPool.put(email, properties);
        pool.schedule(new ClearDaemon(email), 5, TimeUnit.SECONDS);
    }

    public static Integer getMailCode(String email) {
        Properties properties = mailPool.remove(email);
        if (properties == null)
            return null;
        return (Integer) properties.get("code");
    }

    private static void removeMailCode(String email) {
        mailPool.remove(email);
    }

    private static class ClearDaemon implements Runnable {
        private final String email;

        public ClearDaemon(String email) {
            this.email = email;
        }

        @Override
        public void run() {
            removeMailCode(email);
        }
    }
}
