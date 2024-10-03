package com.jonathan;

import java.util.concurrent.Semaphore;

public class WebPage {
    private final Semaphore semaphore;

    public WebPage(int maxConnexions) {this.semaphore = new Semaphore(maxConnexions);}

    public void connect(String name) throws InterruptedException {
        if (semaphore.availablePermits() == 0) {
            System.out.println("El servidor esta ple y el usuario "+name +" esta esperant /=");
        }
        semaphore.acquire();
        System.out.println("El usuari "+name+" ha entrat a la web /+");
        int time = (int) (Math.random() * 15000);
        Thread.sleep(time);
    }

    public void disconnect(String name) {
        System.out.println("El usuari "+name+ " a sortir del web /-");
        semaphore.release();
    }
}
