package ru.promo.javacore;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Thread2Application {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName()
                + ": " + IntStream.range(1, 100).sum());

        Callable<Integer> callable = () -> {
            Thread.sleep(3000);
            return IntStream.range(1, 100).sum();
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future = executorService.submit(callable);

        List<Callable<Integer>> callableList = List.of(callable, callable, callable, callable, callable);

//        List<Future<Integer>> futures = executorService.invokeAll(callableList);


        System.out.println(Thread.currentThread().getName() + " End main");
//        System.out.println(future.get());
//        futures.forEach(f -> {
//                    try {
//                        System.out.println(f.get());
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//        );
        System.out.println("Finished");


        executorService.shutdown();

        Void unused = CompletableFuture.runAsync(runnable)
                .thenRun(runnable)
                .get();

        CompletableFuture.supplyAsync(() -> IntStream.range(100, 200).sum())
                .thenAccept(System.out::println);

    }
}
