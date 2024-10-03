package com.jonathan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Semaphore semaphore = new Semaphore(0);


    public static void main(String[] args) {
        int capacity = 3;
        System.out.println("Llançant LongRunningTask...");

        // Llançar la tasca de duració indeterminada
        ParkingLot task = new ParkingLot(capacity);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        task.start();

        for (int i = 1; i <= 10; i++) {
            executor.submit(new Coche(task, "Vehicle" + i));
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}