package com.project.strategy;

import com.project.model.Car;

public class PowerComparator implements CarComparatorStrategy {

    @Override
    public int compare(Car car1, Car car2) {

        return Integer.compare(car1.getPower(), car2.getPower());
    }
}
