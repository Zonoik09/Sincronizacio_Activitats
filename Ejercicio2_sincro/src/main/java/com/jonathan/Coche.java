package com.jonathan;

public class Coche implements Runnable {
    private final ParkingLot parkingLot;
    private final String carName;

    public Coche(ParkingLot parkingLot, String carName) {
        this.parkingLot = parkingLot;
        this.carName = carName;
    }

    public void run() {
        try {
            parkingLot.entradaVehiculo(carName);
            parkingLot.salidaVehiculo(carName);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
