package com.project.model;

import java.util.ArrayList;
import java.util.List;

public class CarTest {

    public static void main(String[] args) {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car.Builder().setModel("BMW").setPower(250).setYear(2020).build());
        cars.add(new Car.Builder().setModel("Audi").setPower(200).setYear(2018).build());
        cars.add(new Car.Builder().setModel("Tesla").setPower(400).setYear(2022).build());

        System.out.println("Before sort:");
        cars.forEach(System.out::println);

        cars.sort(CarComparators.BY_POWER);
        System.out.println("\nSorted by power:");
        cars.forEach(System.out::println);
    }
}
