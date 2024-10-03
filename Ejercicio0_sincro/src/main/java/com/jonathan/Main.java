package com.jonathan;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        int datoInicial = 1000;
        int[] resultadoFinal = {datoInicial}; // Usamos un array
        System.out.println("Dada inicial: "+datoInicial);
        // Creem un CyclicBarrier per a 3 fils
        CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("Tots els microserveis han acabat. Combinant els resultats...\nResultat: "+resultadoFinal[0]);
            }
        });

        // Executor per gestionar els fils
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Tasques que simulen els microserveis
        Runnable microservice1 = () -> {
            try {
                System.out.println("Microservei 1 processant dades...");
                // Simulem un treball
                Thread.sleep(1000);
                resultadoFinal[0] += 300;
                System.out.println("Microservei 1 completat.");
                barrier.await(); // Esperem que els altres fils acabin
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        Runnable microservice2 = () -> {
            try {
                System.out.println("Microservei 2 processant dades...");
                Thread.sleep(1500);
                resultadoFinal[0] += 1000;
                System.out.println("Microservei 2 completat.");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        Runnable microservice3 = () -> {
            try {
                System.out.println("Microservei 3 processant dades...");
                Thread.sleep(2000);
                resultadoFinal[0] -= 1200;
                System.out.println("Microservei 3 completat.");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        // Executem les tasques
        executor.submit(microservice1);
        executor.submit(microservice2);
        executor.submit(microservice3);

        // Tanquem l'executor
        executor.shutdown();
    }
}