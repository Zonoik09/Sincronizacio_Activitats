package com.jonathan;

import java.util.concurrent.Semaphore;

public class ParkingLot extends Thread{
    private final Semaphore semaphore;

    public ParkingLot(int capacity) {this.semaphore = new Semaphore(capacity);}

    public void entradaVehiculo(String name) throws InterruptedException {
        if (semaphore.availablePermits() == 0) {
            System.out.println("El cotxe "+name+" esta esperant a poder entrar, aparcament ple /=");
        }
        semaphore.acquire();
        System.out.println("El cotxe "+name+" ha entrat al aparcament /+");
        int time = (int) (Math.random() * 15000);
        Thread.sleep(time);
    }

    public void salidaVehiculo(String name) {
        System.out.println("El cotxe "+name+ " a sortir del aparcament /-");
        semaphore.release();
    }
}

