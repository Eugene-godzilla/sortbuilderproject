package com.project.Strategy;

import com.project.model.Car;

public class PowerComparator implements InsertionSortCarComparatorStrategy {

    @Override
    public int compare(Car car1, Car car2) {

        return Integer.compare(car1.getPower(), car2.getPower());
    }
}
