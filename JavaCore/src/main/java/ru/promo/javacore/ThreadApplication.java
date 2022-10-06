package ru.promo.javacore;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadApplication {
    private static volatile int a = 0;
    private static AtomicInteger val = new AtomicInteger(0);

    private static ThreadLocal<Integer> local = new ThreadLocal<>();


    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();
        local.set(0);

        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

//        thread.start();
//        thread.run();


        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (object) {
                        a++;
                    }
                    // a = a + 1
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (object) {
                        if (a % 2 == 0) System.out.println(a);
                    }
                }
            }
        };

//        Thread thread1 = new Thread(runnable1);
//        Thread thread2 = new Thread(runnable2);
//
//        thread1.start();
//        thread2.start();

        Lock lock = new ReentrantLock();

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                local.set(0);
                for (int i = 0; i < 100000; i++) {
                    lock.lock();
                    a++;
                    lock.unlock();
                    //a = a + 1

                    val.incrementAndGet();
                    local.set(local.get() + 1);
                }
                System.out.println(Thread.currentThread().getName() + " : " + local.get());
            }
        };

        Runnable runnable4 = new Runnable() {
            @Override
            public void run() {
                local.set(0);
                for (int i = 0; i < 100000; i++) {
                    lock.lock();
                    a--;
                    lock.unlock();

                    val.decrementAndGet();
                    local.set(local.get() - 1);
                }

                System.out.println(Thread.currentThread().getName() + " : " + local.get());
            }
        };

        Thread thread3 = new Thread(runnable3);
        Thread thread4 = new Thread(runnable4);

        thread3.start();
        thread4.start();

        thread3.join();
        thread4.join();

        System.out.println(a);
        System.out.println(val.get());
        System.out.println(Thread.currentThread().getName() + " : " + local.get());
    }
}
