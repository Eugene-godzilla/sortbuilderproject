package com.project.Strategy;

import com.project.model.Car;

public class ModelComparator implements InsertionSortCarComparatorStrategy {

    @Override
    public int compare(Car car1, Car car2) {

        return car1.getModel().compareToIgnoreCase(car2.getModel());
    }
}
