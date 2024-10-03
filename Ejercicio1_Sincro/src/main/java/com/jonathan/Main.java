package com.jonathan;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        double[] datos = {10, 12, 23, 23, 16, 23, 21, 16};
        // https://stackoverflow.com/questions/3964211/when-to-use-atomicreference-in-java

        AtomicReference<Double> sumaTotal = new AtomicReference<>((double) 0);
        AtomicReference<Double> mediaTotal = new AtomicReference<>((double) 0);
        AtomicReference<Double> desviacionEstandarTotal = new AtomicReference<>((double) 0);

        // Barrera para sincronizar las tareas
        CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("Varianza final: " + desviacionEstandarTotal.get());
            }
        });

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Runnable para calcular la suma
        Runnable sumas = () -> {
            double suma = 0;
            System.out.println("Procesando datos... Realizando suma...");
            for (double dato : datos) {
                suma += dato;
            }
            sumaTotal.set(suma);
            System.out.println("Suma completada: " + suma);
            try {
                barrier.await();
                barrier.await();
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        };

        // Runnable para calcular la media
        Runnable media = () -> {
            try {
                barrier.await();
                System.out.println("Procesando datos... Realizando media...");
                double operation = sumaTotal.get() / datos.length;
                System.out.println("Media realizada con éxito: " + operation);
                mediaTotal.set(operation);
                barrier.await();
                barrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        // Runnable para calcular la varianza
        Runnable varianza = () -> {
            try {
                barrier.await();
                barrier.await();
                System.out.println("Procesando datos... Realizando varianza...");
                double sumaDiferencia = 0.0;
                for (double dato : datos) {
                    sumaDiferencia += Math.pow(dato - mediaTotal.get(), 2);
                }
                double varianzas = sumaDiferencia / datos.length;
                desviacionEstandarTotal.set(varianzas);
                System.out.println("Proceso terminado... Varianza calculada. \nTodos los servicios terminados");
                barrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        // Ejecutar las tareas
        executor.submit(sumas);
        executor.submit(media);
        executor.submit(varianza);

        // Cerrar el executor
        executor.shutdown();
    }
}
