package ru.promo.springapp.service;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SomeJob {
//    @Async
    @SchedulerLock(name = "task_lock", lockAtLeastFor = 5000)
    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void task() throws InterruptedException {
        log.info("Schedule task run in {}", Thread.currentThread().getName());
    }

//    @Scheduled(cron = "${task2.cron}")
    public void task2() throws InterruptedException {
        log.info("Schedule task2 run in {}", Thread.currentThread().getName());
    }
}
