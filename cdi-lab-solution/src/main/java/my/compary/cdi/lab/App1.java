package my.compary.cdi.lab;

import my.compary.cdi.lab.vehicle.Car;
import my.compary.cdi.lab.vehicle.Vehicle;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class App1 {

    public static void main(String[] args) {
        /** TODO: Replace the "null" initialization inside the try statement below with the right initialization code.
         * TIP: You can use the method initialize in the class SeContainerInitializer.
         **/
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Vehicle vehicle = container.select(Vehicle.class).get();
            vehicle.move();

            /** TODO: Replace the "null" initialization with the right code.
             *  TIP: You can obtain the instance of a Car from the container.
             *  If there's an existing instance, fetch the existing one. If there's no instance, create a new one.
            **/
            Car car = container.select(Car.class).get();
            car.move();

            System.out.println("Is the same vehicle? " + car.equals(vehicle));
        }
    }
}
